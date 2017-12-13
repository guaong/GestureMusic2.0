package io.guaong.gesturemusic20.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.util.AttributeSet;

import io.guaong.gesturemusic20.config.ColorConfig;
import io.guaong.gesturemusic20.util.WindowUtil;

/**
 * Created by 关桐 on 2017/9/6.
 */

public class MenuCircleButton extends CircleButton{

    private Paint mPaint;
    private DrawFilter mDrawFilter;
    private float mPaintWidth;
    public static final int ON_MENU_BUTTON = 1;
    public static final int CANCEL_MENU_BUTTON = 2;
    private int mStatus;
    private final String TEXT = "menu";
    private float r, left, top, right, bottom;

    public MenuCircleButton(Context context) {
        super(context);
    }

    public MenuCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStatus = ON_MENU_BUTTON;
        mPaint = new Paint();
        // 去除画笔锯齿
        mPaint.setAntiAlias(true);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaintWidth = WindowUtil.getWindowWidth(getContext()) / 240f;
        // 半径取外边框圆的半径（r = Math.min(w, h) / 3）的1/3
        r = Math.min(w, h) / 9;
        left = w / 2 - r;
        right = w / 2 + r;
        top = h / 2 - r;
        bottom = h / 2 + r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.setDrawFilter(mDrawFilter);
        mPaint.setColor(ColorConfig.PAINT_COLOR);
        mPaint.setStrokeWidth(mPaintWidth);
        switch (mStatus){
            case ON_MENU_BUTTON:
                super.onDraw(canvas);
                setText(TEXT);break;
            case CANCEL_MENU_BUTTON:
                drawCancelStyle(canvas);break;
            default:break;
        }
    }

    /**
     * 绘制取消按钮样式
     */
    private void drawCancelStyle(Canvas canvas){
        canvas.drawLine(left, top, right, bottom, mPaint);
        canvas.drawLine(right, top, left, bottom, mPaint);
    }

    /**
     * 设置状态
     */
    public void setStatus(int status){
        mStatus = status;
        postInvalidate();
    }

    /**
     * 得到状态
     */
    public int getStatus(){
        return mStatus;
    }
}
