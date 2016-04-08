package com.ratio.deviceService.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.ratio.deviceService.communication.provider.SleepDetailDB;
import com.ratio.deviceService.communication.provider.SleepDetailTB;
import com.ratio.deviceService.datamanager.AccessoryValues;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/24.
 */
public class SleepDataUtil {

    private int index;
    private Context mContext;
    SleepDetailDB mSleepDetailDB = null;

    public SleepDataUtil(Context paramContext) {
        this.mContext = paramContext;
        SleepDetailDB localSleepDetailDB = new SleepDetailDB(paramContext);
        this.mSleepDetailDB = localSleepDetailDB;
    }

    public static String saveInfo2File(String paramString) {
        return null;
    }

    public void combineSame(List<SleepDetailTB> paramList) {
        if (1 + this.index < paramList.size()) {
            SleepDetailTB localSleepDetailTB1 = (SleepDetailTB) paramList.get(this.index);
            SleepDetailTB localSleepDetailTB2 = (SleepDetailTB) paramList.get(1 + this.index);
            if (localSleepDetailTB1.type != localSleepDetailTB2.type)
                this.index = (1 + this.index);
            paramList.remove(localSleepDetailTB2);
        }
        while (true) {

            this.index = (1 + this.index);
            return;
        }
    }

    public String getSleepDetail(String paramString, long paramLong1, long paramLong2) {
        this.mSleepDetailDB.open();
        List localList = this.mSleepDetailDB.get(paramString, paramLong1, paramLong2);
        this.mSleepDetailDB.close();
        String str1 = "";
        Iterator localIterator1 = null;
        Iterator localIterator2 = null;
        if ((localList != null) && (localList.size() > 0)) {
            this.index = 0;
            localIterator1 = localList.iterator();
            if (!localIterator1.hasNext()) {
                do
                    combineSame(localList);
                while (1 + this.index < localList.size());
                localIterator2 = localList.iterator();
                label101:
                if (localIterator2.hasNext()) ;

            }
        }
        for (String str2 = str1; ; str2 = null) {

            SleepDetailTB localSleepDetailTB1 = (SleepDetailTB) localIterator1.next();
            localSleepDetailTB1.type = Math.abs(localSleepDetailTB1.type);

            SleepDetailTB localSleepDetailTB2 = (SleepDetailTB) localIterator2.next();
            if (TextUtils.isEmpty(str1)) {
                StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str1));
                str1 = "[" + localSleepDetailTB2.time / 1000L + "," + localSleepDetailTB2.type + "]";
                break;
            }
            StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str1));
            str1 = ",[" + localSleepDetailTB2.time / 1000L + "," + localSleepDetailTB2.type + "]";
            return str2;
        }
        return null;
    }

    public AccessoryValues getSleepTotal(String paramString, long paramLong1, long paramLong2) {
        this.mSleepDetailDB.open();
        List localList = this.mSleepDetailDB.get(paramString, paramLong1, paramLong2);
        this.mSleepDetailDB.close();
        String str = "";
        int i = 0;
        AccessoryValues localAccessoryValues2 = null;
        Iterator localIterator = null;
        if ((localList != null) && (localList.size() > 0)) {
            StringBuilder localStringBuilder1 = new StringBuilder("read size ");
            Log.d("enlong", localList.size() + "");
            localAccessoryValues2 = new AccessoryValues();
            localIterator = localList.iterator();
            if (!localIterator.hasNext()) {
                saveInfo2File(str);
                localAccessoryValues2.sleep_startTime = paramLong1;
                localAccessoryValues2.sleep_endTime = paramLong2;
                localAccessoryValues2.sleepmins += (localAccessoryValues2.deepSleep + localAccessoryValues2.light_sleep + localAccessoryValues2.wake_time) / 60;
                localAccessoryValues2.deepSleep /= 60;
                localAccessoryValues2.light_sleep /= 60;
                localAccessoryValues2.wake_time = (localAccessoryValues2.sleepmins - localAccessoryValues2.deepSleep - localAccessoryValues2.light_sleep);
            }
        }
        for (AccessoryValues localAccessoryValues1 = localAccessoryValues2; ; localAccessoryValues1 = null) {

            SleepDetailTB localSleepDetailTB = (SleepDetailTB) localIterator.next();
            if (localSleepDetailTB.type == 3)
                localAccessoryValues2.deepSleep = (200 + localAccessoryValues2.deepSleep);
            while (true) {
                if (i % 3 == 0) {
                    StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str));
                    str = "\n";
                }
                i++;
                StringBuilder localStringBuilder3 = new StringBuilder(String.valueOf(str));
                str = localSleepDetailTB.sleepvalue + " ";

                if (localSleepDetailTB.type == 2)
                    localAccessoryValues2.light_sleep = (200 + localAccessoryValues2.light_sleep);
                else if (localSleepDetailTB.type == 1)
                    localAccessoryValues2.wake_time = (200 + localAccessoryValues2.wake_time);
                break;
            }
            return localAccessoryValues1;
        }
    }

    public int updateSleepDetail(String paramString, long paramLong1, long paramLong2, int[] paramArrayOfInt) {
        this.mSleepDetailDB.open();
        this.mSleepDetailDB.deleteBetweenTime(paramString, paramLong1, paramLong2);
        if (paramArrayOfInt != null) ;
        for (int i = 0; ; i++) {
            if (i >= paramArrayOfInt.length) {
                this.mSleepDetailDB.close();
                return 0;
            }
            SleepDetailTB localSleepDetailTB = new SleepDetailTB();
            localSleepDetailTB.time = (paramLong1 + 1000 * (200 * i));
            localSleepDetailTB.sleepvalue = paramArrayOfInt[i];
            localSleepDetailTB.type = LocalDecode.getSleepLevelByTime(localSleepDetailTB.sleepvalue);
            localSleepDetailTB.userid = paramString;
            this.mSleepDetailDB.insert(localSleepDetailTB);
        }
    }
}
