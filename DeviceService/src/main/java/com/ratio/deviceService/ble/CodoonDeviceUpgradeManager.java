package com.ratio.deviceService.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ratio.deviceService.communication.IConnectCallback;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.SendData;
import com.ratio.deviceService.data.TimeoutCheck;
import com.ratio.deviceService.datamanager.DeviceUpgradeCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class CodoonDeviceUpgradeManager implements Runnable {
    private static int EACH_PART_NUM = 0;
    private static final int SEND_CONTENT = 3;
    protected static final String TAG = "CodoonDeviceUpgradeManager";
    private final int CONNECT_AGAIN = 2;
    private final int ORDER_CONNECT = 1;
    private final int TIME_OUT = 12000;
    byte[] buffer = new byte[EACH_PART_NUM];
    private int checkData = 0;
    IConnectCallback connectBack;
    private String filePath;
    private int frame;
    private boolean hasFound;
    private FileInputStream input;
    private boolean isBootMode;
    private boolean isHuawei = Build.BRAND.toLowerCase().contains("huawei");
    private boolean isSendingData;
    private boolean isStart;
    private boolean isVerify;
    private BleManager mBleManager;
    private Context mContext;
    private BluetoothDevice mDevice;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            super.handleMessage(paramAnonymousMessage);
            if (paramAnonymousMessage.what == 1) {
                Log.i("CodoonDeviceUpgradeManager", "send order to device getPostConnection");
                if (CodoonDeviceUpgradeManager.this.isBootMode)
                    CodoonDeviceUpgradeManager.this.SendDataToDevice(SendData.postConnectBootOrder());
            }
            do {
                CodoonDeviceUpgradeManager.this.SendDataToDevice(SendData.postBootMode());
                if (paramAnonymousMessage.what == 2) {
                    Log.i("CodoonDeviceUpgradeManager", "connect  device  again");
                    CodoonDeviceUpgradeManager.this.mBleManager.connect(CodoonDeviceUpgradeManager.this.mDevice, true);
                    return;
                }
            }
            while (paramAnonymousMessage.what != 3);
            CodoonDeviceUpgradeManager.this.isSendingData = true;
        }
    };
    private int[] mLastSendData;
    private TimeoutCheck.ITimeoutCallback mTimeoutCallback = new TimeoutCheck.ITimeoutCallback() {
        public void onConnectFailed(int paramAnonymousInt) {
            Log.d("CodoonDeviceUpgradeManager", "ConnectFailed() tryConnectIndex:" + paramAnonymousInt);
            CodoonDeviceUpgradeManager.this.upgradeCallback.onTimeOut();
        }

        public void onReConnect(int paramAnonymousInt) {
            Log.d("CodoonDeviceUpgradeManager", "reConnect() tryConnectIndex:" + paramAnonymousInt);
            CodoonDeviceUpgradeManager.this.reConnectBle();
        }

        public void onReSend() {
            Log.d("CodoonDeviceUpgradeManager", "reSend()");
            if (CodoonDeviceUpgradeManager.this.hasFound)
                CodoonDeviceUpgradeManager.this.reSendDataToDevice(CodoonDeviceUpgradeManager.this.mLastSendData);
        }

        public void onReceivedFailed() {
            Log.d("CodoonDeviceUpgradeManager", "receivedFailed()");
            if (CodoonDeviceUpgradeManager.this.isVerify) {
                CodoonDeviceUpgradeManager.this.isVerify = false;
                CodoonDeviceUpgradeManager.this.upgradeCallback.onCheckBootResult(true);
                return;
            }
            CodoonDeviceUpgradeManager.this.upgradeCallback.onTimeOut();
        }
    };
    private TimeoutCheck mTimeoutCheck;
    private String mWriteCharacteristicUUID = "00002a19-0000-1000-8000-00805f9b34fb";
    private String mWriteClicentUUID = "0000180f-0000-1000-8000-00805f9b34fb";
    private int totalFrame;
    private DeviceUpgradeCallback upgradeCallback;

    public CodoonDeviceUpgradeManager(Context paramContext, DeviceUpgradeCallback paramDeviceUpgradeCallback) {
        this.mContext = paramContext;
        this.upgradeCallback = paramDeviceUpgradeCallback;
        this.mTimeoutCheck = new TimeoutCheck(this.mTimeoutCallback);
        this.mTimeoutCheck.setTryConnectCounts(4);
        this.mTimeoutCheck.setTimeout(12000);
        this.mBleManager = new BleManager(this.mContext);
        registerBLE();
    }

    private boolean checkBackIsRight(ArrayList<Integer> paramArrayList) {
        return true;
    }

    private byte[] intToByte(int[] paramArrayOfInt) {
        int j = paramArrayOfInt.length;
        byte[] arrayOfByte = new byte[j];
        int i = 0;
        while (true) {
            if (i >= j)
                return arrayOfByte;
            arrayOfByte[i] = (byte) paramArrayOfInt[i];
            i += 1;
        }
    }

    private boolean parseIsUpSuccess(ArrayList<Integer> paramArrayList) {
        if ((paramArrayList == null) || (paramArrayList.size() < 5))
            return false;
        return ((Integer) paramArrayList.get(3)).intValue() == 0;
    }

    private void reConnectBle() {
        this.mTimeoutCheck.setTryConnectCounts(1);
        this.mTimeoutCheck.setTimeout(12000);
        this.mTimeoutCheck.startCheckTimeout();
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        if (this.mBleManager != null) {
            this.mBleManager.disconnect();
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessageDelayed(2, 1500L);
        }
    }

    private void reSendDataToDevice(int[] paramArrayOfInt) {
        if (this.mBleManager != null) {
            this.mLastSendData = paramArrayOfInt;
            this.mBleManager.writeIasAlertLevel(this.mWriteClicentUUID, this.mWriteCharacteristicUUID, intToByte(paramArrayOfInt));
        }
    }

    public void SendDataToDevice(int[] paramArrayOfInt) {
        if (this.mBleManager != null) {
            this.mTimeoutCheck.setIsConnection(false);
            this.mTimeoutCheck.setTimeout(12000);
            this.mTimeoutCheck.setTryConnectCounts(2);
            this.mTimeoutCheck.startCheckTimeout();
            this.mLastSendData = paramArrayOfInt;
            this.mBleManager.writeIasAlertLevel(this.mWriteClicentUUID, this.mWriteCharacteristicUUID, intToByte(paramArrayOfInt));
        }
    }

    protected void analysis(ArrayList<Integer> paramArrayList) {
        if (paramArrayList != null) ;
        switch (((Integer) paramArrayList.get(1)).intValue()) {
            default:
                Log.d("CodoonDeviceUpgradeManager", "on get other datas");
                if (this.isVerify) {
                    this.upgradeCallback.onCheckBootResult(true);
                    this.isVerify = false;
                }
                break;
            case 240:
            case 242:
            case 241:
            case 243:
                do {
                    this.upgradeCallback.onChangeToBootMode();
                    this.isBootMode = true;
                    this.mBleManager.disconnect();
                    this.mTimeoutCheck.setIsConnection(true);
                    this.mTimeoutCheck.setTryConnectCounts(3);
                    this.mTimeoutCheck.setTimeout(12000);
                    this.mTimeoutCheck.startCheckTimeout();
                    this.mHandler.removeMessages(2);
                    this.mHandler.sendEmptyMessageDelayed(2, 1200L);
                    if ((paramArrayList == null) || (paramArrayList.size() < 6)) {
                        reSendDataToDevice(this.mLastSendData);
                        return;
                    }
                    this.mTimeoutCheck.stopCheckTimeout();
                    this.upgradeCallback.onGetBootVersion(paramArrayList.get(4) + "." + paramArrayList.get(5));
                    CLog.d("CodoonDeviceUpgradeManager", "onGetBootVersion" + paramArrayList.get(4) + "." + paramArrayList.get(5));
                    this.frame = 0;
                    this.isSendingData = true;
                    calcToatals();
                    sendData();
                    this.upgradeCallback.onConnectBootSuccess();
                    CLog.d("CodoonDeviceUpgradeManager", "begint to get boot version");
                    SendDataToDevice(SendData.postConnectBootVersion());
                    this.isBootMode = true;
                    this.upgradeCallback.onWriteFrame(this.frame, this.totalFrame);
                    ((Integer) paramArrayList.get(3)).intValue();
                    ((Integer) paramArrayList.get(4)).intValue();
                    if (!checkBackIsRight(paramArrayList))
                        break;
                    this.frame += 1;
                }
                while (sendData());
                try {
                    this.input.close();
                    this.input = null;
                    this.isVerify = true;
                    SendDataToDevice(SendData.postBootEnd(this.checkData & 0xFFFF));
                    this.mTimeoutCheck.setTimeout(6000);
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                Log.e("CodoonDeviceUpgradeManager", "frame: err:" + this.frame);
                reSendDataToDevice(this.mLastSendData);
                return;
            case 244:
                boolean bool = parseIsUpSuccess(paramArrayList);
                Log.d("CodoonDeviceUpgradeManager", "onCheck��" + bool);
                this.upgradeCallback.onCheckBootResult(bool);
                return;
        }
        reSendDataToDevice(this.mLastSendData);
    }

    public void calcToatals() {
        File localFile = new File(this.filePath);
        if (!localFile.exists()) {
            Log.e("CodoonDeviceUpgradeManager", "err not find file:" + this.filePath);
            return;
        }
        this.totalFrame = (int) (localFile.length() / EACH_PART_NUM);
        this.frame = 0;
        this.checkData = 0;
        Log.d("CodoonDeviceUpgradeManager", "totalFrame is:" + this.totalFrame);
        if (this.input != null)
        try {
            this.input.close();
            this.input = null;
        } catch (IOException localIOException) {
            try {
                this.input = new FileInputStream(localFile);
                localIOException.printStackTrace();
                return;

            } catch (FileNotFoundException localFileNotFoundException) {
                localFileNotFoundException.printStackTrace();
            }
        }
    }

    public void cancel() {
    }

    public String getUpgradeFilePath() {
        return this.filePath;
    }

    public boolean isConnect() {
        return this.mBleManager.isConnect;
    }

    public void registerBLE() {
        if (this.connectBack == null)
            this.connectBack = new IConnectCallback() {
                public void connectState(BluetoothDevice paramAnonymousBluetoothDevice, int paramAnonymousInt1, int paramAnonymousInt2) {
                    CodoonDeviceUpgradeManager.this.isSendingData = false;
                    if (paramAnonymousInt2 == 2) {
                        if ((!CodoonDeviceUpgradeManager.this.isBootMode) && (CodoonDeviceUpgradeManager.this.mDevice.getName() != null) && (CodoonDeviceUpgradeManager.this.mDevice.getName().equalsIgnoreCase("Cboot")))
                            CodoonDeviceUpgradeManager.this.isBootMode = true;
                        CodoonDeviceUpgradeManager.this.hasFound = true;
                        CodoonDeviceUpgradeManager.this.mHandler.removeMessages(2);
                    }
                    while (true) {
                        Log.i("CodoonDeviceUpgradeManager", "isconnected:" + CodoonDeviceUpgradeManager.this.hasFound);
                        if ((paramAnonymousInt2 == 0) && (CodoonDeviceUpgradeManager.this.isStart) && (paramAnonymousInt1 != 0)) {
                            CodoonDeviceUpgradeManager.this.hasFound = false;
                            Log.i("CodoonDeviceUpgradeManager", "disconnected been down so connect again");
                            CodoonDeviceUpgradeManager.this.reConnectBle();
                        }
                        return;

                    }
                }

                public void discovered() {
                    if (CodoonDeviceUpgradeManager.this.isStart) {
                        CodoonDeviceUpgradeManager.this.mHandler.removeMessages(1);
                        CodoonDeviceUpgradeManager.this.mHandler.sendEmptyMessageDelayed(1, 1000L);
                    }
                }

                public void getValue(int paramAnonymousInt) {
                }

                public void getValues(ArrayList<Integer> paramAnonymousArrayList) {
                    if (CodoonDeviceUpgradeManager.this.isStart)
                        CodoonDeviceUpgradeManager.this.analysis(paramAnonymousArrayList);
                }
            };
        this.mBleManager.register(this.connectBack);
    }

    public void run() {
    }

    public boolean sendData() {
        try {
            int j = this.input.read(this.buffer);
            if (-1 != j) {
                int i = 0;
                if (i >= j)
                    if (j < this.buffer.length)
                        i = j;
                while (true) {
                    if (i >= this.buffer.length) {
                        SendDataToDevice(SendData.postBootUploadData(this.frame, this.buffer, this.buffer.length));

                        this.checkData += (this.buffer[i] & 0xFF);
                        i += 1;
                        return true;
                    }
                    this.buffer[i] = 0;
                    i += 1;
                }
            }
            return false;
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            Log.e("CodoonDeviceUpgradeManager", "exception��" + this.frame + " total:" + this.totalFrame);
        }
        return true;
    }

    public void setTryConnectCounts(int paramInt) {
        this.mTimeoutCheck.setTryConnectCounts(paramInt);
    }

    public void setUpgradeFilePath(String paramString) {
        this.filePath = paramString;
    }

    public void setWriteCharacteristicUUID(String paramString) {
        this.mWriteCharacteristicUUID = paramString;
    }

    public void setWriteClientUUID(String paramString) {
        this.mWriteClicentUUID = paramString;
    }

    public void start(BluetoothDevice paramBluetoothDevice) {
        this.mDevice = paramBluetoothDevice;
        this.isStart = true;
        this.isBootMode = false;
        this.mBleManager.disconnect();
        if (this.isHuawei)
            this.mBleManager.connect(paramBluetoothDevice, false);
        while (true) {
            this.mTimeoutCheck.startCheckTimeout();
            this.mTimeoutCheck.setIsConnection(true);
            this.mTimeoutCheck.setTryConnectCounts(2);
            this.mTimeoutCheck.setTimeout(12000);
            this.mBleManager.connect(paramBluetoothDevice, true);
            return;
        }
    }

    public void start(int[] paramArrayOfInt) {
        this.isStart = true;
        SendDataToDevice(paramArrayOfInt);
        this.mTimeoutCheck.setIsConnection(true);
    }

    public void stop() {
        this.isStart = false;
        this.isBootMode = false;
        this.isSendingData = false;
        this.mHandler.removeMessages(1);
        this.mHandler.removeMessages(2);
        if (this.mTimeoutCheck != null)
            this.mTimeoutCheck.stopCheckTimeout();
        if (this.mBleManager != null)
            this.mBleManager.close();
        if (this.input != null) ;
        try {
            this.input.close();
            return;
        } catch (Exception localException) {
        }
    }
}
