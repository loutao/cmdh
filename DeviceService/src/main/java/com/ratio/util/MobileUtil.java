package com.ratio.util;

import android.os.Build;

import com.ratio.deviceService.data.CLog;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class MobileUtil {
    public static boolean isCurMobileManuConnect() {
        boolean bool = false;
        String str = Build.BRAND.toLowerCase();
        CLog.i("MobileUtil", "curMobile" + str);
        if (!str.contains("samsung"))
            bool = true;
        return bool;
    }
}
