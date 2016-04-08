package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.myhealthy.healthcollection.DoctorItemFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthcollection.HeleathSuggestionCollectionFragment;
import com.njucm.cmdh.app.misc.Fragments;
import com.norbsoft.typefacehelper.ActionBarHelper;

import java.util.ArrayList;
import java.util.Map;

import butterknife.ButterKnife;
import in.srain.cube.request.JsonData;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MyCollectionActivity extends ActionBarActivity implements HeleathSuggestionCollectionFragment.OnFragmentInteractionListener,
        DoctorItemFragment.OnFragmentInteractionListener{


    private Context context;
    private CharSequence mTitle;
    ActionBar actionBar;
    private PtrClassicFrameLayout mPtrFrame_Health;
    private ListViewDataAdapter<JsonData> mAdapter_mycollectionhistory;

    ArrayList<Map<String, Object>> collectionData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(typeface(this, R.string.mycollection));
        mTitle = getTitle();

        ActionBarHelper.setTitle(
                getSupportActionBar(),
                typeface(mTitle));

        setContentView(R.layout.activity_my_collection);
        context = getApplication().getApplicationContext();
        try {
            getSupportFragmentManager().beginTransaction().add(R.id.mycollection_content,
                    Fragment.instantiate(MyCollectionActivity.this, Fragments.MyCollection.getFragment())).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ButterKnife.inject(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_my_collection, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

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
}
