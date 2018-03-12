package com.houtrry.pathmeasuresamples.component.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.houtrry.pathmeasuresamples.R;
import com.houtrry.pathmeasuresamples.component.widget.BoatWaveView;

/**
 * @author houtrry
 * @date 2018/3/10
 */
public class BoatActivity extends AppCompatActivity {

    private BoatWaveView mBoatWaveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boat);

        mBoatWaveView = (BoatWaveView) findViewById(R.id.boatWaveView);

        mBoatWaveView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBoatWaveView.startBoat();
            }
        });

    }
}
