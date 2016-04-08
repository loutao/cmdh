package com.ratio.deviceService.datamanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.ratio.BTDeviceService.R;
import com.ratio.deviceService.accessory.Accessory;
import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.bean.AccessoriesTotal;
import com.ratio.deviceService.data.CLog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
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
                CLog.d("enlong", "friendTurn:" + paramInt12);
            }
            return arrayOfInt;
        }
    }

    private String getDeviceDesByType(String paramString) {
        if (paramString == null)
            return null;
        String str;
        if (("CANDY".equals(paramString)) || ("CSBS".equals(paramString))) {
            str = this.mContext.getString(R.string.device_common_describe);
        }
        while (true) {

            if (paramString.startsWith("Smartband"))
                str = this.mContext.getString(R.string.device_heart_band_des);
            else
                str = this.mContext.getString(R.string.device_ble_describe);
            break;
        }
        return str;
    }

    public static boolean isBLEDevice(String paramString) {
        boolean bool2 = true;
        boolean bool1 = bool2;
        if (paramString != null)
            if (!paramString.equals("CSBS")) {
                bool1 = bool2;
                if (!paramString.equals("CANDY")) ;
            } else {
                bool1 = false;
            }
        return bool1;
    }

    public static boolean isRomDevice(String paramString) {
        if (paramString == null)
            return true;
        if ((paramString.equals("codoon")) || (paramString.startsWith("cod_"))) ;
        for (boolean bool = true; ; bool = false)
            return bool;
    }

    public static boolean isSupportBLEDevice(Context paramContext) {
        return paramContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public static boolean isSupportFriends(Context paramContext, String paramString) {
        if (paramString == null)
            return false;
        return AccessoryConfig.getBooleanValue(paramContext, "isSupportAccessoryFriend" + paramString, false);
    }

    public static boolean isThirdBleDevice(String paramString) {
        if (paramString == null)
            return true;
        int i;
        if ((paramString.equals("CSL")) || (paramString.equals("codoon")) || (paramString.equals("CBL")) || (paramString.equals("CANDY")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("cod_")) || (paramString.equals("CSBS"))) {
            i = 1;
            if (i != 0)
                for (boolean bool = true; ; bool = false) {

                    i = 0;
                    return bool;
                }
        }
        return false;
    }

    public static void setSupportFriends(Context paramContext, String paramString, boolean paramBoolean) {
        AccessoryConfig.setBooleanValue(paramContext, "isSupportAccessoryFriend" + paramString, paramBoolean);
    }

   /* public int[] getAnimaIconsByType(String paramString)
    {
        int[] arrayOfInt = new int[2];
        int[] tmp5_4 = arrayOfInt;
        tmp5_4[0] = 2130839757;
        int[] tmp10_5 = tmp5_4;
        tmp10_5[1] = 2130839758;
        tmp10_5;
        if (paramString.equals("CANDY"))
        {
            arrayOfInt[0] = 2130839761;
            arrayOfInt[1] = 2130839762;
        }
        while (true)
        {
            return arrayOfInt;
            if ((paramString.equals("CBL")) || (paramString.startsWith("codoon")))
            {
                arrayOfInt[0] = 2130839757;
                arrayOfInt[1] = 2130839758;
            }
            else if (paramString.equals("CSBS"))
            {
                arrayOfInt[0] = 2130839755;
                arrayOfInt[1] = 2130839756;
            }
            else if (paramString.equals("CSL"))
            {
                arrayOfInt[0] = 2130839759;
                arrayOfInt[1] = 2130839760;
            }
            else if (paramString.equals("Aster"))
            {
                arrayOfInt[0] = 2130839763;
                arrayOfInt[1] = 2130839764;
            }
            else if (paramString.equals("ZTECBL"))
            {
                arrayOfInt[0] = 2130839757;
                arrayOfInt[1] = 2130839758;
            }
            else if (paramString.startsWith("cod_mi"))
            {
                arrayOfInt[0] = 2130838270;
                arrayOfInt[1] = 2130838271;
            }
        }
    }*/

    public List<Accessory> getCodoonRomDeviceList() {
        ArrayList localArrayList = new ArrayList();
        Accessory localAccessory = new Accessory();
        localAccessory.mDeviceType = "cod_mi";
        localAccessory.name = getDeviceNameByType(localAccessory.mDeviceType);
        localAccessory.id = "19";
        localAccessory.isRom = true;
        localAccessory.describe = getDeviceDesByType(localAccessory.mDeviceType);
        localArrayList.add(localAccessory);
        localAccessory = new Accessory();
        localAccessory.mDeviceType = "codoon";
        localAccessory.name = getDeviceNameByType(localAccessory.mDeviceType);
        localAccessory.id = "17";
        localAccessory.describe = getDeviceDesByType(localAccessory.mDeviceType);
        localAccessory.isRom = true;
        localArrayList.add(localAccessory);
        return localArrayList;
    }

    public Accessory getCurAccessory() {
        Accessory localAccessory = new Accessory();
        localAccessory.id = this.mSharedPreferences.getString("BindTypeId", "");
        localAccessory.version = this.mSharedPreferences.getString("BindTypeVersion", "0");
        localAccessory.mDeviceType = this.mSharedPreferences.getString("BindTypeName", "");
        localAccessory.address = this.mSharedPreferences.getString("Address", "");
        localAccessory.name = getDeviceNameByType(localAccessory.mDeviceType);
//        localAccessory.icon = getDeviceIconByType(localAccessory.mDeviceType);
        localAccessory.describe = getDeviceDesByType(localAccessory.mDeviceType);
        localAccessory.version_describe = this.mSharedPreferences.getString("BindVersionDes", "");
        return localAccessory;
    }

    public String getDeviceBroadNameByAddr(String paramString) {
        return AccessoryConfig.getStringValue(this.mContext, paramString);
    }

    public int getDeviceFindIconByType(String paramString) {
        int i = R.drawable.ic_find_codoon_device;
        if (paramString == null) return 0;
        while (true) {

            if (paramString.startsWith("cod_mi"))
                i = R.drawable.ic_xm_large;
            break;
        }
        return i;
    }

   /* public Drawable getDeviceIconByType(String paramString)
    {
        if (paramString == null)
            paramString = this.mContext.getResources().getDrawable(2130838265);
        while (true)
        {
            return paramString;
            if ((paramString.equals("CBL")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("Smartband")) || (paramString.startsWith("codoon")))
                paramString = this.mContext.getResources().getDrawable(2130838464);
            else if ((paramString.equals("Aster")) || (paramString.equals("Aster")))
                paramString = this.mContext.getResources().getDrawable(2130838465);
            else if (paramString.equals("CSL"))
                paramString = this.mContext.getResources().getDrawable(2130838465);
            else if (paramString.equals("CANDY"))
                paramString = this.mContext.getResources().getDrawable(2130838463);
            else if (paramString.equals("CSBS"))
                paramString = this.mContext.getResources().getDrawable(2130838462);
            else if (paramString.equals("cod_mi"))
                paramString = this.mContext.getResources().getDrawable(2130838963);
            else
                paramString = this.mContext.getResources().getDrawable(2130838265);
        }
    }*/


    /*public int getDeviceLargeIconByType(String paramString)
    {
        int i = 2130838466;
        if (paramString == null);
        while (true)
        {
            return i;
            if ((paramString.equals("CBL")) || (paramString.equals("ZTECBL")) || (paramString.startsWith("Smartband")) || (paramString.equals("codoon")))
                i = 2130839019;
            else if ((paramString.equals("Aster")) || (paramString.equals("Aster")))
                i = 2130838466;
            else if (paramString.equals("CSL"))
                i = 2130838466;
            else if (paramString.equals("CANDY"))
                i = 2130839021;
            else if (paramString.equals("CSBS"))
                i = 2130839018;
            else if (paramString.startsWith("cod_mi"))
                i = 2130838962;
            else
                i = 2130838466;
        }
    }*/

    public String getDeviceNameByID(String paramString) {
        String str = this.mContext.getString(R.string.device_codoon_health_default);
        if (paramString == null)
            return str;
        if (paramString.startsWith("13-"))
            str = this.mContext.getString(R.string.device_codoon_candy);
        while (true) {

            if (paramString.startsWith("17-")) {
                str = this.mContext.getString(R.string.device_codoon_bandBLE);
            } else if (paramString.startsWith("12-")) {
                str = this.mContext.getString(R.string.device_codoon_band);
            } else if (paramString.startsWith("16-")) {
                str = this.mContext.getString(R.string.device_codoon_smile);
            } else if (paramString.startsWith("18-")) {
                str = this.mContext.getString(R.string.device_codoon_znwb);
            } else if (paramString.startsWith("164-")) {
                str = this.mContext.getString(R.string.device_codoon_mtk);
            } else if (paramString.startsWith("165-")) {
                str = this.mContext.getString(R.string.device_codoon_zte);
            } else if (paramString.startsWith("168-")) {
                paramString = this.mSharedPreferences.getString("BindDeviceNum", "");
                str = this.mContext.getString(R.string.device_codoon_lenovo) + paramString;
            } else if (paramString.startsWith("19-")) {
                str = this.mContext.getString(R.string.device_codoon_mi);
            }
            return str;
        }
    }

    public String getDeviceNameByType(String paramString) {
        if (paramString == null)
            return null;
        if (paramString.equals("CANDY"))
            paramString = this.mContext.getString(R.string.device_codoon_candy);
        while (true) {

            if (paramString.equals("CBL")) {
                paramString = this.mContext.getString(R.string.device_codoon_bandBLE);
            } else if (paramString.equals("codoon")) {
                paramString = this.mContext.getString(R.string.device_codoon_weixin_band);
            } else if (paramString.equals("CSBS")) {
                paramString = this.mContext.getString(R.string.device_codoon_band);
            } else if (paramString.equals("CSL")) {
                paramString = this.mContext.getString(R.string.device_codoon_smile);
            } else if (paramString.equals("Aster")) {
                paramString = this.mContext.getString(R.string.device_codoon_znwb);
            } else if (paramString.equals("ZTECBL")) {
                paramString = this.mContext.getString(R.string.device_codoon_zte);
            } else if (paramString.equals("Aster")) {
                paramString = this.mContext.getString(R.string.device_codoon_mtk);
            } else if (paramString.equals("cod_mi")) {
                paramString = this.mContext.getString(R.string.device_codoon_mi);
            } else if (paramString.startsWith("Smartband")) {
                paramString = this.mSharedPreferences.getString("BindDeviceNum", "");
                paramString = this.mContext.getString(R.string.device_codoon_lenovo) + " " + paramString;
            }
            return paramString;
        }
    }

   /* public String getErrResonByMsgType(int paramInt)
    {
        switch (paramInt)
        {
            case 254:

            case 255:
            case 251:
            case 253:
            case 252:
            default:
        }
        while (true)
        {
            return null;
            continue;
            continue;
        }
    }*/

    public long getLastSyncTime() {
        return AccessoryConfig.getLongValue(this.mContext, "last_sync_accessory", 0L).longValue();
    }

    public AccessoriesTotal getTotalSpotsDatas(String paramString1, String paramString2, long[] paramArrayOfLong) {
        AccessoriesTotal localAccessoriesTotal = new AccessoriesTotal();
        if ((paramArrayOfLong == null) || (paramArrayOfLong.length < 13))
            return localAccessoriesTotal;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l1 = paramArrayOfLong[5];
        long l2 = paramArrayOfLong[6] * 60L * 1000L;
        long l3 = l1 + l2;
        int i = (int) paramArrayOfLong[8];
        int j = (int) paramArrayOfLong[7];
        int k = (int) paramArrayOfLong[9];
//        paramArrayOfLong = UserData.GetInstance(this.mContext).GetUserBaseInfo();
        localAccessoriesTotal.calories = i;
        localAccessoriesTotal.steps = j;
        localAccessoriesTotal.meters = k;
        localAccessoriesTotal.product_id = paramString1;
        localAccessoriesTotal.start_time = localSimpleDateFormat.format(new Date(l1));
        localAccessoriesTotal.end_time = localSimpleDateFormat.format(new Date(l3));
        localAccessoriesTotal.total_time = l2;
//        localAccessoriesTotal.user_id = paramArrayOfLong.id;
        localAccessoriesTotal.product_name = getDeviceNameByType(paramString2);
        localAccessoriesTotal.endLongtime = l3;
        Log.i("AccessoryManager", "start_time:" + localAccessoriesTotal.start_time + " endTime:" + localAccessoriesTotal.end_time + "steps:" + j + " calories(daka):" + i + " distance: " + k + " total:" + l2 / 60000L + " min ");
        return localAccessoriesTotal;
    }

  /*  public int[] getUserColockInfo()
    {
        Object localObject1 = UserData.GetInstance(this.mContext).GetUserBaseInfo();
        ActivityRemindObject localActivityRemindObject = new ActivityRemindDAO(this.mContext).getByUserId(((UserBaseInfo)localObject1).id);
        int i1 = Integer.parseInt(localActivityRemindObject.begin_time, 16);
        int i2 = Integer.parseInt(localActivityRemindObject.end_time, 16);
        int m = localActivityRemindObject.interval;
        int i3 = Integer.parseInt(new StringBuilder(localActivityRemindObject.week_day).reverse().toString(), 2);
        int i;
        Object localObject2;
        int i4;
        int i5;
        int n;
        int i6;
        int j;
        if (localActivityRemindObject.switch_state == 1)
        {
            i = 127;
            localObject1 = new AlarmClockDAO(this.mContext).getByUserId(((UserBaseInfo)localObject1).id);
            localObject2 = ((AlarmClockObject)localObject1).wakeup_time;
            i4 = Integer.parseInt(localObject2.split(":")[0], 16);
            i5 = Integer.parseInt(localObject2.split(":")[1], 16);
            n = ((AlarmClockObject)localObject1).wakeup_period;
            localObject2 = new StringBuilder(((AlarmClockObject)localObject1).week_day);
            i6 = Integer.parseInt(((StringBuilder)localObject2).reverse().toString(), 2);
            if (((AlarmClockObject)localObject1).switch_state != 1)
                break label315;
            j = 127;
            label209: if (!isTurnFriends(getCurAccessory().address))
                break label320;
        }
        int[] arrayOfInt;
        label315: label320: for (int k = 127; ; k = 0)
        {
            arrayOfInt = getClockUpdateInfo(i1, i2, m, i3, i, i4, i5, n, i6, 127, j, k);
            StringBuilder localStringBuilder = new StringBuilder();
            i = 0;
            while (i < arrayOfInt.length)
            {
                localStringBuilder.append(Integer.toHexString(arrayOfInt[i]) + " ");
                i += 1;
            }
            i = 0;
            break;
            j = 0;
            break label209;
        }
        Log.d("AccessoryManager", localActivityRemindObject.begin_time + " " + localActivityRemindObject.end_time + " " + m + " " + ((AlarmClockObject)localObject1).wakeup_time + " " + n + " " + ((StringBuilder)localObject2).toString());
        return arrayOfInt;
    }*/

    public boolean isBindAccessory() {
        return this.mSharedPreferences.getBoolean("IsBindDevice", false);
    }

    public boolean isDataAvailable(AccessoryValues paramAccessoryValues) {
        boolean bool = false;
        if (paramAccessoryValues != null) {
            if (0L + paramAccessoryValues.calories + paramAccessoryValues.distances + paramAccessoryValues.steps + paramAccessoryValues.sleepmins + paramAccessoryValues.deepSleep + paramAccessoryValues.light_sleep + paramAccessoryValues.wake_time + paramAccessoryValues.sport_duration + paramAccessoryValues.sleep_endTime > 0L)
                return true;
            bool = false;
            Log.d("AccessoryManager", "all datas is 0");
        }
        return bool;
    }

    public boolean isDataAvailable(long[] paramArrayOfLong) {
        boolean bool2 = false;
        boolean bool1 = bool2;
        if (paramArrayOfLong != null) {
            bool1 = bool2;
            if (paramArrayOfLong.length > 10) {
                long l1 = 0L;
                int i = 0;
                while (i < paramArrayOfLong.length) {
                    long l2 = l1;
                    if (i != 0) {
                        l2 = l1;
                        if (i != 5)
                            l2 = l1 + paramArrayOfLong[i];
                    }
                    i += 1;
                    l1 = l2;
                }
                if (l1 > 0L)
                    return true;
                bool1 = false;
                Log.d("AccessoryManager", "all datas is 0");
            }
        }
        return bool1;
    }

    public boolean isRightDeviceByID(String paramString1, String paramString2) {
        if ((paramString2 == null) || (paramString1 == null))
            return false;
        boolean bool2 = false;
        if (isBLEDevice(paramString1))
            return true;
        boolean bool1;
        if (paramString1.equals("CSBS")) {
            bool1 = bool2;
            if (paramString2.startsWith("12-"))
                bool1 = true;
        }
        while (true) {

            bool1 = bool2;
            if (paramString1.equals("CANDY")) {
                bool1 = bool2;
                if (paramString2.startsWith("13-"))
                    bool1 = true;
            }
            return bool1;
        }

    }

    public boolean isTurnFriends(String paramString) {
        return AccessoryConfig.getBooleanValue(this.mContext, "isAccessoryCanFriend" + paramString, false);
    }

   /* public void saveToDB(HashMap<String, AccessoryValues> paramHashMap)
    {
        if ((paramHashMap == null) || (paramHashMap.size() == 0))
            return;
        String str1 = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        SportDataCurrentDayDAO localSportDataCurrentDayDAO = new SportDataCurrentDayDAO(this.mContext);
        Object localObject = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        new SportStatisticsDataDAO(this.mContext);
        localObject = paramHashMap.keySet().iterator();
        while (((Iterator)localObject).hasNext())
        {
            String str2 = (String)((Iterator)localObject).next();
            if (!str2.startsWith("1970"))
            {
                AccessoryValues localAccessoryValues = (AccessoryValues)paramHashMap.get(str2);
                if (isDataAvailable(localAccessoryValues))
                {
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
                    Log.d("enlong", "insert sleep start time:" + DateTimeHelper.get_yMdHms_String(localAccessoryValues.sleep_startTime) + " sleep over " + DateTimeHelper.get_yMdHms_String(localAccessoryValues.sleep_endTime) + " result:" + l);
                }
            }
        }
    }

    public void saveToQQ(Set<String> paramSet)
    {
        paramSet = paramSet.iterator();
        String str = UserData.GetInstance(this.mContext).GetUserBaseInfo().id;
        while (paramSet.hasNext())
        {
            Object localObject = (String)paramSet.next();
            CachedHttpUtil localCachedHttpUtil = CachedHttpUtil.getInstance(this.mContext);
            HeartBean localHeartBean = HeartRateHelper.getHeartRateByDate(this.mContext, (String)localObject, str);
            if (localHeartBean != null)
            {
                localHeartBean.user_id = str;
                localHeartBean.the_day = ((String)localObject);
                CLog.d("AccessoryManager", "time:" + (String) localObject);
                localHeartBean.product_id = getCurAccessory().id;
                localObject = BuildTaskUtil.buildHeartRateTask(this.mContext, localHeartBean);
                if (localObject != null)
                    localCachedHttpUtil.addTask((CachedHttpTask)localObject);
            }
        }
    }*/

    public void saveVersion(String paramString) {
        this.mSharedPreferences.edit().putString("BindTypeVersion", paramString).commit();
    }

    public void saveVersion(String paramString1, String paramString2) {
        SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
        localEditor.putString("BindTypeVersion", paramString1);
        localEditor.putString("BindVersionDes", paramString2);
        localEditor.commit();
    }

    public void setBindAccessory(boolean paramBoolean) {
        SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
        localEditor.putBoolean("IsBindDevice", paramBoolean);
        localEditor.commit();
    }

    public void setDeviceBroadNameByAddr(String paramString1, String paramString2) {
        AccessoryConfig.setStringValue(this.mContext, paramString1, paramString2);
    }

    public void setIsTurnFriend(String paramString, boolean paramBoolean) {
        AccessoryConfig.setBooleanValue(this.mContext, "isAccessoryCanFriend" + paramString, paramBoolean);
    }

    public void setLastSyncTime(long paramLong) {
        AccessoryConfig.setLongValue(this.mContext, "last_sync_accessory", paramLong);
    }
}
