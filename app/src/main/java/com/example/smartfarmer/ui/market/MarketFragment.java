package com.example.smartfarmer.ui.market;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.smartfarmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MarketFragment extends Fragment {

    private TextView profileLabel,usersLabel;
    private ViewPager mViewPager;
    private PagerViewAdapter pagerViewAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference myUsersDatabase;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_market, container, false);

        mAuth=FirebaseAuth.getInstance();
        myUsersDatabase= FirebaseDatabase.getInstance().getReference("Users");
        myUsersDatabase.keepSynced(true);
        profileLabel=root.findViewById(R.id.profileLabel);
        usersLabel=root.findViewById(R.id.usersLabel);
        mViewPager=root.findViewById(R.id.mainViewPager);


        profileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);
            }
        });
        usersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);

            }
        });

        pagerViewAdapter=new PagerViewAdapter(getChildFragmentManager());
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

            profileLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            profileLabel.setTextSize(22);
            usersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            usersLabel.setTextSize(16);

        }
        if (position==1)
        {

            profileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            profileLabel.setTextSize(16);
            usersLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            usersLabel.setTextSize(22);

        }

    }

}