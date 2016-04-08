package com.njucm.cmdh.app.fragments.myhealthy;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.MyConstitution;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.fragments.ConsIdenty.ConsIdentyFragment;
import com.njucm.cmdh.app.fragments.NoDataFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
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

public class MyConstitutionFragment extends BaseFragment {
    MyApplication myApplication;
    @InjectView(R.id.constituteInfo)
    TextView constitution;
    @InjectView(R.id.btn_constitution)
    BootstrapButton btn;
    @InjectView(R.id.loading_imageview)
    ImageView loading;
    @InjectView(R.id.content)
    RelativeLayout content;
    @InjectView(R.id.loading_page)
    RelativeLayout loading_page;
    @InjectView(R.id.loading_text)
    TextView loading_text;
    @InjectView(R.id.what_is)
    TextView what_is;
    @InjectView(R.id.result_of)
    TextView result_of;
    @InjectView(R.id.how_adjust)
    TextView how_adjust;
    @InjectView(R.id.linear_what_is)
    LinearLayout linear1;
    @InjectView(R.id.linear_result_of)
    LinearLayout linear2;
    @InjectView(R.id.linear_how_adjust)
    LinearLayout linear3;
    @InjectView(R.id.linear_food)
    LinearLayout linear4;
    @InjectView(R.id.food)
    TextView food;

    private Context context;
    private String constitutionType;
    private String testTime;
    private String info;
    private static int flag = 0;
    private String constitution1;
    private String Interpretation;
    private String Reason;
    private String AdjustMethod;
    private String Foodallowtaboo;

    private OnFragmentInteractionListener mListener;
    public List<MyConstitution> myconstitution = new ArrayList<MyConstitution>();
    private Float[] listOfRemarks = new Float[9];
    RequestClient requestClient;
    TextView liaojie;

