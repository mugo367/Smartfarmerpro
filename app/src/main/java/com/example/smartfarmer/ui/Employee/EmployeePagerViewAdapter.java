package com.example.smartfarmer.ui.Employee;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class EmployeePagerViewAdapter extends FragmentStatePagerAdapter {
    public EmployeePagerViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Employeefragmenthome employeefragmenthome = new Employeefragmenthome();
                return employeefragmenthome;
            case 1:
                EmployeeReportFragment employeereportFragment=new EmployeeReportFragment();
                return employeereportFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
