package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.HealthSuggestionCombileSimplify;
import com.njucm.cmdh.app.domain.Recommendedconditionsmapp;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.HealthSuggestionCombileSimplifyDBHelper;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;

public class HealthSuggestionExpand2 extends ActionBarActivity {
    MyApplication myApplication; //为修改IP
    ActionBar actionBar;
    private List<Recommendedconditionsmapp> healthSuggestions = new ArrayList<>();
    RequestClient requestClient;
    RelativeLayout picback;
    private TextView maintext, maintext2;
    private TextView constitution;
    private String constitution1;
    //cube加载图片的类
    ImageLoader imageLoader;
    //图片
    CubeImageView imageView;
    //图片名称
    TextView picname;
    //收藏块
    LinearLayout li1;
    //存在数据库中的实体类
    HealthSuggestionCombileSimplify healthSuggestionCombileSimplify;
    //收藏文字
    TextView collectornot;
    //隐藏的字段用来，记录收藏id
    TextView recommendid;

    HealthSuggestionCombileSimplifyDBHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);  //必须在setCOntentView之前调用
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
       // requestClient = ServiceGenerator.createService(RequestClient.class, getString(R.string.base_url), gson, getApplicationContext());
        requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        /*
        * 为修改IP
        */
        myApplication = (MyApplication)getApplication();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("constitution");
        constitution1 = bundle.getString("constitution");

        setContentView(R.layout.activity_health_suggestion_expand2);  //必须在find方法前面
        imageView = (CubeImageView) findViewById(R.id.pic);
        picname = (TextView) findViewById(R.id.name);
        maintext = (TextView) findViewById(R.id.maintext);
        maintext2 = (TextView) findViewById(R.id.maintext2);
        constitution = (TextView) findViewById(R.id.constitution);
        li1 = (LinearLayout) findViewById(R.id.li1);
        imageLoader = ImageLoaderFactory.create(getApplicationContext());
        collectornot = (TextView) findViewById(R.id.collectornot);
        recommendid = (TextView) findViewById(R.id.recommendid);
        healthSuggestionCombileSimplify = new HealthSuggestionCombileSimplify(Boolean.FALSE);
        helper = HealthSuggestionCombileSimplifyDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);

        li1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(collectornot.getText().toString().trim().equals("收藏")){
                    healthSuggestionCombileSimplify.setTemp_healthsuggestlimitedid(healthSuggestions.get(0).getTemp_healthsuggestlimitedid());
                    healthSuggestionCombileSimplify.setHealthsuggestcontent(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestcontent());
                    healthSuggestionCombileSimplify.setHealthsuggestremarks(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestremarks());
                    healthSuggestionCombileSimplify.setTemp_healthsuggtypeid(healthSuggestions.get(0).getTemp_healthsuggestid().getTemp_healthsuggtypeid());
                    healthSuggestionCombileSimplify.setOriginalpicturepath(healthSuggestions.get(0).getTemp_healthsuggestid().getTemp_picturelocationid().getOriginalpicturepath());
                    healthSuggestionCombileSimplify.setPicturename(healthSuggestions.get(0).getTemp_healthsuggestid().getTemp_picturelocationid().getPicturename());
                    healthSuggestionCombileSimplify.setHealthsuggestflag(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestflag());
                    healthSuggestionCombileSimplify.setHealthsuggestcode(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestcode());
                    healthSuggestionCombileSimplify.setRecommendedconditionsmappid(healthSuggestions.get(0).getRecommendedconditionsmappid());
                    helper.addHealthSuggestionSimplify(healthSuggestionCombileSimplify);
                    collectornot.setText("取消收藏");
                    Toast.makeText(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    helper.deleteHealthSuggestionSimplify(Integer.parseInt(recommendid.getText().toString().trim()));
                    collectornot.setText("收藏");
                }

            }
        });

        new Thread(getHealthSuggestionExpand).start();

        picback = (RelativeLayout) findViewById(R.id.picback);
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        picback.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, screenHeight * 5 / 12));  //高度设置成屏幕高度的5/12

    }

    private Runnable getHealthSuggestionExpand = new Runnable() {
        @Override
        public void run() {
            if(NetHelper.isHaveInternet(getApplicationContext())){
                try {
                    healthSuggestions = requestClient.getHealthSuggestion("tbrecommendedconditionsmappcombine/", "体质与饮食建议", constitution1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(), "请先检查网络！", Toast.LENGTH_SHORT).show();

            }

            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("1", 1);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String name = healthSuggestions.get(0).getTemp_healthsuggestid().getTemp_picturelocationid().getPicturename();
            maintext.setText(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestremarks());
            maintext2.setText(healthSuggestions.get(0).getTemp_healthsuggestid().getHealthsuggestcontent());
            imageView.loadImage(imageLoader, healthSuggestions.get(0).getTemp_healthsuggestid().getTemp_picturelocationid().getOriginalpicturepath());
            actionBar.setTitle(name);
            picname.setText(name);
            constitution.setText(healthSuggestions.get(0).getTemp_healthsuggestlimitedid());
            recommendid.setText(healthSuggestions.get(0).getRecommendedconditionsmappid().toString());
            if(helper.findCollectStatusById(Integer.parseInt(recommendid.getText().toString().trim()))){
                collectornot.setText("取消收藏");
            }else{
                collectornot.setText("收藏");
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_suggestion_expand2, menu);
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
}
