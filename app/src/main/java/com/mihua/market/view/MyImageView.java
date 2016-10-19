package com.mihua.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/21
 */
public class MyImageView extends ImageView {

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("AAAAAA", "dispatchTouchEvent: view");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("AAAAAA", "onTouchEvent: view");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.d("AAAAAA", "down1:  view");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("AAAAAA", "move1:  view");
                break;
            case MotionEvent.ACTION_UP:

                Log.d("AAAAAA", "up1:   view");
                break;
        }
        return super.onTouchEvent(event);
    }
}
