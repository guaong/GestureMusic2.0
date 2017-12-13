package io.guaong.gesturemusic20.control;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by 关桐 on 2017/9/7.
 */

public class OrderCircleButton extends CircleButton {

    // 顺序播放
    public static final int PLAY_ORDER = 1;
    // 随机播放
    public static final int PLAY_RANDOM = 2;
    // 单曲播放
    public static final int PLAY_SINGLE = 3;

    private int mOrderStatus;

    public OrderCircleButton(Context context) {
        super(context);
    }

    public OrderCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mOrderStatus = PLAY_ORDER;
        setText("order");
    }

    public void setStatus(int status) {
        setOrderStatus(status);
        switch (mOrderStatus){
            case PLAY_ORDER:setText("order");break;
            case PLAY_RANDOM:setText("random");break;
            case PLAY_SINGLE:setText("single");break;
        }
    }

    public int getNextStatus() {
        return getOrderStatus() % 3 + 1;
    }

    public void setOrderStatus(int status){
        mOrderStatus = status;
    }

    public int getOrderStatus(){
        return mOrderStatus;
    }

}
