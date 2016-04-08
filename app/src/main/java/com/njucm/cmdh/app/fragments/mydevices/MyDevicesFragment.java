package com.njucm.cmdh.app.fragments.mydevices;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.DetileResultActivity;
import com.njucm.cmdh.app.activitys.codoon.DiscoveryAccessoryActivity;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.misc.HealthKnowlListItem;
import com.ratio.deviceService.datamanager.AccessoryManager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

public class MyDevicesFragment extends BaseFragment implements View.OnClickListener {
    private String TAG = MyDevicesFragment.class.getName();
    private Context context;
    private LinearLayout bleBandLayout;


    /**
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.bind_accessory, container, false);
        bleBandLayout = ((LinearLayout) view.findViewById(R.id.bind_codoon_bleband));
        bleBandLayout.setOnClickListener(this);
        Log.i(TAG, "--------onCreateView");

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onPause() {
        super.onPause();
        // stop auto scroll when onPause
    }

    @Override
    public void onResume() {
        super.onResume();
        // start auto scroll when onResume
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "----------onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_codoon_bleband:
                startSeartchDevice("codoon");
                return;
        }

    }

    public void startSeartchDevice(String paramString) {
        if (!AccessoryManager.isSupportBLEDevice(context)) {
            return;
        }
        if (paramString != null) {
            Intent localIntent = new Intent(context, DiscoveryAccessoryActivity.class);
            localIntent.putExtra("is_rom_device", true);
            localIntent.putExtra("device_name", paramString);
            startActivityForResult(localIntent, 0);
        }
    }


}