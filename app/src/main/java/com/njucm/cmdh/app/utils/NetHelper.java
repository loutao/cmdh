package com.njucm.cmdh.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mesogene on 4/22/15.
 */
public class NetHelper {

    public static boolean isHaveInternet(final Context context){
        try {
            ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return (info!=null&&info.isConnected());
        } catch (Exception e) {
            return false;
        }
    }
}
