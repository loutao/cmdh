package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.myhealthy.healthtending.DietTendingFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthtending.ElementSelectFragment;
import com.njucm.cmdh.app.misc.Fragments;

public class HealthTendingActivity extends ActionBarActivity implements DietTendingFragment.OnFragmentInteractionListener
            , ElementSelectFragment.OnFragmentInteractionListener{


    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tending);

        try {
            getSupportFragmentManager().beginTransaction().add(R.id.healthtending,
                    Fragment.instantiate(HealthTendingActivity.this, Fragments.HealthTending.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置返回键
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_tending, menu);
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

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
