package com.houtrry.pathmeasuresamples.component.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.houtrry.pathmeasuresamples.R;

/**
 * @author: houtrry
 * @time: 2018/3/10
 * @desc: ${TODO}
 */

public class BoatWaveView extends View {

    private static final String TAG = "WaveView";
    private Paint mWavePaint;
    private Path mWavePath = new Path();
    private int mWidth;
    private int mHeight;
    private float waveHorizontalProgress = 0;

    private float mWaveWidth = 160;
    private float mWaveHeight = 240;

    private float mOffsetX = 0;
    private float mOffsetY = 250;

    private int mWaveCount = 0;
    private float mHalfWaveWidth = 0;
    private PathMeasure mPathMeasure;
    private Bitmap mBitmap;

    private float boatHorizontalProgress = 0;
    private Matrix mBoatMatrix = new Matrix();
    private Paint mBoatPaint;

    private Path mBoatPath = new Path();

    public BoatWaveView(Context context) {
        this(context, null);
    }

    public BoatWaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoatWaveView(Context context, AttributeSet attrs, int defStyle) {
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
        canvas.save();
        canvas.clipRect(0, 0, mWidth, mHeight);
        calculateWavePath();
        calculateBoatPosition();
        drawWave(canvas);
        drawBoat(canvas);
        canvas.restore();
    }

    public void setWaveHorizontalProgress(float progress) {
        this.waveHorizontalProgress = progress;
        postInvalidate();
    }

    public void setBoatHorizontalProgress(float progress) {
        this.boatHorizontalProgress = progress;
        postInvalidate();
    }

    public void startBoat() {
        ObjectAnimator boatAnimator = ObjectAnimator.ofFloat(this, "boatHorizontalProgress", 0, 1.0f);
        boatAnimator.setDuration(10000);
        boatAnimator.setInterpolator(new LinearInterpolator());
        boatAnimator.setRepeatCount(2);
        boatAnimator.setRepeatMode(ValueAnimator.RESTART);
        boatAnimator.start();
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initData(context);
        initPaint(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initData(Context context) {
        mPathMeasure = new PathMeasure();

        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_boat_64);

    }

    private void initPaint(Context context) {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setColor(Color.GREEN);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mBoatPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void drawWave(Canvas canvas) {
        canvas.drawPath(mWavePath, mWavePaint);
    }

    private void calculateWavePath() {
        mBoatPath.reset();
        mOffsetX = waveHorizontalProgress * mWaveWidth * -1;
        mWavePath.reset();
        mWavePath.moveTo(mOffsetX, mOffsetY);
        Log.d(TAG, "calculateWavePath: mOffsetX: "+mOffsetX+", mOffsetY: "+mOffsetY+", mWaveCount: "+mWaveCount);
        for (int i = 0; i < mWaveCount; i++) {
            mWavePath.rQuadTo(mHalfWaveWidth, (i % 2 == 0?1:-1)*mWaveHeight, mWaveWidth, 0);
        }
        mBoatPath.set(mWavePath);
        mWavePath.lineTo(mOffsetX + mWidth, mHeight);
        mWavePath.lineTo(mOffsetX, mHeight);
        mWavePath.lineTo(mOffsetX, mOffsetY);
    }

    private void calculateBoatPosition() {
        mPathMeasure.setPath(mBoatPath, false);
        mBoatMatrix.reset();
        mPathMeasure.getMatrix(mPathMeasure.getLength() * boatHorizontalProgress, mBoatMatrix, PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);


    }

    private void drawBoat(Canvas canvas) {
        mBoatMatrix.preTranslate(-mBitmap.getWidth()*0.5f, -mBitmap.getHeight());
        canvas.drawBitmap(mBitmap, mBoatMatrix, mBoatPaint);
    }
}
