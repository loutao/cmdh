package com.ratio.deviceService.communication.mancherster;

import java.util.ArrayList;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class ManchesterEncoding implements CstCode {
    public static short[] bytesHigh = {32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767};
    public static short[] bytesLow = {-32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767};
    private final int bitLength = 8;
    public ArrayList<short[]> byteStartStopList;

    private String charToManBinary(int paramInt) {
        String str1 = Integer.toBinaryString(paramInt);
        String str2 = "0000000".substring(0, 8 - str1.length()) + str1;
        str1 = "10";
        int i = 0;
        paramInt = 7;
        if (paramInt < 0)
            if (i % 2 != 0)
                for (str1 = str1 + "10"; ; str1 = str1 + "01") {

                    if (str2.charAt(paramInt) == '1')
                        i += 1;
                    for (str1 = str1 + "01"; ; str1 = str1 + "10") {
                        paramInt -= 1;
                        break;
                    }
                }
        return str1 + "01";
    }

    public static void resetHighLowBit(boolean paramBoolean) {
        if (!paramBoolean) {
            bytesLow = new short[]{32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767};
            bytesHigh = new short[]{-32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767};
            return;
        }
        bytesHigh = new short[]{32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767};
        bytesLow = new short[]{-32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767, -32767, 32767};
    }

    public ArrayList<short[]> getManchesterCode(int paramInt) {
        String str = charToManBinary(paramInt);
        int i = str.length();
        ArrayList localArrayList = new ArrayList();
        paramInt = 0;
        if (paramInt >= i)

            if (str.charAt(paramInt) == '0')
                localArrayList.add(bytesHigh);
        while (true) {
            paramInt += 1;

            localArrayList.add(bytesLow);
            break;
        }
        return localArrayList;
    }

    public ArrayList<short[]> getStart() {
        this.byteStartStopList = new ArrayList();
        int i = 0;
        while (true) {
            if (i >= 120)
                return this.byteStartStopList;
            this.byteStartStopList.add(bytesHigh);
            this.byteStartStopList.add(bytesLow);
            i += 1;
        }
    }

    public ArrayList<short[]> getStop() {
        this.byteStartStopList = new ArrayList();
        int i = 0;
        while (true) {
            if (i >= 5)
                return this.byteStartStopList;
            this.byteStartStopList.add(bytesLow);
            this.byteStartStopList.add(bytesHigh);
            i += 1;
        }
    }
}
