package com.ratio.deviceService.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class SurroundPersonJSON   implements Serializable
{
    public String background_img;
    public double calories;
    public String descroption;
    public double distance;
    public int distance_from_me;
    public String distance_update_time;
    public long duration;
    public String feed;
    public String feed_pic;
    public String feed_time;
    public boolean followed = false;
    public boolean following = false;
    public boolean friend = false;
    public String frist_char;
    public int gender;
    public String get_icon_large;
    public String get_icon_xlarge;
    public String hobby;
    public String id;
    public int id_num;
    public int index;
    public boolean isLoading = true;
    public double lastsportdistance;
    public String lastsporttime = "";
    public String lastsporttype;
    public String member_name;
    public List<String> mobile_portraits_l;
    public List<String> mobile_portraits_x;
    public int month_sport_time;
    public float near_sports;
    public String nick;
    public float[] point;
    public String portrait;
    public String portrait_l;
    public String portrait_x;
    public PortraitArrayJSON portraits;
    public String position;
    public String regist_datetime;
    public int relationship;
    public String route_id;
    public int sports_type;
    public String time;
    public float total_length;
    public String user_icon;
    public String user_id;
}
