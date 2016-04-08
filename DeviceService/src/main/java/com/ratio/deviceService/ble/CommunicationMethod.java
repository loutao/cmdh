package com.ratio.deviceService.ble;

import android.bluetooth.BluetoothDevice;

import com.ratio.deviceService.data.ISyncDataCallback;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract interface CommunicationMethod {
    public abstract void SendDataToDevice(int[] paramArrayOfInt);

    public abstract void cancel();

    public abstract boolean isConnect();

    public abstract void register(ISyncDataCallback paramISyncDataCallback);

    public abstract void start(BluetoothDevice paramBluetoothDevice);

    public abstract void start(int[] paramArrayOfInt);

    public abstract void stop();
}
