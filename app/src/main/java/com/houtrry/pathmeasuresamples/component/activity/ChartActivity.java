package com.houtrry.pathmeasuresamples.component.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.houtrry.pathmeasuresamples.R;
import com.houtrry.pathmeasuresamples.component.widget.ChartWaveView;

/**
 * @author houtrry
 * @date 2018/3/10
 */
public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        ChartWaveView chartWaveView = (ChartWaveView) findViewById(R.id.chartWaveView);
        chartWaveView.startAnimator();
    }
}
