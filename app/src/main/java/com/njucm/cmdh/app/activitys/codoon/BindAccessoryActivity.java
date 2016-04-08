package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.ratio.deviceService.datamanager.AccessoryManager;


public class BindAccessoryActivity extends Activity implements View.OnClickListener {
    private LinearLayout bleBandLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_accessory);
        getActionBar().hide();
        bleBandLayout = ((LinearLayout) findViewById(R.id.bind_codoon_bleband));
        bleBandLayout.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bind_accessory, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_codoon_bleband:
                startSeartchDevice("codoon");
                return;
        }
    }

    public void startSeartchDevice(String paramString) {
        if (!AccessoryManager.isSupportBLEDevice(this)) {
            return;
        }
        if (paramString != null) {
            Intent localIntent = new Intent(this, DiscoveryAccessoryActivity.class);
            localIntent.putExtra("is_rom_device",true);
            localIntent.putExtra("device_name", paramString);
            startActivityForResult(localIntent, 0);
        }
    }
}
