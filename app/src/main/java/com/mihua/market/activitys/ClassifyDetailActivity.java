package com.mihua.market.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.mihua.market.R;
import com.mihua.market.adapter.GoodsListPagerAdapter;
import com.mihua.market.fragments.GoodsListFragment;

import java.util.ArrayList;

public class ClassifyDetailActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ArrayList<GoodsListFragment> mFragments;
    private GoodsListPagerAdapter mPagerAdapter;
    private ArrayList<String> mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.market_activity_classify_detail);

        initView();
        initData();

        setData();
        setListener();
    }

    private void initView() {

        mTabLayout = (TabLayout) findViewById(R.id.classify_detail_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.classify_detail_viewpager);
    }

    private void initData() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        mFragments = new ArrayList<>();

        mTitles = new ArrayList<>();
        mTitles.add("新品上架");
        mTitles.add("热销商品");
        mTitles.add("最便宜");

        for (int i = 0; i < 3; i++) {
            GoodsListFragment fragment = new GoodsListFragment();

            bundle.putString("keytype",i+"");
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        mPagerAdapter = new GoodsListPagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
    }


    private void setData() {

        mViewPager.setAdapter(mPagerAdapter);

        // 与ViewPager 进行联动
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setListener() {

    }

}
