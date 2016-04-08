package com.njucm.cmdh.app.fragments.myhealthy.healthcollection;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.DoctorView;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.RequestClient;

import java.util.ArrayList;
import java.util.List;

public class HealthKnowageCollectionFragment extends BaseFragment {

    MyApplication myApplication;
    private OnFragmentInteractionListener mListener;
    RequestClient requestClient;
    private List<DoctorView> doctorInfo = new ArrayList<>();



    public HealthKnowageCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getActivity().getApplicationContext());
        } catch (Exception e) {
            Log.d("errorinfo", e.getMessage());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_knowage_collection, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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





}
