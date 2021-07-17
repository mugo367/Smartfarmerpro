package com.example.smartfarmer.ui.news;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class newsPagerViewAdapter extends FragmentStatePagerAdapter {

    public newsPagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                subnews1Fragment subnews1Fragment = new subnews1Fragment();
                return subnews1Fragment;
            case 1:
                subnews2Fragment subnews2Fragment = new subnews2Fragment();
                return subnews2Fragment;
            case 2:
                subnews3Fragment subnews3Fragment = new subnews3Fragment();
                return subnews3Fragment;
            case 3:
                subnews4Fragment subnews4Fragment = new subnews4Fragment();
                return subnews4Fragment;
            case 4:
                subnews5Fragment subnews5Fragment = new subnews5Fragment();
                return subnews5Fragment;
            case 5:
                karloFragment karloFragment = new karloFragment();
                return karloFragment;
            case 6:
                nafisFragment nafisFragment = new nafisFragment();
                return nafisFragment;
            case 7:
                googleFragment googleFragment = new googleFragment();
                return googleFragment;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }
}