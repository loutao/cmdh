package com.njucm.cmdh.app.fragments.myhealthy;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.MyConstitution;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.utils.RequestJsonData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.views.list.ListViewDataAdapter;
import in.srain.cube.views.list.ViewHolderBase;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit.http.GET;

import static com.njucm.cmdh.app.utils.ImageOptimizing.decodeSampledBitmapFromResource;

public class MycontitutehistoryFragment extends BaseFragment{


    private List<Map<String, Object>> mData;

    private Context context;


    private myConstitutionhistory myconstitutionAdapter;
    private ListViewDataAdapter<JsonData> mAdapter;


    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private inf_TcmMycontitution inf_tcmMycontitution;
    private List<MyConstitution> myconstitution = new ArrayList<MyConstitution>();
    private ListView actualListView;
    private ImageLoader mImageLoader;
    private ListView listView;
    private PtrClassicFrameLayout mPtrFrame;

    interface inf_TcmMycontitution{
        @GET("/")
        List<MyConstitution> getTcmmyconstitutionList();
    }



    public MycontitutehistoryFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mData = getData();
        //设置适配器

        mAdapter = new ListViewDataAdapter<JsonData>();
        mAdapter.setViewHolderClass(this, ViewHolder.class);
        listView.setAdapter(mAdapter);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
//        RestAdapter restAdapter = new RestAdapter.Builder()
//                .setErrorHandler(new ExceptionNetwork(this.context))
//                .setConverter(new GsonConverter(gson))
//                .setEndpoint("http://10.120.59.9:81/tbconstituteidentifyresult")
//                .build();
//        inf_tcmMycontitution = restAdapter.create(inf_TcmMycontitution.class);
//        new Thread(getData).start();

    }

    Runnable getData = new Runnable() {
        @Override
        public void run() {
            try {
                myconstitution = inf_tcmMycontitution.getTcmmyconstitutionList();
            } catch (Exception e) {
                Log.d("MyConstitutionFragment", "我的体质历史请求出错"+e.getMessage());
            }
            Message msg = new Message();
            Bundle data = new Bundle();
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//            myconstitutionAdapter = new myConstitutionhistory(context, myconstitution);
//            SimpleAdapter sa = new SimpleAdapter(getActivity().getApplicationContext(), getData(), R.layout.myhistoryitem,
//                    new String[]{"img", "title", "info"},
//                    new int[]{R.id.historyimg, R.id.historytitle, R.id.historyintro});
//            setListAdapter(sa);

//            actualListView.setAdapter(myconstitutionAdapter);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        context = getActivity();
//        dbHelper = DBHelper.getInstance(this.context, HBContants.DATABASE_NAME_HealthKnow);
        final View contentView = inflater.inflate(R.layout.fragment_constitutionhistory, null);

        mImageLoader = ImageLoaderFactory.create(context);
        listView = (ListView) contentView.findViewById(R.id.constitutionhistory_listview);
        mPtrFrame = (PtrClassicFrameLayout) contentView.findViewById(R.id.customlist_frame);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Toast.makeText(getActivity(), "text", Toast.LENGTH_LONG);
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    MyConstitutionFragment myf = new MyConstitutionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("constitutionType",myconstitution.get(position).getConstituteidentifyresult().toString());
//                    bundle.putString("testTime",myconstitution.get(position).getConstituteidentifytime().toString());
                    bundle.putString("info",myconstitution.get(position).getConstituteidentifyremarks());
                    myf.setArguments(bundle); //把消息传递给myf
                    ft.replace(R.id.contitutecontent, myf);
                    ft.addToBackStack(null);
                    ft.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return contentView;
    }

    protected void updateData() {

        RequestJsonData.getCustomListData(new RequestFinishHandler<JsonData>() {
            @Override
            public void onRequestFinish(final JsonData data) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.getDataList().clear();
                        mAdapter.getDataList().addAll(data.toArrayList());
                        mPtrFrame.refreshComplete();
                        mAdapter.notifyDataSetChanged();
                    }
                }, 0);
            }
        }, this.context, "/tbconstituteidentifyresult?constituteldentifyflag=1", "request_init/healthknowledge/myconstitutionhistory.json", "myconstitutionhistory");
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



    //添加数据
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for(MyConstitution mycons:myconstitution){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", mycons.getConstituteidentifyresult());
            map.put("img", R.drawable.yangxuzhi);
            if(mycons.getConstituteidentifyremarks().length() > 6)
                map.put("info", mycons.getConstituteidentifyremarks().substring(0, 7)+"...");
            list.add(map);
        }

        return list;
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this methodsimple
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
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
        public void onFragmentInteraction(String id);
    }




    class myConstitutionhistory extends BaseAdapter{

        private LayoutInflater mInflater;
        private List<MyConstitution> myconstitution;
        private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

        public myConstitutionhistory(Context context, List<MyConstitution> myconstitution) {
            this.mInflater = LayoutInflater.from(context);
            this.myconstitution = myconstitution;
        }

        @Override
        public int getCount() {
            return myconstitution.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public final class ViewHolder {
            //            public ImageView img;
            public TextView title;
            public TextView info;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null || position < myconstitution.size()) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.myhistoryitem, null);
//                holder.img = (ImageView) convertView.findViewById(R.id.historyimg);
//                holder.title = (TextView) convertView.findViewById(R.id.historytitle);
                holder.info = (TextView) convertView.findViewById(R.id.historyintro);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            String time = format.format(myconstitution.get(myconstitution.size() - position).getConstituteidentifytime());
//            holder.img.setImageResource((Integer) mData.get(position).get("img"));
            holder.title.setText(myconstitution.get(myconstitution.size() - position).getConstituteidentifyresult());
            holder.info.setText(time);

            return convertView;
        }

