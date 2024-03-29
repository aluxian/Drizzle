package com.aluxian.drizzle.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.aluxian.drizzle.R;

public class FixedAspectRatioLinearLayout extends LinearLayout {

    private int mAspectRatioWidth = 4;
    private int mAspectRatioHeight = 3;

    public FixedAspectRatioLinearLayout(Context context) {
        super(context);
    }

    public FixedAspectRatioLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FixedAspectRatioLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FixedAspectRatioView);

        mAspectRatioWidth = array.getInt(R.styleable.FixedAspectRatioView_aspectRatioWidth, mAspectRatioWidth);
        mAspectRatioHeight = array.getInt(R.styleable.FixedAspectRatioView_aspectRatioHeight, mAspectRatioHeight);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int calculatedHeight = originalWidth * mAspectRatioHeight / mAspectRatioWidth;

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(originalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(calculatedHeight, MeasureSpec.EXACTLY));
    }

}
