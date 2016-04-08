package com.njucm.cmdh.app.activitys.user;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
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

public class RegistActivity extends ActionBarActivity implements View.OnClickListener {

    MyApplication myApplication;
    RequestClient registerRequestClient;
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    RelativeLayout rl_registerSuccess;
    RelativeLayout rl_registerModel;
    BootstrapButton bbtn_loginNow;
    Button btn_UserRegister;
    EditText edt_userName;
    EditText edt_userPasswd;
    EditText edt_userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        setTitle(getString(R.string.registertitle));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        btn_UserRegister = (Button) findViewById(R.id.register_btnok);
        edt_userEmail = (EditText) findViewById(R.id.edt_email);
        edt_userName = (EditText) findViewById(R.id.edt_login);
        edt_userPasswd = (EditText) findViewById(R.id.edt_pass);
        rl_registerSuccess = (RelativeLayout) findViewById(R.id.rl_register_success);
        rl_registerModel = (RelativeLayout) findViewById(R.id.rl_register_model);
        bbtn_loginNow = (BootstrapButton) findViewById(R.id.bbtn_login_now);
        bbtn_loginNow.setOnClickListener(this);
        btn_UserRegister.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_regist, menu);
        return true;
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
            this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.register_btnok:
                try {
                    myApplication = (MyApplication) getApplication();
                    User registerUser = new User();
                    registerUser.setUsername(edt_userName.getText().toString().trim());
                    registerUser.setEmail(edt_userEmail.getText().toString().trim());
                    registerUser.setPassword(edt_userPasswd.getText().toString().trim());
                    registerRequestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, this);
                    registerRequestClient.UserRegister(registerUser, new Callback<Object>() {
                        @Override
                        public void success(Object o, Response response) {
                            rl_registerModel.setVisibility(RelativeLayout.GONE);
                            rl_registerSuccess.setVisibility(RelativeLayout.VISIBLE);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("username", "error");

                        }
                    });
                } catch (Exception e) {
                    edt_userName.clearFocus();
                    edt_userPasswd.clearFocus();
                    edt_userEmail.clearFocus();
                    edt_userEmail.setText("");
                    edt_userName.setText("");
                    edt_userPasswd.setText("");
                    edt_userName.setHint(R.string.register_username);
                    edt_userPasswd.setHint(R.string.register_password);
                    edt_userEmail.setHint(R.string.register_email);
                    Toast.makeText(this,"注册失败！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bbtn_login_now:
                Intent intent = new Intent();
                intent.setClass(this, UserLoginActivity.class);
                startActivity(intent);
        }

    }
}
