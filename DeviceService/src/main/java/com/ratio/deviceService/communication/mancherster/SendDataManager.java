package com.ratio.deviceService.communication.mancherster;

import android.media.AudioTrack;

import java.util.ArrayList;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class SendDataManager implements CstCode {
    private AudioTrack trackInstance = null;

    public SendDataManager() {
        initAudio();
    }

    private void initAudio() {
        this.trackInstance = new AudioTrack(3, 44100, 3, 2, AudioTrack.getMinBufferSize(44100, 3, 2), 1);
        this.trackInstance.play();
        this.trackInstance.setStereoVolume(1.0F, 1.0F);
    }

    public void reInitAudio() {
        stopAudio();
        this.trackInstance = new AudioTrack(1, 44100, 3, 2, AudioTrack.getMinBufferSize(44100, 3, 2), 1);
        this.trackInstance.play();
        this.trackInstance.setStereoVolume(1.0F, 1.0F);
    }

    public void resetEncoding(boolean paramBoolean) {
        ManchesterEncoding.resetHighLowBit(paramBoolean);
    }

    public void stopAudio() {
        if (this.trackInstance != null) {
            this.trackInstance.stop();
            this.trackInstance.release();
            this.trackInstance = null;
        }
    }

    public void write(int[] paramArrayOfInt) {
        while (true) {
            int i;
            try {
                new ArrayList();
                Object localObject = new ManchesterEncoding();
                ArrayList localArrayList = new ArrayList();
                i = 0;
                if (i >= paramArrayOfInt.length) {
                    localObject = ((ManchesterEncoding) localObject).getStart();
                    i = 0;
                    if (i >= ((ArrayList) localObject).size()) {
                        i = 0;
                        if (i < paramArrayOfInt.length)
                            continue;
                        if (this.trackInstance == null)
                            return;
                        this.trackInstance.flush();
                        this.trackInstance.reloadStaticData();
                    }
                } else {
                    localArrayList.add(((ManchesterEncoding) localObject).getManchesterCode(paramArrayOfInt[i]));
                    i += 1;
                    continue;
                }
                if (this.trackInstance == null)
                    i += 1;
                this.trackInstance.write((short[]) ((ArrayList) localObject).get(i), 0, 32);
                i += 1;
                localObject = (ArrayList) localArrayList.get(i);
                int j = 0;
                if (j >= ((ArrayList) localObject).size()) {
                    i += 1;
                    continue;
                }
                if (this.trackInstance != null)
                    this.trackInstance.write((short[]) ((ArrayList) localObject).get(j), 0, 32);
                j += 1;
                continue;
            } catch (Exception ex) {
            }
        }
    }
}
