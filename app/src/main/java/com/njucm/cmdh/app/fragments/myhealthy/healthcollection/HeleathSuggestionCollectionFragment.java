package com.njucm.cmdh.app.fragments.myhealthy.healthcollection;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.activitys.HealthSuggestionExpand2;
import com.njucm.cmdh.app.domain.HealthSuggestionCombileSimplify;
import com.njucm.cmdh.app.domain.Recommendedconditionsmapp;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.service.ServiceGenerator;
import com.njucm.cmdh.app.utils.HBContants;
import com.njucm.cmdh.app.utils.HealthSuggestionCombileSimplifyDBHelper;
import com.njucm.cmdh.app.utils.RequestClient;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
public class HeleathSuggestionCollectionFragment extends BaseFragment {
    MyApplication myApplication;
    private ActionBar actionBar;
    public String TAG = "SportTendingFragment";
    @InjectView(R.id.sugglisst)
    ListView suggList;
    ProgressDialog progressDialog;
    List<Recommendedconditionsmapp> list1;
    RequestClient requestClient;
    HealthSuggestionCombileSimplifyDBHelper helper;

    private OnFragmentInteractionListener mListener;


    public HeleathSuggestionCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApplication = (MyApplication)getApplicationContext();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        requestClient = ServiceGenerator.createService(RequestClient.class, MyApplication.myUrl, gson, getActivity().getApplicationContext());
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(R.layout.fragment_heleath_suggestion_collection, container, false);
            ButterKnife.inject(this, view);
            progressDialog = ProgressDialog.show(getActivity(), "加载中", "请等待", true, false);
            progressDialog.show();
            new Thread(getCollectedSuggById).start();

        } catch (Exception e) {
            Log.d("HealthSuggestionCollectionFragment", e.getMessage());
        }
        suggList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("constitution", list1.get(position).getTemp_healthsuggestlimitedid());
                Intent intent = new Intent();
                intent.putExtra("constitution", bundle);
                intent.setClass(getActivity(), HealthSuggestionExpand2.class);
                startActivity(intent);
            }
        });
        return view;
    }

    Runnable getCollectedSuggById = new Runnable() {
        @Override
        public void run() {
            helper = HealthSuggestionCombileSimplifyDBHelper.getInstance(getActivity(), HBContants.DATABASE_NAME_USERHEALTHKNOWLEDGE);
            List<HealthSuggestionCombileSimplify> list = helper.getrecommendidList();

            StringBuilder stringBuilder = new StringBuilder();
            for(int i=0;i<list.size();i++){
//                i==0?stringBuilder.append(list.get(i).getRecommendedconditionsmappid()):stringBuilder.append(list.get(i).getRecommendedconditionsmappid());
                if(i == 0){
                    stringBuilder.append(list.get(i).getRecommendedconditionsmappid());
                }else{
                    stringBuilder.append(","+list.get(i).getRecommendedconditionsmappid());
                }
            }
            String queryString = stringBuilder.toString();
            list1 = requestClient.getHealthsuggestioncollectionById("tbrecommendedconditionsmappcombineid/", "{"+queryString+"}");
            Message message = new Message();
            Bundle bundle = new Bundle();
            bundle.putInt("1", 1);
            message.setData(bundle);
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            suggList.setAdapter(new HealthSuggestionAdapter(getActivity().getApplicationContext()));
            progressDialog.dismiss();
        }
    };


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            getActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ViewHolder{
        CubeImageView image;
        TextView title;
        TextView cons;
    }

    private class HealthSuggestionAdapter extends BaseAdapter{
        public String queryString;
        public Context context;
        private ImageLoader mImageLoader;

        private HealthSuggestionAdapter(Context context) {
            this.context = context;
            mImageLoader = ImageLoaderFactory.create(context);
        }

        private HealthSuggestionAdapter(Context context, String queryString) {
            this.context = context;
            this.queryString = queryString;
        }

        @Override
        public int getCount() {
            return list1.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.healthsuggitem, null);
                holder.image = (CubeImageView) convertView.findViewById(R.id.pic);
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.cons = (TextView) convertView.findViewById(R.id.cons);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                holder.image.loadImage(mImageLoader, list1.get(position).getTemp_healthsuggestid().getTemp_picturelocationid().getOriginalpicturepath());
                holder.title.setText(list1.get(position).getTemp_healthsuggestid().getTemp_picturelocationid().getPicturename());
                holder.cons.setText(list1.get(position).getTemp_healthsuggestlimitedid());
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            return convertView;
        }
    }

}
