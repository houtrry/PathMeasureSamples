package com.houtrry.pathmeasuresamples.component.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.support.annotation.FloatRange;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.houtrry.pathmeasuresamples.utils.LogUtils;

/**
 * @author: houtrry
 * @time: 2018/3/10
 * @desc: ${TODO}
 */

public class UpgradeBoatView2 extends View {

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


    public UpgradeBoatView2(Context context) {
        this(context, null);
    }

    public UpgradeBoatView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpgradeBoatView2(Context context, AttributeSet attrs, int defStyle) {
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
        mWaveCount = Double.valueOf(Math.ceil(mWidth / mWaveWidth * 1.0d)).intValue() + 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.d("===>>>onDraw,,, start");
        calculateWavePath();
        drawWave(canvas);
    }

    public void setWaveHorizontalProgress(float progress) {
        this.waveHorizontalProgress = progress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context);

        mRegion = new Region();
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private Paint mCirclePaint;

    private void initPaint(Context context) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GREEN);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);


    }

    private void drawWave(Canvas canvas) {
//        LogUtils.d("===>>>drawWave,,, start");
        canvas.drawPath(mWavePath, mWavePaint);


//        LogUtils.d("===>>>drawWave, Region, isEmpty: "+mRegion.isEmpty()+", isComplex: "+mRegion.isComplex()+". isRect: "+mRegion.isRect());
        System.out.println("===>>>drawWave, Region, isEmpty: "+mRegion.isEmpty()+", isComplex: "+mRegion.isComplex()+". isRect: "+mRegion.isRect());
        if (mRegion.isRect()) {
            mCurrentX = mRegion.getBounds();
        }
//        LogUtils.d("===>>>drawWave,  rect: "+mCurrentX.right+", boatProgress: "+boatProgress);
        System.out.println("===>>>drawWave,  rect: "+mCurrentX.right+", boatProgress: "+boatProgress);
        canvas.drawCircle(mCurrentX.right, mCurrentX.top, 20, mCirclePaint);

    }

    Rect mCurrentX = new Rect(0, 0, 0,0);
    private void calculateWavePath() {
        LogUtils.d("===>>>calculateWavePath,,, start");
        mWavePath.reset();
        mOffsetX = -waveHorizontalProgress * mWaveWidth * 2;
        mWavePath.moveTo(mOffsetX, mOffsetY);

        for (int i = 0; i < mWaveCount; i++) {
            mWavePath.rQuadTo(mHalfWaveWidth, (i % 2 == 0 ? 1 : -1) * mWaveHeight, mWaveWidth, 0);
        }
        mWavePath.lineTo(mWidth, mHeight);
        mWavePath.lineTo(mOffsetX, mHeight);
        mWavePath.lineTo(mOffsetX, 0);

        float x = mWidth * boatProgress;
        LogUtils.d("===>>> x: "+x+", boatProgress: "+boatProgress);
        clipRegion.set((int) (x - 0.1), 0, (int) x, mHeight);
        mRegion.setPath(mWavePath, clipRegion);
    }

    private Region mRegion;
    private Region clipRegion = new Region();

    public void startWave() {
        ObjectAnimator waveAnimator = ObjectAnimator.ofFloat(this, "waveHorizontalProgress", 0f, 1f);
        waveAnimator.setDuration(2000);
        waveAnimator.setInterpolator(new LinearInterpolator());
        waveAnimator.setRepeatCount(-1);
        waveAnimator.setRepeatMode(ValueAnimator.RESTART);
        waveAnimator.start();

        ObjectAnimator boatAnimator = ObjectAnimator.ofFloat(this, "boatProgress", 0f, 1f);
        boatAnimator.setDuration(16000);
        boatAnimator.setInterpolator(new LinearInterpolator());
        boatAnimator.setRepeatCount(-1);
        boatAnimator.setRepeatMode(ValueAnimator.RESTART);
        boatAnimator.start();
    }

    private float boatProgress = 0f;

    public void setBoatProgress(float progress){
        boatProgress = progress;
//        ViewCompat.postInvalidateOnAnimation(this);
    }
}
