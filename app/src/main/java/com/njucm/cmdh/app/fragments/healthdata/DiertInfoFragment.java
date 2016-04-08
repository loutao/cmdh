package com.njucm.cmdh.app.fragments.healthdata;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.EatRecordEnterActivity;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
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
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
public class DiertInfoFragment extends BaseFragment {
    MyApplication myApplication; //为了修改IP
    private static final String TAG = "diertfragmentTest";
    private FontAwesomeText fontAwesomeTextleft;
    private FontAwesomeText fontAwesomeTextright;
    private TextView textViewDatechange;
    @InjectView(R.id.tv_valueget_value)
    TextView textViewValueGet;
    @InjectView(R.id.tv_waterget_value)
    TextView textViewWaterGet;
    @InjectView(R.id.tv_fat_value)
    TextView textViewFatGet;
    @InjectView(R.id.tv_protein_value)
    TextView textViewProteinGet;
    @InjectView(R.id.tv_cholesterol_value)
    TextView textViewCholesterolGet;
    @InjectView(R.id.tv_fatty_acids_value)
    TextView textViewFattyGet;
    @InjectView(R.id.btv_eat_thumbs_icon)
    FontAwesomeText fontAwesomeTextThumbs;
    @InjectView(R.id.btv_eat_share_icon)
    FontAwesomeText fontAwesomeTextShare;
    @InjectView(R.id.fab_eatrecords_add)
    CustomFAB customFABAdd;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private int userid = 11;
    private static JsonData eatJsondata = null;
    private RequestClient requestClient;
    private SimpleDateFormat simpleDateFormatTime;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_diet_content);
            /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplicationContext();
        ButterKnife.inject(this, getContentView());
        fontAwesomeTextleft = (FontAwesomeText) findViewById(R.id.btv_eat_leftarrow);
        fontAwesomeTextright = (FontAwesomeText) findViewById(R.id.btv_eat_rightarrow);
        textViewDatechange = (TextView) findViewById(R.id.tv_eat_datachange);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormatTime = new SimpleDateFormat("HH:mm");
        calendar.setTime(new Date());
        textViewDatechange.setText(DateToString(calendar.getTime()));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getResources().getString(R.string.loadingtitle));
        progressDialog.setMessage(getResources().getString(R.string.loadingtext));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
        new MyAsyncTask().execute("eatingtype", String.valueOf(userid), simpleDateFormat.format(new Date()));
        fontAwesomeTextleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Date date = simpleDateFormat.parse(textViewDatechange.getText().toString().trim());
                    calendar.setTime(date);
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                } catch (ParseException e) {
                    Log.d("datachangeerror", e.getMessage());
                }
                textViewDatechange.setText(DateToString(calendar.getTime()).trim());
                new MyAsyncTask().execute("eatingtype", String.valueOf(userid), DateToString(calendar.getTime()));
            }
        });
        fontAwesomeTextright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textViewDatechange.getText().toString().compareTo(simpleDateFormat.format(new Date())) >= 0) {
                    calendar.setTime(new Date());
                } else {
                    try {
                        Date date = simpleDateFormat.parse(textViewDatechange.getText().toString().trim());
                        calendar.setTime(date);
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    } catch (ParseException e) {
                        Log.d("datachangeerror", e.getMessage());
                    }
                }
                textViewDatechange.setText(DateToString(calendar.getTime()).trim());
                new MyAsyncTask().execute("eatingtype", String.valueOf(userid), DateToString(calendar.getTime()).trim());
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
        customFABAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), EatRecordEnterActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }
    private String DateToString(Date date) {

        return simpleDateFormat.format(date);
    }



    public class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {
        @Override
        protected JsonData doInBackground(String... params) {
            try {
                eatJsondata = JsonData.create(requestClient.getOneEatingData(params[0], params[1], params[2]));
            } catch (Exception e) {
                eatJsondata = JsonData.create(null);
                e.printStackTrace();
            }
            return eatJsondata;
        }
        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
            getChildFragmentManager().beginTransaction().replace(R.id.container, new PlaceholderFragment(jsonData.optJson("alldata"))).commit();
            textViewValueGet.setText((jsonData.optDouble("能量") > 0 ? String.format("%.1f", jsonData.optDouble("能量")) : "0.00").trim());
            textViewFatGet.setText((jsonData.optDouble("脂肪") > 0 ? String.format("%.1f", jsonData.optDouble("脂肪")) : "0.00").trim());
            textViewWaterGet.setText((jsonData.optDouble("水分") > 0 ? String.format("%.1f", jsonData.optDouble("水分")) : "0.00").trim());
            textViewFattyGet.setText((jsonData.optDouble("饱和脂肪酸") > 0 ? String.format("%.1f", jsonData.optDouble("饱和脂肪酸")) : "0.00").trim());
            textViewProteinGet.setText((jsonData.optDouble("蛋白质") > 0 ? String.format("%.1f", jsonData.optDouble("蛋白质")) : "0.00").trim());
            textViewCholesterolGet.setText((jsonData.optDouble("胆固醇") > 0 ? String.format("%.1f", jsonData.optDouble("胆固醇")) : "0.00").trim());
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
     * A fragment containing a pie chart.
     */
    public static class PlaceholderFragment extends Fragment {
        private static final int DEFAULT_DATA = 0;
        private static final int SUBCOLUMNS_DATA = 1;
        private static final int STACKED_DATA = 2;
        private static final int NEGATIVE_SUBCOLUMNS_DATA = 3;
        private static final int NEGATIVE_STACKED_DATA = 4;
        private ColumnChartView EatColumnChart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = false;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        private int dataType = DEFAULT_DATA;
        private JsonData eatjsondata = null;
        public PlaceholderFragment() {
            this.eatjsondata = eatJsondata;
        }
        public PlaceholderFragment(JsonData sportjsondata) {
            super();
            this.eatjsondata = sportjsondata;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            View rootView = inflater.inflate(R.layout.fragment_sport_column_chart, container, false);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, displayMetrics.heightPixels / 3);
            EatColumnChart = (ColumnChartView) rootView.findViewById(R.id.sport_column_chart);
            EatColumnChart.setLayoutParams(layoutParams);
            EatColumnChart.setOnValueTouchListener(new ValueTouchListener());
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
            int numColumns = eatjsondata.length();
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            List<AxisValue> list=new ArrayList<AxisValue>();
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                values.add(new SubcolumnValue((float) eatjsondata.optJson(i).optInt("value"), getResources().getColor(R.color.graphcontentcolor)));
                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);
            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(false);
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

            EatColumnChart.setColumnChartData(data);
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
            EatColumnChart.setColumnChartData(data);

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
                // Toast.makeText(getActivity(), "步数: " + value.getValue(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onValueDeselected() {
                // TODO Auto-generated method stub
            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }
    @Override
    public void onPause() {
        super.onPause();
        this.onDetach();
        Log.d(TAG, "onPause");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }
}
