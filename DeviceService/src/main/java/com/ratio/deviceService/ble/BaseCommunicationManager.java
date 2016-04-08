package com.ratio.deviceService.ble;

import android.content.Context;

import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.TimeoutCheck;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract class BaseCommunicationManager implements CommunicationMethod {
    protected int frameCount = 0;
    protected int indexFrame = 0;
    protected boolean isStart;
    protected ByteArrayOutputStream mBaos;
    protected String mCharacteristicUUID;
    protected String mClicentUUID;
    protected Context mContext;
    protected List<ISyncDataCallback> mISyncDataCallbacks;
    protected int[] mLastSendData;
    protected ArrayList<ArrayList<Integer>> mRecordDatas;
    protected TimeoutCheck mTimeoutCheck;
    protected String mWriteCharacteristicUUID = "00002a19-0000-1000-8000-00805f9b34fb";
    protected String mWriteClicentUUID = "0000180f-0000-1000-8000-00805f9b34fb";
    protected final byte[] xorKey;

    public BaseCommunicationManager() {
        byte[] arrayOfByte = new byte[6];
        arrayOfByte[0] = 84;
        arrayOfByte[1] = -111;
        arrayOfByte[2] = 40;
        arrayOfByte[3] = 21;
        arrayOfByte[4] = 87;
        arrayOfByte[5] = 38;
        this.xorKey = arrayOfByte;
    }
}
