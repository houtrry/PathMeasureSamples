package com.houtrry.pathmeasuresamples.component.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author: houtrry
 * @time: 2018/3/10
 * @desc: ${TODO}
 */

public class WaveView extends View {

    private static final String TAG = "WaveView";
    private Paint mWavePaint;
    private Path mWavePath = new Path();
    private int mWidth;
    private int mHeight;
    private
    @FloatRange(from = 0.0f, to = 1.0f)
    float waveHorizontalProgress = 0.5001f;

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
        mWaveCount = Double.valueOf(Math.ceil(mWidth / mWaveWidth * 1.0d)).intValue();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateWavePath();
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

    private float mCurrentX = 0;
    private float mCurrentY = 0;
    private float mStartY = 0;


    private void calculateWavePath() {
        mOffsetX = waveHorizontalProgress * mWaveWidth * 2;
        mWavePath.reset();
        mWavePath.moveTo(mOffsetX, mOffsetY);


        mCurrentX = 0;
        mStartY = mCurrentY = (float) (mWaveHeight * Math.sin(mOffsetX / mWaveWidth * Math.PI)) + mOffsetY;

        mWavePath.moveTo(mCurrentX, mCurrentY);

        float controlX = 0;
        float controlY = 0;
        boolean hasRunHalf = waveHorizontalProgress > 0.5f;
        if (hasRunHalf) {
            controlX = mOffsetX - mHalfWaveWidth * 3;
            controlY = mOffsetY - mWaveHeight;
        } else {
            controlX = mOffsetX - mHalfWaveWidth;
            controlY = mOffsetY + mWaveHeight;
        }
        mWavePath.quadTo(controlX, controlY, mOffsetX, mOffsetY);

//        waveHorizontalProgress:0.5,  mOffsetX: 60.0,      mOffsetY: 150.0, controlX: 30.0,      controlY: 250.0, mCurrentX: 0.0 mCurrentY: 150.0
//        waveHorizontalProgress:0.51, mOffsetX: 61.199997, mOffsetY: 150.0, controlX: 16.199997, controlY: 50.0,  mCurrentX: 0.0 mCurrentY: 143.72095

//        waveHorizontalProgress:0.51, mOffsetX: 61.199997, mOffsetY: 150.0, controlX: -28.800003, controlY: 50.0, mCurrentX: 0.0 mCurrentY: 143.72095

//        waveHorizontalProgress:0.5,  mOffsetX: 60.0,      mOffsetY: 150.0, controlX: 30.0,       controlY: 250.0,mCurrentX: 0.0 mCurrentY: 150.0
//        waveHorizontalProgress:0.5001, mOffsetX: 60.012,  mOffsetY: 150.0, controlX: -29.987999, controlY: 50.0, mCurrentX: 0.0 mCurrentY: 149.93716



        Log.d(TAG, "calculateWavePath: waveHorizontalProgress:"+waveHorizontalProgress+", mOffsetX: " + mOffsetX + ", mOffsetY: " + mOffsetY + ", controlX: " + controlX+", controlY: "+controlY+", mCurrentX: "+mCurrentX+" mCurrentY: "+mCurrentY);
        for (int i = 0; i < mWaveCount; i++) {
            mCurrentX += mWaveWidth;
            if (mCurrentX <= mWidth) {
                mWavePath.rQuadTo(mHalfWaveWidth, (hasRunHalf?1:-1)*(i % 2 == 0?1:-1)*mWaveHeight, mWaveWidth, 0);
            } else {
                mWavePath.quadTo(mCurrentX - mHalfWaveWidth, mOffsetY + (hasRunHalf?1:-1)*(i % 2 == 0?1:-1)*mWaveHeight, mWidth,
                        (float) (mWaveHeight * Math.sin((-mOffsetX + 2 * mWaveWidth + mWidth) / mWaveWidth * Math.PI)));
            }

        }
        mWavePath.lineTo(mWidth, mHeight);
        mWavePath.lineTo(0, mHeight);
        mWavePath.lineTo(0, mStartY);
    }

    private float calculateCurrentY(float currentX, float waveHorizontalProgress) {

        if (currentX == 0) {
            if (waveHorizontalProgress < 0.5f) {

            } else {

            }
        }

        return 0;
    }

    public void startWave() {
        ObjectAnimator waveAnimator = ObjectAnimator.ofFloat(this, "waveHorizontalProgress", 0f, 1f);
        waveAnimator.setDuration(2000);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.setRepeatCount(-1);
        waveAnimator.setRepeatMode(ValueAnimator.RESTART);
        waveAnimator.start();
    }
}
