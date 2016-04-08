package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.njucm.cmdh.app.domain.ConsitituteIdentifyResult;
import com.njucm.cmdh.app.domain.IdentificationIssuess;
import com.njucm.cmdh.app.domain.RowObject;
import com.njucm.cmdh.app.domain.UserAnswerRecord;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.ConstituteIdentifyResultDBHelper;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.IdentificationIssuessDBHelper;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.srain.cube.request.JsonData;
import in.srain.cube.views.list.ListViewDataAdapter;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Mesogene on 3/31/15.修改IP
 */
public class AllIdentifyQuestion extends ActionBarActivity {
    MyApplication myApplication; //为了修改IP
    ActionBar actionBar;
    private int[] score = new int[66];
    int a;
    final int SINGLE_DIALOG = 0x113;
    int scoreTotal = 0;
    int max = 0;
    int scoreTotal1 = 0;
    int scoreTotal2 = 0;
    int scoreTotal3 = 0;
    int scoreTotal4 = 0;
    int scoreTotal5 = 0;
    int scoreTotal6 = 0;
    int scoreTotal7 = 0;
    int scoreTotal8 = 0;
    int scoreTotal9 = 0;
    int conscore1 = 0;
    int conscore2 = 0;
    int conscore3 = 0;
    int conscore4 = 0;
    int conscore5 = 0;
    int conscore6 = 0;
    int conscore7 = 0;
    int conscore8 = 0;
    int conscore9 = 0;
    private int listOfRemarks[]= new int [9];
    String result;
    int Type_Score;
    String title;
    private int[] choiceid = new int[66];
    public ProgressDialog myDialog = null;
    private ArrayList<RowObject> mSource;
    private ListView lv;
    private Context context;
    private ListViewDataAdapter<JsonData> mAdapter;
    private ConsIdentyFragment.OnFragmentInteractionListener mListener;
    public List<UserAnswerRecord> useranswerrecord = new ArrayList<UserAnswerRecord>();
    public List<IdentificationIssuess> identifyquestion_list;

