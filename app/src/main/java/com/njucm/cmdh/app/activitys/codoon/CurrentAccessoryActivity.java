package com.njucm.cmdh.app.activitys.codoon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.MainActivity;
import com.njucm.cmdh.app.fragments.healthdata.HealthDataFragement;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CurrentAccessoryActivity extends Activity implements View.OnClickListener {
    @InjectView(R.id.tv_check_health_data)
    TextView mtextViewHealthdata;
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_bind_accessory);
        ButterKnife.inject(this);
        getActionBar().hide();
        mtextViewHealthdata.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_current_accessory, menu);
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
            case R.id.tv_check_health_data:
                Intent intent = new Intent();
                intent.setClass(this, DeviceDataActivity.class);
                startActivity(intent);

        }

    }
}
