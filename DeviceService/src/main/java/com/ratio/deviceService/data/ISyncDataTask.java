package com.ratio.deviceService.data;

/**
 * Created by Mesogene on 2015/5/20.
 */
public abstract interface ISyncDataTask
{
    public abstract void SendDataToDevice(int[] paramArrayOfInt);

    public abstract void connectDevice();

    public abstract void setSaveType(SaveManager.eSaveType parameSaveType);

    public abstract void setTryConnectCounts(int paramInt);

    public abstract void start();

    public abstract void stop();
}