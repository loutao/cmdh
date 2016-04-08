package com.ratio.deviceService.datamanager;

import android.content.Context;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class BLEDeviceFactory {
    public static BaseAccessorySyncManager getManagerByDeviceType(Context paramContext, String paramString) {
        if (paramString != null)
            return new CodoonProtocolBLE(paramContext);
        else return null;
    }
}
