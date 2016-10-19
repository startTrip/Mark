package com.mihua.market.fragments;


import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.mihua.market.R;
import com.mihua.market.activitys.BannerDetailActivity;
import com.mihua.market.activitys.CheckOutActivity;
import com.mihua.market.adapter.GoCartListViewAdapter;
import com.mihua.market.bean.GoCartGoods;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class GocartFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private ListView mMarket_lv;
    private ListView mFresh_lv;
    private View mView_title;
    private ArrayList<GoCartGoods.DatasBean.CartListBean> mMarketData;
    private OkHttpClient mClient;
    public static final String TAG = GocartFragment.class.getSimpleName();

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==100){
                GoCartGoods goCartGoods = (GoCartGoods) msg.obj;

                List<GoCartGoods.DatasBean.CartListBean> cart_list = goCartGoods.getDatas().getCart_list();
                if (cart_list != null) {

                    for (int i = 0; i < cart_list.size(); i++) {
                        // 如果是万能居超市
                        if(cart_list.get(i).getStore_id().equals(126+"")){

                            mCartIdList.add("," + cart_list.get(i).getCart_id()
                                    + "|" + cart_list.get(i).getGoods_num());

                            Log.d(TAG, "handleMessage: "+mMarketData.size());
                        }else {
                            cart_list.remove(cart_list.get(i));
                        }
                    }
                    mGoCartListViewAdapter.addDatas(cart_list);
                }
                // 设置为不可用
                mBtCheck.setEnabled(false);
            }
        }
    };
    private GoCartListViewAdapter mGoCartListViewAdapter;
    private boolean mIsUse;
    private TextView mTv_goods_change;
    private TextView mStoreName;
    private CheckBox mCb_Goods_All;
    private CheckBox mCheckTitle;
    private TextView mTvPrice;
    private Button mBtCheck;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<String> mCartIdList;
    private String key="f6d38622c20799e1cef01c110f2d600a";
    private boolean mIsAll = true;
    private boolean mIsAll1;

    public GocartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.market_fragment_gocart, container, false);

        initView(view);

        initData();
        setData();

        setListener();
        return view;
    }

    private void setListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            // 两秒后停止刷新
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(mSwipeRefreshLayout.isRefreshing()){
                                        mSwipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        // 店铺名监听事件
        mStoreName.setOnClickListener(this);
        // 点击编辑
        mTv_goods_change.setOnClickListener(this);

        // 点击全选
        mCb_Goods_All.setOnCheckedChangeListener(this);
        // 点击头部的CheckBox
        mCheckTitle.setOnCheckedChangeListener(this);

        // 当改变数据是就会调用这个方法去改变总的金额
        mGoCartListViewAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                String money = String.format("%.1f", getMoney());
                Log.d("fff",money);
                mTvPrice.setText("总计："+ money +"元");

            }
        });

        // 点击结算按钮跳转到结算界面
        mBtCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cart_id = getStringCartId();
                Log.d(TAG, "onClick: "+cart_id);
                Intent intent = new Intent(getActivity(),CheckOutActivity.class);
                intent.putExtra("cart_id",cart_id);
                intent.putExtra("ifcart", "1");
                startActivity(intent);
            }
        });
    }


    private String getStringCartId() {

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mCartIdList.size(); i++) {
            builder.append(mCartIdList.get(i));
        }
        String string = builder.deleteCharAt(0).toString();
        return  string;
    }

    // 设置选中的状态
    private void setCheckState() {
        int m = 0;
        for (int i = 0; i < mMarketData.size(); i++) {
            if(mMarketData.get(i).getIsCheck()){
                m++;
            }
        }
        // 被选中
        if(m>0){
            mBtCheck.setEnabled(true);
            mBtCheck.setText("结算（"+m+")");
            if(m==mMarketData.size()){  // 全部被选中
                mIsAll1 = true;
                mIsAll= true;
                mCb_Goods_All.setChecked(true);
                mCheckTitle.setChecked(true);
            }else {   // 不是全部被选中
                if (mIsAll1){
                    mIsAll=false;
                }else {
                    mIsAll = true;
                }
                mIsAll1 = false;
                mCb_Goods_All.setChecked(false);
                mCheckTitle.setChecked(false);
            }
        }else {
            mIsAll1 = false;
            mIsAll= true;
            mBtCheck.setEnabled(false);
            mBtCheck.setText("结算（0）");
        }
    }

    // 初始化数据
    private void initData() {

        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLACK,getResources().getColor(R.color.mainColor));

        mClient = new OkHttpClient();

        // 购物车数据
        mMarketData = new ArrayList<>();

        mCartIdList = new ArrayList<>();
        // 给ListView添加头部布局
        mMarket_lv.addHeaderView(mView_title);
