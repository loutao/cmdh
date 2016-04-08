package com.njucm.cmdh.app.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.analytics.containertag.proto.MutableServing;
import com.google.gson.Gson;
import com.google.tagmanager.proto.Resource;
import com.njucm.cmdh.app.CustomException.ExceptionNetwork;
import com.njucm.cmdh.app.MyApplication;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.internal.http.OkHeaders;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Mesogene on 4/2/15.
 */
public class ServiceGenerator {
    static SharedPreferences sp;
    public static <T> T createService(Class<T> serviceClass, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder().
                setEndpoint(baseUrl).
                setClient(new OkClient(new OkHttpClient()));
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }

    /**
     * @param serviceClass 接口类的类名
     * @param baseUrl      域名
     * @param gson
     * @param context
     * @param <T>
     * @return
     */
    public static <T> T createAuthService(Class<T> serviceClass, String baseUrl, Gson gson, Context context) {
        sp = context.getSharedPreferences("user_config",Context.MODE_PRIVATE);
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Authorization", "Token "+sp.getString("auth_token",""));
            }
        };
        
        RestAdapter.Builder builder = new RestAdapter.Builder().
                setConverter(new GsonConverter(gson)).setLogLevel(RestAdapter.LogLevel.FULL).
                setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL).
                setErrorHandler(new ExceptionNetwork(context)).setRequestInterceptor(requestInterceptor).
                setClient(new OkClient(new OkHttpClient()));
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);

    }
    public static <T> T createService(Class<T> serviceClass, String baseUrl, Gson gson, Context context) {
        RestAdapter.Builder builder = new RestAdapter.Builder().
                setConverter(new GsonConverter(gson)).setLogLevel(RestAdapter.LogLevel.FULL).
                setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL).
                setErrorHandler(new ExceptionNetwork(context)).
                setClient(new OkClient(new OkHttpClient()));
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);

    }

    public static <T> T createService(Class<T> serviceClass, String baseUrl, Gson gson) {
        RestAdapter.Builder builder = new RestAdapter.Builder().
                setConverter(new GsonConverter(gson)).
                setEndpoint(baseUrl).setLogLevel(RestAdapter.LogLevel.FULL).

                setClient(new OkClient(new OkHttpClient()));
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);

    }
}
