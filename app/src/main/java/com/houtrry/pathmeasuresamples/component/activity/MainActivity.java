package com.houtrry.pathmeasuresamples.component.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.houtrry.pathmeasuresamples.R;

/**
 * @author houtrry
 * @date 2018/3/10
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.sample_boat).setOnClickListener(this);
        findViewById(R.id.sample_chart).setOnClickListener(this);
        findViewById(R.id.sample_text).setOnClickListener(this);
        findViewById(R.id.upgrade_boat).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sample_boat:{
                toAty(BoatActivity.class);
                break;
            }
            case R.id.sample_chart:{
                toAty(ChartActivity.class);
                break;
            }
            case R.id.sample_text:{
                toAty(TextActivity.class);
                break;
            }
            case R.id.upgrade_boat:{
                toAty(UpgradeBoatActivity.class);
                break;
            }
            default:
                break;
        }
    }

    private void toAty(Class<? extends Activity> cls) {
        final Intent intent = new Intent(MainActivity.this, cls);
        MainActivity.this.startActivity(intent);
    }
}
