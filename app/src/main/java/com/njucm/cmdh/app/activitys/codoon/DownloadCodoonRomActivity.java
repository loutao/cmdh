package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.njucm.cmdh.app.R;


public class DownloadCodoonRomActivity extends Activity  implements View.OnClickListener
{
    private static final String downURL = "http://static.codoon.com/app/android/codoon_rom.apk";
    private Button download;
    private Button finish;

    private void initView()
    {
        this.finish = ((Button)findViewById(R.id.btn_finish));
        this.download = ((Button)findViewById(R.id.btn_down_rom));
        this.finish.setOnClickListener(this);
        this.download.setOnClickListener(this);
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
           /* default:
                return;
            case 2131428355:
                finish();
                return;
            case 2131428356:
        }
        paramView = new Intent();
        paramView.setAction("android.intent.action.VIEW");
        paramView.setData(Uri.parse("http://static.codoon.com/app/android/codoon_rom.apk"));
        startActivity(paramView);*/
        }
    }

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.download_rom_activity);
        getActionBar().hide();
        initView();
    }
}