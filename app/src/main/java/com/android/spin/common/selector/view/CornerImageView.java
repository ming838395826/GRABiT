package com.android.spin.common.selector.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.android.spin.R;

/**
 * 只有上方是圆角的图片控件
 *
 * Created by like on 2017/2/9.
 */

public class CornerImageView extends ImageView {

    int cornerSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, getResources().getDisplayMetrics());
    private Path shapePath;

    public CornerImageView(Context context) {
        this(context, null);
    }

    public CornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    Paint paint;
    public CornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopCornerImageView, defStyleAttr, 0);
//        cornerSize = a.getDimensionPixelSize(R.styleable.TopCornerImageView_corner_size, cornerSize);
        paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w == oldw && h == oldh)
            return;
        if (shapePath == null)
            shapePath = new Path();

//        shapePath.moveTo(0, h);
//        shapePath.lineTo(0, cornerSize);
//        shapePath.arcTo(new RectF(0, 0, cornerSize*2, cornerSize*2), 180, 90);
//        shapePath.lineTo(w - cornerSize, 0);
//        shapePath.arcTo(new RectF(w - cornerSize*2, 0, w, cornerSize*2), 90, 270);
//        shapePath.lineTo(w, h);
//        shapePath.close();

        shapePath.moveTo(0, cornerSize);
        shapePath.arcTo(new RectF(0, 0, cornerSize * 2, cornerSize * 2), -180, 90);
        shapePath.lineTo(w - cornerSize, 0);
        shapePath.arcTo(new RectF(w - 2 * cornerSize, 0, w, cornerSize * 2), -90, 90);
        shapePath.lineTo(w, h - cornerSize);
        shapePath.arcTo(new RectF(w - 2 * cornerSize, h - 2 * cornerSize, w, h), 0, 90);
        shapePath.lineTo(cornerSize, h);
        shapePath.arcTo(new RectF(0, h - 2 * cornerSize, cornerSize * 2, h), 90, 90);
        shapePath.close();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (cornerSize * 2 > getWidth() || cornerSize*2 >getHeight()) {
            super.onDraw(canvas);
            return;
        }
        int sc = canvas.saveLayer(0,0,getWidth(),getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        // 画出圆角
        canvas.drawPath(shapePath, paint);
        canvas.restoreToCount(sc);
    }
}
