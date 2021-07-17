package com.example.smartfarmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartfarmer.ui.Employee.Employeeindex;
import com.example.smartfarmer.ui.customer.CustomerLoginActivity;

public class SmartFarmerActivity extends AppCompatActivity {

    private AdapterViewFlipper adapterViewFlipper;

    private static final int[] IMAGES ={R.drawable.maizefarm2, R.drawable.maizefarm3, R.drawable.maizefarm4, R.drawable.maizegrain };
    private static final String[] TEXT ={"ADD YOUR FARM RECORDS", "MANAGE YOUR FARM BETTER", "GET TIMELY INFO" , "BOOST YOUR PRODUCTION"};
    private int mPosition =-1;
    Button farmer, employee, market;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_farmer);

        farmer= findViewById(R.id.smartfarmerbutton);
        adapterViewFlipper = findViewById(R.id.adapterviewflippersmart);
        employee = findViewById(R.id.smartfarmeremployee);
        market = findViewById(R.id.smartmarketbutton);

        FlipperAdapter adapter = new FlipperAdapter(this,TEXT, IMAGES);
        adapterViewFlipper.setAdapter(adapter);
        adapterViewFlipper.setAutoStart(true);

        farmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inten = new Intent(SmartFarmerActivity.this, LoginActivity.class);
                startActivity(inten);
            }
        });
        employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartFarmerActivity.this, Employeeindex.class);
                startActivity(intent);
            }
        });
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmartFarmerActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}