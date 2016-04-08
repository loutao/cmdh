package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.ratio.deviceService.datamanager.AccessoryManager;


public class BindAccessorySuccess extends Activity implements View.OnClickListener
{
    private String deviceType = null;
    private Button mImgClose;
    private TextView mTxtDes;

    private void directToHome()
    {
        Intent localIntent = new Intent(this, CurrentAccessoryActivity.class);
        localIntent.putExtra("device_name", this.deviceType);
        localIntent.putExtra("isFromBind", true);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
        finish();
    }

    private void setupView()
    {
        this.mImgClose = ((Button)findViewById(R.id.btn_close));
        this.mImgClose.setOnClickListener(this);
        this.mTxtDes = ((TextView)findViewById(R.id.txt_des));
        Object localObject = getIntent();
        if (localObject != null)
        {
            this.deviceType = ((Intent)localObject).getStringExtra("device_name");
            localObject = new AccessoryManager(this).getDeviceNameByType(this.deviceType);
            this.mTxtDes.setText(String.format(getString(R.string.bind_radio_sucess_des), new Object[] { localObject }));
        }
    }

    public void finish()
    {
        super.finish();
    }

    public void onBackPressed()
    {
        setResult(-1);
        directToHome();
    }

    public void onClick(View paramView)
    {
        if (paramView == this.mImgClose)
        {
            setResult(-1);
            directToHome();
        }
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.bind_accessory_over);
       getActionBar().hide();
        setupView();
    }
}