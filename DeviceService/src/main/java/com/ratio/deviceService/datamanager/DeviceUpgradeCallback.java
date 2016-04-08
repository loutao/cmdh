package com.ratio.deviceService.datamanager;

/**
 * Created by Mesogene on 2015/5/13.
 */
public abstract interface DeviceUpgradeCallback
{
    public abstract void onChangeToBootMode();

    public abstract void onCheckBootResult(boolean paramBoolean);

    public abstract void onConnectBootSuccess();

    public abstract void onGetBootVersion(String paramString);

    public abstract void onTimeOut();

    public abstract void onWriteFrame(int paramInt1, int paramInt2);
}