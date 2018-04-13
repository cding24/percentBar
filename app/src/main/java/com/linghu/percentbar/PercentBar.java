package com.linghu.percentbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by linghu on 2018/3/21.
 * 比例条控件,该控件包含左边的文字，当然文字宽度也是可以设置的，
 * 因为多个比例条一般是左边对齐的，所以多个条一般设置相同的左边的文字宽度，即percentLeftWidth
 *
 */
public class PercentBar extends View {
    private int mWidth;
    private int mHeight;
    private int leftWidth = 40;
    private int rightWidth = 34;

    private int bgColor = 0x888888;
    private int startColor = 0xffaa00;
    private int endColor = 0xff8800;
    private int nameColor = 0x393939;
    private LinearGradient shader;
    private int paddingTop = 2;//顶部和底部的padding
    private int paddingBottom = 3;

    private int txtSize = 14;
    private Paint namePaint;
    private Paint defaultPaint;
    private String txtStr;
    private float numBaseLine = 0;

    private int percent = 0;

    public PercentBar(Context context) {
        super(context);
        initView(context, null);
    }

    public PercentBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public PercentBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PercentBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        setWillNotDraw(false);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        leftWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftWidth, dm);
        txtSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, txtSize, dm);
        rightWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightWidth, dm);
        paddingTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingTop, dm);
        paddingBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, paddingBottom, dm);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PercentBar);
            leftWidth = typedArray.getDimensionPixelSize(R.styleable.PercentBar_percentLeftWidth, leftWidth);
            txtSize = typedArray.getDimensionPixelSize(R.styleable.PercentBar_percentNameTxtSize, txtSize);
            percent = typedArray.getInteger(R.styleable.PercentBar_percent, percent);
            paddingTop = typedArray.getDimensionPixelSize(R.styleable.PercentBar_percentPaddingTop, paddingTop);
            paddingBottom = typedArray.getDimensionPixelSize(R.styleable.PercentBar_percentPaddingBottom, paddingBottom);

            txtStr = typedArray.getString(R.styleable.PercentBar_percentName);
            nameColor = typedArray.getColor(R.styleable.PercentBar_percentNameColor, nameColor);
            bgColor = typedArray.getColor(R.styleable.PercentBar_percentbgColor, bgColor);
            startColor = typedArray.getColor(R.styleable.PercentBar_percentStartColor, startColor);
            endColor  = typedArray.getColor(R.styleable.PercentBar_percentEndColor, endColor);
            typedArray.recycle();
        }

        defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setStyle(Paint.Style.FILL);
        defaultPaint.setColor(bgColor);

        namePaint = new Paint();
        namePaint.setAntiAlias(true);
        namePaint.setColor(nameColor);
        namePaint.setTextSize(txtSize);
        namePaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
    }

    public void setPercent(int percent){
        this.percent = percent;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (numBaseLine == 0) {
            Rect targetRect = new Rect(0, mHeight/2 - txtSize/2, mWidth, mHeight/2 + txtSize/2);
            Paint.FontMetrics txtFontMetrics = namePaint.getFontMetrics();
            numBaseLine = (targetRect.bottom + targetRect.top - txtFontMetrics.bottom - txtFontMetrics.top) / 2.0f;

            shader = new LinearGradient(leftWidth, paddingTop, mWidth-rightWidth, mHeight-paddingBottom,
                    new int[]{startColor, endColor}, null, Shader.TileMode.CLAMP);
        }

        if (!TextUtils.isEmpty(txtStr)) {
            canvas.drawText(txtStr, leftWidth/2, numBaseLine, namePaint);
        }

        defaultPaint.setColor(bgColor);
        defaultPaint.setShader(null);
        canvas.drawRect(leftWidth, paddingTop, mWidth-rightWidth, mHeight-paddingBottom, defaultPaint);
        defaultPaint.setColor(endColor);
        defaultPaint.setShader(shader);
        canvas.drawRect(leftWidth, paddingTop, leftWidth+((mWidth-leftWidth-rightWidth)*percent/100.0f), mHeight-paddingBottom, defaultPaint);

        canvas.drawText(percent+"%", leftWidth+(mWidth-leftWidth-rightWidth)*percent/100.0f+rightWidth/2.0f, numBaseLine, namePaint);
    }

}