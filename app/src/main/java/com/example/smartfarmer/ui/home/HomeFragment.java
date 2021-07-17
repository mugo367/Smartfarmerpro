package com.example.smartfarmer.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.smartfarmer.FlipperAdapter;
import com.example.smartfarmer.R;
import com.github.mikephil.charting.charts.BarChart;

public class HomeFragment extends Fragment {
    private AdapterViewFlipper adapterViewFlipper;
    private static final String[] TEXT ={"ADD YOUR FARM RECORDS", "MANAGE YOUR FARM BETTER", "GET TIMELY INFO" , "BOOST YOUR PRODUCTION"};
    private static final int[] IMAGES ={R.drawable.maizefarm2, R.drawable.maizefarm3, R.drawable.maizefarm4, R.drawable.maizegrain };
    private int mPosition =-1;
    BarChart barchart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        adapterViewFlipper = root.findViewById(R.id.adapterviewflipperhome);


        FlipperAdapter adapter = new FlipperAdapter(getContext(), TEXT, IMAGES);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);



        return root;
    }
}