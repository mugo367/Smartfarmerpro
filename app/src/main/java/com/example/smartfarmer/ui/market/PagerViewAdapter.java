package com.example.smartfarmer.ui.market;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerViewAdapter extends FragmentStatePagerAdapter {

    public PagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
               ProductFragment productFragment = new ProductFragment();
               return productFragment;
            case 1:
                CartFragment cartFragment=new CartFragment();
                return cartFragment;


        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
