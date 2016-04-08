package com.ratio.deviceService.bean;

import com.ratio.deviceService.annotation.Expose;

import java.io.Serializable;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class GPSMilePoint   implements Serializable
{
    public int atLineIndexNext;
    public int atLineIndexPre;
    public String atLocation;

    @Expose(serialize = false, deserialize = false)
    public float distance;
    public GPSLocation gpsLocation;
    public long id;
    public int index;

    @Expose(serialize = false, deserialize = false)
    public float speed;

    @Expose(serialize = false, deserialize = false)
    public long totalUseTime;

    @Expose(serialize = false, deserialize = false)
    public long useTime;
}
