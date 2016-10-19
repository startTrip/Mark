package com.mihua.market.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.bean.GoodsListData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/8
 */
public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder>{

    Context mContext;
    ArrayList<GoodsListData.ListItemsBean> mList;
    private final int mWidthPixels;

    public GoodsListAdapter(Context context, ArrayList<GoodsListData.ListItemsBean> list) {
        mContext = context;
        mList = list;
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.market_goods_list_item, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GoodsListData.ListItemsBean listItemsBean = mList.get(position);
        if (listItemsBean != null) {
            holder.mTitle.setText(listItemsBean.getGoodsname());
            holder.mPrice.setText(listItemsBean.getPrice());
            if(!TextUtils.isEmpty(listItemsBean.getImg())){

                Picasso.with(mContext)
                        .load(listItemsBean.getImg()).resize(mWidthPixels/3,mWidthPixels/3).centerCrop()
                        .config(Bitmap.Config.RGB_565).into(holder.mImageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mPrice;
        private TextView mTitle;

        public ViewHolder(View itemView) {

            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.goodsList_item_title);
            mPrice = (TextView) itemView.findViewById(R.id.goodsList_item_price);
            mImageView = (ImageView) itemView.findViewById(R.id.goodsList_item_iv);
        }
    }
}
