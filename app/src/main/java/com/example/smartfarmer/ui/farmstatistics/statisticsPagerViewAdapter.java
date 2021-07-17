package com.example.smartfarmer.ui.farmstatistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class statisticsPagerViewAdapter  extends FragmentStatePagerAdapter {
    public statisticsPagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                productionFragment productionFragment= new productionFragment();
                return productionFragment;
            case 1:
                IncomeFragment incomeFragment=new IncomeFragment();
                return incomeFragment;

        }
        return null;
    }
    @Override
    public int getCount() {
        return 2;
    }
}

