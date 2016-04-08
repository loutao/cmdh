package com.ratio.deviceService.datamanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class AccessoryValues {
    public int calories;
    public int deepSleep;
    public int distances;
    public int light_sleep;
    public long sleep_endTime;
    public long sleep_startTime;
    public HashMap<Long, Integer> sleepdetail = new HashMap();
    public int sleepmins;
    public int sport_duration;
    public int sport_mode;
    public long start_sport_time;
    public int steps;
    public String time;
    public long tmpEndSleep;
    public int wake_time;

    public String toString() {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return this.time + " sport_start:" + localSimpleDateFormat.format(new Date(this.start_sport_time)) + " sport_duration: " + this.sport_duration + " steps: " + this.steps + " calories: " + this.calories + " distances: " + this.distances + " sleep_startTime: " + localSimpleDateFormat.format(new Date(this.sleep_startTime)) + " sleep_endTime: " + localSimpleDateFormat.format(new Date(this.sleep_endTime)) + " sleepmins: " + this.sleepmins + " deepSleep: " + this.deepSleep + " light_sleep: " + this.light_sleep + " wake_time: " + this.wake_time;
    }
}