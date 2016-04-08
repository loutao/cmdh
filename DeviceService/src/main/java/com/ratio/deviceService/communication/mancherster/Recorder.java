package com.ratio.deviceService.communication.mancherster;

import android.media.AudioRecord;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class Recorder implements CstCode, Runnable {
    private boolean isOpen = false;
    private boolean isRecording = false;
    private HiJackDecoding mDecoding;
    private AudioRecord recordInstance = null;
    private short[] tempBuffer;

    public Recorder(INumberCallback paramINumberCallback) {
        this.mDecoding = new HiJackDecoding(paramINumberCallback);
        int i = AudioRecord.getMinBufferSize(44100, 1, 2);
        this.recordInstance = new AudioRecord(1, 44100, 1, 2, i);
        this.tempBuffer = new short[i];
        this.recordInstance.startRecording();
        new Thread(this).start();
    }

    public void pause() {
        this.isOpen = false;
    }

    public void resetRaiseBit(boolean paramBoolean) {
        this.mDecoding.resetRaiseBit(paramBoolean);
    }

    public void restart() {
        this.isOpen = true;
    }

    public void run() {
        while (true) {
            if (!this.isRecording)
                return;
            if (this.isOpen) {
                int i = this.recordInstance.read(this.tempBuffer, 0, this.tempBuffer.length);
                if ((this.tempBuffer != null) && (this.tempBuffer.length > 0))
                    this.mDecoding.decoding(this.tempBuffer, i);
            }
        }
    }

    public void setRecording(boolean paramBoolean) {
        this.isRecording = paramBoolean;
    }

    public void setThreshold(int paramInt) {
        this.mDecoding.setThreshold(paramInt);
    }

    public void start() {
        this.isOpen = true;
    }

    public void stop() {
        setRecording(false);
        if (this.recordInstance != null) ;
        try {
            this.recordInstance.stop();
            this.recordInstance.release();
            this.recordInstance = null;
            this.isOpen = false;
            return;
        } catch (Exception localException) {
            this.recordInstance = null;
            this.isOpen = false;
            return;
        }
    }
}
