package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.PhysiqueInfoDBHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.request.JsonData;

/**
 * Created by Mesogene on 3/23/15.
 */
public class DetectionResult extends ActionBarActivity {
    MyApplication myApplication; //为了修改IP
    ActionBar actionBar;
    int tizhi;
    String title;
    String result;
    private List<PhysiqueInfo> physiqueInfos = new ArrayList<PhysiqueInfo>();
    private List<PhysiqueInfo> physiqueInfo_list;
    PhysiqueInfoDBHelper physiqueInfoDBHelper;

    String Interpretation;
    String Reason;
    String AdjustMethod;
    String Foodallowtaboo;

    RequestClient requestClient;
    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;
    LinearLayout linear4;
    public DetectionResult() {
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
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
                    //physiqueInfo_list = physiqueInfoDBHelper.getPhysiqueInfoList(tizhi);
                    Interpretation = physiqueInfo_list.get(0).getPhysicalinterpretation();
                    String[] interpretation= Interpretation.split("。");

                    Reason = physiqueInfo_list.get(0).getPhysicalreason();
                    String[] reason = Reason.split("。");

                    AdjustMethod = physiqueInfo_list.get(0).getPhysicaladjustmethod();
                    String[] adjustmethod = AdjustMethod.split("。");

                    Foodallowtaboo = physiqueInfo_list.get(0).getFoodallowtaboo();
                    String[] foodallowtaboo = Foodallowtaboo.split("。");

                    for(String str:interpretation){
                        LinearLayout linear = new LinearLayout(getApplicationContext());
                        linear.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5,5,5,5);
                        linear.setLayoutParams(layoutParams);
                        FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        icon.setTextColor(getResources().getColor(R.color.green_light));
                        icon.setIcon("fa-check");
                        TextView text = new TextView(getApplicationContext());
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setTextSize(16);
                        linear.addView(icon);
                        linear.addView(text);
                        text.setText(str);
                        linear1.addView(linear);
                    }

                    for(String str:reason){
                        LinearLayout linear = new LinearLayout(getApplicationContext());
                        linear.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5,5,5,5);
                        linear.setLayoutParams(layoutParams);
                        FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        icon.setTextColor(getResources().getColor(R.color.green_light));
                        icon.setIcon("fa-check");
                        TextView text = new TextView(getApplicationContext());
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setTextSize(16);
                        linear.addView(icon);
                        linear.addView(text);
                        text.setText(str);

                        linear2.addView(linear);
                    }
                    for(String str:adjustmethod){
                        LinearLayout linear = new LinearLayout(getApplicationContext());
                        linear.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5,5,5,5);
                        linear.setLayoutParams(layoutParams);
                        FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        icon.setTextColor(getResources().getColor(R.color.green_light));
                        icon.setIcon("fa-check");
                        TextView text = new TextView(getApplicationContext());
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setTextSize(16);
                        linear.addView(icon);
                        linear.addView(text);
                        text.setText(str);

                        linear3.addView(linear);
                    }
                    for(String str:foodallowtaboo){
                        LinearLayout linear = new LinearLayout(getApplicationContext());
                        linear.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                        layoutParams.setMargins(5,5,5,5);
                        linear.setLayoutParams(layoutParams);
                        FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                        icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        icon.setTextColor(getResources().getColor(R.color.green_light));
                        icon.setIcon("fa-check");
                        TextView text = new TextView(getApplicationContext());
                        text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                        text.setTextColor(getResources().getColor(R.color.black));
                        text.setTextSize(16);
                        linear.addView(icon);
                        linear.addView(text);
                        text.setText(str);

                        linear4.addView(linear);
                    }
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detection_result);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
          /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplication();
        ScrollView scrollView = (ScrollView) findViewById(R.id.scView);
        scrollView.setVerticalScrollBarEnabled(false);

        Button tryOther = (Button)findViewById(R.id.try_other);
        TextView textView =(TextView)findViewById(R.id.result);
        TextView what_is =(TextView)findViewById(R.id.what_is);
        TextView result_of =(TextView)findViewById(R.id.result_of);
        TextView how_adjust = (TextView)findViewById(R.id.how_adjust);
        TextView food = (TextView)findViewById(R.id.food);
        linear1 = (LinearLayout)findViewById(R.id.linear_what_is);
        linear2 = (LinearLayout)findViewById(R.id.linear_result_of);
        linear3 = (LinearLayout)findViewById(R.id.linear_how_adjust);
        linear4 = (LinearLayout)findViewById(R.id.linear_food);

        Bundle bundle =this.getIntent().getExtras();
        result = bundle.getString("result");
        title = bundle.getString("title");
        tizhi = bundle.getInt("tizhi");
        tryOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetectionResult.this.finish();
            }
        });
        physiqueInfoDBHelper = PhysiqueInfoDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson);
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
        textView.setText(result+String.valueOf(title));
        what_is.setText("什么是"+title+"？");
        result_of.setText(title+"的成因？");
        how_adjust.setText(title+"如何调节？");
        food.setText("饮食宜忌");

        if(NetHelper.isHaveInternet(getApplicationContext())){
            new MyAsyncTask().execute();
        }else{
            handler.sendEmptyMessage(0);
        }


    }
    public class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
            if (jsonData != null)
                Interpretation = jsonData.optString("physicalinterpretation");
                String[] interpretation= Interpretation.split("。");

                Reason = jsonData.optString("physicalreason");
                String[] reason = Reason.split("。");

                AdjustMethod = jsonData.optString("physicaladjustmethod");
                String[] adjustmethod = AdjustMethod.split("。");

                Foodallowtaboo = jsonData.optString("foodallowtaboo");
                String[] foodallowtaboo = Foodallowtaboo.split("。");

                for(String str:interpretation){
                    LinearLayout linear = new LinearLayout(getApplicationContext());
                    linear.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(5,5,5,5);
                    linear.setLayoutParams(layoutParams);
                    FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                    icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    icon.setTextColor(getResources().getColor(R.color.green_light));
                    icon.setIcon("fa-check");
                    TextView text = new TextView(getApplicationContext());
                    text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    text.setTextColor(getResources().getColor(R.color.black));
                    text.setTextSize(16);
                    linear.addView(icon);
                    linear.addView(text);
                    text.setText(str);
                    linear1.addView(linear);
                }

            for(String str:reason){
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5,5,5,5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);

                linear2.addView(linear);
            }
            for(String str:adjustmethod){
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5,5,5,5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);

                linear3.addView(linear);
            }
            for(String str:foodallowtaboo){
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5,5,5,5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);
                linear4.addView(linear);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected JsonData doInBackground(String... params) {

            try {
                return  JsonData.create(requestClient.getPhysiqueInfoObj("tbphysiqueinfo"+"/"+tizhi));

            } catch (Exception e) {
                Log.d("requesterror", e.getMessage());
                return null;
            }
        }
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
