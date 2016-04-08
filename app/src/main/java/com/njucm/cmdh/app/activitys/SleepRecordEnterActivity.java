package com.njucm.cmdh.app.activitys;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.preference.DialogPreference;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.SleepInfoRecords;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SleepRecordEnterActivity extends ActionBarActivity {
    final int SINGLE_DIALOG = 0x113;
    MyApplication myApplication;   // base_url
    public ProgressDialog myDialog = null;
    RequestClient requestClient;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Button endIB;
    Button beginIB;
    Button queding;
    EditText sleepBeginTime;
    EditText sleepInfoRemark;
    EditText sleepBeginDay;
    EditText sleepEndDay;
    EditText sleepEndTime;
    String sleepinforemark;
    String begin;
    String end;
    public List<SleepInfoRecords> sleepInfoRecordses = new ArrayList<SleepInfoRecords>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_record_entering);
        setTitle(getResources().getString(R.string.title_activity_sleep_record_enter));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        myApplication = (MyApplication)getApplication();
        sleepBeginTime =(EditText)findViewById(R.id.sleep_begin_time);
        sleepInfoRemark = (EditText)findViewById(R.id.sleep_info_remark);
        sleepBeginDay = (EditText)findViewById(R.id.sleep_begin_day);
        sleepEndDay = (EditText)findViewById(R.id.sleep_end_day);
        sleepEndTime =(EditText)findViewById(R.id.sleep_end_time);
        endIB=(Button) findViewById(R.id.bt_sleep_end_time);
        beginIB=(Button) findViewById(R.id.bt_sleep_begin_time);
        queding = (Button)findViewById(R.id.sure);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (("".equals(sleepBeginTime.getText().toString().trim()))||("".equals(sleepEndTime.getText().toString().trim()))) {

                    showMessage("信息没有填完");
                }
//                else if (开始时间在结束时间之后){
//                    showMessage("时间信息有误");
//                }
                else {
                    sleepinforemark = sleepInfoRemark.getText().toString();
                    showDialog(SINGLE_DIALOG);
                }
            }
        });
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

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, myApplication.getMyUrl(), gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }

    }

    public void showMessage(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();
    }
    private SlideDateTimeListener beginListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            sleepBeginDay.setText(mFormatter.format(date).substring(0,10));
            sleepBeginTime.setText(mFormatter.format(date).substring(11, 19));
            begin = sleepBeginDay.getText().toString().trim()+" "+sleepBeginTime.getText().toString().trim();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(SleepRecordEnterActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

    private SlideDateTimeListener endListener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
            sleepEndDay.setText(mFormatter.format(date).substring(0,10));
            sleepEndTime.setText(mFormatter.format(date).substring(11, 19));
            end = sleepEndDay.getText().toString().trim()+" "+sleepEndTime.getText().toString().trim();
        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
            Toast.makeText(SleepRecordEnterActivity.this,
                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };
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
                        myDialog = ProgressDialog.show(SleepRecordEnterActivity.this, strDialogTitle, strDialogBody, true);
                        new Thread() {
                            public void run() {

                                try{
                                    sleep(2000);

                                    sleepInfoRecordses.add(new SleepInfoRecords(begin,end,sleepinforemark,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"吕昊"));
                                  //  if(NetHelper.isHaveInternet(getApplicationContext())) {
                                        requestClient.postSleepinforecords(sleepInfoRecordses, new Callback<Object>() {
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
//
//                                    }else{
//                                        Toast.makeText(getApplicationContext(),"请检查网络！",Toast.LENGTH_SHORT);
//                                    }
                                    SleepRecordEnterActivity.this.finish();
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

    @Override
    public void onBackPressed() {
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sport_record_entering, menu);
        return true;
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
