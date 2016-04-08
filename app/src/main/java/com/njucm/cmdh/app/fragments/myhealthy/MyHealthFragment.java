package com.njucm.cmdh.app.fragments.myhealthy;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.ConstitutionActivity;
import com.njucm.cmdh.app.activitys.HabitActivity;
import com.njucm.cmdh.app.activitys.HealthSuggestionsActivity;
import com.njucm.cmdh.app.activitys.HealthTendingActivity;
import com.njucm.cmdh.app.activitys.HealthWarningActivity;
import com.njucm.cmdh.app.activitys.MyCollectionActivity;
import com.njucm.cmdh.app.activitys.NoNetActivity;
import com.njucm.cmdh.app.adapters.ImagePagerAdapter;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.utils.AutoScrollViewPagerInnerDemo;
import com.njucm.cmdh.app.utils.NetHelper;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.ListUtils;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.njucm.cmdh.app.utils.ImageOptimizing.decodeSampledBitmapFromResource;
import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MyHealthFragment extends BaseFragment {
    private String TAG = MyHealthFragment.class.getName();
    private TextView indexText;
    AutoScrollViewPager scrollViewPager;
    private List<Integer> imageIdList = new ArrayList<Integer>();
    private CirclePageIndicator indicator;
    private int index;
    private ScrollControlReceiver scrollControlReceiver;
    private TextView tv_picture_explain;
    private GridView gridView;
    private Context context;
    View ConsidentyTopImage;
    static final String[] MOBILE_OS = new String[]{
            "我的体质", "养生建议", "我的收藏", "健康趋势", "健康预警", "生活习惯"};

    
    public MyHealthFragment() {
    }
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_my_health);
        context = getActivity().getApplicationContext();
        gridView = (GridView) findViewById(R.id.gv_myhealth_grid);
        scrollViewPager = (AutoScrollViewPager) findViewById(R.id.view_pager);
        ConsidentyTopImage = findViewById(R.id.myhealth_top_image);

        tv_picture_explain = (TextView) getContentView().findViewById(R.id.tv_picture_explain);
        tv_picture_explain = (TextView) getContentView().findViewById(R.id.tv_picture_explain);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);//找到翻页指示器
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();//获取屏幕尺寸
        int w_screen = displayMetrics.widthPixels;
        int h_screen = displayMetrics.heightPixels;
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 3* h_screen / 10);
        Drawable drawable =new BitmapDrawable(decodeSampledBitmapFromResource(getResources(), R.drawable.lifehobby, 60, 60));
        imageIdList.add(R.drawable.banner1r);
        imageIdList.add(R.drawable.banner2r);
        imageIdList.add(R.drawable.banner3r);
        imageIdList.add(R.drawable.banner4r);
        scrollViewPager.setLayoutParams(layoutParams);
        ConsidentyTopImage.setLayoutParams(layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(w_screen * 2 / 3, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        tv_picture_explain.setLayoutParams(layoutParams);
        scrollViewPager.setAdapter(new ImagePagerAdapter(getActivity(), imageIdList));  //利用适配器填充scrollViewPager
        scrollViewPager.setOnPageChangeListener(new MyOnPageChangeListener());  //设置页面切换时的监听器
        indicator.setViewPager(scrollViewPager);  //把指示器绑定在scrollViewPager上面
        scrollViewPager.setInterval(3000);  //设置切换的时间
        scrollViewPager.startAutoScroll();  //设置为自动切换页面
        scrollViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));  //使能够直接跳转到指定的图片
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 3 * h_screen / 5);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.myhealth_top_image);
        gridView.setLayoutParams(layoutParams);
        layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 7 * h_screen / 45);
        gridView.setAdapter(new ImageAdapter(getActivity(), MOBILE_OS, layoutParams, displayMetrics));
        gridView.setOnItemClickListener(new myHealthGridviewListener());  //设置gridview的item监听
        Log.i(TAG, "--------onCreateView");

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Log.i(TAG, "--------onActivityCreated");

    }

    private void sendToNoNetActivity(int number){
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        Intent intent = new Intent();
        intent.putExtra("number", bundle);
        intent.setClass(getActivity(), NoNetActivity.class);
        getActivity().startActivity(intent);
    }

    class myHealthGridviewListener implements GridView.OnItemClickListener {
        /**
         * @param parent
         * @param view
         * @param position 点击的位置，从0开始
         * @param id
         */
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            try {
                Intent intent = new Intent();

                switch (position) {
                    case 0:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), ConstitutionActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(1);
                        }
                        break;
                    case 1:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), HealthSuggestionsActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(2);
                        }
                        break;
                    case 2:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), MyCollectionActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(3);
                        }
                        break;
                    case 3:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), HealthTendingActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(4);
                        }
                        break;
                    case 4:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), HealthWarningActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(5);
                        }
                        break;
                    case 5:
                        if(NetHelper.isHaveInternet(getApplicationContext())){
                            intent.setClass(getActivity(), HabitActivity.class);
                            getActivity().startActivity(intent);
                        }else{
                            sendToNoNetActivity(6);
                        }
                        break;
                    default:
                        break;

                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }


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
                textView.setText(mobileValues[position]);

                // set image based on selected text
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.myhealth_grid_image);

                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                String mobile = mobileValues[position];

                if (mobile.equals("我的体质")) {

//                    imageView.setImageResource(R.drawable.myphysical);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.myphysical, 60, 60));
                } else if (mobile.equals("养生建议")) {
//                    imageView.setImageResource(R.drawable.healthsuggestion);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.healthsuggestion, 60, 60));
                } else if (mobile.equals("我的收藏")) {
//                    imageView.setImageResource(R.drawable.mycolluction);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.mycolluction, 60, 60));
                } else if (mobile.equals("健康趋势")) {
//                    imageView.setImageResource(R.drawable.healthtending);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.healthtending, 60, 60));
                } else if (mobile.equals("健康预警")) {
//                    imageView.setImageResource(R.drawable.healthwaring);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.healthwaring, 60, 60));
                } else if (mobile.equals("生活习惯")) {
//                    imageView.setImageResource(R.drawable.lifehobby);
                    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.lifehobby, 60, 60));
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
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
