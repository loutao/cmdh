package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.DietSuggestionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.HealthSuggestionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.SleepSuggestionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.SportSuggestionsDetailFragment;
import com.njucm.cmdh.app.misc.Fragments;

public class HealthSuggestionsActivity extends ActionBarActivity implements HealthSuggestionFragment.OnFragmentInteractionListener, DietSuggestionFragment.OnFragmentInteractionListener, SportSuggestionsDetailFragment.OnFragmentInteractionListener, SleepSuggestionFragment.OnFragmentInteractionListener{

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_suggestions);

        try {
            getSupportFragmentManager().beginTransaction().add(R.id.healthsuggestions,
                    Fragment.instantiate(HealthSuggestionsActivity.this, Fragments.HealthSuggestion.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置返回键
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_health_suggestions, menu);
//        return true;
//    }

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
