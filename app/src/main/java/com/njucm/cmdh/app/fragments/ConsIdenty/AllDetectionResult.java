package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.HealthSuggestionsActivity;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.PhysiqueInfoDBHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.request.JsonData;
import lecho.lib.hellocharts.formatter.AxisValueFormatter;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Mesogene on 3/23/15.
 */
public class AllDetectionResult extends ActionBarActivity {
    MyApplication myApplication; //为了修改IP
    ActionBar actionBar;
    String title1;
    String result1;
    String title2;
    String result2;
    String title3;
    String result3;
    String title4;
    String result4;
    String title5;
    String result5;
    String title6;
    String result6;
    String title7;
    String result7;
    String title8;
    String result8;
    String title9;
    String result9;
    int type1_score;
    int type2_score;
    int type3_score;
    int type4_score;
    int type5_score;
    int type6_score;
    int type7_score;
    int type8_score;
    int type9_score;
    private int listOfRemarks[]= new int [9];
    int max = 0;
    private List<PhysiqueInfo> physiqueInfos = new ArrayList<PhysiqueInfo>();
    private List<PhysiqueInfo> physiqueInfo_list;
    PhysiqueInfoDBHelper physiqueInfoDBHelper;
    String Interpretation;
    String Reason;
    String AdjustMethod;
    String Foodallowtaboo;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView what_is;
    TextView result_of;
    TextView how_adjust;
    TextView food;
    String constitution1;
    public String jianyou="";
    public String qingxiang="";
    int tizhi;
    RequestClient requestClient;
    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;
    LinearLayout linear4;
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
                   // physiqueInfo_list = physiqueInfoDBHelper.getPhysiqueInfoList(tizhi);
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.all_detection_result);
        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
         /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplication();
        ScrollView scrollView = (ScrollView)findViewById(R.id.scView);
        scrollView.setVerticalScrollBarEnabled(false);

        Button advice = (Button)findViewById(R.id.advice);
        textView1 = (TextView)findViewById(R.id.result1);
        textView2 = (TextView)findViewById(R.id.result2);
        textView3 = (TextView)findViewById(R.id.result3);
        what_is =(TextView)findViewById(R.id.what_is);
        result_of =(TextView)findViewById(R.id.result_of);
        how_adjust = (TextView)findViewById(R.id.how_adjust);
        food = (TextView)findViewById(R.id.food);

        linear1 = (LinearLayout)findViewById(R.id.linear_what_is);
        linear2 = (LinearLayout)findViewById(R.id.linear_result_of);
        linear3 = (LinearLayout)findViewById(R.id.linear_how_adjust);
        linear4 = (LinearLayout)findViewById(R.id.linear_food);


        Bundle bundle =this.getIntent().getExtras();
        result1 = bundle.getString("result0");
        title1 = bundle.getString("title0");
        result2 = bundle.getString("result1");
        title2 = bundle.getString("title1");
        result3 = bundle.getString("result2");
        title3 = bundle.getString("title2");
        result4 = bundle.getString("result3");
        title4 = bundle.getString("title3");
        result5 = bundle.getString("result4");
        title5 = bundle.getString("title4");
        result6 = bundle.getString("result5");
        title6 = bundle.getString("title5");
        result7 = bundle.getString("result6");
        title7 = bundle.getString("title6");
        result8 = bundle.getString("result7");
        title8 = bundle.getString("title7");
        result9 = bundle.getString("result8");
        title9 = bundle.getString("title8");
        type1_score =bundle.getInt("type1_score");
        type2_score =bundle.getInt("type2_score");
        type3_score =bundle.getInt("type3_score");
        type4_score =bundle.getInt("type4_score");
        type5_score =bundle.getInt("type5_score");
        type6_score =bundle.getInt("type6_score");
        type7_score =bundle.getInt("type7_score");
        type8_score =bundle.getInt("type8_score");
        type9_score =bundle.getInt("type9_score");

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            //requestClient = ServiceGenerator.createService(RequestClient.class, getString(R.string.base_url), gson);为了修改IP
            requestClient = ServiceGenerator.createService(RequestClient.class,MyApplication.myUrl, gson);
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }

        advice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(NetHelper.isHaveInternet(getApplicationContext())) {
                    Intent intent = new Intent(AllDetectionResult.this, HealthSuggestionsActivity.class);
                    startActivity(intent);
                    AllDetectionResult.this.finish();
//                }else{
//                    Toast.makeText(getApplicationContext(),"请检查网络！",Toast.LENGTH_SHORT);
//                }
            }
        });
        physiqueInfoDBHelper = PhysiqueInfoDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);

        listOfRemarks[0]=type1_score;
        listOfRemarks[1]=type2_score;
        listOfRemarks[2]=type3_score;
        listOfRemarks[3]=type4_score;
        listOfRemarks[4]=type5_score;
        listOfRemarks[5]=type6_score;
        listOfRemarks[6]=type7_score;
        listOfRemarks[7]=type8_score;
        listOfRemarks[8] =type9_score;
        getSupportFragmentManager().beginTransaction().add(R.id.container_of_myconstitution, new PlaceholderFragmentOfMyConstitution()).commit();
        int screenWidth = this.getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        findViewById(R.id.container_of_myconstitution).setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, screenHeight / 3));

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
                String[] interpretation = Interpretation.split("。");

                Reason = jsonData.optString("physicalreason");
                String[] reason = Reason.split("。");

                AdjustMethod = jsonData.optString("physicaladjustmethod");
                String[] adjustmethod = AdjustMethod.split("。");

                Foodallowtaboo = jsonData.optString("foodallowtaboo");
                String[] foodallowtaboo = Foodallowtaboo.split("。");

            for (String str : interpretation) {
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5, 5, 5, 5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);
                linear1.addView(linear);
            }

            for (String str : reason) {
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5, 5, 5, 5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);

                linear2.addView(linear);
            }
            for (String str : adjustmethod) {
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5, 5, 5, 5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                text.setTextColor(getResources().getColor(R.color.black));
                text.setTextSize(16);
                linear.addView(icon);
                linear.addView(text);
                text.setText(str);

                linear3.addView(linear);
            }
            for (String str : foodallowtaboo) {
                LinearLayout linear = new LinearLayout(getApplicationContext());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(5, 5, 5, 5);
                linear.setLayoutParams(layoutParams);
                FontAwesomeText icon = new FontAwesomeText(getApplicationContext());
                icon.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                icon.setTextColor(getResources().getColor(R.color.green_light));
                icon.setIcon("fa-check");
                TextView text = new TextView(getApplicationContext());
                text.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
    public  class PlaceholderFragmentOfMyConstitution extends Fragment {
        private static final int DEFAULT_DATA = 0;
        private static final int SUBCOLUMNS_DATA = 1;
        private static final int STACKED_DATA = 2;
        private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
        private static final int NEGATIVE_STACKED_DATA = 4;

        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = false;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        private int dataType = DEFAULT_DATA;
        private List<AxisValue> list;
        private AxisValue value;
        private AxisValueFormatter axisFormatter;
        private String[] arr = {"平和","阳虚", "阴虚", "痰湿", "湿热", "气郁", "气虚", "血淤", "特禀"};
        private String[] att = {"平和质","阳虚质", "阴虚质", "痰湿质", "湿热质", "气郁质", "气虚质", "血淤质", "特禀质"};

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_column_chart, container, false);

            chart = (ColumnChartView) rootView.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());

            list = new ArrayList<>();
            for(int i=0;i<9;i++){
                list.add(new AxisValue(i, arr[i].toCharArray()));

            }

            int index = 0;
            for(int i=1; i<9; i++){
                if(listOfRemarks[i]>=max){
                    max = listOfRemarks[i];
                    index = i;
                }
            }
            if(max>40){
                switch (index){
                    case 1:constitution1 = "阳虚质";tizhi = 2;break;
                    case 2:constitution1 = "阴虚质";tizhi = 3;break;
                    case 3:constitution1 = "痰湿质";tizhi = 4;break;
                    case 4:constitution1 = "湿热质";tizhi = 5;break;
                    case 5:constitution1 = "气郁质";tizhi = 6;break;
                    case 6:constitution1 = "气虚质";tizhi = 7;break;
                    case 7:constitution1 = "血瘀质";tizhi = 8;break;
                    case 8:constitution1 = "特廪质";tizhi = 9;break;
                    default:break;
                }
            }else if(max<30 && listOfRemarks[0]>60)
            {
                constitution1 = "平和质";
                tizhi =1;
            }else if(max<40 && max>30 && listOfRemarks[0]>60){
                constitution1 = "平和质";
                tizhi =1;
            }
            textView1.setText("您目前的体质："+constitution1);
            what_is.setText("什么是"+constitution1+"？");
            result_of.setText(constitution1+"的成因？");
            how_adjust.setText(constitution1+"如何调节？");
            food.setText("饮食宜忌");

            for(int i=1;i<9;i++){
                if(listOfRemarks[i]<=max&&listOfRemarks[i]>=40&&index!=i){
                    jianyou+=att[i]+"、";
                }
                if(listOfRemarks[i]>=30&&listOfRemarks[i]<=39){
                    qingxiang+=att[i]+"、";
                }

            }
            if(jianyou.length()<3){
                textView2.setVisibility(View.GONE);
            }else{
                textView2.setText("兼有"+jianyou);
            }
            if(qingxiang.length()<3){
                textView3.setVisibility(View.GONE);
            }else{
                textView3.setText("倾向"+qingxiang);
            }
            generateDefaultData();
          //  if(NetHelper.isHaveInternet(getApplicationContext())){
          //      new MyAsyncTask().execute();
          //  }else{
                handler.sendEmptyMessage(0);
          //  }
            return rootView;
        }


        private class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                String tizhi = "";
                switch (columnIndex){
                    case 0:tizhi = "平和质";break;
                    case 1:tizhi = "阳虚质";break;
                    case 2:tizhi = "阴虚质";break;
                    case 3:tizhi = "痰湿质";break;
                    case 4:tizhi = "湿热质";break;
                    case 5:tizhi = "气郁质";break;
                    case 6:tizhi = "气虚质";break;
                    case 7:tizhi = "血瘀质";break;
                    case 8:tizhi = "特廪质";break;
                    default:break;
                }
                Toast.makeText(getActivity(), tizhi+"得分:"+value.toString().substring(value.toString().indexOf("=")+1, value.toString().indexOf("]"))+"分", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }

        private void generateDefaultData() {
            int numSubcolumns = 1;
            int numColumns = 9;
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();

                values.add(new SubcolumnValue((listOfRemarks[i]), ChartUtils.pickColor()));


                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);


            if (hasAxes) {

                Axis axisX = new Axis().setValues(list);
                Axis axisY = new Axis().setHasLines(true);
                axisX.setMaxLabelChars(1);

                if (hasAxesNames) {
                    axisX.setName("Axis X");
                    axisY.setName("Axis Y");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);

            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);

        }


    }
}
