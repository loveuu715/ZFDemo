package com.loveuu.vv.widget.citypicker;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.TextView;

import com.loveuu.vv.utils.UIUtils;

public class LineTextView extends TextView {

    private Paint mPaint;
    private String mText = "";
    private int mColor;
    private Context mContext;

    /**
     * @param context
     * @param attrs
     */
    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        if (null == mText) mText = "";
        mColor =
                Color.parseColor("#666666");
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.ACTION_UP) {
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        FontMetrics fontMetrics = mPaint.getFontMetrics();
        float fontTotalHeight = fontMetrics.bottom - fontMetrics.top;
        float offY = fontTotalHeight / 2 - fontMetrics.bottom;
        float newY = 0 + offY;
        mPaint.setTextSize(UIUtils.sp2px(mContext, 20));
        mPaint.setColor(mColor);
        canvas.drawText(mText, 10, UIUtils.dip2px(mContext, 34), mPaint);
        mPaint.setColor(Color.parseColor("#dfdfdf"));
        canvas.drawLine(0, this.getHeight() - 1, this.getWidth() - 1,
                this.getHeight() - 1, mPaint);
    }

}