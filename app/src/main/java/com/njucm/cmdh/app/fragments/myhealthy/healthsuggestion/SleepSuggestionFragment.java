package com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.MyCollectionDomain;
import com.njucm.cmdh.app.domain.Suggestioncollect;
import com.njucm.cmdh.app.domain.UserHealthknowage;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestJsonData;
import com.njucm.cmdh.app.utils.UserKnowledgeViewDBHelper;

import java.util.ArrayList;
import java.util.List;

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
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SleepSuggestionFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MyApplication myApplication;
    private Context context;
    private PtrClassicFrameLayout mPtrFrame_Health;
    private ListViewDataAdapter<JsonData> dataListViewDataAdapter;
    private ListView listView;
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter_SleepSuggestion;
    private int hidecode_3;
    private int thiscollectionid;

    private OnFragmentInteractionListener mListener;
    ActionBar actionBar;
    DietSuggestionFragment.Mycollection collection_sleep;
    private List<Suggestioncollect> collectionid_list = new ArrayList<Suggestioncollect>();
    private UserHealthknowage userHealthknowage;
    private List<UserHealthknowage> userHealthknowage_list;


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    // TODO: Rename and change types of parameters
    public static SleepSuggestionFragment newInstance(String param1, String param2) {
        SleepSuggestionFragment fragment = new SleepSuggestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter_SleepSuggestion = new ListViewDataAdapter<JsonData>();
        mAdapter_SleepSuggestion.setViewHolderClass(this, ViewHolder.class);
        listView.setAdapter(mAdapter_SleepSuggestion);
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
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    protected void updateData(){
        RequestJsonData.getCustomListData(new RequestFinishHandler<JsonData>() {
            @Override
            public void onRequestFinish(final JsonData data) {
                mPtrFrame_Health.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter_SleepSuggestion.getDataList().clear();
                        mAdapter_SleepSuggestion.getDataList().addAll(data.toArrayList());
                        mPtrFrame_Health.refreshComplete();
                        mAdapter_SleepSuggestion.notifyDataSetChanged();
                    }
                }, 0);
            }
        }, this.context, "/userhealthknowledgeview/sleepknowledge/", "request_init/healthknowledge/SleepSuggestion.json", "sleepsuggestion");
    }


    public SleepSuggestionFragment() {
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
        collection_sleep = restAdapter.create(DietSuggestionFragment.Mycollection.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = getActivity().getApplicationContext();

        View view = inflater.inflate(R.layout.fragment_custom, container, false);

        mImageLoader = ImageLoaderFactory.create(context);
        listView = (ListView)view.findViewById(R.id.customlist);
        mPtrFrame_Health = (PtrClassicFrameLayout) view.findViewById(R.id.customlist_frame);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setDividerHeight(0);
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }



    Runnable getid_list_sleep = new Runnable() {
        @Override
        public void run() {
            collectionid_list = collection_sleep.getTheCollectionId(hidecode_3, 1, "3");
            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("1", 1);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            thiscollectionid = collectionid_list.get(0).getMycollectionid();
            collection_sleep.deletecollection(thiscollectionid, new retrofit.Callback<MyCollectionDomain>() {
                @Override
                public void success(MyCollectionDomain myCollectionDomain, Response response) {
                    Toast.makeText(getApplicationContext(), "取消收藏成功！", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(getApplicationContext(), "取消收藏失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

    private class ViewHolder extends ViewHolderBase<JsonData> {

        private CubeImageView img;
        private TextView title, text, time;
        private View line;
        private ImageButton imgbtn , imgbtn2;
        private TextView shoucang, hidecode;
        UserKnowledgeViewDBHelper helper = UserKnowledgeViewDBHelper.getInstance(getApplicationContext(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);

        @Override
        public View createView(LayoutInflater inflater) {
            final View view = inflater.inflate(R.layout.sleepsuggestion_list_item, null);
            title = (TextView) view.findViewById(R.id.suggestiontitle_sleep);
            line = (View)view.findViewById(R.id.suggestline_sleep);
            text = (TextView)view.findViewById(R.id.suggestinfo_sleep);
            img = (CubeImageView)view.findViewById(R.id.yinshiimg_sleep);
            time = (TextView)view.findViewById(R.id.suggesttime_sleep);
            imgbtn = (ImageButton)view.findViewById(R.id.thumb_up_sleep);
            imgbtn2 = (ImageButton)view.findViewById(R.id.heart_sleep);
            shoucang = (TextView)view.findViewById(R.id.shoucang_sleep);
            hidecode = (TextView)view.findViewById(R.id.hidecode_sleep);
            imgbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView name = (TextView)view.findViewById(R.id.suggestinfo);
                    Toast.makeText(getApplicationContext(), "" + name.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            imgbtn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        hidecode_3 = Integer.parseInt(hidecode.getText().toString());
                    } catch (NumberFormatException e) {
                        Log.d("SleepSuggestionDetailFragment", e.getMessage());
                    }
                    if(shoucang.getText().equals("收藏")){
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            collection_sleep.addcollection(new Suggestioncollect("3", hidecode_3, 1), new Callback<Suggestioncollect>() {
                                @Override
                                public void success(Suggestioncollect suggestioncollect, Response response) {
                                    Toast.makeText(getApplicationContext(), "收藏成功！", Toast.LENGTH_SHORT).show();
                                    shoucang.setText("取消收藏");
                                }
                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(getApplicationContext(), "收藏失败！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(getApplicationContext(), "请先检查网络", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        new Thread(getid_list_sleep).start();
                        shoucang.setText("收藏");
                    }
                }
            });
            return view;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            if(NetHelper.isHaveInternet(getApplicationContext())){
                title.setText(itemData.optString("healthknowledgetitle"));
                text.setText(itemData.optString("healthknowledgecontent"));
                img.loadImage(mImageLoader, itemData.optString("temp_picturelistid"));
                time.setText(itemData.optString("healthknowledgetime"));
                hidecode.setText(itemData.optString("healthknowledgecode"));

                userHealthknowage = new UserHealthknowage();
                userHealthknowage.setHealthknowledgetitle(itemData.optString("healthknowledgetitle"));
                userHealthknowage.setHealthknowledgecontent(itemData.optString("healthknowledgecontent"));
                userHealthknowage.setHealthknowledgecode(Integer.parseInt(itemData.optString("healthknowledgecode")));
                userHealthknowage.setHealthknowltypename(itemData.optString("healthknowltypename"));
//                try {
//                    userHealthknowage.setHealthknowledgetime(formatter.parse(itemData.optString("healthknowledgetime")));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                helper.deleteUserHealthknowageByType("sleepknowledge");  //清空全表
                helper.addUserHealthknowage(userHealthknowage);
            }else if(!NetHelper.isHaveInternet(getApplicationContext() )&& helper.getUserHealthknowageList("sleepknowledge")!=null){
                userHealthknowage_list = new ArrayList<>();
                userHealthknowage_list = helper.getUserHealthknowageListByType("sleepknowledge");
                title.setText(userHealthknowage_list.get(0).getHealthknowledgetitle());
                text.setText(userHealthknowage_list.get(0).getHealthknowledgecontent());
                hidecode.setText(userHealthknowage_list.get(0).getHealthknowledgecode().toString());
                //时间还有一些问题

            }else{
                Toast.makeText(getApplicationContext(), "请检查网络", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

        }
    }


}
