package io.guaong.gesturemusic20.control;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by 关桐 on 2017/9/7.
 *
 */

public class ListCircleButton extends CircleButton {
    public ListCircleButton(Context context) {
        super(context);
    }

    public ListCircleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setText("list");
    }
}
