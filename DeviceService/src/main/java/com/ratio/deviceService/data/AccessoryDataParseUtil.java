package com.ratio.deviceService.data;

import android.content.Context;
import android.util.Log;

import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.communication.provider.SleepDetailDB;
import com.ratio.deviceService.communication.provider.SleepDetailTB;
import com.ratio.deviceService.datamanager.AccessoryValues;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class AccessoryDataParseUtil extends LocalDecode {
    private static int todaySleep;
    private int index = 0;
    SleepDetailDB mSleepDetailDB = null;
    long sleepEndtime = 0L;
    long startSleep = 0L;

    public AccessoryDataParseUtil(Context paramContext) {
        super(paramContext);
    }

    private HashMap<String, AccessoryValues> _analysis(ArrayList<ArrayList<Integer>> paramArrayList) {
        HashMap localHashMap = new HashMap();
        this.mSleepDetailDB.open();
        this.mSleepDetailDB.beginTransaction();
        int i = -1;
        this.sleepEndtime = 0L;
        this.startSleep = 0L;
        String str1 = "";
        Iterator localIterator1 = paramArrayList.iterator();
        ArrayList localArrayList;
        while (true) {
            if (!localIterator1.hasNext()) {
                calcSleepInTransacntion(localHashMap);
                long l9 = calStartTime(this.sleepEndtime);
                insetDB(l9, -1);
                long l10 = l9 + 200000L;
                insetDB(l10, -1);
                long l11 = l10 + 200000L;
                insetDB(l11, -1);
                this.sleepEndtime = (l11 + 200000L);
                getSleepTotalIntrans(AccessoryConfig.userID, this.startSleep, this.sleepEndtime, localHashMap);
                calTotalSleep(localHashMap);
                Log.d(this.TAG, "parse over");
                this.mSleepDetailDB.setTransactionSuccessful();
                this.mSleepDetailDB.endTransaction();
                this.mSleepDetailDB.close();
                return localHashMap;
            }
            localArrayList = (ArrayList) localIterator1.next();
            if (localArrayList.size() == 6) {
                int j = 0;
                int k = 0;
                Iterator localIterator2 = localArrayList.iterator();
                label212:
                if (!localIterator2.hasNext()) {
                    i = 0;
                    if (j != 6)
                        if (k == 6)
                            i = 3;
                    break;

                }

                switch (i) {
                    default:
                        break;
                    case 0:
                        i = 1;

                        int m = ((Integer) localIterator2.next()).intValue();
                        if (m == 254) {
                            j++;
                            if (!localIterator2.hasNext()) {
                                i = 0;
                                if (j != 6)
                                    if (k == 6)
                                        i = 3;
                                break;

                            }
                        }
                        if (m != 253)
                            if (!localIterator2.hasNext()) {
                                i = 0;
                                if (j != 6)
                                    if (k == 6)
                                        i = 3;
                                break;

                            }
                        k++;
                        if (!localIterator2.hasNext()) {
                            i = 0;
                            if (j != 6)
                                if (k == 6)
                                    i = 3;
                            break;

                        }

                        if (k == 6)
                            i = 3;
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
                this.curTime = getTime(localArrayList);
                if (!isInvalidTime(this.curTime)) {
                    str1 = getDateString(this.curTime);
                    AccessoryValues localAccessoryValues14 = null;
                    if (!localHashMap.containsKey(str1)) {
                        AccessoryValues localAccessoryValues13 = new AccessoryValues();
                        localAccessoryValues14 = localAccessoryValues13;
                        localHashMap.put(str1, localAccessoryValues14);
                        localAccessoryValues14.time = str1;
                    }
                    while (true) {
                        if (localAccessoryValues14.start_sport_time != 0L) {
                            AccessoryValues localAccessoryValues15 = localAccessoryValues14;
                        }
                        localAccessoryValues14.start_sport_time = this.curTime;
                        i = 2;

                        localAccessoryValues14 = (AccessoryValues) localHashMap.get(str1);
                        break;
                    }
                    AccessoryValues localAccessoryValues15 = localAccessoryValues14;
                    if (localAccessoryValues14.start_sport_time < this.curTime) ;
                    for (long l8 = localAccessoryValues14.start_sport_time; ; l8 = this.curTime) {
                        localAccessoryValues15.start_sport_time = l8;
                        break;
                    }
                }
                i = -1;

                int[] arrayOfInt2 = getSportData(localArrayList);
                filterSportResult(arrayOfInt2);
                this.curTime = (600000L + this.curTime);
                AccessoryValues localAccessoryValues8 = null;
                if (!localHashMap.containsKey(str1)) {
                    AccessoryValues localAccessoryValues7 = new AccessoryValues();
                    localAccessoryValues8 = localAccessoryValues7;
                    localAccessoryValues8.time = str1;
                    localHashMap.put(str1, localAccessoryValues8);
                }
                while (true) {
                    if (arrayOfInt2[0] != 0) {
                        AccessoryValues localAccessoryValues12 = localAccessoryValues8;
                        localAccessoryValues12.sport_duration = (10 + localAccessoryValues12.sport_duration);
                    }
                    AccessoryValues localAccessoryValues9 = localAccessoryValues8;
                    localAccessoryValues9.steps += arrayOfInt2[0];
                    AccessoryValues localAccessoryValues10 = localAccessoryValues8;
                    localAccessoryValues10.calories += arrayOfInt2[1];
                    AccessoryValues localAccessoryValues11 = localAccessoryValues8;
                    localAccessoryValues11.distances += arrayOfInt2[2];

                    localAccessoryValues8 = (AccessoryValues) localHashMap.get(str1);
                    break;
                }
                i = 4;
                this.curTime = getTime(localArrayList);
                if (!isInvalidTime(this.curTime)) {
                    str1 = getDateString(this.curTime);
                    AccessoryValues localAccessoryValues5 = null;
                    long l3;
                    long l4;
                    long l5 = 0;
                    long l6 = 0;
                    String str3 = null;
                    if (!localHashMap.containsKey(str1)) {
                        AccessoryValues localAccessoryValues4 = new AccessoryValues();
                        localAccessoryValues5 = localAccessoryValues4;
                        localHashMap.put(str1, localAccessoryValues5);
                        localAccessoryValues5.time = str1;
                        if (this.startSleep == 0L)
                            this.startSleep = calStartTime(this.curTime);
                        if (this.sleepEndtime <= this.curTime)
                            l3 = this.curTime;
                        l3 = this.sleepEndtime;
                        this.sleepEndtime = l3;
                        this.sleepEndtime = calcuEndtime(this.sleepEndtime);
                        i = 5;
                        AccessoryValues localAccessoryValues6 = localAccessoryValues5;
                        if (localAccessoryValues5.tmpEndSleep <= this.curTime)
                            l4 = this.curTime;
                        l4 = localAccessoryValues5.tmpEndSleep;
                        localAccessoryValues6.tmpEndSleep = l4;
                        l5 = calcuEndtime(this.curTime);
                        l6 = calStartTime(localAccessoryValues5.tmpEndSleep);
                        localAccessoryValues5.tmpEndSleep = this.curTime;
                        str3 = "";
                    }
                    while (true) {
                        if (l6 < l5) {
                            localAccessoryValues5 = (AccessoryValues) localHashMap.get(str1);
                            l3 = this.curTime;
                            l4 = this.curTime;
                            break;

                        }
                        long l7 = l5;
                        try {
                            Long localLong2 = Long.valueOf(l7);
                            int i1 = -1;
                            if (localAccessoryValues5.sleepdetail.containsKey(localLong2))
                                i1 = ((Integer) localAccessoryValues5.sleepdetail.remove(localLong2)).intValue();
                            StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str3));
                            str3 = "reduce " + localLong2 + " result:" + i1 + "\r\n";
                            l5 = 200000L + l5;
                            Long localLong3 = Long.valueOf(l5);
                            if (localAccessoryValues5.sleepdetail.containsKey(localLong3))
                                i1 = ((Integer) localAccessoryValues5.sleepdetail.remove(localLong3)).intValue();
                            l5 = 200000L + l5;
                            StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(str3));
                            str3 = "reduce " + localLong3 + " result:" + i1 + "\r\n";
                            Long localLong4 = Long.valueOf(l5);
                            if (localAccessoryValues5.sleepdetail.containsKey(localLong4))
                                i1 = ((Integer) localAccessoryValues5.sleepdetail.remove(localLong4)).intValue();
                            l5 = 200000L + l5;
                            StringBuilder localStringBuilder4 = new StringBuilder(String.valueOf(str3));
                            str3 = "reduce " + localLong4 + " result:" + i1 + "\r\n";
                            SleepDataUtil.saveInfo2File(str3);
                        } catch (Exception localException) {
                            localException.printStackTrace();
                        }
                    }
                }
                i = -1;
            }
        }
        str1 = getDateString(this.curTime);
        AccessoryValues localAccessoryValues2;

        int[] arrayOfInt1 = new int[0];
        String str2 = null;
        long l1 = 0;
        int n = 0;
        if (!localHashMap.containsKey(str1)) {
            AccessoryValues localAccessoryValues1 = new AccessoryValues();
            localAccessoryValues2 = localAccessoryValues1;
            localHashMap.put(str1, localAccessoryValues2);
            localAccessoryValues2.time = str1;
            arrayOfInt1 = getSportData(localArrayList);
            str2 = "";
            l1 = calStartTime(this.curTime);
            n = 0;
            if (n < arrayOfInt1.length) {
                Long localLong1 = Long.valueOf(l1 + 1000 * (200 * n));
            }
            SleepDataUtil.saveInfo2File(str2);
            AccessoryValues localAccessoryValues3 = localAccessoryValues2;
            localAccessoryValues3.tmpEndSleep = (600000L + localAccessoryValues3.tmpEndSleep);
            this.curTime = (600000L + this.curTime);
            if (this.sleepEndtime <= this.curTime) ;

        }
        for (long l2 = this.sleepEndtime; ; l2 = this.curTime) {
            this.sleepEndtime = l2;
            this.sleepEndtime = calcuEndtime(this.sleepEndtime);

            localAccessoryValues2 = (AccessoryValues) localHashMap.get(str1);

            Long localLong1 = Long.valueOf(l1 + 1000 * (200 * n));
            Integer localInteger1 = Integer.valueOf(0);
            if (localAccessoryValues2.sleepdetail.containsKey(localLong1))
                localInteger1 = (Integer) localAccessoryValues2.sleepdetail.get(localLong1);
            StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str2));
            str2 = " add time " + localLong1;
            Integer localInteger2 = Integer.valueOf(localInteger1.intValue() + arrayOfInt1[n]);
            localAccessoryValues2.sleepdetail.put(localLong1, localInteger2);
            n++;
            if (n < arrayOfInt1.length) {
                localLong1 = Long.valueOf(l1 + 1000 * (200 * n));
            }
            break;
        }
        return localHashMap;
    }


    private long calStartTime(long paramLong) {
        return paramLong / 600000L * 600000L;
    }

    private void calTotalSleep(HashMap<String, AccessoryValues> paramHashMap) {
        Object localObject1 = null;
        long l = 0;
        if ((paramHashMap != null) && (paramHashMap.size() > 0)) {
            localObject1 = getDateString(System.currentTimeMillis());
            if (paramHashMap.containsKey(localObject1)) {
                l = ((AccessoryValues) paramHashMap.get(localObject1)).sleep_startTime;
                localObject1 = paramHashMap.keySet().iterator();
            }
        }
        while (true) {
            if (!((Iterator) localObject1).hasNext())
                return;
            Object localObject2 = ((AccessoryValues) paramHashMap.get((String) ((Iterator) localObject1).next())).sleepdetail;
            if ((localObject2 != null) && (((HashMap) localObject2).size() > 0)) {
                localObject2 = ((HashMap) localObject2).keySet().iterator();
                while (((Iterator) localObject2).hasNext())
                    if (((Long) ((Iterator) localObject2).next()).longValue() >= l)
                        todaySleep += 200;
            }
        }
    }

    private void calcSleepInTransacntion(HashMap<String, AccessoryValues> paramHashMap) {
        if ((paramHashMap == null) || (paramHashMap.size() == 0))
            return;
        long l1 = calcuEndtime(System.currentTimeMillis());
        Iterator localIterator1 = paramHashMap.keySet().iterator();
        todaySleep = 0;
        while (true) {
            if (!localIterator1.hasNext())
                return;
            AccessoryValues localAccessoryValues = (AccessoryValues) paramHashMap.get((String) localIterator1.next());
            localAccessoryValues.sleep_endTime = 0L;
            localAccessoryValues.sleep_startTime = 0L;
            long l2 = calcuEndtime(localAccessoryValues.start_sport_time);
            insetDB(l2, -1);
            l2 += 200000L;
            insetDB(l2, -1);
            l2 += 200000L;
            insetDB(l2, -1);
            Iterator localIterator2 = localAccessoryValues.sleepdetail.keySet().iterator();
            while (localIterator2.hasNext()) {
                Long localLong = (Long) localIterator2.next();
                if (localLong.longValue() < l1) {
                    int i = ((Integer) localAccessoryValues.sleepdetail.get(localLong)).intValue();
                    insetDB(localLong.longValue(), i);
                }
            }
        }
    }

    private long calcuEndtime(long paramLong) {
        int j = 600000;
        int i = j;
        if (paramLong % j == 0L)
            i = 0;
        return paramLong / 600000L * 600000L + i;
    }

    public static long[] getCurrentData(HashMap<String, AccessoryValues> paramHashMap) {
        if ((paramHashMap != null) && (paramHashMap.size() > 0)) {
            long[] arrayOfLong = new long[17];
            int i = 0;
            Object localObject = null;
            if (i >= arrayOfLong.length) {
                localObject = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis()));
                if (paramHashMap.containsKey(localObject)) {
                    localObject = (AccessoryValues) paramHashMap.get(localObject);
                    arrayOfLong[0] = ((AccessoryValues) localObject).start_sport_time;
                    arrayOfLong[1] = ((AccessoryValues) localObject).sport_duration;
                    arrayOfLong[2] = ((AccessoryValues) localObject).steps;
                    arrayOfLong[3] = ((AccessoryValues) localObject).calories;
                    arrayOfLong[4] = ((AccessoryValues) localObject).distances;
                    arrayOfLong[10] = ((AccessoryValues) localObject).deepSleep;
                    arrayOfLong[11] = ((AccessoryValues) localObject).light_sleep;
                    arrayOfLong[12] = ((AccessoryValues) localObject).wake_time;
                    arrayOfLong[13] = todaySleep / 60;
                    arrayOfLong[14] = ((AccessoryValues) localObject).sleep_startTime;
                    arrayOfLong[15] = ((AccessoryValues) localObject).sleep_endTime;
                }
                localObject = paramHashMap.keySet().iterator();
            }
            AccessoryValues localAccessoryValues;
            while (true) {
                if (!((Iterator) localObject).hasNext()) {
                    todaySleep = 0;
                    arrayOfLong[i] = 0L;
                    i += 1;
                    return arrayOfLong;

                }
                localAccessoryValues = (AccessoryValues) paramHashMap.get(((Iterator) localObject).next());
                if (arrayOfLong[5] != 0L)
                    if (arrayOfLong[5] < localAccessoryValues.start_sport_time) break;
                arrayOfLong[5] = localAccessoryValues.start_sport_time;
                arrayOfLong[6] += localAccessoryValues.sport_duration;
                arrayOfLong[7] += localAccessoryValues.steps;
                arrayOfLong[8] += localAccessoryValues.calories;
                arrayOfLong[9] += localAccessoryValues.distances;
                arrayOfLong[16] += localAccessoryValues.sleepmins;
            }
            if (arrayOfLong[5] < localAccessoryValues.start_sport_time) ;
            for (long l = arrayOfLong[5]; ; l = localAccessoryValues.start_sport_time) {
                arrayOfLong[5] = l;
                break;
            }
        }
        return null;
    }

    private String getDateString(long paramLong) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(paramLong));
    }

    public HashMap<String, AccessoryValues> analysisDatas(ArrayList<ArrayList<Integer>> paramArrayList) {
        Object localObject1;
        if ((paramArrayList == null) || (paramArrayList.size() < 1)) {
            return null;
        }
        String str = "";
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        Object localObject2 = localArrayList2;
        int i = 0;
        Iterator localIterator1 = paramArrayList.iterator();
        while (true) {
            if (!localIterator1.hasNext()) {
                SleepDataUtil.saveInfo2File(str);
                localObject1 = _analysis(localArrayList1);
                break;
            }
            Iterator localIterator2 = ((ArrayList) localIterator1.next()).iterator();
            while (localIterator2.hasNext()) {
                Integer localInteger = (Integer) localIterator2.next();
                ((ArrayList) localObject2).add(localInteger);
                StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str));
                str = localInteger + "  ";
                i++;
                if (i == 6) {
                    StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str));
                    str = "\r\n";
                    i = 0;
                    localArrayList1.add(localObject2);
                    ArrayList localArrayList3 = new ArrayList();
                    localObject2 = localArrayList3;
                }
            }
        }
        return (HashMap<String, AccessoryValues>) localObject2;
    }

    public void getSleepTotalIntrans(String paramString, long paramLong1, long paramLong2, HashMap<String, AccessoryValues> paramHashMap) {
        List localList = this.mSleepDetailDB.get(paramString, paramLong1, paramLong2);
        ArrayList localArrayList = null;
        Object localObject1 = null;
        int j = 0;
        int k = 0;
        if ((localList != null) && (localList.size() > 0)) {
            int i = localList.size();
            localArrayList = new ArrayList();
            AccessoryValues localAccessoryValues1 = new AccessoryValues();
            localObject1 = localAccessoryValues1;
            ((AccessoryValues) localObject1).sleep_startTime = paramLong1;
            ((AccessoryValues) localObject1).sleep_endTime = paramLong2;
            j = 0;
            if (j >= i) {
                if (localArrayList.size() == 0)
                    localArrayList.add(localObject1);
                StringBuilder localStringBuilder2 = new StringBuilder(" find start begin from:");
                String str2 = paramLong1 + " end:" + paramLong2;
                Log.d(this.TAG, str2);
                k = 0;
                if (k < localArrayList.size()) {
                    AccessoryValues localAccessoryValues3 = (AccessoryValues) localArrayList.get(k);
                }
                if (localArrayList.size() == 0)
                    Log.e(this.TAG, "not find start or end");
            }
        }
        while (true) {
            assert localList != null;
            SleepDetailTB localSleepDetailTB = (SleepDetailTB) localList.get(j);
            if ((((AccessoryValues) localObject1).sleep_startTime == 0L) && (localSleepDetailTB.time != 0L)) {
                Object localObject4 = localObject1;
                Object localObject5 = localObject1;
                long l3 = localSleepDetailTB.time;
                ((AccessoryValues) localObject5).sleep_endTime = l3;
                ((AccessoryValues) localObject4).sleep_startTime = l3;
            }
            do {
                j++;

                if (localSleepDetailTB.type != -1) {
                    Object localObject3 = localObject1;
                    if (((AccessoryValues) localObject1).sleep_endTime < localSleepDetailTB.time) ;
                    for (long l2 = localSleepDetailTB.time; ; l2 = ((AccessoryValues) localObject1).sleep_endTime) {
                        ((AccessoryValues) localObject3).sleep_endTime = l2;
                        break;
                    }

                }
            }
            while (localSleepDetailTB.type != -1);
            Object localObject2 = localObject1;
            if (((AccessoryValues) localObject1).sleep_endTime < localSleepDetailTB.time) ;
            for (long l1 = localSleepDetailTB.time; ; l1 = ((AccessoryValues) localObject1).sleep_endTime) {
                ((AccessoryValues) localObject2).sleep_endTime = l1;
                if (0L == ((AccessoryValues) localObject1).sleep_startTime)
                    break;
                localArrayList.add(localObject1);
                AccessoryValues localAccessoryValues2 = new AccessoryValues();
                localObject1 = localAccessoryValues2;
                break;
            }
            assert localArrayList != null;
            AccessoryValues localAccessoryValues3 = (AccessoryValues) localArrayList.get(k);
            String str3 = getDateString(localAccessoryValues3.sleep_endTime);
            AccessoryValues localAccessoryValues5 = null;
            long l5;
            AccessoryValues localAccessoryValues6 = null;
            if (!paramHashMap.containsKey(str3)) {
                AccessoryValues localAccessoryValues4 = new AccessoryValues();
                localAccessoryValues5 = localAccessoryValues4;
                paramHashMap.put(str3, localAccessoryValues5);
                localAccessoryValues5.time = str3;
                if (localAccessoryValues5.sleep_startTime == 0L)
                    for (long l4 = localAccessoryValues5.sleep_endTime; ; l4 = localAccessoryValues3.sleep_endTime) {
                        localAccessoryValues6.sleep_endTime = l4;
                        k++;
                        localAccessoryValues5 = (AccessoryValues) paramHashMap.get(str3);
                        l5 = localAccessoryValues5.sleep_startTime;
                        localAccessoryValues5.sleep_startTime = localAccessoryValues3.sleep_startTime;
                        break;
                    }
                AccessoryValues localAccessoryValues7 = localAccessoryValues5;
                if (localAccessoryValues3.sleep_startTime >= localAccessoryValues5.sleep_startTime)
                    for (long l4 = localAccessoryValues5.sleep_endTime; ; l4 = localAccessoryValues3.sleep_endTime) {
                        localAccessoryValues6.sleep_endTime = l4;
                        k++;
                        localAccessoryValues5 = (AccessoryValues) paramHashMap.get(str3);
                        l5 = localAccessoryValues5.sleep_startTime;
                        localAccessoryValues5.sleep_startTime = localAccessoryValues3.sleep_startTime;
                        break;
                    }
                l5 = localAccessoryValues3.sleep_startTime;
                localAccessoryValues7.sleep_startTime = l5;
                localAccessoryValues6 = localAccessoryValues5;
                if (localAccessoryValues5.sleep_endTime <= localAccessoryValues3.sleep_endTime)
                    for (long l4 = localAccessoryValues5.sleep_endTime; ; l4 = localAccessoryValues3.sleep_endTime) {
                        localAccessoryValues6.sleep_endTime = l4;
                        k++;
                        localAccessoryValues5 = (AccessoryValues) paramHashMap.get(str3);
                        l5 = localAccessoryValues5.sleep_startTime;
                        localAccessoryValues5.sleep_startTime = localAccessoryValues3.sleep_startTime;
                        break;
                    }
                return;

            }
            for (long l4 = localAccessoryValues5.sleep_endTime; ; l4 = localAccessoryValues3.sleep_endTime) {
                localAccessoryValues6.sleep_endTime = l4;
                k++;
                localAccessoryValues5 = (AccessoryValues) paramHashMap.get(str3);
                l5 = localAccessoryValues5.sleep_startTime;
                localAccessoryValues5.sleep_startTime = localAccessoryValues3.sleep_startTime;
                break;
            }
            String str1 = this.TAG;
            StringBuilder localStringBuilder1 = new StringBuilder("not find detail between ");
            Log.e(str1, paramLong1 + " end " + paramLong2);
        }
    }

    public SleepDetailTB insetDB(long paramLong, int paramInt) {
        SleepDetailTB localSleepDetailTB = this.mSleepDetailDB.get(AccessoryConfig.userID, paramLong);
        if (localSleepDetailTB == null) {
            localSleepDetailTB = new SleepDetailTB();
            localSleepDetailTB.time = paramLong;
            localSleepDetailTB.userid = AccessoryConfig.userID;
            localSleepDetailTB.sleepvalue = paramInt;
            localSleepDetailTB.type = getSleepLevelByTime(localSleepDetailTB.sleepvalue);
            this.mSleepDetailDB.insert(localSleepDetailTB);
            return localSleepDetailTB;
        }
        localSleepDetailTB.time = paramLong;
        localSleepDetailTB.userid = AccessoryConfig.userID;
        if (paramInt != -1)
            if (localSleepDetailTB.sleepvalue > paramInt)
                paramInt = localSleepDetailTB.sleepvalue;
        for (localSleepDetailTB.sleepvalue = paramInt; ; localSleepDetailTB.sleepvalue = -1) {
            localSleepDetailTB.type = getSleepLevelByTime(localSleepDetailTB.sleepvalue);
            this.mSleepDetailDB.update(localSleepDetailTB);
            break;
        }
        return localSleepDetailTB;
    }
}
