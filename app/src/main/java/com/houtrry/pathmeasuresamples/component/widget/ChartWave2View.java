package com.houtrry.pathmeasuresamples.component.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author: houtrry
 * @time: 2018/3/10
 * @desc: ${TODO}
 */

public class ChartWave2View extends View {

    private static final String TAG = "WaveView";
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
    private PathMeasure mChartPathMeasure;

    private Path mWaveBezierPath = new Path();

    private float chartProgress = 0;

    private Path mDstPath = new Path();
    private Paint mChartPaint;


    public ChartWave2View(Context context) {
        this(context, null);
    }

    public ChartWave2View(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartWave2View(Context context, AttributeSet attrs, int defStyle) {
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
        mWaveCount = Double.valueOf(Math.ceil((mWidth - mOffsetX) / mWaveWidth *1.0d )).intValue();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateWavePath();

        drawWave(canvas);
        drawChart(canvas);
    }

    public void setWaveHorizontalProgress(float progress) {
        this.waveHorizontalProgress = progress;
        postInvalidate();
    }

    public void setChartProgress(float chartProgress) {
        this.chartProgress = chartProgress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initData(context);
        initPaint(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initData(Context context) {
        mChartPathMeasure = new PathMeasure();
    }

    private void initPaint(Context context) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GREEN);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);


        mChartPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mChartPaint.setColor(Color.WHITE);
        mChartPaint.setStyle(Paint.Style.STROKE);
        mChartPaint.setStrokeWidth(10);
        mChartPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void drawWave(Canvas canvas) {
        canvas.drawPath(mWavePath, mWavePaint);
    }

    private void calculateWavePath() {
        mWaveBezierPath.reset();
        mOffsetX = waveHorizontalProgress * mWaveWidth * -1;
        mWavePath.reset();
        mWavePath.moveTo(mOffsetX, mOffsetY);
        Log.d(TAG, "calculateWavePath: mOffsetX: "+mOffsetX+", mOffsetY: "+mOffsetY+", mWaveCount: "+mWaveCount);
        for (int i = 0; i < mWaveCount; i++) {
            mWavePath.rQuadTo(mHalfWaveWidth, (i % 2 == 0?1:-1)*mWaveHeight, mWaveWidth, 0);
        }
        mWaveBezierPath.set(mWavePath);
        mWavePath.lineTo(mOffsetX + mWidth, mHeight);
        mWavePath.lineTo(mOffsetX, mHeight);
        mWavePath.lineTo(mOffsetX, mOffsetY);
    }


    private void drawChart(Canvas canvas) {
        mChartPathMeasure.setPath(mWaveBezierPath, false);

        mDstPath.reset();
        mChartPathMeasure.getSegment(0, mChartPathMeasure.getLength()*chartProgress, mDstPath, true);
        canvas.drawPath(mDstPath, mChartPaint);
    }


    public void startAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "chartProgress", 0.0f, 1.0f);
        objectAnimator.setDuration(6000);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
}
