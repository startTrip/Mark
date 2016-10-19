package com.mihua.market.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mihua.market.MyApplication;
import com.mihua.market.R;
import com.mihua.market.adapter.GoodsListAdapter;
import com.mihua.market.bean.GoodsListData;
import com.mihua.market.config.NetConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private String mBaseUrl;
    private String mId;
    private String mIndex;
    private boolean mFromHome;
    private RequestQueue mRequestQueue;
    private ArrayList<GoodsListData.ListItemsBean> mList;
    private GoodsListAdapter mGoodsListAdapter;

    public GoodsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.market_fragment_goods_list, container, false);
        initView(view);

        initData();

        setData();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.goodsList_recycler);

    }

    private void initData() {
        // 得到 Activity 传过来的数据
        Bundle bundle = getArguments();
        String source = bundle.getString("source");
        mId = bundle.getString("id");
        Log.d("CCC",mId);
        mIndex = bundle.getString("keytype");

        // 根据跳转的来源 进行得到不同的URL
        if(source.equals("home")){
            mBaseUrl = NetConfig.GOODSLISTFROMHOME;
            mFromHome = true;
        }else if(source.equals("classify")){
            mBaseUrl = NetConfig.GOODSLISTFROMCLASSIFY;
            mFromHome = false;
        }

        // 得到请求队列
        mRequestQueue = MyApplication.getRequestQueue();

        mList = new ArrayList<>();

        mGoodsListAdapter = new GoodsListAdapter(getActivity(),mList);

        getData();
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mBaseUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("on",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String infor = jsonObject.optString("infor");
                    GoodsListData goodsListData = JSON.parseObject(infor, GoodsListData.class);

                    List<GoodsListData.ListItemsBean> listItems = goodsListData.getListItems();
                    if (listItems != null) {
                        mList.addAll(listItems);
                        mGoodsListAdapter.notifyDataSetChanged();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hashMap = new HashMap<>();
                if(mFromHome){
                    // 请求参数 agent_id&client_id=0&page=0&type_id=%d&keytype=0
                    hashMap.put("agent_id","101");
                    hashMap.put("client_id","0");
                    hashMap.put("page","0");
                    hashMap.put("type_id",mId);
                    hashMap.put("keytype",mIndex);
                }else {
                    // keyid=%d&page=0&keytype=%d&agent_id=101

                    hashMap.put("agent_id","101");
                    hashMap.put("keyid",mId);
                    hashMap.put("page","0");
                    hashMap.put("keytype",mIndex);
                }
                return hashMap;
            }
        };
        stringRequest.setTag("goodList");
        mRequestQueue.add(stringRequest);
    }

    private void setData() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mGoodsListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRequestQueue != null) {

            mRequestQueue.cancelAll("goodList");
        }
    }
}