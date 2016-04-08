package com.njucm.cmdh.app.activitys.user;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.File;

/**
 * Created by Mesogene on 7/18/15.
 */
public class LoginService {
    public static void saveUserInfo(Context context,String login ,String password){

        SharedPreferences sp = context.getSharedPreferences("user_config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.commit();

    }

    public static boolean isLogin(Context context){

        boolean flag = false;
        SharedPreferences sp = context.getSharedPreferences("user_config",Context.MODE_PRIVATE);
        String sp_login =sp.getString("login", "");
        String sp_password =sp.getString("password", "");

        if(!sp_login.equals("")){
            flag = true;
        }
        return flag;

    }

    public static void clearLogin(Context context){
        SharedPreferences sp = context.getSharedPreferences("user_config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (!sp.getString("login","").equals("")){
            
            editor.clear();
            editor.commit();
        }
       /* File file= new File("/data/data/"+ context.getPackageName().toString()+"/shared_prefs","user_config.xml");

        if(file.exists()){
            file.delete();
            file.getAbsoluteFile().delete();
            System.gc();
        }*/
    }

    public static void editSp(Context context,String login ,String password){
        SharedPreferences sp = context.getSharedPreferences("user_config",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("login", login);
        editor.putString("password", password);
        editor.commit();
    }
}
