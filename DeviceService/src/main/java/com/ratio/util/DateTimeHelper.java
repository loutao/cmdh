package com.ratio.util;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.ratio.deviceService.logic.ConfigManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/25.
 */
public class DateTimeHelper {

        public static int calcAgeByDate(Date paramDate)
        {
            Calendar localCalendar = Calendar.getInstance();
            if (localCalendar.before(paramDate))
                return 0;
            int i = localCalendar.get(Calendar.YEAR);
            int k = localCalendar.get(Calendar.MONTH) + 1;
            int m = localCalendar.get(Calendar.DAY_OF_MONTH);
            localCalendar.setTime(paramDate);
            int j = localCalendar.get(Calendar.YEAR);
            int n = localCalendar.get(Calendar.MONTH);
            int i1 = localCalendar.get(Calendar.DAY_OF_MONTH);
            j = i - j;
            i = j;
            if (k <= n)
            {
                if (k != n)
                    return i;
                i = j;
                if (m >= i1);
            }
           for (i = j - 1; ; i = j - 1)
                return i;
        }

        public static int compareDatayMd(String paramString1, String paramString2)
        {
            long l1 = get_yMd_long(paramString1);
            long l2 = get_yMd_long(paramString2);
            if (l1 < l2)
                return -1;
            if (l1 == l2)
                return 0;
            return 1;
        }

        public static String get24TimeString(long paramLong)
        {
            int i = (int)(paramLong / 3600000L);
            int j = (int)(paramLong % 3600000L / 60000L);
            int k = i % 24;
            String str1;
            String str2 = null;
            StringBuilder localStringBuilder3;
            if (k < 10)
            {
                StringBuilder localStringBuilder1 = new StringBuilder();
                str1 = "0" + k;
                StringBuilder localStringBuilder2 = new StringBuilder();
                str2 = str1 + ":";

                localStringBuilder3 = new StringBuilder();
            }
             StringBuilder localStringBuilder4;
            for (String str3 = str2 + "0" + j; ; str3 = str2 + j)
            {
                str1 = String.valueOf(k);
                localStringBuilder4 = new StringBuilder();
                return str3;
            }



        }

        public static String getChinaCurrentWeekFristDay()
        {
            return get_YYMMDD_W_String(getWeekFristDay(getCurrentDay()));
        }

        public static String getCurrentDay()
        {
            Object localObject = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                ((Calendar)localObject).setTimeInMillis(System.currentTimeMillis());
                localObject = localSimpleDateFormat.format(((Calendar)localObject).getTime());
                return (String)localObject;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return "";
        }

        public static String getCurrentMonth()
        {
            Object localObject = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            try
            {
                ((Calendar)localObject).setTimeInMillis(System.currentTimeMillis());
                localObject = localSimpleDateFormat.format(((Calendar)localObject).getTime());
                return (String)localObject;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return "";
        }

        public static Long getCurrentTime()
        {
            return Long.valueOf(System.currentTimeMillis());
        }

        public static String getCurrentTimeyMdHms()
        {
            Object localObject = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                ((Calendar)localObject).setTimeInMillis(System.currentTimeMillis());
                localObject = localSimpleDateFormat.format(((Calendar)localObject).getTime());
                return (String)localObject;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return "";
        }

        public static String getCurrentWeekFristDay()
        {
            Object localObject = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                ((Calendar)localObject).setTimeInMillis(System.currentTimeMillis());
                ((Calendar)localObject).set(Calendar.DAY_OF_MONTH, ((Calendar)localObject).get(Calendar.DAY_OF_MONTH) - ((Calendar)localObject).get(Calendar.DAY_OF_WEEK) + 2);
                localObject = localSimpleDateFormat.format(((Calendar)localObject).getTime());
                return (String)localObject;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return "";
        }

        public static String getCurrentYMZh()
        {
            Object localObject = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            try
            {
                ((Calendar)localObject).setTimeInMillis(System.currentTimeMillis());
                localObject = localSimpleDateFormat.format(((Calendar)localObject).getTime());
                return (String)localObject;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return "";
        }

        public static List<HashMap<String, String>> getDefaultDates(long paramLong)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM-dd");
            int i = 0;
            HashMap localHashMap;
            while (i <= 15)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.add(Calendar.DAY_OF_MONTH, -i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, localHashMap);
                i += 1;
            }
            i = 1;
            while (i <= 10)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.add(Calendar.DAY_OF_MONTH, i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(localHashMap);
                i += 1;
            }
            return localArrayList;
        }

