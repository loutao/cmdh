package com.ratio.deviceService.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.ratio.deviceService.communication.IConnectCallback;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.DataUtil;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class BleManager {
    public static final UUID CCC = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    protected static final int NOTIFY = 5;
    private static BleManager instance;
    private final String TAG = BleManager.class.toString();
    public boolean isConnect = false;
    private boolean isForceDisconnect = false;
    private boolean isHuawei = false;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private String mCharacteristicUUID = "00002a19-0000-1000-8000-00805f9b34fb";
    private String mClicentUUID = "0000180f-0000-1000-8000-00805f9b34fb";
    private IConnectCallback mConnectCallback;
    private Context mContext;
    private BluetoothGattDescriptor mDescriptor;
    private BluetoothDevice mDevice;
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        public void onCharacteristicChanged(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic) {
            Log.i(BleManager.this.TAG, "onCharacteristicChanged()");
            byte[] arrayOfByte = paramAnonymousBluetoothGattCharacteristic.getValue();
            int j = arrayOfByte.length;
            ArrayList localArrayList = new ArrayList();
            int i = 0;
            while (true) {
                if (i >= j) {
//                    DataUtil.DebugPrint(localArrayList);
                    if (BleManager.this.mConnectCallback == null) {
                        Log.i(BleManager.this.TAG, "no call back");
                        return;
                    }
                    BleManager.this.mConnectCallback.getValues(localArrayList);
                    return;
                }
                localArrayList.add(Integer.valueOf(0xFF & arrayOfByte[i]));
                i += 1;
            }

        }

        public void onCharacteristicRead(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt) {
            Log.i(BleManager.this.TAG, "onCharacteristicRead()");
            super.onCharacteristicRead(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattCharacteristic, paramAnonymousInt);
        }

        public void onCharacteristicWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattCharacteristic paramAnonymousBluetoothGattCharacteristic, int paramAnonymousInt) {
            String str = BleManager.this.TAG;
            StringBuilder localStringBuilder = new StringBuilder("onCharacteristicWrite() result ?  ");
            if (paramAnonymousInt == 0) ;
            for (boolean bool = true; ; bool = false) {
                CLog.i(str, bool + "");
                super.onCharacteristicWrite(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattCharacteristic, paramAnonymousInt);
                if ((paramAnonymousInt != 0) && (paramAnonymousBluetoothGatt != null))
                    paramAnonymousBluetoothGatt.writeCharacteristic(paramAnonymousBluetoothGattCharacteristic);
                if (BleManager.this.isHuawei)
                    BleManager.this.readCharacterByHand(BleManager.this.mClicentUUID, BleManager.this.mCharacteristicUUID);
                return;
            }
        }

        public void onConnectionStateChange(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt1, int paramAnonymousInt2) {
            try {
                Log.i("connect Status:", paramAnonymousInt1 + "-------" + paramAnonymousInt2);
                BleManager.this.mBluetoothGatt = paramAnonymousBluetoothGatt;
                if (paramAnonymousInt1 == 0) {
                    if ((paramAnonymousInt2 == 2) && (BleManager.this.mBluetoothGatt != null)) {
                        BleManager.this.isConnect = true;
                        Log.i(BleManager.this.TAG, "onConnectionStateChange:connected");
                        BleManager.this.mBluetoothGatt.discoverServices();
                        if (BleManager.this.mConnectCallback != null)
                            BleManager.this.mConnectCallback.connectState(BleManager.this.mDevice, paramAnonymousInt1, paramAnonymousInt2);
                    } else if ((paramAnonymousInt2 == 0) && (BleManager.this.mBluetoothGatt != null)) {
                        Log.i(BleManager.this.TAG, "onConnectionStateChange:disconnected");
                        if (BleManager.this.mConnectCallback != null)
                            BleManager.this.mConnectCallback.connectState(BleManager.this.mDevice, paramAnonymousInt1, paramAnonymousInt2);
                    }
                } else {
                    BleManager.this.isConnect = false;
                    if (BleManager.this.mConnectCallback != null) {
                        Log.i(BleManager.this.TAG, "onConnectionStateChange:disconnecct ");
                        BleManager.this.mConnectCallback.connectState(BleManager.this.mDevice, paramAnonymousInt1, paramAnonymousInt2);
                        return;
                    }
                }
            } catch (Exception ex) {
                CLog.e(BleManager.this.TAG, "err:" + ex.getMessage());
            }
        }

        public void onDescriptorRead(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattDescriptor paramAnonymousBluetoothGattDescriptor, int paramAnonymousInt) {
            String str = BleManager.this.TAG;
            StringBuilder localStringBuilder = new StringBuilder("onDescriptorRead() status? :");
            if (paramAnonymousInt == 0) ;
            for (boolean bool = true; ; bool = false) {
                CLog.i(str, bool + "");
                BleManager.this.mHandler.removeMessages(5);
                if (paramAnonymousInt != 0)
                    break;
                BleManager.this.mBluetoothGatt = paramAnonymousBluetoothGatt;
                paramAnonymousBluetoothGattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                paramAnonymousBluetoothGatt.writeDescriptor(paramAnonymousBluetoothGattDescriptor);
                return;
            }
            paramAnonymousBluetoothGatt.readDescriptor(paramAnonymousBluetoothGattDescriptor);
        }

        public void onDescriptorWrite(BluetoothGatt paramAnonymousBluetoothGatt, BluetoothGattDescriptor paramAnonymousBluetoothGattDescriptor, int paramAnonymousInt) {
            String str = BleManager.this.TAG;
            StringBuilder localStringBuilder = new StringBuilder("onDescriptorWrite() status ? ");
            boolean bool;
            if (paramAnonymousInt == 0) {
                bool = true;
                Log.i(str, bool + "");
                BleManager.this.mBluetoothGatt = paramAnonymousBluetoothGatt;
                if (paramAnonymousInt != 0)
                    BleManager.this.mHandler.sendEmptyMessage(5);
                if (BleManager.this.mConnectCallback != null)
                    BleManager.this.mConnectCallback.discovered();
            }
            while (true) {
                super.onDescriptorWrite(paramAnonymousBluetoothGatt, paramAnonymousBluetoothGattDescriptor, paramAnonymousInt);
                bool = false;
                break;
            }
            return;
        }

        public void onServicesDiscovered(BluetoothGatt paramAnonymousBluetoothGatt, int paramAnonymousInt) {
            Log.i(BleManager.this.TAG, "onServicesDiscovered()");
            if (paramAnonymousInt == 0) {
                BleManager.this.mBluetoothGatt = paramAnonymousBluetoothGatt;
                BleManager.this.mHandler.removeMessages(5);
                UUID localUUID1 = UUID.fromString(BleManager.this.mClicentUUID);
                UUID localUUID2 = UUID.fromString(BleManager.this.mCharacteristicUUID);
                BleManager.this.enableNotify(paramAnonymousBluetoothGatt, localUUID1, localUUID2, BleManager.CCC);
                return;
            }
            Log.e(BleManager.this.TAG, "err reson:" + paramAnonymousInt + " and try connect ble agin");
        }
    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message paramAnonymousMessage) {
            if (paramAnonymousMessage.what == 5) {
                if ((BleManager.this.mDescriptor != null) && (!BleManager.this.mDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE))) {
                    Log.i(BleManager.this.TAG, "Failed to set descriptor value");
                    BleManager.this.mHandler.sendEmptyMessageDelayed(5, 100L);
                    return;
                }
                if (BleManager.this.mBluetoothGatt != null) {
                    boolean bool = BleManager.this.mBluetoothGatt.writeDescriptor(BleManager.this.mDescriptor);
                    Log.i(BleManager.this.TAG, "notify suceess ? " + bool);
                    if (!bool)
                        BleManager.this.mHandler.sendEmptyMessageDelayed(5, 100L);
                    if (BleManager.this.mConnectCallback != null)
                        BleManager.this.mConnectCallback.discovered();
                }
            }
            return;

        }
    };
    private Runnable openBle = new Runnable() {
        public void run() {
            BluetoothAdapter.getDefaultAdapter().enable();
        }
    };
    private byte[] writeBuff;

    public BleManager(Context paramContext) {
        this.mContext = paramContext;
        this.isHuawei = Build.BRAND.toLowerCase().contains("huawei");
        initialize();
    }

    private void enableNotification(UUID paramUUID1, UUID paramUUID2) {
        CLog.i(this.TAG, "enableHRNotification ");
        BluetoothGattService localBluetoothGattService = this.mBluetoothGatt.getService(paramUUID1);
        if (paramUUID1 == null) {
            Log.e(this.TAG, "HRP service not found!");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 1, 0);
            return;
        }
        BluetoothGattCharacteristic localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(paramUUID2);
        if (localBluetoothGattCharacteristic == null) {
            Log.e(this.TAG, "HEART RATE MEASUREMENT charateristic not found!");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 1, 0);
            return;
        }
        BluetoothGattDescriptor localBluetoothGattDescriptor = localBluetoothGattCharacteristic.getDescriptor(CCC);
        if (localBluetoothGattCharacteristic == null) {
            Log.e(this.TAG, "CCC for HEART RATE MEASUREMENT charateristic not found!");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 1, 0);
            return;
        }
        if (!this.mBluetoothGatt.readDescriptor(localBluetoothGattDescriptor)) {
            Log.e(this.TAG, "readDescriptor() is failed");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 1, 0);
            return;
        }
    }

    private boolean enableNotify(BluetoothGatt paramBluetoothGatt, UUID paramUUID1, UUID paramUUID2, UUID paramUUID3) {
        if (paramBluetoothGatt == null)
            return false;
        BluetoothGattCharacteristic localBluetoothGattCharacteristic;
        Log.i(this.TAG, "enableNotification ");
        BluetoothGattService localBluetoothGattService = this.mBluetoothGatt.getService(paramUUID1);
        if (localBluetoothGattService == null) {
            Log.e(this.TAG, " service not found!");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 257, 0);
            return false;
        }
        Log.i(this.TAG, "find service");
        localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(paramUUID2);
        if (paramUUID1 == null) {
            Log.e(this.TAG, " charateristic not found!");
            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 257, 0);
            return false;
        }
        Log.i(this.TAG, "find BluetoothGattCharacteristic");
        boolean bool = paramBluetoothGatt.setCharacteristicNotification(localBluetoothGattCharacteristic, true);
        Log.i(this.TAG, "setCharacteristicNotification result :" + bool + " Propertie: " + localBluetoothGattCharacteristic.getProperties());
        Log.i("UUID3", paramUUID3.toString());
        BluetoothGattDescriptor localBluetoothGattDescriptor = localBluetoothGattCharacteristic.getDescriptor(paramUUID3);
        if (localBluetoothGattCharacteristic != null) {
            this.mDescriptor = localBluetoothGattDescriptor;
            this.mHandler.sendEmptyMessageDelayed(5, 300L);
        }
     /*   while (true) {

            if (this.mConnectCallback != null)
                this.mConnectCallback.connectState(this.mDevice, 257, 0);
            return false;
        }*/
        return bool;
    }

    public static BleManager getInstance(Context paramContext) {
        if (instance == null)
            instance = new BleManager(paramContext);
        instance.setContext(paramContext);
        return instance;
    }

    private boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = ((BluetoothManager) this.mContext.getSystemService(Context.BLUETOOTH_SERVICE));
            if (this.mBluetoothManager == null) {
                Log.e(this.TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter == null) {
            Log.e(this.TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }
        Log.i(this.TAG, "start ble service");
        return true;
    }

    private void setContext(Context paramContext) {
        this.mContext = paramContext;
    }

    public void close() {
        this.isConnect = false;
        this.mHandler.removeMessages(5);
        if (this.mBluetoothGatt != null) {
            this.isForceDisconnect = false;
            this.mHandler.removeCallbacks(this.openBle);
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    public void connect(BluetoothDevice paramBluetoothDevice, boolean paramBoolean) {
        Log.i(this.TAG, "connect device");
        if (paramBluetoothDevice != null)
            Log.i("Device ID:", paramBluetoothDevice.getAddress());
//        Log.i("deviceInfo:",paramBluetoothDevice.getAddress());
        if (paramBluetoothDevice == null)
            return;
        if (this.mBluetoothGatt == null) {
            this.isForceDisconnect = true;
            this.mBluetoothGatt = paramBluetoothDevice.connectGatt(this.mContext, paramBoolean, this.mGattCallback);
            if (this.mBluetoothGatt == null) {
                Log.i(this.TAG, "reopen bluetooth");
                this.mBluetoothAdapter.disable();
                this.mHandler.postDelayed(this.openBle, 3000L);
                return;
            }

        }
        this.mBluetoothGatt.connect();
    }

    public void disconnect() {
        this.isConnect = false;
        this.mHandler.removeMessages(5);
        this.mHandler.removeCallbacks(this.openBle);
        if (this.mBluetoothGatt != null)
            this.mBluetoothGatt.disconnect();
    }

    public boolean getConnectState() {
        return this.isForceDisconnect;
    }

    public boolean readCharacterByHand(String paramString1, String paramString2) {
        if (this.mBluetoothGatt == null)
            return false;
        BluetoothGattService localBluetoothGattService = this.mBluetoothGatt.getService(UUID.fromString(paramString1));
        if (localBluetoothGattService == null)
            return false;
        BluetoothGattCharacteristic localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(UUID.fromString(paramString2));
        return this.mBluetoothGatt.readCharacteristic(localBluetoothGattCharacteristic);
    }

    public void register(IConnectCallback paramIConnectCallback) {
        this.mConnectCallback = paramIConnectCallback;
    }

    public void setCharacteristicUUID(String paramString) {
        this.mCharacteristicUUID = paramString;
    }

    public void setClientUUID(String paramString) {
        this.mClicentUUID = paramString;
    }

    public void writeIasAlertLevel(String paramString1, String paramString2, byte[] paramArrayOfByte) {
        String str = null;
        int i = 0;
        if (CLog.isDebug) {
            str = "write ";
            i = 0;
        }
        while (i < paramArrayOfByte.length) {
            str = str + Integer.toHexString(paramArrayOfByte[i] & 0xFF) + ",";
            i += 1;
        }
        Log.i(this.TAG, str);
//        if (this.mBluetoothGatt != null)


//                return;


        Log.i("sendDataValue:", paramArrayOfByte.toString());
        this.writeBuff = paramArrayOfByte;
        BluetoothGattService localBluetoothGattService = this.mBluetoothGatt.getService(UUID.fromString(paramString1));
        if (localBluetoothGattService == null)
            return;
        BluetoothGattCharacteristic localBluetoothGattCharacteristic = localBluetoothGattService.getCharacteristic(UUID.fromString(paramString2));
        if (paramString1 == null)
            return;
        i = localBluetoothGattCharacteristic.getWriteType();
        Log.i(this.TAG, "Character writeType��" + i);
        localBluetoothGattCharacteristic.setValue(paramArrayOfByte);
        localBluetoothGattCharacteristic.setWriteType(i);
        boolean bool = this.mBluetoothGatt.writeCharacteristic(localBluetoothGattCharacteristic);
        Log.i(this.TAG, "write status:" + bool);
    }
}
