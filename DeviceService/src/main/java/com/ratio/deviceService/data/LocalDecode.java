package com.ratio.deviceService.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class LocalDecode {
    public static final int DEEP_SLEEP = 3;
    public static final int DEEP_Threshold = 4;
    protected static final int INVALID_STATE = -1;
    public static final int LIGHT_SLEEP = 2;
    public static final int LIGHT_Threshold = 17;
    protected static final int SLEEPDATA_STATE = 5;
    protected static final int SLEEPDATE_STATE = 4;
    protected static final int SLEEPHEAD_STATE = 3;
    protected static final int SPORTDATA_STATE = 2;
    protected static final int SPORTDATE_STATE = 1;
    protected static final int SPORTHEAD_STATE = 0;
    public static final int WAKE_SLEEP = 1;
    public final int CALORIE = 1;
    protected final int CALORIE_TYPE = 1;
    public final int DISTANCE = 2;
    protected final int DISTANCE_TYPE = 2;
    protected final String KEY_BIND_PRODUCT_ID = "BindTypeId";
    protected String KEY_PRE_SLEEP = "lastSleepTime";
    protected final String PRE_NAME = "MyPrefsFile";
    public final int SLEEPTIME = 3;
    public final int STEP = 0;
    protected final int STEP_TYPE = 0;
    protected String TAG = "LocalDecode";
    long curTime;
    protected Calendar mCalendar = Calendar.getInstance();
    protected Context mContext;
    protected SimpleDateFormat mFormat;

    public LocalDecode(Context paramContext) {
        this.mContext = paramContext;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.mFormat = localSimpleDateFormat;
        this.mFormat.setTimeZone(TimeZone.getDefault());
    }

    public LocalDecode(Context paramContext, SaveManager.eSaveType parameSaveType) {
        this.mContext = paramContext;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.mFormat = localSimpleDateFormat;
        this.mFormat.setTimeZone(TimeZone.getDefault());
    }

    public static int getSleepLevelByTime(int paramInt) {
        int i;
        if (paramInt < 0)
            i = -1;
        while (true) {

            if (paramInt < 4)
                i = 3;
            else if (paramInt < 17)
                i = 2;
            else
                i = 1;
            return i;
        }
    }

    public int _doubleInt(int paramInt1, int paramInt2, int paramInt3) {
        return paramInt2 + (0xFF00 & paramInt1 << 8);
    }

    public int _singleInt(int paramInt) {
        return paramInt;
    }

    public long[] analysis(ArrayList<ArrayList<Integer>> paramArrayList) {
        long[] arrayOfLong;
        if ((paramArrayList == null) || (paramArrayList.size() < 1)) {
            arrayOfLong = null;
            return arrayOfLong;
        }
        String str = "";
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        Object localObject = localArrayList2;
        int i = 0;
        Iterator localIterator1 = paramArrayList.iterator();
        while (true) {
            if (!localIterator1.hasNext()) {
                CLog.i(this.TAG, str);
                arrayOfLong = analysisR(localArrayList1);
                break;
            }
            Iterator localIterator2 = ((ArrayList) localIterator1.next()).iterator();
            while (localIterator2.hasNext()) {
                Integer localInteger = (Integer) localIterator2.next();
                ((ArrayList) localObject).add(localInteger);
                StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str));
                str = localInteger + "  ";
                i++;
                if (i == 6) {
                    StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str));
                    str = "\r\n";
                    i = 0;
                    localArrayList1.add(localObject);
                    ArrayList localArrayList3 = new ArrayList();
                    localObject = localArrayList3;
                }
            }
        }
        return arrayOfLong;
    }

    protected long[] analysisR(ArrayList<ArrayList<Integer>> paramArrayList) {
        long l1 = 0L;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;
        long l5 = 0L;
        long l6 = 0L;
        long l7 = 0L;
        long l8 = 0L;
        long l9 = 0L;
        int i = 0;
        long l10 = 0L;
        long l11 = 0L;
        long l12 = 0L;
        int j = -1;
        long l13 = 0L;
        int k = 0;
        int m = 0;
        int n = 0;
        SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("MyPrefsFile", 0);
        String str1 = this.KEY_PRE_SLEEP.concat(localSharedPreferences.getString("BindTypeId", "_00"));
        long l14 = localSharedPreferences.getLong(str1, 0L);
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        Iterator localIterator1 = paramArrayList.iterator();
        ArrayList localArrayList;
        while (true) {
            if (!localIterator1.hasNext()) {
                int i6 = k / 60;
                int i7 = m / 60;
                int i8 = (int) (l12 - i6 - i7);
                String str3 = this.TAG;
                StringBuilder localStringBuilder3 = new StringBuilder("current day steps:");
                Log.d(str3, l5 + ", calories:" + l6 + ", distances:" + l7 + ", sleepTotaltime:" + l12 + " deepSleepValue:" + i6 + " lightSleepValue:" + i7 + " wakeSleepValue " + i8);
                long[] arrayOfLong = new long[16];
                arrayOfLong[0] = l9;
                arrayOfLong[1] = l8;
                arrayOfLong[2] = l5;
                arrayOfLong[3] = l6;
                arrayOfLong[4] = l7;
                arrayOfLong[5] = l10;
                arrayOfLong[6] = l4;
                arrayOfLong[7] = l1;
                arrayOfLong[8] = l2;
                arrayOfLong[9] = l3;
                arrayOfLong[10] = i6;
                arrayOfLong[11] = i7;
                arrayOfLong[12] = i8;
                arrayOfLong[13] = l12;
                arrayOfLong[14] = l13;
                arrayOfLong[15] = l11;
                return arrayOfLong;
            }
            localArrayList = (ArrayList) localIterator1.next();
            if (localArrayList.size() == 6) {
                int i1 = 0;
                int i2 = 0;
                Iterator localIterator2 = localArrayList.iterator();
                label395:
                if (!localIterator2.hasNext()) {
                    if (i1 != 6)
                        if (i2 == 6)
                            j = 3;

                    j = 0;
                    l13 = 0L;
                    break;
                }
                switch (j) {
                    default:
                        l5 = l4;
                        break;
                    case 0:
                        j = 1;
                        break;


                    case 1:
                        int i3 = ((Integer) localIterator2.next()).intValue();
                        if (i3 == 254) {
                            i1++;
                            if (!localIterator2.hasNext()) {
                                if (i1 != 6)
                                    if (i2 == 6)
                                        j = 3;

                                j = 0;
                                l13 = 0L;
                                break;
                            }
                        }
                    case 2:
                        i3 = ((Integer) localIterator2.next()).intValue();
                        if (i3 != 253)
                            if (!localIterator2.hasNext()) {
                                if (i1 != 6)
                                    if (i2 == 6)
                                        j = 3;
                                j = 0;
                                l13 = 0L;
                                break;

                            }
                    case 3:
                        i2++;
                        if (!localIterator2.hasNext()) {
                            if (i1 != 6)
                                if (i2 == 6)
                                    j = 3;
                            j = 0;
                            l13 = 0L;
                            break;

                        }
                    case 4:
                        if (i2 == 6)
                            j = 3;
                        break;
                    case 5:
                        break;
                }

                this.curTime = getTime(localArrayList);
                if (!isInvalidTime(this.curTime)) {
                    if (l10 == 0L)
                        l10 = this.curTime;
                    long l16;
                    if (l10 < this.curTime) {
                        l16 = l10;
                        l10 = l16;
                        if (!isToday(this.curTime))
                            i = 0;
                        i = 1;
                        if (l9 != 0L)
                            if (l9 < this.curTime) ;
                        for (long l17 = l9; ; l17 = this.curTime) {
                            l9 = l17;
                            break;
                        }
                        l9 = this.curTime;
                    }
                    while (true) {
                        j = 2;
                        l16 = this.curTime;
                        l10 = l16;
                        if (l9 < this.curTime) ;
                        for (long l17 = l9; ; l17 = this.curTime) {
                            l9 = l17;
                            break;
                        }
                        break;
                    }
                }
                j = -1;
//                continue;
                int[] arrayOfInt2 = getSportData(localArrayList);
                filterSportResult(arrayOfInt2);
                l1 += arrayOfInt2[0];
                l2 += arrayOfInt2[1];
                l3 += arrayOfInt2[2];
                l4 = 600000L + l4;
                if (i != 0) {
                    l8 = 10L + l8;
                    l5 += arrayOfInt2[0];
                    l6 += arrayOfInt2[1];
                    l7 += arrayOfInt2[2];
                }
                this.curTime = (600000L + this.curTime);
                l11 = this.curTime;
//                continue;
                j = 4;
//                continue;
                this.curTime = getTime(localArrayList);
                boolean bool = isInvalidTime(this.curTime);
                l14 = localSharedPreferences.getLong(str1, 0L);
                if (!bool) {
                    j = 5;
                    if (isTodaySleep(this.curTime)) {
                        if (l13 == 0L)
                            l13 = this.curTime;
                        if (l13 < this.curTime) ;
                        for (long l15 = l13; ; l15 = this.curTime) {
                            l13 = l15;
                            break;
                        }
                    }
                } else {
                    j = -1;
                }
            }
        }
        int[] arrayOfInt1 = new int[0];
        int i4 = 0;
        if (isTodaySleep(this.curTime)) {
            if (1L + l14 / 600000L > this.curTime / 600000L)
                Log.e(this.TAG, "time has calculated");
            arrayOfInt1 = getSportData(localArrayList);
            i4 = 0;
            if (i4 < arrayOfInt1.length) {
                int i5 = getSleepLevelByTime(arrayOfInt1[i4]);
            }
            l12 = 10L + l12;
//            (600000L + this.curTime);
        }
        while (true) {
            localEditor.putLong(str1, this.curTime);
            localEditor.commit();
            this.curTime = (600000L + this.curTime);
            isTodaySleep(this.curTime);

//            label1008:
            int i5 = getSleepLevelByTime(arrayOfInt1[i4]);
            if (i5 == 3)
                k += 200;
            while (true) {
                i4++;

                if (i5 == 2)
                    m += 200;
                else
                    n += 200;
                break;
            }
            Log.e(this.TAG, "time has calculated");
            String str2 = this.TAG;
            StringBuilder localStringBuilder1 = new StringBuilder("last sleep time is:");
            SimpleDateFormat localSimpleDateFormat1 = this.mFormat;
            Date localDate1 = new Date(l14);
            StringBuilder localStringBuilder2 = localStringBuilder1.append(localSimpleDateFormat1.format(localDate1)).append(" and cur:");
            SimpleDateFormat localSimpleDateFormat2 = this.mFormat;
            Date localDate2 = new Date(this.curTime);
            Log.i(str2, localSimpleDateFormat2.format(localDate2));
            break;
        }
        return null;
    }

    protected long createTime(ArrayList<Integer> paramArrayList, boolean paramBoolean) {
        int i = 100 * Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(0)).intValue())) + Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(1)).intValue()));
        int j = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(2)).intValue()));
        int k = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(3)).intValue()));
        int m = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(4)).intValue()));
        int n = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(5)).intValue()));
        if ((paramBoolean) && (n % 10 > 0))
            n = 10 * (1 + n / 10);
        int i1 = 10 * (n / 10);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeZone(TimeZone.getDefault());
        localCalendar.set(i, j, k, m, i1);
        StringBuilder localStringBuilder = new StringBuilder("time is ");
        Log.d("debug", i + "-" + j + "-" + k + "-" + m + "-" + i1);
        return 10L * (localCalendar.getTimeInMillis() / 600000L);
    }

    protected void filterSportResult(int[] paramArrayOfInt) {
        if ((paramArrayOfInt == null) || (paramArrayOfInt.length < 3))
            return;
        String str = this.mContext.getSharedPreferences("MyPrefsFile", 0).getString("BindTypeName", "");
        if ((str != null) && (str.equals("CANDY"))) {
            if (paramArrayOfInt[0] <= 0)
                return;
            if (paramArrayOfInt[1] <= paramArrayOfInt[0]) ;
            paramArrayOfInt[1] = 0;
            paramArrayOfInt[0] = 0;
            paramArrayOfInt[2] = 0;
        }
        label73:
        label214:
        while (true) {
            if (paramArrayOfInt[0] > 3000) {
                paramArrayOfInt[1] = 0;
                paramArrayOfInt[0] = 0;
                paramArrayOfInt[2] = 0;
            }
            while (true) {
                label97:
                if ((10 * paramArrayOfInt[1] != paramArrayOfInt[0]) || (paramArrayOfInt[0] != paramArrayOfInt[2]))
                    break label214;
                paramArrayOfInt[1] = 0;
                paramArrayOfInt[0] = 0;
                paramArrayOfInt[2] = 0;
                if (paramArrayOfInt[1] <= paramArrayOfInt[0])
                    break label73;
                paramArrayOfInt[1] = 0;
                paramArrayOfInt[0] = 0;
                paramArrayOfInt[2] = 0;
                if ((paramArrayOfInt[0] == 0) && (paramArrayOfInt[2] >= 2)) {
                    paramArrayOfInt[0] = 0;
                    paramArrayOfInt[1] = 0;
                    paramArrayOfInt[2] = 0;
                } else if (1.8D * paramArrayOfInt[0] < paramArrayOfInt[2]) {
                    paramArrayOfInt[0] = 0;
                    paramArrayOfInt[1] = 0;
                    paramArrayOfInt[2] = 0;
                }
                break;

            }
        }
    }

    protected int[] getSportData(ArrayList<Integer> paramArrayList) {
        int[] arrayOfInt = new int[3];
        int i = ((Integer) paramArrayList.get(0)).intValue();
        int j = ((Integer) paramArrayList.get(1)).intValue();
        if ((i != 0) || (j != 0))
            arrayOfInt[0] = _doubleInt(i, j, 0);
        int k = ((Integer) paramArrayList.get(2)).intValue();
        int m = ((Integer) paramArrayList.get(3)).intValue();
        if ((k != 0) || (m != 0))
            arrayOfInt[1] = _doubleInt(k, m, 1);
        int n = ((Integer) paramArrayList.get(4)).intValue();
        int i1 = ((Integer) paramArrayList.get(5)).intValue();
        if ((n != 0) || (i1 != 0))
            arrayOfInt[2] = _doubleInt(n, i1, 2);
        return arrayOfInt;
    }

    protected long getSystemTime() {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeZone(TimeZone.getDefault());
        int i = localCalendar.get(Calendar.MINUTE);
        if (i % 10 > 0)
            i += 10 - i % 10;
        localCalendar.set(localCalendar.get(Calendar.YEAR), 1 + localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_YEAR), localCalendar.get(Calendar.HOUR_OF_DAY), i);
        StringBuilder localStringBuilder = new StringBuilder("system time is ");
        Log.d("debug", localCalendar.get(Calendar.YEAR) + "-" + localCalendar.get(Calendar.MONTH) + "-" + localCalendar.get(Calendar.DAY_OF_YEAR) + "-" + localCalendar.get(Calendar.HOUR_OF_DAY) + "-" + localCalendar.get(Calendar.MINUTE));
        return 10L * (localCalendar.getTimeInMillis() / 600000L);
    }

    protected long getTime(ArrayList<Integer> paramArrayList) {

        try {
            int i;
            int k;
            i = 100 * Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(0)).intValue())) + Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(1)).intValue()));
            int j = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(2)).intValue()));
            k = j - 1;
            int m = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(3)).intValue()));
            int n = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(4)).intValue()));
            int i1 = Integer.parseInt(Integer.toHexString(((Integer) paramArrayList.get(5)).intValue()));
            this.mCalendar.setTimeZone(TimeZone.getDefault());
            this.mCalendar.set(i, k, m, n, i1);
               /* for (l = this.mCalendar.getTimeInMillis(); ; l = -1L) {
                    return l;
                    localException1 = localException1;
                    Log.e(this.TAG, localException1.toString());
                }*/
        } catch (Exception localException2) {
            while (true) {
                Log.e(this.TAG, localException2.toString());
                long l = -1L;
            }
        }
        return mCalendar.getTimeInMillis();
    }

    protected boolean isInvalidTime(long paramLong) {
        if (paramLong < 0L) ;
        for (boolean bool = true; ; bool = false)
            return bool;
    }

    protected boolean isToday(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date localDate1 = new Date(paramLong);
        String str1 = localSimpleDateFormat.format(localDate1);
        Date localDate2 = new Date(System.currentTimeMillis());
        String str2 = localSimpleDateFormat.format(localDate2);
        String str3 = this.TAG;
        StringBuilder localStringBuilder = new StringBuilder("des:  ");
        Log.d(str3, str1 + " systemDay:" + str2);
        return str2.equals(str1);
    }

    protected boolean isTodaySleep(long paramLong) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date localDate = new Date(paramLong);
        String str1 = localSimpleDateFormat.format(localDate);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeZone(TimeZone.getDefault());
        int i = localCalendar.get(Calendar.AM_PM);
        localCalendar.get(Calendar.YEAR);
        localCalendar.get(Calendar.MONTH);
        localCalendar.get(Calendar.DAY_OF_MONTH);
        if (i == 0)
            localCalendar.add(Calendar.DAY_OF_MONTH, -1);
        localCalendar.set(localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH), 12, 0);
        String str2 = localSimpleDateFormat.format(localCalendar.getTime());
        localCalendar.add(Calendar.DAY_OF_MONTH, 1);
        localCalendar.set(localCalendar.get(Calendar.YEAR), localCalendar.get(Calendar.MONTH), localCalendar.get(Calendar.DAY_OF_MONTH), 12, 0);
        String str3 = localSimpleDateFormat.format(localCalendar.getTime());
        if ((str1.compareToIgnoreCase(str2) >= 0) && (str1.compareToIgnoreCase(str3) < 0)) ;
        for (boolean bool = true; ; bool = false)
            return bool;
    }

   /* protected void writeSD(String paramString)
    {
        FileManager localFileManager = new FileManager();
        localFileManager.saveContentSD(this.mContext, "hjack", paramString);
    }*/
}
