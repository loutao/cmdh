package com.njucm.cmdh.app.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.Commonfoodtype;
import com.njucm.cmdh.app.domain.Exerciseinfo;
import com.njucm.cmdh.app.domain.SleepInfoRecords;
import com.njucm.cmdh.app.domain.SportInfoRecords;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SportRecordEnteringActivity extends ActionBarActivity {
    final int SINGLE_DIALOG = 0x113;
    MyApplication myApplication; //base_url
    public ProgressDialog myDialog = null;
    View view1;
    View view2;
    Button endIB;
    Button beginIB;
   // Button yulan;
   // Button submit;
    Button queding;
    ListView sportTypeList;
    TextView sporttype;
    EditText sportBeginTime;
    EditText sportBeginDay;
    EditText sportRemark;
    EditText sportEndTime;
    EditText sportEndDay;
    String sportinforemark;
    String begin;
    String end;
    int index;
    private int CHECKED_TAG = 0;
    private MyListAdapter mAdapter;
    public List<Exerciseinfo> exerciseinfoList;
    public List<SportInfoRecords> sportInfoRecordses = new ArrayList<SportInfoRecords>();
    RequestClient requestClient;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Runnable getData1 = new Runnable() {
        @Override
        public void run() {
            try {

                Gson gson = new Gson();
                InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.exerciseinfo);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                String json = new String(buffer);

                exerciseinfoList = gson.fromJson(json, new TypeToken<List<Exerciseinfo>>() {
                }.getType());

                // identifyquestion_list = identifyquestionissuess.subList(fromindex,toindex);
                Log.isLoggable("LLLLL", exerciseinfoList.size());
                handler.sendEmptyMessage(1);

            } catch (Exception e) {
                Log.d("PhysiqueTest", "体质信息请求出错" + e.getMessage());

            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:
                    sportTypeList.setAdapter(new MyListAdapter());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.activity_sport_record_entering, null);
        view2 = getLayoutInflater().inflate(R.layout.activity_sport_record_entering_two, null);
        setContentView(view1);
        setTitle(getResources().getString(R.string.title_activity_sport_record_enter));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        myApplication = (MyApplication) getApplication();
       // yulan = (Button) view1.findViewById(R.id.yulan);
       // submit = (Button) view1.findViewById(R.id.submit);
        sportTypeList = (ListView) view1.findViewById(android.R.id.list);
        sporttype = (TextView)view2.findViewById(R.id.sport_type);
        beginIB = (Button) view2.findViewById(R.id.bt_sport_begin_time);
        endIB = (Button) view2.findViewById(R.id.bt_sport_end_time);
        queding = (Button) view2.findViewById(R.id.sure);
        sportRemark = (EditText) view2.findViewById(R.id.sport_info_remark);
        sportBeginTime = (EditText) view2.findViewById(R.id.sport_begin_time);
        sportEndTime = (EditText) view2.findViewById(R.id.sport_end_time);
        sportBeginDay = (EditText) view2.findViewById(R.id.sport_begin_day);
        sportEndDay = (EditText) view2.findViewById(R.id.sport_end_day);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("".equals(sportBeginTime.getText().toString().trim()))||("".equals(sportEndTime.getText().toString().trim()))) {

                    showMessage("信息没有填完");
                }
//                else if (开始时间在结束时间之后){
//                    showMessage("时间信息有误");
//                }
                else {
                    sportinforemark = sportRemark.getText().toString();
                    showDialog(SINGLE_DIALOG);
                }


            }
        });
        mAdapter = new MyListAdapter();

        sportTypeList.setAdapter(mAdapter);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, myApplication.getMyUrl(), gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
        new Thread(getData1).start();
        sportTypeList.deferNotifyDataSetChanged();


        endIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(endListener)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                        .setIs24HourTime(true)
                                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });
        beginIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlideDateTimePicker.Builder(getSupportFragmentManager())
                        .setListener(beginListener)
                        .setInitialDate(new Date())
                                //.setMinDate(minDate)
                                //.setMaxDate(maxDate)
                        .setIs24HourTime(true)
                                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                                //.setIndicatorColor(Color.parseColor("#990000"))
                        .build()
                        .show();
            }
        });

        sportTypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(SINGLE_DIALOG);
//            }
//        });
    }
    public Dialog onCreateDialog(int id,Bundle state){
        switch (id){
            case SINGLE_DIALOG:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("确定提交吗？");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final CharSequence strDialogTitle = getString(R.string.str_dialog_title);
                        final CharSequence strDialogBody = getString(R.string.str_dialog_body);
                        myDialog = ProgressDialog.show(SportRecordEnteringActivity.this, strDialogTitle, strDialogBody, true);
                        new Thread() {
                            public void run() {

                                try{
                                    sleep(2000);

                                    sportInfoRecordses.add(new SportInfoRecords(begin, end, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), sportinforemark, "吕昊",index));
                                    // if(NetHelper.isHaveInternet(getApplicationContext())) {
                                    requestClient.postSportinforecords(sportInfoRecordses, new Callback<Object>() {
                                        @Override
                                        public void success(Object o, Response response) {
                                            Log.i("success", "插入成功");

                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.i("fail", "插入失败");
                                            showMessage("上传失败");

                                        }
                                    });

//                                    }else{
//                                        Toast.makeText(getApplicationContext(),"请检查网络！",Toast.LENGTH_SHORT);
//                                    }
                                    SportRecordEnteringActivity.this.finish();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }finally {
                                    myDialog.dismiss();
                                }

                            }
                        }.start();
                    }

                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
        }
        return null;

    }
    class MyListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        //        public MyListAdapter(Context context){
//            this.mInflater = LayoutInflater.from(context);
//
//        }
        public int getCount() {
            return 78;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(SportRecordEnteringActivity.this).inflate(R.layout.list_record, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.item_tv1);
                viewHolder.button = (Button) convertView.findViewById(R.id.record);
                viewHolder.view = (View) convertView.findViewById(R.id.view);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(exerciseinfoList.get(position).getExercisename());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToView2();
                    index = position;
                    sporttype.setText(exerciseinfoList.get(index).getExercisename());

                }
            });
            return convertView;
        }
    }

    void jumpToView2() {
        setContentView(view2);
        CHECKED_TAG =1;
    }

    void jumpToView1() {
        setContentView(view1);
        CHECKED_TAG =0;
    }
    public void showMessage(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();
    }
    private SlideDateTimeListener beginListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            sportBeginDay.setText(mFormatter.format(date).substring(0, 10));
            sportBeginTime.setText( mFormatter.format(date).substring(11, 19));
            begin = sportBeginDay.getText().toString().trim()+" "+sportBeginTime.getText().toString().trim();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(SportRecordEnteringActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private SlideDateTimeListener endListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date) {
            sportEndDay.setText(mFormatter.format(date).substring(0, 11));
            sportEndTime.setText(mFormatter.format(date).substring(11, 19));
            end = sportEndDay.getText().toString().trim()+" "+sportEndTime.getText().toString().trim();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel() {
            Toast.makeText(SportRecordEnteringActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                if(CHECKED_TAG==1){
                    sportRemark.setText("");
                    sportEndDay.setText("");
                    sportBeginDay.setText("");
                    sportBeginTime.setText("");
                    sportEndTime.setText("");
                    jumpToView1();
                }else {
                    finish();
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ViewHolder {

        TextView title;
        Button button;
        View view;
    }
}