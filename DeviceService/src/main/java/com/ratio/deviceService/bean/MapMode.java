package com.ratio.deviceService.bean;

/**
 * Created by Mesogene on 2015/5/25.
 */
public enum  MapMode {
    STREET_MODE(0),SATELLITE_MODE(1);


     MapMode(int i) {
    }




    public static MapMode getValue(int paramInt)
    {
        if (paramInt == 0);
        for (MapMode localMapMode = STREET_MODE; ; localMapMode = SATELLITE_MODE)
            return localMapMode;
    }
}
