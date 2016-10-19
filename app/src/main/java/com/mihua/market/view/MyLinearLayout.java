package com.mihua.market.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/21
 */
public class MyLinearLayout extends LinearLayout{


    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("AAAAAA", "dispatchTouchEvent: ViewGroup ");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("AAAAAA", "onInterceptTouchEvent: ViewGroup");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("AAAAAA", "onTouchEvent: ViewGroup");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.d("AAAAAA", "down1: ViewGroup");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("AAAAAA", "move1: ViewGroup");
                break;
            case MotionEvent.ACTION_UP:

                Log.d("AAAAAA", "up1:  ViewGroup");
                break;
        }

        return super.onTouchEvent(event);
    }
}
