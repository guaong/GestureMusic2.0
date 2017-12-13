package io.guaong.gesturemusic20.control;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 关桐 on 2017/9/7.
 */

public class TimingCircleButton extends CircleButton {

    // 0
    public static final int ZERO = 1;
    // 半小时
    public static final int HALF_HOUR = 2;
    // 一小时
    public static final int AN_HOUR = 3;
    // 一个半小时
    public static final int ONE_AND_HALF_AN_HOUR = 4;
    // 两小时
    public static final int TWO_HOURS = 5;

    private int mTimingStatus;

    public TimingCircleButton(Context context) {
        super(context);
    }

    public TimingCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTimingStatus = ZERO;
        setText("timing");
    }


    public void setStatus(int status) {
        setTimingStatus(status);
        switch (mTimingStatus){
            case ZERO:setText("timing");break;
            case HALF_HOUR:setText("0.5h");break;
            case AN_HOUR:setText(" 1 h");break;
            case ONE_AND_HALF_AN_HOUR:setText("1.5h");break;
            case TWO_HOURS:setText(" 2 h");break;
        }
    }

    public int getNextStatus() {
        return getTimingStatus() % 5 + 1;
    }

    public void setTimingStatus(int status){
        mTimingStatus = status;
    }

    public int getTimingStatus(){
        return mTimingStatus;
    }

}
