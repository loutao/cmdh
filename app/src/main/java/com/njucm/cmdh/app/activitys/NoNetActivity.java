package com.njucm.cmdh.app.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.njucm.cmdh.app.R;
import com.njucm.cmdh.app.utils.NetHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoNetActivity extends ActionBarActivity {
    @InjectView(R.id.wraningtext)
    TextView warningtext;
    private int number;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_net);
        context = getApplicationContext();
        ButterKnife.inject(this);
        final Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("number");
        number = bundle.getInt("number");
        final Intent intent2 = new Intent();
        warningtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (number){
                    case 1: if(NetHelper.isHaveInternet(context)){
                        intent2.setClass(getApplicationContext(), ConstitutionActivity.class);
                        startActivity(intent2);finish();
                    }
                    break;
                    case 2: if(NetHelper.isHaveInternet(context)){
                        intent2.setClass(getApplicationContext(), HealthSuggestionsActivity.class);
                        startActivity(intent2);finish();
                    }
                        break;
                    case 3: if(NetHelper.isHaveInternet(context)){
                        intent2.setClass(getApplicationContext(), MyCollectionActivity.class);
                        startActivity(intent2);finish();
                    }
                        break;
                    case 4: if(NetHelper.isHaveInternet(context)){
                        intent2.setClass(getApplicationContext(), HealthTendingActivity.class);
                        startActivity(intent2);finish();
                    }
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_net, menu);
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
}
