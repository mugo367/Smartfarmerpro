package com.example.smartfarmer.ui.farmrecords;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartfarmer.R;

public class FarmRecordFragment extends Fragment {
    private TextView fielddetails, employeedetails, machinery;
    private ViewPager mViewPager;
    private FarmPagerViewAdapter pagerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_farmrecord, container, false);

        fielddetails=root.findViewById(R.id.FarmLabel);
        employeedetails=root.findViewById(R.id.EmployeeLabel);
        machinery=root.findViewById(R.id.EquipmentLabel);
        mViewPager = root.findViewById(R.id.farmmainViewPager);



        fielddetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);
            }
        });
        employeedetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);

            }
        });

        machinery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);

            }
        });

        pagerViewAdapter=new FarmPagerViewAdapter(getChildFragmentManager());
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

            fielddetails.setTextColor(getResources().getColor(R.color.textTabBright));
            fielddetails.setTextSize(22);
            employeedetails.setTextColor(getResources().getColor(R.color.textTabLight));
            employeedetails.setTextSize(16);
            machinery.setTextColor(getResources().getColor(R.color.textTabLight));
            machinery.setTextSize(16);

        }
        if (position==1)
        {

            fielddetails.setTextColor(getResources().getColor(R.color.textTabLight));
            fielddetails.setTextSize(16);
            employeedetails.setTextColor(getResources().getColor(R.color.textTabBright));
            employeedetails.setTextSize(22);
            machinery.setTextColor(getResources().getColor(R.color.textTabLight));
            machinery.setTextSize(16);

        }
        if (position==2)
        {

            fielddetails.setTextColor(getResources().getColor(R.color.textTabLight));
            fielddetails.setTextSize(16);
            employeedetails.setTextColor(getResources().getColor(R.color.textTabLight));
            employeedetails.setTextSize(16);
            machinery.setTextColor(getResources().getColor(R.color.textTabBright));
            machinery.setTextSize(22);

        }
    }
}
