package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.IdentificationIssuess;
import com.njucm.cmdh.app.domain.RowObject;
import com.njucm.cmdh.app.domain.UserAnswerRecord;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.IdentificationIssuessDBHelper;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PhysiqueTest extends ActionBarActivity {
    MyApplication myApplication; //为了修改IP
    final int SINGLE_DIALOG = 0x113;
    int tizhi;
    String title;
    String result;
    int number,fromindex,toindex;

    private int[] score = new int[10];
    private int[] choiceid = new int[10];
    int a;
    int scoreTotal = 0;
    int conscore = 0;
    private ArrayList<RowObject> mSource;
    private ListView lv;
    ActionBar actionBar;
    public Button button;
    public ProgressDialog myDialog = null;
    private List<IdentificationIssuess> identifyquestionissuess = new ArrayList<IdentificationIssuess>();
    private List<IdentificationIssuess> identifyquestion_list;

    public List<UserAnswerRecord> useranswerrecord = new ArrayList<UserAnswerRecord>();

    RequestClient requestClient;
    IdentificationIssuessDBHelper identifyquestionDBHlper;
    private Context context;
//
//    Runnable getData = new Runnable() {
//        @Override
//        public void run() {
//            try{
//                if(identifyquestionDBHlper.getTcmidentifyquestionList().size()==0) {
//                    identifyquestion_list = requestClient.getTcmidentifyquestionList("tbidentificationissuess",String.valueOf(tizhi));
//
//                }else if(identifyquestionDBHlper.getTcmidentifyquestionList().size()!=0){
//                    identifyquestion_list = identifyquestionDBHlper.getTcmidentifyquestion(tizhi);
//
//                }else{//網絡不可用
//                    Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
//                }
//                handler.sendEmptyMessage(0);
//
//            }catch (Exception e){
//                Log.d("PhysiqueTest", "体质信息请求出错" + e.getMessage());
//
//            }
//        }
//    };
    Runnable getData1 = new Runnable() {
        @Override
        public void run() {
            try{

                    Gson gson = new Gson();
                    InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.identificationissuess);
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    String json = new String(buffer);

                    identifyquestionissuess = gson.fromJson(json, new TypeToken<List<IdentificationIssuess>>(){}.getType());

                    identifyquestion_list = identifyquestionissuess.subList(fromindex,toindex);
                    Log.isLoggable("LLLLL",identifyquestion_list.size());
                    handler.sendEmptyMessage(0);

            }catch (Exception e){
                Log.d("PhysiqueTest", "体质信息请求出错" + e.getMessage());

            }
        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    lv.setAdapter(new MyListAdapter(context, mSource));
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinghezhi);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        context = this;
           /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplication();
        Bundle bundle = this.getIntent().getExtras();
        tizhi = bundle.getInt("type");
        title = bundle.getString("title");
        actionBar.setTitle(String.valueOf(title));
        lv = (ListView) findViewById(android.R.id.list);
       identifyquestionDBHlper = IdentificationIssuessDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
        if (tizhi == 1) {

            number = 8;
            fromindex = 0;
            toindex = 8;

        } else if (tizhi == 2) {

            number = 7;
            fromindex = 8;
            toindex = 15;

        } else if (tizhi == 3) {

            number = 8;
            fromindex = 15;
            toindex = 23;

        } else if (tizhi == 4) {
            fromindex = 23;
            number = 8;
            toindex = 31;
        } else if (tizhi == 5) {
            fromindex =31;
            number = 6;
            toindex = 37;
        } else if (tizhi == 6) {
            fromindex = 37;
            number = 7;
            toindex = 44;
        } else if (tizhi == 7) {
            fromindex =44;
            number = 8;
            toindex = 52;
        } else if (tizhi == 8) {
            fromindex =52;
            number = 7;
            toindex = 59;
        } else {
            fromindex = 59;
            number = 7;
            toindex =66;
        }
        button = (Button) findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (a == number) {
                    showDialog(SINGLE_DIALOG);
                } else {
                    showMessage("还有选项未选择答案");
                }
            }

        });
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }

        mSource = new ArrayList<RowObject>();

        for (int i = 0; i < number; i++) {

            mSource.add(new RowObject(i, false, false, false, false, false));

        }

        lv.deferNotifyDataSetChanged();
      //  new Thread(getData).start();
        new Thread(getData1).start();
    }


    public void showMessage(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 220);
        toast.show();
    }

    class MyListAdapter extends ArrayAdapter<RowObject> {

        private LayoutInflater mInflater;

        public MyListAdapter(Context context, ArrayList<RowObject> mSource) {
            super(context, R.layout.list, mSource);
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return mSource.size();
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder viewHolder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list, null);

                viewHolder = new ViewHolder();
                viewHolder.title1 = (TextView) convertView.findViewById(R.id.tv1);
                viewHolder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radiogroup);
                viewHolder.radioButton1 = (RadioButton) convertView.findViewById(R.id.radio1);
                viewHolder.radioButton2 = (RadioButton) convertView.findViewById(R.id.radio2);
                viewHolder.radioButton3 = (RadioButton) convertView.findViewById(R.id.radio3);
                viewHolder.radioButton4 = (RadioButton) convertView.findViewById(R.id.radio4);
                viewHolder.radioButton5 = (RadioButton) convertView.findViewById(R.id.radio5);
                viewHolder.radioButton6 = (RadioButton) convertView.findViewById(R.id.radio6);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //viewHolder.title1.setText(ListStr[position+location]);
            viewHolder.title1.setText(identifyquestion_list.get(position).getIdentifyissuecontent());

            viewHolder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    switch (checkedId) {
                        case R.id.radio1: {
                            mSource.get(position).setFirstChecked(true);
                            mSource.get(position).setSecondChecked(false);
                            mSource.get(position).setThreeChecked(false);
                            mSource.get(position).setFourChecked(false);
                            mSource.get(position).setFiveChecked(false);
                            if (viewHolder.title1.getText().toString().contains("*")) {
                                score[position] = 5;

                            } else {
                                score[position] = 1;

                            }
                            choiceid[position] = 1;

                            break;
                        }
                        case R.id.radio2: {
                            mSource.get(position).setFirstChecked(false);
                            mSource.get(position).setSecondChecked(true);
                            mSource.get(position).setThreeChecked(false);
                            mSource.get(position).setFourChecked(false);
                            mSource.get(position).setFiveChecked(false);
                            if (viewHolder.title1.getText().toString().contains("*")) {
                                score[position] = 4;

                            } else {
                                score[position] = 2;

                            }
                            choiceid[position] = 2;
                            break;
                        }
                        case R.id.radio3: {
                            mSource.get(position).setFirstChecked(false);
                            mSource.get(position).setSecondChecked(false);
                            mSource.get(position).setThreeChecked(true);
                            mSource.get(position).setFourChecked(false);
                            mSource.get(position).setFiveChecked(false);
                            if (viewHolder.title1.getText().toString().contains("*")) {
                                score[position] = 3;

                            } else {
                                score[position] = 3;

                            }
                            choiceid[position] = 3;
                            break;
                        }
                        case R.id.radio4: {
                            mSource.get(position).setFirstChecked(false);
                            mSource.get(position).setSecondChecked(false);
                            mSource.get(position).setThreeChecked(false);
                            mSource.get(position).setFourChecked(true);
                            mSource.get(position).setFiveChecked(false);
                            if (viewHolder.title1.getText().toString().contains("*")) {
                                score[position] = 2;

                            } else {
                                score[position] = 4;

                            }
                            choiceid[position] = 4;
                            break;
                        }
                        case R.id.radio5: {
                            mSource.get(position).setFirstChecked(false);
                            mSource.get(position).setSecondChecked(false);
                            mSource.get(position).setThreeChecked(false);
                            mSource.get(position).setFourChecked(false);
                            mSource.get(position).setFiveChecked(true);
                            if (viewHolder.title1.getText().toString().contains("*")) {
                                score[position] = 1;

                            } else {
                                score[position] = 5;

                            }
                            choiceid[position] = 5;
                            break;
                        }
                        case R.id.radio6: {
                            mSource.get(position).setFirstChecked(false);
                            mSource.get(position).setSecondChecked(false);
                            mSource.get(position).setThreeChecked(false);
                            mSource.get(position).setFourChecked(false);
                            mSource.get(position).setFiveChecked(false);
                            score[position] = 0;

                            choiceid[position] = 6;
                            break;
                        }

                    }

                    for (a = 0; a < number; a++) {
                        if (score[a] == 0) break;
                    }
                    if (a == number) {
                        scoreTotal = 0;

                        useranswerrecord.clear();
                        for (a = 0; a < number; a++) {
                            scoreTotal = scoreTotal + score[a];
                          //  useranswerrecord.add(new UserAnswerRecord(identifyquestion.get(a).getIdentifyissueid(), choiceid[a], "张传清", score[a], 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));

                        }
                        conscore = 25 * (scoreTotal - number) / number;
                        showMessage("" + conscore);
                    }
                }
            });
            if (mSource.get(position).isFirstChecked()) {
                viewHolder.radioButton1.setChecked(true);
                viewHolder.radioButton2.setChecked(false);
                viewHolder.radioButton3.setChecked(false);
                viewHolder.radioButton4.setChecked(false);
                viewHolder.radioButton5.setChecked(false);
                viewHolder.radioButton6.setChecked(false);
            } else if (mSource.get(position).isSecondChecked()) {
                viewHolder.radioButton1.setChecked(false);
                viewHolder.radioButton2.setChecked(true);
                viewHolder.radioButton3.setChecked(false);
                viewHolder.radioButton4.setChecked(false);
                viewHolder.radioButton5.setChecked(false);
                viewHolder.radioButton6.setChecked(false);
            } else if (mSource.get(position).isThreeChecked()) {
                viewHolder.radioButton1.setChecked(false);
                viewHolder.radioButton2.setChecked(false);
                viewHolder.radioButton3.setChecked(true);
                viewHolder.radioButton4.setChecked(false);
                viewHolder.radioButton5.setChecked(false);
            } else if (mSource.get(position).isFourChecked()) {
                viewHolder.radioButton1.setChecked(false);
                viewHolder.radioButton2.setChecked(false);
                viewHolder.radioButton3.setChecked(false);
                viewHolder.radioButton4.setChecked(true);
                viewHolder.radioButton5.setChecked(false);
                viewHolder.radioButton6.setChecked(false);
            } else if (mSource.get(position).isFiveChecked()) {
                viewHolder.radioButton1.setChecked(false);
                viewHolder.radioButton2.setChecked(false);
                viewHolder.radioButton3.setChecked(false);
                viewHolder.radioButton4.setChecked(false);
                viewHolder.radioButton5.setChecked(true);
                viewHolder.radioButton6.setChecked(false);
            } else {
                viewHolder.radioButton1.setChecked(false);
                viewHolder.radioButton2.setChecked(false);
                viewHolder.radioButton3.setChecked(false);
                viewHolder.radioButton4.setChecked(false);
                viewHolder.radioButton5.setChecked(false);
                viewHolder.radioButton6.setChecked(true);
            }
            return convertView;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pinghezhi, menu);

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
        }
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public Dialog onCreateDialog(int id, Bundle state) {
        switch (id) {
            case SINGLE_DIALOG:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("查看体质辨识结果请点击确定，修改选项请点击取消");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final CharSequence strDialogTitle = getString(R.string.str_dialog_title);
                        final CharSequence strDialogBody = getString(R.string.str_dialog_body);
                        myDialog = ProgressDialog.show(PhysiqueTest.this, strDialogTitle, strDialogBody, true);
                        new Thread() {
                            public void run() {
                                try {
                                    /*在这里写上要后台运行的代码段*/
                                    /*为了明显看见效果，以暂停4妙作为示范*/
                                    final String result_contitution;
                                    Intent intent = new Intent(PhysiqueTest.this, DetectionResult.class);
                                    String actionBarTitle = actionBar.getTitle().toString().trim();
                                    if (actionBarTitle.equals("平和质")) {
                                        //
                                        if (conscore >= 60) {
                                            result_contitution = "是";
                                            Bundle bundle = new Bundle();
                                            title = actionBar.getTitle().toString().trim();

                                            result = "您平和质测试分合格，测试其他偏颇体质可以确定您体质类型";
                                            bundle.putString("result", result);
                                            bundle.putString("title", title);
                                            bundle.putInt("tizhi",tizhi);

                                            intent.putExtras(bundle);
                                        } else {
                                            Bundle bundle = new Bundle();
                                            title = actionBar.getTitle().toString().trim();
                                            result = "不是";
                                            bundle.putString("result", result);
                                            bundle.putString("title", title);
                                            bundle.putInt("tizhi",tizhi);

                                            intent.putExtras(bundle);
                                        }

                                    } else {
                                        if (conscore >= 40) {
                                            Bundle bundle = new Bundle();
                                            title = actionBar.getTitle().toString().trim();
                                            result = "是";
                                            bundle.putString("result", result);
                                            bundle.putString("title", title);
                                            bundle.putInt("tizhi",tizhi);

                                            intent.putExtras(bundle);
                                        } else if (scoreTotal < 30) {
                                            Bundle bundle = new Bundle();
                                            title = actionBar.getTitle().toString().trim();
                                            result = "不是";
                                            bundle.putString("result", result);
                                            bundle.putString("title", title);
                                            bundle.putInt("tizhi",tizhi);

                                            intent.putExtras(bundle);
                                        } else {
                                            Bundle bundle = new Bundle();
                                            title = actionBar.getTitle().toString().trim();
                                            result = "倾向是";
                                            bundle.putString("result", result);
                                            bundle.putString("title", title);
                                            bundle.putInt("tizhi",tizhi);

                                            intent.putExtras(bundle);
                                        }

                                    }

                                    sleep(4000);

                                    startActivity(intent);
                                    PhysiqueTest.this.finish();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    //卸载所创建的myDialog对象
                                    myDialog.dismiss();

                                }
                            }
                        }.start();/*开始运行线程*/

                    }/*END：public void onClick()*/
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

    class ViewHolder {
        TextView title1;
        RadioGroup radioGroup;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;
        RadioButton radioButton6;
    }
}
