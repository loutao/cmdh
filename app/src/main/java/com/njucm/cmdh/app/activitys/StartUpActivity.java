package com.njucm.cmdh.app.activitys;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import com.njucm.cmdh.app.R;

public class StartUpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DITHER);

        setContentView(R.layout.activity_start_up);

        //Display the current version number
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("org.wordpress.android", 0);
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            versionNumber.setText("Version " + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* Create an Intent that will start the Main WordPress Activity. */
                Intent mainIntent = new Intent(StartUpActivity.this, MainActivity.class);
                StartUpActivity.this.startActivity(mainIntent);
                StartUpActivity.this.finish();
            }
        }, 2900); //2900 for release

    }

}
