package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.njucm.cmdh.app.R;


public class RomdeviceBindSuccess extends Activity implements View.OnClickListener
{
    private TextView desTxt;
    private String deviceType = null;
    private boolean isRomDevice;
    private TextView title;
    private Button upBtn;

    private void directToHome()
    {
      /*  Intent localIntent = new Intent(this, CurrentAccessoryActivity.class);
        localIntent.putExtra("device_name", this.deviceType);
        localIntent.putExtra("isFromBind", true);
        localIntent.setFlags(67108864);
        startActivity(localIntent);
        finish();*/
    }

    public void onBackPressed()
    {
        setResult(-1);
        directToHome();
    }

    public void onClick(View paramView)
    {
       /* switch (paramView.getId())
        {
            default:
                return;
            case 2131427406:
                setResult(-1);
                directToHome();
                return;
            case 2131429652:
        }
        paramView = new Intent(this, AccessoryUpWarningActivity.class);
        paramView.putExtra("isFromBind", true);
        startActivity(paramView);*/
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.rom_device_bind_over_layout);
        getActionBar().hide();
        findViewById(R.id.btn_close).setOnClickListener(this);
        Intent intent = getIntent();
        if (paramBundle != null)
        {
            this.deviceType = intent.getStringExtra("device_name");
            this.isRomDevice = intent.getBooleanExtra("is_rom_device", false);
        }
        this.title = ((TextView)findViewById(R.id.device_bind_title));
        this.desTxt = ((TextView)findViewById(R.id.device_bind_des));
        this.upBtn = ((Button)findViewById(R.id.go_upgrade));
        this.upBtn.setOnClickListener(this);
        if (this.isRomDevice)
        {
            this.upBtn.setVisibility(View.GONE);
            this.title.setText(getString(R.string.bind_success));
            this.desTxt.setText(R.string.rom_device_des);
//            new AccessoryWareManager(this).checkServiceVersion();
            return;
        }
        this.upBtn.setVisibility(View.VISIBLE);
        this.title.setText(getString(R.string.rom_device_upgrade_title));
        this.desTxt.setText(R.string.rom_device_des);
    }
}