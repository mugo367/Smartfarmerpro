package com.example.smartfarmer.ui.Employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartfarmer.Addemployee;
import com.example.smartfarmer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Employeeindex extends AppCompatActivity {
    EditText Password;
    Spinner Email;
    Button Login;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;

    private ArrayList<String> arraylist = new ArrayList<>();

    Addemployee addemployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employeeindex);

        Toolbar toolbar = findViewById(R.id.toolbaremplogin);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Employee Login");

        Email=findViewById(R.id.etemaillogin);
        Password=findViewById(R.id.etPasswordempLogin);
        Login=findViewById(R.id.btnempLogin);

        progressBar=findViewById(R.id.progressbarlogin);
        mFirebaseAuth=FirebaseAuth.getInstance();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("addemployee");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                arraylist.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){

                    addemployee = item.getValue(Addemployee.class);

                    assert addemployee != null;

                        arraylist.add(item.child("email").getValue(String.class));

                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Employeeindex.this, R.layout.support_simple_spinner_dropdown_item, arraylist);
                Email.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=Email.getSelectedItem().toString();
                final String password=Password.getText().toString();

               if(password.isEmpty()){
                    Password.setError("Please Enter your Password");
                    Password.requestFocus();
                }else if(!(email.isEmpty() && password.isEmpty() )){

                    progressBar.setVisibility(View.VISIBLE);

                   DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("addemployee");


                   ref1.orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           for(DataSnapshot item: dataSnapshot.getChildren()) {

                               addemployee = item.getValue(Addemployee.class);
                               assert addemployee != null;

                               if (addemployee.getPersonid().equals(password)) {
                                   Toast.makeText(Employeeindex.this, "Login Successful", Toast.LENGTH_SHORT).show();

                                   Intent intent  = new Intent(Employeeindex.this, Employeehome.class);
                                   intent.putExtra("name", email);

                                   startActivity(intent);
                                   finish();
                               } else {
                                   Toast.makeText(Employeeindex.this, "Login Failed, Please Try again", Toast.LENGTH_SHORT).show();
                               }
                           }

                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError error) {

                       }
                   });

               }
            }
        });
    }
}