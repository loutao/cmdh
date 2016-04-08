package com.ratio.deviceService.communication.mancherster;

import java.util.ArrayList;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class ReceiveManager {
    private int ID = 0;
    private int index = 0;
    private boolean isStart = false;
    private int length = 0;
    private ArrayList<Integer> list = new ArrayList();
    private INumberCallback mCallback = new INumberCallback() {
        public void getNumber(int paramAnonymousInt) {
            ReceiveManager.this.receive(paramAnonymousInt);
        }
    };
    private Recorder mRecorder;
    private ManDeviceDataManager mSyncDeviceDataManager;

    public ReceiveManager(ManDeviceDataManager paramManDeviceDataManager) {
        this.mSyncDeviceDataManager = paramManDeviceDataManager;
        this.mRecorder = new Recorder(this.mCallback);
    }

    private void checkData(int paramInt, ArrayList<Integer> paramArrayList) {
        if (isValid(paramArrayList))
            this.mSyncDeviceDataManager.analysis(paramArrayList);
    }

    private boolean isValid(ArrayList<Integer> paramArrayList) {
        int k = paramArrayList.size() - 1;
        if ((k > 2) && (k - 3 != ((Integer) paramArrayList.get(2)).intValue()))
            return false;
        int j = 0;
        int i = 0;
        while (true) {
            if (i >= k) {
                if ((j & 0xFF) != ((Integer) paramArrayList.get(k)).intValue())
                    break;
                return true;
            }
            j += ((Integer) paramArrayList.get(i)).intValue();
            i += 1;
        }
        return false;
    }

    private void receive(int paramInt) {
        if (paramInt == 170) {
            this.isStart = true;
            if (this.isStart) {
                this.index += 1;
                if (this.index != 2)
                    if (this.index == 3)
                        this.length = paramInt;
                this.ID = paramInt;
            }
        }
        while (true) {
            if (this.index - this.length != 4)
                this.list.add(Integer.valueOf(paramInt));
            this.list.add(Integer.valueOf(paramInt));
            checkData(this.ID, this.list);
            this.isStart = false;
            this.ID = 0;
            this.index = 0;
            this.length = 0;
            this.list = new ArrayList();
            if ((paramInt != -1) && (paramInt != -2))
                break;
            if (this.index != 0)
                this.list.add(Integer.valueOf(paramInt));
            if (this.list.size() > 2)
                checkData(this.ID, this.list);
            this.ID = 0;
            this.index = 0;
            this.length = 0;
            this.isStart = false;
            this.list = new ArrayList();
            break;

        }
        return;

    }

    public void resetRaiseBit(boolean paramBoolean) {
        this.mRecorder.resetRaiseBit(paramBoolean);
    }

    public void start() {
        this.mRecorder.setRecording(true);
        this.mRecorder.start();
    }

    public void stop() {
        this.mRecorder.stop();
    }
}
