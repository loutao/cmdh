package com.ratio.deviceService.communication.mancherster;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import com.ratio.deviceService.ble.BaseCommunicationManager;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.ISyncDataTask;
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

import static com.ratio.deviceService.data.SaveManager.eSaveType;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class FSKDeviceDataManager extends BaseCommunicationManager
        implements ISyncDataTask {
    private String TAG = "SyncDeviceDataManager";
    private int errCodeTimes = 0;
    private ByteArrayOutputStream mBaos;
    private ISyncDataCallback mCallback;
    private ReceiveManager mReceiveManager;
    private eSaveType mSaveType = eSaveType.XML;
    private SendDataManager mSendDataManager;
    private int mTimeout = 2000;
    private TimeoutCheck.ITimeoutCallback mTimeoutCallback = new TimeoutCheck.ITimeoutCallback() {
        public void onConnectFailed(int paramAnonymousInt) {
            Log.d(FSKDeviceDataManager.this.TAG, "ConnectFailed() tryConnectIndex:" + paramAnonymousInt);
            FSKDeviceDataManager.this.mCallback.onTimeOut();
        }

        public void onReConnect(int paramAnonymousInt) {
            Log.d(FSKDeviceDataManager.this.TAG, "reConnect() tryConnectIndex:" + paramAnonymousInt);
            if (paramAnonymousInt == 5) {
                Log.i(FSKDeviceDataManager.this.TAG, "reinit choose other channel");
                FSKDeviceDataManager.this.mSendDataManager.reInitAudio();
            }
            FSKDeviceDataManager.this.reSendDataToDevice(FSKDeviceDataManager.this.mLastSendData);
        }

        public void onReSend() {
            Log.d(FSKDeviceDataManager.this.TAG, "reSend()");
            FSKDeviceDataManager.this.reSendDataToDevice(FSKDeviceDataManager.this.mLastSendData);
        }

        public void onReceivedFailed() {
            Log.d(FSKDeviceDataManager.this.TAG, "receivedFailed()");
            FSKDeviceDataManager.this.mCallback.onTimeOut();
        }
    };

    public FSKDeviceDataManager(Context paramContext, ISyncDataCallback paramISyncDataCallback) {
        this.mContext = paramContext;
        this.mCallback = paramISyncDataCallback;
        this.mTimeoutCheck = new TimeoutCheck(this.mTimeoutCallback);
        this.mTimeoutCheck.setTryConnectCounts(3);
        this.mTimeoutCheck.setTimeout(this.mTimeout);
    }

    private String countTime(ArrayList<Integer> paramArrayList) {
        int i = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(3)).intValue()));
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

    public void SendDataToDevice(int[] paramArrayOfInt) {
        if (this.mSendDataManager != null) {
            this.mLastSendData = paramArrayOfInt;
            this.mSendDataManager.write(paramArrayOfInt);
            this.mTimeoutCheck.startCheckTimeout();
        }
    }

    protected void analysis(ArrayList<Integer> paramArrayList) {
        if (paramArrayList == null) {
            this.mCallback.onNullData();
            return;
        }
        int i = ((Integer) paramArrayList.get(1)).intValue();
        Log.d(this.TAG, "message ID:" + i);
        Object localObject;
        switch (i) {
            case 131:
            case 137:
            case 141:
            case 142:
            case 143:
            case 144:
            case 146:
            case 147:
            default:
                Log.e(this.TAG, "get err response: 0x" + Integer.toHexString(i));
                this.errCodeTimes += 1;
                if (this.errCodeTimes > 3) {
                    this.errCodeTimes = 0;
                    this.mCallback.onTimeOut();
                    return;
                }
                break;
            case 129:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                this.mTimeoutCheck.setTryConnectCounts(5);
                this.mTimeoutCheck.setIsConnection(false);
                this.mTimeoutCheck.setTimeout(this.mTimeout);
                this.mCallback.onConnectSuccessed();
                return;
            case 130:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                StringBuilder localStringBuilder4 = new StringBuilder();
                String str6 = paramArrayList.get(4) + "." + paramArrayList.get(5);
                this.mCallback.onGetVersion(str6);
                break;
            case 132:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                String str5 = getDeviceID(paramArrayList);
                this.mCallback.onGetDeviceID(str5);
                break;
            case 138:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onUpdateTimeSuccessed();
                return;
            case 139:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                String str4 = countTime(paramArrayList);
                this.mCallback.onGetDeviceTime(str4);
                break;
            case 148:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onClearDataSuccessed();
                return;
            case 140:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                this.frameCount = ((((Integer) paramArrayList.get(4)).intValue() * 256 + ((Integer) paramArrayList.get(5)).intValue() + 3) / 4);
                this.indexFrame = 0;
                this.mBaos = new ByteArrayOutputStream();
                Log.d(this.TAG, "framecount:" + this.frameCount);
                i = this.indexFrame;
                this.indexFrame = (i + 1);
                SendDataToDevice(SendData.getPostReadSportData(i << 2));
                this.mRecordDatas = new ArrayList();
                return;
            case 145:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                int k = -1 + paramArrayList.size();
                ArrayList localArrayList1 = new ArrayList();
                for (int m = 3; ; m++) {
                    if (m >= k) {
                        this.mRecordDatas.add(localArrayList1);
                        if (this.indexFrame >= this.frameCount)
                            break;
                        this.mCallback.onSyncDataProgress(100 * this.indexFrame / this.frameCount);
                        int n = this.indexFrame;
                        this.indexFrame = (n + 1);
                        SendDataToDevice(SendData.getPostReadSportData(n << 2));
                        break;
                    }
                    this.mBaos.write(encryptMyxor(((Integer) paramArrayList.get(m)).intValue(), this.mBaos.size() % 6));
                    localArrayList1.add((Integer) paramArrayList.get(m));
                }
                this.frameCount = 0;
                this.indexFrame = 0;
                this.mCallback.onSyncDataProgress(100);
               /* AccessoryDataParseUtil localAccessoryDataParseUtil = new AccessoryDataParseUtil(this.mContext);
                HashMap localHashMap = localAccessoryDataParseUtil.analysisDatas(this.mRecordDatas);
                this.mCallback.onSyncDataOver(localHashMap, this.mBaos);*/
            case 135:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                getUserInfo(paramArrayList);
                return;
            case 133:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                this.mCallback.onUpdateUserinfoSuccessed();
                return;
            case 134:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                Log.d(this.TAG, "update alarm and activity remind success.");
                this.mCallback.onUpdateAlarmReminderSuccessed();
                String str2 = "";
                Iterator localIterator = paramArrayList.iterator();
                while (true) {
                    if (!localIterator.hasNext()) {
                        Log.d(this.TAG, str2);
                        break;
                    }
                    int j = ((Integer) localIterator.next()).intValue();
                    StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str2));
                    str2 = ",0x" + Integer.toHexString(j);
                }
            case 136:
                this.errCodeTimes = 0;
                this.mTimeoutCheck.stopCheckTimeout();
                if (paramArrayList.size() > 15) {
                    this.mCallback.onBattery(((Integer) paramArrayList.get(15)).intValue());
                    return;
                }
                this.mCallback.onBattery(((Integer) paramArrayList.get(10)).intValue());
                return;
        }
        this.mTimeoutCheck.stopCheckTimeout();
        SendDataToDevice(this.mLastSendData);
    }

    public void cancel() {
    }

    public void connectDevice() {
        this.mLastSendData = SendData.getPostConnection();
        this.mTimeoutCheck.startCheckTimeout();
        this.mTimeoutCheck.setTryConnectCounts(1);
        this.mTimeoutCheck.setIsConnection(true);
        this.mTimeoutCheck.setTimeout(1000);
        reSendDataToDevice(this.mLastSendData);
    }


    public boolean isConnect() {
        return false;
    }

    public void redoLastAction() {
    }

    public void register(ISyncDataCallback paramISyncDataCallback) {
    }

    public void setSaveType(eSaveType parameSaveType) {
        this.mSaveType = parameSaveType;
    }

    public void setTryConnectCounts(int paramInt) {
        this.mTimeoutCheck.setTryConnectCounts(paramInt);
    }

    public void start() {
        this.mSendDataManager = new SendDataManager();
        this.mReceiveManager = new ReceiveManager(new ManDeviceDataManager(mContext, mCallback));
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
