package com.njucm.cmdh.app.fragments.myhealthy.healthtending;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.ElementSelectActivity;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.ElementSelectListener;
import com.njucm.cmdh.app.utils.RequestClient;
import com.njucm.cmdh.app.widgetlibrary.CustomFAB;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.request.JsonData;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;

public class DietTendingFragment extends BaseFragment {
    MyApplication myApplication;
    private static final String TAG = "sportfragmentTest";
    public static int days;
    private FontAwesomeText fontAwesomeTextleft;
    private FontAwesomeText fontAwesomeTextright;
    private TextView textViewMonthchange;
    private TextView testViewBegin;
    private TextView testViewFinish;
    private TextView textViewStempnumber;
    private TextView textViewMoveDistance;
    private TextView textViewTotalcalorieConsume;
    @InjectView(R.id.tv_energyintake_value)
    TextView energyIntakeValue;
    @InjectView(R.id.tv_moistureintake_value)
    TextView moistureIntakeValue;
    @InjectView(R.id.tv_proteinintake_value)
    TextView proteinIntakeValue;
    @InjectView(R.id.tv_fatintake_value)
    TextView fatIntakeValue;
    @InjectView(R.id.tv_cholesterolintake_value)
    TextView cholesterolIntakeValue;
    @InjectView(R.id.tv_saturatedintake_value)
    TextView saturatedIntakeValue;
    @InjectView(R.id.tv_sport_analysis_content)
    TextView textViewSportAnalysis;
    @InjectView(R.id.btv_thumbs_icon)
    FontAwesomeText fontAwesomeTextThumbs;
    @InjectView(R.id.btv_share_icon)
    FontAwesomeText fontAwesomeTextShare;
    @InjectView(R.id.fab_sportrecords_add)
    CustomFAB customFABAdd;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int userid = 11;
    private static JsonData dietJsondata = null;
    private RequestClient requestClient;
    private SimpleDateFormat simpleDateFormatTime;
    private SimpleDateFormat formatMonth;
    private ProgressDialog progressDialog;
    public int allWalkNumbers;
    public double allWalkDistance;
    public double allCalorieConsumption;
    ElementSelectListener elementSelectListener;


    private OnFragmentInteractionListener mListener;
    private JsonData eatingJsondata;

