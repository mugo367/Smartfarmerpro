package com.example.smartfarmer.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartfarmer.R;

public class NewsFragment extends Fragment {
    TextView news1, news2, news3, news4,news5,  karlo, nafis, google;
    private newsPagerViewAdapter pagerViewAdapter;
    private ViewPager mViewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        news1 = view.findViewById(R.id.newsLabel);
        news2 = view.findViewById(R.id.news1Label);
        news3 = view.findViewById(R.id.news2Label);
        news4 = view.findViewById(R.id.news3Label);
        news5 = view.findViewById(R.id.news4Label);
        karlo = view.findViewById(R.id.karloLabel);
        nafis = view.findViewById(R.id.nafisLabel);
        google = view.findViewById(R.id.googleLabel);

        mViewPager = view.findViewById(R.id.newsmainViewPager);


        news1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);
            }
        });
        news2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);

            }
        });
        news3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);

            }
        });
        news4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(3);

            }
        });
        news5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(4);

            }
        });
        karlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(5);

            }
        });
        nafis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(6);

            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(7);

            }
        });

        pagerViewAdapter=new newsPagerViewAdapter(getChildFragmentManager());
        mViewPager.setAdapter(pagerViewAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position ,float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int i) {

                changeTabs(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        return view;
    }

    private void changeTabs(int position) {
        if (position==0)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabBright));
            news1.setTextSize(22);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==1)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabBright));
            news2.setTextSize(22);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==2)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabBright));
            news3.setTextSize(22);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==3)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabBright));
            news4.setTextSize(22);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==4)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabBright));
            news5.setTextSize(22);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==5)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabBright));
            karlo.setTextSize(22);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==6)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabBright));
            nafis.setTextSize(22);
            google.setTextColor(getResources().getColor(R.color.textTabLight));
            google.setTextSize(8);

        }
        if (position==7)
        {

            news1.setTextColor(getResources().getColor(R.color.textTabLight));
            news1.setTextSize(8);
            news2.setTextColor(getResources().getColor(R.color.textTabLight));
            news2.setTextSize(8);
            news3.setTextColor(getResources().getColor(R.color.textTabLight));
            news3.setTextSize(8);
            news4.setTextColor(getResources().getColor(R.color.textTabLight));
            news4.setTextSize(8);
            news5.setTextColor(getResources().getColor(R.color.textTabLight));
            news5.setTextSize(8);
            karlo.setTextColor(getResources().getColor(R.color.textTabLight));
            karlo.setTextSize(8);
            nafis.setTextColor(getResources().getColor(R.color.textTabLight));
            nafis.setTextSize(8);
            google.setTextColor(getResources().getColor(R.color.textTabBright));
            google.setTextSize(22);

        }

    }
}