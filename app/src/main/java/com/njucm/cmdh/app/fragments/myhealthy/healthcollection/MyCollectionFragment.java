package com.njucm.cmdh.app.fragments.myhealthy.healthcollection;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.MainActivity;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.fragments.healthknowledges.HealthKnowledgeFragment;
import com.njucm.cmdh.app.misc.Fragments;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class MyCollectionFragment extends BaseFragment{



    private OnFragmentInteractionListener mListener;
    private int subActionButtonSize = 100;



    public MyCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null){
        }

    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.menuround);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, Fragment.instantiate(getApplicationContext(), Fragments.HealthSuggestionCollection.getFragment()))
                .commit();
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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
