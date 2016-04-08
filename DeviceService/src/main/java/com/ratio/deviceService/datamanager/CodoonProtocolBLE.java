package com.ratio.deviceService.datamanager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ratio.BTDeviceService.R;
import com.ratio.deviceService.DisconveryManager;
import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.bean.BluetoothUser;
import com.ratio.deviceService.bean.CodoonBluethoothDevice;
import com.ratio.deviceService.ble.BleSyncManager;
import com.ratio.deviceService.ble.CodoonDeviceUpgradeManager;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.DataUtil;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.SendData;
import com.ratio.deviceService.logic.UserData;
import com.ratio.util.FilePathConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class CodoonProtocolBLE extends BaseAccessorySyncManager {
    private static final int SEARTCH_TIME = 15000;
    private static final String TAG = "CodoonProtocolBLE";
    private DisconveryManager.OnSeartchCallback mSeartchCallBack = new DisconveryManager.OnSeartchCallback() {
        public boolean onSeartch(CodoonBluethoothDevice paramCodoonBluethoothDevice, byte[] paramArrayOfByte) {
            int i;
            String str1 = CodoonProtocolBLE.this.mContext.getSharedPreferences("MyPrefsFile", 0).getString("Address", null);
            String str2 = paramCodoonBluethoothDevice.getName();
            BluetoothDevice localBluetoothDevice = paramCodoonBluethoothDevice.getDevice();
            Log.i("CodoonProtocolBLE", "find device:" + str2 + "<" + localBluetoothDevice.getAddress() + ">" + " my device:<" + str1 + ">");
            BluetoothUser localBluetoothUser = AccessoryDeviceHelper.parseDevice(localBluetoothDevice, paramArrayOfByte);
            if (localBluetoothUser != null) {
                AccessoryManager.setSupportFriends(CodoonProtocolBLE.this.mContext, localBluetoothDevice.getAddress(), localBluetoothUser.isRomBand);
                CodoonProtocolBLE.this.mAccessoryManager.setIsTurnFriend(localBluetoothDevice.getAddress(), localBluetoothUser.iscanFriend);
            }
            if (str2 != null) {
                i = 1;
                if (CodoonProtocolBLE.this.targetDeviceName != null)
                    if ((str1 != null) || (str2 == null))
                        return false;
                if (str2 != null)
                    CodoonProtocolBLE.this.sendMsgBack(33, 0, 0, paramCodoonBluethoothDevice);
            }
            do
                while (true) {

                    if ((str1 != null) || (str2 == null))
                        break;
                    if (str2.startsWith(CodoonProtocolBLE.this.targetDeviceName)) {
                        CodoonProtocolBLE.this.mDiscoveryBleManager.stopSearch();
                        CodoonProtocolBLE.this.mBLEDevice = localBluetoothDevice;
                        CodoonProtocolBLE.this.connectToBLEDevice();
                        return true;
                    }
                    CodoonProtocolBLE.this.sendMsgBack(33, 0, 0, paramCodoonBluethoothDevice);
                }
            while ((str1 == null) || (!(str1.equals(localBluetoothDevice.getAddress()))));
            CodoonProtocolBLE.this.mDiscoveryBleManager.stopSearch();
            CodoonProtocolBLE.this.mBLEDevice = localBluetoothDevice;
            DataUtil.DebugPrint(paramArrayOfByte);
            CodoonProtocolBLE.this.connectToBLEDevice();
            return true;
        }

        public boolean onSeartchTimeOut() {
            CodoonProtocolBLE.this.sendEmptyMsgBack(34);
            return false;
        }
    };

    public CodoonProtocolBLE(Context paramContext) {
        super(paramContext);
        init();
    }

    private void initUpgradeManager() {
        if (this.mUpGradeManager == null)
            this.mUpGradeManager = new CodoonDeviceUpgradeManager(this.mContext, new DeviceUpgradeCallback() {
                public void onChangeToBootMode() {
                    Log.d("CodoonProtocolBLE", "has chage to: boot mode");
                    CodoonProtocolBLE.this.sendEmptyMsgBack(227);
                }

                public void onCheckBootResult(boolean paramBoolean) {
                    Log.d("enlong", "onCheckBootResult:" + paramBoolean);
                    AccessoryManager.isFirstAutoSync = true;
                    CodoonProtocolBLE.this.sendMsgBack(226, 0, 0, Boolean.valueOf(paramBoolean));
                }

                public void onConnectBootSuccess() {
                    Log.d("CodoonProtocolBLE", "boot mode connect success");
                    CodoonProtocolBLE.this.sendEmptyMsgBack(228);
                }

                public void onGetBootVersion(String paramString) {
                    Log.d("CodoonProtocolBLE", "onGetBootVersion" + paramString);
                    CodoonProtocolBLE.this.sendMsgBack(229, 0, 0, paramString);
                }

                public void onTimeOut() {
                    CodoonProtocolBLE.this.stopSyncDevice();
                    Log.d("enlong", "onTimeOut");
                    CodoonProtocolBLE.this.sendEmptyMsgBack(255);
                }

                public void onWriteFrame(int paramInt1, int paramInt2) {
                    Log.d("CodoonProtocolBLE", "onWriteFrame:" + paramInt1);
                    CodoonProtocolBLE.this.sendMsgBack(225, paramInt1, paramInt2, null);
                }
            });
        this.mUpGradeManager.setTryConnectCounts(3);
    }

    private void stopBleSync() {
        this.isStartBle = false;
        this.connectHandler.removeMessages(242);
        this.connectHandler.removeMessages(241);
        this.connectHandler.removeMessages(2);
        unRegisterBle();
        stopSeatrch();
        if (this.mBLESyncManager != null)
            this.mBLESyncManager.cancel();
        if (this.mUpGradeManager == null)
            return;
        this.mUpGradeManager.stop();
    }

    protected void connectToBLEDevice() {
        if ((this.action != 101) && (this.mBLEDevice != null) && ("Cboot".equals(AccessoryConfig.getStringValue(this.mContext, this.mBLEDevice.getAddress())))) {
            showMsgUpgradeWarning();
            return;
        }
        this.connectHandler.removeMessages(241);
        this.connectHandler.sendEmptyMessageDelayed(241, 1200L);
    }

    public void connectToDeviceRightNow(BluetoothDevice paramBluetoothDevice) {
        if (paramBluetoothDevice == null)
            return;
        this.mBLEDevice = paramBluetoothDevice;
        String str1;
        String str2 = null;
        if (paramBluetoothDevice.getName() == null) {
            str1 = AccessoryConfig.getStringValue(this.mContext, paramBluetoothDevice.getAddress());
            setTargetDevice(str1);
            if ((this.targetDeviceName != null) && (this.targetDeviceName.equals("Cboot"))) {
                setAction(101);
                str2 = AccessoryConfig.getStringValue(this.mContext, "com.codoon.gps.key_accessory_upfile");
                if (!(TextUtils.isEmpty(str2))) ;

            }
        }
        for (String str3 = FilePathConstants.getAccessoryDownLoadPath() + File.separator + "CBL_wx_3.16.1.bin"; ; str3 = str2) {
            setUpGradeFilePath(str3);
            this.isAutoSeartchNext = false;
            this.connectHandler.removeCallbacks(this.timeConnect);
            this.connectHandler.postDelayed(this.timeConnect, 70000L);
            connectToBLEDevice();
//                str1 = paramBluetoothDevice.getName();
            break;
        }
    }
    public void destroy() {
        super.destroy();
        this.mBLEDevice = null;
        if (this.mBLESyncManager == null)
            return;
        this.mBLESyncManager.cancel();
        this.mBLESyncManager.stop();
        this.mBLESyncManager = null;
    }
    public void init() {
        if (!(AccessoryManager.isSupportBLEDevice(this.mContext)))
            return;
        Log.d("CodoonProtocolBLE", "CodoonProtocolBLE init");
        this.mDeviceID = this.mAccessoryManager.getCurAccessory().id;
        if (this.mDiscoveryBleManager == null)
            this.mDiscoveryBleManager = new DisconveryManager(this.mContext, this.mSeartchCallBack);
        this.mAccessoryWareManager = new AccessoryWareManager(this.mContext);
        AccessoryConfig.userID = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        initUpgradeManager();
        if (this.mISyncDataCallback == null)
            this.mISyncDataCallback = new ISyncDataCallback() {
                public void onBattery(int paramInt) {
                    CodoonProtocolBLE.this.curDeviceButtey = paramInt;
                    CodoonProtocolBLE.this.sendButteryBack(paramInt);
                    Log.d("CodoonProtocolBLE", "get battery");
                    CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostClearSportData());
                }

                public void onBindSucess() {
                    Log.d("CodoonProtocolBLE", "Bind Sucess");
                    if (CodoonProtocolBLE.this.action != 1)
                        return;
                    CodoonProtocolBLE.this.sendEmptyMsgBack(18);
                    AccessoryManager.isFirstAutoSync = true;
                    CodoonProtocolBLE.this.stopSyncDevice();
                    CodoonProtocolBLE.this.saveBasicInfo();
                }

                public void onClearDataSuccessed() {
                    Log.d("CodoonProtocolBLE", "onClearDataSuccessed");
                    AccessoryManager.isFirstAutoSync = false;
//                    CodoonProtocolBLE.this.stopSyncDevice();
                    CodoonProtocolBLE.this.sendEmptyMsgBack(12);
                }

                public void onConnectSuccessed() {

                    StringBuilder localStringBuilder1 = new StringBuilder();
                    StringBuilder localStringBuilder2 = localStringBuilder1.append("BLE onConnectSuccessed ACTION_CONNECT ?");
                    boolean bool;
                    Log.i("Action:--------------", action + "");
                    if (action == 1) {
                        bool = true;
                        Log.i("CodoonProtocolBLE", bool + "");
                        CodoonProtocolBLE.this.sendEmptyMsgBack(2);
                        CLog.i("CodoonProtocolBLE", "BLE onConnectSuccessed and send order to id:");
                        if (action != 1) {
                            mBLESyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));
                            return;
                        }
                        mBLESyncManager.SendDataToDevice(SendData.getPostDeviceID());
                    }

                    mBLESyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));

                  /*  while (true)
                    {
                        return;
                        bool = false;
                        break;
                        label86: mBLESyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));
                    }*/
                }

                public void onFriedWarningSuccess() {
                    Log.d("CodoonProtocolBLE", "onFriedWarningSuccess()");
                    CodoonProtocolBLE.this.sendEmptyMsgBack(49);
                    CodoonProtocolBLE.this.stopSyncDevice();
                }

                public void onGetDeviceID(String paramString) {
                    Log.d("CodoonProtocolBLE", "onGetDeviceID(): " + paramString);
                    if (CodoonProtocolBLE.this.isRightDeviceByID(paramString)) {
                        CodoonProtocolBLE.this.mDeviceID = paramString;
                        CodoonProtocolBLE.this.sendDeviceIDCallBack(paramString);
                        CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));
                        return;
                    }
                    CodoonProtocolBLE.this.sendEmptyMsgBack(251);
                }

                public void onGetDeviceTime(String paramString) {
                    Log.d("CodoonProtocolBLE", "onGetDeviceTime(): " + paramString);
                }

                public void onGetOtherDatas(ArrayList<Integer> paramArrayList) {
                }

                public void onGetUserInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8) {
                    Log.d("CodoonProtocolBLE", "onGetUserInfo:height " + paramInt1 + " weigh: " + paramInt2 + " age: " + paramInt3 + " gender" + paramInt4 + " stepLength" + paramInt5 + " runLength" + paramInt6 + " sportType" + paramInt7 + " goalValue" + paramInt8);
                }

                public void onGetVersion(String paramString) {
                    Log.i("CodoonProtocolBLE", "BLE onGetVersion :" + paramString);
                    CodoonProtocolBLE.this.curDeviceVersion = paramString;
                    CodoonProtocolBLE.this.sendVersionIdBack(CodoonProtocolBLE.this.curDeviceVersion);
                    switch (CodoonProtocolBLE.this.action) {
                        default:
                            return;
                        case 8:
                            CodoonProtocolBLE.this.stopSyncDevice();
                            return;
                        case 1:
                    }
                    if (CodoonProtocolBLE.this.isAutoSeartchNext) {
                        CodoonProtocolBLE.this.connectHandler.removeMessages(242);
                        CodoonProtocolBLE.this.connectHandler.sendEmptyMessageDelayed(242, 10000L);
                    }
                    Log.d("CodoonProtocolBLE", "begin to bind");
                    CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostBindOrder());
                }

                public void onNullData() {
                }

                public void onSetFrindSwitchOver() {
                    Log.d("CodoonProtocolBLE", "onSetFrindSwitchOver()");
                    CodoonProtocolBLE.this.sendEmptyMsgBack(50);
                    CodoonProtocolBLE.this.stopSyncDevice();
                }

                public void onSetTargetStepOver() {
                }

                public void onSyncDataOver(HashMap<String, AccessoryValues> paramHashMap, ByteArrayOutputStream paramByteArrayOutputStream) {
                    Log.d("CodoonProtocolBLE", "Sync Data Over!");
                   /* if (paramHashMap == null)
                        CLog.d("CodoonProtocolBLE", "data is null");
                    while (true)
                    {
                        CLog.d("CodoonProtocolBLE", "begin to get battery!");
                        CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostGetUserInfo2());
                        return;
                        CodoonProtocolBLE.this.curDataMap = paramHashMap;
                        CodoonProtocolBLE.this.mAccessoryManager.saveToDB(paramHashMap);
                        CodoonProtocolBLE.this.sendTotalDataback(paramHashMap);
                        paramHashMap = AccessoryDataParseUtil.getCurrentData(paramHashMap);
                        CodoonProtocolBLE.this.accessoriesTotals = CodoonProtocolBLE.this.getTotalSpotsDatas(paramHashMap);
                        int i = 1;
                        if (CodoonProtocolBLE.this.mAccessoryManager.isDataAvailable(paramHashMap))
                        {
                            i = 0;
                            Message localMessage = CodoonProtocolBLE.this.connectHandler.obtainMessage();
                            localMessage.what = 9;
                            localMessage.obj = paramByteArrayOutputStream.toByteArray();
                            CodoonProtocolBLE.this.connectHandler.sendMessage(localMessage);
                        }
                        CodoonProtocolBLE.this.sendMsgBack(5, i, 0, paramHashMap);
                    }*/
                }

                public void onSyncDataProgress(int paramInt) {
                    CodoonProtocolBLE.this.sendMsgBack(1, paramInt, 0, null);
                }

                public void onTimeOut() {
                    Log.i("CodoonProtocolBLE", "BLE conenct outTime");
                    CodoonProtocolBLE.this.stopSyncDevice();
                    CodoonProtocolBLE.this.sendEmptyMsgBack(255);
                }

                public void onUpdateAlarmReminderSuccessed() {
                    Log.d("enlong", "onUpdateAlarmReminderSuccessed");
                    CodoonProtocolBLE.this.stopSyncDevice();
                    CodoonProtocolBLE.this.sendEmptyMsgBack(11);
                }

                public void onUpdateHeartWarningSuccess() {
                }

                public void onUpdateTimeSuccessed() {
                    Log.i("UpdateTimeSucced:------------------",action+"");
                    switch (CodoonProtocolBLE.this.action) {
                        case 6:
                        case 9:
                        default:
                            return;
                        case 1:
                        case 8:
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostDeviceTypeVersion());
                            return;
                        case 4:
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(CodoonProtocolBLE.this.setUserInfoOrder());
                            return;
                        case 3:
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(CodoonProtocolBLE.this.setUserColockInfoOrder());
                            return;
                        case 5:
                        case 11:
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(CodoonProtocolBLE.this.setUserAlarmOrder());
                            return;
                        case 2:
                            CodoonProtocolBLE.this.connectHandler.removeCallbacks(CodoonProtocolBLE.this.timeConnect);
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostSyncDataByFrame());
                            return;
                        case 7:
                            CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.getPostGetUserInfo2());
                            return;
                        case 10:
                    }
                    CodoonProtocolBLE.this.mBLESyncManager.SendDataToDevice(SendData.postBlueFriendRequst());
                }

                public void onUpdateUserinfoSuccessed() {
                    Log.d("CodoonProtocolBLE", "onUpdateUserinfoSuccessed");
                    switch (CodoonProtocolBLE.this.action) {
                        case 3:
                        default:
                            return;
                        case 2:
                        case 4:
                    }
                    CodoonProtocolBLE.this.stopSyncDevice();
                    CodoonProtocolBLE.this.sendEmptyMsgBack(10);
                }
            };
        this.mBLESyncManager = new BleSyncManager(this.mContext, this.mISyncDataCallback);
    }

    public boolean isRightDeviceByID(String paramString) {
        return true;
    }

    public void onBlutoothStateChanged(boolean paramBoolean) {
        Log.d("CodoonProtocolBLE", "onBlutoothStateChanged isStart:" + isStart + " isEnable:" + paramBoolean);
        if ((this.isStartBle) && (paramBoolean)) {
            isStart = true;
            this.connectHandler.removeMessages(2);
            sendEmptyMsgBack(61680);
            if (this.mBLEDevice != null)
                this.mBLEDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(this.mBLEDevice.getAddress());
            this.connectHandler.sendEmptyMessageDelayed(2, 500L);
        }
        this.mBLEDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(this.mBLEDevice.getAddress());
        connectToBLEDevice();
        return;

    }

    public void parseDeviceInfo(BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte) {
    }

    public void seartchBle() {
        this.mBLEDevice = null;
        BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!(AccessoryManager.isSupportBLEDevice(this.mContext)))
            return;
        if (localBluetoothAdapter.isEnabled()) {
            this.mDiscoveryBleManager.setTime_out(15000);
            this.mDiscoveryBleManager.startSearch();
            return;
        }
        Log.d("CodoonProtocolBLE", "can not seartch beacuse mBluetoothAdapter is close");
    }

    public void setAction(int paramInt) {
        super.setAction(paramInt);
        if (paramInt == 101) {
            if (this.mUpGradeManager != null)
                this.mUpGradeManager.registerBLE();
            return;
        }
        this.mBLESyncManager.register(this.mISyncDataCallback);
    }

    public void setTargetDevice(String paramString) {
        super.setTargetDevice(paramString);
    }

    public void setUpGradeFilePath(String paramString) {
        if (this.mUpGradeManager != null) {
            if (paramString != null)
                return;
            paramString = "";
        }
        while (true) {
            this.mUpGradeManager.setUpgradeFilePath(paramString);
            return;

        }
    }

    public void start() {
        if (isStart) {
            Log.d("CodoonProtocolBLE", "sync has start");
            return;
        }
        this.mDeviceID = "";
        this.connectHandler.removeCallbacks(this.timeConnect);
        this.connectHandler.postDelayed(this.timeConnect, 70000L);
        if (!(AccessoryManager.isSupportBLEDevice(this.mContext))) {
            Toast.makeText(this.mContext, this.mContext.getString(R.string.action_settings), Toast.LENGTH_SHORT).show();
            this.connectHandler.removeCallbacks(this.timeConnect);
            sendEmptyMsgBack(253);
            return;
        }
        if ((this.mAccessoryManager.isBindAccessory()) && (this.mAccessoryWareManager.isBootMode()) && (this.action != 101)) {
            sendEmptyMsgBack(227);
            Toast.makeText(this.mContext, "", Toast.LENGTH_SHORT).show();
            return;
        }
        registerBle();
        BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!(localBluetoothAdapter.isEnabled())) {
            Log.e("CodoonProtocolBLE", "ble closed and open the ble");
            this.isStartBle = true;
            localBluetoothAdapter.enable();
        }
        do {
            isStart = true;
            this.isStartBle = true;
            Log.d("enlong", " is connect:" + this.mBLESyncManager.isConnect());
            sendEmptyMsgBack(61680);
            if ((this.mBLEDevice == null) || (!(this.mAccessoryManager.isBindAccessory())))
                this.connectHandler.removeCallbacks(this.timeConnect);
            if (this.action != 101)
                continue;
            this.mUpGradeManager.start(this.mBLEDevice);
            return;
        }
        while (this.mBLESyncManager == null);
        if (this.mBLESyncManager.isConnect()) {
            this.mBLESyncManager.start(SendData.getPostSyncTime(System.currentTimeMillis()));
            return;
        }
        this.mBLESyncManager.start(this.mBLEDevice);
        this.connectHandler.removeCallbacks(this.timeConnect);
        this.connectHandler.postDelayed(this.timeConnect, 70000L);
        seartchBle();
        return;

    }

    public void startSeartch() {
        this.mDeviceID = "";
        this.targetDeviceName = null;
        this.mBLEDevice = null;
        registerBle();
        this.isStartBle = true;
        BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!(localBluetoothAdapter.isEnabled())) {
            Log.e("CodoonProtocolBLE", "ble closed and open the ble");
            localBluetoothAdapter.enable();
            return;
        }
        isStart = true;
        seartchBle();
    }

    public void stopSeatrch() {
        if (this.mDiscoveryBleManager == null)
            return;
        this.mDiscoveryBleManager.stopSearch();
    }

    public void stopSyncDevice() {
        Log.i("stopsyncdevice:----------",isStart+"");
        if (this.timeConnect != null)
            this.connectHandler.removeCallbacks(this.timeConnect);
        this.connectHandler.removeMessages(2);
        this.connectHandler.removeMessages(240);
        isStart = false;
        stopSeatrch();
        stopBleSync();
    }

    public void unbind() {
        super.unbind();
        this.mAccessoryWareManager.setIsBootMode(false);
        this.mAccessoryWareManager.setNeedUpload(false);
        this.mBLEDevice = null;
        if (this.mBLESyncManager == null)
            return;
        this.mBLESyncManager.stop();
    }
}