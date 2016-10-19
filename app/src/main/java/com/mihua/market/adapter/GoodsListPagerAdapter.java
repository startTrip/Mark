package com.mihua.market.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mihua.market.fragments.GoodsListFragment;

import java.util.ArrayList;

/**
 * Project: Market
 * Author: wm
 * Data:   2016/9/8
 */
public class GoodsListPagerAdapter extends FragmentPagerAdapter {

    ArrayList<GoodsListFragment> mList;
    ArrayList<String> mTitles;

    public GoodsListPagerAdapter(FragmentManager fm, ArrayList<GoodsListFragment> fragments, ArrayList<String> titles) {
        super(fm);
        mList = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);
    }
}
