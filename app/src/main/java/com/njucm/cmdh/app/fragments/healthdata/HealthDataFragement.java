package com.njucm.cmdh.app.fragments.healthdata;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.viewpager.indicator.FixedIndicatorView;
import com.njucm.cmdh.viewpager.indicator.IndicatorViewPager;
import com.njucm.cmdh.viewpager.indicator.slidebar.ColorBar;
import com.njucm.cmdh.viewpager.indicator.slidebar.ScrollBar;
import com.njucm.cmdh.viewpager.indicator.transition.OnTransitionTextListener;
import java.util.ArrayList;
import butterknife.ButterKnife;
import butterknife.InjectView;
import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;
public class HealthDataFragement extends BaseFragment {
    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;
    public static final String INTENT_STRING_TABNAME = "intent_String_tabname";
    public static final String INTENT_INT_INDEX = "intent_int_index";
    public static final String INTENT_STRINGLIST_NAME = "viewpage_names";
    private ArrayList<String> viewName = new ArrayList<String>();
    @InjectView(R.id.fragment_tabmain)
    LinearLayout linearLayout;
    @InjectView(R.id.tabmain_viewPager)
    ViewPager viewPager;
    @InjectView(R.id.tabmain_indicator)
    FixedIndicatorView indicator;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_tabmain);
        typeface(getView());
        ButterKnife.inject(this, getContentView());
        Resources res = getResources();
        fragmentTransaction = getFragmentManager().beginTransaction();
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#669900"), 2, ScrollBar.Gravity.BOTTOM));
        indicator.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(getApplicationContext(), R.color.green_dark, R.color.gray, R.dimen.text_size_medium1, R.dimen.text_size_medium1));
        viewPager.setCanScroll(true);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(1);
        // 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
        viewPager.setPrepareNumber(1);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
    }
    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"运动", "睡眠", "饮食"};
//        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }
        @Override
        public int getCount() {
            return tabNames.length;
        }
        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = (TextView) inflater.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            typeface(textView);
            textView.setText(tabNames[position]);
            //  textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            return textView;
        }
        @Override
        public Fragment getFragmentForPage(int position) {
            ArrayList<Fragment> fragments = new ArrayList<android.support.v4.app.Fragment>();
            String[] types = {"scale", "flip", "depth"};
            Bundle viewbundle = new Bundle();
            fragments.add(new SportChartFragment());
            fragments.add(new SleepInfoFragment());
            fragments.add(new DiertInfoFragment());
            viewbundle.putString("type", types[position]);
            return fragments.get(position);
        }
    }

}
