package com.mihua.market.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mihua.market.activitys.ClassifyDetailActivity;
import com.mihua.market.MarketMainActivity;
import com.mihua.market.MyApplication;
import com.mihua.market.R;
import com.mihua.market.adapter.HomeClassifyAdapter;
import com.mihua.market.adapter.HomePagerAdapter;
import com.mihua.market.bean.HomeClassifyTitle;
import com.mihua.market.bean.HomeTopImage;
import com.mihua.market.config.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, HomeClassifyAdapter.ClassifyClickCallback {


    private static final String TAG = HomeFragment.class.getSimpleName();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ViewPager mViewPager;
    private HomePagerAdapter mTopAdapter;
    private RequestQueue mRequestQueue;
    private int position;
    private ArrayList<HomeTopImage.DataBean> mTopData;
    private LinearLayout mIndicatorContainer;
    private ArrayList<ImageView> mTopIndicators;
    private int mDensityDpi;
    private Timer mTimer;
    private RecyclerView mClassifyRecyclerView;
    private ArrayList<HomeClassifyTitle> mClassifyData;
    private HomeClassifyAdapter mHomeClassifyAdapter;
    private boolean mClassifyComplete;
    private boolean mTopComplete;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.market_fragment_home, container, false);

        // 初始化视图
        initView(view);
        // 初始化数据
        initData();
        setData();
        setListener();
        // 请求数据
        getData();

        return view;
    }

    private void initView(View view) {

        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.home_refresh_layout);
        mViewPager = (ViewPager)view.findViewById(R.id.home_pager);
        mClassifyRecyclerView = (RecyclerView)view.findViewById(R.id.home_classify);
        mIndicatorContainer = (LinearLayout)view.findViewById(R.id.home_dot_indicator_container);
    }


    private void initData() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        mDensityDpi = displayMetrics.densityDpi;

        // 得到全局的请求队列
        mRequestQueue = MyApplication.getRequestQueue();

        // 用于存储请求到的banner页面的数据
        mTopData = new ArrayList<>();
        // 用于存储小圆点
        mTopIndicators = new ArrayList<>();

        // 设置进度的动画颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#ff552d"));
        mSwipeRefreshLayout.setDistanceToTriggerSync(50);

        mClassifyData = new ArrayList<>();
        mHomeClassifyAdapter = new HomeClassifyAdapter(getActivity(),mClassifyData);
    }

    private void setData() {
        //启动定时器切换
        startPagerSwitchTimer();

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 5);
        layoutManager.setAutoMeasureEnabled(true);
        mClassifyRecyclerView.setLayoutManager(layoutManager);
        mClassifyRecyclerView.setAdapter(mHomeClassifyAdapter);

    }

    // 5秒以后启动，隔 3 秒切换一次
    private void startPagerSwitchTimer(){
        mTimer = new Timer();
        mTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        pagerSwitchHandler.sendEmptyMessage(0);
                    }
                },
                5000, 3000);
    }
    // 自定义Handler,处理定时器的页面切换
    private Handler pagerSwitchHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int index = mViewPager.getCurrentItem();
            mViewPager.setCurrentItem(index+1);
        }
    };

    private void setListener() {
        mViewPager.addOnPageChangeListener(this);

        // 接口回调，监听事件
        mHomeClassifyAdapter.setClassifyClickCallback(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mTopData.clear();
                mClassifyData.clear();
                getData();
            }
        });
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeRefreshLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeRefreshLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });
    }

    // 网络请求数据
    private void getData() {
        // 得到头部轮播图的数据
        getTopData();
        // 得到中间分类的数据
        getClassifyTitle();
    }

    // 请求中间分类图片的数据
    private void getClassifyTitle() {
        StringRequest stringRequest =
                new StringRequest(Request.Method.POST,
                        NetConfig.BASEHOMECLASSIFY, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d(TAG, "onResponse: "+response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject infor = jsonObject.optJSONObject("infor");
                            String type = infor.optString("type");
                            List<HomeClassifyTitle> homeClassifyTitles = JSON.parseArray(type, HomeClassifyTitle.class);
                            mClassifyData.addAll(homeClassifyTitles);
                            Log.d(TAG, "onResponse: "+mClassifyData.size());
                            mHomeClassifyAdapter.notifyDataSetChanged();
                            mClassifyComplete = true;
                            if (mTopComplete&&mClassifyComplete&& mSwipeRefreshLayout.isRefreshing()){
                                mSwipeRefreshLayout.setRefreshing(false);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    // post 提交的字段 ：agent_id = 101
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String,String> map = new HashMap<>();
                        map.put("agent_id","101");
                        return map;
                    }
                };
        stringRequest.setTag("classify");
        mRequestQueue.add(stringRequest);
    }

    // 请求顶部的广告页面banner页面的数据
    private void getTopData() {

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, NetConfig.DISCOVERBANNER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                HomeTopImage homeTopImage = JSON.parseObject(response, HomeTopImage.class);

                List<HomeTopImage.DataBean> data = homeTopImage.getData();
                // 将数据放到集合中
                for (int i = 0; i < data.size(); i++) {

                    HomeTopImage.DataBean dataBean = data.get(i);
                    mTopData.add(dataBean);
                }
                mTopAdapter = new HomePagerAdapter(getContext(), mTopData, mViewPager);
                mViewPager.setAdapter(mTopAdapter);
                // 初始化小圆点
                initIndicatorsDot();
                // 默认展示第一张图片
                mViewPager.setCurrentItem(1,false);
                mTopComplete = true;
                if (mTopComplete&&mClassifyComplete&& mSwipeRefreshLayout.isRefreshing()){
                    mSwipeRefreshLayout.setRefreshing(false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // 设置标记
        stringRequest.setTag("banner");
        mRequestQueue.add(stringRequest);
    }

    // 初始化小圆点
    private void initIndicatorsDot() {
        if (mTopIndicators.size()==0) {

            int dp = mDensityDpi/160;
            for (int i = 0; i < mTopData.size(); i++) {
                ImageView iv = new ImageView(HomeFragment.this.getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8*dp, 8*dp);
                params.setMargins(0,0,8*dp,8*dp);
                iv.setLayoutParams(params);
                mTopIndicators.add(iv);
                mIndicatorContainer.addView(iv);
                if (i==0){
                    iv.setImageResource(R.mipmap.dot_read);
                }else {
                    iv.setImageResource(R.mipmap.dot_gary);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("banner");
            mRequestQueue.cancelAll("classify");
        }
    }

    // ViewPager滑动监听事件
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < mTopIndicators.size(); i++) {
           mTopIndicators.get(i).setImageResource(R.mipmap.dot_gary);
        }

        if(position==mTopIndicators.size()+1){
            mTopIndicators.get(0).setImageResource(R.mipmap.dot_read);;
        }else if(position==0){
            mTopIndicators.get(mTopIndicators.size()-1).setImageResource(R.mipmap.dot_read);
        }else {
            mTopIndicators.get(position-1).setImageResource(R.mipmap.dot_read);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    // 中间分类按钮的点击事件
    @Override
    public void onClassifyClick(int position) {

        if(position==0||position==1||position==2||position==5||position==6||position==7||position==8){
            String id = mClassifyData.get(position).getId();
            Intent intent = new Intent(getActivity(), ClassifyDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            bundle.putString("source","home");
            intent.putExtras(bundle);
            getActivity().startActivity(intent);
        }else if(position==9){

            ((MarketMainActivity)getActivity()).showClassifyFragment();

        }
    }
}
