package com.njucm.cmdh.app.fragments.myhealthy.healthtending;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
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
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.RequestClient;

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


public class SleepTendingFragment extends BaseFragment {
    MyApplication myApplication;
    private static final String TAG = "sleepfragmentTest";
    public static int days;
    private FontAwesomeText fontAwesomeTextleft;
    private FontAwesomeText fontAwesomeTextright;
    private TextView textViewMonthchange;
    private TextView testViewBegin;
    private TextView testViewFinish;
    private TextView textViewSleepPerDay;
    private TextView textViewMoveDistance;
    private TextView textViewWakeTimePerday;
    @InjectView(R.id.tv_deepsleep_value)
    TextView deepSleepValue;
    @InjectView(R.id.tv_shadow_sleep_value)
    TextView shadowSleepValue;
    @InjectView(R.id.tv_intosleep_value)
    TextView intoSleepValue;
    @InjectView(R.id.tv_bedtime_value)
    TextView bedTimeValue;
    @InjectView(R.id.tv_cleartime_value)
    TextView clearTimeValue;
    @InjectView(R.id.tv_waketime_value)
    TextView wakeTimeValue;
    @InjectView(R.id.tv_sleep_analysis_content)
    TextView textViewsleepAnalysis;
    @InjectView(R.id.btv_thumbs_icon)
    FontAwesomeText fontAwesomeTextThumbs;
    @InjectView(R.id.btv_share_icon)
    FontAwesomeText fontAwesomeTextShare;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int userid = 11;
    private static JsonData sleepJsondata = null;
    private RequestClient requestClient;
    private SimpleDateFormat simpleDateFormatTime;
    private SimpleDateFormat formatMonth;
    private ProgressDialog progressDialog;
    public int allWakeTimes;
    public double allWalkDistance;
    public double allCalorieConsumption;
    private ProgressDialog dialog;
    private double wakeTimePerDay;
    private double allSleepTime;
    private double sleepTimePerDay;


    private OnFragmentInteractionListener mListener;
    private double allDeepSleepTime;
    private double allShadowSleepTime;
    private double allintoSleepTime;
    private double allBedTime;
    private double allClearTime;
    private double deepSleepTimePerday;
    private double shadowSleepTimePerday;
    private double intoSleepTimePerday;
    private double bedTimePerday;
    private double clearTImePerday;

