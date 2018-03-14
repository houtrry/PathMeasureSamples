package com.houtrry.pathmeasuresamples.component.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.houtrry.pathmeasuresamples.R;
import com.houtrry.pathmeasuresamples.component.widget.TextPathView;

public class TextActivity extends AppCompatActivity {

    private TextPathView mTextPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        mTextPathView = (TextPathView) findViewById(R.id.textPathView);
        mTextPathView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextPathView.startAnimator();
            }
        });

    }
}
