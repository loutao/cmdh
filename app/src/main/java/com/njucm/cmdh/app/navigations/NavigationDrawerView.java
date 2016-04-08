package com.njucm.cmdh.app.navigations;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.adapters.NavigationDrawerAdapter;
import com.njucm.cmdh.app.misc.NavigationDrawerItem;
import com.njucm.cmdh.app.misc.BetterViewAnimator;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.norbsoft.typefacehelper.TypefaceHelper.typeface;

/**
 * Created by Michal Bialas on 19/07/14.
 */
public class NavigationDrawerView extends BetterViewAnimator {

    @InjectView(R.id.leftDrawerListView)
    ListView leftDrawerListView;

    private final NavigationDrawerAdapter adapter;


    public NavigationDrawerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        adapter = new NavigationDrawerAdapter(context);
    }

    /**
     *
     * @param items  左边下拉框中的选项，用NavigationDrawerItem 类填充
     */
    public void replaceWith(List<NavigationDrawerItem> items) {
        adapter.replaceWith(items);
        setDisplayedChildId(R.id.leftDrawerListView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.inject(this);
        leftDrawerListView.setAdapter(adapter);
    }

    public NavigationDrawerAdapter getAdapter() {
        return adapter;
    }
}
