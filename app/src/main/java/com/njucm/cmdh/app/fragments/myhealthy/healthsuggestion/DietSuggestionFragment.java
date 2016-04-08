package com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.HealthSuggestionExpand;
import com.njucm.cmdh.app.activitys.HealthSuggestionExpand2;
import com.njucm.cmdh.app.domain.ConsitituteIdentifyResult;
import com.njucm.cmdh.app.domain.HealthKnowledgeJoinUserMapp;
import com.njucm.cmdh.app.domain.HealthSuggestions;
import com.njucm.cmdh.app.domain.MyCollectionDomain;
import com.njucm.cmdh.app.domain.Suggestioncollect;
import com.njucm.cmdh.app.domain.UserHealthknowage;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestClient;
import com.njucm.cmdh.app.utils.RequestJsonData;
import com.njucm.cmdh.app.utils.UserKnowledgeViewDBHelper;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class DietSuggestionFragment extends BaseFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2;
    MyApplication myApplication;
    private Context context;
    private PtrClassicFrameLayout mPtrFrame_Health;
    private ListViewDataAdapter<JsonData> dataListViewDataAdapter;
    private ListView listView;
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter_dietsuggestion;
    private Mycollection addMycollection;
    private ActionBar actionBar;
    private TextView praise;
    private ImageButton thumb_up;
    private List<Suggestioncollect> collectionid_list = new ArrayList<Suggestioncollect>();
    private int thiscollectionid;
    private int hidecode_2;
    private HealthSuggestions userHealthSuggestion;
    private List<UserHealthknowage> userHealthknowage_list;
    private String cons;
    private FontAwesomeText me;
    private FontAwesomeText another;
    private TextView meText;
    private TextView anotherText;
    private ProgressDialog dialog;
    private RequestClient requestClient;
    private ConsitituteIdentifyResult constituteidentifyresult;

    static {
        ARG_PARAM2 = "param2";
    }



    private OnFragmentInteractionListener mListener;
    private List<HealthKnowledgeJoinUserMapp> mydietsuggestion = new ArrayList<HealthKnowledgeJoinUserMapp>();

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private String helthsuggestionValue;
    private Map queryData;
    private HashMap map;
    int healthsuggestioncode = 0;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */

    // TODO: Rename and change types of parameters
    public static DietSuggestionFragment newInstance(String param1, String param2) {
        DietSuggestionFragment fragment = new DietSuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        mAdapter_dietsuggestion = new ListViewDataAdapter<JsonData>();
        mAdapter_dietsuggestion.setViewHolderClass(this, ViewHolder.class);
        listView.setAdapter(mAdapter_dietsuggestion);
        mPtrFrame_Health.setLastUpdateTimeRelateObject(this);
        mPtrFrame_Health.setResistance(1.7f);
        mPtrFrame_Health.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame_Health.setDurationToClose(200);
        mPtrFrame_Health.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame_Health.setPullToRefresh(true);
        // default is true
        mPtrFrame_Health.setKeepHeaderWhenRefresh(true);

        mPtrFrame_Health.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame_Health.autoRefresh();
            }
        }, 100);
        mPtrFrame_Health.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                dialog = ProgressDialog.show(getActivity(), "加载中", "请等待", true, false);
                dialog.show();
                map = new HashMap();
                /**
                 * 这里请求根据tbrecommendedconditionsmappcombine 来的,利用temp_healthsuggtypeid 和 temp_picturelocationid 确定
                 */
                new Thread(getConsThread).start();
                while(true){
                    if(helthsuggestionValue!=null){
                        map.put("temp_healthsuggestid__temp_healthsuggtypeid__healthsuggtypeid", "11");
                        String cons = helthsuggestionValue.substring(helthsuggestionValue.length()-3, helthsuggestionValue.length());
                        if(cons.equals("平和质")) {healthsuggestioncode = 1;}
                        else if(cons.equals("气虚质")) {healthsuggestioncode = 2;}
                        else if(cons.equals("湿热质")) {healthsuggestioncode = 3;}
                        else if(cons.equals("阳虚质")) {healthsuggestioncode = 4;}
                        else if(cons.equals("阴虚质")) {healthsuggestioncode = 5;}
                        else if(cons.equals("痰湿质")) {healthsuggestioncode = 6;}
                        else if(cons.equals("血瘀质")) {healthsuggestioncode = 7;}
                        else if(cons.equals("气郁质")) {healthsuggestioncode = 8;}
                        else if(cons.equals("特廪质")) {healthsuggestioncode = 9;}
                        map.put("temp_healthsuggestid__healthsuggestcode", healthsuggestioncode);
                        updateData(map);
                        break;
                    }else{
                        continue;
                    }
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        queryData = new HashMap();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               try {
                   Bundle bundle = new Bundle();
                   bundle.putString("flag", "DietSuggestionFragment");  //用來判斷從哪一個界面傳過去
                   bundle.putString("constitution", cons);
                   Intent intent = new Intent();
                   intent.putExtra("constitution", bundle);
                   intent.setClass(getActivity(), HealthSuggestionExpand2.class);
                   startActivity(intent);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
       });
        /**
         * 从网络获取用户是什么体质
         */
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getApplicationContext());

    }

    Runnable getConsThread = new Runnable() {
        @Override
        public void run() {
            List<ConsitituteIdentifyResult> consList = requestClient.getConsitituteIdentifyResultList("tbconstituteidentifyresult/", 2);
            constituteidentifyresult = consList.get(consList.size()-1);
            helthsuggestionValue = constituteidentifyresult.getConstituteidentifyresult();
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("helthsuggestionValue", helthsuggestionValue);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
        }
    };


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DietSuggestionFragment() {
    }

    private void getOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(getActivity());
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("健康建议");

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(MyApplication.myUrl+"/tbhealthsuggestions")
                .build();
        addMycollection = restAdapter.create(Mycollection.class);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();
        final View view = inflater.inflate(R.layout.fragment_custom, container, false);
        mImageLoader = ImageLoaderFactory.create(context);
        mPtrFrame_Health = (PtrClassicFrameLayout) view.findViewById(R.id.customlist_frame);
        listView = (ListView) view.findViewById(R.id.customlist);
        listView.setDividerHeight(0);
        me = (FontAwesomeText) view.findViewById(R.id.me);
        another = (FontAwesomeText) view.findViewById(R.id.another);
        meText = (TextView) view.findViewById(R.id.meText);
        anotherText = (TextView) view.findViewById(R.id.anotherText);


        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setTextColor(Color.rgb(102, 153, 0));
                meText.setTextColor(Color.rgb(102, 153, 0));
                anotherText.setTextColor(Color.rgb(142, 142, 142));
                another.setTextColor(Color.rgb(142, 142, 142));
            }
        });
        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                another.setTextColor(Color.rgb(102, 153, 0));
                anotherText.setTextColor(Color.rgb(102, 153, 0));
                meText.setTextColor(Color.rgb(142, 142, 142));
                me.setTextColor(Color.rgb(142, 142, 142));
                Intent intent = new Intent();
                intent.setClass(getActivity(), HealthSuggestionExpand.class);
                startActivity(intent);
            }
        });
        meText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setTextColor(Color.rgb(102, 153, 0));
                meText.setTextColor(Color.rgb(102, 153, 0));
                anotherText.setTextColor(Color.rgb(142, 142, 142));
                another.setTextColor(Color.rgb(142, 142, 142));
            }
        });
        anotherText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                another.setTextColor(Color.rgb(102, 153, 0));
                anotherText.setTextColor(Color.rgb(102, 153, 0));
                meText.setTextColor(Color.rgb(142, 142, 142));
                me.setTextColor(Color.rgb(142, 142, 142));
                Intent intent = new Intent();
                intent.setClass(getActivity(), HealthSuggestionExpand.class);
                startActivity(intent);
            }
        });
        return view;
    }

    protected void updateData(Map map) {
        try {
            RequestJsonData.getCustomListDataByJson(new RequestFinishHandler<JsonData>() {
                @Override
                public void onRequestFinish(final JsonData data) {
                    mPtrFrame_Health.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter_dietsuggestion.getDataList().clear();
                            mAdapter_dietsuggestion.getDataList().addAll(data.toArrayList());
                            mPtrFrame_Health.refreshComplete();
                            mAdapter_dietsuggestion.notifyDataSetChanged();
                        }
                    }, 0);
                }//&temp_healthsuggestlimitedid__suggestlimitedvalue="+helthsuggestionValue
            }, this.context, "/tbrecommendedconditionsmappcombine/", "request_init/healthknowledge/DietSuggestion.json", "dietsuggestion", map);
        } catch (Exception e) {
            Log.d("DietSuggestionFragment update error", e.getMessage());
        }
    }
