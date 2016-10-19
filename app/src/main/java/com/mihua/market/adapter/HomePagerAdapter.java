package com.mihua.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mihua.market.activitys.BannerDetailActivity;
import com.mihua.market.bean.HomeTopImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/8/31
 */
public class HomePagerAdapter extends PagerAdapter implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private Context mContext;
    private ArrayList<ImageView> mList;
    private ArrayList<HomeTopImage.DataBean> mDataBeen;
    private ViewPager mViewPager;

    public HomePagerAdapter(Context context, ArrayList<HomeTopImage.DataBean> topData, ViewPager topPager) {
        mContext = context;
        if (topData != null) {
            mDataBeen = topData;
        }else {
            mDataBeen = new ArrayList<>();
        }

        mList = new ArrayList<>();

        for (int i = 0; i < mDataBeen.size() + 2; i++) {
            ImageView imageView = new ImageView(mContext);
            mList.add(imageView);
        }
        mViewPager = topPager;
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public int getCount() {

        return mDataBeen==null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("AAA",position+"");

        ImageView imageView =mList.get(position);

        if(mDataBeen.size()>0){

            HomeTopImage.DataBean dataBean;
            if (position==0){
                dataBean = mDataBeen.get(mDataBeen.size()-1);
            }else if(position==mList.size()-1){
                dataBean = mDataBeen.get(0);
            }else {
                dataBean = mDataBeen.get(position-1);
            }
            String img_url = dataBean.getImg_url();

            if (img_url != null) {
                Picasso.with(mContext).load(img_url)
                        .config(Bitmap.Config.RGB_565)
                            .into(imageView);
            }

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i = mList.indexOf(v);

                    Log.d("AAA","onClick:"+i);
                    Intent intent = new Intent(mContext, BannerDetailActivity.class);

                    mContext.startActivity(intent);
                }
            });

            container.addView(imageView);
        }
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
         if(position <1 ){
             mViewPager.setCurrentItem(mDataBeen.size(),false); //首位之前跳转到末尾
         }else if(position > mDataBeen.size()){

             mViewPager.setCurrentItem(1,false);    // 末位之后跳转到首位
         }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
