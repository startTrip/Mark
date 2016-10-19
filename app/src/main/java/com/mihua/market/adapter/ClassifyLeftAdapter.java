package com.mihua.market.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mihua.market.R;

import java.util.LinkedList;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/6
 */
public class ClassifyLeftAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<String> mLinkedList;
    public ClassifyLeftAdapter(Context context, LinkedList<String> leftData) {
        mContext = context;
        mLinkedList = leftData;
    }

    @Override
    public int getCount() {
        return mLinkedList==null?0:mLinkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return mLinkedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.market_classify_left_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.tv_item_classify);
            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.mTextView.setText( mLinkedList.get(position));

        return convertView;
    }

     class ViewHolder{
        public TextView mTextView;
    }
}
