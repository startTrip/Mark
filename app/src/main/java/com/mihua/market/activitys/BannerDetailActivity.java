package com.mihua.market.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mihua.market.R;

public class BannerDetailActivity extends AppCompatActivity {

    private ImageView mView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_activity_banner_detail);

        mView = (ImageView) findViewById(R.id.view);
        mLinearLayout = (LinearLayout) findViewById(R.id.view_group);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AAAAAA", "onClick: view");
            }
        });

        mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d("AAAAAA", "onLongClick: view");
                return false;
            }
        });

        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d("AAAAAA", "down: view");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d("AAAAAA", "move: view");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d("AAAAAA", "up: view");
                        break;
                }
                // 返回 true 以后  onTouchEvent 收不到
                return false;
            }
        });

        mLinearLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        Log.d("AAAAAA", "down: viewGroup");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        Log.d("AAAAAA", "move: viewGroup");
                        break;
                    case MotionEvent.ACTION_UP:

                        Log.d("AAAAAA", "up: viewGroup");
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.d("AAAAAA", "down: activity");
                break;
            case MotionEvent.ACTION_MOVE:

                Log.d("AAAAAA", "move: activity");
                break;
            case MotionEvent.ACTION_UP:

                Log.d("AAAAAA", "up:  activity");
                break;
        }

//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
        return super.onTouchEvent(event);
    }
}
