package com.example.smartfarmer.ui.reports;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.smartfarmer.R;


public class ReportFragment extends Fragment {
Button designation, type, income, expense, production, equipment, task;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        designation = view.findViewById(R.id.employeedesignationreport);
        type = view.findViewById(R.id.employeetypereport);
        income = view.findViewById(R.id.incomereport);
        expense = view.findViewById(R.id.expensereport);
        production = view.findViewById(R.id.productionreport);
        equipment = view.findViewById(R.id.workingreport);
        task = view.findViewById(R.id.taskreport);


        designation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentd = new Intent(getContext(), employeedesignationReportActivity.class);
                startActivity(intentd);

            }
        });
        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getContext(), employeetypeReportActivity.class);
                startActivity(intentt);

            }
        });
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(getContext(), incomeReportActivity.class);
                startActivity(intenti);
            }
        });
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intente = new Intent(getContext(), expenseReportActivity.class);
                startActivity(intente);
            }
        });
        production.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentp = new Intent(getContext(), productionReportActivity.class);
                startActivity(intentp);
            }
        });
        equipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentw = new Intent(getContext(), workingequipReportActivity.class);
                startActivity(intentw);
            }
        });
        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(getContext(), TaskreportActivity.class);
                startActivity(intentt);
            }
        });

        return view;
    }
}