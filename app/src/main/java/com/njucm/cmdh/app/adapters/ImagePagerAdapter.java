/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.njucm.cmdh.app.adapters;

import java.util.List;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.viewpager.viewpager.RecyclingPagerAdapter;

import cn.trinea.android.common.util.ListUtils;


/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;
    private List<Integer> imageIdList;
    private List<String> imageUrlList;

    private int size;
    private boolean isInfiniteLoop;
    DisplayMetrics displayMetrics ;
    private LayoutInflater inflater;

    public ImagePagerAdapter(Context context, List<Integer> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size = ListUtils.getSize(imageIdList);
        isInfiniteLoop = false;
        this.displayMetrics = displayMetrics;
        inflater = LayoutInflater.from(context.getApplicationContext());
    }

    public ImagePagerAdapter(Context context, List<String> imageUrlList, int flag) {
        this.context = context;
        this.size = ListUtils.getSize(imageIdList);
        this.imageUrlList = imageUrlList;
        isInfiniteLoop = false;
        inflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(imageIdList);
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(int position, View view, ViewGroup container) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        holder.imageView.setImageResource(imageIdList.get(getPosition(position)));
        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
