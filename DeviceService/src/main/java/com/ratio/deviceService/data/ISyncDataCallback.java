package com.ratio.deviceService.data;

import com.ratio.deviceService.datamanager.AccessoryValues;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract interface ISyncDataCallback {
    public abstract void onBattery(int paramInt);

    public abstract void onBindSucess();

    public abstract void onClearDataSuccessed();

    public abstract void onConnectSuccessed();

    public abstract void onFriedWarningSuccess();

    public abstract void onGetDeviceID(String paramString);

    public abstract void onGetDeviceTime(String paramString);

    public abstract void onGetOtherDatas(ArrayList<Integer> paramArrayList);

    public abstract void onGetUserInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);

    public abstract void onGetVersion(String paramString);

    public abstract void onNullData();

    public abstract void onSetFrindSwitchOver();

    public abstract void onSetTargetStepOver();

    public abstract void onSyncDataOver(HashMap<String, AccessoryValues> paramHashMap, ByteArrayOutputStream paramByteArrayOutputStream);

    public abstract void onSyncDataProgress(int paramInt);

    public abstract void onTimeOut();

    public abstract void onUpdateAlarmReminderSuccessed();

    public abstract void onUpdateHeartWarningSuccess();

    public abstract void onUpdateTimeSuccessed();

    public abstract void onUpdateUserinfoSuccessed();
}
