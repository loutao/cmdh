package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;

/**
 * Created by Mesogene on 7/13/15.
 */
public class SettingActivity extends ActionBarActivity {
    MyApplication myApplication;
    ActionBar actionBar;
    TextView changeIp;
    TextView showIP;
    EditText newIP;
    String base_url;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("设置");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        myApplication = (MyApplication)getApplication();
        changeIp = (TextView)findViewById(R.id.change_ip);
        changeIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("修改IP地址");
                LinearLayout ChangeIp = (LinearLayout)getLayoutInflater().inflate(R.layout.change_ip,null);
                builder.setView(ChangeIp);
                newIP = (EditText)ChangeIp.findViewById(R.id.new_ip);
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String str = newIP.getText().toString();
                        Log.d("url",str);
                        //此处可执行修改ip处理
                        myApplication.setMyUrl(str);
                        myApplication.restartApplication();
                        SettingActivity.this.finish();
                    }
                });
                builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //取消修改，不做任何事情
                    }
                });
                builder.create().show();
            }
        });
        showIP = (TextView)findViewById(R.id.show_ip);
        showIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView)findViewById(R.id.ip);
                textView.setText(MyApplication.myUrl);
                textView.setVisibility(View.VISIBLE);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
