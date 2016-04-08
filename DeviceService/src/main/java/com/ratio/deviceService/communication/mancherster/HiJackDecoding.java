package com.ratio.deviceService.communication.mancherster;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class HiJackDecoding {
    private final int DECODE = 2;
    private final int LONG = 48;
    private final int SAMPLESPERBIT = 32;
    private final int SHORT = 24;
    private final int STARTBIT = 0;
    private final int STARTBIT_FALL = 1;
    private int THRESHOLD = 500;
    int bitNum = 0;
    int byteCounter = 1;
    int decState = 0;
    private int fallBit = 1;
    int lastPhase2 = 0;
    int lastSample = 0;
    private INumberCallback mCallback;
    int parityRx = 0;
    int parityTx = 0;
    int phase = 0;
    int phase2 = 0;
    int sample = 0;
    private int startBit = 0;
    int uartByte = 0;

    public HiJackDecoding(INumberCallback paramINumberCallback) {
        this.mCallback = paramINumberCallback;
    }

    private void sendDataToUI(int paramInt) {
        this.mCallback.getNumber(paramInt);
    }

    public void decoding(short[] paramArrayOfShort, int paramInt) {
        int i = 0;
        if (i >= paramInt)
            return;
        float f = paramArrayOfShort[i];
        this.phase2 += 1;
        int j = 0;
        if (f < this.THRESHOLD) {
            this.sample = this.startBit;
            if (this.sample != this.lastSample) {
                j = this.phase2 - this.lastPhase2;
                switch (this.decState) {
                    default:
                        this.lastPhase2 = this.phase2;
                    case 0:
                    case 1:
                    case 2:
                }
            }
        }
        for (this.lastSample = this.sample; ; this.lastSample = this.sample) {
            i += 1;
            this.sample = this.fallBit;
            if ((this.lastSample != 0) || (this.sample != 1))
                this.lastPhase2 = this.phase2;
            this.decState = 1;
            this.lastPhase2 = this.phase2;
            if ((24 < j) && (j < 48)) {
                this.bitNum = 0;
                this.parityRx = 0;
                this.uartByte = 0;
                this.decState = 2;
                this.lastPhase2 = this.phase2;
            }
            this.decState = 0;
            this.lastPhase2 = this.phase2;
            if ((24 < j) && (j < 48)) {
                if (this.bitNum < 8) {
                    this.uartByte = ((this.uartByte >> 1) + (this.sample << 7));
                    this.bitNum += 1;
                    this.parityRx += this.sample;
                    this.lastPhase2 = this.phase2;
                }
                if (this.bitNum == 8) {
                    if (this.sample != (this.parityRx & 0x1)) {
                        this.decState = 0;
                        this.lastPhase2 = this.phase2;
                    }
                    this.bitNum += 1;
                    this.lastPhase2 = this.phase2;
                }
                if (this.sample == 1)
                    sendDataToUI(this.uartByte);
                this.decState = 0;
                this.lastPhase2 = this.phase2;
            }
            if (j > 48) {
                this.decState = 0;
                this.lastPhase2 = this.phase2;
            }
            break;
        }
    }

    public void resetRaiseBit(boolean paramBoolean) {
        if (paramBoolean) {
            this.startBit = 0;
            this.fallBit = 1;
            return;
        }
        this.startBit = 1;
        this.fallBit = 0;
    }

    public void setThreshold(int paramInt) {
        this.THRESHOLD = paramInt;
    }
}
