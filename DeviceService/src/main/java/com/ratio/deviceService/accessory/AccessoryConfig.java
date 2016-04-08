package com.ratio.deviceService.accessory;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class AccessoryConfig {
    private static final String mXmlFile = "accessory_config_xml";
    public static String userID = "";

    public static boolean getBooleanValue(Context paramContext, String paramString, boolean paramBoolean) {
        return paramContext.getSharedPreferences("accessory_config_xml", 0).getBoolean(paramString, paramBoolean);
    }

    public static float getFloatValue(Context paramContext, String paramString, float paramFloat) {
        return paramContext.getSharedPreferences("accessory_config_xml", 0).getFloat(paramString, paramFloat);
    }

    public static int getIntValue(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("accessory_config_xml", 0).getInt(paramString, 0);
    /*    int i = 0;
        try {
            int j = paramContext.getSharedPreferences("accessory_config_xml", 0).getInt(paramString, 0);
            i = j;
        } catch (Exception localException) {
        }*/
    }

    public static int getIntValue(Context paramContext, String paramString, int paramInt) {
        return paramContext.getSharedPreferences("accessory_config_xml", 0).getInt(paramString, paramInt);
   /*     int i = 0;
        try {
            int j = paramContext.getSharedPreferences("accessory_config_xml", 0).getInt(paramString, paramInt);
            i = j;

        } catch (Exception localException) {
            while (true)
                int i = 0;
        }*/
    }

    public static Long getLongValue(Context paramContext, String paramString, long paramLong) {
        return Long.valueOf(paramContext.getSharedPreferences("accessory_config_xml", 0).getLong(paramString, paramLong));
    }

    public static String getStringValue(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("accessory_config_xml", 0).getString(paramString, "");
    }

    public static void setBooleanValue(Context paramContext, String paramString, boolean paramBoolean) {
        Editor localEditor = paramContext.getSharedPreferences("accessory_config_xml", 0).edit();
        localEditor.putBoolean(paramString, paramBoolean);
        localEditor.commit();
    }

    public static void setFloatValue(Context paramContext, String paramString, float paramFloat) {
        Editor localEditor = paramContext.getSharedPreferences("accessory_config_xml", 0).edit();
        localEditor.putFloat(paramString, paramFloat);
        localEditor.commit();
    }

    public static void setIntValue(Context paramContext, String paramString, int paramInt) {
        Editor localEditor = paramContext.getSharedPreferences("accessory_config_xml", 0).edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public static void setLongValue(Context paramContext, String paramString, long paramLong) {
        Editor localEditor = paramContext.getSharedPreferences("accessory_config_xml", 0).edit();
        localEditor.putLong(paramString, paramLong);
        localEditor.commit();
    }

    public static void setStringValue(Context paramContext, String paramString1, String paramString2) {
        Editor localEditor = paramContext.getSharedPreferences("accessory_config_xml", 0).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }
}