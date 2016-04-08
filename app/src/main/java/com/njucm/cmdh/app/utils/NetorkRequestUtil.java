package com.njucm.cmdh.app.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mesogene on 3/31/15.
 */
public class NetorkRequestUtil {

    HttpResponse response;
    StringBuffer sb;

    public void post2DjangoServer(){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("10.120.59.9:81/");

        //添加你的请求数据
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("scoreone", "87"));
        nameValuePairs.add(new BasicNameValuePair("scoretwo", "67"));
        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //执行HTTP POST请求
        try {
            response = httpclient.execute(httppost);
            BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while((line = in.readLine())!= null){
                sb.append(line + NL);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();

    }


}


