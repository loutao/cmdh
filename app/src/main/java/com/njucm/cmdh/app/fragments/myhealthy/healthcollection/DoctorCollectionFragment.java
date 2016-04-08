package com.njucm.cmdh.app.fragments.myhealthy.healthcollection;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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


public class DoctorCollectionFragment extends BaseFragment {
    MyApplication myApplication;
    private OnFragmentInteractionListener mListener;
    RequestClient requestClient;
    private List<DoctorView> doctorInfo = new ArrayList<>();
    GridView gridView;
    ProgressDialog progressDialog;
    DoctorView doctorItem;



    public DoctorCollectionFragment() {
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
        View view = null;

        try {
            view = inflater.inflate(R.layout.fragment_doctor_collection, container, false);
            gridView = (GridView) view.findViewById(R.id.gv_myhealth_grid);
            progressDialog = ProgressDialog.show(getActivity(), "加载中...", "请等待", true, false);
            progressDialog.show();
            new Thread(getData).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doctorItem = new DoctorView();
                Bundle bundle = new Bundle();
                bundle.putString("doctorname", doctorInfo.get(position).getDoctorname());
                bundle.putString("doctorsex", doctorInfo.get(position).getDoctorsex());
                bundle.putInt("doctorage", doctorInfo.get(position).getDoctorage());
                bundle.putString("professionalrands", doctorInfo.get(position).getProfessionalrands());
                bundle.putInt("doctorworktime", doctorInfo.get(position).getDoctorworktime());
                bundle.putString("doctorsynopsis", doctorInfo.get(position).getDoctorsynopsis());
                bundle.putString("researcharea", doctorInfo.get(position).getResearcharea());
                bundle.putString("researchfindings", doctorInfo.get(position).getResearchfindings());
                bundle.putString("adminisareaprovince", doctorInfo.get(position).getAdminisareaprovince());
                bundle.putString("adminisareacity", doctorInfo.get(position).getAdminisareacity());
                bundle.putString("adminisareacounty", doctorInfo.get(position).getAdminisareacounty());
                bundle.putString("doctorexptypename", doctorInfo.get(position).getDoctorexptypename());
                bundle.putString("doctorexpertisetitle", doctorInfo.get(position).getDoctorexpertisetitle());
                bundle.putString("hospitalname", doctorInfo.get(position).getHospitalname());
                bundle.putString("hospitalofficename", doctorInfo.get(position).getHospitalofficename());
                bundle.putString("workduty", doctorInfo.get(position).getWorkduty());
                try {
                    android.support.v4.app.FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    DoctorItemFragment ditem = new DoctorItemFragment();
                    ditem.setArguments(bundle);
                    ft.replace(R.id.containerofmenu, ditem);
                    ft.addToBackStack(null);
                    ft.commit();
                } catch (Exception e) {
                    Log.d("DoctorCollectionFragment", e.getMessage());
                }


            }
        });
        return view;

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

    Runnable getData = new Runnable() {
        @Override
        public void run() {
            try {
                doctorInfo = requestClient.getDoctorList("doctorview");
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bundle bundle = new Bundle();
            bundle.putInt("1", 1);
            Message message = new Message();
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            gridView.setAdapter(new DoctorViewAdapter(getActivity().getApplicationContext()));
        }
    };

    private class ViewHolder{
        private TextView doctorname;
        private TextView professionalrands;
        private TextView researcharea;

        private void update(){
            researcharea.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            int position = 0;
                            try {
                                position = (Integer)researcharea.getTag();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //这里保证同一行item高度相同！也就是同一行整齐
                            if(position>0){
                                View v = (View)professionalrands.getTag();
                                int height = v.getHeight();

                                View view = gridView.getChildAt(position-1);
                                int lastHeight = view.getHeight();

                                if(height > lastHeight){
                                    view.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.FILL_PARENT, height));
                                }else if(height < lastHeight){
                                    v.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.FILL_PARENT, lastHeight));
                                }
                            }
                        }
                    }
            );
        }
    }

    private class DoctorViewAdapter extends BaseAdapter {
        private Context context;

        private DoctorViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder holder;

            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.doctorgriditem, null);
                holder.doctorname = (TextView) convertView.findViewById(R.id.doctorname);
                holder.professionalrands = (TextView) convertView.findViewById(R.id.professionalrands);
                holder.researcharea = (TextView)convertView.findViewById(R.id.researcharea);
                convertView.setTag(holder);
//                holder.update();
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            try {
                holder.doctorname.setText(doctorInfo.get(position).getDoctorname());
                holder.professionalrands.setText(doctorInfo.get(position).getProfessionalrands());
                holder.researcharea.setText(doctorInfo.get(position).getResearcharea());
//                holder.researcharea.setTag(position);
//                holder.professionalrands.setTag(convertView);  //绑定当前的item也就是convertview
            } catch (Exception e) {
                e.printStackTrace();
            }
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return doctorInfo.get(position);
        }

        @Override
        public int getCount() {
            return doctorInfo.size();
        }
    }

}
