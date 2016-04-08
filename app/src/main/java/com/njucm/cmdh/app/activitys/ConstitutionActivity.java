package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.healthknowledges.HealthKnowledgeFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MyConstitutionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MycontitutehistoryFragment;
import com.njucm.cmdh.app.misc.Fragments;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ConstitutionActivity extends ActionBarActivity implements MyConstitutionFragment.OnFragmentInteractionListener, MycontitutehistoryFragment.OnFragmentInteractionListener{

    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置标题栏返回按钮
        setContentView(R.layout.activity_constitution);

        try {
            getSupportFragmentManager().beginTransaction().add(R.id.contitutecontent,
                    Fragment.instantiate(ConstitutionActivity.this, Fragments.MyConstitutionFragment.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置返回键
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        forceShowActionBarOverflowMenu();
    }
    private void forceShowActionBarOverflowMenu(){
        try{
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField!=null){
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config,false);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_constitution, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //点击“我的体质测试历史”跳转到
        if (id == R.id.myhistory) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contitutecontent, Fragment
                            .instantiate(ConstitutionActivity.this, Fragments.MyConstitute.getFragment()))
                    .commit();
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
