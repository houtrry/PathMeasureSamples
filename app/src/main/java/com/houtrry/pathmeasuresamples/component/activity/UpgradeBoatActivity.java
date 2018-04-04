package com.houtrry.pathmeasuresamples.component.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.houtrry.pathmeasuresamples.R;
import com.houtrry.pathmeasuresamples.component.widget.UpgradeBoatView;

public class UpgradeBoatActivity extends AppCompatActivity {

    private UpgradeBoatView mUpgradeBoatView;

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
    }
}
