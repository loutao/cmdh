package com.njucm.cmdh.app.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.utils.ElementSelectListener;

import java.util.ArrayList;
import java.util.List;


public class ElementSelectActivity extends ActionBarActivity {

    /**
     * The fragment's ListView/GridView.
     */
    private ListView mListView;
    public static ElementSelectListener elementSelectListener = null;
    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private FragmentManager fm;
    private String element = "";
    public void setElementSelectListener(ElementSelectListener elementSelectListener) {
        this.elementSelectListener = elementSelectListener;
    }

    public static ElementSelectActivity getInstance(){
        ElementSelectActivity elementSelectActivity = new ElementSelectActivity();
        return elementSelectActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_element_select);
        mListView = (ListView) findViewById(R.id.elementselectlist);
        mAdapter = new myAdapter(getApplicationContext());
        mListView.setAdapter(mAdapter);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("objbundle");


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position){
                case 0: element = "energy"; break;
                case 1: element = "moisture"; break;
                case 2: element = "protein"; break;
                case 3: element = "fat"; break;
                case 4: element = "fiber"; break;
                case 5: element = "carbohydrate"; break;
                case 6: element = "vitamina"; break;
                case 7: element = "vitaminb1"; break;
                case 8: element = "vitaminb2"; break;
                case 9: element = "vitaminb6"; break;
                case 10: element = "vitaminb12"; break;
                case 11: element = "vitaminc"; break;
                case 12: element = "vitamind"; break;
                case 13: element = "vitamine"; break;
                case 14: element = "vitamink"; break;
                case 15: element = "nicotinicacid"; break;
                case 16: element = "folate"; break;
                case 17: element = "sodium"; break;
                case 18: element = "calcium"; break;
                case 19: element = "iron"; break;
                case 20: element = "potassium"; break;
                case 21: element = "zinc"; break;
                case 22: element = "magnesium"; break;
                case 23: element = "copper"; break;
                case 24: element = "chomuim"; break;
                case 25: element = "mangaesium"; break;
                case 26: element = "molybdenum"; break;
                case 27: element = "iodine"; break;
                case 28: element = "phosphrus"; break;
                case 29: element = "selenium"; break;
                case 30: element = "fluorine"; break;
                case 31: element = "cholesterol"; break;
                case 32: element = "saturated"; break;
                case 33: element = "acidregurgitation"; break;
                case 34: element = "biotin"; break;
                case 35: element = "choline"; break;
                case 36: element = "carotene"; break;
            }
                if(elementSelectListener!=null){
                    elementSelectListener.refreshChart(element);
                }
                finish();
            }
        });
    }

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_element_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ViewHolder{
        private TextView element;
    }

    private class myAdapter extends BaseAdapter {

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
            int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
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
