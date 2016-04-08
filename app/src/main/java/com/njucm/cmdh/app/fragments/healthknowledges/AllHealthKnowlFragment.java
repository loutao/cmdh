package com.njucm.cmdh.app.fragments.healthknowledges;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.DetileResultActivity;
import com.njucm.cmdh.app.adapters.ImagePagerAdapter;
import com.njucm.cmdh.app.domain.PhysiqueInfo;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.misc.HealthKnowlListItem;
import com.njucm.cmdh.app.service.PhysiqueConsIdentyService;
import com.njucm.cmdh.app.utils.AutoScrollViewPagerInnerDemo;
import com.njucm.cmdh.app.utils.RequestClient;
import com.njucm.cmdh.app.utils.RequestJsonData;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
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

public class AllHealthKnowlFragment extends BaseFragment {
    private String TAG = AllHealthKnowlFragment.class.getName();
    AutoScrollViewPager scrollViewPager;
    private List<Integer> imageIdList = new ArrayList<Integer>();
    private CirclePageIndicator indicator;
    private int index;
    private ScrollControlReceiver scrollControlReceiver;
    private Context context;
    private TextView tv_picture_explain;
    View HealthKnowlListHead;
    private List<HealthKnowlListItem> healthKnowllist;
    List<TCMHealthknowledge> tcmHealthknowledges = new ArrayList<TCMHealthknowledge>();
    List<PhysiqueInfo> physiqueInfos = new ArrayList<PhysiqueInfo>();
    PhysiqueConsIdentyService physiqueConsIdentyService;
    RequestClient requestClient;
    Bundle bundle;
    Fragment fragment = this;
    private PtrClassicFrameLayout mPtrFrame;
    private ListViewDataAdapter<JsonData> dataListViewDataAdapter;
    private ListView listView;
    private ImageLoader mImageLoader;
    private ProgressDialog progressDialog;
    private ListViewDataAdapter<JsonData> mAdapter;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        context = getActivity();
       // dbHelper = DBHelper.getInstance(this.context, HBContants.DATABASE_NAME_HealthKnow);
        final View contentView = layoutInflater.inflate(R.layout.fragment_all_health_knowl, null);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getResources().getString(R.string.loadingtitle));
        progressDialog.setMessage(getResources().getString(R.string.loadingtext));
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h_screen / 3);
        mImageLoader = ImageLoaderFactory.create(context);
        listView = (ListView) contentView.findViewById(R.id.rotate_header_list_view);
        mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.rotate_header_list_view_frame);
        HealthKnowlListHead = (RelativeLayout) getLayoutInflater(getArguments()).inflate(R.layout.scoll_image_item, null);
        tv_picture_explain = (TextView) HealthKnowlListHead.findViewById(R.id.tv_picture_explain);
        scrollViewPager = (AutoScrollViewPager) HealthKnowlListHead.findViewById(R.id.view_pager);
        scrollViewPager.setLayoutParams(layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(w_screen * 2 / 3, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv_picture_explain.setLayoutParams(layoutParams);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    position--;
                    Intent intent = new Intent();
                    Bundle bundletitle = new Bundle();
                    bundletitle.putString("healthknowledge_title", mAdapter.getItem(position).optString("healthknowledgetitle"));
                    bundletitle.putString("healthknowledge_content", mAdapter.getItem(position).optString("healthknowledgecontent"));
                    bundletitle.putString("exersuggtime", mAdapter.getItem(position).optString("exersuggtime"));
                    intent.putExtra("bundle", bundletitle);
                    intent.setClass(getActivity(), DetileResultActivity.class);
                    getActivity().startActivity(intent);
                }
            }
        });
        listView.addHeaderView(HealthKnowlListHead);
        indicator = (CirclePageIndicator) HealthKnowlListHead.findViewById(R.id.indicator);
        imageIdList.add(R.drawable.banner19);
        imageIdList.add(R.drawable.banner29);
        imageIdList.add(R.drawable.banner39);
        imageIdList.add(R.drawable.banner49);
        scrollViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList));
//        indicator.setViewPager(scrollViewPager);
        scrollViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        indicator.setViewPager(scrollViewPager);
        scrollViewPager.setInterval(3000);
//        scrollViewPager.startAutoScroll();
        scrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));
