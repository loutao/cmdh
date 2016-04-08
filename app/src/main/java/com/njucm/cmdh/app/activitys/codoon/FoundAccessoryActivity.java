package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.ratio.deviceService.datamanager.AccessoryManager;

/**
 * Created by Mesogene on 2015/5/20.
 */
public class FoundAccessoryActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "FoundAccessoryActivity";
    private boolean isFound;
    private boolean is_rom_device;
    private AccessoryManager mAccessoryManager;
    private Button mBtnSureBind;
    private Context mContext;
    private String mDeviceType = "";
    private ImageView mImgClose;
    private ImageView mImgDeviceType;
    private TextView mTxtDes;
    private TextView mTxtFoundTitle;
    private TextView mTxtResearch;

    private void setupView() {
        this.mTxtFoundTitle = ((TextView) findViewById(R.id.has_found_title));
        this.mTxtDes = ((TextView) findViewById(R.id.txt_des));
        this.mImgDeviceType = ((ImageView) findViewById(R.id.img_devicetype));
        this.mBtnSureBind = ((Button) findViewById(R.id.btn_surebind));
        this.mBtnSureBind.setOnClickListener(this);
        this.mImgClose = ((ImageView) findViewById(R.id.btn_close));
        this.mImgClose.setOnClickListener(this);
        this.mTxtResearch = ((TextView) findViewById(R.id.txt_research));
        this.mTxtResearch.setOnClickListener(this);
        Intent localIntent = getIntent();
        String str1;
        if (localIntent != null) {
            this.is_rom_device = localIntent.getBooleanExtra("is_rom_device", false);
            this.mDeviceType = localIntent.getStringExtra("device_name");
            this.isFound = localIntent.getBooleanExtra("seartch_result", false);
            StringBuilder localStringBuilder = new StringBuilder();
            Log.i("FoundAccessoryActivity", "isFound:" + this.isFound + " device:" + this.mDeviceType);
            str1 = this.mAccessoryManager.getDeviceNameByType(this.mDeviceType);
//            this.mImgDeviceType.setImageResource(this.mAccessoryManager.getDeviceLargeIconByType(this.mDeviceType));
            if (!this.isFound) {
                TextView localTextView1 = this.mTxtFoundTitle;
                String str2 = getString(R.string.find_not_device);
                Object[] arrayOfObject1 = new Object[1];
                arrayOfObject1[0] = str1;
                localTextView1.setText(String.format(str2, arrayOfObject1));
                this.mBtnSureBind.setText(R.string.research);
                this.mTxtResearch.setVisibility(View.GONE);
            }
            this.mImgDeviceType.setVisibility(View.VISIBLE);
            TextView localTextView2 = this.mTxtFoundTitle;
            String str3 = getString(R.string.has_found);
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = str1;
            localTextView2.setText(String.format(str3, arrayOfObject2));
            this.mBtnSureBind.setText(R.string.button_surebind);
        }

    }

    public void bindOver() {
        Log.i("is_rom_device", is_rom_device + "");
        SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("MyPrefsFile", 0);
        SharedPreferences.Editor localEditor = localSharedPreferences.edit();
        localEditor.putBoolean("IsBindDevice", true);
        localEditor.putString("BindTypeName", this.mDeviceType);
        localEditor.commit();
        String str = localSharedPreferences.getString("Address", null);
        Intent localObject = new Intent();
        if (this.is_rom_device) {
            if (AccessoryManager.isSupportFriends(this, str)) {
                Log.i("supportfriends", "true");
                localObject = new Intent(this, RomdeviceBindSuccess.class);
                localObject.putExtra("is_rom_device", true);
            } else {
                Log.i("supportfriends", "false");
                Intent localIntent3 = new Intent(this, RomdeviceBindSuccess.class);
                localObject = localIntent3;
            }
        } else {
            localObject.putExtra("is_rom_device", false);
            Intent localIntent1 = new Intent(this, BindAccessorySuccess.class);
            localObject = localIntent1;
        }
       /* while (true) {
            
            Intent localIntent3 = new Intent(this, RomdeviceBindSuccess.class);
            localObject = localIntent3;
            ((Intent) localObject).putExtra("is_rom_device", false);
            Intent localIntent1 = new Intent(this, BindAccessorySuccess.class);
            localObject = localIntent1;
            break;
        }*/
        localObject.putExtra("device_name", this.mDeviceType);
        startActivity(localObject);
        setResult(-1);
        finish();
        return;
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        Log.i("FoundAccessoryActivity", "FoundAccessoryActivity onActivityResult finish");
        setResult(-1);
        finish();
    }

    public void onBackPressed() {
        setResult(0);
        finish();
    }

    public void onClick(View paramView) {
        Log.i("isFound",isFound+"");
        if (paramView == this.mBtnSureBind)
            if (this.isFound) {
                bindOver();
                finish();
            }
        while (true) {
            setResult(1001);
            if (paramView == this.mImgClose) {
                setResult(0);
                finish();
            } else if (paramView == this.mTxtResearch) {
                setResult(1001);
                finish();
            }
            return;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.mContext = getApplicationContext();
        setContentView(R.layout.find_accessory);
        getActionBar().hide();
        AccessoryManager localAccessoryManager = new AccessoryManager(this);
        this.mAccessoryManager = localAccessoryManager;

        setupView();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
    }
}
