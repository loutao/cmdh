package com.ratio.deviceService.bean;


import com.ratio.deviceService.annotation.Expose;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class GPSTotal implements Serializable {


    @Expose(serialize = false, deserialize = false)
    public float AverageSpeed;
    public double ClimbAltitude;

    @Expose(serialize = false, deserialize = false)
    public long EndDateTime;
    public int IsUpload = 0;

    @Expose(serialize = false, deserialize = false)
    public int LocationCount;

    @Expose(serialize = false, deserialize = false)
    public double MaxAltitude;

    @Expose(serialize = false, deserialize = false)
    public float MaxToPreviousSpeed;
    public double MinAltitude;

    @Expose(serialize = false, deserialize = false)
    public long StartDateTime;

    @Expose(serialize = false, deserialize = false)
    public float TotalContEnergy;

    @Expose(serialize = false, deserialize = false)
    public float TotalDistance;

    @Expose(serialize = false, deserialize = false)
    public int TotalTime;

    @Expose(serialize = false, deserialize = false)
    public int activity_type = 0;

    @Expose(serialize = false, deserialize = false)
    public int baidu_cloud = 0;

    @Expose(serialize = false, deserialize = false)
    public float[] calories_per_m;

    @Expose(serialize = false, deserialize = false)
    public String custom_words;
    public String disLocation;

    @Expose(serialize = false, deserialize = false)
    public String end_time;

    @Expose(serialize = false, deserialize = false)
    public int goal_type;

    @Expose(serialize = false, deserialize = false)
    public float goal_value;

    @Expose(serialize = false, deserialize = false)
    public HashMap<Long, Integer> heart_rate;

    @Expose(serialize = false, deserialize = false)
    public int highest_speed_perkm;

    @Expose(serialize = false, deserialize = false)
    public int history_version;
    public long id;
    public int isAutoSave = 0;

    @Expose(serialize = false, deserialize = false)
    public int isChallengeSuccess = 0;
    public int isShared = 0;

    @Expose(serialize = false, deserialize = false)
    public int is_baidu;

    @Expose(serialize = false, deserialize = false)
    public int is_crash_restore = 0;
    public int is_download_detail = 0;

    @Expose(serialize = false, deserialize = false)
    public int is_open;

    @Expose(serialize = false, deserialize = false)
    public int is_real = 0;

    @Expose(serialize = false, deserialize = false)
    public boolean is_root = false;

    @Expose(serialize = false, deserialize = false)
    public int last_of_program;

    @Expose(serialize = false, deserialize = false)
    public String location;

    @Expose(serialize = false, deserialize = false)
    public String model;

    @Expose(serialize = false, deserialize = false)
    public String offset_text;

    @Expose(serialize = false, deserialize = false)
    public GPSPoint[] points;

    @Expose(serialize = false, deserialize = false)
    public String product_id;

    @Expose(serialize = false, deserialize = false)
    public String program_name;

    @Expose(serialize = false, deserialize = false)
    public String release_version;
    public String route_id;

    @Expose(serialize = false, deserialize = false)
    public int sportsMode = 0;
    public String sportsModeText;

    @Expose(serialize = false, deserialize = false)
    public int sportsType = 0;

    @Expose(serialize = false, deserialize = false)
    public String stage_des;

    @Expose(serialize = false, deserialize = false)
    public String start_time;

    @Expose(serialize = false, deserialize = false)
    public float total_time;
    public String userid;

    @Expose(serialize = false, deserialize = false)
    public List<GPSMilePoint> usettime_per_km;

    @Expose(serialize = false, deserialize = false)
    public String version;
}
