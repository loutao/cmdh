package com.njucm.cmdh.app.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.user.LoginService;
import com.njucm.cmdh.app.activitys.user.UserLoginActivity;
import com.njucm.cmdh.app.misc.Fragments;
import com.njucm.cmdh.app.misc.NavigationDrawerItem;
import com.njucm.cmdh.app.navigations.NavigationDrawerView;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.RequestClient;
import com.njucm.cmdh.slidemenu.SlidingMenu;
import com.njucm.cmdh.viewpager.indicator.Indicator;
import com.njucm.cmdh.viewpager.indicator.IndicatorViewPager;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.request.JsonData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity2 extends ActionBarActivity implements View.OnClickListener {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private IndicatorViewPager indicatorViewPager;
    private int currentSelectedPosition = 0;
    private SlidingMenu slidingMenu;
    NavigationDrawerView mNavigationDrawerListViewWrapper_right;
    private List<NavigationDrawerItem> navigationItems_right;
    TextView tv_login, tv_logout;
    Button btn_testAuth;
    MyApplication myApplication;
    RequestClient testRequestClient;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        setTitle(R.string.app_name);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindOffsetWidth(displayMetrics.widthPixels / 3);
        slidingMenu.setMenu(R.layout.slidingmenu_right);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        tv_login = (TextView) slidingMenu.findViewById(R.id.drawerUserName);
        tv_logout = (TextView) slidingMenu.findViewById(R.id.tv_logout);
        btn_testAuth = (Button) slidingMenu.findViewById(R.id.drawerUserButton);
        tv_logout.setOnClickListener(this);
        tv_login.setOnClickListener(this);
        btn_testAuth.setOnClickListener(this);
        mNavigationDrawerListViewWrapper_right = (NavigationDrawerView) slidingMenu.findViewById(R.id.navigationDrawerListViewWrapper_right);
        navigationItems_right = new ArrayList<>();
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_message), "fa-envelope-o", Color.parseColor("#FF00FF"), true));
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_active), "fa-gift", Color.parseColor("#FF0000"), true));
        navigationItems_right.add(new NavigationDrawerItem(getString(R.string.user_info_listitem_circle), "fa-comments", Color.parseColor("#1E90FF"), true));


        mNavigationDrawerListViewWrapper_right.replaceWith(navigationItems_right);
        ViewPager viewPager = (ViewPager) findViewById(R.id.tabmain_viewPager);
        Indicator indicator = (Indicator) findViewById(R.id.tabmain_indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(1);
        // 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
        viewPager.setPrepareNumber(0);
        viewPager.setCurrentItem(2);
      /*  if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.contentFrame,
                    Fragment.instantiate(MainActivity2.this, Fragments.HealthKnowledge.getFragment())).commit();
            //instantiate的两个参数：1.context环境也就是获取它的类装载器---ClassLoader对象。
        } else {
//            getSupportFragmentManager().getFragments().clear();//2.要实例化的fragment的名称
            currentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);   //当Activity跳转回来的时候获得之前的选项的界面
        }*/

        myApplication = (MyApplication) getApplication();
        if (LoginService.isLogin(this)) {
            isLogined();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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
            Intent intent = new Intent(MainActivity2.this, SettingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawerUserName:
                Intent intent = new Intent();
                intent.setClass(MainActivity2.this, UserLoginActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.tv_logout:
                LoginService.clearLogin(this);
                tv_logout.setVisibility(View.GONE);
                tv_login.setText(getString(R.string.no_login_name));
                tv_login.setClickable(true);
                break;
            case R.id.drawerUserButton:
                try {
                    testRequestClient = ServiceGenerator.createAuthService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
                    testRequestClient.getauthTest("me", new Callback<Object>() {
                        @Override
                        public void success(Object o, Response response) {
                            Log.d("username", JsonData.create(o).optJson(0).optString("username"));
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("username", "error");

                        }
                    });
                } catch (Exception e) {
                    Log.d("errorinfo", e.getMessage());
                }
                break;

        }

    }

    private void isLogined() {
        SharedPreferences sp1 = this.getSharedPreferences("user_config", Context.MODE_PRIVATE);
        String login = sp1.getString("login", "");
        tv_login.setText(login);
        tv_login.setClickable(false);
        tv_logout.setVisibility(View.VISIBLE);

    }

   /* class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected JsonData doInBackground(String... params) {
           MyApplication myApplication = (MyApplication)getApplication();
            RequestClient testRequestClient;
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            try {
              testRequestClient = ServiceGenerator.createService(RequestClient.class, myApplication.getMyUrl(), gson, getApplicationContext());
            } catch (Exception e) {
                Log.d("errorinfo", e.getMessage());
            }
            return testRequestClient.getauthTest(new ca);
        }
    }*/

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"知识", "健康", "数据", "体质", "设备"};
        private String[] tabIcons = {"fa-book", "fa-home", "fa-file-excel-o", "fa-file", "fa-spinner"};
        /* private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector,
                 R.drawable.maintab_4_selector};*/
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = (View) inflater.inflate(R.layout.tab_main, container, false);
                FontAwesomeText tv_navigationitemconvertViewIcon = (FontAwesomeText) convertView.findViewById(R.id.navigationDrawerItemIconMainIcon);
                tv_navigationitemconvertViewIcon.setIcon(tabIcons[position]);
                TextView tv_navigationitemconvertViewText = (TextView) convertView.findViewById(R.id.navigationDrawerItemIconMainText);
                tv_navigationitemconvertViewText.setText(tabNames[position]);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            switch (position) {
                case 0:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.HealthKnowledge.getFragment());
                case 1:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.MyHealth.getFragment());
                case 2:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.HealthData.getFragment());
                case 3:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.ConsIdenty.getFragment());
                case 4:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.MyDevices.getFragment());
                default:
                    return Fragment
                            .instantiate(MainActivity2.this, Fragments.HealthKnowledge.getFragment());
            }

        }
    }
}
