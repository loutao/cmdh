package com.ratio.deviceService.datamanager;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.ratio.deviceService.DisconveryManager;
import com.ratio.deviceService.accessory.AccessoryConfig;
import com.ratio.deviceService.accessory.AccessoryManager;
import com.ratio.deviceService.bean.AccessoriesTotal;
import com.ratio.deviceService.bean.BluetoothUser;
import com.ratio.deviceService.bean.GPSTotal;
import com.ratio.deviceService.ble.BaseCommunicationManager;
import com.ratio.deviceService.ble.CodoonDeviceUpgradeManager;
import com.ratio.deviceService.data.CLog;
import com.ratio.deviceService.data.ISyncDataCallback;
import com.ratio.deviceService.data.SendData;
import com.ratio.util.KeyConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public abstract class BaseAccessorySyncManager implements AccessoryConstOrder {
    private static final String TAG = "BaseAccessorySyncManager";
    public static boolean isStart;
    protected final int CHECK_BLE_RESEARTCH = 242;
    public int TARGET_TYPE = 1;
    public int TARGET_VALUE = 10000;
    protected final int TIME_OUT_DELAY = 70000;
    protected AccessoriesTotal accessoriesTotals;
    protected int action;
    protected BluetoothUser blue_user = null;
    protected HashMap<String, AccessoryValues> curDataMap;
    protected int curDeviceButtey;
    protected String curDeviceVersion;
    protected int curSeartchMethod;
    protected boolean isAutoSeartchNext;
    protected boolean isBleDevice;
    protected boolean isStartBle;
    protected int lastSeartchMethod;
    protected AccessoryManager mAccessoryManager;
    protected int mAge;
    protected BluetoothDevice mBLEDevice;
    protected BaseCommunicationManager mBLESyncManager;
    public Context mContext;
    protected String mDeviceID;
    protected DisconveryManager mDiscoveryBleManager;
    public List<Handler> mHandlers = new ArrayList<Handler>();
    ;
    protected int mHeight;
    protected ISyncDataCallback mISyncDataCallback;
    protected int mMaxHeartData;
    protected int mMinHeartData;
    protected CodoonDeviceUpgradeManager mUpGradeManager;
    protected int mSex;
    protected String mUploadJson;
    protected AccessoryWareManager mAccessoryWareManager;
    protected int mWeight;
    protected String targetDeviceName;
    protected int targetStep = 10000;
    public BroadcastReceiver mBleReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            if (paramAnonymousIntent.getAction().equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                boolean bool = BluetoothAdapter.getDefaultAdapter().isEnabled();
                BaseAccessorySyncManager.this.onBlutoothStateChanged(bool);
            }
        }
    };
    protected Runnable timeConnect = new Runnable() {
        public void run() {
            BaseAccessorySyncManager.this.stopSyncDevice();
            BaseAccessorySyncManager.this.sendEmptyMsgBack(255);
        }
    };

    public BaseAccessorySyncManager(Context paramContext) {
        this.mContext = paramContext.getApplicationContext();
        this.mAccessoryManager = new AccessoryManager(paramContext);
    }

    protected Handler connectHandler = new Handler() {
        @Override
        public void handleMessage(Message paramAnonymousMessage) {
            super.handleMessage(paramAnonymousMessage);
            if (paramAnonymousMessage.what == 9) {
                byte[] arrayOfByte = (byte[]) paramAnonymousMessage.obj;
                BaseAccessorySyncManager.this.notifyHistroyChange();
            }
            while (true) {

                if (paramAnonymousMessage.what == 241) {
                    if (BaseAccessorySyncManager.this.action == 101) {
                        connectHandler.removeCallbacks(BaseAccessorySyncManager.this.timeConnect);
                        BaseAccessorySyncManager.this.mBLESyncManager.cancel();
                        return;
                    }
                    BaseAccessorySyncManager.this.mBLESyncManager.register(BaseAccessorySyncManager.this.mISyncDataCallback);
                    BaseAccessorySyncManager.this.mBLESyncManager.start(BaseAccessorySyncManager.this.mBLEDevice);
                    return;
                }
                if (paramAnonymousMessage.what == 2) {
                    BaseAccessorySyncManager.this.seartchBle();
                    return;
                }
                if (paramAnonymousMessage.what == 242) {
                    BaseAccessorySyncManager.this.sendEmptyMsgBack(51);
                    BaseAccessorySyncManager.this.seartchBle();
                    return;
                }
            }
        }
    };

    protected void notifyHistroyChange() {
        Log.i("accessory", "notifyHistroyChange");
        if ((this.curDataMap != null) && (this.curDataMap.size() > 0)) {
            Intent localIntent = new Intent();
            Iterator localIterator = this.curDataMap.keySet().iterator();
            while (localIterator.hasNext()) {
                Object localObject = (String) localIterator.next();
                localObject = (AccessoryValues) this.curDataMap.get(localObject);
                if (this.mAccessoryManager.isDataAvailable((AccessoryValues) localObject)) {
                    GPSTotal localGPSTotal = new GPSTotal();
                    localGPSTotal.sportsType = -1;
                    localGPSTotal.TotalContEnergy = (((AccessoryValues) localObject).calories / 10.0F);
                    localGPSTotal.TotalDistance = (((AccessoryValues) localObject).distances / 1000.0F);
                    localGPSTotal.total_time = ((AccessoryValues) localObject).sport_duration * 60 * 1000;
                    localGPSTotal.EndDateTime = ((AccessoryValues) localObject).start_sport_time;
                    localIntent.putExtra("data", localGPSTotal);
                }
            }
            localIntent.setAction(KeyConstants.ON_HISTORYDATA_CHANGE_MESSAGE);
            this.mContext.sendBroadcast(localIntent);
        }
    }

    public void connectToDeviceRightNow(BluetoothDevice paramBluetoothDevice) {
    }

    public void destroy() {
        if (this.mHandlers != null)
            this.mHandlers.clear();
    }

    public int getAction() {
        return this.action;
    }

    public int getCurButtery() {
        return this.curDeviceButtey;
    }

    public String getDeviceId() {
        return this.mDeviceID;
    }

    public String getDeviceNameByID(String paramString) {
        return this.mAccessoryManager.getDeviceNameByID(paramString);
    }

    public String getDeviceVersion() {
        return this.curDeviceVersion;
    }

    public List<Handler> getHandlers() {
        return this.mHandlers;
    }

    public String getTargetDevice() {
        return this.targetDeviceName;
    }

    protected AccessoriesTotal getTotalSpotsDatas(long[] paramArrayOfLong) {
        return this.mAccessoryManager.getTotalSpotsDatas(this.mDeviceID, this.targetDeviceName, paramArrayOfLong);
    }

    protected String getTransferJson(byte[] paramArrayOfByte)
            throws JSONException {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("device_id", this.mDeviceID);
        localJSONObject.put("hardversion", "android:" + Build.VERSION.RELEASE);
        localJSONObject.put("file_name", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())));
        localJSONObject.put("file_data", Base64.encode(paramArrayOfByte, paramArrayOfByte.length));
        return localJSONObject.toString();
    }

    public abstract void onBlutoothStateChanged(boolean paramBoolean);

    public void parseDeviceInfo(BluetoothDevice paramBluetoothDevice, byte[] paramArrayOfByte) {
    }

    public void reBind() {
        unbind();
        start();
    }

    public void registerBle() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
        try {
            this.mContext.registerReceiver(this.mBleReceiver, localIntentFilter);
            return;
        } catch (Exception localException) {
        }
    }

    public void registerHandler(Handler paramHandler) {
        Log.d("handler", paramHandler.toString());
        if ((!this.mHandlers.contains(paramHandler)) && (paramHandler != null)) {
            boolean bool = this.mHandlers.add(paramHandler);
            CLog.d("BaseAccessorySyncManager", "register result " + bool);
        }
    }

    public void saveBasicInfo() {
        SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("MyPrefsFile", 0).edit();
        localEditor.putString("BindTypeId", this.mDeviceID);
        localEditor.putString("BindTypeName", this.targetDeviceName);
        localEditor.putString("BindTypeVersion", this.curDeviceVersion);
        localEditor.putString("Buttery", String.valueOf(this.curDeviceButtey));
        localEditor.putInt("LastSeartchMethod", this.curSeartchMethod);
        if (this.mBLEDevice != null) {
            localEditor.putString("Address", this.mBLEDevice.getAddress());
            if (AccessoryManager.isThirdBleDevice(this.targetDeviceName))
                localEditor.putString("HeartRateAdress", this.mBLEDevice.getAddress());
            this.mAccessoryManager.isTurnFriends(this.mBLEDevice.getAddress());
        }
        localEditor.commit();
    }

    public void seartchBle() {
    }

    protected void sendButteryBack(int paramInt) {
        this.mAccessoryManager.setLastSyncTime(System.currentTimeMillis());
        AccessoryConfig.setIntValue(this.mContext, "last_eletric", paramInt);
        sendMsgBack(8, paramInt, 0, null);
    }

    /*  public void sendDataToService(final byte[] paramArrayOfByte)
      {
          ConfigManager.setLastSportType(this.mContext, AchievementActivity.SportsType.ACCESSORIES);
          this.mDeviceID = this.mAccessoryManager.getCurAccessory().id;
          try
          {
              this.mUploadJson = getTransferJson(paramArrayOfByte);
              paramArrayOfByte = UserData.GetInstance(this.mContext).GetUserBaseInfo();
              if (this.mUploadCallback == null)
                  this.mUploadCallback = new IOperationResult()
                  {
                      public void operationResult(String paramAnonymousString, boolean paramAnonymousBoolean)
                      {
                          paramAnonymousString = new AccessoriesMainDAO(BaseAccessorySyncManager.this.mContext);
                          paramAnonymousString.open();
                          paramAnonymousString.beginTransaction();
                          if (!paramAnonymousBoolean)
                              if (!TextUtils.isEmpty(BaseAccessorySyncManager.this.mUploadJson))
                              {
                                  String str = String.valueOf(System.currentTimeMillis() / 1000L);
                                  new FileManager().saveContent(BaseAccessorySyncManager.this.mContext, paramArrayOfByte.id, str, BaseAccessorySyncManager.this.mUploadJson);
                                  BaseAccessorySyncManager.this.mUploadJson = null;
                                  BaseAccessorySyncManager.this.accessoriesTotals.is_upload = 0;
                                  BaseAccessorySyncManager.this.accessoriesTotals.filename = (paramArrayOfByte.id + "_" + str + ".txt");
                                  BaseAccessorySyncManager.this.sendEmptyMsgBack(6);
                                  CLog.d("debug", "error upload file:" + BaseAccessorySyncManager.this.accessoriesTotals.filename);
                              }
                          while (true)
                          {
                              if (paramAnonymousBoolean);
                              BaseAccessorySyncManager.this.mDeviceID = BaseAccessorySyncManager.this.mAccessoryManager.getCurAccessory().id;
                              BaseAccessorySyncManager.this.accessoriesTotals.type_id = BaseAccessorySyncManager.this.mDeviceID.substring(0, 2);
                              paramAnonymousString.insert(BaseAccessorySyncManager.this.accessoriesTotals);
                              paramAnonymousString.setTransactionSuccessful();
                              paramAnonymousString.endTransaction();
                              paramAnonymousString.close();
                              return;
                              BaseAccessorySyncManager.this.accessoriesTotals.is_upload = 1;
                              BaseAccessorySyncManager.this.sendEmptyMsgBack(7);
                              BaseAccessorySyncManager.this.mUploadJson = null;
                          }
                      }
                  };
              this.preferences = new ApkSharedPreferences(this.mContext);
              paramArrayOfByte = new CodoonAsyncTask(this.mContext, CodoonAsyncTask.ConnectWay.post, this.mUploadCallback, this.mUploadJson, this.preferences.getAccessToken(this.mContext), false);
          }
          catch (JSONException paramArrayOfByte)
          {
              try
              {
                  paramArrayOfByte.execute(new String[] { CodoonHttpHelper.getHostAddress() + "/api/post_tracker_data" });
                  return;
                  paramArrayOfByte = paramArrayOfByte;
                  paramArrayOfByte.printStackTrace();
              }
              catch (Exception paramArrayOfByte)
              {
                  CLog.d("debug", "error" + paramArrayOfByte.getMessage());
                  sendEmptyMsgBack(6);
              }
          }
      }*/
    public AccessoriesTotal getUploadAccessoriesTotal() {
        return this.accessoriesTotals;
    }

    protected void sendDeviceIDCallBack(String paramString) {
        Iterator localIterator = this.mHandlers.iterator();
        while (localIterator.hasNext()) {
            Handler localHandler = (Handler) localIterator.next();
            Message localMessage = localHandler.obtainMessage();
            localMessage.what = 3;
            localMessage.obj = paramString;
            localHandler.sendMessage(localMessage);
        }
    }

    protected void sendEmptyMsgBack(int paramInt) {
        Iterator localIterator = this.mHandlers.iterator();
        while (localIterator.hasNext())
            ((Handler) localIterator.next()).sendEmptyMessage(paramInt);
    }

    protected void sendMsgBack(int paramInt1, int paramInt2, int paramInt3, Object paramObject) {
        Iterator localIterator = this.mHandlers.iterator();
        while (localIterator.hasNext()) {
            Handler localHandler = (Handler) localIterator.next();
            Message localMessage = localHandler.obtainMessage();
            localMessage.what = paramInt1;
            localMessage.obj = paramObject;
            localMessage.arg1 = paramInt2;
            localMessage.arg2 = paramInt3;
            localHandler.sendMessage(localMessage);
        }
    }

    protected void sendTotalDataback(HashMap<String, AccessoryValues> paramHashMap) {
        Message localMessage = this.connectHandler.obtainMessage();
        localMessage.what = 28;
        localMessage.obj = paramHashMap;
        this.connectHandler.sendMessage(localMessage);
    }

    protected void sendVersionIdBack(String paramString) {
        Iterator localIterator = this.mHandlers.iterator();
        while (localIterator.hasNext()) {
            Handler localHandler = (Handler) localIterator.next();
            Message localMessage = localHandler.obtainMessage();
            localMessage.what = 4;
            localMessage.obj = paramString;
            localHandler.sendMessage(localMessage);
        }
    }

    public void setAction(int paramInt) {
        this.action = paramInt;
    }

    public void setHandlers(List<Handler> paramList) {
        this.mHandlers = paramList;
    }

    public void setTargetDevice(String paramString) {
        this.targetDeviceName = paramString;
    }

    public void setTargetStep(int paramInt) {
        this.targetStep = paramInt;
    }

    public abstract void setUpGradeFilePath(String paramString);

    protected int[] setUserAlarmOrder() {
        return SendData.getPostUpdateUser2(this.mAccessoryManager.getUserColockInfo());
    }

    protected int[] setUserColockInfoOrder() {
        return SendData.getPostUpdateUser2(this.mAccessoryManager.getUserColockInfo());
    }

    public void setUserInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.mSex = paramInt1;
        this.mAge = paramInt2;
        this.mHeight = paramInt3;
        this.mWeight = paramInt4;
    }

    public abstract void init();

    protected int[] setUserInfoOrder() {
        Toast.makeText(mContext, "setUserInfoOrder", Toast.LENGTH_LONG);
        /* int[] arrayOfInt = new int[14];
         Object localObject = UserData.GetInstance(this.mContext).GetUserBaseInfo();
         arrayOfInt[0] = ((UserBaseInfo)localObject).height;
         if (((UserBaseInfo)localObject).weight > 200.0F);
         for (float f = 200.0F; ; f = ((UserBaseInfo)localObject).weight)
         {
             arrayOfInt[1] = (int)f;
             arrayOfInt[2] = ((UserBaseInfo)localObject).age;
             arrayOfInt[3] = ((UserBaseInfo)localObject).gender;
             arrayOfInt[4] = (int)(((UserBaseInfo)localObject).height * 0.39F);
             arrayOfInt[5] = (int)(((UserBaseInfo)localObject).height * 0.39F * 1.2F);
             arrayOfInt[6] = arrayOfInt[4];
             arrayOfInt[7] = 0;
             localObject = new SportTargetDAO(this.mContext).getSportTarget(((UserBaseInfo)localObject).id);
             if (localObject != null)
                 this.TARGET_TYPE = ((SportTargetTable)localObject).target;
             this.TARGET_VALUE = this.targetStep;
             arrayOfInt[8] = this.TARGET_TYPE;
             if (this.TARGET_VALUE > 65534)
                 this.TARGET_VALUE = 65534;
             arrayOfInt[9] = (this.TARGET_VALUE >> 8);
             arrayOfInt[10] = (this.TARGET_VALUE & 0xFF);
             arrayOfInt[11] = 2;
             arrayOfInt[12] = 0;
             arrayOfInt[13] = 0;
             return SendData.getPostUpdateUserInfoAll(arrayOfInt);
         }*/
        return new int[2];
    }

    public void setheartRateWarning(int paramInt1, int paramInt2) {
        this.mMaxHeartData = paramInt1;
        this.mMinHeartData = paramInt2;
    }

    protected void showMsgUpgradeWarning() {
        Toast.makeText(mContext, "showMsgUpgradeWaring", Toast.LENGTH_LONG).show();
       /* final CommonDialog localCommonDialog = new CommonDialog(this.mContext);
        localCommonDialog.openConfirmDialog(this.mContext.getString(2131558406), this.mContext.getString(2131558529), this.mContext.getString(2131558400), new CommonDialog.DialogButtonInterface()
        {
            public void onDialogButtonClick(CommonDialog.DialogResult paramAnonymousDialogResult)
            {
                localCommonDialog.closeConfirmDialog();
                if (paramAnonymousDialogResult.equals(CommonDialog.DialogResult.Yes))
                    BaseAccessorySyncManager.this.mContext.startActivity(new Intent(BaseAccessorySyncManager.this.mContext, AccessoryUpWarningActivity.class));
            }
        });*/
    }

    public abstract void start();

    public abstract void startSeartch();

    public abstract void stopSeatrch();

    public abstract void stopSyncDevice();

    public void unRegisterBle() {
        try {
            this.mContext.unregisterReceiver(this.mBleReceiver);
            return;
        } catch (Exception localException) {
        }
    }

    public void unRegisterHandler(Handler paramHandler) {
        this.mHandlers.remove(paramHandler);
    }

    public void unbind() {
        SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences("MyPrefsFile", 0).edit();
        localEditor.remove("IsBindDevice");
        localEditor.remove("Address");
        localEditor.remove("BindTypeName");
        localEditor.remove("BindTypeId");
        localEditor.remove("BindTypeName");
        localEditor.remove("BindTypeVersion");
        localEditor.commit();
        this.mBLEDevice = null;
    }
}
