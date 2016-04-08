package com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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


public class HealthSuggestionFragment extends BaseFragment{

    private IndicatorViewPager indicatorViewPager;

    //上方划动条
    @InjectView(R.id.fragment_tabmain)
    LinearLayout linerLayout;
    //
    @InjectView(R.id.tabmain_viewPager)
    ViewPager viewPager;
    //划动指示器
    @InjectView(R.id.tabmain_indicator)
    FixedIndicatorView indictor;

    private OnFragmentInteractionListener mListener;


    public HealthSuggestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_tabmain);
        ButterKnife.inject(this, getContentView());
        Resources res = getResources();
        indictor.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#669900"), 2, ScrollBar.Gravity.BOTTOM));
        indictor.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(getApplicationContext(), R.color.green_dark, R.color.gray, R.dimen.text_size_medium1, R.dimen.text_size_medium1));viewPager.setCanScroll(true);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setPrepareNumber(1);
        indicatorViewPager = new IndicatorViewPager(indictor, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter{

        private String[] tabNames = {"饮食建议", "睡眠建议", "运动建议"};
        //        private int[] tabicons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector};
        private LayoutInflater inflater;
        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
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
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            String[] types = {"sport", "sleep", "diet"};
            Bundle viewBundle = new Bundle();
//            Fragment fragments = new DietSuggestionFragment();
            fragments.add(new DietSuggestionFragment());
            fragments.add(new SleepSuggestionFragment());
            fragments.add(new SportSuggestionsDetailFragment());
            viewBundle.putString("type", types[position]);

            return fragments.get(position);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
