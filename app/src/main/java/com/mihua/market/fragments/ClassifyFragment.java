package com.mihua.market.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mihua.market.MyApplication;
import com.mihua.market.R;
import com.mihua.market.bean.ClassifyData;
import com.mihua.market.config.NetConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends Fragment implements View.OnClickListener {


    private RequestQueue mRequestQueue;
    private LinkedList<String> mLeftData;
    private LinearLayout mLinearLayout;
    private LinkedList<ClassifyRightFragment> mFragments;
    private List<ClassifyData.InforBean.ListItemsBean> mListItems;

    public ClassifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.market_fragment_classify, container, false);

        initView(view);
        initData();
        setData();

        setListener();
        return view;
    }

    private void initView(View view) {

        mLinearLayout = (LinearLayout)view.findViewById(R.id.classify_left_layout);

    }

    private void initData() {

        // 得到全局的请求队列
        mRequestQueue = MyApplication.getRequestQueue();

        mLeftData = new LinkedList<>();

        mFragments = new LinkedList<>();
        getData();
    }


    private void getData() {

        StringRequest stringRequest =
                new StringRequest(Request.Method.POST, NetConfig.CLASSIFYDATA, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ClassifyData classifyData = JSON.parseObject(response, ClassifyData.class);
                        mListItems = classifyData.getInfor().getListItems();

                        for (int i = 0; i < mListItems.size(); i++) {
                            // 得到分类
                            mLeftData.add(mListItems.get(i).getType());
                        }
                        addViewToLayout();
                        setFragment();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("agent_id","101");
                        return hashMap;
                    }
                };
        stringRequest.setTag("classifyData");
        mRequestQueue.add(stringRequest);
    }

    private void setFragment() {

        if (mLeftData != null) {

            for (int i = 0; i < mLeftData.size(); i++) {
                ClassifyRightFragment classifyRightFragment = new ClassifyRightFragment();
                mFragments.add(classifyRightFragment);
            }
            // 得到Fragment 管理器
            FragmentManager childFragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();

            for (int i = 0; i < mFragments.size(); i++) {
                ClassifyRightFragment fragment = mFragments.get(i);
                fragmentTransaction.add(R.id.classify_fragment_container, fragment,i+"");
                ClassifyData.InforBean.ListItemsBean listItemsBean = mListItems.get(i);
                Bundle bundle = new Bundle();
                bundle.putParcelable("listItemsBean",listItemsBean);
                fragment.setArguments(bundle);
                fragmentTransaction.hide(fragment);
            }
            fragmentTransaction.show(mFragments.get(0));
            fragmentTransaction.commit();
        }
    }

    // 将TextView 添加到布局中去
    private void addViewToLayout() {
        // 添加到 左边的 Layout中去
        for (int i = 0; i < mLeftData.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.market_classify_left_item,null);
            TextView textView = (TextView) view.findViewById(R.id.tv_item_classify);
            // 设置点击监听事件
            view.setOnClickListener(this);
            textView.setText(mLeftData.get(i));
            mLinearLayout.addView(view);
        }
        Log.d("size",mLinearLayout.getChildCount()+"");
    }

    private void setData() {

    }


    private void setListener() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll("classifyData");
        }
    }

    // textView的点击事件
    @Override
    public void onClick(View v) {

        int i = mLinearLayout.indexOfChild(v);

        // 设置字体的颜色
        setTextColor(i);

        // 显示不同的Fragment
        switchFragment(i);
    }

    // 切换不同的Fragment
    private void switchFragment(int index) {

        if(index>=0&& index<mFragments.size()){
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            for (int i = 0; i < mFragments.size(); i++) {
                ClassifyRightFragment classifyRightFragment = mFragments.get(i);
                if(i== index){
                    fragmentTransaction.show(classifyRightFragment);
                }else {
                    fragmentTransaction.hide(classifyRightFragment);
                }
            }
            fragmentTransaction.commit();
        }
    }

    /**
     *  设置字体的颜色
     * @param position 选中的position
     */
    private void setTextColor(int position) {
        // 让TextView 为不选中的状态
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
             LinearLayout layout= (LinearLayout) mLinearLayout.getChildAt(i);
            layout.setBackgroundColor(Color.parseColor("#f1eeee"));
            RelativeLayout childAt = (RelativeLayout) layout.getChildAt(0);
            ((TextView)childAt.getChildAt(0)).setTextColor(Color.BLACK);
             childAt.getChildAt(1).setBackgroundColor(Color.parseColor("#f1eeee"));
        }
        // 让点击的为选中的状态
        LinearLayout layout= (LinearLayout) mLinearLayout.getChildAt(position);
        layout.setBackgroundColor(Color.WHITE);
        RelativeLayout childAt = (RelativeLayout) layout.getChildAt(0);
        ((TextView)childAt.getChildAt(0)).setTextColor(Color.parseColor("#ff552d"));
        childAt.getChildAt(1).setBackgroundColor(Color.parseColor("#ff552d"));
    }

}