    public SleepTendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_sleep_tending);
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
        testViewBegin = (TextView) findViewById(R.id.tv_sleep_begin);
        testViewFinish = (TextView) findViewById(R.id.tv_sleep_finish);
        textViewMoveDistance = (TextView) findViewById(R.id.tv_movedistance_value);
        textViewSleepPerDay = (TextView) findViewById(R.id.tv_sleeptimeperday_value);
        textViewWakeTimePerday = (TextView) findViewById(R.id.tv_waketimeperday_value);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
        new MyAsyncTask().execute("sleepallmon/", String.valueOf(userid), simpleDateFormat.format(new Date()));
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
                new MyAsyncTask().execute("sleepallmon/", String.valueOf(userid), DateToString(calendar.getTime()));
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
                new MyAsyncTask().execute("sleepallmon/", String.valueOf(userid), DateToString(calendar.getTime()).trim());
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
    }

    /**
     *
     * @param date
     * @return
     * 传入Date类型的日期参数，返回一个标准的日期类型yyyy-MM-dd
     */
    private String DateToString(Date date) {
        return simpleDateFormat.format(date);
    }

    /**
     *
     * @param year
     * @param month
     * @return
     * 通过年和月份计算出这个月的天数
     */
    public int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }

    /**
     * 异步的网络数据请求
     */
    public class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {

        @Override
        protected JsonData doInBackground(String... params) {
            try {
                sleepJsondata = JsonData.create(requestClient.getTendingDataByMonth("sleepallmon/", "7", textViewMonthchange.getText().toString().trim()));
            } catch (Exception e) {
                sleepJsondata = JsonData.create(null);
                e.printStackTrace();
            }
            return sleepJsondata;
        }

        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
            getChildFragmentManager().beginTransaction().replace(R.id.container, new PlaceholderFragment(jsonData)).commit();
            if (jsonData != null) {
                try {
//                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(jsonData.optString("sleepbegintime")));
                    testViewBegin.setText("1");
//                    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(jsonData.optString("sleepovertime")));
                    days = getDaysByYearMonth(Integer.parseInt(textViewMonthchange.getText().toString().trim()
                            .substring(0, 4)), Integer.parseInt(textViewMonthchange.getText().toString().trim().substring(5, 7)));
                    testViewFinish.setText(String.valueOf(days));
                    Log.d("time", simpleDateFormatTime.format(calendar.getTime()));
                } catch (Exception e) {
                    testViewBegin.setText("0");
                    testViewFinish.setText("0");
                    Log.d("dateerror", e.getMessage());
                }
                allWakeTimes = 0;
                wakeTimePerDay = 0;
                allSleepTime = 0;
                sleepTimePerDay = 0;
                allDeepSleepTime = 0;
                allShadowSleepTime = 0;
                allintoSleepTime = 0;
                allBedTime = 0;
                allClearTime = 0;
                deepSleepTimePerday = 0;
                shadowSleepTimePerday = 0;
                intoSleepTimePerday = 0;
                bedTimePerday = 0;
                clearTImePerday = 0;
                for(int i=0; i<jsonData.length(); i++){
                    allWakeTimes += jsonData.optJson(i).optInt("waketimes");
                    allSleepTime += jsonData.optJson(i).optDouble("sumsleepime");
                    allDeepSleepTime += jsonData.optJson(i).optDouble("deepsleeptime");
                    allShadowSleepTime += jsonData.optJson(i).optDouble("shallowsleeptime");
                    allintoSleepTime += jsonData.optJson(i).optDouble("intosleeptime");
                    allClearTime += jsonData.optJson(i).optDouble("cleartime");
                }
                wakeTimePerDay = allWakeTimes/jsonData.length();
                sleepTimePerDay = allSleepTime/jsonData.length();
                deepSleepTimePerday = allDeepSleepTime/jsonData.length();
                shadowSleepTimePerday = allShadowSleepTime/jsonData.length();
                intoSleepTimePerday = allintoSleepTime/jsonData.length();
                clearTImePerday = allClearTime/jsonData.length();

            } else {
                testViewBegin.setText("0");
                testViewFinish.setText("0");
            }
            if(String.valueOf(wakeTimePerDay).trim().length()>3)
                textViewWakeTimePerday.setText(String.valueOf(wakeTimePerDay).trim().substring(0, 3));
            else
                textViewWakeTimePerday.setText(String.valueOf(wakeTimePerDay).trim());
            if(String.valueOf(sleepTimePerDay).trim().length()>4)
                textViewSleepPerDay.setText(String.valueOf(sleepTimePerDay).trim().substring(0, 4));
            else
                textViewSleepPerDay.setText(String.valueOf(sleepTimePerDay).trim());
            deepSleepValue.setText(String.format("%.1f", deepSleepTimePerday).trim());
            shadowSleepValue.setText((shadowSleepTimePerday > 0 ? String.format("%.1f", shadowSleepTimePerday) : "0.0").trim());
            intoSleepValue.setText((intoSleepTimePerday > 0 ? String.format("%.1f", intoSleepTimePerday) : "0.0").trim());
            clearTimeValue.setText(String.valueOf(intoSleepTimePerday));
//            textViewCrawFloor.setText(String.valueOf(jsonData.optInt("crawledfloor")));
//            textViewLongsetActive.setText((jsonData.optDouble("longestactiveduration") > 0 ? String.format("%.1f", jsonData.optDouble("calorieconsumption")) : "0.0").trim());
//            textViewLongestIdle.setText((jsonData.optDouble("longestidleduration") > 0 ? String.format("%.1f", jsonData.optDouble("calorieconsumption")) : "0.0").trim());
//            textViewsleepAnalysis.setText(jsonData.optJson("alldata").optJson(0).optString("sleepanalysis"));
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
     * 一个包含有柱状图的fragment
     */
    public  class PlaceholderFragment extends android.support.v4.app.Fragment {

        private static final int DEFAULT_DATA = 0;
        private static final int SUBCOLUMNS_DATA = 1;
        private static final int STACKED_DATA = 2;
        private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
        private static final int NEGATIVE_STACKED_DATA = 4;

        private ColumnChartView sleepColumnChart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        private int dataType = DEFAULT_DATA;
        private JsonData sleepjsondata = null;

        public PlaceholderFragment() {
            this.sleepjsondata = sleepJsondata;
        }

        public PlaceholderFragment(JsonData sleepjsondata) {
            super();
            this.sleepjsondata = sleepjsondata;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_sport_column_chart, container, false);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, displayMetrics.heightPixels / 3);

            sleepColumnChart = (ColumnChartView) rootView.findViewById(R.id.sport_column_chart);
            sleepColumnChart.setLayoutParams(layoutParams);
            sleepColumnChart.setOnValueTouchListener(new ValueTouchListener());
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
            int numColumns = sleepjsondata.length();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            values = new ArrayList<SubcolumnValue>();
            if(sleepjsondata.optJson(0).optInt("deepsleeptime")!=0){
                String beginTime = sleepjsondata.optJson(0).optString("overtime");
                String endTime = sleepjsondata.optJson(sleepjsondata.length()-1).optString("overtime");
                String beginDay = beginTime.substring(beginTime.length()-2, endTime.length());  //当月从第几天开始测试的，没有测试的用0代替
                String endDay = endTime.substring(endTime.length()-2, endTime.length());  //当月从第几天开始测试的，没有测试的用0代替
                int realDays = 0;
                for (int i = 0; i < days; ++i) {
                    values = new ArrayList<SubcolumnValue>();
                    if((i+1)<=Integer.valueOf(endDay) && (i+1)>=Integer.valueOf(beginDay)){
                        values.add(new SubcolumnValue((float) sleepjsondata.optJson(realDays++).optInt("shallowsleeptime"), getResources().getColor(R.color.graphcontentcolor)));
                        values.add(new SubcolumnValue((float) sleepjsondata.optJson(realDays-1).optInt("deepsleeptime"), getResources().getColor(R.color.bbutton_danger_disabled)));
                        values.add(new SubcolumnValue((float) sleepjsondata.optJson(realDays-1).optInt("intosleeptime"), getResources().getColor(R.color.bbutton_info)));
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
            /**
             * 设置成堆叠形式的
             */
            data.setStacked(true);

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

            sleepColumnChart.setColumnChartData(data);

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

            sleepColumnChart.setColumnChartData(data);

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

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
