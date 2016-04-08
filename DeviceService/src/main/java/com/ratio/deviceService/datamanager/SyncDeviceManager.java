package com.ratio.deviceService.datamanager;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;

import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.accessory.AccessoryManager;
import com.ratio.deviceService.bean.AccessoriesTotal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class SyncDeviceManager implements AccessoryConstOrder {
    protected static final String TAG = "SyncDeviceData";
    private static SyncDeviceManager instance;
    private Context mContext;
    List<Handler> mHandlers;
    private BaseAccessorySyncManager mSyncManager;

    public SyncDeviceManager(Context paramContext) {
        ArrayList localArrayList = new ArrayList();
        this.mHandlers = localArrayList;
        this.mContext = paramContext;
    }

    public SyncDeviceManager(Context paramContext, String paramString) {
        ArrayList localArrayList = new ArrayList();
        this.mHandlers = localArrayList;
        this.mContext = paramContext;
        setTargetDevice(paramString);
    }

    public static SyncDeviceManager getInstance(Context paramContext) {
        if (instance == null) {
            SyncDeviceManager localSyncDeviceManager = new SyncDeviceManager(paramContext);
            instance = localSyncDeviceManager;
        }
        return instance;
    }

    public static SyncDeviceManager getInstance(Context paramContext, Handler paramHandler, int paramInt, String paramString) {
        if (instance == null) {
            SyncDeviceManager localSyncDeviceManager = new SyncDeviceManager(paramContext);
            instance = localSyncDeviceManager;
        }
        instance.setTargetDevice(paramString);
        instance.setAction(paramInt);
        instance.registerHandler(paramHandler);
        return instance;
    }

    public void connectToDeviceRightNow(BluetoothDevice paramBluetoothDevice) {
        if (this.mSyncManager != null) {
            if (paramBluetoothDevice.getName() == null) ;
            for (String str1 = AccessoryConfig.getStringValue(this.mContext, paramBluetoothDevice.getAddress()); ; str1 = paramBluetoothDevice.getName()) {
                String str2 = str1;
                if ((str2 == null) || (!str2.contains("Smartband")))
                    break;
                this.mSyncManager.stopSyncDevice();
                this.mSyncManager.destroy();
                this.mSyncManager = null;
                this.mSyncManager = BLEDeviceFactory.getManagerByDeviceType(this.mContext, paramBluetoothDevice.getName());
                this.mSyncManager.setTargetDevice("Smartband");
                Iterator localIterator = this.mHandlers.iterator();
                while (localIterator.hasNext()) {
                    Handler localHandler = (Handler) localIterator.next();
                    this.mSyncManager.registerHandler(localHandler);
                }
            }
            BaseAccessorySyncManager.isStart = true;
            this.mSyncManager.setAction(1);
            this.mSyncManager.connectToDeviceRightNow(paramBluetoothDevice);
        }
    }

    public void destroy() {
        if (this.mSyncManager != null) {
            this.mSyncManager.destroy();
            this.mSyncManager = null;
        }
        this.mHandlers.clear();
    }

    public int getAction() {
        if (this.mSyncManager != null) ;
        for (int i = this.mSyncManager.getAction(); ; i = 0)
            return i;
    }

    public String getTargetDevice() {
        if (this.mSyncManager != null) ;
        for (String str = this.mSyncManager.getTargetDevice(); ; str = null)
            return str;
    }

    public AccessoriesTotal getUploadAccessoriesTotal() {
        if (this.mSyncManager != null) ;
        for (AccessoriesTotal localAccessoriesTotal = this.mSyncManager.getUploadAccessoriesTotal(); ; localAccessoriesTotal = null)
            return localAccessoriesTotal;
    }

    public void init() {
        if (this.mSyncManager != null)
            this.mSyncManager.init();
    }

    public boolean isStart() {
        return BaseAccessorySyncManager.isStart;
    }

    public void reBind() {
        if (this.mSyncManager != null)
            this.mSyncManager.reBind();
    }

    public void registerHandler(Handler paramHandler) {
        if ((paramHandler != null) && (!this.mHandlers.contains(paramHandler)))
            this.mHandlers.add(paramHandler);
        if (this.mSyncManager != null) {
            Iterator localIterator = this.mHandlers.iterator();
            while (localIterator.hasNext()) {
                Handler localHandler = (Handler) localIterator.next();
                this.mSyncManager.registerHandler(localHandler);
            }
        }
    }

    public void setAction(int paramInt) {
        if (this.mSyncManager != null)
            this.mSyncManager.setAction(paramInt);
    }

    public void setTargetDevice(String paramString) {
        if (this.mSyncManager == null)
            if (AccessoryManager.isBLEDevice(paramString)) {
                this.mSyncManager = BLEDeviceFactory.getManagerByDeviceType(this.mContext, paramString);
                this.mSyncManager.setTargetDevice(paramString);
            }

    }

    public void setTargetStep(int paramInt) {
        if (this.mSyncManager != null)
            this.mSyncManager.setTargetStep(paramInt);
    }

    public void setUpGradeFilePath(String paramString) {
        if (this.mSyncManager != null)
            this.mSyncManager.setUpGradeFilePath(paramString);
    }

    public void start() {
        if (this.mSyncManager != null)
            this.mSyncManager.start();
    }

    public void start(int paramInt) {
        if (this.mSyncManager != null) {
            this.mSyncManager.setAction(paramInt);
            this.mSyncManager.start();
        }
    }

    public void startDevice(BluetoothDevice paramBluetoothDevice, int paramInt, Handler paramHandler) {
        if (this.mSyncManager != null) {
            this.mSyncManager.stopSyncDevice();
            this.mSyncManager.destroy();
            this.mSyncManager = null;
        }
        if (paramBluetoothDevice.getName() == null) stopSeartchBle();
        ;
        for (String str1 = AccessoryConfig.getStringValue(this.mContext, paramBluetoothDevice.getAddress()); ; str1 = paramBluetoothDevice.getName()) {
            String str2 = str1;
            this.mSyncManager = BLEDeviceFactory.getManagerByDeviceType(this.mContext, paramBluetoothDevice.getName());
            this.mSyncManager.setTargetDevice(str2);
            this.mSyncManager.setAction(paramInt);
            this.mHandlers.add(paramHandler);
            Iterator localIterator = this.mHandlers.iterator();
            while (localIterator.hasNext()) {
                Handler localHandler = (Handler) localIterator.next();
                this.mSyncManager.registerHandler(localHandler);
            }
            BaseAccessorySyncManager.isStart = true;
            this.mSyncManager.connectToDeviceRightNow(paramBluetoothDevice);
        }


    }

    public void startSeartch() {
        if (this.mSyncManager != null)
            this.mSyncManager.startSeartch();
    }

    public void stopSeartchBle() {
        if (this.mSyncManager != null)
            this.mSyncManager.stopSeatrch();
    }

    public void stopSyncDevice() {
        if (this.mSyncManager != null)
            this.mSyncManager.stopSyncDevice();
    }

    public void unRegisterHandler(Handler paramHandler) {
        if (this.mSyncManager != null)
            this.mSyncManager.unRegisterHandler(paramHandler);
        this.mHandlers.remove(paramHandler);
    }

    public void unbind() {
        if (this.mSyncManager != null)
            this.mSyncManager.unbind();
    }
}

