package com.njucm.cmdh.app.navigations;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.FontAwesomeText;
import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.misc.NavigationDrawerItem;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Created by Michal Bialas on 19/07/14.
 */
public class NavigationDrawerItemView extends RelativeLayout {

    @InjectView(R.id.itemRR)
    RelativeLayout rr;

    @InjectView(R.id.navigationDrawerItemTitleTV)
    TextView itemTitleTV;

    @InjectView(R.id.navigationDrawerItemIconIV)
    ImageView itemIconIV;

    @InjectView(R.id.navigationDrawerItemIconMain)
    FontAwesomeText itemIconBS;


    final Resources res;


    public NavigationDrawerItemView(Context context) {
        super(context);
        res = context.getResources();

    }

    public NavigationDrawerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        res = context.getResources();
    }

    public NavigationDrawerItemView(Context context, AttributeSet attrs,
                                    int defStyle) {
        super(context, attrs, defStyle);
        res = context.getResources();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        typeface(itemTitleTV);
    }

    public void bindTo(NavigationDrawerItem item) {
        requestLayout();
        if (item.isMainItem()) {
            itemTitleTV.setText(item.getItemName());
            itemIconBS.setIcon(item.getItemIconBootstrap());
            itemIconBS.setTextColor(item.getItemIconColor());
            itemIconBS.setVisibility(View.VISIBLE);
            itemIconIV.setVisibility(View.GONE);
        } /*else {
            itemTitleTV.setText(item.getItemName());
            itemTitleTV.setTextSize(14);
            itemIconIV.setImageDrawable(getIcon(item.getItemIconImage()));
            itemIconIV.setVisibility(View.VISIBLE);
            itemIconBS.setVisibility(View.GONE);
            rr.setBackgroundColor(res.getColor(R.color.grey_background));
        }*/

        if (item.isSelected()) {
//            itemTitleTV.setTypeface(null, Typeface.NORMAL);
        } else {

        }

    }

    private Drawable getIcon(int res) {
        return getContext().getResources().getDrawable(res);
    }

}