        public static List<HashMap<String, String>> getDefaultMonths(long paramLong)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM");
            int i = 0;
            HashMap localHashMap;
            while (i <= 6)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.add(Calendar.MONTH, -i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, localHashMap);
                i += 1;
            }
            i = 1;
            while (i <= 5)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.add(Calendar.MONTH, i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(localHashMap);
                i += 1;
            }
            return localArrayList;
        }

        public static List<HashMap<String, String>> getDefaultWeeks(long paramLong)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM-dd");
            int i = 0;
            HashMap localHashMap;
            while (i < 10)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.set(Calendar.DAY_OF_MONTH, localCalendar.get(Calendar.DAY_OF_MONTH) - localCalendar.get(Calendar.DAY_OF_WEEK) + 2 - 7 * i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, localHashMap);
                i += 1;
            }
            i = 1;
            while (i < 6)
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.set(Calendar.DAY_OF_MONTH, localCalendar.get(Calendar.DAY_OF_MONTH) - localCalendar.get(Calendar.DAY_OF_WEEK) + 2 + 7 * i);
                localHashMap = new HashMap();
                localHashMap.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(localHashMap);
                i += 1;
            }
            return localArrayList;
        }

        public static String getDistanceTime(String paramString1, String paramString2, String paramString3)
        {
            String str1 = paramString1.replace("T", " ");
            String str2 = paramString2.replace("T", " ");
            String str3 = paramString3.replace("T", " ");
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long l1 = 0L;
            long l2 = 0L;
            long l3 = 0L;
            String str5 = null;
            String str4 = "";
            try
            {
                Date localDate1 = localSimpleDateFormat.parse(str3);
                Date localDate2 = localSimpleDateFormat.parse(str1);
                Date localDate3 = localSimpleDateFormat.parse(str2);
                long l4 = localDate2.getTime();
                long l5 = localDate3.getTime();
                long l6 = localDate1.getTime();
                long l7;
                StringBuilder localStringBuilder3;
                if (l6 <= l4)
                {
                    l7 = l4 - l6;
                    str4 = "后开始";
                    l1 = l7 / 86400000L;
                    l2 = l7 / 3600000L - 24L * l1;
                    long l8 = l7 / 60000L;
                    l3 = l8 - 60L * (24L * l1) - 60L * l2;
                    if (l1 != 0L){
                        StringBuilder localStringBuilder4 = new StringBuilder();
                    str5 = l3 + "分钟" + str4;}
                    if (l2 != 0L) {
                        StringBuilder localStringBuilder2 = new StringBuilder();
                        str5 = l2 + "小时" + str4;
                    }
                    if (l3 == 0L)
                        localStringBuilder3 = new StringBuilder();
                    return str5;
                }
                else
                {
                    for (str5 = "1分钟" + str4; ; str5 = "结算中")
                    {
                        if ((l6 > l4) && (l6 <= l5))
                        {
                            l7 = l5 - l6;
                            str4 = "后结束";
                            return str5;

                        }
                    }
                }
            }
            catch (ParseException localParseException)
            {
                    localParseException.printStackTrace();
                    StringBuilder localStringBuilder4 = new StringBuilder();
                     str5 = l3 + "分钟" + str4;
                     StringBuilder localStringBuilder2 = new StringBuilder();
                    str5 = l2 + "小时" + str4;
                    StringBuilder localStringBuilder1 = new StringBuilder();
                    str5 = l1 + "天" + str4;
                return null;
            } catch (java.text.ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        public static long getDuration(Date paramDate1, Date paramDate2)
        {
            return paramDate1.getTime() - paramDate2.getTime();
        }

        public static int getGapCount(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                Date localDate1 = localSimpleDateFormat.parse(paramString);
                Date localDate2 = new Date(System.currentTimeMillis());
                Calendar localCalendar1 = Calendar.getInstance();
                localCalendar1.setTime(localDate1);
                localCalendar1.set(Calendar.HOUR_OF_DAY, 0);
                localCalendar1.set(Calendar.MINUTE, 0);
                localCalendar1.set(Calendar.SECOND, 0);
                localCalendar1.set(Calendar.MILLISECOND, 0);
                Calendar localCalendar2 = Calendar.getInstance();
                localCalendar2.setTime(localDate2);
                localCalendar2.set(Calendar.HOUR_OF_DAY, 0);
                localCalendar2.set(Calendar.MINUTE, 0);
                localCalendar2.set(Calendar.SECOND, 0);
                localCalendar2.set(Calendar.MILLISECOND, 0);
                return                 (int)((localCalendar2.getTime().getTime() - localCalendar1.getTime().getTime()) / 86400000L);

            }
            catch (Exception localException)
            {
               return 0;
            }
        }
/*

    public static String getLastWeekDuring()
    {
        Long localLong1 = Long.valueOf(getWeekFristDay(getCurrentDay()));
        Long localLong2 = getNextDayByDay(localLong1, -7);
        Long localLong3 = getNextDayByDay(localLong1, -1);
        StringBuilder localStringBuilder = new StringBuilder();
        return get_china_MMDD_String(localLong2.longValue()) + "-" + get_china_MMDD_String(localLong3.longValue());
    }
*/

  /*  public static String getNextDay(String paramString, int paramInt)
    {
        Calendar localCalendar = Calendar.getInstance();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try
        {
            localCalendar.setTime(localSimpleDateFormat.parse(paramString));
            localCalendar.add(Calendar.MONTH, paramInt);
            return localSimpleDateFormat.format(localCalendar.getTime());
        }
        catch (ParseException localParseException)
        {
                localParseException.printStackTrace();
            return null;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return null;
        }

    }*/

     /*   public static String getLastWeekDuring()
        {
            Long localLong2 = Long.valueOf(getWeekFristDay(getCurrentDay()));
            Long localLong1 = getNextDayByDay(localLong2, -7);
            localLong2 = getNextDayByDay(localLong2, -1);
            return get_china_MMDD_String(localLong1.longValue()) + "-" + get_china_MMDD_String(localLong2.longValue());
        }*/

      /*  public static String getNextDay(String paramString, int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                localCalendar.add(2, paramInt);
                return localSimpleDateFormat.format(localCalendar.getTime());
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static Long getNextDayByDay(Long paramLong, int paramInt)
        {
            return Long.valueOf(get_yMd_long(getNextDayByDay(get_yMd_String(paramLong.longValue()), paramInt)));
        }

        public static String getNextDayByDay(String paramString, int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                localCalendar.add(5, paramInt);
                return localSimpleDateFormat.format(localCalendar.getTime());
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String getNextMonth(String paramString, int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                localCalendar.add(2, paramInt);
                return localSimpleDateFormat.format(localCalendar.getTime());
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static long getNextYear(long paramLong, int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(new Date(paramLong));
            localCalendar.add(1, paramInt);
            return localCalendar.getTimeInMillis();
        }

        public static String getNextYear(String paramString, int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                localCalendar.add(1, paramInt);
                return localSimpleDateFormat.format(localCalendar.getTime());
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String[] getNowDateStringArray()
        {
            Date localDate = new Date();
            return new SimpleDateFormat("HH mm ss").format(localDate).split(" ");
        }

        public static String getPaceAllTime(int paramInt)
        {
            int i = paramInt / 60;
            paramInt %= 60;
            if (i < 10)
            {
                str = "" + "0" + i;
                str = str + "'";
                if (paramInt >= 10)
                    break label137;
            }
            label137: for (String str = str + "0" + paramInt; ; str = str + String.valueOf(paramInt))
            {
                return str + "''";
                str = "" + String.valueOf(i);
                break;
            }
        }

        public static String getPaceTime(int paramInt)
        {
            int i = paramInt / 60;
            paramInt %= 60;
            int j = i / 60;
            String str;
            if (j > 0)
            {
                str = "" + String.valueOf(j);
                str = str + "h";
                paramInt = i % 60;
                if (paramInt < 10)
                {
                    str = str + "0" + paramInt;
                    str = str + "'";
                }
            }
            while (true)
            {
                return str;
                str = str + String.valueOf(paramInt);
                break;
                if (i > 0)
                {
                    str = "" + String.valueOf(i);
                    str = str + "'";
                    if (paramInt < 10);
                    for (str = str + "0" + paramInt; ; str = str + String.valueOf(paramInt))
                    {
                        str = str + "''";
                        break;
                    }
                }
                str = "" + String.valueOf(paramInt);
                str = str + "''";
            }
        }

        public static List<HashMap<String, String>> getRealDates(String paramString)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM-dd");
            long l3 = System.currentTimeMillis();
            long l2 = getGapCount(paramString);
            long l1 = l2;
            if (l2 < 15L)
                l1 = 15L;
            int i = 0;
            while (i <= l1)
            {
                localCalendar.setTimeInMillis(l3);
                localCalendar.add(5, -i);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, paramString);
                i += 1;
            }
            i = 1;
            while (i <= 15)
            {
                localCalendar.setTimeInMillis(l3);
                localCalendar.add(5, i);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(paramString);
                i += 1;
            }
            return localArrayList;
        }

        public static List<HashMap<String, String>> getRealMonths(String paramString)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM");
            long l = System.currentTimeMillis();
            int j = getGapCount(paramString) / 30 + 1;
            int i = j;
            if (j < 6)
                i = 6;
            j = 0;
            while (j <= i)
            {
                localCalendar.setTimeInMillis(l);
                localCalendar.add(2, -j);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, paramString);
                j += 1;
            }
            i = 1;
            while (i <= 5)
            {
                localCalendar.setTimeInMillis(l);
                localCalendar.add(2, i);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(paramString);
                i += 1;
            }
            return localArrayList;
        }

        public static List<HashMap<String, String>> getRealWeeks(String paramString)
        {
            ArrayList localArrayList = new ArrayList();
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("MM-dd");
            long l = System.currentTimeMillis();
            int j = getGapCount(paramString) / 7 + 1;
            int i = j;
            if (j < 10)
                i = 10;
            j = 0;
            while (j < i)
            {
                localCalendar.setTimeInMillis(l);
                localCalendar.set(5, localCalendar.get(5) - localCalendar.get(7) + 2 - 7 * j);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(0, paramString);
                j += 1;
            }
            i = 1;
            while (i < 6)
            {
                localCalendar.setTimeInMillis(l);
                localCalendar.set(5, localCalendar.get(5) - localCalendar.get(7) + 2 + 7 * i);
                paramString = new HashMap();
                paramString.put(localSimpleDateFormat1.format(localCalendar.getTime()), localSimpleDateFormat2.format(localCalendar.getTime()));
                localArrayList.add(paramString);
                i += 1;
            }
            return localArrayList;
        }

        public static String getSportAllTime(int paramInt)
        {
            paramInt /= 60;
            int i = paramInt / 60;
            Object localObject2 = "";
            Object localObject1 = localObject2;
            if (i != 0)
            {
                if (i < 10)
                {
                    localObject1 = (String)localObject2 + "0" + i;
                    localObject1 = (String)localObject1 + "h";
                }
            }
            else
            {
                paramInt %= 60;
                localObject2 = localObject1;
                if (paramInt != 0)
                    if (paramInt >= 10)
                        break label157;
            }
            label157: for (localObject1 = (String)localObject1 + "0" + paramInt; ; localObject1 = (String)localObject1 + String.valueOf(paramInt))
            {
                localObject2 = (String)localObject1 + "'";
                return localObject2;
                localObject1 = (String)localObject2 + String.valueOf(i);
                break;
            }
        }

        public static String getSportHMSpeedTime(long paramLong)
        {
            int i = (int)(paramLong / 60000L);
            int j = (int)(paramLong % 60000L / 1000L);
            int k = i / 60;
            if (k > 0)
            {
                if (k < 10)
                {
                    str = "" + "0" + k;
                    str = str + "h";
                    i %= 60;
                    if (i >= 10)
                        break label175;
                }
                label175: for (str = str + "0" + i; ; str = str + String.valueOf(i))
                {
                    str = str + "'";
                    return str;
                    str = "" + String.valueOf(k);
                    break;
                }
            }
            if (i > 0)
            {
                if (i < 10)
                {
                    str = "" + "0" + i;
                    label238: str = str + "'";
                    if (j >= 10)
                        break label345;
                }
                label345: for (str = str + "0" + j; ; str = str + String.valueOf(j))
                {
                    str = str + "”";
                    break;
                    str = "" + String.valueOf(i);
                    break label238;
                }
            }
            if (j < 10);
            for (String str = "" + "0" + j; ; str = "" + String.valueOf(j))
            {
                str = str + "”";
                break;
            }
        }

        public static String getSportShowTime(long paramLong, boolean paramBoolean)
        {
            int i = (int)(paramLong / 60000L % 60L);
            int j = (int)(paramLong % 60000L / 1000L);
            int k = (int)(paramLong / 3600000L);
            StringBuilder localStringBuilder = new StringBuilder();
            if (k > 0)
                if (k < 10)
                {
                    localStringBuilder.append("0" + k);
                    localStringBuilder.append(":");
                    label86: if (i >= 10)
                        break label194;
                    localStringBuilder.append("0" + i);
                    label117: localStringBuilder.append(":");
                    if (j >= 10)
                        break label207;
                    localStringBuilder.append("0" + j);
                }
            while (true)
            {
                return localStringBuilder.toString();
                localStringBuilder.append(String.valueOf(k));
                break;
                if (!paramBoolean)
                    break label86;
                localStringBuilder.append("00:");
                break label86;
                label194: localStringBuilder.append(String.valueOf(i));
                break label117;
                label207: localStringBuilder.append(String.valueOf(j));
            }
        }

        public static String getSportStamp(long paramLong)
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(paramLong)).replace(" ", "T");
        }

        public static String getStepSpeedHMTime(long paramLong)
        {
            int i = (int)(paramLong / 60000L);
            int j = (int)(paramLong % 60000L / 1000L);
            int k = i / 60;
            String str2 = "";
            if (k > 0)
                if (k < 10)
                {
                    str1 = str2 + "0" + k;
                    str1 = str1 + "h";
                    i %= 60;
                    if (i >= 10)
                        break label179;
                    str1 = str1 + "0" + i;
                    label126: str1 = str1 + "'";
                }
            label179:
            do
            {
                return str1;
                str1 = str2 + String.valueOf(k);
                break;
                str1 = str1 + String.valueOf(i);
                break label126;
                if (i > 0)
                {
                    if (i < 10);
                    for (str1 = str2 + "0" + i; ; str1 = str2 + String.valueOf(i))
                    {
                        str1 = str1 + "'";
                        break;
                    }
                }
                str1 = str2;
            }
            while (k != 0);
            if (j < 10);
            for (String str1 = str2 + "0" + j; ; str1 = str2 + String.valueOf(j))
            {
                str1 = str1 + "'";
                break;
            }
        }

        public static String getStepSpeedTime(long paramLong)
        {
            int i = (int)(paramLong / 60000L);
            int j = (int)(paramLong % 60000L / 1000L);
            if (i < 10)
            {
                str = "" + "0" + i;
                str = str + "'";
                if (j >= 10)
                    break label153;
            }
            label153: for (String str = str + "0" + j; ; str = str + String.valueOf(j))
            {
                return str + "\"";
                str = "" + String.valueOf(i);
                break;
            }
        }

        public static String getStepSpeedTime2(long paramLong)
        {
            int i = (int)(paramLong / 60000L);
            int j = (int)(paramLong % 60000L / 1000L);
            if (i < 10)
            {
                str = "" + "" + i;
                str = str + "分";
                if (j >= 10)
                    break label154;
            }
            label154: for (String str = str + "0" + j; ; str = str + String.valueOf(j))
            {
                return str + "秒";
                str = "" + String.valueOf(i);
                break;
            }
        }

        public static String getStepSpeedTime_tread(long paramLong)
        {
            int i = (int)(paramLong / 60L);
            int j = (int)(paramLong % 60L);
            if (i < 10)
            {
                str = "" + "0" + i;
                str = str + "'";
                if (j >= 10)
                    break label149;
            }
            label149: for (String str = str + "0" + j; ; str = str + String.valueOf(j))
            {
                return str + "\"";
                str = "" + String.valueOf(i);
                break;
            }
        }

        public static long getTimeDate(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                return localCalendar.getTimeInMillis();
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String getTimeString(long paramLong)
        {
            Log.d("longtime", String.valueOf(paramLong));
            Date localDate = new Date(paramLong);
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
            localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("London"));
            return localSimpleDateFormat.format(localDate);
        }

        public static String getTimeStringMin(long paramLong)
        {
            Date localDate = new Date(paramLong);
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("London"));
            return localSimpleDateFormat.format(localDate);
        }

        public static long getTimesmorning(long paramLong)
        {
            return get_yMd_long(get_YYMMDD_W_String(paramLong));
        }

        public static long getTimesnight(long paramLong)
        {
            return get_yMd_long(get_YYMMDD_W_String(paramLong)) + 86400000L;
        }

        public static String getTotalTime2String(long paramLong)
        {
            String str = "";
            int i = (int)(paramLong / 3600000L);
            paramLong %= 3600000L;
            int j = (int)(paramLong / 60000L);
            int k = (int)(paramLong % 60000L / 1000L);
            if (i > 0)
            {
                if (i < 10)
                {
                    str = "0" + i;
                    str = str + "h";
                }
            }
            else
            {
                if (j >= 10)
                    break label207;
                str = str + "0" + j;
                label121: str = str + "'";
                if (k >= 10)
                    break label231;
            }
            label207: label231: for (str = str + "0" + k; ; str = str + k)
            {
                return str + "''";
                str = String.valueOf(i);
                break;
                str = str + j;
                break label121;
            }
        }

        public static String getTotalTime3String(long paramLong)
        {
            int i = (int)(paramLong / 3600000L);
            paramLong %= 3600000L;
            int j = (int)(paramLong / 60000L);
            int k = (int)(paramLong % 60000L / 1000L);
            if (i < 10)
            {
                str = "0" + i;
                str = str + "h";
                if (j >= 10)
                    break label143;
            }
            label143: for (String str = str + "0" + j; ; str = str + j)
            {
                return str + "'";
                str = String.valueOf(i);
                break;
            }
        }

        public static String getTotalTime4String(long paramLong)
        {
            int i = (int)(paramLong / 3600000L);
            paramLong %= 3600000L;
            int j = (int)(paramLong / 60000L);
            int k = (int)(paramLong % 60000L / 1000L);
            if (i < 10)
            {
                str = "0" + i;
                str = str + "小时";
                if (j >= 10)
                    break label143;
            }
            label143: for (String str = str + "0" + j; ; str = str + j)
            {
                return str + "分钟";
                str = String.valueOf(i);
                break;
            }
        }

        public static String getTotalTime4String2(long paramLong)
        {
            Object localObject = "";
            int i = (int)(paramLong / 3600000L);
            paramLong %= 3600000L;
            int j = (int)(paramLong / 60000L);
            int k = (int)(paramLong % 60000L / 1000L);
            label77: String str;
            if (i == 0)
            {
                if (j >= 10)
                    break label290;
                if (j < 1)
                    break label264;
                localObject = (String)localObject + "" + j;
                str = (String)localObject + "分";
                localObject = str;
                if (i == 0)
                {
                    localObject = str;
                    if (j != 0)
                    {
                        localObject = str;
                        if (k > 0)
                        {
                            if (k >= 10)
                                break label319;
                            localObject = str + "" + k;
                        }
                    }
                }
            }
            for (localObject = (String)localObject + "秒"; ; localObject = (String)localObject + "秒")
            {
                return localObject;
                if (i < 10)
                {
                    localObject = "" + i;
                    localObject = (String)localObject + "小时";
                    break;
                }
                localObject = String.valueOf(i) + "小时";
                break;
                label264: localObject = (String)localObject + "1";
                break label77;
                label290: localObject = (String)localObject + "" + j;
                break label77;
                label319: localObject = str + String.valueOf(k);
            }
        }

        public static String getTotalTimeString(long paramLong)
        {
            int i = (int)(paramLong / 3600000L);
            int j = (int)(paramLong % 3600000L / 60000L);
            if (i < 10)
            {
                str = "0" + i;
                str = str + "h";
                if (j >= 10)
                    break label129;
            }
            label129: for (String str = str + "0" + j; ; str = str + j)
            {
                return str + "'";
                str = String.valueOf(i);
                break;
            }
        }

        public static String getTotalTime_chinaString(long paramLong)
        {
            int i = (int)(paramLong / 3600000L);
            int j = (int)(paramLong % 3600000L / 60000L);
            if (i < 10)
            {
                str = "0" + i;
                str = str + "小时";
                if (j >= 10)
                    break label129;
            }
            label129: for (String str = str + "0" + j; ; str = str + j)
            {
                return str + "分钟";
                str = String.valueOf(i);
                break;
            }
        }

        public static String getTreadmillHMSpeedTime(long paramLong)
        {
            int i = (int)(paramLong / 60L);
            int j = (int)paramLong % 60;
            int k = i / 60;
            if (k > 0)
            {
                if (k < 10)
                {
                    str = "" + "0" + k;
                    str = str + "h";
                    i %= 60;
                    if (i >= 10)
                        break label170;
                }
                label170: for (str = str + "0" + i; ; str = str + String.valueOf(i))
                {
                    str = str + "'";
                    return str;
                    str = "" + String.valueOf(k);
                    break;
                }
            }
            if (i > 0)
            {
                if (i < 10)
                {
                    str = "" + "0" + i;
                    label233: str = str + "'";
                    if (j >= 10)
                        break label340;
                }
                label340: for (str = str + "0" + j; ; str = str + String.valueOf(j))
                {
                    str = str + "”";
                    break;
                    str = "" + String.valueOf(i);
                    break label233;
                }
            }
            if (j < 10);
            for (String str = "" + "0" + j; ; str = "" + String.valueOf(j))
            {
                str = str + "”";
                break;
            }
        }

        public static String getWeekDayInterval(int paramInt)
        {
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTimeInMillis(System.currentTimeMillis());
            localCalendar.set(5, localCalendar.get(5) - localCalendar.get(7) + 2 + 7 * paramInt);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(new SimpleDateFormat("yyyy年MM月dd日").format(localCalendar.getTime()));
            localStringBuilder.append("~");
            localCalendar.set(5, localCalendar.get(5) + 6);
            localStringBuilder.append(new SimpleDateFormat("MM月dd日").format(localCalendar.getTime()));
            return localStringBuilder.toString();
        }
*/
        public static long getWeekFristDay(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                int i = localCalendar.get(Calendar.DAY_OF_WEEK);
                localCalendar.set(Calendar.DAY_OF_MONTH, localCalendar.get(Calendar.DAY_OF_MONTH) - localCalendar.get(Calendar.DAY_OF_WEEK) + 2);
                if (i == 1)
                    localCalendar.add(Calendar.DAY_OF_MONTH, -7);
                long l = localCalendar.getTimeInMillis();
                return l;
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return 0L;
        }

      /*  public static long getWeekLastDay(long paramLong)
        {
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTimeInMillis(paramLong);
                localCalendar.add(5, 6);
                paramLong = localCalendar.getTimeInMillis();
                return paramLong;
            }
            catch (Exception localException)
            {
                localException.printStackTrace();
            }
            return 0L;
        }

        public static String getWeekLastDay(String paramString)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try
            {
                localCalendar.setTime(localSimpleDateFormat.parse(paramString));
                localCalendar.add(5, 6);
                paramString = localSimpleDateFormat.format(localCalendar.getTime());
                return paramString;
            }
            catch (Exception paramString)
            {
                paramString.printStackTrace();
            }
            return "";
        }

        public static String getWeekName(Date paramDate)
        {
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTime(paramDate);
                Object localObject = "星期";
                paramDate = (Date)localObject;
                if (localCalendar.get(7) == 1)
                    paramDate = (String)localObject + "天";
                localObject = paramDate;
                if (localCalendar.get(7) == 2)
                    localObject = paramDate + "一";
                paramDate = (Date)localObject;
                if (localCalendar.get(7) == 3)
                    paramDate = (String)localObject + "二";
                localObject = paramDate;
                if (localCalendar.get(7) == 4)
                    localObject = paramDate + "三";
                paramDate = (Date)localObject;
                if (localCalendar.get(7) == 5)
                    paramDate = (String)localObject + "四";
                localObject = paramDate;
                if (localCalendar.get(7) == 6)
                    localObject = paramDate + "五";
                paramDate = (Date)localObject;
                if (localCalendar.get(7) == 7)
                    paramDate = (String)localObject + "六";
                return paramDate;
            }
            catch (Exception paramDate)
            {
                while (true)
                    paramDate.printStackTrace();
            }
        }

        public static long getWeeklyNewInfoTime()
        {
            long l = getWeekFristDay(getCurrentDay());
            int i = new Random().nextInt(7200);
            Long localLong = Long.valueOf(Long.valueOf(Long.valueOf(l).longValue() + 28800000L + (i + 1) * 1000).longValue() - System.currentTimeMillis());
            if (localLong.longValue() > 0L);
            for (l = localLong.longValue(); ; l = localLong.longValue() + 604800000L)
                return System.currentTimeMillis() + Long.valueOf(l).longValue();
        }

        public static long getWeeklyUpdateInfoTime()
        {
            long l = getWeekFristDay(getCurrentDay());
            int i = new Random().nextInt(7200);
            Long localLong = Long.valueOf(Long.valueOf(getNextDayByDay(Long.valueOf(l), 7).longValue() - 28800000L + (i + 1) * 1000).longValue() - System.currentTimeMillis());
            if (localLong.longValue() > 0L);
            for (l = localLong.longValue(); ; l = localLong.longValue() + 604800000L)
                return System.currentTimeMillis() + Long.valueOf(l).longValue();
        }

        public static String getXMLSchemaDate(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(localDate);
        }

        public static long[] getYesterday12TocurDay12(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar localCalendar = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                localCalendar = Calendar.getInstance();
                localCalendar.setTime(paramString);
                long l = localCalendar.getTimeInMillis();
                localCalendar.add(5, 1);
                return new long[] { l - 43200000L, localCalendar.getTimeInMillis() - 43200000L };
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localCalendar;
                }
            }
        }

        public static String get_DD_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("dd").format(localDate);
        }

        public static String get_HHmm_String(long paramLong)
        {
            return new SimpleDateFormat("HH:mm").format(new Date(paramLong));
        }

        public static String get_Hm_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
            localSimpleDateFormat.setTimeZone(TimeZone.getDefault());
            return localSimpleDateFormat.format(localDate);
        }

        public static String get_Hm_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                paramString.printStackTrace();
                return "";
            }
            return new SimpleDateFormat("HH:mm").format(paramString);
        }

        public static String get_Hms_String(long paramLong)
        {
            Object localObject1 = new Date(paramLong);
            Object localObject2 = new SimpleDateFormat("HH:mm’ss”");
            ((SimpleDateFormat)localObject2).setTimeZone(TimeZone.getTimeZone("London"));
            localObject2 = ((SimpleDateFormat)localObject2).format((Date)localObject1);
            localObject1 = localObject2;
            if (localObject2 != null)
            {
                if (Integer.parseInt(((String)localObject2).substring(0, 2)) <= 0)
                    break label87;
                localObject2 = ((String)localObject2).replace(":", "h");
                localObject1 = localObject2;
                if (Integer.parseInt(((String)localObject2).substring(0, 2)) >= 10);
            }
            label87: for (localObject1 = ((String)localObject2).substring(1); ; localObject1 = ((String)localObject2).substring(3))
                return localObject1;
        }

        public static String get_MM_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("MM月").format(localDate);
        }

        public static String get_MdH_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("M月d日 H时").format(localDate);
        }

        public static String get_MdHm2_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("MM-dd HH:mm").format(localDate);
        }

        public static String get_MdHm_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("MM月dd日 HH:mm").format(localDate);
        }

        public static String get_MdHm_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("MM-dd HH:mm").format(paramString);
        }

        public static String get_Md_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("M月d日").format(localDate);
        }

        public static String get_Md_String(String paramString)
        {
            Object localObject = paramString.replace("T", " ");
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            paramString = null;
            try
            {
                localObject = localSimpleDateFormat.parse((String)localObject);
                paramString = (String)localObject;
                return new SimpleDateFormat("MM.dd").format(paramString);
            }
            catch (ParseException localParseException)
            {
                while (true)
                    localParseException.printStackTrace();
            }
        }

        public static String get_MessageSessionItemTime_String(long paramLong)
        {
            Object localObject2 = Calendar.getInstance();
            Object localObject4 = new SimpleDateFormat("yyyy-MM-dd");
            Date localDate = new Date(paramLong);
            Object localObject1 = null;
            Object localObject3 = null;
            try
            {
                localObject2 = ((SimpleDateFormat)localObject4).parse(((SimpleDateFormat)localObject4).format(((Calendar)localObject2).getTime()));
                localObject1 = localObject2;
                localObject4 = ((SimpleDateFormat)localObject4).parse(((SimpleDateFormat)localObject4).format(localDate));
                localObject3 = localObject4;
                localObject1 = localObject2;
                paramLong = (localObject1.getTime() - localObject3.getTime()) / 1000L / 86400L;
                if (paramLong == 0L)
                    return new SimpleDateFormat("今天 ").format(localDate);
            }
            catch (ParseException localParseException)
            {
                while (true)
                    localParseException.printStackTrace();
                if (paramLong == 1L)
                    return new SimpleDateFormat("昨天").format(localDate);
            }
            return new SimpleDateFormat("yyyy年MM月dd日").format(localDate);
        }

        public static String get_TreadMill_MdHm_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("MM月dd日 HH:mm");
            SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                paramString = localSimpleDateFormat1.format(localSimpleDateFormat2.parse(paramString));
                return paramString;
            }
            catch (ParseException paramString)
            {
                paramString.printStackTrace();
            }
            return "";
        }

        public static String get_YYMMDD_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy年MM月dd日").format(localDate);
        }

        public static String get_YYMMDD_String(String paramString)
        {
            Object localObject = paramString;
            paramString = (String)localObject;
            if (localObject != null)
            {
                paramString = (String)localObject;
                if (((String)localObject).indexOf("T") != 0)
                    paramString = ((String)localObject).replace("T", " ");
            }
            localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                paramString = ((SimpleDateFormat)localObject).parse(paramString);
                return new SimpleDateFormat("yyyy-MM-dd").format(paramString);
            }
            catch (ParseException paramString)
            {
                paramString.printStackTrace();
            }
            return "";
        }
*/
        public static String get_YYMMDD_W_String(long paramLong)
        {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(paramLong));
        }

        public static String get_YYMMDD_W_String(String paramString)
        {
            Object localObject = new SimpleDateFormat("yyyy-MM-dd");
            Date localDate = null;
            try
            {
                localDate = ((SimpleDateFormat)localObject).parse(paramString);
                if (localDate == null)
                    return "";
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
                return "";
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTime(localDate);
                localObject = "星期";
                paramString = (String)localObject;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 1)
                    paramString = (String)localObject + "天";
                localObject = paramString;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 2)
                    localObject = paramString + "一";
                paramString = (String)localObject;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 3)
                    paramString = (String)localObject + "二";
                localObject = paramString;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 4)
                    localObject = paramString + "三";
                paramString = (String)localObject;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 5)
                    paramString = (String)localObject + "四";
                localObject = paramString;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 6)
                    localObject = paramString + "五";
                paramString = (String)localObject;
                if (localCalendar.get(Calendar.DAY_OF_WEEK) == 7)
                    paramString = (String)localObject + "六";
                localObject = new SimpleDateFormat("yyyy年MM月dd日");
                return ((SimpleDateFormat)localObject).format(localDate) + "  " + paramString;
            }
            catch (Exception ex)
            {
                    ex.printStackTrace();
            }
            return null;
        }

        public static String get_YYMMDD_W_String(String paramString, int paramInt)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date localDate = null;
            try
            {
                localDate = localSimpleDateFormat.parse(paramString);
                if (localDate == null)
                    return "";
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
                return "";
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            Calendar localCalendar = Calendar.getInstance();
            try
            {
                localCalendar.setTime(localDate);
                localCalendar.add(Calendar.DAY_OF_MONTH, paramInt);
                return localSimpleDateFormat.format(localCalendar.getTime());
            }
            catch (Exception localException)
            {
                    localException.printStackTrace();
            }
            return null;
        }

        public static String get_YYMM_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy年MM月").format(localDate);
        }

        public static String get_YYMM_String(String paramString)
        {
            Object localObject = paramString;
            paramString = (String)localObject;
            if (localObject != null)
            {
                paramString = (String)localObject;
                if (((String)localObject).indexOf("T") != 0)
                    paramString = ((String)localObject).replace("T", " ");
            }
            localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                Date localdate = ((SimpleDateFormat)localObject).parse(paramString);
                return new SimpleDateFormat("yyyy年MM月").format(localdate);
            }
            catch (ParseException ex)
            {
                ex.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return "";
        }

     /*   public static String get_china2_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(localDate);
        }

        public static String get_china2_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy年MM月dd日 HH:mm").format(paramString);
        }

        public static String get_china_MMDD2_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("MM-dd周").format(paramString);
        }

        public static String get_china_MMDD3_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("MM.dd").format(paramString);
        }

        public static String get_china_MMDD_String(long paramLong)
        {
            return get_china_MMDD_String(get_yMd_String(paramLong));
        }

        public static String get_china_MMDD_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("MM月dd日").format(paramString);
        }

        public static String get_china_MM_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("MM月").format(paramString);
        }

        public static String get_china_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                paramString.printStackTrace();
                return "";
            }
            return new SimpleDateFormat("yyyy年MM月dd日").format(paramString);
        }

        public static String get_china_YY2_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy年").format(paramString);
        }

        public static String get_china_YYMMDD_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy年MM月dd日").format(paramString);
        }

        public static String get_china_YYMM_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy年MM月").format(paramString);
        }

        public static String get_china_YY_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy年").format(paramString);
        }

        public static String get_china_yMdHms_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramString);
        }

        public static String get_china_yMdHms_String(String paramString, int paramInt)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar localCalendar = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localCalendar;
                }
                localCalendar = Calendar.getInstance();
                localCalendar.setTime(paramString);
                localCalendar.add(5, paramInt);
                paramString = localCalendar.getTime();
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramString);
        }

        public static String get_china_yMdHms_lastTime_String(String paramString, int paramInt)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar localCalendar = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localCalendar;
                }
                localCalendar = Calendar.getInstance();
                localCalendar.setTime(paramString);
                localCalendar.add(5, paramInt);
                paramString = localCalendar.getTime();
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramString);
        }

        public static String get_china_yMdHms_month_firstDay_String(String paramString, int paramInt)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Calendar localCalendar = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localCalendar;
                }
                localCalendar = Calendar.getInstance();
                localCalendar.setTime(paramString);
                localCalendar.add(2, paramInt);
                paramString = localCalendar.getTime();
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramString);
        }

        public static String get_china_yMdHms_month_lastTime_String(String paramString, int paramInt)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM");
            Calendar localCalendar = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localCalendar;
                }
                localCalendar = Calendar.getInstance();
                localCalendar.setTime(paramString);
                localCalendar.add(2, paramInt);
                paramString = localCalendar.getTime();
            }
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramString);
        }

        public static String get_convasationTime_String(long paramLong)
        {
            Object localObject3 = Calendar.getInstance();
            Object localObject5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject2 = null;
            try
            {
                localObject1 = new Date(paramLong);
                localObject2 = localObject1;
                localObject1 = null;
                localObject4 = null;
            }
            catch (Exception localException)
            {
                try
                {
                    localObject3 = ((SimpleDateFormat)localObject5).parse(((SimpleDateFormat)localObject5).format(((Calendar)localObject3).getTime()));
                    Object localObject1 = localObject3;
                    localObject5 = ((SimpleDateFormat)localObject5).parse(((SimpleDateFormat)localObject5).format(localObject2));
                    Object localObject4 = localObject5;
                    localObject1 = localObject3;
                    i = ((Date)localObject1).getDate() - localObject4.getDate();
                    if (i == 0)
                    {
                        return new SimpleDateFormat("HH:mm").format(localObject2);
                        localException = localException;
                    }
                }
                catch (ParseException localParseException)
                {
                    int i;
                    while (true)
                        localParseException.printStackTrace();
                    if (i == 1)
                    {
                        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm");
                        return "昨天 " + localSimpleDateFormat.format(localObject2);
                    }
                }
            }
            return get_yMdHm_String(paramLong);
        }

        public static String get_dH_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("d日 H时").format(localDate);
        }

        public static String get_dHm_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("dd日 HH:mm").format(localDate);
        }

        public static String get_dHm_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("dd日 HH:mm").format(paramString);
        }

        public static String get_d_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("d日").format(localDate);
        }

        // ERROR //
        public static String get_feedTime_String(String paramString)
        {
            // Byte code:
            //   0: aload_0
            //   1: invokestatic 471	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
            //   4: ifeq +6 -> 10
            //   7: ldc 107
            //   9: areturn
            //   10: invokestatic 17	java/util/Calendar:getInstance	()Ljava/util/Calendar;
            //   13: astore 10
            //   15: new 79	java/text/SimpleDateFormat
            //   18: dup
            //   19: ldc 120
            //   21: invokespecial 84	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
            //   24: astore 9
            //   26: new 79	java/text/SimpleDateFormat
            //   29: dup
            //   30: ldc 120
            //   32: invokespecial 84	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
            //   35: astore 11
            //   37: aload 11
            //   39: aload_0
            //   40: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   43: astore_0
            //   44: aload 9
            //   46: aload 9
            //   48: aload 10
            //   50: invokevirtual 98	java/util/Calendar:getTime	()Ljava/util/Date;
            //   53: invokevirtual 102	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
            //   56: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   59: astore 10
            //   61: aload 9
            //   63: aload 9
            //   65: aload_0
            //   66: invokevirtual 102	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
            //   69: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   72: astore 9
            //   74: invokestatic 90	java/lang/System:currentTimeMillis	()J
            //   77: invokestatic 473	com/codoon/gps/util/DateTimeHelper:getTimesnight	(J)J
            //   80: lstore_3
            //   81: aload 10
            //   83: invokevirtual 180	java/util/Date:getTime	()J
            //   86: aload 9
            //   88: invokevirtual 180	java/util/Date:getTime	()J
            //   91: lsub
            //   92: ldc2_w 266
            //   95: ldiv
            //   96: lstore 5
            //   98: lload 5
            //   100: ldc2_w 411
            //   103: ldiv
            //   104: lstore 7
            //   106: lload_3
            //   107: aload 9
            //   109: invokevirtual 180	java/util/Date:getTime	()J
            //   112: lsub
            //   113: ldc2_w 266
            //   116: ldiv
            //   117: l2f
            //   118: ldc_w 474
            //   121: fdiv
            //   122: fstore_1
            //   123: fload_1
            //   124: fconst_1
            //   125: fcmpg
            //   126: ifgt +82 -> 208
            //   129: lload 5
            //   131: ldc2_w 475
            //   134: ldiv
            //   135: l2i
            //   136: istore_2
            //   137: iload_2
            //   138: iconst_1
            //   139: if_icmplt +32 -> 171
            //   142: new 43	java/lang/StringBuilder
            //   145: dup
            //   146: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   149: iload_2
            //   150: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   153: ldc_w 478
            //   156: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   159: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   162: areturn
            //   163: astore_0
            //   164: ldc 107
            //   166: areturn
            //   167: astore_0
            //   168: ldc 107
            //   170: areturn
            //   171: lload 5
            //   173: ldc2_w 187
            //   176: ldiv
            //   177: l2i
            //   178: istore_2
            //   179: iload_2
            //   180: ifgt +7 -> 187
            //   183: ldc_w 480
            //   186: areturn
            //   187: new 43	java/lang/StringBuilder
            //   190: dup
            //   191: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   194: iload_2
            //   195: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   198: ldc_w 482
            //   201: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   204: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   207: areturn
            //   208: fload_1
            //   209: fconst_1
            //   210: fcmpl
            //   211: ifle +24 -> 235
            //   214: fload_1
            //   215: fconst_2
            //   216: fcmpg
            //   217: ifgt +18 -> 235
            //   220: new 79	java/text/SimpleDateFormat
            //   223: dup
            //   224: ldc_w 416
            //   227: invokespecial 84	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
            //   230: aload_0
            //   231: invokevirtual 102	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
            //   234: areturn
            //   235: fload_1
            //   236: fconst_2
            //   237: fcmpl
            //   238: ifle +33 -> 271
            //   241: fload_1
            //   242: ldc_w 483
            //   245: fcmpg
            //   246: ifge +25 -> 271
            //   249: new 43	java/lang/StringBuilder
            //   252: dup
            //   253: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   256: fload_1
            //   257: f2i
            //   258: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   261: ldc_w 485
            //   264: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   267: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   270: areturn
            //   271: fload_1
            //   272: ldc_w 483
            //   275: fcmpl
            //   276: iflt +37 -> 313
            //   279: fload_1
            //   280: ldc_w 486
            //   283: fcmpg
            //   284: ifgt +29 -> 313
            //   287: new 43	java/lang/StringBuilder
            //   290: dup
            //   291: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   294: fload_1
            //   295: ldc_w 483
            //   298: fdiv
            //   299: f2i
            //   300: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   303: ldc_w 488
            //   306: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   309: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   312: areturn
            //   313: fload_1
            //   314: ldc_w 486
            //   317: fcmpl
            //   318: ifle +36 -> 354
            //   321: fload_1
            //   322: ldc_w 489
            //   325: fcmpg
            //   326: ifge +28 -> 354
            //   329: new 43	java/lang/StringBuilder
            //   332: dup
            //   333: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   336: fload_1
            //   337: f2i
            //   338: bipush 30
            //   340: idiv
            //   341: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   344: ldc_w 491
            //   347: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   350: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   353: areturn
            //   354: new 43	java/lang/StringBuilder
            //   357: dup
            //   358: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   361: fload_1
            //   362: f2i
            //   363: sipush 365
            //   366: idiv
            //   367: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   370: ldc_w 493
            //   373: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   376: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   379: areturn
            //
            // Exception table:
            //   from	to	target	type
            //   37	44	163	java/lang/Exception
            //   44	81	167	java/lang/Exception
        }

        public static String get_fullTime_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
        }

        public static String get_historyItemTime_String(long paramLong)
        {
            Object localObject2 = Calendar.getInstance();
            Object localObject4 = new SimpleDateFormat("yyyy-MM-dd");
            Date localDate = new Date(paramLong);
            Object localObject1 = null;
            Object localObject3 = null;
            try
            {
                localObject2 = ((SimpleDateFormat)localObject4).parse(((SimpleDateFormat)localObject4).format(((Calendar)localObject2).getTime()));
                localObject1 = localObject2;
                localObject4 = ((SimpleDateFormat)localObject4).parse(((SimpleDateFormat)localObject4).format(localDate));
                localObject3 = localObject4;
                localObject1 = localObject2;
                paramLong = (localObject1.getTime() - localObject3.getTime()) / 1000L / 86400L;
                if (paramLong == 0L)
                    return new SimpleDateFormat("今天 ").format(localDate);
            }
            catch (ParseException localParseException)
            {
                while (true)
                    localParseException.printStackTrace();
                if (paramLong == 1L)
                    return new SimpleDateFormat("昨天").format(localDate);
            }
            return new SimpleDateFormat("yyyy年MM月dd日").format(localDate);
        }

        public static String get_lastSprotsTime_String(long paramLong)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject2 = null;
            long l2;
            try
            {
                localObject1 = new Date(paramLong);
                localObject2 = localObject1;
                localObject1 = null;
                localObject3 = null;
            }
            catch (Exception localException)
            {
                try
                {
                    Date localDate = localSimpleDateFormat.parse(localSimpleDateFormat.format(localCalendar.getTime()));
                    Object localObject1 = localDate;
                    localObject2 = localSimpleDateFormat.parse(localSimpleDateFormat.format((Date)localObject2));
                    localObject1 = localDate;
                    l1 = (((Date)localObject1).getTime() - ((Date)localObject2).getTime()) / 1000L;
                    l2 = l1 / 86400L;
                    if (l2 == 0L)
                    {
                        i = (int)(l1 / 3600L);
                        if (i >= 1)
                        {
                            return i + "小时前";
                            localException = localException;
                        }
                    }
                }
                catch (ParseException localParseException)
                {
                    long l1;
                    while (true)
                    {
                        Object localObject3;
                        localParseException.printStackTrace();
                        localObject2 = localObject3;
                    }
                    int j = (int)(l1 / 60L);
                    int i = j;
                    if (j <= 0)
                        i = 1;
                    return i + "分钟前";
                }
            }
            if ((l2 >= 1L) && (l2 <= 30L))
            {
                localCalendar.setTimeInMillis(paramLong);
                return l2 + "天前" + "的" + localCalendar.getTime().getHours() + ":" + localCalendar.getTime().getMinutes();
            }
            return "1月前";
        }

        public static String get_lastSprotsTime_String2(long paramLong)
        {
            Calendar localCalendar = Calendar.getInstance();
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject2 = null;
            long l2;
            try
            {
                localObject1 = new Date(paramLong);
                localObject2 = localObject1;
                localObject1 = null;
                localObject3 = null;
            }
            catch (Exception localException)
            {
                try
                {
                    Date localDate = localSimpleDateFormat.parse(localSimpleDateFormat.format(localCalendar.getTime()));
                    Object localObject1 = localDate;
                    localObject2 = localSimpleDateFormat.parse(localSimpleDateFormat.format((Date)localObject2));
                    localObject1 = localDate;
                    l1 = (((Date)localObject1).getTime() - ((Date)localObject2).getTime()) / 1000L;
                    l2 = l1 / 86400L;
                    if (l2 == 0L)
                    {
                        i = (int)(l1 / 3600L);
                        if (i >= 1)
                        {
                            return i + "小时前";
                            localException = localException;
                        }
                    }
                }
                catch (ParseException localParseException)
                {
                    long l1;
                    while (true)
                    {
                        Object localObject3;
                        localParseException.printStackTrace();
                        localObject2 = localObject3;
                    }
                    int j = (int)(l1 / 60L);
                    int i = j;
                    if (j <= 0)
                        i = 1;
                    return i + "分钟前";
                }
            }
            if ((l2 >= 1L) && (l2 <= 30L))
            {
                localCalendar.setTimeInMillis(paramLong);
                return l2 + "天前";
            }
            return "1月前";
        }

        public static String get_messageTime_String(long paramLong)
        {
            Object localObject3 = Calendar.getInstance();
            Object localObject5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject2 = null;
            try
            {
                localObject1 = new Date(paramLong);
                localObject2 = localObject1;
                localObject1 = null;
                localObject4 = null;
            }
            catch (Exception localException)
            {
                try
                {
                    localObject3 = ((SimpleDateFormat)localObject5).parse(((SimpleDateFormat)localObject5).format(((Calendar)localObject3).getTime()));
                    Object localObject1 = localObject3;
                    localObject5 = ((SimpleDateFormat)localObject5).parse(((SimpleDateFormat)localObject5).format(localObject2));
                    localObject4 = localObject5;
                    localObject1 = localObject3;
                    i = ((Date)localObject1).getDate() - localObject4.getDate();
                    if (i == 0)
                    {
                        localObject1 = new SimpleDateFormat("HH:mm");
                        return ((SimpleDateFormat)localObject1).format(localObject2);
                        localException = localException;
                    }
                }
                catch (ParseException localParseException)
                {
                    while (true)
                    {
                        Object localObject4;
                        int i;
                        localParseException.printStackTrace();
                        continue;
                        if (i == 1)
                            return "昨天";
                        if (isInSameWeek(localException, localObject4))
                            return getWeekName(localObject2);
                        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM-dd");
                    }
                }
            }
        }

        public static String get_messageTime_String(String paramString)
        {
            long l = get_yMdHms_long(paramString);
            Object localObject1 = Calendar.getInstance();
            Object localObject3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = null;
            try
            {
                paramString = new Date(l);
                str = paramString;
                paramString = null;
                localObject2 = null;
            }
            catch (Exception paramString)
            {
                try
                {
                    localObject1 = ((SimpleDateFormat)localObject3).parse(((SimpleDateFormat)localObject3).format(((Calendar)localObject1).getTime()));
                    paramString = (String)localObject1;
                    localObject3 = ((SimpleDateFormat)localObject3).parse(((SimpleDateFormat)localObject3).format(str));
                    localObject2 = localObject3;
                    paramString = (String)localObject1;
                    i = paramString.getDate() - localObject2.getDate();
                    if (i == 0)
                    {
                        paramString = new SimpleDateFormat("HH:mm");
                        return paramString.format(str);
                        paramString = paramString;
                    }
                }
                catch (ParseException localParseException)
                {
                    while (true)
                    {
                        Object localObject2;
                        int i;
                        localParseException.printStackTrace();
                        continue;
                        if (i == 1)
                            return "昨天";
                        if (isInSameWeek(paramString, localObject2))
                            return getWeekName(str);
                        paramString = new SimpleDateFormat("MM-dd");
                    }
                }
            }
        }

        public static long get_min_long(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("London"));
            long l2 = 0L;
            String str = paramString;
            long l1;
            if (paramString != null)
                l1 = l2;
            try
            {
                str = paramString.replace("h", ":").replace("'", ":").replace("''", "");
                l1 = l2;
                l2 = localSimpleDateFormat.parse(str).getTime();
                l1 = l2;
                int i = (int)l2 / 60000;
                l1 = i;
                return l1;
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String get_ms_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("mm’ss”");
            localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("London"));
            return localSimpleDateFormat.format(localDate);
        }

        // ERROR //
        public static String get_reservRunTime_String(String paramString)
        {
            // Byte code:
            //   0: aload_0
            //   1: invokestatic 471	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
            //   4: ifeq +6 -> 10
            //   7: ldc 107
            //   9: areturn
            //   10: invokestatic 17	java/util/Calendar:getInstance	()Ljava/util/Calendar;
            //   13: astore 8
            //   15: new 79	java/text/SimpleDateFormat
            //   18: dup
            //   19: ldc 120
            //   21: invokespecial 84	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
            //   24: astore 7
            //   26: new 79	java/text/SimpleDateFormat
            //   29: dup
            //   30: ldc 120
            //   32: invokespecial 84	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
            //   35: astore 9
            //   37: aload 9
            //   39: aload_0
            //   40: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   43: astore_0
            //   44: aload 7
            //   46: aload 7
            //   48: aload 8
            //   50: invokevirtual 98	java/util/Calendar:getTime	()Ljava/util/Date;
            //   53: invokevirtual 102	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
            //   56: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   59: astore 8
            //   61: aload 7
            //   63: aload 7
            //   65: aload_0
            //   66: invokevirtual 102	java/text/SimpleDateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
            //   69: invokevirtual 209	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
            //   72: astore_0
            //   73: aload 8
            //   75: invokevirtual 180	java/util/Date:getTime	()J
            //   78: aload_0
            //   79: invokevirtual 180	java/util/Date:getTime	()J
            //   82: lsub
            //   83: ldc2_w 266
            //   86: ldiv
            //   87: lstore_3
            //   88: lload_3
            //   89: ldc2_w 411
            //   92: ldiv
            //   93: lstore 5
            //   95: lload 5
            //   97: lconst_0
            //   98: lcmp
            //   99: ifne +80 -> 179
            //   102: lload_3
            //   103: ldc2_w 475
            //   106: ldiv
            //   107: l2i
            //   108: istore_1
            //   109: iload_1
            //   110: iconst_1
            //   111: if_icmplt +32 -> 143
            //   114: new 43	java/lang/StringBuilder
            //   117: dup
            //   118: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   121: iload_1
            //   122: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   125: ldc_w 478
            //   128: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   131: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   134: areturn
            //   135: astore_0
            //   136: ldc 107
            //   138: areturn
            //   139: astore_0
            //   140: ldc 107
            //   142: areturn
            //   143: lload_3
            //   144: ldc2_w 187
            //   147: ldiv
            //   148: l2i
            //   149: istore_2
            //   150: iload_2
            //   151: istore_1
            //   152: iload_2
            //   153: ifne +5 -> 158
            //   156: iconst_1
            //   157: istore_1
            //   158: new 43	java/lang/StringBuilder
            //   161: dup
            //   162: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   165: iload_1
            //   166: invokevirtual 53	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
            //   169: ldc_w 482
            //   172: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   175: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   178: areturn
            //   179: lload 5
            //   181: lconst_1
            //   182: lcmp
            //   183: iflt +34 -> 217
            //   186: lload 5
            //   188: ldc2_w 526
            //   191: lcmp
            //   192: ifgt +25 -> 217
            //   195: new 43	java/lang/StringBuilder
            //   198: dup
            //   199: invokespecial 44	java/lang/StringBuilder:<init>	()V
            //   202: lload 5
            //   204: invokevirtual 198	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
            //   207: ldc_w 485
            //   210: invokevirtual 50	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   213: invokevirtual 57	java/lang/StringBuilder:toString	()Ljava/lang/String;
            //   216: areturn
            //   217: ldc_w 529
            //   220: areturn
            //
            // Exception table:
            //   from	to	target	type
            //   37	44	135	java/lang/Exception
            //   44	73	139	java/lang/Exception
        }

        public static String get_yM_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM").format(localDate);
        }

        public static long get_yM_long(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy年MM月");
            long l1 = 0L;
            try
            {
                long l2 = localSimpleDateFormat.parse(paramString).getTime();
                l1 = l2;
                return l1;
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String get_yMdHm_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(localDate);
        }

        public static String get_yMdHms_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
        }

        public static String get_yMdHms_String(Date paramDate)
        {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(paramDate);
        }

        public static long get_yMdHms_long(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long l1 = 0L;
            try
            {
                long l2 = localSimpleDateFormat.parse(paramString).getTime();
                l1 = l2;
                return l1;
            }
            catch (ParseException paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static String get_yMd_String(long paramLong)
        {
            Date localDate = new Date(paramLong);
            return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
        }

       */ public static long get_yMd_long(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long l1 = 0L;
            try
            {
                long l2 = localSimpleDateFormat.parse(paramString).getTime();
                return l2;
            }
            catch (ParseException localParseException)
            {
                    localParseException.printStackTrace();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            return 0;
        }

       /* public static String get_ym_String(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                if (paramString == null)
                    return "";
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
            return new SimpleDateFormat("yyyy-MM").format(paramString);
        }

        public static boolean isInSameWeek(Date paramDate1, Date paramDate2)
        {
            Calendar localCalendar1 = Calendar.getInstance();
            Calendar localCalendar2 = Calendar.getInstance();
            localCalendar1.setTime(paramDate1);
            localCalendar2.setTime(paramDate2);
            int i = localCalendar1.get(7);
            int j = paramDate1.getDate() - paramDate2.getDate();
            return (j < (i + 6) % 7) && (j > 0);
        }

        public static boolean isInTime()
        {
            Date localDate = new Date();
            int i = Integer.parseInt(new SimpleDateFormat("HH").format(localDate));
            Log.d("gsensor", "hour:" + String.valueOf(i));
            return (i >= 1) && (i <= 5);
        }

        public static boolean isInYM(String paramString)
        {
            String str = getCurrentYMZh();
            if ((StringUtil.isEmpty(paramString)) || (StringUtil.isEmpty(str)))
                return false;
            return str.equals(paramString);
        }

        public static boolean isNoDisturbTime(Context paramContext)
        {
            if (!ConfigManager.getIsOpenNoDisturb(paramContext))
                return false;
            Date localDate = new Date();
            int i = Integer.parseInt(new SimpleDateFormat("HH").format(localDate));
            if (ConfigManager.getNoDisturbEndTime(paramContext) - ConfigManager.getNoDisturbStartTime(paramContext) > 0)
            {
                if ((i >= ConfigManager.getNoDisturbStartTime(paramContext)) && (i <= ConfigManager.getNoDisturbEndTime(paramContext)))
                    return true;
            }
            else
            {
                if ((i >= ConfigManager.getNoDisturbStartTime(paramContext)) || (i == 0))
                    return true;
                if ((i <= ConfigManager.getNoDisturbEndTime(paramContext)) || (i == 0))
                    return true;
            }
            return false;
        }

        public static long parseSportStamp(String paramString)
        {
            Object localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            long l1 = 0L;
            try
            {
                paramString = ((SimpleDateFormat)localObject).parse(paramString.replace("T", " "));
                localObject = Calendar.getInstance();
                ((Calendar)localObject).setTime(paramString);
                long l2 = ((Calendar)localObject).getTimeInMillis();
                l1 = l2;
                return l1;
            }
            catch (Exception paramString)
            {
                while (true)
                    paramString.printStackTrace();
            }
        }

        public static Date stringToDate(String paramString)
        {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Object localObject = null;
            try
            {
                paramString = localSimpleDateFormat.parse(paramString);
                return paramString;
            }
            catch (ParseException paramString)
            {
                while (true)
                {
                    paramString.printStackTrace();
                    paramString = localObject;
                }
            }
        }*/
    }

