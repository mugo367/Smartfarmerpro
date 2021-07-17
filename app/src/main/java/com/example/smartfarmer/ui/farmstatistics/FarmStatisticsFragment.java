package com.example.smartfarmer.ui.farmstatistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartfarmer.R;

public class FarmStatisticsFragment extends Fragment {
    TextView income, production;
    private ViewPager mViewPager;
    private statisticsPagerViewAdapter pagerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_farmstatistics, container, false);

        production=root.findViewById(R.id.productionLabel);
        income=root.findViewById(R.id.incomeLabel);
        mViewPager = root.findViewById(R.id.mainViewPagerincome);



        production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);
            }
        });
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);

            }
        });


        pagerViewAdapter=new statisticsPagerViewAdapter(getChildFragmentManager());
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

        return root;
    }

    private void changeTabs(int position) {
        if (position==0)
        {

            production.setTextColor(getResources().getColor(R.color.textTabBright));
            production.setTextSize(22);
            income.setTextColor(getResources().getColor(R.color.textTabLight));
            income.setTextSize(16);


        }
        if (position==1)
        {

            production.setTextColor(getResources().getColor(R.color.textTabLight));
            production.setTextSize(16);
            income.setTextColor(getResources().getColor(R.color.textTabBright));
            income.setTextSize(22);

        }
    }
}
