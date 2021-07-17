package com.example.smartfarmer.ui.customer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class CustomerPagerViewerAdapter extends FragmentStatePagerAdapter {
    public CustomerPagerViewerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                MarketViewFragment productFragment = new MarketViewFragment();
                return productFragment;

            case 1:
                CustomerCartFragment cartFragment = new CustomerCartFragment();
                return cartFragment;



        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
