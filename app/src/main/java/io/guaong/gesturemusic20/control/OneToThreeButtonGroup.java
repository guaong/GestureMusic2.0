package io.guaong.gesturemusic20.control;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;

import io.guaong.gesturemusic20.config.ColorConfig;
import io.guaong.gesturemusic20.util.WindowUtil;

/**
 * Created by 关桐 on 2017/9/6.
 */

public class OneToThreeButtonGroup extends ViewGroup{

    public static final int ONE_TO_THREE = 1;
    public static final int THREE_TO_ONE = 2;
    public static final int STATIC = 3;
    private int mStatus;
    private Paint mPaint;
    private DrawFilter mDrawFilter;
    private float mPaintWidth;
    private float childR;
    private float mWidth, mHeight;
    private final int TOP = 1;
    private final int BOTTOM = 2;
    private final int LEFT = 3;
    private final int RIGHT = 4;

    public OneToThreeButtonGroup(Context context) {
        super(context);
    }

    public OneToThreeButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStatus = STATIC;
        mPaintWidth = WindowUtil.getWindowWidth(getContext()) / 240f;
        mPaint = new Paint();
        // 去除画笔锯齿
        mPaint.setAntiAlias(true);
        mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //由于没有设置边距，因此写死，使该Group和子CircleButton的r一致
        childR = Math.min(r / 3, b / 2) / 3;
        mWidth = r;
        mHeight = b;
        variation = 0;
        for (int i = 0; i < getChildCount(); i++){
            switch (i){
                case 0:
                    getChildAt(0).layout(r / 3, b / 2, r / 3 * 2, b);break;
                case 1:
                    getChildAt(1).layout(r / 3, t, r / 3 * 2, b / 2);
                    getChildAt(1).setVisibility(INVISIBLE);
                    break;
                case 2:
                    getChildAt(2).layout(l, b / 2, r / 3, b);
                    getChildAt(2).setVisibility(INVISIBLE);
                    break;
                case 3:
                    getChildAt(3).layout(r / 3 * 2, b / 2, r, b);
                    getChildAt(3).setVisibility(INVISIBLE);
                    break;
                default:break;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setDrawFilter(mDrawFilter);
        mPaint.setColor(ColorConfig.PAINT_COLOR);
        mPaint.setStrokeWidth(mPaintWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        switch (mStatus){
            case ONE_TO_THREE:
                oneToThreeAnimation(canvas);break;
            case THREE_TO_ONE:
                threeToOneAnimation(canvas);break;
            case STATIC:break;
            default:break;
        }
    }

    float variation = 0;
    float length = 0;

    /**
     * 一变三动画
     */
    private void oneToThreeAnimation(Canvas canvas){
        //长宽比例，保持以相同时间到达
        float ratio = (mHeight / 2 - childR * 2) / (mWidth / 3 - childR * 2);
        if (variation < mWidth / 3 - childR * 2){
            getChildAt(0).setVisibility(INVISIBLE);
            /* 三个方向 */
            drawLineMove(canvas, mWidth / 2 - childR * 2 - variation, mHeight / 4 * 3 - childR
                    , mWidth / 2 - variation, mHeight / 4 * 3 - childR); //左
            drawLineMove(canvas, mWidth / 2 + childR * 2 + variation, mHeight / 4 * 3 + childR
                    , mWidth / 2 + variation, mHeight / 4 * 3 + childR); //右
            drawLineMove(canvas, mWidth / 2 + childR
                    , mHeight / 4 * 3 - childR * 2 - variation * ratio
                    , mWidth / 2 + childR
                    , mHeight / 4 * 3 - variation * ratio); //上
            variation += 10;
            if (variation > mWidth / 3 - childR * 2){
                variation = mWidth / 3 - childR * 2;
            }
            postInvalidate();
        }else { //到达预定地点，开始线变圆
            if (length <= childR * 2){
                drawLineChangeCircle(canvas, mWidth / 2 - childR * 2 - variation
                        , mHeight / 4 * 3 - childR, mWidth / 2 - variation - length,
                        mHeight / 4 * 3 - childR, -90, LEFT);
                drawLineChangeCircle(canvas, mWidth / 2 + childR * 2 + variation
                        , mHeight / 4 * 3 + childR, mWidth / 2 + variation + length
                        , mHeight / 4 * 3 + childR, 90, RIGHT);
                drawLineChangeCircle(canvas, mWidth / 2 + childR
                        , mHeight / 4 * 3 - childR * 2 - variation * ratio
                        , mWidth / 2 + childR
                        , mHeight / 4 * 3 - variation * ratio - length, 0, TOP);
                length += 5;
            }else {
                final MenuCircleButton btn = (MenuCircleButton)getChildAt(0);
                btn.setStatus(MenuCircleButton.CANCEL_MENU_BUTTON);
                for (int i = 0; i < getChildCount(); i++){
                    getChildAt(i).setVisibility(VISIBLE);
                }
            }
        }
    }

    /**
     * 三变一动画
     */
    private void threeToOneAnimation(Canvas canvas){
        //长宽比例，保持以相同时间到达
        float ratio = (mHeight / 2 - childR * 2) / (mWidth / 3 - childR * 2);
        if (length >= childR * 2){
            for (int i = 0; i < getChildCount(); i++){
                getChildAt(i).setVisibility(INVISIBLE);
            }
            length -= 5;
            postInvalidate();
        }else {
            if (length > 0){
                drawLineChangeCircle(canvas, mWidth / 2 - childR * 2 - variation
                        , mHeight / 4 * 3 - childR, mWidth / 2 - variation - length,
                        mHeight / 4 * 3 - childR, -90, LEFT);
                drawLineChangeCircle(canvas, mWidth / 2 + childR * 2 + variation
                        , mHeight / 4 * 3 + childR, mWidth / 2 + variation + length
                        , mHeight / 4 * 3 + childR, 90, RIGHT);
                drawLineChangeCircle(canvas, mWidth / 2 + childR
                        , mHeight / 4 * 3 - childR * 2 - variation * ratio
                        , mWidth / 2 + childR
                        , mHeight / 4 * 3 - variation * ratio - length, 0, TOP);
                length -= 5;
            }else {
                if (variation > 0){
                    /* 三个方向 */
                    drawLineMove(canvas, mWidth / 2 - childR * 2 - variation, mHeight / 4 * 3 - childR
                            , mWidth / 2 - variation, mHeight / 4 * 3 - childR); //左
                    drawLineMove(canvas, mWidth / 2 + childR * 2 + variation, mHeight / 4 * 3 + childR
                            , mWidth / 2 + variation, mHeight / 4 * 3 + childR); //右
                    drawLineMove(canvas, mWidth / 2 + childR
                            , mHeight / 4 * 3 - childR * 2 - variation * ratio
                            , mWidth / 2 + childR
                            , mHeight / 4 * 3 - variation * ratio); //上
                    variation -= 10;
                    if (variation < 0){
                        variation = 0;
                    }
                    postInvalidate();
                }else {
                    final MenuCircleButton btn = (MenuCircleButton)getChildAt(0);
                    btn.setStatus(MenuCircleButton.ON_MENU_BUTTON);
                    getChildAt(0).setVisibility(VISIBLE);
                }
            }
        }
    }

    /**
     * 绘制线的移动
     */
    private void drawLineMove(Canvas canvas, float sX, float sY, float eX, float eY){
        canvas.drawLine(sX, sY, eX, eY, mPaint);
    }

    /**
     * 绘制线转化成圆
     */
    private void drawLineChangeCircle(Canvas canvas, float sX, float sY, float eX, float eY,
                                      int startAngle, int direction){
        float x = 0, y = 0;
        switch (direction){
            case LEFT:x = sX; y = sY + childR; break;
            case RIGHT:x = sX; y = sY - childR; break;
            case TOP:x = sX - childR; y = sY; break;
            case BOTTOM:x = sX + childR; y = sY; break;
            default:break;
        }
        final RectF rectF = new RectF(x - childR, y - childR, x + childR, y + childR);
        switch (direction){
            case LEFT:
                canvas.drawArc(rectF, startAngle, ((eX - sX) / (2 * childR) - 1) * 360, false, mPaint);
                break;
            case RIGHT:
                canvas.drawArc(rectF, startAngle, ((sX - eX) / (2 * childR) - 1) * 360, false, mPaint);
                break;
            case TOP:
                canvas.drawArc(rectF, startAngle, ((eY - sY) / (2 * childR) - 1) * 360, false, mPaint);
                break;
            case BOTTOM:
                canvas.drawArc(rectF, startAngle, ((sY - eY) / (2 * childR) - 1) * 360, false, mPaint);
                break;
            default:break;
        }
        canvas.drawLine(sX, sY, eX, eY, mPaint);
        postInvalidate();
    }

    /**
     * 设置状态
     */
    public void setStatus(int status){
        mStatus = status;
        postInvalidate();
    }
}
