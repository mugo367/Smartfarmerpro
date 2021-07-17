package com.example.smartfarmer.ui.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.smartfarmer.R;
import com.example.smartfarmer.SmartFarmerActivity;

public class Employeehome extends AppCompatActivity {

    String Email;
    private Bundle results;
    private TextView tasklabel, reportlabel;
    private ViewPager mViewPager;
    private EmployeePagerViewAdapter pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeehome);

        Email=getIntent().getStringExtra("name");


        Toolbar toolbar = findViewById(R.id.toolbaremployee);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle("Employee Home");

        tasklabel = findViewById(R.id.taskLabel);
        reportlabel = findViewById(R.id.reportLabel);
        mViewPager = findViewById(R.id.employeeViewPager);

        tasklabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mViewPager.setCurrentItem(0);
            }
        });
        reportlabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);

            }
        });

        pagerViewAdapter=new EmployeePagerViewAdapter(getSupportFragmentManager());
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

    }
    private void changeTabs(int position) {
        if (position==0)
        {

            tasklabel.setTextColor(getResources().getColor(R.color.textTabBright));
            tasklabel.setTextSize(22);
            reportlabel.setTextColor(getResources().getColor(R.color.textTabLight));
            reportlabel.setTextSize(16);

        }
        if (position==1)
        {

            tasklabel.setTextColor(getResources().getColor(R.color.textTabLight));
            tasklabel.setTextSize(16);
            reportlabel.setTextColor(getResources().getColor(R.color.textTabBright));
            reportlabel.setTextSize(22);

        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employee, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logoutemp) {

            Intent intToIndex= new Intent(Employeehome.this, SmartFarmerActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intToIndex);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public Bundle getMyData(){
        Bundle hm = new Bundle();
        hm.putString("val1", Email);
        return hm;
    }
}