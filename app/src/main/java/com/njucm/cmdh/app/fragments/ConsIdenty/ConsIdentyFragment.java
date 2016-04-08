package com.njucm.cmdh.app.fragments.ConsIdenty;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.utils.AutoScrollViewPagerInnerDemo;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ListUtils;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class ConsIdentyFragment extends BaseFragment {
    private String TAG = ConsIdentyFragment.class.getName();
    private TextView indexText;
//    AutoScrollViewPager scrollViewPager;
    private List<Integer> imageIdList = new ArrayList<Integer>();
//    private CirclePageIndicator indicator;
    private int index;
    private ScrollControlReceiver scrollControlReceiver;
//    private TextView tv_picture_explain;
    private GridView gridView;
    String title;
    RelativeLayout ConsidentyTopImage;
    private Context context;

    static final String[] MOBILE_OS = new String[]{
            "平和质", "阳虚质", "阴虚质", "痰湿质", "湿热质", "气郁质", "气虚质", "血瘀质", "特禀质"};

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_cons_identy);
        Button all = (Button) findViewById(R.id.all);
        gridView = (GridView) findViewById(R.id.gv_considenty_grid);
        context = getApplicationContext();
        ConsidentyTopImage = (RelativeLayout) findViewById(R.id.considenty_top_image);
//        tv_picture_explain = (TextView) getContentView().findViewById(R.id.tv_picture_explain);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        all.setTextSize(w_screen / 40);
        all.setHeight(w_screen / 100);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h_screen / 15);

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConsIdentyFragment.this.getActivity(), AllIdentifyQuestion.class);
                startActivity(intent);
            }
        });
        layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 14* h_screen / 50);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    Intent intent = new Intent(ConsIdentyFragment.this.getActivity(), PhysiqueIntroduce.class);
                    Bundle bundle = new Bundle();
                    title = MOBILE_OS[position];
                    bundle.putString("title",title);
                    bundle.putInt("physiqueinfoid",position);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 0);
                }
            }
        });

//
//        scrollViewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
//        scrollViewPager.setLayoutParams(layoutParams);
        ConsidentyTopImage.setLayoutParams(layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(w_screen * 2 / 3, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        tv_picture_explain.setLayoutParams(layoutParams);
//        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        imageIdList.add(R.drawable.banner1);
        imageIdList.add(R.drawable.banner2);
        imageIdList.add(R.drawable.banner3);
        imageIdList.add(R.drawable.banner4);
//        scrollViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList));
//        scrollViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
//        indicator.setViewPager(scrollViewPager);
//        scrollViewPager.setInterval(3000);
//        scrollViewPager.startAutoScroll();
//        scrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 93 * h_screen / 200);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.considenty_top_image);
        gridView.setLayoutParams(layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, h_screen / 8);
        gridView.setAdapter(new ImageAdapter(getActivity(), MOBILE_OS, layoutParams, displayMetrics));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "--------onActivityCreated");
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            indexText.setText(new StringBuilder().append((position) % ListUtils.getSize(imageIdList) + 1).append("/")
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
//        scrollViewPager.stopAutoScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
//        scrollViewPager.startAutoScroll();
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
//                    scrollViewPager.startAutoScroll();
                } else {
//                    scrollViewPager.stopAutoScroll();
                }
            }
        }
    }

    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private final String[] mobileValues;
        RelativeLayout.LayoutParams layoutParams;
        int w_screen;
        int h_screen;


        public ImageAdapter(Context context, String[] mobileValues, RelativeLayout.LayoutParams layoutParams, DisplayMetrics displayMetrics) {
            this.context = context;
            this.mobileValues = mobileValues;
            this.layoutParams = layoutParams;
            this.w_screen = displayMetrics.widthPixels;
            this.h_screen = displayMetrics.heightPixels;

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.myhealth_grid_item, null);

                // set value into textview
                TextView textView = (TextView) gridView
                        .findViewById(R.id.myhealth_grid_text);
                textView.setTextSize(w_screen / 44);

                // set image based on selected text
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.myhealth_grid_image);

                String mobile = mobileValues[position];
                if (mobile.equals("平和质")) {
                    imageView.setImageResource(R.drawable.pinghe);
                    textView.setText(mobile);
                } else if (mobile.equals("气虚质")) {
                    imageView.setImageResource(R.drawable.qixu);
                    textView.setText(mobile);
                } else if (mobile.equals("阳虚质")) {
                    imageView.setImageResource(R.drawable.yangxu);
                    textView.setText(mobile);
                } else if (mobile.equals("阴虚质")) {
                    imageView.setImageResource(R.drawable.yinxu);
                    textView.setText(mobile);
                } else if (mobile.equals("痰湿质")) {
                    imageView.setImageResource(R.drawable.tanshi);
                    textView.setText(mobile);
                } else if (mobile.equals("湿热质")) {
                    imageView.setImageResource(R.drawable.shire);
                    textView.setText(mobile);
                } else if (mobile.equals("气郁质")) {
                    imageView.setImageResource(R.drawable.qiyu);
                    textView.setText(mobile);
                } else if (mobile.equals("血瘀质")) {
                    imageView.setImageResource(R.drawable.xueyu);
                    textView.setText(mobile);
                } else {
                    imageView.setImageResource(R.drawable.tebing);
                    textView.setText(mobile);
                }

            } else {
                gridView = (View) convertView;
            }
            typeface(gridView);
            return gridView;
        }

        @Override
        public int getCount() {
            return mobileValues.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
