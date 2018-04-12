package com.houtrry.pathmeasuresamples.component.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.houtrry.pathmeasuresamples.R;
import com.houtrry.pathmeasuresamples.component.widget.UpgradeBoatView;
import com.houtrry.pathmeasuresamples.component.widget.UpgradeBoatView2;

public class UpgradeBoatActivity extends AppCompatActivity {

    private UpgradeBoatView mUpgradeBoatView;
    private UpgradeBoatView2 mUpgradeBoatView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade_boat);

        mUpgradeBoatView = (UpgradeBoatView) findViewById(R.id.boatWaveView);
        mUpgradeBoatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpgradeBoatView.startWave();
            }
        });

        mUpgradeBoatView2 = (UpgradeBoatView2) findViewById(R.id.boatWaveView2);
        mUpgradeBoatView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpgradeBoatView2.startWave();
            }
        });
    }
}
