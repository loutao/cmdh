package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.ratio.deviceService.accessory.AccessoryManager;
import com.ratio.deviceService.bean.CodoonBluethoothDevice;
import com.ratio.deviceService.datamanager.SyncDeviceManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mesogene on 2015/5/19.
 */
public class DiscoveryAccessoryActivity extends Activity implements View.OnClickListener {

    protected static final String TAG = "DiscoveryAccessoryActivity";
    private final int CONENCT = 65521;
    BroadcastReceiver HeadsetPlugReceiver = new BroadcastReceiver() {
        public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent) {
            if (paramAnonymousIntent.getAction().equals("android.intent.action.HEADSET_PLUG")) {
                int i = paramAnonymousIntent.getIntExtra("state", -1);
                Log.d("DiscoveryAccessoryActivity", "plug in state is " + i);
                if (i != 0) {
                    DiscoveryAccessoryActivity.this.isPlagIn = false;
                    return;
                }
                Log.i("DiscoveryAccessoryActivity", "your headset is plug in");
                DiscoveryAccessoryActivity.this.isPlagIn = true;
                Log.d("type", DiscoveryAccessoryActivity.this.mDeviceType);
                if (!AccessoryManager.isBLEDevice(DiscoveryAccessoryActivity.this.mDeviceType))
                    DiscoveryAccessoryActivity.this.mHandler.sendEmptyMessageDelayed(65521, 800L);
            }

        }
    };
    private final int RESTART = 65535;
    private String deviceId;
    private List<CodoonBluethoothDevice> devices;
    private ImageView iconImage;
    private int[] icons;
    protected boolean isPlagIn;
    private boolean isResponseMsg;
    private boolean is_rom_device;
    private AccessoryManager mAccessoryManager;
    private String mDeviceType = "";
    private Handler mHandler;
    private ImageView mImgClose;
    private Handler mSyncHandler;
    private TextView mTxtSureStart;
    private TextView mTxtTitle;
    private AnimationDrawable seartchAnim;
    private LinearLayout searthResultLayout;
    private LinearLayout searthingLayout;
    private SyncDeviceManager syncManager;
    private TextView warningPosition;

    private void connectToDevice() {
        Log.d("DiscoveryAccessoryActivity", "enter connectToDevice");
        this.syncManager = SyncDeviceManager.getInstance(this, this.mSyncHandler, 1, this.mDeviceType);
        this.syncManager.start();
    }

    private void registerReceiver() {
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
        if (HeadsetPlugReceiver != null) ;
        try {
            DiscoveryAccessoryActivity.this.registerReceiver(HeadsetPlugReceiver, localIntentFilter);
            Log.i("receiver", HeadsetPlugReceiver.toString());
        } catch (Exception localException) {
            Log.e("DiscoveryAccessoryActivity", localException.toString());
        }
    }

    private void setupView() {
        this.mImgClose = ((ImageView) findViewById(R.id.btn_close));
        this.mImgClose.setOnClickListener(this);
        this.mImgClose.setFocusable(true);
        this.mImgClose.requestFocus();
        this.mImgClose.setFocusableInTouchMode(true);
        this.mTxtTitle = ((TextView) findViewById(R.id.txt_search_title));
        this.mTxtSureStart = ((TextView) findViewById(R.id.txt_sure_start));
        this.iconImage = ((ImageView) findViewById(R.id.icon_device_discovry));
        Object localObject = getIntent();
        if (localObject != null) {
            this.mDeviceType = ((Intent) localObject).getStringExtra("device_name");
            this.icons = this.mAccessoryManager.getAnimaIconsByType(this.mDeviceType);
            localObject = this.mAccessoryManager.getDeviceNameByType(this.mDeviceType);
            this.mTxtTitle.setText(String.format(getString(R.string.search_device), new Object[]{localObject}));
            if (!AccessoryManager.isBLEDevice(this.mDeviceType))
                this.mTxtSureStart.setText(String.format(getString(R.string.sure_insert), new Object[]{localObject}));
            this.mTxtSureStart.setText(String.format(getString(R.string.sure_start), new Object[]{localObject}));
        }
        while (true) {
            this.seartchAnim = new AnimationDrawable();
            int i = 0;
            while (i < 2) {
                localObject = getResources().getDrawable(this.icons[i]);
                this.seartchAnim.addFrame((Drawable) localObject, 300);
                i += 1;
            }
            this.seartchAnim.setOneShot(false);
            setIconImage(this.seartchAnim);
            return;
        }
    }

    private void unRegisterReceiver() {
        if (this.HeadsetPlugReceiver != null) ;
        try {
            unregisterReceiver(this.HeadsetPlugReceiver);
            return;
        } catch (Exception localException) {
        }
    }

    public void finish() {
        super.finish();
        this.mHandler.removeMessages(1);
        this.seartchAnim.stop();
        if (this.syncManager != null) {
            this.syncManager.unRegisterHandler(this.mSyncHandler);
            this.syncManager.stopSyncDevice();
        }
        this.isResponseMsg = false;
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if (paramInt2 == 1001) {
            Log.d("DiscoveryAccessoryActivity", "onActivityResult:DISCONVERY_RESULT_RESEARCH");
            if ((this.syncManager != null) && (AccessoryManager.isBLEDevice(this.mDeviceType))) {
                this.syncManager.stopSyncDevice();
                this.syncManager.destroy();
                this.syncManager = null;
                this.mHandler.sendEmptyMessageDelayed(65535, 2000L);
                reinitView();
            }
            return;
        }
        if (paramInt2 == 0) {
            setResult(0);
            finish();
            return;
        }
        setResult(-1);
        finish();
    }

    public void onBackPressed() {
        setResult(0);
        super.onBackPressed();
    }

    public void onClick(View paramView) {
        if (paramView == this.mImgClose)
            onBackPressed();
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.discovery_accessory);
        getActionBar().hide();
        this.isResponseMsg = true;
        this.mAccessoryManager = new AccessoryManager(this);
        this.is_rom_device = getIntent().getBooleanExtra("is_rom_device", false);
        setupView();
        this.mSyncHandler = new Handler() {
            public void handleMessage(Message paramAnonymousMessage) {
                super.handleMessage(paramAnonymousMessage);
                switch (paramAnonymousMessage.what) {
                    default:
                        return;
                    case 51:
                        DiscoveryAccessoryActivity.this.reinitView();
                        return;
                    case 2:
                        DiscoveryAccessoryActivity.this.showMsgClickband();
                        return;
                    case 4:
                        return;
                    case 18:
                        Log.d("DiscoveryAccessoryActivity", "connect failed");
                        Intent localintent2 = new Intent(DiscoveryAccessoryActivity.this, FoundAccessoryActivity.class);
                        localintent2.putExtra("device_name", DiscoveryAccessoryActivity.this.mDeviceType);
                        localintent2.putExtra("seartch_result", true);
                        if (!DiscoveryAccessoryActivity.this.isResponseMsg)
                            return;
                        DiscoveryAccessoryActivity.this.startActivityForResult(localintent2, 1);
                        return;
                       
                    case 251:
                        Log.d("DiscoveryAccessoryActivity", "connect success");
                        Intent localintent = new Intent(DiscoveryAccessoryActivity.this, FoundAccessoryActivity.class);
                        localintent.putExtra("device_name", DiscoveryAccessoryActivity.this.mDeviceType);
                        localintent.putExtra("is_rom_device", DiscoveryAccessoryActivity.this.is_rom_device);
                        localintent.putExtra("seartch_result", true);
                        if (!DiscoveryAccessoryActivity.this.isResponseMsg) {
                            Log.e("DiscoveryAccessoryActivity", "cannot response msg syncManager beacause activity stop");
                            return;
                        }
                        DiscoveryAccessoryActivity.this.startActivityForResult(localintent, 1);
                        return;


                    case 252:
                        while ((DiscoveryAccessoryActivity.this.devices == null) || (DiscoveryAccessoryActivity.this.devices.size() <= 0))
                            ;
                        Iterator localitetator = DiscoveryAccessoryActivity.this.devices.iterator();
                        while (localitetator.hasNext()) {
                            String str = ((CodoonBluethoothDevice) localitetator.next()).getName();
                            if ((str != null) && (str.equals("MI")) && (DiscoveryAccessoryActivity.this.mDeviceType.equals("cod_mi")))
                                DiscoveryAccessoryActivity.this.startRomWarningActivity();
                        }
                        return;
                    case 255:
                        if (DiscoveryAccessoryActivity.this.devices == null)
                            devices = new ArrayList();
                        CodoonBluethoothDevice localCodoonBluethoothDevice = (CodoonBluethoothDevice) paramAnonymousMessage.obj;
                        if (!DiscoveryAccessoryActivity.this.devices.contains(localCodoonBluethoothDevice))
                            DiscoveryAccessoryActivity.this.devices.add(localCodoonBluethoothDevice);
                    case 34:
                        if (DiscoveryAccessoryActivity.this.devices == null)
                            devices = new ArrayList();
                        CodoonBluethoothDevice localCodoonBluethoothDevice34 = (CodoonBluethoothDevice) paramAnonymousMessage.obj;
                        if (!DiscoveryAccessoryActivity.this.devices.contains(localCodoonBluethoothDevice34))
                            DiscoveryAccessoryActivity.this.devices.add(localCodoonBluethoothDevice34);
                        return;
                    case 33:
                        if (DiscoveryAccessoryActivity.this.devices == null)
                            devices = new ArrayList();
                        CodoonBluethoothDevice localCodoonBluethoothDevice33 = (CodoonBluethoothDevice) paramAnonymousMessage.obj;
                        if (!DiscoveryAccessoryActivity.this.devices.contains(localCodoonBluethoothDevice33))
                            DiscoveryAccessoryActivity.this.devices.add(localCodoonBluethoothDevice33);
                        return;
                       /* DiscoveryAccessoryActivity.this.showMsgClickband();
                        return;*/
                       /* Log.d("DiscoveryAccessoryActivity", "connect success");
                        Intent localintent33 = new Intent(DiscoveryAccessoryActivity.this, FoundAccessoryActivity.class);
                        localintent33.putExtra("device_name", DiscoveryAccessoryActivity.this.mDeviceType);
                        localintent33.putExtra("is_rom_device", DiscoveryAccessoryActivity.this.is_rom_device);
                        localintent33.putExtra("seartch_result", true);
                        if (!DiscoveryAccessoryActivity.this.isResponseMsg) {
                            Log.e("DiscoveryAccessoryActivity", "cannot response msg syncManager beacause activity stop");
                            return;
                        }
                        DiscoveryAccessoryActivity.this.startActivityForResult(localintent33, 1);
                        return;*/

                }
            }
        };
        this.mHandler = new Handler() {
            public void handleMessage(Message paramAnonymousMessage) {
                super.handleMessage(paramAnonymousMessage);
                if (paramAnonymousMessage.what == 65521)
                    DiscoveryAccessoryActivity.this.connectToDevice();
                while (paramAnonymousMessage.what != 65535)
                    return;
                Log.d("quit;", "65535");
                if (DiscoveryAccessoryActivity.this.devices != null)
                    DiscoveryAccessoryActivity.this.devices.clear();
                syncManager = SyncDeviceManager.getInstance(DiscoveryAccessoryActivity.this, DiscoveryAccessoryActivity.this.mSyncHandler, 1, DiscoveryAccessoryActivity.this.mDeviceType);
                Log.d("syscManager", syncManager.getTargetDevice());
                Log.d("mSyncHandler", mSyncHandler.toString());
                DiscoveryAccessoryActivity.this.syncManager.registerHandler(DiscoveryAccessoryActivity.this.mSyncHandler);
                DiscoveryAccessoryActivity.this.syncManager.reBind();
            }
        };

        if (AccessoryManager.isBLEDevice(mDeviceType)) {
            syncManager = SyncDeviceManager.getInstance(getApplicationContext());
            syncManager.stopSyncDevice();
            syncManager.destroy();
            mHandler.sendEmptyMessageDelayed(65535, 800L);
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        this.mHandler.removeMessages(65521);
        this.mHandler.removeMessages(65535);
        this.seartchAnim.stop();
        if (this.syncManager != null) {
            this.syncManager.unRegisterHandler(this.mSyncHandler);
            this.syncManager.stopSyncDevice();
        }
        this.isResponseMsg = false;
    }
    protected void onResume() {
        super.onResume();
        registerReceiver();
        this.isResponseMsg = true;
        if (this.syncManager != null)
            this.syncManager.registerHandler(this.mSyncHandler);
    }
    protected void onStop() {
        super.onStop();
        this.mHandler.removeMessages(65521);
        unRegisterReceiver();
        if (this.syncManager != null)
            this.syncManager.unRegisterHandler(this.mSyncHandler);
    }
    public void onWindowFocusChanged(boolean paramBoolean) {
        super.onWindowFocusChanged(paramBoolean);
        if (paramBoolean) {
            this.mImgClose.requestFocus();
            this.seartchAnim.start();
        }
    }
    protected void reinitView() {
        if ((AccessoryManager.isBLEDevice(this.mDeviceType)) && (!AccessoryManager.isThirdBleDevice(this.mDeviceType))) {
            String str = this.mAccessoryManager.getDeviceNameByType(this.mDeviceType);
            this.mTxtTitle.setText(String.format(getString(R.string.search_device), new Object[]{str}));
            this.mTxtSureStart.setText(String.format(getString(R.string.sure_start), new Object[]{str}));
            setIconImage(this.seartchAnim);
            this.seartchAnim.start();
        }
    }
    public void setIconImage(Drawable paramDrawable) {
        if (Build.VERSION.SDK_INT > 15) {
            this.iconImage.setBackground(paramDrawable);
            return;
        }
        this.iconImage.setBackgroundDrawable(paramDrawable);
    }

    protected void showMsgClickband() {
        String str;
        if ((AccessoryManager.isBLEDevice(this.mDeviceType)) && (!AccessoryManager.isThirdBleDevice(this.mDeviceType))) {
            this.seartchAnim.stop();
            setIconImage(getResources().getDrawable(this.mAccessoryManager.getDeviceFindIconByType(this.mDeviceType)));
            str = this.mAccessoryManager.getDeviceNameByType(this.mDeviceType);
            this.mTxtTitle.setText(String.format(getString(R.string.has_found), new Object[]{str}));
            if (this.mDeviceType.startsWith("cod_mi"))
                this.mTxtSureStart.setText(String.format(getString(R.string.find_mi_band), new Object[]{str}));
        } else {
            return;
        }
        this.mTxtSureStart.setText(String.format(getString(R.string.has_found), new Object[]{str}));
    }
    protected void startRomWarningActivity() {
        startActivity(new Intent(this, DownloadCodoonRomActivity.class));
        finish();
    }
}
