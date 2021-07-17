package com.example.smartfarmer.ui.customer;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class CustomerActivity extends AppCompatActivity {

    private TextView profileLabel,usersLabel;
    private ViewPager mViewPager;
    private CustomerPagerViewerAdapter pagerViewAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference myUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Toolbar toolbar = findViewById(R.id.toolcus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("SmartFarmer Market Place");

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CustomerActivity.this);
        alertBuilder.setTitle("SmartFarmer Markert");
        alertBuilder.setMessage("Welcome to SmartFarmer market. Get products as posted by our Farmers. " +
                "Product Categories are Grain, Fodder or Both. ");

        alertBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = alertBuilder.create();
        mDialog.show();

        profileLabel=findViewById(R.id.profileLabelcus);
        usersLabel=findViewById(R.id.usersLabelcus);

        mViewPager=findViewById(R.id.mainViewPagercustomer);
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


        pagerViewAdapter=new CustomerPagerViewerAdapter(getSupportFragmentManager());
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

            profileLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            profileLabel.setTextSize(22);
            usersLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            usersLabel.setTextSize(15);

        }

        if (position==1)
        {
            profileLabel.setTextColor(getResources().getColor(R.color.textTabLight));
            profileLabel.setTextSize(15);
            usersLabel.setTextColor(getResources().getColor(R.color.textTabBright));
            usersLabel.setTextSize(22);

        }


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.customer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logoutcus) {
            FirebaseAuth.getInstance().signOut();
            Intent intToIndex= new Intent(CustomerActivity.this, SmartFarmerActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity(intToIndex);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}