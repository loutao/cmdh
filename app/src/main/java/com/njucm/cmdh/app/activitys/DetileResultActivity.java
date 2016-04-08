package com.njucm.cmdh.app.activitys;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.njucm.cmdh.app.R;


public class DetileResultActivity extends ActionBarActivity {
    WebView chromeView;
    Bundle bundle;
    TextView tv_healthknowlcontent;
    TextView tv_healthknowltitle;
    TextView tv_exersuggtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detile_result);
        tv_healthknowlcontent = (TextView) findViewById(R.id.tv_healthknowlcontent);
        tv_healthknowltitle = (TextView) findViewById(R.id.tv_healthknowltitle);
        tv_exersuggtime = (TextView) findViewById(R.id.tv_exersuggtime);
        bundle = getIntent().getBundleExtra("bundle");
        setTitle(bundle.getString("healthknowledge_title"));
        tv_healthknowltitle.setText(bundle.getString("healthknowledge_title"));
        tv_healthknowlcontent.setText(bundle.getString("healthknowledge_content"));
        tv_exersuggtime.setText(bundle.getString("exersuggtime"));
        getActionBar().setDisplayHomeAsUpEnabled(true);
       /* chromeView = (WebView) findViewById(R.id.detile_result_webview);
        chromeView.getSettings().setJavaScriptEnabled(true);
        chromeView.loadUrl("http://10.120.59.14:63343/healthknowledge/html/healthknowldetailpage.html");
        chromeView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Toast.makeText(getApplicationContext(), newProgress + "", Toast.LENGTH_SHORT).show();
                super.onProgressChanged(view, newProgress);
            }

        });
        
        chromeView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            //        loadurl(chromeView, "http://www.baidu.com");
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), errorCode + " " + description, Toast.LENGTH_SHORT).show();
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });*/

        // Fetch and print a list of the contributors to this library.

      /*  for (Tcmhealthknowledge tcmhealthknowledge : tcmhealthknowledges) {
            System.out.println(tcmhealthknowledges.get(0));
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detile_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