//        mFresh_lv.addHeaderView(mView_title);

        mGoCartListViewAdapter = new GoCartListViewAdapter(getActivity(),mMarketData,this,this);

        loadNetworkData();
    }

    // 下载网络购物车数据
    private void loadNetworkData() {

        FormBody.Builder builder = new FormBody.Builder();
        //
        builder.add("key",key);
        Request request = new Request.Builder().url("http://shop.trqq.com/mobile/index.php?act=member_cart&op=cart_list").post(builder.build()).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();


                if (string != null) {

                    GoCartGoods GoCartGoods = JSON.parseObject(string, GoCartGoods.class);

                    Message message = new Message();
                    message.obj = GoCartGoods;
                    message.what = 100;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    private void setData() {

        mMarket_lv.setAdapter(mGoCartListViewAdapter);
        Drawable drawable = getResources().getDrawable(R.drawable.selector_press_color);
        mMarket_lv.setSelector(drawable);

        // 点击跳转到商品的详情页面
        mMarket_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                GoCartGoods.DatasBean.CartListBean cartListBean = mMarketData.get(position-1);
                String cart_id = cartListBean.getCart_id();
                Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    // 初始化视图
    private void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);

        mView_title = LayoutInflater.from(getActivity()).inflate(R.layout.cart_title, null);
        mTv_goods_change =(TextView) mView_title.findViewById(R.id.goods_chang);
        mStoreName = (TextView)mView_title.findViewById(R.id.gocart_lv_title);

        mMarket_lv = (ListView) view.findViewById(R.id.market_goods_lv);
//        mFresh_lv = (ListView) view.findViewById(R.id.market_fresh_lv);

        mCb_Goods_All = (CheckBox)view.findViewById(R.id.cb_gocart_all);
        mCheckTitle = (CheckBox)mView_title.findViewById(R.id.gocart_market_all);

        mTvPrice = (TextView)view.findViewById(R.id.tv_gocart_price);

        mBtCheck = (Button)view.findViewById(R.id.bt_main_ensure);
    }

    // 得到商品的总价
    private  Double getMoney()
    {
        Double money = 0.0;
        for (int i = 0; i < mMarketData.size(); i++) {

            if (mMarketData.get(i).getIsCheck())
            {
                int count=Integer.parseInt(mMarketData.get(i).getGoods_num());
                Double price=Double.parseDouble(mMarketData.get(i).getGoods_price());
                Double itemMoney=count*price;
                money+=itemMoney;
            }
        }
        return money;
    }

    // 点击事件回调方法
    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.gocart_lv_title:

                    break;

                case R.id.goods_chang:
                    mIsUse=!mIsUse;
                    if(mIsUse){
                        mTv_goods_change.setText("完成");
                    }else {
                        mTv_goods_change.setText("编辑");
                    }
                    mGoCartListViewAdapter.setDelete(mIsUse);
                    mGoCartListViewAdapter.notifyDataSetChanged();
                    break;

                // 点击添加数量
                case R.id.bt_item_add:

                    Integer addPosition = (Integer) v.getTag();
                    GoCartGoods.DatasBean.CartListBean cartListBean = mMarketData.get(addPosition);
                    cartListBean.setIsCheck(true);
                    int i = Integer.parseInt(cartListBean.getGoods_num());
                    cartListBean.setGoods_num(i+1+"");
                    //向服务器提交数量
                    String cart_id2 = cartListBean.getCart_id();
                    cart_edit_quantity(cart_id2,i+1+"");
                    mGoCartListViewAdapter.notifyDataSetChanged();
                    break;

                // 点击减少数量
                case R.id.bt_item_sub:

                    Integer subPosition = (Integer) v.getTag();
                    GoCartGoods.DatasBean.CartListBean cartListBean1 = mMarketData.get(subPosition);
                    String cart_id = cartListBean1.getCart_id();
                    // 设置为选中状态
                    cartListBean1.setIsCheck(true);
                    int num = Integer.parseInt(cartListBean1.getGoods_num());
                    if(num<2){
                        cartListBean1.setGoods_num(1+"");
                        // 向服务器提交数量
                        cart_edit_quantity(cart_id,1+"");
                    }else {
                        cartListBean1.setGoods_num((num-1)+"");

                        cart_edit_quantity(cart_id,(num-1)+"");
                    }
                    mGoCartListViewAdapter.notifyDataSetChanged();

                    break;

                // 点击删除按钮，通过后台删除相应的条目
                case R.id.tv_goods_delete:

                    Integer position= (Integer) v.getTag();
                    if (position != null) {

                        GoCartGoods.DatasBean.CartListBean cartListBean2 = mMarketData.get(position);

                        mMarketData.remove(cartListBean2);
                        String cart_id1 = cartListBean2.getCart_id();
                        // 删除购物车中的指定的商品
                        cartDetele(cart_id1);
                        mGoCartListViewAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    }


    // CheckBox 状态改变时就会回调这个方法
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.gocart_market_all:
                Log.d("mIsAll",mIsAll+"");
                if(mIsAll){   // 默认能全部选中的
                    mCb_Goods_All.setChecked(isChecked);
                    for (int i = 0; i < mMarketData.size(); i++) {
                        mMarketData.get(i).setIsCheck(isChecked);
                    }
                }else {
                    mIsAll = true;
                }
                mGoCartListViewAdapter.notifyDataSetChanged();
                break;

            // 全选CheckBox
            case R.id.cb_gocart_all:
                mCheckTitle.setChecked(isChecked);

                break;

            case R.id.cb_item_check:

                Integer position = (Integer) buttonView.getTag();
                GoCartGoods.DatasBean.CartListBean cartListBean = mMarketData.get(position);
                cartListBean.setIsCheck(isChecked);

                // 设置选中状态
                setCheckState();
                mGoCartListViewAdapter.notifyDataSetChanged();
                break;
        }
    }

    // 改变商品的数量时就会提交
    private void cart_edit_quantity(String cart_id, String quantity) {

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key", key);
        builder.add("cart_id", cart_id);
        builder.add("quantity", quantity);
        Request request = new Request.Builder().url("http://shop.trqq.com/mobile/index.php?act=member_cart&op=cart_edit_quantity").post(builder.build()).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if (string != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        String errStr = jsonObject.getString("error");
                        if (errStr != null) {
                            Toast.makeText(getActivity(),errStr,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                    }
                }
            }
        });
    }

    // 删除指定的商品
    private void cartDetele(String cart_id){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("key", key);
        builder.add("cart_id", cart_id);
        Request request = new Request.Builder().url("http://shop.trqq.com/mobile/index.php?act=member_cart&op=cart_del").post(builder.build()).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonString = response.body().string();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonString)
                            .getJSONObject("datas");
                    String errStr = jsonObject.getString("error");
                    if (errStr != null) {
                        Toast.makeText(getActivity(),errStr,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    try {
                        if (jsonObject.getString("datas")
                                .equals("1")) {
                            Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
                }
        });
    }
}
