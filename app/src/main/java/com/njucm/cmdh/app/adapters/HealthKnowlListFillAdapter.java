package com.njucm.cmdh.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.domain.TCMHealthknowledge;
import com.norbsoft.typefacehelper.TypefaceCollection;

import java.util.List;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Created by Mesogene on 3/5/15.
 * 健康知识适配器
 */
public class HealthKnowlListFillAdapter extends BaseAdapter {
    /**
     * 标题的item
     */
    public static final int ITEM_TITLE = 1;
    /**
     * 二级菜单的item
     */
    public static final int ITEM_INTRODUCE = 2;
    private List<TCMHealthknowledge> TCMHealthknowledgeList;
    private Context context;
    private int[] imagelist = {R.drawable.banner1,R.drawable.banner2,R.drawable.banner3,R.drawable.banner4};

    private LayoutInflater inflater;

    TypefaceCollection mytypeface;

    // 两个样式 两个holder。100就写100holder。。当然你何以把他抽离出来这里先只为了说明问题
    class Holder1 {
        TextView play_title;

        Holder1(View view) {
            play_title = (TextView) view.findViewById(R.id.healtnknol__kind_title);
        }

    }

    class Holder2 {
        TextView play_introduce_title, healtnknol_item_detail;
        ImageView play_iv;

        Holder2(View view) {
            healtnknol_item_detail = (TextView) view.findViewById(R.id.healtnknol_item_detail);
            play_introduce_title = (TextView) view
                    .findViewById(R.id.play_introduce_title);
            play_iv = (ImageView) view.findViewById(R.id.healtnknol_content_image);
        }
    }

    public HealthKnowlListFillAdapter(Context context, List<TCMHealthknowledge> mList) {
        this.context = context;
        this.TCMHealthknowledgeList = mList;
        inflater = LayoutInflater.from(context);
        mytypeface = ((MyApplication) context).getMyRobototypeface();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return TCMHealthknowledgeList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return TCMHealthknowledgeList.get(arg0);
    }

    //返回 代表某一个样式 的 数值
    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 1;
    }

    //两个样式 返回2
    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = Integer.parseInt(TCMHealthknowledgeList.get(position).getHealthknowledgeremarks());

        Holder1 holder1 = null;
        Holder2 holder2 = null;
        if (convertView == null) {
            //选择某一个样式。。
            switch (type) {
                case ITEM_INTRODUCE:
                    convertView = inflater.inflate(R.layout.healthknowl_item_title_two,
                            null);
                    holder2 = new Holder2(convertView);

                    holder2.play_introduce_title
                            .setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());
                   holder2.play_iv.setImageResource(imagelist[position%4]);
                    holder2.healtnknol_item_detail.setText(TCMHealthknowledgeList.get(position).getHealthknowledgecontent().substring(0,20));
                    convertView.setTag(holder2);
                    break;

                case ITEM_TITLE:
                    if (TCMHealthknowledgeList.get(position).getHealthknowledgeid() == 2) {
                        convertView = inflater.inflate(R.layout.healthknowl_item_title_two,
                                null);
                        holder2 = new Holder2(convertView);

                        holder2.play_introduce_title
                                .setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());
                        convertView.setTag(holder2);
                        break;
                    }

                    convertView = inflater.inflate(R.layout.healthknowl_item_title_one, null);
                    holder1 = new Holder1(convertView);

                    holder1.play_title.setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());

                    convertView.setTag(holder1);
                    break;

                default:
                    break;
            }

        } else {
            switch (type) {
                case ITEM_INTRODUCE:
                    holder2 = (Holder2) convertView.getTag();
                    holder2.play_introduce_title
                            .setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());
                    holder2.healtnknol_item_detail.setText(TCMHealthknowledgeList.get(position).getHealthknowledgecontent().substring(0,20));
                    break;
                case ITEM_TITLE:
                    if (TCMHealthknowledgeList.get(position).getHealthknowledgeid() == 2) {
                        holder2 = (Holder2) convertView.getTag();
                        holder2.play_introduce_title
                                .setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());
                        break;
                    }
                    holder1 = (Holder1) convertView.getTag();
                    holder1.play_title.setText(TCMHealthknowledgeList.get(position).getHealthknowledgetitle());

                    break;

                default:
                    break;
            }

        }
        typeface(convertView);
        return convertView;
    }
}
