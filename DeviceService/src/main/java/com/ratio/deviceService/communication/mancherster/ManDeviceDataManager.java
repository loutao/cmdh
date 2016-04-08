package com.ratio.deviceService.communication.mancherster;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ratio.deviceService.ble.BaseCommunicationManager;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.ISyncDataTask;
import com.ratio.deviceService.data.SaveManager;
import com.ratio.deviceService.data.SendData;
import com.ratio.deviceService.data.TimeoutCheck;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class ManDeviceDataManager extends BaseCommunicationManager
        implements ISyncDataTask {
    private final String KEY_ENCODE_DECODE = "com.communication.mancherster.encodedecodestate";
    private final String PREFERENCE_NAME = "com.communication.mancherster.preferences";
    private String TAG = "SyncDeviceDataManager";
    private ByteArrayOutputStream mBaos;
    private ISyncDataCallback mCallback;
    private ReceiveManager mReceiveManager;
    private SaveManager.eSaveType mSaveType = SaveManager.eSaveType.XML;
    private SendDataManager mSendDataManager;
    private SharedPreferences mSharedPreferences;
    private final int mState = 0;
    private TimeoutCheck.ITimeoutCallback mTimeoutCallback = new TimeoutCheck.ITimeoutCallback() {
        public void onConnectFailed(int paramAnonymousInt) {
            Log.d(ManDeviceDataManager.this.TAG, "ConnectFailed() tryConnectIndex:" + paramAnonymousInt);
            ManDeviceDataManager.this.mCallback.onTimeOut();
        }

        public void onReConnect(int paramAnonymousInt) {
            Log.d(ManDeviceDataManager.this.TAG, "reConnect() tryConnectIndex:" + paramAnonymousInt);
            if (paramAnonymousInt == 13) {
                Log.i(ManDeviceDataManager.this.TAG, "reinit choose other channel");
                ManDeviceDataManager.this.mSendDataManager.reInitAudio();
            }
            ManDeviceDataManager.this.setEncodeDecodeState(paramAnonymousInt % 4);
            ManDeviceDataManager.this.connect();
        }

        public void onReSend() {
            Log.d(ManDeviceDataManager.this.TAG, "reSend()");
            ManDeviceDataManager.this.reSendDataToDevice(ManDeviceDataManager.this.mLastSendData);
        }

        public void onReceivedFailed() {
            Log.d(ManDeviceDataManager.this.TAG, "receivedFailed()");
            ManDeviceDataManager.this.mCallback.onTimeOut();
        }
    };
    private final byte[] xorKey = {84, -111, 40, 21, 87, 38};

    public ManDeviceDataManager(Context paramContext, ISyncDataCallback paramISyncDataCallback) {
        this.mContext = paramContext;
        this.mCallback = paramISyncDataCallback;
        this.mTimeoutCheck = new TimeoutCheck(this.mTimeoutCallback);
        this.mTimeoutCheck.setTryConnectCounts(1);
        this.mTimeoutCheck.setTimeout(800);
    }

    private void connect() {
        if ((this.mSendDataManager != null) && (this.mReceiveManager != null))
            switch (getEncodeDecodeState()) {
                default:
                    this.mSendDataManager.write(SendData.getPostConnection());
                    return;
                case 0:
                    this.mSendDataManager.resetEncoding(true);
                    this.mReceiveManager.resetRaiseBit(true);
                case 1:
                    this.mSendDataManager.resetEncoding(true);
                    this.mReceiveManager.resetRaiseBit(false);
                case 2:
                    this.mSendDataManager.resetEncoding(false);
                    this.mReceiveManager.resetRaiseBit(true);
                case 3:
                    this.mSendDataManager.resetEncoding(false);
                    this.mReceiveManager.resetRaiseBit(false);
            }
    }

    private String countTime(ArrayList<Integer> paramArrayList) {
        int i = 2000 + Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(3)).intValue()));
        int j = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(4)).intValue()));
        int k = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(5)).intValue()));
        int l = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(6)).intValue()));
        int i1 = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(7)).intValue()));
        int i2 = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(8)).intValue()));
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeZone(TimeZone.getDefault());
        localCalendar.set(i, j, k, l, i1, i2);
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
        localStringBuilder.append((((Integer) paramArrayList.get(4)).intValue() << 8) + ((Integer) paramArrayList.get(5)).intValue());
        localStringBuilder.append("-");
        localStringBuilder.append((((Integer) paramArrayList.get(6)).intValue() << 8) + ((Integer) paramArrayList.get(7)).intValue());
        localStringBuilder.append("-");
        localStringBuilder.append((((Integer) paramArrayList.get(8)).intValue() << 8) + ((Integer) paramArrayList.get(9)).intValue());
        localStringBuilder.append("-");
        localStringBuilder.append(paramArrayList.get(10));
        localStringBuilder.append("-");
        localStringBuilder.append((((Integer) paramArrayList.get(11)).intValue() << 8) + ((Integer) paramArrayList.get(12)).intValue());
        localStringBuilder.append("-");
        localStringBuilder.append((((Integer) paramArrayList.get(13)).intValue() << 8) + ((Integer) paramArrayList.get(14)).intValue());
        localStringBuilder.append("-");
        localStringBuilder.append(paramArrayList.get(15));
        return localStringBuilder.toString();
    }

    private int getEncodeDecodeState() {
        this.mSharedPreferences = this.mContext.getSharedPreferences("com.communication.mancherster.preferences", 0);
        return this.mSharedPreferences.getInt("com.communication.mancherster.encodedecodestate", 0);
    }

    private void getUserInfo(ArrayList<Integer> paramArrayList) {
        int i = ((Integer) paramArrayList.get(3)).intValue();
        int j = ((Integer) paramArrayList.get(4)).intValue();
        int k = ((Integer) paramArrayList.get(5)).intValue();
        int m = ((Integer) paramArrayList.get(6)).intValue();
        int n = ((Integer) paramArrayList.get(7)).intValue();
        int i1 = ((Integer) paramArrayList.get(8)).intValue();
        int i2 = ((Integer) paramArrayList.get(11)).intValue();
        int i3 = ((Integer) paramArrayList.get(12)).intValue();
        int i4 = ((Integer) paramArrayList.get(13)).intValue();
        this.mCallback.onGetUserInfo(i, j, k, m, n, i1, i2, (i3 << 8) + i4);
    }

    private void reSendDataToDevice(int[] paramArrayOfInt) {
        if (this.mSendDataManager != null) {
            this.mLastSendData = paramArrayOfInt;
            this.mSendDataManager.write(paramArrayOfInt);
        }
    }

    private void setEncodeDecodeState(int paramInt) {
        this.mSharedPreferences = this.mContext.getSharedPreferences("com.communication.mancherster.preferences", 0);
        SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
        localEditor.putInt("com.communication.mancherster.encodedecodestate", paramInt);
        localEditor.commit();
    }

    public void SendDataToDevice(int[] paramArrayOfInt) {
        if (this.mSendDataManager != null) {
            this.mTimeoutCheck.setIsConnection(false);
            this.mTimeoutCheck.setTimeout(800);
            this.mTimeoutCheck.startCheckTimeout();
            this.mLastSendData = paramArrayOfInt;
            this.mSendDataManager.write(paramArrayOfInt);
        }
    }

    protected void analysis(ArrayList<Integer> paramArrayList) {
        if (paramArrayList == null) {
            this.mCallback.onNullData();
            return;
        }
        int i;
        Object localObject;
        switch (((Integer) paramArrayList.get(1)).intValue()) {
            case 131:
            case 137:
            case 141:
            case 142:
            case 143:
            case 144:
            case 146:
            case 147:
            default:
                this.mCallback.onGetOtherDatas(paramArrayList);
                break;
            case 129:
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onConnectSuccessed();
                break;
            case 130:
                this.mTimeoutCheck.stopCheckTimeout();
                StringBuilder localStringBuilder3 = new StringBuilder();
                String str5 = paramArrayList.get(4) + "." + paramArrayList.get(5);
                this.mCallback.onGetVersion(str5);
                break;
            case 132:
                this.mTimeoutCheck.stopCheckTimeout();
                String str4 = getDeviceID(paramArrayList);
                this.mCallback.onGetDeviceID(str4);
                break;
            case 138:
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onUpdateTimeSuccessed();
                break;
            case 139:
                this.mTimeoutCheck.stopCheckTimeout();
                String str3 = countTime(paramArrayList);
                this.mCallback.onGetDeviceTime(str3);
                break;
            case 148:
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onClearDataSuccessed();
                break;
            case 140:
                this.mTimeoutCheck.stopCheckTimeout();
                this.frameCount = ((3 + 256 * ((Integer) paramArrayList.get(4)).intValue() + ((Integer) paramArrayList.get(5)).intValue()) / 4);
                this.indexFrame = 0;
                ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
                this.mBaos = localByteArrayOutputStream;
                String str2 = this.TAG;
                StringBuilder localStringBuilder2 = new StringBuilder("framecount:");
                Log.d(str2, this.frameCount + "");
                int i1 = this.indexFrame;
                this.indexFrame = (i1 + 1);
                SendDataToDevice(SendData.getPostReadSportData(i1 << 2));
                ArrayList localArrayList2 = new ArrayList();
                this.mRecordDatas = localArrayList2;
                break;
            case 145:
                this.mTimeoutCheck.stopCheckTimeout();
                int j = paramArrayList.size();
                ArrayList localArrayList1 = new ArrayList();
                i = 3;
                while (true)
                {
                    if (i >= j - 1)
                    {
                        this.mRecordDatas.add(localArrayList1);
                        if (this.indexFrame >= this.frameCount)
                            break;
                        this.mCallback.onSyncDataProgress(this.indexFrame * 100 / this.frameCount);
                        i = this.indexFrame;
                        this.indexFrame = (i + 1);
                        SendDataToDevice(SendData.getPostReadSportData(i << 2));
                        return;
                    }
                    this.mBaos.write(encryptMyxor(((Integer)paramArrayList.get(i)).intValue(), this.mBaos.size() % 6));
                    ((ArrayList)localArrayList1).add((Integer)paramArrayList.get(i));
                    i += 1;
                }
                this.frameCount = 0;
                this.indexFrame = 0;
                this.mCallback.onSyncDataProgress(100);
             /*   AccessoryDataParseUtil localAccessoryDataParseUtil = new AccessoryDataParseUtil(this.mContext);
                HashMap localHashMap = localAccessoryDataParseUtil.analysisDatas(this.mRecordDatas);
                this.mCallback.onSyncDataOver(localHashMap, this.mBaos);*/
                break;
            case 135:
                this.mTimeoutCheck.stopCheckTimeout();
                getUserInfo(paramArrayList);
                break;
            case 133:
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onUpdateUserinfoSuccessed();
                break;
            case 134:
                this.mTimeoutCheck.stopCheckTimeout();
                Log.d(this.TAG, "update alarm and activity remind success.");
                this.mCallback.onUpdateAlarmReminderSuccessed();
                String str1 = "";
                Iterator localIterator = paramArrayList.iterator();
                while (true) {
                    while (!(localIterator.hasNext()))
                        Log.d(this.TAG, str1);
                    i = ((Integer) localIterator.next()).intValue();
                    StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
                    str1 = ",0x" + Integer.toHexString(i);
                }
            case 136:
        }
        this.mTimeoutCheck.stopCheckTimeout();
        if (paramArrayList.size() > 15)
            this.mCallback.onBattery(((Integer) paramArrayList.get(15)).intValue());
        this.mCallback.onBattery(((Integer) paramArrayList.get(10)).intValue());
    }


    public void cancel() {
    }

    public void connectDevice() {
        this.mTimeoutCheck.startCheckTimeout();
        this.mTimeoutCheck.setIsConnection(true);
        this.mTimeoutCheck.setTimeout(500);
        connect();
    }

    public boolean isConnect() {
        return false;
    }

    public void register(ISyncDataCallback paramISyncDataCallback) {
    }

    public void setSaveType(SaveManager.eSaveType parameSaveType) {
        this.mSaveType = parameSaveType;
    }

    public void setTryConnectCounts(int paramInt) {
        this.mTimeoutCheck.setTryConnectCounts(paramInt);
    }

    public void start() {
        this.mSendDataManager = new SendDataManager();
        this.mReceiveManager = new ReceiveManager(this);
        this.mReceiveManager.start();
    }

    public void start(BluetoothDevice paramBluetoothDevice) {
    }

    public void start(int[] paramArrayOfInt) {
    }

    public void stop() {
        if (this.mTimeoutCheck != null)
            this.mTimeoutCheck.stopCheckTimeout();
        if (this.mSendDataManager != null)
            this.mSendDataManager.stopAudio();
        if (this.mReceiveManager != null)
            this.mReceiveManager.stop();
    }
}
