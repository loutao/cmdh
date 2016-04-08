package com.ratio.deviceService.bean;

/**
 * Created by Mesogene on 2015/5/25.
 */
public enum  MapType {
    BAIDU_MAP(0), GAODE_MAP(1), GOOGLE_MAP(2);

    private MapType(int paramInt)
    {
    }

    public static MapType getValue(int paramInt)
    {
        if (paramInt == 1)
            return GAODE_MAP;
        if (paramInt == 0)
            return BAIDU_MAP;
        return GOOGLE_MAP;
    }
}
