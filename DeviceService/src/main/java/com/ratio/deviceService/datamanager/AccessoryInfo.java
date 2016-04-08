package com.ratio.deviceService.datamanager;

import java.io.Serializable;

/**
 * Created by Mesogene on 2015/5/25.
 */
public class AccessoryInfo
    implements Serializable
    {
        public String binary_url;
        public String checksum;
        public String date;
        public String description;
        public String device_name;
        public int device_type;
        public String function_url;
        public int popup = 0;
        public long size;
        public int version_code;
        public String version_name;
    }
