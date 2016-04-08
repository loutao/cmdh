package com.ratio.deviceService.logic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.renderscript.Type;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ratio.deviceService.bean.AchievementData;
import com.ratio.deviceService.bean.GPSTotal;
import com.ratio.deviceService.bean.ShareTextBean;
import com.ratio.deviceService.bean.SportsPage;
import com.ratio.deviceService.bean.SportsStatisticsData;
import com.ratio.deviceService.bean.SportsTotal;
import com.ratio.util.DateTimeHelper;

import org.apache.http.util.VersionInfo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by Mesogene on 2015/5/25.
 */
public class ConfigManager {
    private static final String mXmlFile = "codoon_config_xml";

    public static void addClickAutoPauseButtonCount(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setIntValue(paramContext, "click_autopause_button_count", getClickAutoPauseButtonCount(paramContext) + 1);
    }

    public static void clearUpdateInfo(Context paramContext) {
        setStringValue(paramContext, "update_info", "");
    }

    public static boolean contains(Context paramContext, String paramString) {
        try {
            boolean bool = paramContext.getSharedPreferences("codoon_config_xml", 4).contains(paramString);
            return bool;
        } catch (Exception ex) {
        }
        return false;
    }

    public static int getAppSource(Context paramContext) {
        try {
            ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
            return localApplicationInfo.metaData.getInt("App_Source_Key");
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            while (true)
                localNameNotFoundException.printStackTrace();
        }
    }

    public static Object getAppSource(Context paramContext, String paramString) {
        Object localObject = null;
        try {
            ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
            return localApplicationInfo.metaData.get(paramString);
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            while (true)
                localNameNotFoundException.printStackTrace();
        }
    }

    public static String getBaiduLocation(Context paramContext) {
        return getStringValue(paramContext, "gps_location_key".concat("_baidu"));
    }

    public static boolean getBooleanValue(Context paramContext, String paramString, boolean paramBoolean) {
        return paramContext.getSharedPreferences("codoon_config_xml", 4).getBoolean(paramString, paramBoolean);
    }

    public static int getClickAutoPauseButtonCount(Context paramContext) {
        return getIntValue(paramContext, "click_autopause_button_count", 0);
    }

    public static float getFloatValue(Context paramContext, String paramString, float paramFloat) {
        return paramContext.getSharedPreferences("codoon_config_xml", 4).getFloat(paramString, paramFloat);
    }

    public static String getGPSLocation(Context paramContext) {
        String str1 = getStringValue(paramContext, "gps_location_key");
        if ((str1.contains("E")) || (str1.equals(""))) ;
        for (String str2 = ""; ; str2 = str1)
            return str2;
    }

    public static String getGaodeLocation(Context paramContext) {
        return getStringValue(paramContext, "gps_location_key".concat("_gaode"));
    }

    public static String getImei(Context paramContext) {
        TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        Object localObject2 = localTelephonyManager.getDeviceId();
        Object localObject1;
        if (localObject2 != null) {
            localObject1 = localObject2;
            if (((String) localObject2).length() != 0) ;
        } else {
            localObject1 = "" + localTelephonyManager.getDeviceId();
            localObject2 = "" + localTelephonyManager.getSimSerialNumber();
            localObject1 = new UUID(("" + Settings.Secure.getString(paramContext.getContentResolver(), "android_id")).hashCode(), ((String) localObject1).hashCode() << 32 | ((String) localObject2).hashCode()).toString();
        }
        localObject2 = localObject1;
        try {
            if (Integer.parseInt((String) localObject1) == 0)
                localObject2 = UserData.GetInstance(paramContext).GetUserBaseInfo().id;
            return "24-" + (String) localObject2;
        } catch (Exception ex) {
            while (true)
                localObject2 = localObject1;
        }
    }

    public static String getImei2(Context paramContext) {
        TelephonyManager localTelephonyManager = (TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE);
        String str2 = localTelephonyManager.getDeviceId();
        String str1;
        if (str2 != null) {
            str1 = str2;
            if (str2.length() != 0) ;
        } else {
            str1 = "" + localTelephonyManager.getDeviceId();
            str2 = "" + localTelephonyManager.getSimSerialNumber();
            str1 = new UUID(("" + Settings.Secure.getString(paramContext.getContentResolver(), "android_id")).hashCode(), str1.hashCode() << 32 | str2.hashCode()).toString();
        }
        return "250-" + str1;
    }

    public static int getIntValue(Context paramContext, String paramString) {
        try {
            int i = paramContext.getSharedPreferences("codoon_config_xml", 4).getInt(paramString, 0);
            return i;
        } catch (Exception ex) {
        }
        return 0;
    }