//        private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource){
//            View v;
//            if(convertView == null){
//                v = mInflater.inflate(resource, parent, false);
//            }else{
//                v = convertView;
//            }
//            bindView(position, v);
//            return v;
//        }
//
//        public void setDropDownViewResource(int resource){
//
//        }
    }

    private class ViewHolder extends ViewHolderBase<JsonData> {

        private ImageView mImageView;
        private TextView title, info, time;
        private DateFormat format2= new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        private int[] colors = {R.color.E0E0E0, R.color.F0F0F0};

        @Override
        public View createView(LayoutInflater inflater) {
            View view = inflater.inflate(R.layout.myhistoryitem, null);
            mImageView = (ImageView) view.findViewById(R.id.historyimg);
            info = (TextView) view.findViewById(R.id.historyintro);
            time = (TextView) view.findViewById(R.id.myconstitutiontime);
            return view;
        }

        @Override
        public void showData(int position, JsonData itemData) {
            if(itemData.optString("constituteidentifyremarks").length() > 6){
                info.setText(itemData.optString("constituteidentifyremarks").substring(0, 9)+"...");
            }
            time.setText(itemData.optString("constituteidentifytime"));
//            mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.pinghe, 60, 60));


            MyConstitution my = new MyConstitution();
            my.setConstituteidentifyresult(itemData.optString("constituteidentifyresult"));
            my.setConstituteidentifyremarks(itemData.optString("constituteidentifyremarks"));
            if(itemData.optString("constituteidentifyresult").equals("气虚质") ){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.qixu2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("平和质"))
            {
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.pinghe2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("阴虚质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.yinxu2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("阳虚质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.yangxu2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("痰湿质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tanshi2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("湿热质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.shire2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("气郁质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.qixu2, 60, 60));
            }else if(itemData.optString("constituteidentifyresult").equals("血淤质")){
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.xueyu2, 60, 60));
            }else{
                mImageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.tebing2, 60, 60));
            }

//            try {
//                my.setConstituteidentifytime(format2.parse(itemData.optString("constituteidentifytime")));
//            } catch (ParseException e) {
//                Log.i("MyconstitutionFragment", "我的体质日期转换出错"+e.getMessage());
//            }
            myconstitution.add(my);

        }
    }

}
