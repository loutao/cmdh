package com.ratio.deviceService.accessory;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;


import com.ratio.BTDeviceService.R;
import com.ratio.deviceService.bean.AccessoriesTotal;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.datamanager.AccessoryValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AccessoryManager {
    public static final int ERR_CONNECT_INTERRUPT = 254;
    public static final int ERR_NOT_MATCH = 253;
    public static final int ERR_NOT_SUPPORT = 252;
    public static final int ERR_NO_DEVICE = 251;
    public static final int ERR_TIME_OUT = 255;
    public static boolean isFirstAutoSync = true;
    private final String TAG = "AccessoryManager";
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public AccessoryManager(Context paramContext) {
        this.mContext = paramContext.getApplicationContext();
        this.mSharedPreferences = this.mContext.getSharedPreferences("MyPrefsFile", 0);
    }

    private int[] getClockUpdateInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12) {
        boolean bool = isSupportFriends(this.mContext, getCurAccessory().address);
        StringBuilder localStringBuilder1 = new StringBuilder();
        CLog.d("enlong", "isSupportFriends:" + bool);
        if (bool) ;
        for (int[] arrayOfInt = new int[14]; ; arrayOfInt = new int[13]) {
            arrayOfInt[0] = paramInt1;
            arrayOfInt[1] = paramInt2;
            arrayOfInt[2] = paramInt3;
            arrayOfInt[3] = paramInt4;
            arrayOfInt[4] = paramInt5;
            arrayOfInt[5] = paramInt6;
            arrayOfInt[6] = paramInt7;
            arrayOfInt[7] = paramInt8;
            arrayOfInt[8] = paramInt9;
            arrayOfInt[9] = paramInt10;
            arrayOfInt[10] = paramInt11;
            arrayOfInt[11] = 0;
            arrayOfInt[12] = 0;
            if (arrayOfInt.length > 13) {
                arrayOfInt[13] = paramInt12;
                StringBuilder localStringBuilder2 = new StringBuilder();
                CLog.d("enlong", "friendTurn:" + paramInt12);
            }
            return arrayOfInt;
        }
    }

    private String getDeviceDesByType(String paramString) {
        String localString;
        if (paramString == null) {
            localString = null;
            return localString;
        }
        String str = null;
        if (("CANDY".equals(paramString)) || ("CSBS".equals(paramString)))
            str = this.mContext.getString(R.string.device_common_describe);

        if (paramString.startsWith("Smartband"))
            str = this.mContext.getString(R.string.device_heart_band_des);
        else
            str = this.mContext.getString(R.string.device_ble_describe);


        return str;
    }

    public static boolean isBLEDevice(String paramString) {
        boolean bool = true;
        if ((paramString != null) && ((paramString.equals("CSBS")) || (paramString.equals("CANDY"))))
            bool = false;
        return bool;
    }

    public static boolean isRomDevice(String paramString) {
        boolean bool2 = true;
        if (paramString == null) {
            bool2 = true;
        }
        if ((paramString.equals("codoon")) || (paramString.startsWith("cod_")))
            bool2 = false;
        return bool2;
    }

    public static boolean isSupportBLEDevice(Context paramContext) {
        return paramContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static boolean isSupportFriends(Context paramContext, String paramString) {
        if (paramString == null) return false;
        return AccessoryConfig.getBooleanValue(paramContext, "isSupportAccessoryFriend" + paramString, false);
    }

    public static boolean isThirdBleDevice(String paramString) {
        boolean bool2 = true;
        if (paramString == null) {
            bool2 = true;
        }
        if ((paramString.equals("CSL")) || (paramString.equals("codoon")) || (paramString.equals("CBL")) || (paramString.equals("CANDY")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("cod_")) || (paramString.equals("CSBS")))
            bool2 = false;
        return bool2;
    }

    public static void setSupportFriends(Context paramContext, String paramString, boolean paramBoolean) {
        StringBuilder localStringBuilder = new StringBuilder();
        AccessoryConfig.setBooleanValue(paramContext, "isSupportAccessoryFriend" + paramString, paramBoolean);
    }

    public int[] getAnimaIconsByType(String paramString) {
        int[] arrayOfInt = new int[2];
        arrayOfInt[0] = R.drawable.seartch_codoon_bleband_1;
        arrayOfInt[1] = R.drawable.seartch_codoon_bleband_2;
        if (paramString.equals("CANDY")) {
            arrayOfInt[0] = R.drawable.seartch_codoon_tangguo_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_tangguo_2;
        }
        if ((paramString.equals("CBL")) || (paramString.startsWith("codoon"))) {
            arrayOfInt[0] = R.drawable.seartch_codoon_bleband_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_bleband_2;
        } else if (paramString.equals("CSBS")) {
            arrayOfInt[0] = R.drawable.seartch_codoon_band_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_band_2;
        } else if (paramString.equals("CSL")) {
            arrayOfInt[0] = R.drawable.seartch_codoon_smile_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_smile_2;
        } else if (paramString.equals("Aster")) {
            arrayOfInt[0] = R.drawable.seartch_codoon_znwb_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_znwb_2;
        } else if (paramString.equals("ZTECBL")) {
            arrayOfInt[0] = R.drawable.seartch_codoon_bleband_1;
            arrayOfInt[1] = R.drawable.seartch_codoon_bleband_2;
        } else if (paramString.startsWith("cod_mi")) {
            arrayOfInt[0] = R.drawable.device_searching_xm_0;
            arrayOfInt[1] = R.drawable.device_searching_xm_1;
        }
        return arrayOfInt;
    }

    public List<Accessory> getCodoonRomDeviceList() {
        ArrayList localArrayList = new ArrayList();
        Accessory localAccessory1 = new Accessory();
        localAccessory1.mDeviceType = "cod_mi";
        localAccessory1.name = getDeviceNameByType(localAccessory1.mDeviceType);
        localAccessory1.id = "19";
        localAccessory1.isRom = true;
        localAccessory1.describe = getDeviceDesByType(localAccessory1.mDeviceType);
        localArrayList.add(localAccessory1);
        Accessory localAccessory2 = new Accessory();
        localAccessory2.mDeviceType = "codoon";
        localAccessory2.name = getDeviceNameByType(localAccessory2.mDeviceType);
        localAccessory2.id = "17";
        localAccessory2.describe = getDeviceDesByType(localAccessory2.mDeviceType);
        localAccessory2.isRom = true;
        localArrayList.add(localAccessory2);
        return localArrayList;
    }

    public Accessory getCurAccessory() {
        Accessory localAccessory = new Accessory();
        localAccessory.id = this.mSharedPreferences.getString("BindTypeId", "");
        localAccessory.version = this.mSharedPreferences.getString("BindTypeVersion", "0");
        localAccessory.mDeviceType = this.mSharedPreferences.getString("BindTypeName", "");
        localAccessory.address = this.mSharedPreferences.getString("Address", "");
        localAccessory.name = getDeviceNameByType(localAccessory.mDeviceType);
        localAccessory.icon = getDeviceIconByType(localAccessory.mDeviceType);
        localAccessory.describe = getDeviceDesByType(localAccessory.mDeviceType);
        localAccessory.version_describe = this.mSharedPreferences.getString("BindVersionDes", "");
        return localAccessory;
    }

    public String getDeviceBroadNameByAddr(String paramString) {
        return AccessoryConfig.getStringValue(this.mContext, paramString);
    }

    public int getDeviceFindIconByType(String paramString) {
        int i = R.drawable.ic_find_codoon_device;
        if (paramString == null) i = 0;

        if (paramString.startsWith("cod_mi"))
            i = R.drawable.ic_xm_large;
        return i;
    }

    public Drawable getDeviceIconByType(String paramString) {
        Drawable localDrawable;
        if (paramString == null)
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.default_ble_device);


        if ((paramString.equals("CBL")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("Smartband")) || (paramString.startsWith("codoon")))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_codoon_znsh2);
        else if ((paramString.equals("Aster")) || (paramString.equals("Aster")))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_codoon_znwb);
        else if (paramString.equals("CSL"))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_codoon_znwb);
        else if (paramString.equals("CANDY"))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_codoon_tg);
        else if (paramString.equals("CSBS"))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_codoon_sh_s);
        else if (paramString.equals("cod_mi"))
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.ic_xm_small);
        else
            localDrawable = this.mContext.getResources().getDrawable(R.drawable.default_ble_device);
        return localDrawable;
    }

    public int getDeviceLargeIconByType(String paramString) {
        int i = R.drawable.ic_codoon_znwb_h;
        if (paramString == null) i = 0;


        if ((paramString.equals("CBL")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("Smartband")) || (paramString.equals("codoon")))
            i = R.drawable.icon_codoon_bleband_h;
        else if ((paramString.equals("Aster")) || (paramString.equals("Aster")))
            i = R.drawable.ic_codoon_znwb_h;
        else if (paramString.equals("CSL"))
            i = R.drawable.ic_codoon_znwb_h;
        else if (paramString.equals("CANDY"))
            i = R.drawable.icon_codoon_tangguo_h;
        else if (paramString.equals("CSBS"))
            i = R.drawable.icon_codoon_ban_h;
        else if (paramString.startsWith("cod_mi"))
            i = R.drawable.ic_xm_large;
        else
            i = R.drawable.ic_codoon_znwb_h;
        return i;
    }

    public String getDeviceNameByID(String paramString) {
        String str1 = this.mContext.getString(R.string.device_codoon_health_default);
        String str2;
        if (paramString == null) {
            str2 = str1;
            return str2;
        }
        if (paramString.startsWith("13-"))
            str1 = this.mContext.getString(R.string.device_codoon_candy);

        if (paramString.startsWith("17-")) {
            str1 = this.mContext.getString(R.string.device_codoon_bandBLE);
        } else if (paramString.startsWith("12-")) {
            str1 = this.mContext.getString(R.string.device_codoon_band);
        } else if (paramString.startsWith("16-")) {
            str1 = this.mContext.getString(R.string.device_codoon_smile);
        } else if (paramString.startsWith("18-")) {
            str1 = this.mContext.getString(R.string.device_codoon_znwb);
        } else if (paramString.startsWith("164-")) {
            str1 = this.mContext.getString(R.string.device_codoon_mtk);
        } else if (paramString.startsWith("165-")) {
            str1 = this.mContext.getString(R.string.device_codoon_zte);
        } else if (paramString.startsWith("168-")) {
            String str3 = this.mSharedPreferences.getString("BindDeviceNum", "");
            StringBuilder localStringBuilder = new StringBuilder();
            str1 = this.mContext.getString(R.string.device_codoon_lenovo) + str3;
        } else if (paramString.startsWith("19-")) {
            str1 = this.mContext.getString(R.string.device_codoon_mi);
        }
        return str1;
    }

    public String getDeviceNameByType(String paramString) {
        String localString;
        if (paramString == null) {
            localString = null;
            return localString;
        }
        String str1;
        if (paramString.equals("CANDY"))
            str1 = this.mContext.getString(R.string.device_codoon_candy);
        if (paramString.equals("CBL")) {
            str1 = this.mContext.getString(R.string.device_codoon_bandBLE);
        } else if (paramString.equals("codoon")) {
            str1 = this.mContext.getString(R.string.device_codoon_weixin_band);
        } else if (paramString.equals("CSBS")) {
            str1 = this.mContext.getString(R.string.device_codoon_band);
        } else if (paramString.equals("CSL")) {
            str1 = this.mContext.getString(R.string.device_codoon_smile);
        } else if (paramString.equals("Aster")) {
            str1 = this.mContext.getString(R.string.device_codoon_znwb);
        } else if (paramString.equals("ZTECBL")) {
            str1 = this.mContext.getString(R.string.device_codoon_zte);
        } else if (paramString.equals("Aster")) {
            str1 = this.mContext.getString(R.string.device_codoon_mtk);
        } else if (paramString.equals("cod_mi")) {
            str1 = this.mContext.getString(R.string.device_codoon_mi);
        } else if (paramString.startsWith("Smartband")) {
            String str2 = this.mSharedPreferences.getString("BindDeviceNum", "");
            StringBuilder localStringBuilder = new StringBuilder();
            str1 = this.mContext.getString(R.string.device_codoon_lenovo) + " " + str2;
        } else {
            str1 = paramString;
        }
        return str1;
    }

    public String getErrResonByMsgType(int paramInt) {
        switch (paramInt) {
            case ERR_CONNECT_INTERRUPT:

            case ERR_TIME_OUT:
            case ERR_NO_DEVICE:
            case ERR_NOT_MATCH:
            case ERR_NOT_SUPPORT:
            default:
        }
        return null;
    }

    public long getLastSyncTime() {
        return AccessoryConfig.getLongValue(this.mContext, "last_sync_accessory", 0L).longValue();
    }

    public AccessoriesTotal getTotalSpotsDatas(String paramString1, String paramString2, long[] paramArrayOfLong) {
        AccessoriesTotal localAccessoriesTotal1 = new AccessoriesTotal();
        if ((paramArrayOfLong == null) || (paramArrayOfLong.length < 13)) ;

        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l1 = paramArrayOfLong[5];
        long l2 = 1000L * (60L * paramArrayOfLong[6]);
        long l3 = l1 + l2;
        int i = (int) paramArrayOfLong[8];
        int j = (int) paramArrayOfLong[7];
        int k = (int) paramArrayOfLong[9];
//        UserBaseInfo localUserBaseInfo = UserData.GetInstance(this.mContext).GetUserBaseInfo();
        localAccessoriesTotal1.calories = i;
        localAccessoriesTotal1.steps = j;
        localAccessoriesTotal1.meters = k;
        localAccessoriesTotal1.product_id = paramString1;
        Date localDate1 = new Date(l1);
        localAccessoriesTotal1.start_time = localSimpleDateFormat.format(localDate1);
        Date localDate2 = new Date(l3);
        localAccessoriesTotal1.end_time = localSimpleDateFormat.format(localDate2);
        localAccessoriesTotal1.total_time = l2;
        localAccessoriesTotal1.user_id = "1111";
        localAccessoriesTotal1.product_name = getDeviceNameByType(paramString2);
        localAccessoriesTotal1.endLongtime = l3;
        StringBuilder localStringBuilder = new StringBuilder();
        Log.i("AccessoryManager", "start_time:" + localAccessoriesTotal1.start_time + " endTime:" + localAccessoriesTotal1.end_time + "steps:" + j + " calories(daka):" + i + " distance: " + k + " total:" + l2 / 60000L + " min ");
        return localAccessoriesTotal1;
    }

    public int[] getUserColockInfo() {
       /* UserBaseInfo localUserBaseInfo = UserData.GetInstance(this.mContext).GetUserBaseInfo();
        ActivityRemindDAO localActivityRemindDAO = new ActivityRemindDAO(this.mContext);
        ActivityRemindObject localActivityRemindObject = localActivityRemindDAO.getByUserId(localUserBaseInfo.id);
        int i = Integer.parseInt(localActivityRemindObject.begin_time, 16);
        int j = Integer.parseInt(localActivityRemindObject.end_time, 16);
        int k = localActivityRemindObject.interval;
        StringBuilder localStringBuilder1 = new StringBuilder(localActivityRemindObject.week_day);
        int m = Integer.parseInt(localStringBuilder1.reverse().toString(), 2);
        int n;
        int i1;
        AlarmClockObject localAlarmClockObject = null;
        int i2;
        int i3;
        int i4 = 0;
        StringBuilder localStringBuilder2 = null;
        int i5;
        int i6;
        int i7 = 0;
        int[] arrayOfInt = new int[0];
        if (localActivityRemindObject.switch_state == 1) {
            n = 127;
            i1 = n;
            AlarmClockDAO localAlarmClockDAO = new AlarmClockDAO(this.mContext);
            localAlarmClockObject = localAlarmClockDAO.getByUserId(localUserBaseInfo.id);
            String str = localAlarmClockObject.wakeup_time;
            i2 = Integer.parseInt(str.split(":")[0], 16);
            i3 = Integer.parseInt(str.split(":")[1], 16);
            i4 = localAlarmClockObject.wakeup_period;
            localStringBuilder2 = new StringBuilder(localAlarmClockObject.week_day);
            i5 = Integer.parseInt(localStringBuilder2.reverse().toString(), 2);
            if (localAlarmClockObject.switch_state != 1) {
                i6 = 127;
                i7 = i6;
            }


            if (!isTurnFriends(getCurAccessory().address))
                ;


            int i8 = 0;
            arrayOfInt = getClockUpdateInfo(i, j, k, m, i1, i2, i3, i4, i5, 127, i7, i8);
            StringBuilder localStringBuilder3 = new StringBuilder();
            for (int i9 = 0; i9 < arrayOfInt.length; i9++) {
                localStringBuilder3.append(Integer.toHexString(arrayOfInt[i9]) + " ");
            }
        }

        Log.d("AccessoryManager", localActivityRemindObject.begin_time + " " + localActivityRemindObject.end_time + " " + k + " " + localAlarmClockObject.wakeup_time + " " + i4 + " " + localStringBuilder2.toString());
        return arrayOfInt;*/
        Toast.makeText(mContext, "getUserColockInfo", Toast.LENGTH_LONG);
        return new int[2];
    }

    public boolean isBindAccessory() {
        return this.mSharedPreferences.getBoolean("IsBindDevice", false);
    }

    public boolean isDataAvailable(AccessoryValues paramAccessoryValues) {
        boolean bool = false;
        if (paramAccessoryValues != null) {
            if (0L + paramAccessoryValues.calories + paramAccessoryValues.distances + paramAccessoryValues.steps + paramAccessoryValues.sleepmins + paramAccessoryValues.deepSleep + paramAccessoryValues.light_sleep + paramAccessoryValues.wake_time + paramAccessoryValues.sport_duration + paramAccessoryValues.sleep_endTime > 0L)
                bool = true;
//            Log.d("AccessoryManager", "all datas is 0");
        }
        return bool;
    }

    public boolean isDataAvailable(long[] paramArrayOfLong) {
        boolean bool = false;
        if ((paramArrayOfLong != null) && (paramArrayOfLong.length > 10)) {
            long l = 0L;
            for (int i = 0; i < paramArrayOfLong.length; i++)
                if ((i != 0) && (i != 5))
                    l += paramArrayOfLong[i];
            if (l > 0L)
                bool = true;
//            Log.d("AccessoryManager", "all datas is 0");
        }
        return bool;
    }

    public boolean isRightDeviceByID(String paramString1, String paramString2) {
        boolean bool1 = false, bool2 = true;
        if ((paramString2 == null) || (paramString1 == null)) {
            return bool1;
        }

        if (paramString1.equals("CSBS") && !paramString2.startsWith("12-"))
            bool2 = false;


        if ((paramString1.equals("CANDY")) && (!paramString2.startsWith("13-")))
            bool2 = false;
        return bool2;
    }

    public boolean isTurnFriends(String paramString) {
        Context localContext = this.mContext;
        StringBuilder localStringBuilder = new StringBuilder();
        return AccessoryConfig.getBooleanValue(localContext, "isAccessoryCanFriend" + paramString, false);
    }

   /* public void saveToDB(HashMap<String, AccessoryValues> paramHashMap) {
        if ((paramHashMap == null) || (paramHashMap.size() == 0))
            return;
            String str1 = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
            SportDataCurrentDayDAO localSportDataCurrentDayDAO = new SportDataCurrentDayDAO(this.mContext);
            new SportStatisticsDataDAO(this.mContext);
            Iterator localIterator = paramHashMap.keySet().iterator();
            while (localIterator.hasNext()) {
                String str2 = (String) localIterator.next();
                if (!str2.startsWith("1970")) {
                    AccessoryValues localAccessoryValues = (AccessoryValues) paramHashMap.get(str2);
                    if (isDataAvailable(localAccessoryValues)) {
                        AcessoryDailyDataTable localAcessoryDailyDataTable = new AcessoryDailyDataTable();
                        localAcessoryDailyDataTable.calories = localAccessoryValues.calories;
                        localAcessoryDailyDataTable.deepSleep = localAccessoryValues.deepSleep;
                        localAcessoryDailyDataTable.distances = localAccessoryValues.distances;
                        localAcessoryDailyDataTable.light_sleep = localAccessoryValues.light_sleep;
                        localAcessoryDailyDataTable.sleep_endTime = localAccessoryValues.sleep_endTime;
                        localAcessoryDailyDataTable.wake_time = localAccessoryValues.wake_time;
                        localAcessoryDailyDataTable.sleep_startTime = localAccessoryValues.sleep_startTime;
                        localAcessoryDailyDataTable.sleepmins = localAccessoryValues.sleepmins;
                        localAcessoryDailyDataTable.sport_duration = localAccessoryValues.sport_duration;
                        localAcessoryDailyDataTable.start_sport_time = localAccessoryValues.start_sport_time;
                        localAcessoryDailyDataTable.steps = localAccessoryValues.steps;
                        localAcessoryDailyDataTable.time = str2;
                        localAcessoryDailyDataTable.userid = str1;
                        localAcessoryDailyDataTable.sport_mode = 0;
                        long l = localSportDataCurrentDayDAO.insert(localAcessoryDailyDataTable);
                        StringBuilder localStringBuilder = new StringBuilder();
                        Log.d("enlong", "insert sleep start time:" + DateTimeHelper.get_yMdHms_String(localAccessoryValues.sleep_startTime) + " sleep over " + DateTimeHelper.get_yMdHms_String(localAccessoryValues.sleep_endTime) + " result:" + l);
                    }
                }
        }
    }*/

   /* public void saveToQQ(Set<String> paramSet) {
        Iterator localIterator = paramSet.iterator();
        String str1 = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        while (localIterator.hasNext()) {
            String str2 = (String) localIterator.next();
            CachedHttpUtil localCachedHttpUtil = CachedHttpUtil.getInstance(this.mContext);
            HeartBean localHeartBean = HeartRateHelper.getHeartRateByDate(this.mContext, str2, str1);
            if (localHeartBean != null) {
                localHeartBean.user_id = str1;
                localHeartBean.the_day = str2;
                StringBuilder localStringBuilder = new StringBuilder();
                CLog.d("AccessoryManager", "time:" + str2);
                localHeartBean.product_id = getCurAccessory().id;
                CachedHttpTask localCachedHttpTask = BuildTaskUtil.buildHeartRateTask(this.mContext, localHeartBean);
                if (localCachedHttpTask != null)
                    localCachedHttpUtil.addTask(localCachedHttpTask);
            }
        }
    }*/

    public void saveVersion(String paramString) {
        this.mSharedPreferences.edit().putString("BindTypeVersion", paramString).commit();
    }

    public void saveVersion(String paramString1, String paramString2) {
        Editor localEditor = this.mSharedPreferences.edit();
        localEditor.putString("BindTypeVersion", paramString1);
        localEditor.putString("BindVersionDes", paramString2);
        localEditor.commit();
    }

    public void setBindAccessory(boolean paramBoolean) {
        Editor localEditor = this.mSharedPreferences.edit();
        localEditor.putBoolean("IsBindDevice", paramBoolean);
        localEditor.commit();
    }

    public void setDeviceBroadNameByAddr(String paramString1, String paramString2) {
        AccessoryConfig.setStringValue(this.mContext, paramString1, paramString2);
    }

    public void setIsTurnFriend(String paramString, boolean paramBoolean) {
        Context localContext = this.mContext;
        StringBuilder localStringBuilder = new StringBuilder();
        AccessoryConfig.setBooleanValue(localContext, "isAccessoryCanFriend" + paramString, paramBoolean);
    }

    public void setLastSyncTime(long paramLong) {
        AccessoryConfig.setLongValue(this.mContext, "last_sync_accessory", paramLong);
    }
}