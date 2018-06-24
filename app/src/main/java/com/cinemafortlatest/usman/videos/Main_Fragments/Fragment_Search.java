package com.cinemafortlatest.usman.videos.Main_Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.cinemafortlatest.usman.videos.ADAPTERS.Pager_Adapter;
import com.cinemafortlatest.usman.videos.R;
import com.cinemafortlatest.usman.videos.Search_Fragments.Fragments_Actors;
import com.cinemafortlatest.usman.videos.Search_Fragments.Fragment_Movies;
import com.cinemafortlatest.usman.videos.Search_Fragments.Fragment_Tv_Shows;

import java.util.List;
import java.util.Vector;

/**
 * Created by usman on 5/3/2017.
 */

public class Fragment_Search extends Fragment implements TabHost.OnTabChangeListener,ViewPager.OnPageChangeListener {
    TabHost tabHost;
    ViewPager viewPager;
    Pager_Adapter myFragmentPagerAdapter;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         v=inflater.inflate(R.layout.activity_search,container,false);
        setHasOptionsMenu(true);

        Initialize_Viewpager(v);
        initializeTabHost(v);
        return v;
    }


    private void Initialize_Viewpager(View v) {
        List<Fragment> fragments=new Vector<>();
        fragments.add(new Fragments_Actors());
        fragments.add(new Fragment_Movies());
        fragments.add(new Fragment_Tv_Shows());
        this.myFragmentPagerAdapter = new Pager_Adapter(
                getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myFragmentPagerAdapter);

    }
    private void initializeTabHost(View v) {

        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();
        String[] data = {"Fragments_Actors","Fragment_Movies","TV Shows"};

        for (int i = 0; i <= 2; i++) {

            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(data[i]);
            tabSpec.setIndicator(data[i]);
            tabSpec.setContent(new Fragment_Search.FakeContent(getContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        this.tabHost.setCurrentTab(position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String tabId) {
        int a=tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(a);
        HorizontalScrollView hScrollView = (HorizontalScrollView) v.findViewById(R.id.hScrollView);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (hScrollView.getWidth() - tabView.getWidth()) / 2;
        hScrollView.smoothScrollTo(scrollPos, 0);

    }


    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }
}

