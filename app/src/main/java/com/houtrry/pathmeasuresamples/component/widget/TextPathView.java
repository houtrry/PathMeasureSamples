package com.houtrry.pathmeasuresamples.component.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author: houtrry
 * @date: 2018/3/13 20:06
 * @version: $Rev$
 * @description: https://juejin.im/post/5a9677b16fb9a063375765ad
 */

public class TextPathView extends View {

    private String mTextString = "小楼昨夜又东风";

    private float progress = 0;

    private int mTextColor = Color.RED;
    private float mTextSize = 60;
    private Paint mTextPaint;

    private Path mTextPath = new Path();
    private Path mTextProgressPath = new Path();

    private PathMeasure mTextPathMeasure = new PathMeasure();

    private float mTextLengthSum = 0;

    public TextPathView(Context context) {
        this(context, null);
    }

    public TextPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPathView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initAttrs(context, attrs);
        initPaint(context);
        initData(context);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }

    private void initData(Context context) {
        mTextPath.reset();
        mTextPaint.getTextPath(mTextString, 0, mTextString.length(), 0, mTextPaint.getTextSize(), mTextPath);
        mTextPathMeasure.setPath(mTextPath, false);

        mTextLengthSum = mTextPathMeasure.getLength();
        while (mTextPathMeasure.nextContour()) {
            mTextLengthSum += mTextPathMeasure.getLength();
        }

    }

    private void initPaint(Context context) {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setStyle(Paint.Style.STROKE);
     }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mTextProgressPath.reset();
        mTextPathMeasure.setPath(mTextPath, false);
        float mTextPathMeasureLength = mTextPathMeasure.getLength();
        mTextPathMeasure.getSegment(0, mTextPathMeasureLength, mTextProgressPath, false);

        canvas.drawPath(mTextProgressPath, mTextPaint);
    }

    public void setProgress(float progress) {
        this.progress = progress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void startAnimator() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", 0.0f, 1.0f);
        objectAnimator.setDuration(3000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }
}
