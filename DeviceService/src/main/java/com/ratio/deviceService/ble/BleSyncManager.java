package com.ratio.deviceService.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ratio.deviceService.accessory.Accessory;
import com.ratio.deviceService.communication.IConnectCallback;
import com.ratio.deviceService.data.AccessoryDataParseUtil;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.SaveManager;
import com.ratio.deviceService.data.SendData;
import com.ratio.deviceService.data.TimeoutCheck;
import com.ratio.deviceService.datamanager.AccessoryValues;
import com.ratio.util.MobileUtil;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class BleSyncManager extends BaseCommunicationManager {
    private final int CONNECT_AGAIN = 2;
    private final int ORDER_CONNECT = 1;
    private String TAG = "BleSyncManager";
    private int TIME_OUT = 15000;
    private final int TIME_OUT_CALL_BACK = 273;
    private IConnectCallback connectBack;
    private boolean isHuawei = MobileUtil.isCurMobileManuConnect();
    private BleManager mBleManager;
    private BluetoothDevice mDevice;
    private Handler mHandler;
    private SaveManager.eSaveType mSaveType = SaveManager.eSaveType.DATABSE;
    private TimeoutCheck.ITimeoutCallback mTimeoutCallback = new TimeoutCheck.ITimeoutCallback() {
        public void onConnectFailed(int paramAnonymousInt) {
            Log.d(BleSyncManager.this.TAG, "ConnectFailed() tryConnectIndex:" + paramAnonymousInt);
            BleSyncManager.this.mHandler.sendEmptyMessage(273);
        }

        public void onReConnect(int paramAnonymousInt) {
            Log.d(BleSyncManager.this.TAG, "reConnect() tryConnectIndex:" + paramAnonymousInt);
            BleSyncManager.this.reConnectBle();
        }

        public void onReSend() {
            Log.d(BleSyncManager.this.TAG, "reSend()");
            if (BleSyncManager.this.isStart)
                BleSyncManager.this.reSendDataToDevice(BleSyncManager.this.mLastSendData);
        }

        public void onReceivedFailed() {
            Log.d(BleSyncManager.this.TAG, "receivedFailed()");
            BleSyncManager.this.mHandler.sendEmptyMessage(273);
        }
    };
    private int reconnect_again = 1500;

    public BleSyncManager(Context paramContext, ISyncDataCallback paramISyncDataCallback) {
        if (this.isHuawei) {
            this.TIME_OUT = 12000;
            this.reconnect_again = 2500;
        }
        CLog.i(this.TAG, "isHuawei:" + this.isHuawei);
        this.mISyncDataCallbacks = new ArrayList();
        this.mISyncDataCallbacks.add(paramISyncDataCallback);

        this.mHandler = new Handler() {
            public void handleMessage(Message paramAnonymousMessage) {
                super.handleMessage(paramAnonymousMessage);
                Iterator localIterator2 = null;
                if (paramAnonymousMessage.what == 1)
                    if (BleSyncManager.this.isStart) {
                        Log.i(BleSyncManager.this.TAG, "send order to device after ble connect " + BleSyncManager.this.mISyncDataCallbacks.size());
                        localIterator2 = BleSyncManager.this.mISyncDataCallbacks.iterator();
                        if (localIterator2.hasNext())
                            ((ISyncDataCallback) localIterator2.next()).onConnectSuccessed();
                    }
                while (true) {


                    Log.e(BleSyncManager.this.TAG, "not start");
                    if (paramAnonymousMessage.what == 2) {
                        Log.i(BleSyncManager.this.TAG, "connect  ble device  again");
                        BleSyncManager.this.mBleManager.connect(BleSyncManager.this.mDevice, true);
                        return;
                    }
                    if ((paramAnonymousMessage.what == 273) && (BleSyncManager.this.isStart)) {
                        Iterator localIterator1 = BleSyncManager.this.mISyncDataCallbacks.iterator();
                        while (localIterator1.hasNext())
                            ((ISyncDataCallback) localIterator1.next()).onTimeOut();
                        return;
                    }
                    return;
                }
            }
        };
        this.mContext = paramContext;
        this.mTimeoutCheck = new TimeoutCheck(this.mTimeoutCallback);
        this.mTimeoutCheck.setTryConnectCounts(5);
        this.mTimeoutCheck.setTimeout(this.TIME_OUT);
        this.mBleManager = new BleManager(this.mContext);
        registerBLE();
    }

    private String countTime(ArrayList<Integer> paramArrayList) {
        int i = 2000 + Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(3)).intValue()));
        int j = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(4)).intValue()));
        int k = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(5)).intValue()));
        int m = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(6)).intValue()));
        int n = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(7)).intValue()));
        int i1 = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(8)).intValue()));
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeZone(TimeZone.getDefault());
        localCalendar.set(i, j, k, m, n, i1);
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localSimpleDateFormat.setTimeZone(TimeZone.getDefault());
        Date localDate = new Date(localCalendar.getTimeInMillis());
        return localSimpleDateFormat.format(localDate);
    }

    private byte encryptMyxor(int paramInt1, int paramInt2) {
        return (byte) ((paramInt1 ^ this.xorKey[paramInt2]) & 0xFF);
    }

    private String getDeviceID(ArrayList<Integer> paramArrayList) {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramArrayList.get(3));
        localStringBuilder.append("-");
        localStringBuilder.append(((((Integer) paramArrayList.get(4)).intValue() & 0xFF) << 8) + (((Integer) paramArrayList.get(5)).intValue() & 0xFF));
        localStringBuilder.append("-");
        localStringBuilder.append(((((Integer) paramArrayList.get(6)).intValue() & 0xFF) << 8) + (((Integer) paramArrayList.get(7)).intValue() & 0xFF));
        localStringBuilder.append("-");
        localStringBuilder.append(((((Integer) paramArrayList.get(8)).intValue() & 0xFF) << 8) + (((Integer) paramArrayList.get(9)).intValue() & 0xFF));
        localStringBuilder.append("-");
        localStringBuilder.append(((Integer) paramArrayList.get(10)).intValue() & 0xFF);
        localStringBuilder.append("-");
        localStringBuilder.append(((((Integer) paramArrayList.get(11)).intValue() & 0xFF) << 8) + (((Integer) paramArrayList.get(12)).intValue() & 0xFF));
        localStringBuilder.append("-");
        localStringBuilder.append(((((Integer) paramArrayList.get(13)).intValue() & 0xFF) << 8) + (((Integer) paramArrayList.get(14)).intValue() & 0xFF));
        localStringBuilder.append("-");
        localStringBuilder.append(((Integer) paramArrayList.get(15)).intValue() & 0xFF);
        return localStringBuilder.toString();
    }

    private void getUserInfo(ArrayList<Integer> paramArrayList) {
        int i = ((Integer) paramArrayList.get(3)).intValue();
        int j = ((Integer) paramArrayList.get(4)).intValue();
        int k = ((Integer) paramArrayList.get(5)).intValue();
        int m = ((Integer) paramArrayList.get(6)).intValue();
        int n = ((Integer) paramArrayList.get(7)).intValue();
        int i1 = ((Integer) paramArrayList.get(8)).intValue();
        int i2 = ((Integer) paramArrayList.get(11)).intValue();
        int i3 = (((Integer) paramArrayList.get(12)).intValue() << 8) + ((Integer) paramArrayList.get(13)).intValue();
        Iterator localIterator = this.mISyncDataCallbacks.iterator();
        while (true) {
            if (!localIterator.hasNext())
                return;
            ((ISyncDataCallback) localIterator.next()).onGetUserInfo(i, j, k, m, n, i1, i2, i3);
        }
    }


    private byte[] intToByte(int[] paramArrayOfInt) {
        Log.i("SendInfo", paramArrayOfInt.toString());
        int j = paramArrayOfInt.length;
        byte[] arrayOfByte = new byte[20];
        int i = 0;
        if (i >= j)
            i = j;
        while (true) {
            if (i < j)
                arrayOfByte[i] = (byte) (paramArrayOfInt[i] & 0xFF);
            if (i >= j && i < 20)
                arrayOfByte[i] = 0;
            if (i >= 20)
                return arrayOfByte;
            i += 1;
        }



    }

    private void reConnectBle() {
        this.mTimeoutCheck.restartChectTime();
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        if (this.mBleManager != null) {
            this.mBleManager.disconnect();
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, this.reconnect_again);
        }
    }

    private void reSendDataToDevice(int[] paramArrayOfInt) {
        if (this.mBleManager != null)
            this.mBleManager.writeIasAlertLevel(this.mWriteClicentUUID, this.mWriteCharacteristicUUID, intToByte(paramArrayOfInt));
    }

    public void SendDataToDevice(int[] paramArrayOfInt) {
        if (this.mBleManager != null) {
            this.mTimeoutCheck.setIsConnection(false);
            this.mTimeoutCheck.setTimeout(this.TIME_OUT);
            this.mTimeoutCheck.setTryConnectCounts(2);
            this.mTimeoutCheck.startCheckTimeout();
            this.mLastSendData = paramArrayOfInt;
            this.mBleManager.writeIasAlertLevel(this.mWriteClicentUUID, this.mWriteCharacteristicUUID, intToByte(paramArrayOfInt));
        }
    }

    protected void analysis(ArrayList<Integer> paramArrayList) {
        Iterator localIterator19;
        if (paramArrayList == null) {
            localIterator19 = this.mISyncDataCallbacks.iterator();
            while (true) {
                if (!localIterator19.hasNext())
                    return;
                ((ISyncDataCallback) localIterator19.next()).onNullData();
            }
        }
        Object localObject = null;
        int i;
        Log.i("analysis--------------------", paramArrayList.get(1).intValue() + "");
        switch (((Integer) paramArrayList.get(1)).intValue()) {
            default:
                Log.d(this.TAG, "on get other datas");
                localObject = this.mISyncDataCallbacks.iterator();
                while (((Iterator) localObject).hasNext())
                    ((ISyncDataCallback) ((Iterator) localObject).next()).onGetOtherDatas(paramArrayList);
            case 129:
                this.mTimeoutCheck.stopCheckTimeout();
                this.mTimeoutCheck.setTryConnectCounts(3);
                this.mTimeoutCheck.setIsConnection(false);
                this.mTimeoutCheck.setTimeout(3000);
                Iterator localIterator17 = this.mISyncDataCallbacks.iterator();
                while (true) {
                    if (!localIterator17.hasNext())
                        return;
                    ((ISyncDataCallback) localIterator17.next()).onConnectSuccessed();
                }
            case 193:
                this.mTimeoutCheck.stopCheckTimeout();
                Iterator localIterator16 = this.mISyncDataCallbacks.iterator();
                while (localIterator16.hasNext())
                    ((ISyncDataCallback) localIterator16.next()).onBindSucess();
            case 130:
                this.mTimeoutCheck.stopCheckTimeout();
                StringBuilder localStringBuilder3 = new StringBuilder();
                String str5 = paramArrayList.get(4) + "." + paramArrayList.get(5);
                Iterator localIterator15 = this.mISyncDataCallbacks.iterator();
                while (localIterator15.hasNext())
                    ((ISyncDataCallback) localIterator15.next()).onGetVersion(str5);
            case 132:
                this.mTimeoutCheck.stopCheckTimeout();
                String str4 = getDeviceID(paramArrayList);
                Iterator localIterator14 = this.mISyncDataCallbacks.iterator();
                while (localIterator14.hasNext())
                    ((ISyncDataCallback) localIterator14.next()).onGetDeviceID(str4);
            case 138:
                this.mTimeoutCheck.stopCheckTimeout();
                Iterator localIterator13 = this.mISyncDataCallbacks.iterator();
                while (localIterator13.hasNext())
                    ((ISyncDataCallback) localIterator13.next()).onUpdateTimeSuccessed();
            case 139:
                this.mTimeoutCheck.stopCheckTimeout();
                String str3 = countTime(paramArrayList);
                Iterator localIterator12 = this.mISyncDataCallbacks.iterator();
                while (localIterator12.hasNext())
                    ((ISyncDataCallback) localIterator12.next()).onGetDeviceTime(str3);
            case 148:
                this.mTimeoutCheck.stopCheckTimeout();
                Iterator localIterator11 = this.mISyncDataCallbacks.iterator();
                while (localIterator11.hasNext())
                    ((ISyncDataCallback) localIterator11.next()).onClearDataSuccessed();
            case 140:
                this.mTimeoutCheck.stopCheckTimeout();
                this.frameCount = ((((Integer) paramArrayList.get(4)).intValue() << 8) + ((Integer) paramArrayList.get(5)).intValue());
                this.indexFrame = 0;
                this.mBaos = new ByteArrayOutputStream();
                Log.d(this.TAG, " framecount:" + this.frameCount);
                if (this.frameCount > 0) {
                    SendDataToDevice(SendData.getPostReadSportData(this.indexFrame));
                    this.mRecordDatas = new ArrayList();
                    return;
                }
                Iterator localIterator9 = this.mISyncDataCallbacks.iterator();
                Iterator localIterator10 = null;
                if (!localIterator9.hasNext())
                    localIterator10 = this.mISyncDataCallbacks.iterator();
                while (true) {
                    if (!localIterator10.hasNext()) {
                        ((ISyncDataCallback) localIterator9.next()).onSyncDataProgress(100);
                        break;
                    }
                    ((ISyncDataCallback) localIterator10.next()).onSyncDataOver(null, null);
                    return;
                }
            case 145:
                this.mTimeoutCheck.stopCheckTimeout();
                int j = ((Integer) paramArrayList.get(2)).intValue();
                ArrayList localArrayList1 = new ArrayList();
                Iterator localIterator6 = null;
                i = 3;
                if (i >= j + 3) {
                    this.mRecordDatas.add(localArrayList1);
                    this.indexFrame += 1;
                    localIterator6 = this.mISyncDataCallbacks.iterator();
                }
                while (true) {
                    if (!localIterator6.hasNext()) {
                        if (this.indexFrame >= this.frameCount) {
                            SendDataToDevice(SendData.postBlueFriendWarning());
                            return;
                        }
                        SendDataToDevice(SendData.getPostReadSportData(this.indexFrame));
                        this.mBaos.write(encryptMyxor(((Integer) paramArrayList.get(i)).intValue(), this.mBaos.size() % 6));
                        ((ArrayList) localObject).add((Integer) paramArrayList.get(i));
                        i += 1;
                        break;
                    }
                    ((ISyncDataCallback) localIterator6.next()).onSyncDataProgress(this.indexFrame * 100 / this.frameCount);
                }
                this.frameCount = 0;
                this.indexFrame = 0;
                Iterator localIterator7 = this.mISyncDataCallbacks.iterator();
                HashMap localHashMap = null;
                Iterator localIterator8;
                if (!localIterator7.hasNext()) {
                    HashMap<String, AccessoryValues> localhashmap = new AccessoryDataParseUtil(this.mContext).analysisDatas(this.mRecordDatas);
                    localObject = this.mISyncDataCallbacks.iterator();
                }
                while (true) {
                    if (!((Iterator) localObject).hasNext()) {
                        ((ISyncDataCallback) localIterator7.next()).onSyncDataProgress(100);
                        break;
                    }
                    ((ISyncDataCallback) ((Iterator) localObject).next()).onSyncDataOver(localHashMap, this.mBaos);
                }
            case 135:
                this.mTimeoutCheck.stopCheckTimeout();
                getUserInfo(paramArrayList);
                return;
            case 133:
                this.mTimeoutCheck.stopCheckTimeout();
                Iterator localIterator5 = this.mISyncDataCallbacks.iterator();
                while (localIterator5.hasNext())
                    ((ISyncDataCallback) localIterator5.next()).onUpdateUserinfoSuccessed();
            case 134:
                this.mTimeoutCheck.stopCheckTimeout();
                Log.d(this.TAG, "update alarm and activity remind success.");
                Iterator localIterator3 = this.mISyncDataCallbacks.iterator();
                String str1 = null;
                Iterator localIterator4 = null;
                if (!localIterator3.hasNext()) {
                    str1 = "";
                    localIterator4 = paramArrayList.iterator();
                }
                while (true) {
                    if (!localIterator4.hasNext()) {
                        Log.d(this.TAG, str1);

                        ((ISyncDataCallback) localIterator3.next()).onUpdateAlarmReminderSuccessed();
                        while (localIterator4.hasNext())
                            ((ISyncDataCallback) localIterator4.next()).onFriedWarningSuccess();
                        break;
                    }
                    j = ((Integer) localIterator4.next()).intValue();
                    StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
                    str1 = ",0x" + Integer.toHexString(j);
                }
            case 136:
                this.mTimeoutCheck.stopCheckTimeout();
                i = 0;
                Iterator localIterator2 = null;
                if (paramArrayList.size() > 15) {
                    i = ((Integer) paramArrayList.get(15)).intValue();
                    localIterator2 = this.mISyncDataCallbacks.iterator();
                }
                while (true) {
                    if (!localIterator2.hasNext()) {

                        i = ((Integer) paramArrayList.get(10)).intValue();
                        Iterator localIterator1 = this.mISyncDataCallbacks.iterator();
                    }
                    ((ISyncDataCallback) localIterator2.next()).onBattery(i);
                    break;
                }
            case 209:

                SendDataToDevice(SendData.postBlueFriendWarning());
                break;
            case 210:

        }
        this.mTimeoutCheck.stopCheckTimeout();
        Iterator localIterator1 = this.mISyncDataCallbacks.iterator();
        while (localIterator1.hasNext())
            ((ISyncDataCallback) localIterator1.next()).onFriedWarningSuccess();
    }


    public void cancel() {
        Log.i("cancle:----------------",isStart+"");
        this.isStart = false;
        this.mLastSendData = null;
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        this.mHandler.removeMessages(273);
        if (this.mTimeoutCheck != null)
            this.mTimeoutCheck.stopCheckTimeout();
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public boolean isConnect() {
        return this.mBleManager.isConnect;
    }

    public void register(ISyncDataCallback paramISyncDataCallback) {
        if ((paramISyncDataCallback != null) && (!this.mISyncDataCallbacks.contains(paramISyncDataCallback)))
            this.mISyncDataCallbacks.add(paramISyncDataCallback);
    }

    public void registerBLE() {
        if (this.connectBack == null)
            this.connectBack = new IConnectCallback() {
                public void connectState(BluetoothDevice paramAnonymousBluetoothDevice, int paramAnonymousInt1, int paramAnonymousInt2) {
                    if (BleSyncManager.this.isHuawei) {
                        BleSyncManager.this.mDevice = paramAnonymousBluetoothDevice;
                        BleSyncManager.this.mTimeoutCheck.setTimeout(BleSyncManager.this.TIME_OUT * 2 / 3);
                        if (paramAnonymousInt2 != 2)
                            if (paramAnonymousInt2 == 0) {
                                if ((BleSyncManager.this.isStart) && (paramAnonymousInt1 != 0)) {
                                    Log.i(BleSyncManager.this.TAG, "disconnected been down so connect again");
                                    BleSyncManager.this.reConnectBle();
                                    return;
                                } else if (BleSyncManager.this.mBleManager != null) {
                                    mBleManager.close();
                                }

                            }

                        BleSyncManager.this.mTimeoutCheck.restartChectTime();
                        BleSyncManager.this.mDevice = paramAnonymousBluetoothDevice;
                        BleSyncManager.this.mHandler.removeMessages(2);

                    }
//                            break label88;

//                        BleSyncManager.this.mBleManager.close();

                }
                  /*  do {
                        do {
                            BleSyncManager.this.mTimeoutCheck.setTimeout(BleSyncManager.this.TIME_OUT / 2);
                        }
                        while (paramAnonymousInt2 != 0);
                        if ((BleSyncManager.this.isStart) && (paramAnonymousInt1 != 0)) {
                            Log.i(BleSyncManager.this.TAG, "disconnected been down so connect again");
                            BleSyncManager.this.reConnectBle();
                            return;
                        }
                    }
                    while (BleSyncManager.this.mBleManager == null);*/


                public void discovered() {
                    if (BleSyncManager.this.isStart) {
                        BleSyncManager.this.mHandler.removeMessages(1);
                        BleSyncManager.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
                    }
                }

                public void getValue(int paramAnonymousInt) {
                }

                public void getValues(ArrayList<Integer> paramAnonymousArrayList) {
                    Log.i("ISStart:----------", isStart + "");
                    if (BleSyncManager.this.isStart) {
                        BleSyncManager.this.analysis(paramAnonymousArrayList);
                        return;
                    }
                    Log.d(BleSyncManager.this.TAG, " isStart:" + BleSyncManager.this.isStart);
                }
            };
        this.mBleManager.register(this.connectBack);
    }

    public void setCharacteristicUUID(String paramString) {
        this.mCharacteristicUUID = paramString;
    }

    public void setClientUUID(String paramString) {
        this.mClicentUUID = paramString;
    }

    public void setSaveType(SaveManager.eSaveType parameSaveType) {
        this.mSaveType = parameSaveType;
    }

    public void setTryConnectCounts(int paramInt) {
        this.mTimeoutCheck.setTryConnectCounts(paramInt);
    }

    public void setWriteCharacteristicUUID(String paramString) {
        this.mWriteCharacteristicUUID = paramString;
    }

    public void setWriteClientUUID(String paramString) {
        this.mWriteClicentUUID = paramString;
    }

    public void start(BluetoothDevice paramBluetoothDevice) {
        this.isStart = true;
        registerBLE();
        Log.i("ISHUAWEI---------------------------", isHuawei + "");
        if (this.isHuawei) {
            this.mBleManager.connect(paramBluetoothDevice, false);

            this.mTimeoutCheck.startCheckTimeout();
            this.mTimeoutCheck.setIsConnection(true);
            this.mTimeoutCheck.setTryConnectCounts(1);
            if (!this.isHuawei) {
                this.mTimeoutCheck.setTimeout(this.TIME_OUT * 3 / 4);
                return;
            }
            this.mTimeoutCheck.setTimeout(this.TIME_OUT / 4);
//            this.mBleManager.connect(paramBluetoothDevice, true);
            return;
        }
    }


    public void start(int[] paramArrayOfInt) {
        this.isStart = true;
        registerBLE();
        SendDataToDevice(paramArrayOfInt);
        this.mTimeoutCheck.setIsConnection(true);
    }

    public void stop() {
        cancel();
        this.mISyncDataCallbacks.clear();
        if (this.mBleManager != null) {
            this.mBleManager.close();
            this.mDevice = null;
        }
    }

    public void unRegisterBLE() {
    }
}