package com.mihua.market.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.base.ListViewBaseAdapter;
import com.mihua.market.bean.GoCartGoods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/30
 */
public class GoCartListViewAdapter extends ListViewBaseAdapter<GoCartGoods.DatasBean.CartListBean> {

    Context mContext;
    private boolean mIsUse;

    private View.OnClickListener  mListener;

    private CompoundButton.OnCheckedChangeListener mChangeListener;

    public GoCartListViewAdapter(Context context, List<GoCartGoods.DatasBean.CartListBean> datas,View.OnClickListener  listener,CompoundButton.OnCheckedChangeListener changeListener) {
        super(context, datas);
        mContext= context;
        mListener = listener;
        mChangeListener = changeListener;
    }

    // 用户点击删除按钮
    public void setDelete(boolean isUse)
    {
        mIsUse=isUse;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            convertView = getLayoutInflater().inflate(R.layout.market_gocart_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoCartGoods.DatasBean.CartListBean marketFoodBean= (GoCartGoods.DatasBean.CartListBean) getItem(position);

        if (!TextUtils.isEmpty(marketFoodBean.getGoods_image_url())){
            Picasso.with(mContext).load(marketFoodBean.getGoods_image_url()).into(holder.mImageViewIcon);
        }
        holder.mTextViewContent.setText(marketFoodBean.getGoods_name());
        holder.mTextViewCount.setText(marketFoodBean.getGoods_num());
        String price = String.format("%.1f",Double.parseDouble(marketFoodBean.getGoods_price()));
        holder.mTextViewPrice.setText("￥"+price);
        holder.mGoods_num.setText("x "+marketFoodBean.getGoods_num());
        if(mIsUse)
        {
            holder.mTextViewDelete.setVisibility(View.VISIBLE);
            holder.mGoods_info.setVisibility(View.GONE);
        }else
        {
            holder.mTextViewDelete.setVisibility(View.GONE);
            holder.mGoods_info.setVisibility(View.VISIBLE);
        }

        holder.mTextViewDelete.setEnabled(mIsUse);

//        给 BUTTON 设置一个tag方便在相应点击的时候区分 哪一个被点击了
        holder.mTextViewDelete.setTag(position);
        holder.mTextViewDelete.setOnClickListener(mListener);

        holder.mButtonSub.setTag(position);
        holder.mButtonSub.setOnClickListener(mListener);

        holder.mButtonAdd.setTag(position);
        holder.mButtonAdd.setOnClickListener(mListener);

        holder.mCheckBox.setTag(position);
        holder.mCheckBox.setOnCheckedChangeListener(mChangeListener);

        holder.mCheckBox.setChecked(marketFoodBean.getIsCheck());

        return convertView;
    }

    public static class ViewHolder{

        private TextView mGoods_num;
        public CheckBox mCheckBox;
        public ImageView mImageViewIcon;
        public TextView mTextViewContent,mTextViewPrice,mTextViewCount,mTextViewDelete, mTextViewWeight;
        public Button mButtonSub,mButtonAdd;
        public RelativeLayout mGoods_info;

        public ViewHolder(View itemview)
        {

            mCheckBox= (CheckBox) itemview.findViewById(R.id.cb_item_check);
            mImageViewIcon= (ImageView) itemview.findViewById(R.id.iv_item_icon);
            mTextViewContent= (TextView) itemview.findViewById(R.id.tv_item_content);
            mTextViewCount= (TextView) itemview.findViewById(R.id.tv_item_count);
            mButtonAdd= (Button) itemview.findViewById(R.id.bt_item_add);
            mButtonSub= (Button) itemview.findViewById(R.id.bt_item_sub);
            mTextViewDelete= (TextView) itemview.findViewById(R.id.tv_goods_delete);
            mTextViewPrice= (TextView) itemview.findViewById(R.id.tv_goods_price);
            mTextViewWeight = (TextView) itemview.findViewById(R.id.tv_goods_weight);
            mGoods_info = (RelativeLayout) itemview.findViewById(R.id.goods_info);
            mGoods_num = (TextView) itemview.findViewById(R.id.gocart_goods_num);
        }

    }
}
