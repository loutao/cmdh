package com.ratio.deviceService.datamanager;

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.ratio.deviceService.communication.mancherster.FSKDeviceDataManager;
import com.ratio.deviceService.communication.mancherster.ManDeviceDataManager;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.SaveManager;
import com.ratio.deviceService.data.SendData;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class RadioDeviceSyncManager extends BaseAccessorySyncManager {
    protected static final String TAG = "RadioDeviceSyncManager";
    private FSKDeviceDataManager mFSKSyncManager;
    private ManDeviceDataManager mManSyncManager;

    public RadioDeviceSyncManager(Context paramContext, String paramString) {
        super(paramContext);
        setTargetDevice(paramString);
        init();
    }

    private void initFSKManager() {
        this.mFSKSyncManager = new FSKDeviceDataManager(this.mContext, new ISyncDataCallback() {
            public void onBattery(int paramAnonymousInt) {
                CLog.i("RadioDeviceSyncManager", "FSK　onBattery()" + paramAnonymousInt);
                RadioDeviceSyncManager.this.curDeviceButtey = paramAnonymousInt;
                RadioDeviceSyncManager.this.sendButteryBack(paramAnonymousInt);
                RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostClearSportData());
            }

            public void onBindSucess() {
            }

            public void onClearDataSuccessed() {
                RadioDeviceSyncManager.this.stopSyncDevice();
            }

            public void onConnectSuccessed() {
                CLog.d("RadioDeviceSyncManager", "FSK onConnectSuccessed() ");
                RadioDeviceSyncManager.this.lastSeartchMethod = RadioDeviceSyncManager.this.curSeartchMethod;
                RadioDeviceSyncManager.this.sendEmptyMsgBack(2);
                RadioDeviceSyncManager.this.connectHandler.removeCallbacks(RadioDeviceSyncManager.this.timeConnect);
                RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostDeviceID());
            }

            public void onFriedWarningSuccess() {
            }

            public void onGetDeviceID(String paramAnonymousString) {
                CLog.d("RadioDeviceSyncManager", "FSK onGetDeviceID(): " + paramAnonymousString);
                if (RadioDeviceSyncManager.this.isRightDeviceByID(paramAnonymousString)) {
                    RadioDeviceSyncManager.this.mDeviceID = paramAnonymousString;
                    RadioDeviceSyncManager.this.sendDeviceIDCallBack(paramAnonymousString);
                    RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));
                    return;
                }
                RadioDeviceSyncManager.this.sendEmptyMsgBack(251);
            }

            public void onGetDeviceTime(String paramAnonymousString) {
                CLog.d("RadioDeviceSyncManager", "FSK onGetDeviceTime(): " + paramAnonymousString);
            }

            public void onGetOtherDatas(ArrayList<Integer> paramAnonymousArrayList) {
                CLog.d("RadioDeviceSyncManager", "FSK onGetOtherDatas");
            }

            public void onGetUserInfo(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8) {
                CLog.d("RadioDeviceSyncManager", "FSK onGetUserInfo:height " + paramAnonymousInt1 + " weigh: " + paramAnonymousInt2 + " age: " + paramAnonymousInt3 + " gender" + paramAnonymousInt4 + " stepLength" + paramAnonymousInt5 + " runLength" + paramAnonymousInt6 + " sportType" + paramAnonymousInt7 + " goalValue" + paramAnonymousInt8);
            }

            public void onGetVersion(String paramAnonymousString) {
                CLog.i("RadioDeviceSyncManager", "FSK onGetVersion :" + paramAnonymousString);
                RadioDeviceSyncManager.this.curDeviceVersion = paramAnonymousString;
                RadioDeviceSyncManager.this.sendVersionIdBack(paramAnonymousString);
                RadioDeviceSyncManager.this.saveBasicInfo();
                RadioDeviceSyncManager.this.stopSyncDevice();
            }

            public void onNullData() {
                CLog.d("RadioDeviceSyncManager", "onNullData");
                RadioDeviceSyncManager.this.sendEmptyMsgBack(240);
                RadioDeviceSyncManager.this.stopFSKConenct();
            }

            public void onSetFrindSwitchOver() {
            }

            public void onSetTargetStepOver() {
            }

            public void onSyncDataOver(HashMap<String, AccessoryValues> paramAnonymousHashMap, ByteArrayOutputStream paramAnonymousByteArrayOutputStream) {
               /* if (paramAnonymousHashMap == null)
                    CLog.d("RadioDeviceSyncManager", "data is null");
                while (true) {
                    RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostGetUserInfo2());
                    return;
                    RadioDeviceSyncManager.this.curDataMap = paramAnonymousHashMap;
                    RadioDeviceSyncManager.this.mAccessoryManager.saveToDB(paramAnonymousHashMap);
                    RadioDeviceSyncManager.this.sendTotalDataback(paramAnonymousHashMap);
                    paramAnonymousHashMap = AccessoryDataParseUtil.getCurrentData(paramAnonymousHashMap);
                    RadioDeviceSyncManager.this.accessoriesTotals = RadioDeviceSyncManager.this.getTotalSpotsDatas(paramAnonymousHashMap);
                    int i = 1;
                    if (RadioDeviceSyncManager.this.mAccessoryManager.isDataAvailable(paramAnonymousHashMap)) {
                        i = 0;
                        Message localMessage = RadioDeviceSyncManager.this.connectHandler.obtainMessage();
                        localMessage.what = 9;
                        localMessage.obj = paramAnonymousByteArrayOutputStream.toByteArray();
                        RadioDeviceSyncManager.this.connectHandler.sendMessage(localMessage);
                    }
                    RadioDeviceSyncManager.this.sendMsgBack(5, i, 0, paramAnonymousHashMap);
                }*/
            }

            public void onSyncDataProgress(int paramAnonymousInt) {
                RadioDeviceSyncManager.this.sendMsgBack(1, paramAnonymousInt, 0, null);
            }

            public void onTimeOut() {
                CLog.i("RadioDeviceSyncManager", "FSK conenct outTime");
                RadioDeviceSyncManager.this.stopSyncDevice();
                if (1 == RadioDeviceSyncManager.this.lastSeartchMethod) {
                    Message localMessage = RadioDeviceSyncManager.this.connectHandler.obtainMessage();
                    localMessage.what = 240;
                    localMessage.arg1 = RadioDeviceSyncManager.this.action;
                    RadioDeviceSyncManager.this.connectHandler.sendMessageDelayed(localMessage, 3000L);
                    return;
                }
                RadioDeviceSyncManager.this.sendEmptyMsgBack(255);
            }

            public void onUpdateAlarmReminderSuccessed() {
                RadioDeviceSyncManager.this.stopSyncDevice();
                RadioDeviceSyncManager.this.sendEmptyMsgBack(11);
            }

            public void onUpdateHeartWarningSuccess() {
            }

            public void onUpdateTimeSuccessed() {
               /* CLog.d("RadioDeviceSyncManager", "onUpdateTimeSuccessed() ");
                switch (RadioDeviceSyncManager.this.action) {
                    default:
                        return;
                    case 1:
                        RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostDeviceTypeVersion());
                        return;
                    case 4:
                        RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserInfoOrder());
                        return;
                    case 3:
                        RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserColockInfoOrder());
                        return;
                    case 5:
                        RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserAlarmOrder());
                        return;
                    case 2:
                }
                RadioDeviceSyncManager.this.connectHandler.removeCallbacks(RadioDeviceSyncManager.this.timeConnect);
                RadioDeviceSyncManager.this.mFSKSyncManager.SendDataToDevice(SendData.getPostSyncDataByFrame());*/
            }

            public void onUpdateUserinfoSuccessed() {
                CLog.d("RadioDeviceSyncManager", "onUpdateUserinfoSuccessed");
                if (RadioDeviceSyncManager.this.action == 4) {
                    RadioDeviceSyncManager.this.sendEmptyMsgBack(10);
                    RadioDeviceSyncManager.this.stopSyncDevice();
                }
            }
        });
        this.mFSKSyncManager.setTryConnectCounts(3);
        this.mFSKSyncManager.setSaveType(SaveManager.eSaveType.DATABSE);
    }

    private void initManchesterManager() {
        this.mManSyncManager = new ManDeviceDataManager(this.mContext, new ISyncDataCallback() {
            public void onBattery(int paramAnonymousInt) {
                RadioDeviceSyncManager.this.curDeviceButtey = paramAnonymousInt;
                CLog.i("RadioDeviceSyncManager", "man　onBattery()" + paramAnonymousInt);
                RadioDeviceSyncManager.this.sendButteryBack(paramAnonymousInt);
                RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostClearSportData());
            }

            public void onBindSucess() {
            }

            public void onClearDataSuccessed() {
                CLog.d("RadioDeviceSyncManager", "onClearDataSuccessed()");
                RadioDeviceSyncManager.this.stopManSyncData();
            }

            public void onConnectSuccessed() {
                CLog.d("RadioDeviceSyncManager", "onConnectSuccessed()");
                RadioDeviceSyncManager.this.lastSeartchMethod = RadioDeviceSyncManager.this.curSeartchMethod;
                RadioDeviceSyncManager.this.sendEmptyMsgBack(2);
                RadioDeviceSyncManager.this.connectHandler.removeCallbacks(RadioDeviceSyncManager.this.timeConnect);
                RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostDeviceID());
            }

            public void onFriedWarningSuccess() {
            }

            public void onGetDeviceID(String paramAnonymousString) {
                CLog.d("RadioDeviceSyncManager", "onGetDeviceID(): " + paramAnonymousString);
                if (RadioDeviceSyncManager.this.isRightDeviceByID(paramAnonymousString)) {
                    RadioDeviceSyncManager.this.mDeviceID = paramAnonymousString;
                    RadioDeviceSyncManager.this.sendDeviceIDCallBack(paramAnonymousString);
                    RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostSyncTime(System.currentTimeMillis()));
                    return;
                }
                RadioDeviceSyncManager.this.stopManSyncData();
                RadioDeviceSyncManager.this.sendEmptyMsgBack(251);
            }

            public void onGetDeviceTime(String paramAnonymousString) {
                CLog.d("RadioDeviceSyncManager", "onGetDeviceTime(): " + paramAnonymousString);
            }

            public void onGetOtherDatas(ArrayList<Integer> paramAnonymousArrayList) {
                CLog.d("RadioDeviceSyncManager", "onGetOtherDatas(): ");
            }

            public void onGetUserInfo(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6, int paramAnonymousInt7, int paramAnonymousInt8) {
                CLog.d("RadioDeviceSyncManager", "onGetUserInfo:height " + paramAnonymousInt1 + " weigh: " + paramAnonymousInt2 + " age: " + paramAnonymousInt3 + " gender" + paramAnonymousInt4 + " stepLength" + paramAnonymousInt5 + " runLength" + paramAnonymousInt6 + " sportType" + paramAnonymousInt7 + " goalValue" + paramAnonymousInt8);
            }

            public void onGetVersion(String paramAnonymousString) {
                CLog.i("RadioDeviceSyncManager", "onGetVersion:" + paramAnonymousString);
                RadioDeviceSyncManager.this.curDeviceVersion = paramAnonymousString;
                RadioDeviceSyncManager.this.sendVersionIdBack(paramAnonymousString);
                RadioDeviceSyncManager.this.saveBasicInfo();
                RadioDeviceSyncManager.this.stopSyncDevice();
            }

            public void onNullData() {
                RadioDeviceSyncManager.this.sendEmptyMsgBack(240);
                RadioDeviceSyncManager.this.stopManSyncData();
            }

            public void onSetFrindSwitchOver() {
            }

            public void onSetTargetStepOver() {
            }

            public void onSyncDataOver(HashMap<String, AccessoryValues> paramAnonymousHashMap, ByteArrayOutputStream paramAnonymousByteArrayOutputStream) {
               /* CLog.d("RadioDeviceSyncManager", "Sync Data Over!");
                if (paramAnonymousHashMap == null)
                    CLog.d("RadioDeviceSyncManager", "data is null");
                while (true) {
                    RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostGetUserInfo2());
                    return;
                    RadioDeviceSyncManager.this.mAccessoryManager.saveToDB(paramAnonymousHashMap);
                    RadioDeviceSyncManager.this.sendTotalDataback(paramAnonymousHashMap);
                    paramAnonymousHashMap = AccessoryDataParseUtil.getCurrentData(paramAnonymousHashMap);
                    RadioDeviceSyncManager.this.accessoriesTotals = RadioDeviceSyncManager.this.getTotalSpotsDatas(paramAnonymousHashMap);
                    int i = 1;
                    if (RadioDeviceSyncManager.this.mAccessoryManager.isDataAvailable(paramAnonymousHashMap)) {
                        i = 0;
                        Message localMessage = RadioDeviceSyncManager.this.connectHandler.obtainMessage();
                        localMessage.what = 9;
                        localMessage.obj = paramAnonymousByteArrayOutputStream.toByteArray();
                        RadioDeviceSyncManager.this.connectHandler.sendMessage(localMessage);
                    }
                    RadioDeviceSyncManager.this.sendMsgBack(5, i, 0, paramAnonymousHashMap);
                }*/
            }

            public void onSyncDataProgress(int paramAnonymousInt) {
                RadioDeviceSyncManager.this.sendMsgBack(1, paramAnonymousInt, 0, null);
            }

            public void onTimeOut() {
                RadioDeviceSyncManager.this.stopSyncDevice();
                CLog.i("RadioDeviceSyncManager", "man_connect time out");
                if (RadioDeviceSyncManager.this.lastSeartchMethod == 0) {
                    Message localMessage = RadioDeviceSyncManager.this.connectHandler.obtainMessage();
                    localMessage.what = 240;
                    localMessage.arg1 = RadioDeviceSyncManager.this.action;
                    RadioDeviceSyncManager.this.connectHandler.sendMessageDelayed(localMessage, 3000L);
                    return;
                }
                RadioDeviceSyncManager.this.sendEmptyMsgBack(255);
            }

            public void onUpdateAlarmReminderSuccessed() {
                RadioDeviceSyncManager.this.stopSyncDevice();
                RadioDeviceSyncManager.this.sendEmptyMsgBack(11);
            }

            public void onUpdateHeartWarningSuccess() {
            }

            public void onUpdateTimeSuccessed() {
               /* switch (RadioDeviceSyncManager.this.action) {
                    default:
                        return;
                    case 1:
                        RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostDeviceTypeVersion());
                        return;
                    case 4:
                        RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserInfoOrder());
                        return;
                    case 3:
                        RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserColockInfoOrder());
                        return;
                    case 5:
                        RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserAlarmOrder());
                        return;
                    case 2:
                }
                RadioDeviceSyncManager.this.connectHandler.removeCallbacks(RadioDeviceSyncManager.this.timeConnect);
                RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(SendData.getPostSyncDataByFrame());*/
            }

            public void onUpdateUserinfoSuccessed() {
               /* CLog.d("RadioDeviceSyncManager", "onUpdateUserinfoSuccessed");
                if (RadioDeviceSyncManager.this.action == 4) {
                    RadioDeviceSyncManager.this.sendEmptyMsgBack(10);
                    RadioDeviceSyncManager.this.stopSyncDevice();
                }
                while (RadioDeviceSyncManager.this.action != 2)
                    return;
                RadioDeviceSyncManager.this.mManSyncManager.SendDataToDevice(RadioDeviceSyncManager.this.setUserColockInfoOrder());*/
            }
        });
        this.mManSyncManager.setSaveType(SaveManager.eSaveType.DATABSE);
        this.mManSyncManager.setTryConnectCounts(3);
    }

    private void startFSKConnect() {
        this.curSeartchMethod = 1;
        CLog.i("RadioDeviceSyncManager", "FSK start");
        this.mFSKSyncManager.start();
        this.mFSKSyncManager.connectDevice();
    }

    private void startManConect() {
        CLog.d("RadioDeviceSyncManager", "startSyncData");
        this.curSeartchMethod = 0;
        try {
            this.mManSyncManager.start();
            CLog.d("RadioDeviceSyncManager", "connectDevice");
            this.mManSyncManager.connectDevice();
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void stopFSKConenct() {
        if (this.mFSKSyncManager != null) ;
        try {
            this.mFSKSyncManager.stop();
            return;
        } catch (Exception localException) {
        }
    }

    private void stopManSyncData() {
        if (this.mManSyncManager != null) ;
        try {
            this.mManSyncManager.stop();
            return;
        } catch (Exception localException) {
        }
    }

    public void init() {
        this.connectHandler = new Handler() {
            public void handleMessage(Message paramAnonymousMessage) {
                if (paramAnonymousMessage.what == 240) {
                    RadioDeviceSyncManager.this.lastSeartchMethod = RadioDeviceSyncManager.this.curSeartchMethod;
                    if (RadioDeviceSyncManager.this.curSeartchMethod == 0)
                        RadioDeviceSyncManager.this.startFSKConnect();
                }
                while (true) {

                    RadioDeviceSyncManager.this.startManConect();
                    if (paramAnonymousMessage.what == 9) {
                        CLog.i("RadioDeviceSyncManager", "receive msg to  upload");
                        byte[] arrayOfByte = (byte[]) paramAnonymousMessage.obj;
                        RadioDeviceSyncManager.this.notifyHistroyChange();
//                            RadioDeviceSyncManager.this.sendDataToService(paramAnonymousMessage);
                        return;
                    } else if (paramAnonymousMessage.what == 28) {
                        HashMap localHashMap = (HashMap) paramAnonymousMessage.obj;
                        if ((localHashMap != null) && (localHashMap.size() > 0)) ;
                        return;
                    }
                }
            }

        };
        this.lastSeartchMethod = this.mContext.getSharedPreferences("MyPrefsFile", 0).getInt("LastSeartchMethod", -1);
        if (this.lastSeartchMethod == -1) ;
        for (int i = 1; ; i = this.lastSeartchMethod) {
            this.curSeartchMethod = i;
            if (this.targetDeviceName.equals("CANDY"))
                this.curSeartchMethod = 0;
            initManchesterManager();
            initFSKManager();
            return;
        }
    }

    public boolean isRightDeviceByID(String paramString) {
        String str = this.mContext.getSharedPreferences("MyPrefsFile", 0).getString("BindTypeId", null);
        if (TextUtils.isEmpty(str)) ;
        for (boolean bool = true; ; bool = str.equals(paramString))
            return bool & this.mAccessoryManager.isRightDeviceByID(this.targetDeviceName, paramString);
    }

    public void onBlutoothStateChanged(boolean paramBoolean) {
    }

    public void seartchBle() {
    }

    public void setUpGradeFilePath(String paramString) {
    }

    public void start() {
        if (isStart) {
            CLog.d("RadioDeviceSyncManager", "sync has start");
            return;
        }
        isStart = true;
        this.mDeviceID = "";
        this.connectHandler.removeCallbacks(this.timeConnect);
        this.connectHandler.postDelayed(this.timeConnect, 70000L);
        AudioManager localAudioManager = (AudioManager) this.mContext.getSystemService(Context.AUDIO_SERVICE);
        localAudioManager.setStreamVolume(3, localAudioManager.getStreamMaxVolume(3) - 1, 0);
        this.lastSeartchMethod = this.curSeartchMethod;
        if (this.curSeartchMethod == 0) {
            CLog.i("RadioDeviceSyncManager", "curSeartchMethod is SEARTCH_MAN");
            startManConect();
        }
        while (this.curSeartchMethod != 1)
            return;
        CLog.i("RadioDeviceSyncManager", "curSeartchMethod is SEARTCH_FSK");
        startFSKConnect();
    }

    public void startSeartch() {
    }

    public void stopSeatrch() {
    }

    public void stopSyncDevice() {
        if (this.timeConnect != null)
            this.connectHandler.removeCallbacks(this.timeConnect);
        this.connectHandler.removeMessages(2);
        this.connectHandler.removeMessages(240);
        isStart = false;
        stopManSyncData();
        stopFSKConenct();
    }
}
