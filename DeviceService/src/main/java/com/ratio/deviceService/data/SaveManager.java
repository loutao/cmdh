package com.ratio.deviceService.data;

import android.content.Context;
import android.util.Log;

import com.ratio.deviceService.communication.provider.Java2Xml;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class SaveManager {
    private String TAG = "SaveManager";
    private Context mContext;
    private Java2Xml mJava2Xml;
    private eSaveType mSaveType;

    public SaveManager(Context paramContext, eSaveType parameSaveType)
    {
        this.mContext = paramContext;
        this.mSaveType = parameSaveType;
        if (this.mSaveType == eSaveType.XML)
        {
            this.mJava2Xml = new Java2Xml(this.mContext);
            return;
        }
       return;
    }

    public void addASleepData(int paramInt, long paramLong)
    {
        if (this.mSaveType == eSaveType.DATABSE)
        {
            Log.d(this.TAG, "addASleepData() level:" + paramInt + ",time:" + paramLong);
            return;
        }
        this.mJava2Xml.addSleepNode(paramInt, paramLong);
    }

    public void addASportData(int paramInt1, int paramInt2, int paramInt3, long paramLong)
    {
        if (this.mSaveType != eSaveType.DATABSE)
            this.mJava2Xml.addSportNode(paramInt1, paramInt2, paramInt3, paramLong);
    }

/*
    public void save()
    {
        if (this.mSaveType != eSaveType.DATABSE)
            this.mJava2Xml.save();
    }
*/

    public static enum eSaveType
    {
        XML, DATABSE;
    }
}
