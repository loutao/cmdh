package com.njucm.cmdh.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.njucm.cmdh.app.R;

/**
 * Created by Mesogene on 5/12/15.
 */
public class CustomDialog extends Dialog{
    TranslateHelper translateHelper = null;
    Context context;
    //选择体质按钮
    BootstrapButton btn1;
    BootstrapButton btn2;
    BootstrapButton btn3;
    BootstrapButton btn4;
    BootstrapButton btn5;
    BootstrapButton btn6;
    BootstrapButton btn7;
    BootstrapButton btn8;
    BootstrapButton btn9;

    /**********通过这个方法，将回调函数的地址传入到Dialog中*********/
    public void setTranslateHelper(TranslateHelper helper){
        this.translateHelper = helper;
    }

    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_custom_dialog_acivity);
        btn1 = (BootstrapButton) findViewById(R.id.con1);
        btn2 = (BootstrapButton) findViewById(R.id.con2);
        btn3 = (BootstrapButton) findViewById(R.id.con3);
        btn4 = (BootstrapButton) findViewById(R.id.con4);
        btn5 = (BootstrapButton) findViewById(R.id.con5);
        btn6 = (BootstrapButton) findViewById(R.id.con6);
        btn7 = (BootstrapButton) findViewById(R.id.con7);
        btn8 = (BootstrapButton) findViewById(R.id.con8);
        btn9 = (BootstrapButton) findViewById(R.id.con9);
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.activity_health_suggestion_expand, null);
        btn1.setOnClickListener(new MyListener("平和质"));
        btn2.setOnClickListener(new MyListener("阳虚质"));
        btn3.setOnClickListener(new MyListener("阴虚质"));
        btn4.setOnClickListener(new MyListener("痰湿质"));
        btn5.setOnClickListener(new MyListener("湿热质"));
        btn6.setOnClickListener(new MyListener("气郁质"));
        btn7.setOnClickListener(new MyListener("气虚质"));
        btn8.setOnClickListener(new MyListener("血淤质"));
        btn9.setOnClickListener(new MyListener("特禀质"));
    }

    private class MyListener implements View.OnClickListener {
        String cons;
        public MyListener(String cons) {
            this.cons = cons;
        }

        @Override
        public void onClick(View v) {
            translateHelper.refreshActivity(cons);
            dismiss();
        }
    }

}