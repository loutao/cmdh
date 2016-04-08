package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.app.Fragment;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.myhealthy.habit.HabitFragment;
import com.njucm.cmdh.app.misc.Fragments;

public class HabitActivity extends ActionBarActivity implements HabitFragment.OnFragmentInteractionListener{
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit);
        try {
            getSupportFragmentManager().beginTransaction().add(R.id.container_of_habit,
                    android.support.v4.app.Fragment.instantiate(HabitActivity.this, Fragments.HabitFragment1.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_habit, menu);
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
        }else if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
