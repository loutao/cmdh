package com.ratio.deviceService.communication;

import android.net.ParseException;
import android.util.Log;


import com.ratio.deviceService.data.CLog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/13.
 */
public class DataUtil {
    static String TAG = "codoon_debug";

/*    public static String DebugPrint(byte[] paramArrayOfByte)
    {
        if (!isDebug())
            return null;
        if (paramArrayOfByte == null)
            return null;
        String str = "";
        int i = 0;
        while (true)
        {
            if (i >= paramArrayOfByte.length)
            {
                Log.i(TAG, str + "   lenth:" + paramArrayOfByte.length);
                return str;
            }
            str = str + Integer.toHexString(paramArrayOfByte[i] & 0xFF) + "   ";
            i += 1;
        }
    }*/

    public static String DebugPrint(int[] paramArrayOfInt)
    {
        if (!isDebug())
            return null;
        if (paramArrayOfInt == null)
            return null;
        String str = "";
        int i = 0;
        while (true)
        {
            if (i >= paramArrayOfInt.length)
            {
                Log.i(TAG, str + "   lenth:" + paramArrayOfInt.length);
                return str;
            }
            str = str + Integer.toHexString(paramArrayOfInt[i]) + "   ";
            i += 1;
        }
    }
    public static void DebugPrint(String paramString)
    {
        if (!isDebug())
            return;
        Log.i(TAG, paramString);
    }

    public static void DebugPrint(List<Integer> paramList)
    {
        if (!isDebug())
            return;
        String str1 = null;
        Iterator localIterator = null;
        if (paramList != null)
        {
            str1 = "";
            localIterator = paramList.iterator();
        }
        while (true)
        {
            if (!localIterator.hasNext())
            {
                String str2 = TAG;
                StringBuilder localStringBuilder2 = new StringBuilder("receive:");
                CLog.i(str2, str1);
                break;
            }
            int i = ((Integer)localIterator.next()).intValue();
            StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
            str1 = Integer.toHexString(i) + "   ";
        }
    }

    public static boolean equalArray(int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
        if ((paramArrayOfInt1 == null) || (paramArrayOfInt2 == null))
            return false;
        if (paramArrayOfInt1.length == paramArrayOfInt2.length) {
            int i = 0;
            while (true) {
                if (i >= paramArrayOfInt1.length)
                    return true;
                if (paramArrayOfInt1[i] != paramArrayOfInt2[i])
                    return false;
                i += 1;
            }
        }
        return false;
    }

    public static long getDay24time(long paramLong) throws java.text.ParseException {
        Object localObject1 = new SimpleDateFormat("yyyy-MM-dd");
        Object localObject2 = ((SimpleDateFormat) localObject1).format(new Date(paramLong));
        try {
            localObject1 = ((SimpleDateFormat) localObject1).parse((String) localObject2);
            localObject2 = Calendar.getInstance();
            ((Calendar) localObject2).setTime((Date) localObject1);
            ((Calendar) localObject2).add(Calendar.DATE, 1);
            long l = ((Calendar) localObject2).getTimeInMillis();
            return l;
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return paramLong;
    }

    private static boolean isDebug()
    {
        return CLog.isDebug;
    }
}