///
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.extendsearch, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(R.id.action_search == item.getItemId()){
            Intent intent = new Intent();
            intent.setClass(getActivity(), HealthSuggestionExpand.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        me.setTextColor(Color.rgb(102, 153, 0));
        meText.setTextColor(Color.rgb(102, 153, 0));
        anotherText.setTextColor(Color.rgb(142, 142, 142));
        another.setTextColor(Color.rgb(142, 142, 142));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    interface Mycollection {
        //post方法用于提交，必须用实体类，设置回调函数
        @POST("/")
        void addcollection(@Body Suggestioncollect suggestioncollect, Callback<Suggestioncollect> cb);

        //delete方法用于删除，传入id参数，设置回调方法
        @DELETE("/{mycollectionid}/")
        void deletecollection(@Path("mycollectionid") int mycollectionid, Callback<MyCollectionDomain> cb);

        //get方法用于获取数据，设置查询的条件
        @GET("/")
        List<Suggestioncollect> getTheCollectionId(@Query("collectioncode") int collectioncode, @Query("temp_userid") int temp_userid, @Query("collectionclass") String collectionclass);

    }

    private class ViewHolder extends ViewHolderBase<JsonData> {

        private CubeImageView img;
        private TextView title, text, time;
        private View line;
        private FontAwesomeText imgbtn,imgbtn2;
        private TextView collect, hidecode;
        private LinearLayout praise_block, collect_block;
        Date date;
        private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        UserKnowledgeViewDBHelper helper = UserKnowledgeViewDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);

        @Override
        public View createView(LayoutInflater inflater) {
            final View view = inflater.inflate(R.layout.dietsuggestion_list_item, null);
            title = (TextView) view.findViewById(R.id.suggestiontitle);
            line = (View) view.findViewById(R.id.suggestline);
            text = (TextView) view.findViewById(R.id.suggestinfo);
            img = (CubeImageView) view.findViewById(R.id.yinshiimg);
            time = (TextView) view.findViewById(R.id.suggesttime);
            hidecode = (TextView) view.findViewById(R.id.hidecode);

            if(helper.getUserHealthknowageList("dietknowledge").size() != 0){
                boolean isCollected = helper.getItemCollectedStatus("dietknowledge");
                if(isCollected){
                    collect.setText("取消收藏");
                }else{
                    collect.setText("收藏");
                }
            }
            return view;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            //如果网络可用
            if (NetHelper.isHaveInternet(getApplicationContext())) {
                title.setText(itemData.optJson("temp_healthsuggestid").optJson("temp_picturelocationid").optString("picturename"));
                cons = itemData.optString("temp_healthsuggestlimitedid");
                text.setText(itemData.optJson("temp_healthsuggestid").optString("healthsuggestcontent").substring(0, 50)+"...");
                img.loadImage(mImageLoader, itemData.optJson("temp_healthsuggestid").optJson("temp_picturelocationid").optString("originalpicturepath"));
//                hidecode.setText(itemData.optJson("temp_healthsuggestid").optInt("healthsuggestcode"));
                dialog.dismiss();

            } else if (!NetHelper.isHaveInternet(getApplicationContext()) && helper.getUserHealthknowageList("dietknowledge") != null) {  //如果网络不可用,并且数据库有数据
                userHealthknowage_list = new ArrayList<>();
                userHealthknowage_list = helper.getUserHealthknowageListByType("dietknowledge");
                title.setText(userHealthknowage_list.get(0).getHealthknowledgetitle());
                text.setText(userHealthknowage_list.get(0).getHealthknowledgecontent());
                hidecode.setText(userHealthknowage_list.get(0).getHealthknowledgecode().toString());
                //时间还有一些问题
            } else {  //最后时网络不可用且数据库没数据，一般发生在app第一回使用
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
            }


        }

    }

}
