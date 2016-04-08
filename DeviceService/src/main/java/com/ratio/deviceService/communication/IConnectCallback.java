package com.ratio.deviceService.communication;

import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract interface IConnectCallback
{
    public abstract void connectState(BluetoothDevice paramBluetoothDevice, int paramInt1, int paramInt2);

    public abstract void discovered();

    public abstract void getValue(int paramInt);

    public abstract void getValues(ArrayList<Integer> paramArrayList);
}