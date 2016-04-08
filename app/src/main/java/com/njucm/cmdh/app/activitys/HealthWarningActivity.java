package com.njucm.cmdh.app.activitys;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.myhealthy.healthwarning.HealthWarningFragment;
import com.njucm.cmdh.app.misc.Fragments;

public class HealthWarningActivity extends ActionBarActivity implements HealthWarningFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_warning);
        try {
            getSupportFragmentManager().beginTransaction().add(R.id.healthwarning,
                    Fragment.instantiate(HealthWarningActivity.this, Fragments.HealthWarning.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_warning, menu);
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
    public void onFragmentInteraction(String id) {

    }
}