    public MyConstitutionFragment() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, context);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_constitution, null);
        context = getActivity().getApplicationContext();
        ButterKnife.inject(this, view);
        //开始旋转的动画
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);  //旋转的动画
        LinearInterpolator lir = new LinearInterpolator();
        anim.setInterpolator(lir);
        view.findViewById(R.id.loading_imageview).startAnimation(anim);

        //设置图像占据手机屏幕的比例为1/3
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        view.findViewById(R.id.container_of_myconstitution).setLayoutParams(new RelativeLayout.LayoutParams(screenWidth, screenHeight / 3));  //高度设置成屏幕高度的1/3

        if (getArguments() == null) {  //判断是否从我的体质历史界面点击回来的

                new AsyncTask<Void, Void, JsonData>() {  //执行下载任务

                @Override
                protected JsonData doInBackground(Void... params) {  //在后台执行的网络请求或者下载等费事的操作
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                    requestClient = ServiceGenerator.createService(RequestClient.class, HBContants.BASE_URL, gson, context);
                    try {
                        myconstitution = requestClient.getTcmmyconstitutionList("/tbconstituteidentifyresult", 2);
                    } catch (Exception e) {
                        Log.d("MyConstitutionFragment", "我的体质请求出错" + e.getMessage());
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return JsonData.create(myconstitution);
                }

                @Override
                protected void onPostExecute(JsonData jsonData) {
                    setContentView(view);
                    if(myconstitution.size() != 0){
                        try {
                            getChildFragmentManager().beginTransaction().add(R.id.container_of_myconstitution, new PlaceholderFragmentOfMyConstitution()).commit();
                        } catch (Exception e) {
                            Log.d("MyConstitutionFragment error", e.getMessage());
                        }
                        String tizhi =  String.valueOf(myconstitution.get(myconstitution.size()-1).getConstituteidentifyresult());
                        tizhi = tizhi.substring(tizhi.length()-3, tizhi.length());
                        constitution.setText(tizhi);
                        what_is.setText("什么是"+tizhi+"？");
                        result_of.setText(tizhi+"的成因？");
                        how_adjust.setText(tizhi+"如何调节？");
                        food.setText("饮食宜忌");
                        new MyAsyncTask().execute();
                        content.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        loading_text.setVisibility(View.GONE);
                    }else{
                        getChildFragmentManager().beginTransaction().replace(R.id.mycollection_content, new NoDataFragment()).commit();
                    }

                }

                @Override
                protected void onPreExecute() {  //在进行费事操作之前进行loading操作
                    super.onPreExecute();
                    setContentView(view);
                    content.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    loading_text.setVisibility(View.VISIBLE);
                }
            }.execute(new Void[]{});
        } else {  //如果是从历史界面回来的，那么就可以调用get方法，否则会创建最新的体质
            constitutionType = getArguments().getString("constitutionType");
            testTime = getArguments().getString("testTime");
            info = getArguments().getString("info");
            constitution.setText("您目前的体质是:" + constitutionType);
        }

        btn.setOnClickListener(new View.OnClickListener() {  //立即测试按钮跳转到体质辨识界面
            @Override
            public void onClick(View v) {
                try {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ConsIdentyFragment myf = new ConsIdentyFragment();
                    Bundle bundle = new Bundle();
                    ft.replace(R.id.contitutecontent, myf);
                    ft.addToBackStack(null);
                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    public class MyAsyncTask extends AsyncTask<String, Integer, JsonData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JsonData doInBackground(String... params) {

            try {
                int id = 2;
                String tizhi = myconstitution.get(myconstitution.size()-1).getConstituteidentifyresult();
                tizhi = tizhi.substring(tizhi.length()-3, tizhi.length());
//                switch (tizhi){
//                    case "平和质": id = 1;break;
//                    case "阳虚质": id = 2;break;
//                    case "阴虚质": id = 3;break;
//                    case "痰湿质": id = 4;break;
//                    case "湿热质": id = 5;break;
//                    case "气郁质": id = 6;break;
//                    case "气虚质": id = 7;break;
//                    case "血瘀质": id = 8;break;
//                    case "特禀质": id = 9;break;
//                }
                if(id != -1){
                    JsonData json = JsonData.create(requestClient.getPhysiqueInfoObj("tbphysiqueinfo"+"/"+id));
                    return  JsonData.create(requestClient.getPhysiqueInfoObj("tbphysiqueinfo"+"/"+id));
                }else{
                    return null;
                }

            } catch (Exception e) {
                Log.d("requesterror", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JsonData jsonData) {
            super.onPostExecute(jsonData);
            if (jsonData != null){
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
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
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

//    Runnable getPhysicalList = new Runnable() {
//        @Override
//        public void run() {
//            physiqueInfoItem = requestClient.getPhysiqueInfoList("tbphysiqueinfo", constitution1);
//            Bundle bundle = new Bundle();
//            bundle.putInt("1", 1);
//            Message message = new Message();
//            message.setData(bundle);
//            handler.sendMessage(message);
//        }
//    };
//
//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            PhysiqueInfo item = physiqueInfoItem.get(0);
//            StringBuilder text = new StringBuilder();
//            text.append(constitution1+"的表现为"+item.getGeneracharacter()+"<br>");
//            text.append(item.getShapefeature()+"<br>");
//            text.append(item.getCommonmanifest()+"<br>");
//            text.append(item.getIncidencetendency()+"<br>");
//            text.append(item.getAdaptivecapacity());
//            liaojie.setText(Html.fromHtml(text.toString()));
//        }
//    };

    /**
     * 包含柱状图的fragment
     */
    private  class PlaceholderFragmentOfMyConstitution extends Fragment {
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
        private String[] arr = {"平和", "阳虚", "阴虚", "痰湿", "湿热", "气郁", "气虚", "血淤", "特禀"};
        Float max = 0f;
        int index = 0;
        int size = myconstitution.size();

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
            for(int i=9;i>0;i--){
                listOfRemarks[9-i] = Float.parseFloat(myconstitution.get(size-i).getConstituteidentifyremarks());
            }
            for(int i=1; i<9; i++){
                if(listOfRemarks[i]>max){
                    max = listOfRemarks[i];
                    index = i;
                }
            }
            if(max>40){
                switch (index){
                    case 1:constitution1 = "阳虚质";break;
                    case 2:constitution1 = "阴虚质";break;
                    case 3:constitution1= "痰湿质";break;
                    case 4:constitution1 = "湿热质";break;
                    case 5:constitution1 = "气郁质";break;
                    case 6:constitution1 = "气虚质";break;
                    case 7:constitution1 = "血瘀质";break;
                    case 8:constitution1 = "特廪质";break;
                    default:break;
                }
            }else if(max<30 && listOfRemarks[0]>60)
            {
                constitution1 = "平和质";
            }else if(max<40 && max>30 && listOfRemarks[0]>60){
                constitution1 = "基本是平和质";
            }
            constitution.setText("您目前的体质是:" + constitution1);

//            new Thread(getPhysicalList).start();

            generateDefaultData();

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
            int numColumns = 9;
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 9 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i=numColumns; i>0; --i) {

                values = new ArrayList<SubcolumnValue>();
                values.add(new SubcolumnValue(Float.parseFloat(myconstitution.get(size-i).getConstituteidentifyremarks()), ChartUtils.pickColor()));
                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);


            if (hasAxes) {

                Axis axisX = new Axis().setValues(list);
                Axis axisY = new Axis().setHasLines(true);
                axisX.setMaxLabelChars(1);  //设置每一个column都有x轴的下标

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
