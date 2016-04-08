package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;

import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.PhysiqueInfoDBHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.util.logging.LogRecord;

/**
 * Created by Mesogene on 3/20/15.
 */
public class PhysiqueIntroduce extends ActionBarActivity {
    MyApplication myApplication; //为了修改IP
    ActionBar actionBar;
    int physiqueinfoid;
    String title;
    int tizhi;
    String generacharacter;
    String shapefeature;
    String commonmanifest;
    String mentalcharacter;
    String incidencetendency;
    String adaptivecapacity;

    private Context context;
    TextView zongti;
    TextView xingti;
    TextView changjian;
    TextView xinli;
    TextView fabing;
    TextView shiying;
    private List<PhysiqueInfo> physiqueInfo_list;
    private List<PhysiqueInfo> physiqueInfos = new ArrayList<PhysiqueInfo>();
    RequestClient requestClient;
     PhysiqueInfoDBHelper physiqueInfoDBHelper;

//    Runnable getData = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                if(physiqueInfoDBHelper.getPhysiqueInfoList().size()==0){
//                    physiqueInfo_list = requestClient.getPhysiqueInfo("tbphysiqueinfo");
//                    handler.sendEmptyMessage(0);
//                }else if(physiqueInfoDBHelper.getPhysiqueInfoList().size()!=0){
//                   physiqueInfo_list = physiqueInfoDBHelper.getPhysiqueInfoList(tizhi);
//                   handler.sendEmptyMessage(1);
//                }else{
//                    Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
//
//                }
//            }catch (Exception e){
//                Log.d("MyConstitutionFragment", "体质信息请求出错" + e.getMessage());
//            }
//        }
//    };
    Runnable getData1 = new Runnable() {
        @Override
        public void run() {
            try{
                Gson gson = new Gson();
                InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.physiqueinfo);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                String json = new String(buffer);
                physiqueInfos = gson.fromJson(json,new TypeToken<List<PhysiqueInfo>>(){}.getType());
                physiqueInfo_list = physiqueInfos.subList(tizhi-1,tizhi);

                handler.sendEmptyMessage(1);
            }catch (Exception e){
                Log.d("PhysiqueIntroduce","体质信息请求出错"+e.getMessage());
            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    for(PhysiqueInfo physiqueInfo:physiqueInfo_list){
                        physiqueInfoDBHelper.addPhysiqueInfo(physiqueInfo);
                    }
                    zongti.setText(physiqueInfo_list.get(physiqueinfoid).getGeneracharacter());
                    xingti.setText(physiqueInfo_list.get(physiqueinfoid).getShapefeature());
                    changjian.setText(physiqueInfo_list.get(physiqueinfoid).getCommonmanifest());
                    xinli.setText(physiqueInfo_list.get(physiqueinfoid).getMentalcharacter());
                    fabing.setText(physiqueInfo_list.get(physiqueinfoid).getIncidencetendency());
                    shiying.setText(physiqueInfo_list.get(physiqueinfoid).getAdaptivecapacity());
                    break;
                case 1:

                    zongti.setText(physiqueInfo_list.get(0).getGeneracharacter());
                    xingti.setText(physiqueInfo_list.get(0).getShapefeature());
                    changjian.setText(physiqueInfo_list.get(0).getCommonmanifest());
                    xinli.setText(physiqueInfo_list.get(0).getMentalcharacter());
                    fabing.setText(physiqueInfo_list.get(0).getIncidencetendency());
                    shiying.setText(physiqueInfo_list.get(0).getAdaptivecapacity());
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce_pinghezhi);
        context = this;
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
          /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplication();
        Bundle bundle = this.getIntent().getExtras();
        physiqueinfoid = bundle.getInt("physiqueinfoid");
        tizhi = physiqueinfoid + 1;
        title = bundle.getString("title");
        actionBar.setTitle(String.valueOf(title));
        ScrollView scrollView = (ScrollView) findViewById(R.id.scView);
        scrollView.setVerticalScrollBarEnabled(false);
        Button goToTry = (Button) findViewById(R.id.goToTry);
        zongti = (TextView) findViewById(R.id.general_characteristics);
        xingti = (TextView) findViewById(R.id.physical_performance);
        changjian = (TextView) findViewById(R.id.common);
        xinli = (TextView) findViewById(R.id.psychological_characteristics);
        fabing = (TextView) findViewById(R.id.incidence_tendency);
        shiying = (TextView) findViewById(R.id.external_adaptability);
        FontAwesomeText testboot = (FontAwesomeText) findViewById(R.id.tv_zhongti_bootstrap);

        physiqueInfoDBHelper = PhysiqueInfoDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
      //  new Thread(getData).start();
        new Thread(getData1).start();
        zongti.setText(String.valueOf(generacharacter));
        xingti.setText(String.valueOf(shapefeature));
        changjian.setText(String.valueOf(commonmanifest));
        xinli.setText(String.valueOf(mentalcharacter));
        fabing.setText(String.valueOf(incidencetendency));
        shiying.setText(String.valueOf(adaptivecapacity));
        goToTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionBar.getTitle().equals("平和质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",1);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("阳虚质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",2);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("阴虚质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",3);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("痰湿质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",4);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("湿热质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",5);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("气郁质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",6);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("气虚质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",7);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                } else if (actionBar.getTitle().equals("血瘀质")) {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",8);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                }  else {
                    Intent intent = new Intent(PhysiqueIntroduce.this, PhysiqueTest.class);
                    Bundle bundle = new Bundle();
                    String title = actionBar.getTitle().toString();
                    bundle.putString("title", title);
                    bundle.putInt("type",9);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    PhysiqueIntroduce.this.finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pinghezhi, menu);

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
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}