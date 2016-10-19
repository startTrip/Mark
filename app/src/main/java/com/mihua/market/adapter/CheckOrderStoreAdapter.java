package com.mihua.market.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.base.ListViewBaseAdapter;
import com.mihua.market.bean.GoodsInfo;

import java.util.List;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/10/13
 */
public class CheckOrderStoreAdapter extends ListViewBaseAdapter<GoodsInfo> {

    private  Context mContext;
    private CheckOrderGoodsAdapter mCheckOrderGoodsAdapter;

    public CheckOrderStoreAdapter(Context context, List<GoodsInfo> datas) {
        super(context, datas);
        mContext = context ;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = getLayoutInflater().inflate(R.layout.market_check_store_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsInfo goodsInfo = (GoodsInfo) getItem(position);
        Log.d("bbbb",goodsInfo.getStore_name());
        if (goodsInfo != null) {

            String store_name = goodsInfo.getStore_name();
            String store_goods_total = goodsInfo.getStore_goods_total();
            int goodsNum = goodsInfo.getGoods_list().size();
            holder.mSum_money.setText("￥ "+store_goods_total);
            holder.mStoreName.setText(store_name);
            holder.mGoodsInfo.setText("共"+goodsNum+"件商品，总重3kg  小计:");

            List<GoodsInfo.GoodsListBean> goods_list = goodsInfo.getGoods_list();
            mCheckOrderGoodsAdapter = new CheckOrderGoodsAdapter(mContext,goods_list);
            holder.mListView.setAdapter(mCheckOrderGoodsAdapter);
            holder.mListView.setEnabled(false);
        }

        return convertView;
    }

    public static class ViewHolder{

        public TextView mStoreName,mFreight,mArrive_time,mGoodsInfo,mSum_money;
        public ListView mListView;
        public ViewHolder(View itemview)
        {
            mStoreName = (TextView)itemview.findViewById(R.id.store_name);
            mListView = (ListView)itemview.findViewById(R.id.goods_list);
            mFreight = (TextView)itemview.findViewById(R.id.freight);
            mArrive_time = (TextView)itemview.findViewById(R.id.arrive_time);
            mGoodsInfo = (TextView)itemview.findViewById(R.id.store_goods_info);
            mSum_money = (TextView) itemview.findViewById(R.id.sum_money);
        }

    }
}
