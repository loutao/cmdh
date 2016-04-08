package com.njucm.cmdh.app.utils;


import android.content.Context;
import android.content.res.Resources;

import com.njucm.cmdh.app.MyApplication;
import com.njucm.cmdh.app.R;

import java.util.Map;

import in.srain.cube.request.CacheAbleRequest;
import in.srain.cube.request.CacheAbleRequestHandler;
import in.srain.cube.request.CacheAbleRequestJsonHandler;
import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;

/**
 * Created by Mesogene on 4/5/15.
 */
public class RequestJsonData {
    MyApplication myApplication;

    public static void getHealthKnowledgeList(final RequestFinishHandler<JsonData> requestFinishHandler, Context context) {
        Resources resources = context.getResources();
        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {
            @Override
            public void onCacheAbleRequestFinish(JsonData jsonData, CacheAbleRequest.ResultType resultType, boolean b) {
                requestFinishHandler.onRequestFinish(jsonData);
            }

        };

        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = resources.getString(R.string.base_url) + "/" + resources.getString(R.string.tcmhealthknowledge_url) + "/";
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.getRequestData().setRequestUrl(url);
//        request.setAssertInitDataPath("request_init/healthknowledge/healthknowledge-list2.json");
//        request.setCacheKey("healthknowledge-list-4");
        request.setDisableCache(true);
        request.send();
    }
/**
 * jeff hahahaha*
 */
    /**
     * @param requestFinishHandler
     * @param context
     * @param sub_url
     * @param initPath
     * @param cacheKey
     */
    public static void getCustomListData(final RequestFinishHandler<JsonData> requestFinishHandler, Context context, String sub_url, String initPath, String cacheKey) {
        Resources resources = context.getResources();
        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {
            @Override
            public void onCacheAbleRequestFinish(JsonData jsonData, CacheAbleRequest.ResultType resultType, boolean b) {
                requestFinishHandler.onRequestFinish(jsonData);
            }
        };

        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = HBContants.BASE_URL + sub_url;
        request.getRequestData().setRequestUrl(url);
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.setAssertInitDataPath(initPath);
        request.setCacheKey(cacheKey);
        request.send();
    }

    public static void getCustomJsonData(Context context, String sub_url, String initPath, String cacheKey) {
        Resources resources = context.getResources();
        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {
            @Override
            public void onCacheAbleRequestFinish(JsonData jsonData, CacheAbleRequest.ResultType resultType, boolean b) {
            }
        };
        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = resources.getString(R.string.base_url) + sub_url;
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.getRequestData().setRequestUrl(url);
        request.setAssertInitDataPath(initPath);
        request.setCacheKey(cacheKey);
        request.send();
    }

    public static void getCustomListDataByJson(final RequestFinishHandler<JsonData> requestFinishHandler, Context context, String sub_url, String initPath, String cacheKey, Map query) {
        Resources resources = context.getResources();
        CacheAbleRequestHandler requestHandler = new CacheAbleRequestJsonHandler() {
            @Override
            public void onCacheAbleRequestFinish(JsonData jsonData, CacheAbleRequest.ResultType resultType, boolean b) {
                requestFinishHandler.onRequestFinish(jsonData);
            }
        };
        CacheAbleRequest<JsonData> request = new CacheAbleRequest<JsonData>(requestHandler);
        String url = HBContants.BASE_URL + sub_url;
        request.getRequestData().setRequestUrl(url).addQueryData(query);
        request.setCacheTime(3600);
        request.setTimeout(1000);
        request.setAssertInitDataPath(initPath);
        request.setCacheKey(cacheKey);
        request.send();
    }

}
