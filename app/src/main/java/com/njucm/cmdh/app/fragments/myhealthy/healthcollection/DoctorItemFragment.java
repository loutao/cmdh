package com.njucm.cmdh.app.fragments.myhealthy.healthcollection;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njucm.cmdh.app.R;





public class DoctorItemFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    TextView doctorname_1;
    TextView doctorsex_1;
    TextView doctorage_1;
    TextView professionalrands_1;
    TextView doctorworktime_1;
    TextView doctorsynopsis_1;
    TextView researcharea_1;
    TextView researchfindings_1;
    TextView worklocation_1;
    TextView doctorexptypename_1;
    TextView doctorexpertisetitle_1;
    TextView hospitalname_1;
    TextView hospitalofficename_1;
    TextView workduty_1;

    public DoctorItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_item, container, false);
        if(getArguments()!=null){
            String doctorname = getArguments().getString("doctorname");
            String doctorsex = getArguments().getString("doctorsex");
            int doctorage = getArguments().getInt("doctorage");
//            String professionalrands = getArguments().getString("professionalrands");
            int doctorworktime = getArguments().getInt("doctorworktime");
            String doctorsynopsis = getArguments().getString("doctorsynopsis");
            String researcharea = getArguments().getString("researcharea");
            String researchfindings = getArguments().getString("researchfindings");
            String adminisareaprovince = getArguments().getString("adminisareaprovince");
            String adminisareacity = getArguments().getString("adminisareacity");
            String adminisareacounty = getArguments().getString("adminisareacounty");
            String doctorexptypename = getArguments().getString("doctorexptypename");
            String doctorexpertisetitle = getArguments().getString("doctorexpertisetitle");
            String hospitalname = getArguments().getString("hospitalname");
            String hospitalofficename = getArguments().getString("hospitalofficename");
            String workduty = getArguments().getString("workduty");

            doctorname_1 = (TextView)view.findViewById(R.id.doctorname);
            doctorsex_1 = (TextView)view.findViewById(R.id.doctorsex);
            doctorage_1 = (TextView)view.findViewById(R.id.doctorage);
//            professionalrands_1 = (TextView)view.findViewById(R.id.professionalrands);
            doctorworktime_1 = (TextView)view.findViewById(R.id.doctorworktime);
            doctorsynopsis_1 = (TextView)view.findViewById(R.id.doctorsynopsis);
            researcharea_1 = (TextView)view.findViewById(R.id.researcharea);
            worklocation_1 = (TextView)view.findViewById(R.id.workloction);
            doctorexpertisetitle_1 = (TextView)view.findViewById(R.id.doctorexpertisetitle);
            hospitalname_1 = (TextView)view.findViewById(R.id.hospitalname);

            doctorname_1.setText("姓名："+doctorname);
            doctorsex_1.setText("性别："+doctorsex);
            doctorage_1.setText("年龄："+doctorage);
//            professionalrands_1.setText("专业领域："+professionalrands);
            doctorworktime_1.setText("工作时长："+doctorworktime);
            doctorsynopsis_1.setText(""+doctorsynopsis);
            researcharea_1.setText("研究领域："+researcharea);
            worklocation_1.setText("地点："+adminisareaprovince+adminisareacity+adminisareacounty);
            doctorexpertisetitle_1.setText("研究专题："+doctorexpertisetitle);
            hospitalname_1.setText("医院及科室："+hospitalname+hospitalofficename);
        }
        return view;
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
