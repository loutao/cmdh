package com.njucm.cmdh.app.fragments.myhealthy.healthtending;


import android.app.ActionBar;
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


public class HealthTendingFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private IndicatorViewPager indicatorViewPager;
    private LayoutInflater inflate;

    private String mParam1;
    private String mParam2;

    @InjectView(R.id.fragment_tabmain)
    LinearLayout linearLayout;
    @InjectView(R.id.tabmain_viewPager)
    ViewPager viewPager;
    @InjectView(R.id.tabmain_indicator)
    FixedIndicatorView indicator;

    private OnFragmentInteractionListener mListener;

    ActionBar actionBar;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment healthtending.
     */
    // TODO: Rename and change types and number of parameters
    public static HealthTendingFragment newInstance(String param1, String param2) {
        HealthTendingFragment fragment = new HealthTendingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HealthTendingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_tabmain);
        typeface(getView());
        ButterKnife.inject(this, getContentView());
        Resources res = getResources();
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#669900"), 2, ScrollBar.Gravity.BOTTOM));
        indicator.setOnTransitionListener(new OnTransitionTextListener().setValueFromRes(getApplicationContext(), R.color.green_dark, R.color.gray, R.dimen.text_size_medium1, R.dimen.text_size_medium1));
        viewPager.setCanScroll(true);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(0);
        // 默认是1,，自动预加载左右两边的界面。设置viewpager预加载数为0。只加载加载当前界面。
        viewPager.setPrepareNumber(1);
        viewPager.getCurrentItem();
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        actionBar = getActivity().getActionBar();
        actionBar.setTitle(R.string.healthtending);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

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
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"运动趋势", "睡眠趋势", "饮食趋势"};
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
            fragments.add(new SportTendingFragment());
            fragments.add(new SleepTendingFragment());
            fragments.add(new DietTendingFragment());
            viewbundle.putString("type", types[position]);

            return fragments.get(position);
        }
    }


}
