package com.mihua.market.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.base.ListViewBaseAdapter;
import com.mihua.market.bean.GoodsInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/10/13
 */
public class CheckOrderGoodsAdapter extends ListViewBaseAdapter<GoodsInfo.GoodsListBean> {


    private  Context mContext;

    public CheckOrderGoodsAdapter(Context context, List<GoodsInfo.GoodsListBean> datas) {
        super(context, datas);
        mContext = context ;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = getLayoutInflater().inflate(R.layout.market_check_goods_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsInfo.GoodsListBean goodsListBean = (GoodsInfo.GoodsListBean) getItem(position);
        if (goodsListBean != null) {
            if (!TextUtils.isEmpty(goodsListBean.getGoods_image_url())){
                Picasso.with(mContext).load(goodsListBean.getGoods_image_url()).into(holder.mImageView);
            }
            holder.mGoodsName.setText("【"+goodsListBean.getStore_name()+"】"+goodsListBean.getGoods_name());
            String price = String.format("%.1f",Double.parseDouble(goodsListBean.getGoods_price()));
            holder.mGoods_price.setText("￥"+price);
            holder.mGoods_num.setText("x"+goodsListBean.getGoods_num());
        }
        return convertView;
    }

    public static class ViewHolder{

        public ImageView mImageView;
        public TextView mGoodsName,mGoods_price,mGoods_weight,mGoods_num;
        public ViewHolder(View itemview)
        {
            mImageView = (ImageView) itemview.findViewById(R.id.iv_item_icon);
            mGoodsName = (TextView) itemview.findViewById(R.id.tv_item_content);
            mGoods_price = (TextView) itemview.findViewById(R.id.tv_goods_price);
            mGoods_weight = (TextView) itemview.findViewById(R.id.tv_goods_weight);
            mGoods_num = (TextView) itemview.findViewById(R.id.tv_goods_num);
        }

    }
}
