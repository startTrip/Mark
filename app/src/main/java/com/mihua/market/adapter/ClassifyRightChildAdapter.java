package com.mihua.market.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.bean.ClassifyData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/7
 */
public class ClassifyRightChildAdapter extends RecyclerView.Adapter<ClassifyRightChildAdapter.ViewHolder>{

    private final int mIndex;
    private Context mContext;
    private ArrayList<ClassifyData.InforBean.ListItemsBean.SonItemsBean.SonItemsBean1> mList;
    private final int mWidthPixels;
    private RecyclerView mRecyclerView;

    private onClassifyRightChildClick mOnClassifyRightClick;

    public interface onClassifyRightChildClick{
        void onClassifyRightChildClick(int index,int position);
    }

    public void setOnClassifyRightClick(onClassifyRightChildClick onClassifyRightClick) {
        mOnClassifyRightClick= onClassifyRightClick;
    }

    public ClassifyRightChildAdapter(Context context,int index,List<ClassifyData.InforBean.ListItemsBean.SonItemsBean.SonItemsBean1> sonItems) {
        mContext = context;
        mIndex = index;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        mWidthPixels = displayMetrics.widthPixels;
        mList = (ArrayList<ClassifyData.InforBean.ListItemsBean.SonItemsBean.SonItemsBean1>) sonItems;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        mRecyclerView=null;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.market_classify_right_child_item, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = mRecyclerView.getChildAdapterPosition(v);
                if (mOnClassifyRightClick != null) {

                    mOnClassifyRightClick.onClassifyRightChildClick(mIndex,position);
                }
                Log.d("position",mIndex+"mIndex");
                Log.d("position",position+"position");
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        
        ClassifyData.InforBean.ListItemsBean.SonItemsBean.SonItemsBean1 sonItemsBean1 = mList.get(position);
        if (sonItemsBean1 != null) {
            holder.mTextView.setText(sonItemsBean1.getType());
            holder.mImageView.setMinimumWidth(mWidthPixels*5/21);
            Picasso.with(mContext)
                    .load(sonItemsBean1.getPhoto()).resize(mWidthPixels*5/21,mWidthPixels*5/21).centerCrop()
                    .config(Bitmap.Config.RGB_565).into(holder.mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.classify_right_child_iv);
            mTextView = (TextView)itemView.findViewById(R.id.classify_right_child_tv);
        }
    }
}
