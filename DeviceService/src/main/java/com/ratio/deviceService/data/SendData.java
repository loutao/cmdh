package com.ratio.deviceService.data;

import android.util.Log;

import com.ratio.deviceService.bean.Person;
import com.ratio.deviceService.communication.DataUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class SendData {
    private static String TAG = "SendData";

    public static int[] getPostBindOrder() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 65;
        arrayOfInt[3] = 235;
        return arrayOfInt;
    }

    public static int[] getPostClearSportData() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 20;
        arrayOfInt[3] = 190;
        return arrayOfInt;
    }

    public static int[] getPostConnection() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 1;
        arrayOfInt[3] = 171;
        return arrayOfInt;
    }

    public static int[] getPostDeviceID() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 4;
        arrayOfInt[3] = 174;
        return arrayOfInt;
    }

    public static int[] getPostDeviceTime() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 11;
        arrayOfInt[3] = 181;
        return arrayOfInt;
    }

    public static int[] getPostDeviceTypeVersion() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 2;
        arrayOfInt[3] = 172;
        return arrayOfInt;
    }

    public static int[] getPostGetUserInfo() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 7;
        arrayOfInt[3] = 177;
        return arrayOfInt;
    }

    public static int[] getPostGetUserInfo2() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 8;
        arrayOfInt[3] = 178;
        return arrayOfInt;
    }

    public static int[] getPostReadSportData(int paramInt) {
        Log.d(TAG, "Frame:" + paramInt);
        int i = paramInt >> 8;
        paramInt &= 255;
        return new int[]{170, 17, 2, i, paramInt, 189 + i + paramInt & 0xFF};
    }

    public static int[] getPostSyncDataByFrame() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 12;
        arrayOfInt[3] = 182;
        return arrayOfInt;
    }

    public static int[] getPostSyncTime(long paramLong) {
        int[] arrayOfInt = new int[11];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 10;
        arrayOfInt[2] = 7;
        int j = 0 + '»';
        SimpleDateFormat localObject = new SimpleDateFormat("yy MM dd HH mm ss");
        localObject.setTimeZone(TimeZone.getDefault());
        Date localDate = new Date(paramLong);
        String[] arrayOfString = localObject.format(localDate).split(" ");
        int i = 0;
        while (true)
        {
            if (i >= 6)
            {
                Calendar localCalendar = Calendar.getInstance();
                localCalendar.setTime(localDate);
                arrayOfInt[9] = Integer.valueOf(localCalendar.get(Calendar.DAY_OF_WEEK)).intValue();
                arrayOfInt[(arrayOfInt.length - 1)] = (arrayOfInt[9] + j & 0xFF);
                return arrayOfInt;
            }
            arrayOfInt[(i + 3)] = Integer.valueOf(arrayOfString[i], 16).intValue();
            j += arrayOfInt[(i + 3)];
            i += 1;
        }
    }

    public static int[] getPostUpdateSportInfo(int[] paramArrayOfInt) {
        int[] arrayOfInt = new int[3 + paramArrayOfInt.length + 1];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 5;
        arrayOfInt[2] = 14;
        int j = 0 + '½';
        int i = 0;
        while (true) {
            if (i >= paramArrayOfInt.length) {
                arrayOfInt[(arrayOfInt.length - 1)] = (j & 0xFF);
                return arrayOfInt;
            }
            arrayOfInt[(i + 3)] = paramArrayOfInt[i];
            j += paramArrayOfInt[i];
            i += 1;
        }
    }

    public static int[] getPostUpdateUser2(int[] paramArrayOfInt) {
        int[] arrayOfInt = new int[4 + paramArrayOfInt.length];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 6;
        arrayOfInt[2] = paramArrayOfInt.length;
        int i = 0;
        if (i >= paramArrayOfInt.length) ;
        for (int j = 0; ; j++) {
            if (j >= -1 + arrayOfInt.length) {
                arrayOfInt[(-1 + arrayOfInt.length)] = (0xFF & arrayOfInt[(-1 + arrayOfInt.length)]);

                arrayOfInt[(3 + i)] = paramArrayOfInt[i];
                i++;
                break;
            }
            int k = -1 + arrayOfInt.length;
            arrayOfInt[k] += arrayOfInt[j];
        }
        return arrayOfInt;
    }

    public static int[] getPostUpdateUserInfoAll(int[] paramArrayOfInt) {
        int[] arrayOfInt = new int[3 + paramArrayOfInt.length + 1];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 5;
        arrayOfInt[2] = 14;
        int j = 0 + '½';
        int i = 0;
        while (true) {
            if (i >= paramArrayOfInt.length) {
                arrayOfInt[(arrayOfInt.length - 1)] = (j & 0xFF);
                return arrayOfInt;
            }
            arrayOfInt[(i + 3)] = paramArrayOfInt[i];
            j += paramArrayOfInt[i];
            i += 1;
        }
    }

    public static int[] getPostWriteDeviceID(int paramInt, int[] paramArrayOfInt) {
        int[] arrayOfInt = new int[4 + paramArrayOfInt.length + 1];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 3;
        arrayOfInt[2] = 13;
        arrayOfInt[3] = paramInt;
        int i = 0 + (186 + paramInt);
        paramInt = 0;
        while (true) {
            if (paramInt >= paramArrayOfInt.length) {
                arrayOfInt[(arrayOfInt.length - 1)] = (i & 0xFF);
                return arrayOfInt;
            }
            arrayOfInt[(paramInt + 4)] = paramArrayOfInt[paramInt];
            i += paramArrayOfInt[paramInt];
            paramInt += 1;
        }
    }

    public static int[] postBlueFriendRequst() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 81;
        arrayOfInt[2] = 0;
        int i = 0;
        while (true) {
            if (i >= arrayOfInt.length - 1) {
                arrayOfInt[(arrayOfInt.length - 1)] &= 255;
                return arrayOfInt;
            }
            int j = arrayOfInt.length - 1;
            arrayOfInt[j] += arrayOfInt[i];
            i += 1;
        }
    }

    public static int[] postBlueFriendWarning() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 82;
        arrayOfInt[2] = 0;
        int i = 0;
        while (true) {
            if (i >= arrayOfInt.length - 1) {
                arrayOfInt[(arrayOfInt.length - 1)] &= 255;
                return arrayOfInt;
            }
            int j = arrayOfInt.length - 1;
            arrayOfInt[j] += arrayOfInt[i];
            i += 1;
        }
    }

    public static int[] postBlueFriendsSwitch(int paramInt) {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 82;
        arrayOfInt[2] = 0;
        paramInt = 0;
        while (true) {
            if (paramInt >= arrayOfInt.length - 1) {
                arrayOfInt[(arrayOfInt.length - 1)] &= 255;
                return arrayOfInt;
            }
            int i = arrayOfInt.length - 1;
            arrayOfInt[i] += arrayOfInt[paramInt];
            paramInt += 1;
        }
    }

    public static int[] postBootEnd(int paramInt) {
        int[] arrayOfInt = new int[6];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 116;
        arrayOfInt[2] = 2;
        arrayOfInt[3] = (paramInt >> 8);
        arrayOfInt[4] = (paramInt & 0xFF);
        paramInt = 0;
        while (true) {
            if (paramInt >= arrayOfInt.length - 1) {
                arrayOfInt[5] &= 255;
                DataUtil.DebugPrint(arrayOfInt);
                return arrayOfInt;
            }
            arrayOfInt[5] += arrayOfInt[paramInt];
            paramInt += 1;
        }
    }

    public static int[] postBootMode() {
        return new int[]{170, 112, 0, 0 + 282 & 0xFF};
    }

    public static int[] postBootUploadData(int paramInt, byte[] paramArrayOfByte) {
        int i = 0;
        int[] arrayOfInt = new int[6 + paramArrayOfByte.length];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 115;
        arrayOfInt[2] = 14;
        arrayOfInt[3] = (paramInt >> 8);
        arrayOfInt[4] = (paramInt & 0xFF);
        int j = 0;
        String str = null;
        int k;
        int m = 0;
        if (j >= paramArrayOfByte.length) {
            str = "";
            k = 0;
            if (k < -1 + arrayOfInt.length)
                i += arrayOfInt[k];
            m = 0xFF & i;
            arrayOfInt[(-1 + arrayOfInt.length)] = m;
        }
        for (int n = 0; ; n++) {
            if (n >= arrayOfInt.length) {
                StringBuilder localStringBuilder1 = new StringBuilder(String.valueOf(str));
                Log.d("CodoonDeviceUpgradeManager", "   lenth:" + arrayOfInt.length);

                arrayOfInt[(5 + j)] = (0xFF & paramArrayOfByte[j]);
                j++;
                break;

            }
            m += arrayOfInt[n];
            StringBuilder localStringBuilder2 = new StringBuilder(String.valueOf(str));
            str = "," + Integer.toHexString(arrayOfInt[n]);
        }
        return arrayOfInt;
    }

    public static int[] postBootUploadData(int paramInt1, byte[] paramArrayOfByte, int paramInt2) {
        int i = 0;
        int[] arrayOfInt = new int[6 + paramInt2];
        arrayOfInt[0] = 170;
        arrayOfInt[1] = 115;
        arrayOfInt[2] = (2 + paramInt2);
        arrayOfInt[3] = (0xFF & paramInt1 >> 8);
        arrayOfInt[4] = (paramInt1 & 0xFF);
        int j = 0;
        if (j >= paramInt2) ;
        for (int k = 0; ; k++) {
            if (k >= -1 + arrayOfInt.length) {
                int m = 0xFF & i;
                arrayOfInt[(-1 + arrayOfInt.length)] = m;
                DataUtil.DebugPrint(arrayOfInt);

                arrayOfInt[(5 + j)] = (0xFF & paramArrayOfByte[j]);
                j++;
                break;
            }
            i += arrayOfInt[k];
        }
        return arrayOfInt;
    }

    public static int[] postConnectBootOrder() {
        return new int[]{170, 113, 0, 0 + 283 & 0xFF};
    }

    public static int[] postConnectBootVersion() {
        return new int[]{170, 114, 0, 0 + 284 & 0xFF};
    }

    public static int[] postWeightInfo(Person paramPerson) {
        Object localObject;
        if (paramPerson == null) {
            return null;
        }
        int[] arrayOfInt = new int[18];
        int i = 0;
        if (i >= arrayOfInt.length) {
            arrayOfInt[0] = 104;
            arrayOfInt[1] = 5;
            arrayOfInt[2] = 14;
            arrayOfInt[3] = paramPerson.group;
            arrayOfInt[4] = paramPerson.sex;
            arrayOfInt[5] = paramPerson.level;
            arrayOfInt[6] = paramPerson.height;
            arrayOfInt[7] = paramPerson.age;
            arrayOfInt[8] = 1;
        }
        for (int j = 0; ; j++) {
            if (j >= -1 + arrayOfInt.length) {
                arrayOfInt[(-1 + arrayOfInt.length)] = (0xFF & arrayOfInt[(-1 + arrayOfInt.length)]);
                arrayOfInt[i] = 0;
                i++;
                break;
            }
            int k = -1 + arrayOfInt.length;
            arrayOfInt[k] += arrayOfInt[j];
        }
        return arrayOfInt;
    }

    public static int[] postWeightScaleConnect() {
        int[] arrayOfInt = new int[4];
        arrayOfInt[0] = 104;
        arrayOfInt[1] = 1;
        arrayOfInt[2] = 0;
        int i = 0;
        while (true) {
            if (i >= arrayOfInt.length - 1) {
                arrayOfInt[(arrayOfInt.length - 1)] &= 255;
                return arrayOfInt;
            }
            int j = arrayOfInt.length - 1;
            arrayOfInt[j] += arrayOfInt[i];
            i += 1;
        }
    }

    private static void setCheckValue(int[] paramArrayOfInt) {
        if (paramArrayOfInt == null)
            return;
        int i = 0;
        while (true) {
            if (i >= paramArrayOfInt.length - 1) {
                paramArrayOfInt[(paramArrayOfInt.length - 1)] &= 255;
                return;
            }
            int j = paramArrayOfInt.length - 1;
            paramArrayOfInt[j] += paramArrayOfInt[i];
            i += 1;
        }
    }
}