//        scrollViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);


        bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(AutoScrollViewPagerInnerDemo.EXTRA_INDEX);
            if (index == AutoScrollViewPagerInnerDemo.DEFAULT_INDEX) {
                scrollViewPager.startAutoScroll();
            }
        }
//        mAdapter = new ListViewDataAdapter<JsonData>();
//        mAdapter.setViewHolderClass(this, ViewHolder.class);
//        listView.setAdapter(mAdapter);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);

        new MyAsyncTask().execute();

        // the following are default settings

        return contentView;
    }

    protected void updateData() {

        RequestJsonData.getCustomListData(new RequestFinishHandler<JsonData>() {
            @Override
            public void onRequestFinish(final JsonData data) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getDataList().clear();
                        mAdapter.getDataList().addAll(data.optJson("results").toArrayList());
                        mPtrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 0);
            }
        }, this.context, "/tbtcmhealthknowledge/", "request_init/healthknowledge/healthknowledge-list2.json", "allhealthknowage");
    }


    private List<? extends Map<String, ?>> getData(String[] strs) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", strs[i]);
            list.add(map);
        }
        return list;
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, byte[]> {
        @Override
        protected void onPostExecute(byte[] bytes) {
            super.onPostExecute(bytes);
            mAdapter = new ListViewDataAdapter<JsonData>();
            mAdapter.setViewHolderClass(fragment, ViewHolder.class);
            listView.setAdapter(mAdapter);
//            progressDialog.dismiss();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            progressDialog.show();
        }

        @Override
        protected byte[] doInBackground(String... params) {
            mPtrFrame.setPtrHandler(new PtrHandler() {
                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    updateData();
                }

                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
            });

            return new byte[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        requestClient = ServiceGenerator.createService(RequestClient.class, getString(R.string.base_url), gson, this.context);
        // Create an instance of our GitHub API interface.*/

        Log.i(TAG, "--------onActivityCreated");
    }


    //添加数据
    public List<HealthKnowlListItem> getData() {
        healthKnowllist = new ArrayList<HealthKnowlListItem>();
        for (int i = 1; i < 20; i++) {
            HealthKnowlListItem item = new HealthKnowlListItem();//这个必须在这里new 不然 数据都是一样的

            if (i % 3 == 0) {
                item.setType(0);
            } else {
                item.setType(1);
            }
            //item.setAddress(mTitle[i]);
            item.setAddress("中医养生" + i);
            item.setName("wang" + i);
            healthKnowllist.add(item);

        }

        return healthKnowllist;
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            tv_picture_explain.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
                    .append(ListUtils.getSize(imageIdList)));
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
        scrollViewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
        scrollViewPager.startAutoScroll();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "----------onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();

        // receive broadcast that selected fragment have changed, to start or stop auto scroll ViewPager
        scrollControlReceiver = new ScrollControlReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(scrollControlReceiver,
                new IntentFilter(AutoScrollViewPagerInnerDemo.ACTION_FRAGMENT_SELECTED));
    }


    @Override
    public void onStop() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(scrollControlReceiver);
        super.onStop();
    }

    private class ScrollControlReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int selectedPosition = intent.getIntExtra(AutoScrollViewPagerInnerDemo.EXTRA_SELECTED_POSITION, 0);
                if (index == selectedPosition) {
                    scrollViewPager.startAutoScroll();
                } else {
                    scrollViewPager.stopAutoScroll();
                }
            }
        }
    }

    private class ViewHolder extends ViewHolderBase<JsonData> {

        private CubeImageView mImageView;
        private TextView healthktTextVIew, healthkcTextView;

        @Override
        public View createView(LayoutInflater inflater) {
            View view = inflater.inflate(R.layout.healthknowl_item_title_two, null);
            mImageView = (CubeImageView) view.findViewById(R.id.healtnknol_content_image);
            healthktTextVIew = (TextView) view.findViewById(R.id.play_introduce_title);
            healthkcTextView = (TextView) view.findViewById(R.id.healtnknol_item_detail);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public void showData(int position, JsonData itemData) {

            try {
                mImageView.loadImage(mImageLoader, itemData.optString("temp_picturelocationid"));
            } catch (Exception e) {
                mImageView.setImageDrawable(getResources().getDrawable(R.drawable.zhihuizhongyi));
            }
            Log.i("position",position+"");
            healthktTextVIew.setText(itemData.optString("healthknowledgetitle"));
            healthkcTextView.setText(itemData.optString("healthknowledgecontent").substring(0, 20));
        }
    }
}