package com.houtrry.pathmeasuresamples.component.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author: houtrry
 * @time: 2018/3/10
 * @desc: ${TODO}
 */

public class WaveView extends View {

    private Paint mWavePaint;
    private Path mWavePath = new Path();
    private int mWidth;
    private int mHeight;
    private float waveHorizontalProgress = 0;

    private float mWaveWidth = 60;
    private float mWaveHeight = 100;

    private float mOffsetX = 0;
    private float mOffsetY = 150;

    private int mWaveCount = 0;
    private float mHalfWaveWidth = 0;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mHalfWaveWidth = mWaveWidth * 0.5f;
        mWaveCount = Double.valueOf(Math.ceil((mWaveWidth + mOffsetX) / mWaveWidth *1.0d )).intValue();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawWave(canvas);
    }

    public void setWaveHorizontalProgress(float progress) {
        this.waveHorizontalProgress = progress;
        postInvalidate();
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initPaint(Context context) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GREEN);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    private void drawWave(Canvas canvas) {
        canvas.drawPath(mWavePath, mWavePaint);
    }

    private void calculateWavePath() {
        mOffsetX = waveHorizontalProgress * mWaveWidth;
        mWavePath.reset();
        mWavePath.moveTo(mOffsetX, mOffsetY);

        for (int i = 0; i < mWaveCount; i++) {
            mWavePath.rQuadTo(mHalfWaveWidth, (i % 2 == 0?1:-1)*mWaveHeight, mWaveWidth, 0);
        }
        mWavePath.lineTo(mOffsetX + mWaveWidth, mHeight);
        mWavePath.lineTo(mOffsetX, mHeight);
        mWavePath.lineTo(mOffsetX, mOffsetY);
    }
}
