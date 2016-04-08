package com.ratio.deviceService.data;

import android.net.ParseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class DataUtil {
    static String TAG = "codoon_debug";

    public static String DebugPrint(byte[] paramArrayOfByte)
    {
        if (!(isDebug()))
            return null;
        if (paramArrayOfByte == null)
            return null;
        String str = "";
        int i = 0;
        while (true)
        {
            if (i >= paramArrayOfByte.length)
            {
                CLog.i(TAG, str + "   lenth:" + paramArrayOfByte.length);
                return str;
            }
            str = str + Integer.toHexString(paramArrayOfByte[i] & 0xFF) + "   ";
            i += 1;
        }
    }

    public static String DebugPrint(int[] paramArrayOfInt)
    {
        if (!(isDebug()))
            return null;
        if (paramArrayOfInt == null)
            return null;
        String str = "";
        int i = 0;
        while (true)
        {
            if (i >= paramArrayOfInt.length)
            {
                CLog.i(TAG, str + "   lenth:" + paramArrayOfInt.length);
                return str;
            }
            str = str + Integer.toHexString(paramArrayOfInt[i]) + "   ";
            i += 1;
        }
    }

    public static void DebugPrint(String paramString)
    {
        if (!(isDebug()))
            return;
        CLog.i(TAG, paramString);
    }

    public static void DebugPrint(List<Integer> paramList)
    {
        if (!(isDebug()))
            return;
        String str1 = "";
        Iterator localIterator = paramList.iterator();
        while (true)
        {
            while (!(localIterator.hasNext()))
            {
                String str2 = TAG;
                StringBuilder localStringBuilder2 = new StringBuilder("receive:");
                CLog.i(str2, str1);
                return;
            }
            int i = ((Integer)localIterator.next()).intValue();
            StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
            str1 = Integer.toHexString(i) + "   ";
        }
    }


    public static boolean equalArray(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    {
        if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null))
            return false;
        if (paramArrayOfInt1.length == paramArrayOfInt2.length)
        {
            int i = 0;
            while (true)
            {
                if (i >= paramArrayOfInt1.length)
                    return true;
                if (paramArrayOfInt1[i] != paramArrayOfInt2[i])
                    return false;
                i += 1;
            }
        }
        return false;
    }

    public static long getDay24time(long paramLong)
    {
        long l1;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate1 = new Date(paramLong);
        String str = localSimpleDateFormat.format(localDate1);
        try
        {
            Date localDate2 = localSimpleDateFormat.parse(str);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setTime(localDate2);
            localCalendar.add(Calendar.DAY_OF_MONTH, 1);
            long l2 = localCalendar.getTimeInMillis();
            l1 = l2;
            return l1;
        }
        catch (ParseException localParseException)
        {
            localParseException.printStackTrace();
            l1 = paramLong;
            return l1;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static boolean isDebug()
    {
        return CLog.isDebug;
    }
}
