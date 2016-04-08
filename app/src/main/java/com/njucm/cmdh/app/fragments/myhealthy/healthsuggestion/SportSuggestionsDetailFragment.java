package com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.CustomException.ExceptionNetwork;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.HealthKnowledgeJoinUserMapp;
import com.njucm.cmdh.app.domain.HealthSuggestions;
import com.njucm.cmdh.app.domain.MyCollectionDomain;
import com.njucm.cmdh.app.domain.Suggestioncollect;
import com.njucm.cmdh.app.domain.UserHealthknowage;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.NetHelper;
import com.njucm.cmdh.app.utils.RequestJsonData;
import com.njucm.cmdh.app.utils.UserKnowledgeViewDBHelper;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import retrofit.converter.GsonConverter;
import retrofit.http.GET;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SportSuggestionsDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SportSuggestionsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SportSuggestionsDetailFragment extends BaseFragment {
    MyApplication myApplication;
    private PtrClassicFrameLayout mPtrFrame_Health;
    private ListViewDataAdapter<JsonData> dataListViewDataAdapter;
    private ListView listView;
    private ImageLoader mImageLoader;
    private ListViewDataAdapter<JsonData> mAdapter_SportSuggestion;
    ActionBar actionBar;

    private OnFragmentInteractionListener mListener;
    private List<Suggestioncollect> collectionid_list = new ArrayList<Suggestioncollect>();
    private int thiscollectionid;
    private int hidecode_4;
    private DietSuggestionFragment.Mycollection addMycollection;
    private Context context;
    private UserHealthknowage userHealthknowage;
    private List<UserHealthknowage> userHealthknowage_list;



    public static SportSuggestionsDetailFragment newInstance(String param1, String param2) {
        SportSuggestionsDetailFragment fragment = new SportSuggestionsDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SportSuggestionsDetailFragment() {
        // Required empty public constructor
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
        addMycollection = restAdapter.create(DietSuggestionFragment.Mycollection.class);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();
        mAdapter_SportSuggestion = new ListViewDataAdapter<JsonData>();
        mAdapter_SportSuggestion.setViewHolderClass(this, ViewHolder.class);
        listView.setAdapter(mAdapter_SportSuggestion);
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
                        mAdapter_SportSuggestion.getDataList().clear();
                        mAdapter_SportSuggestion.getDataList().addAll(data.toArrayList());
                        mPtrFrame_Health.refreshComplete();
                        mAdapter_SportSuggestion.notifyDataSetChanged();
                    }
                }, 0);
            }
        }, this.context, "/userhealthknowledgeview/sportknowledge/", "request_init/healthknowledge/SportSuggestion.json", "sportsuggestion");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        public void onFragmentInteraction(Uri uri);
    }



    Runnable getid_list_sport = new Runnable() {  //利用code请求我的收藏id
        @Override
        public void run() {
            collectionid_list = addMycollection.getTheCollectionId(hidecode_4, 1, "3");
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
            addMycollection.deletecollection(thiscollectionid, new retrofit.Callback<MyCollectionDomain>() {
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
            final View view = inflater.inflate(R.layout.sportsuggestion_list_item, null);
            title = (TextView) view.findViewById(R.id.suggestiontitle_sport);
            line = (View)view.findViewById(R.id.suggestline_sport);
            text = (TextView)view.findViewById(R.id.suggestinfo_sport);
            img = (CubeImageView)view.findViewById(R.id.yinshiimg_sport);
            time = (TextView)view.findViewById(R.id.suggesttime_sport);
            imgbtn = (ImageButton)view.findViewById(R.id.thumb_up_sport);
            imgbtn2 = (ImageButton)view.findViewById(R.id.heart_sport);
            shoucang = (TextView)view.findViewById(R.id.shoucang_sport);
            hidecode = (TextView)view.findViewById(R.id.hidecode);
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
                        hidecode_4 = Integer.parseInt(hidecode.getText().toString());
                    } catch (NumberFormatException e) {
                        Log.d("SportSuggestionDetailFragment", e.getMessage());
                    }
                    if(shoucang.getText().equals("收藏")){
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            addMycollection.addcollection(new Suggestioncollect("3", hidecode_4, 1), new Callback<Suggestioncollect>() {
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
                        new Thread(getid_list_sport).start();
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
                helper.deleteUserHealthknowageByType("sportknowledge");  //清空全表
                helper.addUserHealthknowage(userHealthknowage);
            }else if(!NetHelper.isHaveInternet(getApplicationContext() )&& helper.getUserHealthknowageList("sportknowledge")!=null){  //如果网络不可用,并且数据库有数据
                userHealthknowage_list = new ArrayList<>();
                userHealthknowage_list = helper.getUserHealthknowageListByType("sportknowledge");
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
