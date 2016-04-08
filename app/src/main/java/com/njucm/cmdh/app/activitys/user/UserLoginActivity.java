package com.njucm.cmdh.app.activitys.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.MainActivity2;
import com.njucm.cmdh.app.domain.User;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.RequestClient;

import in.srain.cube.request.JsonData;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserLoginActivity extends ActionBarActivity implements View.OnClickListener {
    MyApplication myApplication; //为了修改IP
    Button btn_login;
    EditText et_login, et_password;
    User user = new User();
    TextView tv_regist;
    RequestClient loginRequestClient;
    RequestClient testAuthRequestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login4);
        setTitle(getString(R.string.userlogin));
        getActionBar().setDisplayHomeAsUpEnabled(true);
    /*
        * 为了修改IP
        * */
        myApplication = (MyApplication) getApplication();
        tv_regist = (TextView) findViewById(R.id.tv_regist);
        tv_regist.setClickable(true);
        tv_regist.setFocusable(true);
        tv_regist.setOnClickListener(this);
        et_login = (EditText) findViewById(R.id.et_login);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            loginRequestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent homeintent = new Intent();
            homeintent.setClass(this, MainActivity2.class);
            startActivity(homeintent);
            return true;

        } else return false;
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
        } else if (id == android.R.id.home) {
            Intent homeintent = new Intent();
            homeintent.setClass(this, MainActivity2.class);
            startActivity(homeintent);
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                return;
            case R.id.tv_regist:
                Intent intent = new Intent();
                intent.setClass(this, RegistActivity.class);
                startActivity(intent);
                return;


        }
    }

    private void login() {

        final String loginname = et_login.getText().toString().trim();
        final String password = et_password.getText().toString().trim();
        user.setUsername(loginname);
        user.setPassword(password);

        loginRequestClient.UserLogin(user, new Callback<Object>() {
            @Override
            public void success(Object object, Response response) {
                Toast.makeText(UserLoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                SharedPreferences sp = getSharedPreferences("user_config", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("login", loginname);
                editor.putString("password", password);
                editor.putString("auth_token", JsonData.create(object).optString("auth_token"));
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(UserLoginActivity.this, MainActivity2.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                UserLoginActivity.this.finish();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("erroe", error.toString());
                et_login.clearFocus();
                et_password.clearFocus();
                et_login.setText("");
                et_password.setText("");
                et_login.setHint(R.string.UserName);
                et_password.setHint(R.string.UserPasswd);
                Toast.makeText(UserLoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
            }
        });
    }
}
