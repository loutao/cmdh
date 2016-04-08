package com.njucm.cmdh.app.fragments.myhealthy.healthwarning;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.njucm.cmdh.app.R;

import com.njucm.cmdh.app.activitys.MainActivity;
import com.njucm.cmdh.app.fragments.BaseFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthsuggestion.DietSuggestionFragment;
import com.njucm.cmdh.app.fragments.myhealthy.healthwarning.dummy.DummyContent;
import com.njucm.cmdh.app.utils.PopService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class HealthWarningFragment extends ListFragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Context context;

    private List<Map<String, Object>> mData;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static HealthWarningFragment newInstance(String param1, String param2) {
        HealthWarningFragment fragment = new HealthWarningFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HealthWarningFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
        Intent startintent = new Intent(getActivity(), PopService.class);
        context.startService(startintent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_healthwarning, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        context = getActivity().getApplicationContext();
        mData = getData();
        setListAdapter(new HealthwarningAdapter(context));
        return view;
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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();


        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", "您的饮食很不规律哦，请注意啦！");
        map.put("img", R.drawable.yangxuzhi);
        map.put("info", "专家认为，科学饮食应结构均衡、营养平衡，其蛋白质、脂肪、碳水化合物的比例要合理。碳水化合物提供的能量应占每天能量消耗的60%左右，脂肪占25%左右，蛋白质占15%左右。由此可见，碳水化合物在其中所占的比例是最大的。人们每顿都应补充粮谷类食物，早30%，晚30%，中午可以多补充一点，40%左右。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量有点少！");
        map.put("img", R.drawable.yangxuzhi);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量有点少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您的饮食有点不规律哦！");
        map.put("img", R.drawable.yangxuzhi);
        map.put("info", "专家认为，科学饮食应结构均衡、营养平衡，其蛋白质、脂肪、碳水化合物的比例要合理。碳水化合物提供的能量应占每天能量消耗的60%左右，脂肪占25%左右，蛋白质占15%左右。由此可见，碳水化合物在其中所占的比例是最大的。人们每顿都应补充粮谷类食物，早30%，晚30%，中午可以多补充一点，40%左右。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的睡眠时间太短啦！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "睡眠周期是由大脑控制的，随着年龄的增长而发生某种变化;同时发现，午休是自然睡眠周期的一个部分。佛罗里达大学的一位睡眠研究专家说，午休已经逐渐演化成为人类自我保护的方式。最初，午休可能只是人们为了躲避正午的烈日，后来逐渐变成一种习惯;那时的人类是生活在暖热的地区，户外劳动是人们维持生存最基本的条件。因此午休成为人们避免遭受热浪袭击的方法。\n" +
                "　　在上午9时、中午1时和下午5时，有3个睡眠高峰，尤其是中午1时的高峰较明显。也就是说，人除了夜间睡眠外，在白天有一个以4小时为间隔的睡眠节律。午休是正常睡眠和清醒的生物节律的表现规律，是保持清醒必不可少的条件。不少人，尤其是脑力劳动者都会体会到，午休后工作效率会大大提高。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您的饮食很不规律哦，请注意啦！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "专家认为，科学饮食应结构均衡、营养平衡，其蛋白质、脂肪、碳水化合物的比例要合理。碳水化合物提供的能量应占每天能量消耗的60%左右，脂肪占25%左右，蛋白质占15%左右。由此可见，碳水化合物在其中所占的比例是最大的。人们每顿都应补充粮谷类食物，早30%，晚30%，中午可以多补充一点，40%左右。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您的饮食有点不规律哦！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "专家认为，科学饮食应结构均衡、营养平衡，其蛋白质、脂肪、碳水化合物的比例要合理。碳水化合物提供的能量应占每天能量消耗的60%左右，脂肪占25%左右，蛋白质占15%左右。由此可见，碳水化合物在其中所占的比例是最大的。人们每顿都应补充粮谷类食物，早30%，晚30%，中午可以多补充一点，40%左右。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量过少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量过少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量过少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量过少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);

        map = new HashMap<String, Object>();
        map.put("title", "您近期的运动量过少！");
        map.put("img", R.drawable.pinghezhiimg);
        map.put("info", "适时：恶劣天气，极冷极热的环境里不适应运动，最佳的运动时间是傍晚太阳落山之后。\n" +
                "适度：运动量过大，可能引起呼吸困难、胸闷气短甚至有猝死风险。运动量过小，则起不到锻炼的效果，因此要把握一个度。一般健康人群每周应至少进行150分钟的体育锻炼，如散步或骑自行车。最理想的运动量为每天最少30分钟的中等强度锻炼。有氧运动前30分钟一般消耗的是糖分和热量，30分钟之后，身体才会逐渐消耗脂肪。如果女孩希望运动减肥，那么每次30分钟以上的锻炼时间是必不可少的，运动强度可根据自身情况控制。运动前要热身，运动过后要注意保暖补水，适当休息。因人而异：体质较弱的人、患有疾病的人、年幼年迈的人、处于特殊生理期(女性经期、哺乳期)的人就不要进行剧烈的运动。提倡进行有一定持续性、舒缓的运动方式。");
        list.add(map);
        return list;
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

    public final class ViewHolder{
        public TextView title;
        public TextView content;
        public View line;
    }

    class HealthwarningAdapter extends BaseAdapter{

        private LayoutInflater flater;

        HealthwarningAdapter(Context context) {
            this.flater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mData.size();
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
            ViewHolder holder = null;
            if(convertView == null){
                try {
                    holder = new ViewHolder();
                    convertView = flater.inflate(R.layout.healthwarning_list_item, null);
                    holder.title = (TextView)convertView.findViewById(R.id.title);
                    holder.content = (TextView)convertView.findViewById(R.id.content);
//                    holder.line = convertView.findViewById(R.id.line1);
                    convertView.setTag(holder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.title.setText((String) mData.get(position).get("title"));
            if(mData.get(position).get("info").toString().length()>100){
                holder.content.setText(((String)mData.get(position).get("info")).substring(0, 100)+"...");
            }
            return convertView;
        }
    }

}
