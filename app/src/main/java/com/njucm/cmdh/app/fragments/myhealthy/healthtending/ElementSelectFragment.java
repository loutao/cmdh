package com.njucm.cmdh.app.fragments.myhealthy.healthtending;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ElementSelectFragment extends Fragment implements AbsListView.OnItemClickListener {

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private FragmentManager fm;


    private List<String> genelementData(){

        List<String> data = new ArrayList<String>();
        data.add(getString(R.string.energy));
        data.add(getString(R.string.water));
        data.add(getString(R.string.protein));
        data.add(getString(R.string.fat));
        data.add(getString(R.string.fiber));
        data.add(getString(R.string.carbohydrate));
        data.add(getString(R.string.vitamina));
        data.add(getString(R.string.vitaminb1));
        data.add(getString(R.string.vitaminb2));
        data.add(getString(R.string.elementen));
        data.add(getString(R.string.vitaminb12));
        data.add(getString(R.string.vitaminc));
        data.add(getString(R.string.vitamind));
        data.add(getString(R.string.vitamine));
        data.add(getString(R.string.vitamink));
        data.add(getString(R.string.nicotinicacid));
        data.add(getString(R.string.folate));
        data.add(getString(R.string.sodium));
        data.add(getString(R.string.calcium));
        data.add(getString(R.string.iron));
        data.add(getString(R.string.potassium));
        data.add(getString(R.string.zinc));
        data.add(getString(R.string.magnesium));
        data.add(getString(R.string.copper));
        data.add(getString(R.string.chomuim));
        data.add(getString(R.string.mangaesium));
        data.add(getString(R.string.molybdenum));
        data.add(getString(R.string.iodine));
        data.add(getString(R.string.phosphrus));
        data.add(getString(R.string.selenium));
        data.add(getString(R.string.fluorine));
        data.add(getString(R.string.cholesterol));
        data.add(getString(R.string.saturated));
        data.add(getString(R.string.acidregurgitation));
        data.add(getString(R.string.biotin));
        data.add(getString(R.string.choline));
        data.add(getString(R.string.carotene));
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mAdapter = new ArrayAdapter(getActivity(),
//               R.layout.element_list_item, R.id.tv, getData());
        mAdapter = new myAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_element_select, container, false);
        // Set the adapter
        mListView = (ListView) view.findViewById(R.id.elementselectlist);
        mListView.setAdapter(mAdapter);
        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
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
            fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("element_number", position);
            SportTendingFragment sf = new SportTendingFragment();
            sf.setArguments(bundle);
            ft.replace(R.id.container, sf);
            ft.addToBackStack(null);
            ft.commit();
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

    private class ViewHolder{
        private TextView element;
    }

    private class myAdapter extends BaseAdapter{

        private Context context;

        private myAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return genelementData().size();
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
            ViewHolder viewHolder;
            int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
            ListView.LayoutParams lp = new ListView.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, screenHeight/12);
            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.element_list_item, null);
                viewHolder.element = (TextView) convertView.findViewById(R.id.tv);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            try {
                viewHolder.element.setText(genelementData().get(position));
                convertView.setLayoutParams(lp);
                if(position%2 == 0){  //设置隔行
                    convertView.setBackgroundColor(Color.rgb(224, 224, 224));
                }else{
                    convertView.setBackgroundColor(Color.rgb(208, 208, 208));
                }
            } catch (Exception e) {
                Log.d("ElementSelectFragment", "viewHolderError");
            }
            return convertView;
        }
    }

}
