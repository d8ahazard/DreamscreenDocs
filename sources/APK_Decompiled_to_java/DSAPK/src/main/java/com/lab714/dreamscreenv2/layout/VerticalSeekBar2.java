package com.lab714.dreamscreenv2.layout;

import android.content.Context;
import android.graphics.Canvas;
import androidx.appcompat.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class VerticalSeekBar2 extends AppCompatSeekBar {
    private OnSeekBarChangeListener myListener;

    public VerticalSeekBar2(Context context) {
        super(context);
    }

    public VerticalSeekBar2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public VerticalSeekBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    /* access modifiers changed from: protected */
    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener mListener) {
        this.myListener = mListener;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas c) {
        c.rotate(-90.0f);
        c.translate((float) (-getHeight()), 0.0f);
        super.onDraw(c);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 0:
            case 2:
                int progress = getMax() - ((int) ((((float) getMax()) * event.getY()) / ((float) getHeight())));
                if (progress < 0) {
                    progress = 0;
                } else if (progress > getMax()) {
                    progress = getMax();
                }
                setProgress(progress);
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                this.myListener.onProgressChanged(this, progress, true);
                break;
            case 1:
                this.myListener.onStopTrackingTouch(this);
                break;
        }
        return true;
    }
}
