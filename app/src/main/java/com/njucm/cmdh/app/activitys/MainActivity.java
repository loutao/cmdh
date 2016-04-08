package com.njucm.cmdh.app.activitys;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.androidpn.ServiceManager;
import com.njucm.cmdh.app.fragments.ConsIdenty.ConsIdentyFragment;
import com.njucm.cmdh.app.fragments.healthdata.HealthDataFragement;
import com.njucm.cmdh.app.fragments.healthknowledges.HealthKnowledgeFragment;
import com.njucm.cmdh.app.fragments.mydevices.MyDevicesFragment;
import com.njucm.cmdh.app.fragments.myhealthy.MyHealthFragment;
import com.njucm.cmdh.app.misc.Fragments;
import com.njucm.cmdh.app.misc.NavigationDrawerItem;
import com.njucm.cmdh.app.navigations.NavigationDrawerView;
import com.njucm.cmdh.slidemenu.SlidingMenu;
import com.norbsoft.typefacehelper.ActionBarHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;


public class MainActivity extends ActionBarActivity {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private int currentSelectedPosition = 0;
    private FontAwesomeText fontAwesomeText;
    /*  DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
      int w_screen = displayMetrics.widthPixels;
      int h_screen = displayMetrics.heightPixels;
      RelativeLayout.LayoutParams layoutParams =
              new RelativeLayout.LayoutParams(w_screen/2, RelativeLayout.LayoutParams.MATCH_PARENT);*/
    private SlidingMenu slidingMenu;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mTitle;

    Bundle viewbundle = new Bundle();


    private List<NavigationDrawerItem> navigationItems;

    private DrawerArrowDrawable drawerArrow;


    @InjectView(R.id.navigationDrawerListViewWrapper)
    NavigationDrawerView mNavigationDrawerListViewWrapper;
    NavigationDrawerView mNavigationDrawerListViewWrapper_right;
    @InjectView(R.id.linearDrawer)
    LinearLayout mLinearDrawerLayout;
    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.leftDrawerListView)
    ListView leftDrawerListView;
    private List<NavigationDrawerItem> navigationItems_right;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        typeface(this, R.string.app_name);
        setTitle(typeface(this, R.string.app_name));
        ButterKnife.inject(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindOffsetWidth(displayMetrics.widthPixels / 3);
        slidingMenu.setMenu(R.layout.slidingmenu_right);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mNavigationDrawerListViewWrapper_right = (NavigationDrawerView) slidingMenu.findViewById(R.id.navigationDrawerListViewWrapper_right);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle = getTitle();
        ActionBarHelper.setTitle(
                getSupportActionBar(),
                typeface(mTitle));

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame,
                    Fragment.instantiate(MainActivity.this, Fragments.HealthKnowledge.getFragment())).commit();
            //instantiate的两个参数：1.context环境也就是获取它的类装载器---ClassLoader对象。
        } else {
//            getSupportFragmentManager().getFragments().clear();//2.要实例化的fragment的名称
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);   //当Activity跳转回来的时候获得之前的选项的界面
        }
        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        //添加左边下拉框选项
        navigationItems = new ArrayList<>();
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_healthknow), "fa-book", Color.parseColor("#6495ED"), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_myhealth), "fa-home", Color.parseColor("#3CB371"), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_healthdata), "fa-file-excel-o", Color.parseColor("#F4A460"), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_considenty), "fa-file", Color.parseColor("#FF7F50"), true));
        navigationItems.add(new NavigationDrawerItem(getString(R.string.fragment_mydevices), "fa-spinner", Color.parseColor("#FF0000"), true));
        //替换默认的选项
        mNavigationDrawerListViewWrapper.replaceWith(navigationItems);
        navigationItems_right = new ArrayList<>();
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_message), "fa-envelope-o", Color.parseColor("#FF00FF"), true));
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_active), "fa-gift", Color.parseColor("#FF0000"), true));
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_circle), "fa-comments", Color.parseColor("#1E90FF"), true));


        mNavigationDrawerListViewWrapper_right.replaceWith(navigationItems_right);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ActionBarHelper.setTitle(
                        getSupportActionBar(),
                        typeface(mTitle));
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        selectItem(currentSelectedPosition);
        //Start the service
//        ServiceManager serviceManager = new ServiceManager(this);
//        serviceManager.setNotificationIcon(R.drawable.zhihuizhongyi);
//        serviceManager.startService();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, currentSelectedPosition);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            /**
             *   菜单栏设置
             */
            Intent intent = new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.leftDrawerListView)
    public void OnItemClick(int position, long id) {
        if (mDrawerLayout.isDrawerOpen(mLinearDrawerLayout)) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
            onNavigationDrawerItemSelected(position);
            selectItem(position);
        }
    }

    private void selectItem(int position) {

        if (leftDrawerListView != null) {
            leftDrawerListView.setItemChecked(position, true);

            navigationItems.get(currentSelectedPosition).setSelected(false);
            navigationItems.get(position).setSelected(true);

            currentSelectedPosition = position;
            ActionBarHelper.setTitle(
                    getSupportActionBar(),
                    typeface(navigationItems.get(currentSelectedPosition).getItemName()));

           /* getSupportActionBar()
                    .setTitle(navigationItems.get(currentSelectedPosition).getItemName());*/
        }

        if (mLinearDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mLinearDrawerLayout);
        }
    }

    private void onNavigationDrawerItemSelected(int position) {
        switch (position) {
            case 0:
                if (!(getSupportFragmentManager().getFragments()
                        .get(0) instanceof HealthKnowledgeFragment)) {
                    viewbundle.putString("type", "HealthKnowledgeFragment");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.HealthKnowledge.getFragment()))
                            .commit();
                }
                break;
            case 1:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof MyHealthFragment)) {
                    viewbundle.putString("type", "MyHealthFragment");
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.MyHealth.getFragment()))
                            .commit();
                }
                break;
            case 2:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof HealthDataFragement)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.HealthData.getFragment()))
                            .commit();
                }
                break;
            case 3:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof ConsIdentyFragment)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.ConsIdenty.getFragment()))
                            .commit();
                }
                break;
            case 4:
                if (!(getSupportFragmentManager().getFragments().get(0) instanceof MyDevicesFragment)) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contentFrame, Fragment
                                    .instantiate(MainActivity.this, Fragments.MyDevices.getFragment()))
                            .commit();
                }
                break;
        }

    }

}