    public static int getIntValue(Context paramContext, String paramString, int paramInt) {
        try {
            paramInt = paramContext.getSharedPreferences("codoon_config_xml", 4).getInt(paramString, paramInt);
            return paramInt;
        } catch (Exception ex) {
        }
        return 0;
    }

    public static boolean getIsClubMember(Context paramContext) {
        return getBooleanValue(paramContext, "ClubJoinHistory".concat(UserData.GetInstance(paramContext).GetUserBaseInfo().id), false);
    }

    public static boolean getIsLogin(Context paramContext) {
        return paramContext.getSharedPreferences("MyPrefsFile", 4).getBoolean("IsLogin", false);
    }

    public static boolean getIsOpenMessageNotify(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_key_" + str, true);
    }

    public static boolean getIsOpenMessageNotifyFriends(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_friends_key_" + str, true);
    }

    public static boolean getIsOpenMessageNotifyGroup(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_group_key_" + str, true);
    }

    public static boolean getIsOpenMessageNotifyRank(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_rank_key_" + str, true);
    }

    public static boolean getIsOpenMessageNotifySound(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_sound_key_" + str, true);
    }

    public static boolean getIsOpenMessageNotifyVibrate(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_vibrate_key_" + str, true);
    }

    public static boolean getIsOpenNoDisturb(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "is_open_message_notify_no_disturb_key_" + str, false);
    }

    public static boolean getIsSaveNetData(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "sava_net_data_" + str, false);
    }

    public static boolean getIsShowLocation(Context paramContext, String paramString) {
        return getBooleanValue(paramContext, "sc_show_location".concat(paramString), true);
    }

    public static boolean getIsStepsPause(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "pause_steps_key_" + str, false);
    }

    public static boolean getIsStepsSurport(Context paramContext) {
        return getBooleanValue(paramContext, "surport_steps_key", true);
    }

    /* public static AchievementActivity.SportsType getLastSportType(Context paramContext)
     {
         String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
         return AchievementActivity.SportsType.values()[getIntValue(paramContext, "last_sports_key_" + str, AchievementActivity.SportsType.RUN.ordinal())];
     }
 */
    public static Long getLongValue(Context paramContext, String paramString, long paramLong) {
        return Long.valueOf(paramContext.getSharedPreferences("codoon_config_xml", 4).getLong(paramString, paramLong));
    }

    public static boolean getNeedComptUploadHint(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "need_compt_upload_hint_" + str, true);
    }

    public static long[] getNewCommentsAndLikesAndFeeds(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return new long[]{getLongValue(paramContext, "feed_comments_" + str, 0L).longValue(), getLongValue(paramContext, "feed_likes_" + str, 0L).longValue(), getLongValue(paramContext, "new_feeds_" + str, 0L).longValue(), getLongValue(paramContext, "new_notify_" + str, 0L).longValue()};
    }

    public static int getNoDisturbEndTime(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getIntValue(paramContext, "message_notify_no_disturb_end_key_" + str, 9);
    }

    public static int getNoDisturbStartTime(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getIntValue(paramContext, "message_notify_no_disturb_start_key_" + str, 22);
    }

    public static boolean getNofifyShareDepressed(Context paramContext) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        return getBooleanValue(paramContext, "notify_share_depressed_key_" + str, true);
    }

    public static ShareTextBean getShareText(Context paramContext) {
        Gson localGson = new Gson();
        if (!getStringValue(paramContext, "share_text").equals(""))
            return (ShareTextBean) localGson.fromJson(getStringValue(paramContext, "share_text"), ShareTextBean.class);
        return new ShareTextBean();
    }

    public static String getStringValue(Context paramContext, String paramString) {
        return paramContext.getSharedPreferences("codoon_config_xml", 4).getString(paramString, "");
    }

    public static VersionInfo getUpdateInfo(Context paramContext) {
        Gson localGson = new Gson();
        if (!getStringValue(paramContext, "update_info").equals(""))
            return (VersionInfo) localGson.fromJson(getStringValue(paramContext, "update_info"), VersionInfo.class);
        return null;
    }

    public static int getUserIntValue(Context paramContext, String paramString, int paramInt) {
        return getIntValue(paramContext, UserData.GetInstance(paramContext).GetUserBaseInfo().id.concat(paramString), paramInt);
    }

    public static String getUserStringValue(Context paramContext, String paramString) {
        return getStringValue(paramContext, UserData.GetInstance(paramContext).GetUserBaseInfo().id.concat(paramString));
    }

    public static void saveUpdateInfo(Context paramContext, VersionInfo paramVersionInfo) {
        if (paramVersionInfo == null)
            return;
        setStringValue(paramContext, "update_info", new Gson().toJson(paramVersionInfo));
    }

    public static void setBaiduLocation(Context paramContext, String paramString) {
        setStringValue(paramContext, "gps_location_key".concat("_baidu"), paramString);
    }

    public static void setBooleanValue(Context paramContext, String paramString, boolean paramBoolean) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("codoon_config_xml", 4).edit();
        localEditor.putBoolean(paramString, paramBoolean);
        localEditor.commit();
    }

    public static void setFloatValue(Context paramContext, String paramString, float paramFloat) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("codoon_config_xml", 4).edit();
        localEditor.putFloat(paramString, paramFloat);
        localEditor.commit();
    }

    public static void setGPSLocation(Context paramContext, String paramString) {
        setStringValue(paramContext, "gps_location_key", paramString);
    }

    public static void setGaodeLocation(Context paramContext, String paramString) {
        setStringValue(paramContext, "gps_location_key".concat("_gaode"), paramString);
    }

    public static void setIntValue(Context paramContext, String paramString, int paramInt) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("codoon_config_xml", 4).edit();
        localEditor.putInt(paramString, paramInt);
        localEditor.commit();
    }

    public static void setIsClubMember(Context paramContext, String paramString, boolean paramBoolean) {
        setBooleanValue(paramContext, "ClubJoinHistory".concat(paramString), paramBoolean);
    }

    public static void setIsClubMember(Context paramContext, boolean paramBoolean) {
        setBooleanValue(paramContext, "ClubJoinHistory".concat(UserData.GetInstance(paramContext).GetUserBaseInfo().id), paramBoolean);
    }

    public static void setIsLogin(Context paramContext, boolean paramBoolean) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("MyPrefsFile", 4).edit();
        localEditor.putBoolean("IsLogin", paramBoolean);
        localEditor.commit();
    }

    public static void setIsShowLocation(Context paramContext, boolean paramBoolean) {
        setBooleanValue(paramContext, "sc_show_location".concat(UserData.GetInstance(paramContext).GetUserBaseInfo().id), paramBoolean);
    }

   /* public static void setLastSportType(Context paramContext, AchievementActivity.SportsType paramSportsType)
    {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setIntValue(paramContext, "last_sports_key_" + str, paramSportsType.ordinal());
    }*/

    public static void setLongValue(Context paramContext, String paramString, long paramLong) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("codoon_config_xml", 4).edit();
        localEditor.putLong(paramString, paramLong);
        localEditor.commit();
    }

    public static void setNeedComptUploadHint(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "need_compt_upload_hint_" + str, paramBoolean);
    }

    public static void setNewCommentsAndLikesAndFeeds(Context paramContext, long paramLong1, long paramLong2, long paramLong3, long paramLong4) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setLongValue(paramContext, "feed_comments_" + str, paramLong1);
        setLongValue(paramContext, "feed_likes_" + str, paramLong2);
        setLongValue(paramContext, "new_feeds_" + str, paramLong3);
        setLongValue(paramContext, "new_notify_" + str, paramLong4);
    }

    public static void setNoDisturbEndTime(Context paramContext, int paramInt) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setIntValue(paramContext, "message_notify_no_disturb_end_key_" + str, paramInt);
    }

    public static void setNoDisturbStartTime(Context paramContext, int paramInt) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setIntValue(paramContext, "message_notify_no_disturb_start_key_" + str, paramInt);
    }

    public static void setNofifyShareDepressed(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "notify_share_depressed_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotify(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotifyFriends(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_friends_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotifyGroup(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_group_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotifyRank(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_rank_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotifySound(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_sound_key_" + str, paramBoolean);
    }

    public static void setOpenMessageNotifyVibrate(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_vibrate_key_" + str, paramBoolean);
    }

    public static void setOpenNoDisturb(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "is_open_message_notify_no_disturb_key_" + str, paramBoolean);
    }

    public static void setSaveNetData(Context paramContext, boolean paramBoolean) {
        String str = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "sava_net_data_" + str, paramBoolean);
    }

    public static void setShareText(Context paramContext, ShareTextBean paramShareTextBean) {
        if (paramShareTextBean == null)
            return;
        setStringValue(paramContext, "share_text", new Gson().toJson(paramShareTextBean));
    }

    public static void setStepsPause(Context paramContext, boolean paramBoolean) {
        Object localObject = UserData.GetInstance(paramContext.getApplicationContext()).GetUserBaseInfo().id;
        setBooleanValue(paramContext, "pause_steps_key_" + (String) localObject, paramBoolean);
        localObject = new Intent();
        ((Intent) localObject).setAction("wake_lock_sensor");
        paramContext.sendBroadcast((Intent) localObject);
    }

    public static void setStepsSurport(Context paramContext, boolean paramBoolean) {
        setBooleanValue(paramContext, "surport_steps_key", paramBoolean);
    }

    public static void setStringValue(Context paramContext, String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("codoon_config_xml", 4).edit();
        localEditor.putString(paramString1, paramString2);
        localEditor.commit();
    }

    public static void setUserIntValue(Context paramContext, String paramString, int paramInt) {
        setIntValue(paramContext, UserData.GetInstance(paramContext).GetUserBaseInfo().id.concat(paramString), paramInt);
    }

    public static void setUserStringValue(Context paramContext, String paramString1, String paramString2) {
        setStringValue(paramContext, UserData.GetInstance(paramContext).GetUserBaseInfo().id.concat(paramString1), paramString2);
    }

    public static void updateHistoryAccessoryData(Context paramContext, GPSTotal paramGPSTotal, int paramInt) {
       /* Gson localGson = new Gson();
        Type localType = new TypeToken<AchievementData>().getType();

        Object localObject1 = getStringValue(paramContext, "achievement_config_key" + UserData.GetInstance(paramContext).GetUserBaseInfo().id);
        Object localObject2;
        if ((localObject1 != null) && (((String)localObject1).length() > 0))
        {
            localObject1 = (AchievementData)localGson.fromJson((String)localObject1, localType);
            ((AchievementData)localObject1).history_count += 1;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_calorie += paramGPSTotal.TotalContEnergy;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_time += paramGPSTotal.TotalTime / 1000;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_km += paramGPSTotal.TotalDistance;
            localObject2 = ((AchievementData)localObject1).user_sports_type_summary.iterator();
            label178: if (((Iterator)localObject2).hasNext())
            {
                SportsPage localSportsPage = (SportsPage)((Iterator)localObject2).next();
                if (localSportsPage.sports_type != paramGPSTotal.sportsType)
                    break label579;
                localSportsPage.count += 1;
                localSportsPage.total_calories += paramGPSTotal.TotalContEnergy;
                localSportsPage.total_length += paramGPSTotal.TotalDistance * 1000.0F;
                localSportsPage.total_time += paramGPSTotal.TotalTime / 1000;
            }
            setStringValue(paramContext, "achievement_config_key" + UserData.GetInstance(paramContext).GetUserBaseInfo().id, localGson.toJson(localObject1, localType));
            localObject1 = new SportsStatisticsData();
            ((SportsStatisticsData)localObject1).date = DateTimeHelper.get_yMd_String(paramGPSTotal.EndDateTime);
            ((SportsStatisticsData)localObject1).length = (paramGPSTotal.TotalDistance * 1000.0F);
            ((SportsStatisticsData)localObject1).total_calories = paramGPSTotal.TotalContEnergy;
            ((SportsStatisticsData)localObject1).total_time = paramGPSTotal.TotalTime / 1000;
            ((SportsStatisticsData)localObject1).sports_type = paramGPSTotal.sportsType;
            ((SportsStatisticsData)localObject1).userid = UserData.GetInstance(paramContext).GetUserBaseInfo().id;
            if (paramGPSTotal.sportsType < 0)
                break label582;
            ((SportsStatisticsData)localObject1).source = 0;
        }
        while (true)
        {
            new SportStatisticsDataDAO(paramContext).update((SportsStatisticsData)localObject1);
            return;
            localObject2 = new AchievementData();
            ((AchievementData)localObject2).user_sports_total = new SportsTotal();
            ((AchievementData)localObject2).user_sports_type_summary = new ArrayList();
            int i = 0;
            localObject1 = localObject2;
            if (i >= 7)
                break;
            localObject1 = new SportsPage();
            if (i == 5)
            {
                ((SportsPage)localObject1).sports_type = -1;
                ((SportsPage)localObject1).sports_type_display = paramContext.getString(2131559704);
            }
            while (true)
            {
                ((AchievementData)localObject2).user_sports_type_summary.add(localObject1);
                i += 1;
                break;
                if (i == 6)
                {
                    ((SportsPage)localObject1).sports_type = -4;
                    ((SportsPage)localObject1).sports_type_display = paramContext.getString(2131559439);
                }
                else
                {
                    ((SportsPage)localObject1).sports_type = i;
                    ((SportsPage)localObject1).sports_type_display = paramContext.getResources().getStringArray(2131165206)[i];
                }
            }
            label579: break label178;
            label582: if (paramGPSTotal.sportsType == -1)
            {
                ((SportsStatisticsData)localObject1).source = 1;
                ((SportsStatisticsData)localObject1).steps = paramInt;
            }
            else
            {
                ((SportsStatisticsData)localObject1).source = 2;
            }
        }*/
    }

    public static void updateHistoryData(Context paramContext, GPSTotal paramGPSTotal) {
      /*  Gson localGson = new Gson();
        Type localType = new TypeToken()
        {
        }
                .getType();
        Object localObject1 = getStringValue(paramContext, "achievement_config_key" + UserData.GetInstance(paramContext).GetUserBaseInfo().id);
        Object localObject2;
        if ((localObject1 != null) && (((String)localObject1).length() > 0))
        {
            localObject1 = (AchievementData)localGson.fromJson((String)localObject1, localType);
            ((AchievementData)localObject1).history_count += 1;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_calorie += paramGPSTotal.TotalContEnergy;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_time += paramGPSTotal.TotalTime / 1000;
            localObject2 = ((AchievementData)localObject1).user_sports_total;
            ((SportsTotal)localObject2).total_km += paramGPSTotal.TotalDistance;
            localObject2 = ((AchievementData)localObject1).user_sports_type_summary.iterator();
            label167: if (((Iterator)localObject2).hasNext())
            {
                SportsPage localSportsPage = (SportsPage)((Iter`ator)localObject2).next();
                if (localSportsPage.sports_type != paramGPSTotal.sportsType)
                    break label580;
                localSportsPage.count += 1;
                localSportsPage.total_calories += paramGPSTotal.TotalContEnergy;
                localSportsPage.total_length += paramGPSTotal.TotalDistance * 1000.0F;
                localSportsPage.total_time += paramGPSTotal.TotalTime / 1000;
            }
            setStringValue(paramContext, "achievement_config_key" + UserData.GetInstance(paramContext).GetUserBaseInfo().id, localGson.toJson(localObject1, localType));
            localObject1 = new SportsStatisticsData();
            ((SportsStatisticsData)localObject1).date = DateTimeHelper.get_yMd_String(paramGPSTotal.EndDateTime);
            ((SportsStatisticsData)localObject1).length = (paramGPSTotal.TotalDistance * 1000.0F);
            ((SportsStatisticsData)localObject1).total_calories = paramGPSTotal.TotalContEnergy;
            ((SportsStatisticsData)localObject1).total_time = paramGPSTotal.TotalTime / 1000;
            ((SportsStatisticsData)localObject1).sports_type = paramGPSTotal.sportsType;
            ((SportsStatisticsData)localObject1).userid = UserData.GetInstance(paramContext).GetUserBaseInfo().id;
            if (paramGPSTotal.sportsType < 0)
                break label583;
            ((SportsStatisticsData)localObject1).source = 0;
        }
        while (true)
        {
            new SportStatisticsDataDAO(paramContext).update((SportsStatisticsData)localObject1);
            return;
            localObject2 = new AchievementData();
            ((AchievementData)localObject2).user_sports_total = new SportsTotal();
            ((AchievementData)localObject2).user_sports_type_summary = new ArrayList();
            int i = 0;
            localObject1 = localObject2;
            if (i >= 8)
                break;
            localObject1 = new SportsPage();
            if (i == 6)
            {
                ((SportsPage)localObject1).sports_type = -1;
                ((SportsPage)localObject1).sports_type_display = paramContext.getString(2131559704);
            }
            while (true)
            {
                ((AchievementData)localObject2).user_sports_type_summary.add(localObject1);
                i += 1;
                break;
                if (i == 5)
                {
                    ((SportsPage)localObject1).sports_type = -4;
                    ((SportsPage)localObject1).sports_type_display = paramContext.getString(2131559439);
                }
                else if (i == 7)
                {
                    ((SportsPage)localObject1).sports_type = -5;
                    ((SportsPage)localObject1).sports_type_display = paramContext.getResources().getStringArray(2131165206)[5];
                }
                else
                {
                    ((SportsPage)localObject1).sports_type = i;
                    ((SportsPage)localObject1).sports_type_display = paramContext.getResources().getStringArray(2131165206)[i];
                }
            }
            label580: break label167;
            label583: if (paramGPSTotal.sportsType == -1)
                ((SportsStatisticsData)localObject1).source = 1;
            else if (paramGPSTotal.sportsType == -5)
                ((SportsStatisticsData)localObject1).source = 3;
            else
                ((SportsStatisticsData)localObject1).source = 2;
        }
    }*/
    }
}