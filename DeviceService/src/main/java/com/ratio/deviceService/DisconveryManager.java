package com.ratio.deviceService;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.bean.CodoonBluethoothDevice;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.TimeoutCheck;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class DisconveryManager {
    private static final int SEARTCH_BY_CLASSIC = 4369;
    private static final String TAG = "DisconveryManager";
    private boolean isScanBLEStart;
    private boolean isScanClassic;
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;
    private BluetoothAdapter.LeScanCallback mLeScanCallback;
    private OnSeartchCallback mOnSeartchCallback;
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            String stringAction = paramAnonymousIntent.getAction();
            if ("android.bluetooth.device.action.FOUND".equals(paramAnonymousContext)) {
                BluetoothDevice localBluetoothDevice = (BluetoothDevice) paramAnonymousIntent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                if (DisconveryManager.this.mOnSeartchCallback != null) {
                    CodoonBluethoothDevice localCodoonBluethoothDevice = new CodoonBluethoothDevice();
                    localCodoonBluethoothDevice.device = localBluetoothDevice;
                    DisconveryManager.this.mOnSeartchCallback.onSeartch(localCodoonBluethoothDevice, null);
                }
            }
            while (!"android.bluetooth.adapter.action.DISCOVERY_FINISHED".equals(paramAnonymousContext))
                return;
            DisconveryManager.this.timeOutAction();
        }
    };
    private Handler mSeartchChangeHander = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.what == SEARTCH_BY_CLASSIC)
                DisconveryManager.this.startClassicSeartch();
        }
    };
    private TimeoutCheck mTimeoutCheck;
    private int time_out = 15000;

    public DisconveryManager(Context paramContext, OnSeartchCallback paramOnSeartchCallback) {
        this.mContext = paramContext;
        this.mOnSeartchCallback = paramOnSeartchCallback;
        this.mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            public void onLeScan(BluetoothDevice paramAnonymousBluetoothDevice, int paramAnonymousInt, byte[] paramAnonymousArrayOfByte) {
                boolean bool = false;
                if (paramAnonymousBluetoothDevice == null)
                    return;
                String localObject1 = paramAnonymousBluetoothDevice.getName();
                if (localObject1 == null) {
                    localObject1 = DisconveryManager.this.parseDeviceName(paramAnonymousBluetoothDevice, paramAnonymousArrayOfByte);
                    if (localObject1 != null) {
                        AccessoryConfig.setStringValue(DisconveryManager.this.mContext, paramAnonymousBluetoothDevice.getAddress(), localObject1);

                    }
                }
                if ((localObject1 == null) || (localObject1.equals("unknown")))
                    return;
                CLog.i("DisconveryManager", "find device:" + localObject1);
                if (DisconveryManager.this.mOnSeartchCallback != null) {
                    CodoonBluethoothDevice localObject2 = new CodoonBluethoothDevice();
                    localObject2.device_name = localObject1;
                    localObject2.device = paramAnonymousBluetoothDevice;
                    bool = DisconveryManager.this.mOnSeartchCallback.onSeartch(localObject2, paramAnonymousArrayOfByte);
                }
                if ((!bool) && (localObject1 == null)) {
                    DisconveryManager.this.isScanBLEStart = false;
                    DisconveryManager.this.mBluetoothAdapter.stopLeScan(DisconveryManager.this.mLeScanCallback);
                    DisconveryManager.this.seartchByClassic();
                }
            }
        };
        this.mBluetoothAdapter = ((BluetoothManager) this.mContext.getSystemService(Context.BLUETOOTH_SERVICE)).getAdapter();
        this.mTimeoutCheck = new TimeoutCheck(new TimeoutCheck.ITimeoutCallback() {
            public void onConnectFailed(int paramAnonymousInt) {
                DisconveryManager.this.timeOutAction();
            }

            public void onReConnect(int paramAnonymousInt) {
                DisconveryManager.this.timeOutAction();
            }

            public void onReSend() {
                DisconveryManager.this.timeOutAction();
            }

            public void onReceivedFailed() {
                DisconveryManager.this.timeOutAction();
            }
        });
        this.mTimeoutCheck.setTryConnectCounts(1);
        this.mTimeoutCheck.setIsConnection(false);
        this.mTimeoutCheck.setTimeout(this.time_out);
    }

    public static String parseDeviceName(BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte) {
        if ((paramArrayOfByte == null) || (paramArrayOfByte.length < 4))
            return null;
        int j = paramArrayOfByte[3] & 0xFF;
        int i = 3 + 1;
        String localString = null;
        int k = paramArrayOfByte[i] & 0xFF;
        while (true) {
            if (k == 9)
                break;
//                paramBluetoothDevice = localObject;
            try {
                String str = new String(paramArrayOfByte, i + 1, j - 1);
//                paramBluetoothDevice = paramArrayOfByte;
                Log.i("DisconveryManager1", "parse name:" + str);
//                paramBluetoothDevice = paramArrayOfByte;
                localString = str;
                i += j;
//                paramBluetoothDevice = localObject;
                j = paramArrayOfByte.length;
                if (i >= j)
                    return "unknown";
                j = paramArrayOfByte[i] & 0xFF;
                i += 1;
//                paramBluetoothDevice = localObject;
                k = paramArrayOfByte.length;
                if (i >= k)
                    return "unknown";
                k = paramArrayOfByte[i] & 0xFF;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return localString;
    }

    private void seartchByClassic() {
        this.mSeartchChangeHander.removeMessages(SEARTCH_BY_CLASSIC);
        this.mSeartchChangeHander.sendEmptyMessageDelayed(SEARTCH_BY_CLASSIC, 500L);
    }

    private void startClassicSeartch() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.bluetooth.device.action.FOUND");
        localIntentFilter.addAction("android.bluetooth.adapter.action.DISCOVERY_FINISHED");
        this.mContext.registerReceiver(this.mReceiver, localIntentFilter);
        this.isScanClassic = this.mBluetoothAdapter.startDiscovery();
        if (!this.isScanClassic)
            timeOutAction();
    }

    public int getTime_out() {
        return this.time_out;
    }

    public void setTime_out(int paramInt) {
        this.time_out = paramInt;
    }

    public boolean startSearch() {
        if ((this.mBluetoothAdapter.isEnabled()) && (!this.isScanBLEStart)) {
            this.mTimeoutCheck.startCheckTimeout();
            this.isScanBLEStart = this.mBluetoothAdapter.startLeScan(this.mLeScanCallback);
        }
        return this.isScanBLEStart;
    }

    public void stopSearch() {
        this.mTimeoutCheck.stopCheckTimeout();
        try {
            this.mContext.unregisterReceiver(this.mReceiver);
        } catch (Exception localException2) {
            try {
                if (this.isScanBLEStart) {
                    this.isScanBLEStart = false;
                    this.mBluetoothAdapter.stopLeScan(this.mLeScanCallback);
                }
            } catch (Exception ex) {
                try {
                    while (true) {
                        if (this.isScanClassic) {
                            this.isScanClassic = false;
                            this.mBluetoothAdapter.cancelDiscovery();
                            continue;
                        }
                        this.mSeartchChangeHander.removeMessages(SEARTCH_BY_CLASSIC);
                        ex.printStackTrace();
                        return;
                    }
                } catch (Exception localException3) {
                    this.mSeartchChangeHander.removeMessages(SEARTCH_BY_CLASSIC);
                }
            }
        }
    }

    public void timeOutAction() {
        boolean bool = false;
        if (this.mOnSeartchCallback != null)
            bool = this.mOnSeartchCallback.onSeartchTimeOut();
        if (!bool)
            stopSearch();
    }

    public static abstract interface OnSeartchCallback {
        public abstract boolean onSeartch(CodoonBluethoothDevice paramCodoonBluethoothDevice, byte[] paramArrayOfByte);

        public abstract boolean onSeartchTimeOut();
    }
}
