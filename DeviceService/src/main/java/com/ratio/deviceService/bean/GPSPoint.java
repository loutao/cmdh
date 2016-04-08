package com.ratio.deviceService.bean;

import android.graphics.Color;

import com.ratio.deviceService.annotation.Expose;

import java.io.Serializable;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class GPSPoint implements Serializable, Cloneable
{

    @Expose(serialize = false, deserialize = false)
    public double altitude;
    public long id;

    @Expose(serialize = false, deserialize = false)
    public double latitude;

    @Expose(serialize = false, deserialize = false)
    public double longitude;
    public int pointflag = 0;

    @Expose(serialize = false, deserialize = false)
    public String time_stamp;

    @Expose(serialize = false, deserialize = false)
    public Color topreviouscolor;

    @Expose(serialize = false, deserialize = false)
    public int topreviouscostTime;

    @Expose(serialize = false, deserialize = false)
    public float topreviousdistance;

    @Expose(serialize = false, deserialize = false)
    public float topreviousenergy;

    @Expose(serialize = false, deserialize = false)
    public float topreviousspeed;

    @Expose(serialize = false, deserialize = false)
    public float tostartcostTime;

    @Expose(serialize = false, deserialize = false)
    public float tostartdistance;

    @Expose(serialize = false, deserialize = false)
    public int type = 0;

    public GPSPoint clone()
    {
        Object localObject = null;
        try
        {
            GPSPoint localGPSPoint = (GPSPoint)super.clone();
            localObject = localGPSPoint;
            return localGPSPoint;
        }
        catch (CloneNotSupportedException localCloneNotSupportedException)
        {
            while (true)
                localCloneNotSupportedException.printStackTrace();
        }
    }

    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        return "GPSPoint [id=" + this.id + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", altitude=" + this.altitude + ", topreviousspeed=" + this.topreviousspeed + ", topreviousdistance=" + this.topreviousdistance + ", topreviouscostTime=" + this.topreviouscostTime + ", topreviousenergy=" + this.topreviousenergy + ", topreviouscolor=" + this.topreviouscolor + ", tostartcostTime=" + this.tostartcostTime + ", tostartdistance=" + this.tostartdistance + ", time_stamp=" + this.time_stamp + ", pointflag=" + this.pointflag + ", type=" + this.type + "]";
    }
}
