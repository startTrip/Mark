package com.mihua.market.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mihua.market.R;
import com.mihua.market.bean.HomeClassifyTitle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/5
 */
public class HomeClassifyAdapter extends RecyclerView.Adapter<HomeClassifyAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<HomeClassifyTitle> mList ;

    private ClassifyClickCallback mClassifyClickCallback;
    private final int mWidthPixels;

    public interface ClassifyClickCallback{
        void onClassifyClick(int position);
    }

    public void setClassifyClickCallback(ClassifyClickCallback classifyClickCallback) {
        mClassifyClickCallback = classifyClickCallback;
    }

    public HomeClassifyAdapter(Context context, ArrayList<HomeClassifyTitle> classifyData) {
        mContext = context;
        mWidthPixels = mContext.getResources().getDisplayMetrics().widthPixels;
        mList = classifyData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.market_layout_home_classify_item, null,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        HomeClassifyTitle homeClassifyTitle = mList.get(position);
        Picasso.with(mContext)
                .load(homeClassifyTitle.getImgurl()).resize(mWidthPixels/10,mWidthPixels/10).centerCrop()
                .config(Bitmap.Config.RGB_565).into(holder.imageView);
        holder.mName.setText(homeClassifyTitle.getName());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClassifyClickCallback != null) {
                    mClassifyClickCallback.onClassifyClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList==null?0:mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        View mView;
        TextView mName;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imageView =(ImageView) itemView.findViewById(R.id.iv_home_classify_item);
            mName = (TextView) itemView.findViewById(R.id.tv_home_classify_item);
        }
    }
}
