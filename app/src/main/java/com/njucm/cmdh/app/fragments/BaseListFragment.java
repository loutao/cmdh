package com.njucm.cmdh.app.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njucm.cmdh.app.R;

import java.util.Map;

public class BaseListFragment extends ListFragment {
    public Map<String, String> map;
    public String tag = this.getClass().getSimpleName();
    public Context context;
    public Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        activity = getActivity();
    }

}
