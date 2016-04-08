package com.njucm.cmdh.app.utils;

/**
 * Created by Mesogene on 5/12/15.
 * 用于Dialog 和 父activity之间的传值的回调函数，该方法在父activity中实现，在Dialog中调用
 */
public interface TranslateHelper {
    public void refreshActivity(String text);
}