    public DietTendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.healthtendingbottommenu, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        Log.d(TAG, "========================onCreateView called==========================");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sport_tending);
        ButterKnife.inject(this, getContentView());
        myApplication = (MyApplication)getApplicationContext();
        fontAwesomeTextleft = (FontAwesomeText) findViewById(R.id.btv_leftarrow);
        fontAwesomeTextright = (FontAwesomeText) findViewById(R.id.btv_rightarrow);
        textViewMonthchange = (TextView) findViewById(R.id.tv_datachange);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormatTime = new SimpleDateFormat("HH:mm");
        formatMonth = new SimpleDateFormat("yyyy-MM");
        calendar.setTime(new Date());
        textViewMonthchange.setText(DateToString(calendar.getTime()).substring(0 ,7));
        testViewBegin = (TextView) findViewById(R.id.tv_sport_begin);
        testViewFinish = (TextView) findViewById(R.id.tv_sport_finish);
        textViewMoveDistance = (TextView) findViewById(R.id.tv_movedistance_value);
        textViewStempnumber = (TextView) findViewById(R.id.tv_stepnumber_value);
        textViewTotalcalorieConsume = (TextView) findViewById(R.id.tv_calorieconsume_value);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
        new MyAsyncTask("energy").execute();
        fontAwesomeTextleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Date date = formatMonth.parse(textViewMonthchange.getText().toString().trim());  //只带年和月份的Date
                    calendar.setTime(date);
                    calendar.add(Calendar.MONTH, -1);  //月份减一个月
                } catch (ParseException e) {
                    Log.d("datachangeerror", e.getMessage());
                }
                textViewMonthchange.setText(DateToString(calendar.getTime()).substring(0, 7).trim());
                new MyAsyncTask("energy").execute();
            }
        });
        fontAwesomeTextright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewMonthchange.getText().toString().compareTo(simpleDateFormat.format(new Date())) >= 0) {
                    calendar.setTime(new Date());
                } else {
                    try {
                        Date date = formatMonth.parse(textViewMonthchange.getText().toString().trim());
                        calendar.setTime(date);
                        calendar.add(Calendar.MONTH, 1);
                    } catch (ParseException e) {
                        Log.d("datachangeerror", e.getMessage());
                    }
                }
                textViewMonthchange.setText(DateToString(calendar.getTime()).trim().substring(0, 7));
                new MyAsyncTask("energy").execute();
            }
        });
        fontAwesomeTextThumbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fontAwesomeTextThumbs.setTextColor(Color.RED);
            }
        });
        fontAwesomeTextShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        /**
         * 实现回调方法
         */
        final ElementSelectActivity esa = new ElementSelectActivity();
        ElementSelectListener elementSelectListener = new ElementSelectListener() {
            @Override
            public void refreshChart(String elementType) {
                new MyAsyncTask(elementType).execute();
            }
        };
        esa.setElementSelectListener(elementSelectListener);

        customFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), esa.getClass());
                startActivity(intent);
            }
        });

    }

    private String DateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {
        private String element;

        public MyAsyncTask(String element) {
            this.element = element;
        }

        public MyAsyncTask() {
        }

        @Override
        protected JsonData doInBackground(String... params) {
            try {
                dietJsondata = JsonData.create(requestClient.getElementIntakeList("eatingallmon/", "11", textViewMonthchange.getText().toString().trim(), element));
                eatingJsondata = JsonData.create(requestClient.getEatingAmmountPerday("eatingallmon/", 11, textViewMonthchange.getText().toString().trim()));
            } catch (Exception e) {
                dietJsondata = JsonData.create(null);
                e.printStackTrace();
            }
            return dietJsondata;
        }

        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
            getChildFragmentManager().beginTransaction().replace(R.id.container, new PlaceholderFragment(jsonData)).commitAllowingStateLoss();

            if (jsonData != null) {
                try {
//                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(jsonData.optString("sportbegintime")));
                    testViewBegin.setText("1");
//                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(jsonData.optString("sportovertime")));
                    days = getDaysByYearMonth(Integer.parseInt(textViewMonthchange.getText().toString().trim()
                            .substring(0, 4)), Integer.parseInt(textViewMonthchange.getText().toString().trim().substring(5, 7)));
                    testViewFinish.setText(String.valueOf(days));
                    Log.d("time", simpleDateFormatTime.format(calendar.getTime()));
                } catch (Exception e) {
                    testViewBegin.setText("0");
                    testViewFinish.setText("0");
                    Log.d("dateerror", e.getMessage());
                }

            } else {
                testViewBegin.setText("0");
                testViewFinish.setText("0");
            }
            energyIntakeValue.setText((eatingJsondata.optDouble("饱和脂肪酸") > 0 ? String.format("%.1f", eatingJsondata.optDouble("饱和脂肪酸")) : "0.0").trim());
            moistureIntakeValue.setText(String.format("%.1f", eatingJsondata.optDouble("能量")));
            proteinIntakeValue.setText(String.format("%.1f", eatingJsondata.optDouble("水分")));
            fatIntakeValue.setText(String.format("%.1f", eatingJsondata.optDouble("胆固醇")));
            cholesterolIntakeValue.setText(String.format("%.1f", eatingJsondata.optDouble("脂肪")));
            saturatedIntakeValue.setText(String.format("%.1f", eatingJsondata.optDouble("蛋白质")));
//            textViewCrawFloor.setText(String.valueOf(jsonData.optInt("crawledfloor")));
//            textViewLongsetActive.setText((jsonData.optDouble("longestactiveduration") > 0 ? String.format("%.1f", jsonData.optDouble("calorieconsumption")) : "0.0").trim());
//            textViewLongestIdle.setText((jsonData.optDouble("longestidleduration") > 0 ? String.format("%.1f", jsonData.optDouble("calorieconsumption")) : "0.0").trim());
//            textViewSportAnalysis.setText(jsonData.optJson("alldata").optJson(0).optString("sportanalysis"));
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
    /**
     * A fragment containing a line chart and preview line chart.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final int DEFAULT_DATA = 0;
        private static final int SUBCOLUMNS_DATA = 1;
        private static final int STACKED_DATA = 2;
        private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
        private static final int NEGATIVE_STACKED_DATA = 4;

        private ColumnChartView SportColumnChart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        private int dataType = DEFAULT_DATA;
//        private JsonData dietJsondata = null;

        public PlaceholderFragment() {
        }

        public PlaceholderFragment(JsonData dietJsondata) {
            super();
//            this.dietJsondata = dietJsondata;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_sport_column_chart, container, false);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, displayMetrics.heightPixels / 3);

            SportColumnChart = (ColumnChartView) rootView.findViewById(R.id.sport_column_chart);
            SportColumnChart.setLayoutParams(layoutParams);
            SportColumnChart.setOnValueTouchListener(new ValueTouchListener());
            toggleAxes();
            return rootView;
        }

        // MENU
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.column_chart, menu);
        }


        private void generateDefaultData() {
            int numSubcolumns = 1;
            if(dietJsondata != null){
                int numColumns = dietJsondata.length();
            }
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            values = new ArrayList<SubcolumnValue>();
            if(dietJsondata.length() != 1 && dietJsondata.optJson(0).optString("intake")!="0" && dietJsondata!=null){
                String beginTime = dietJsondata.optJson(0).optString("uptime");
                String endTime = dietJsondata.optJson(dietJsondata.length()-1).optString("uptime");
                String beginDay = beginTime.substring(beginTime.length()-2, endTime.length());  //当月从第几天开始测试的，没有测试的用0代替
                String endDay = endTime.substring(endTime.length()-2, endTime.length());  //当月从第几天开始测试的，没有测试的用0代替
                int k=0;
                for (int i = 0; i < days; ++i) {
                    values = new ArrayList<SubcolumnValue>();
                    if((i+1)<=Integer.valueOf(endDay) && (i+1)>=Integer.valueOf(beginDay)){
                        values.add(new SubcolumnValue((float) dietJsondata.optJson(k++).optInt("intake"), getResources().getColor(R.color.graphcontentcolor)));
                    }else{
                        values.add(new SubcolumnValue(0f, getResources().getColor(R.color.graphcontentcolor)));
                    }
                    Column column = new Column(values);
                    column.setHasLabels(hasLabels);
                    column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                    columns.add(column);
                }
            }
            data = new ColumnChartData(columns);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
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

            SportColumnChart.setColumnChartData(data);

        }

        /**
         * Generates columns with subcolumns, columns have larger separation than subcolumns.
         */
        private void generateSubcolumnsData() {
            int numSubcolumns = 61;
            int numColumns = 1;
            // Column can have many subcolumns, here I use 4 subcolumn in each of 8 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    values.add(new SubcolumnValue((float) Math.random() * 50f + 5, ChartUtils.pickColor()));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
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

            SportColumnChart.setColumnChartData(data);

        }


        private void generateData() {
            switch (dataType) {
                case DEFAULT_DATA:
                    generateDefaultData();
                    break;
                case SUBCOLUMNS_DATA:
                    generateSubcolumnsData();
                    break;
                default:
                    generateDefaultData();
                    break;
            }
        }


        private void toggleAxes() {
            hasAxes = !hasAxes;

            generateData();
        }

        /**
         * To animate values you have to change targets values and then call {@link lecho.lib.hellocharts.view.Chart#startDataAnimation()}
         * method(don't confuse with View.animate()).
         */

        private class ValueTouchListener implements ColumnChartOnValueSelectListener {

            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
                Toast.makeText(getActivity(), "步数: " + value.getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub

            }

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }




}
