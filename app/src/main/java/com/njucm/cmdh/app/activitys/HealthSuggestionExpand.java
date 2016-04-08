package com.njucm.cmdh.app.activitys;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.cengalabs.flatui.views.FlatEditText;
import com.cengalabs.flatui.views.FlatToggleButton;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.utils.CustomDialog;
import com.njucm.cmdh.app.utils.TranslateHelper;


public class HealthSuggestionExpand extends ActionBarActivity {
//    private FlatButton btn;
    CustomDialog dialog;
    ActionBar actionBar;
    //是否本人
    FlatToggleButton isself;
    //性别
    RadioGroup radioGroup;
    //体质
    TextView constitution1;
    //工作
    FlatEditText work;
    TextView cons1;
    //后面的标签
    FontAwesomeText angle_right;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_suggestion_expand);
        dialog = new CustomDialog(HealthSuggestionExpand.this, R.style.CustomDialog);
        TranslateHelper helper = new TranslateHelper() {
            @Override
            public void refreshActivity(String text) {  //在dialog中调用
                cons1.setText(text);
                cons1.setTextColor(Color.rgb(102, 153, 0));
                angle_right.setVisibility(View.INVISIBLE);
            }
        };
        dialog.setTranslateHelper(helper);  //把helper传入到CustomDialog里面,就可以调用这个对象的refreshDialog方法了。
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setTitle("查一查");
        constitution1 = (TextView) findViewById(R.id.conschoice);
        cons1 = (TextView) findViewById(R.id.conschoice);
        angle_right = (FontAwesomeText) findViewById(R.id.angle_right);

        constitution1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Window dialogWindow = dialog.getWindow();
                dialog.show();
                dialog.setTitle("请选择您的体质");
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                WindowManager m = getWindowManager();
                Display d = m.getDefaultDisplay(); lp.width = (int)(d.getWidth()*0.8);
                lp.height = (int) (d.getHeight()*0.6);
                dialogWindow.setAttributes(lp);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_health_suggestion_expand, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
        }else if(id == android.R.id.home){
            this.finish();
        }else if(id == R.id.action_accept){
            if(!cons1.getText().toString().equals("")){
                Bundle bundle = new Bundle();
                bundle.putString("constitution", cons1.getText().toString().trim());
                bundle.putString("flag", "HealthSuggestionExpand");  //用來判斷從哪一個界面傳過去
                Intent intent = new Intent();
                intent.putExtra("constitution", bundle);
                intent.setClass(getApplicationContext(), HealthSuggestionExpand2.class);
                startActivity(intent);
                //设置切换动画，从右边进入，左边退出
                overridePendingTransition(R.anim.dync_in_from_right, R.anim.dync_out_to_left);
            }else{
                Toast.makeText(getApplicationContext(), "体质信息必须要填写！", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