    public List<ConsitituteIdentifyResult> consitituteIdentifresults_list;
    public List<ConsitituteIdentifyResult> consitituteidentifyresult = new ArrayList<ConsitituteIdentifyResult>();
    RequestClient requestClient;
    IdentificationIssuessDBHelper identifyquestionDBHlper;
    ConstituteIdentifyResultDBHelper constituteIdentifyResultDBHelper;
//    Runnable getData = new Runnable() {
//        @Override
//        public void run() {
//            try {
//
//                if(identifyquestionDBHlper.getTcmidentifyquestionList().size()==0){
//                    identifyquestion_list = requestClient.getTcmidentifyquestionList("tbidentificationissuess");
//                    handler.sendEmptyMessage(0);
//
//                }else if(identifyquestionDBHlper.getTcmidentifyquestionList().size()!=0){
//                    identifyquestion_list = identifyquestionDBHlper.getTcmidentifyquestionList();
//                    handler.sendEmptyMessage(1);
//
//                }else{
//                    Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
//                }
////                consitituteIdentifresults_list = requestClient.getConsitituteIdentifyResultList("tbconstituteidentifyresult");
////                handler.sendEmptyMessage(2);
//                Log.isLoggable("tbconditituteidentifyresult",consitituteIdentifresults_list.size());
//                Log.isLoggable("identifyquestion", identifyquestion_list.size());
//            } catch (Exception e) {
//                Log.d("MyConstitutionFragment", "我的体质请求出错" + e.getMessage());
//            }
//        }
//    };
    Runnable getData1 = new Runnable() {
    @Override
    public void run() {
        try {

                Gson gson = new Gson();
                InputStream inputStream = getApplicationContext().getResources().openRawResource(R.raw.identificationissuess);
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                String json = new String(buffer);

                identifyquestion_list = gson.fromJson(json, new TypeToken<List<IdentificationIssuess>>() {
                }.getType());

                // identifyquestion_list = identifyquestionissuess.subList(fromindex,toindex);
                Log.isLoggable("LLLLL", identifyquestion_list.size());
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
                case 0:
                    for(IdentificationIssuess identifyquestion:identifyquestion_list){
                        identifyquestionDBHlper.addIdentificationIssuess(identifyquestion);
                    }

                    lv.setAdapter(new MyListAdapter(context, mSource));
                    break;
                case 1:
                    lv.setAdapter(new MyListAdapter(context, mSource));
                    break;

//                case 2:
//                    for(ConsitituteIdentifyResult consitituteIdentifyResult:consitituteIdentifresults_list) {
//                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(consitituteIdentifyResult);
//                    }
//                    break;

            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinghezhi);
        context = this;
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
         /*
        * 为了修改IP
        * */
        myApplication = (MyApplication)getApplication();
        lv = (ListView) findViewById(android.R.id.list);
        /*
        /2015/09/20修改此处
         */
//        mAdapter =new ListViewDataAdapter<JsonData>();
//        mAdapter.setViewHolderClass(this,ViewHolder.class);
//        lv.setAdapter(mAdapter);
        identifyquestionDBHlper = IdentificationIssuessDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
        constituteIdentifyResultDBHelper = ConstituteIdentifyResultDBHelper.getInstance(getApplicationContext(),HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
        Button button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a == identifyquestion_list.size()) {
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
        for (int i = 0; i < 66; i++) {
            mSource.add(new RowObject(i, false, false, false, false, false));
        }
        //new Thread(getData).start();
        new Thread(getData1).start();
        lv.deferNotifyDataSetChanged();



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
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv1);
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

            viewHolder.title.setText(identifyquestion_list.get(position).getIdentifyissuecontent());


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
                            if (viewHolder.title.getText().toString().indexOf("*") != -1) {
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
                            if (viewHolder.title.getText().toString().indexOf("*") != -1) {
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
                            if (viewHolder.title.getText().toString().indexOf("*") != -1) {
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
                            if (viewHolder.title.getText().toString().indexOf("*") != -1) {
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
                            if (viewHolder.title.getText().toString().indexOf("*") != -1) {
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

                    for (a = 0; a < identifyquestion_list.size(); a++) {
                        if (score[a] == 0) break;
                    }
                    if (a == identifyquestion_list.size()) {
                        scoreTotal = 0;
                        scoreTotal1 = 0;
                        scoreTotal2 = 0;
                        scoreTotal3 = 0;
                        scoreTotal4 = 0;
                        scoreTotal5 = 0;
                        scoreTotal6 = 0;
                        scoreTotal7 = 0;
                        scoreTotal8 = 0;
                        scoreTotal9 = 0;
                        conscore1 = 0;
                        conscore2 = 0;
                        conscore3 = 0;
                        conscore4 = 0;
                        conscore5 = 0;
                        conscore6 = 0;
                        conscore7 = 0;
                        conscore8 = 0;
                        conscore9 = 0;
                        useranswerrecord.clear();
                        for (a = 0; a < identifyquestion_list.size(); a++) {

                            useranswerrecord.add(new UserAnswerRecord(identifyquestion_list.get(a).getIdentifyissueid(), choiceid[a], "张织", score[a], 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
                        }

                        for (a = 0; a < 8; a++) {
                            scoreTotal1 = scoreTotal1 + score[a];
                        }
                        conscore1 = 25 * (scoreTotal1 - 8) / 8;
                        listOfRemarks[0]=conscore1;
                        Log.d("平和质", "" + conscore1);
                        for (a = 8; a < 15; a++) {
                            scoreTotal2 = scoreTotal2 + score[a];
                        }
                        conscore2 = 25 * (scoreTotal2 - 7) / 7;
                        listOfRemarks[1]=conscore2;
                        Log.d("阳虚质", "" + conscore2);
                        for (a = 15; a < 23; a++) {
                            scoreTotal3 = scoreTotal3 + score[a];
                        }
                        conscore3 = 25 * (scoreTotal3 - 8) / 8;
                        listOfRemarks[2]=conscore3;
                        Log.d("阴虚质", "" + conscore3);
                        for (a = 23; a < 31; a++) {
                            scoreTotal4 = scoreTotal4 + score[a];
                        }
                        conscore4 = 25 * (scoreTotal4 - 8) / 8;
                        listOfRemarks[3]=conscore4;
                        Log.d("痰湿质", "" + conscore4);
                        for (a = 31; a < 37; a++) {
                            scoreTotal5 = scoreTotal5 + score[a];
                        }
                        conscore5 = 25 * (scoreTotal5 - 6) / 6;
                        listOfRemarks[4]=conscore5;
                        Log.d("湿热质", "" + conscore5);
                        for (a = 37; a < 44; a++) {
                            scoreTotal6 = scoreTotal6 + score[a];
                        }
                        conscore6 = 25 * (scoreTotal6 - 7) / 7;
                        listOfRemarks[5]=conscore6;
                        Log.d("气郁质", "" + conscore6);
                        for (a = 44; a < 52; a++) {
                            scoreTotal7 = scoreTotal7 + score[a];
                        }
                        conscore7 = 25 * (scoreTotal7 - 8) / 8;
                        listOfRemarks[6]=conscore7;
                        Log.d("气虚质", "" + conscore7);
                        for (a = 52; a < 59; a++) {
                            scoreTotal8 = scoreTotal8 + score[a];
                        }
                        conscore8 = 25 * (scoreTotal8 - 7) / 7;
                        listOfRemarks[7]=conscore8;
                        Log.d("血瘀质", "" + conscore8);
                        for (a = 59; a < 66; a++) {
                            scoreTotal9 = scoreTotal9 + score[a];
                        }
                        conscore9 = 25 * (scoreTotal9 - 7) / 7;
                        listOfRemarks[8]=conscore9;
                        Log.d("特廪质", "" + conscore9);

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
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("查看体质辨识结果请点击确定，修改选项请点击取消");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final CharSequence strDialogTitle = getString(R.string.str_dialog_title);
                        final CharSequence strDialogBody = getString(R.string.str_dialog_body);
                        myDialog = ProgressDialog.show(AllIdentifyQuestion.this, strDialogTitle, strDialogBody, true);
                        new Thread() {
                            public void run() {
                                Looper.prepare();
                                try {

                                    /*在这里写上要后台运行的代码段*/
                                    /*为了明显看见效果，以暂停4妙作为示范*/

                                    for(int i=1; i<9; i++){
                                        if(listOfRemarks[i]>max){
                                            max = listOfRemarks[i];

                                        }
                                    }
                                    sleep(4000);
                                    Intent intent = new Intent(AllIdentifyQuestion.this, AllDetectionResult.class);
                                    Bundle bundle = new Bundle();
                                    if (conscore1 >= 60 && conscore2 < 30 && conscore3 < 30 && conscore4 < 30 && conscore5 < 30 && conscore6 < 30 && conscore7 < 30 && conscore8 < 30 && conscore9 < 30) {
                                        result = "是";
                                        title = "平和质";
                                        Type_Score = conscore1;
                                        bundle.putInt("type1_score",Type_Score);
                                        bundle.putString("title0", title);
                                        bundle.putString("result0", result);

                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, 2));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, 2));






                                    } else if (conscore1 >= 60 && conscore2 < 40 && conscore2 >=30 && conscore3 < 40 && conscore3>=30 && conscore4 < 40 && conscore4 >=30 && conscore5 < 40 && conscore5>=30 && conscore6 < 40 && conscore6>=30 && conscore7 < 40 && conscore7 >=30 && conscore8 < 40 && conscore8>=30 && conscore9 < 40 && conscore9>=30) {
                                        result = "基本是";
                                        title = "平和质";
                                        Type_Score = conscore1;
                                        bundle.putInt("type1_score",Type_Score);
                                        bundle.putString("title0", title);
                                        bundle.putString("result0", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, 0));
                                    } else {
                                        result = "不是";
                                        title = "平和质";
                                        Type_Score = conscore1;
                                        bundle.putInt("type1_score",Type_Score);
                                        bundle.putString("title0", title);
                                        bundle.putString("result0", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore1), result + title, -1));
                                    }
                                    if (conscore2 >= 40) {
                                        result = "是";
                                        title = "阳虚质";
                                        Type_Score = conscore2;
                                        bundle.putInt("type2_score",Type_Score);
                                        bundle.putString("title1", title);
                                        bundle.putString("result1", result);
                                        if(conscore2==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 1));
                                        }

                                    } else if (conscore2 < 30) {
                                        result = "不是";
                                        title = "阳虚质";
                                        Type_Score = conscore2;
                                        bundle.putInt("type2_score",Type_Score);
                                        bundle.putString("title1", title);
                                        bundle.putString("result1", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, -1));
                                    } else {
                                        result = "倾向是";
                                        title = "阳虚质";
                                        Type_Score = conscore2;
                                        bundle.putInt("type2_score",Type_Score);
                                        bundle.putString("title1", title);
                                        bundle.putString("result1", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 2, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore2), result + title, 0));
                                    }
                                    if (conscore3 >= 40) {
                                        result = "是";
                                        title = "阴虚质";
                                        Type_Score = conscore3;
                                        bundle.putInt("type3_score",Type_Score);
                                        bundle.putString("title2", title);
                                        bundle.putString("result2", result);
                                        if(conscore3==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 1));
                                        }

                                    } else if (conscore3 < 30) {
                                        result = "不是";
                                        title = "阴虚质";
                                        Type_Score = conscore3;
                                        bundle.putInt("type3_score",Type_Score);
                                        bundle.putString("title2", title);
                                        bundle.putString("result2", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, -1));
                                    } else {
                                        result = "倾向是";
                                        title = "阴虚质";
                                        Type_Score = conscore3;
                                        bundle.putInt("type3_score",Type_Score);
                                        bundle.putString("title2", title);
                                        bundle.putString("result2", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 3, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore3), result + title, 0));
                                    }
                                    if (conscore4 >= 40) {
                                        result = "是";
                                        title = "痰湿质";
                                        Type_Score = conscore4;
                                        bundle.putInt("type4_score",Type_Score);
                                        bundle.putString("title3", title);
                                        bundle.putString("result3", result);
                                        if(conscore5==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 1));
                                        }

                                    } else if (conscore4 < 30) {
                                        result = "不是";
                                        title = "痰湿质";
                                        Type_Score = conscore4;
                                        bundle.putInt("type4_score",Type_Score);
                                        bundle.putString("title3", title);
                                        bundle.putString("result3", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, -1));
                                    } else {
                                        result = "倾向是";
                                        title = "痰湿质";
                                        Type_Score = conscore4;
                                        bundle.putInt("type4_score",Type_Score);
                                        bundle.putString("title3", title);
                                        bundle.putString("result3", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 4, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore4), result + title, 0));
                                    }
                                    if (conscore5 >= 40) {
                                        result = "是";
                                        title = "湿热质";
                                        Type_Score = conscore5;
                                        bundle.putInt("type5_score",Type_Score);
                                        bundle.putString("title4", title);
                                        bundle.putString("result4", result);
                                        if(conscore5==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, 1));
                                        }

                                    } else if (conscore5 < 30) {
                                        result = "不是";
                                        title = "湿热质";
                                        Type_Score = conscore5;
                                        bundle.putInt("type5_score",Type_Score);
                                        bundle.putString("title4", title);
                                        bundle.putString("result4", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore5), result + title, -1));
                                    } else {
                                        result = "倾向是";
                                        title = "湿热质";
                                        Type_Score = conscore5;
                                        bundle.putInt("type5_score",Type_Score);
                                        bundle.putString("title4", title);
                                        bundle.putString("result4", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore5), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore5), result + title, 0));

                                    }
                                    if (conscore6 >= 40) {
                                        result = "是";
                                        title = "气郁质";
                                        Type_Score = conscore6;
                                        bundle.putInt("type6_score",Type_Score);
                                        bundle.putString("title5", title);
                                        bundle.putString("result5", result);
                                        if(conscore6==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore6), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore6), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore6), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore6), result + title, 1));

                                        }

                                    } else if (conscore6 < 30) {
                                        result = "不是";
                                        title = "气郁质";
                                        Type_Score = conscore6;
                                        bundle.putInt("type6_score",Type_Score);
                                        bundle.putString("title5", title);
                                        bundle.putString("result5", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore6), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore6), result + title, -1));

                                    } else {
                                        result = "倾向是";
                                        title = "气郁质";

                                        Type_Score = conscore6;
                                        bundle.putInt("type6_score",Type_Score);
                                        bundle.putString("title5", title);
                                        bundle.putString("result5", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore6), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore6), result + title, 0));

                                    }
                                    if (conscore7 >= 40) {
                                        result = "是";
                                        title = "气虚质";
                                        Type_Score = conscore7;
                                        bundle.putInt("type7_score",Type_Score);
                                        bundle.putString("title6", title);
                                        bundle.putString("result6", result);
                                        if(conscore7==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore7), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore7), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore7), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore7), result + title, 1));

                                        }

                                    } else if (conscore7 < 30) {
                                        result = "不是";
                                        title = "气虚质";
                                        Type_Score = conscore7;
                                        bundle.putInt("type7_score",Type_Score);
                                        bundle.putString("title6", title);
                                        bundle.putString("result6", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore7), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore7), result + title, -1));

                                    } else {
                                        result = "倾向是";
                                        title = "气虚质";
                                        Type_Score = conscore7;
                                        bundle.putInt("type7_score",Type_Score);
                                        bundle.putString("title6", title);
                                        bundle.putString("result6", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore7), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore7), result + title, 0));

                                    }
                                    if (conscore8 >= 40) {
                                        result = "是";
                                        title = "血瘀质";
                                        Type_Score = conscore8;
                                        bundle.putInt("type8_score",Type_Score);
                                        bundle.putString("title7", title);
                                        bundle.putString("result7", result);
                                        if(conscore8==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore8), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore8), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore8), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore8), result + title, 1));

                                        }

                                    } else if (conscore8 < 30) {
                                        result = "不是";
                                        title = "血瘀质";
                                        Type_Score = conscore8;
                                        bundle.putInt("type8_score",Type_Score);
                                        bundle.putString("title7", title);
                                        bundle.putString("result7", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore8), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore8), result + title, -1));

                                    } else {
                                        result = "倾向是";
                                        title = "血瘀质";
                                        Type_Score = conscore8;
                                        bundle.putInt("type8_score",Type_Score);
                                        bundle.putString("title7", title);
                                        bundle.putString("result7", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore8), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 8, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore8), result + title, 0));

                                    }
                                    if (conscore9 >= 40) {
                                        result = "是";
                                        title = "特廪质";
                                        Type_Score = conscore9;
                                        bundle.putInt("type9_score",Type_Score);
                                        bundle.putString("title8", title);
                                        bundle.putString("result8", result);
                                        if(conscore9==max){
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore9), result + title, 2));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore9), result + title, 2));
                                        }else{
                                            consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore9), result + title, 1));
                                            constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore9), result + title, 1));

                                        }

                                    } else if (conscore9 < 30) {
                                        result = "不是";
                                        title = "特廪质";
                                        Type_Score = conscore9;
                                        bundle.putInt("type9_score",Type_Score);
                                        bundle.putString("title8", title);
                                        bundle.putString("result8", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore9), result + title, -1));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),String.valueOf(conscore9), result + title, -1));
                                    } else {
                                        result = "倾向是";
                                        title = "特廪质";
                                        Type_Score = conscore9;
                                        bundle.putInt("type9_score",Type_Score);
                                        bundle.putString("title8", title);
                                        bundle.putString("result8", result);
                                        consitituteidentifyresult.add(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore9), result + title, 0));
                                        constituteIdentifyResultDBHelper.addConsitituteIdentifyResult(new ConsitituteIdentifyResult("张织", 9, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), String.valueOf(conscore9), result + title, 0));

                                    }
                                    if(NetHelper.isHaveInternet(getApplicationContext())) {
                                        requestClient.getUserAnswerRecordList(useranswerrecord, new Callback<Object>() {
                                            @Override
                                            public void success(Object o, Response response) {
                                                Log.d("success", "插入成功");

                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.d("fail", "插入失败");
                                                showMessage("上传失败");


                                            }
                                        });
                                        requestClient.getConstituteIdentifyResultList(consitituteidentifyresult, new Callback<Object>() {
                                            @Override
                                            public void success(Object o, Response response) {
                                                Log.d("结果记录", "插入成功");

                                            }

                                            @Override
                                            public void failure(RetrofitError error) {
                                                Log.d("结果记录", "插入失败");
                                                showMessage("无网络结果记录已存入本地");

                                            }
                                        });
                                    }else{
                                        Toast.makeText(getApplicationContext(),"请检查网络！",Toast.LENGTH_SHORT);
                                    }

                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    AllIdentifyQuestion.this.finish();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    //卸载所创建的myDialog对象
                                    myDialog.dismiss();

                                }
                                Looper.loop();
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

        TextView title;
        RadioGroup radioGroup;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;
        RadioButton radioButton4;
        RadioButton radioButton5;
        RadioButton radioButton6;

    }
}
