package com.example.smartfarmer.ui.farmrecords;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FarmPagerViewAdapter extends FragmentStatePagerAdapter {
    public FarmPagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FieldFragment fieldFragment = new FieldFragment();
                return fieldFragment;
            case 1:
                EmployeeFragment employeeFragment=new EmployeeFragment();
                return employeeFragment;
            case 2:
                EquipmentFragment equipmentFragment=new EquipmentFragment();
                return equipmentFragment;
        }
        return null;
    }
    @Override
    public int getCount() {
        return 3;
    }
}
