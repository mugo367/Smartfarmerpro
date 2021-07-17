package com.example.smartfarmer.ui.customer;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.smartfarmer.HomeActivity;
import com.example.smartfarmer.R;
import com.example.smartfarmer.forgotpasswordActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLoginActivity extends AppCompatActivity {
    EditText Email, Password;
    Button Login;
    TextView Forgot,SignUp;
    FirebaseAuth mFirebaseAuth;
    ProgressBar progressBar;
    Toast toast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);
        Toolbar toolbar = findViewById(R.id.toolbarlogincus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Customer Login");
        Email=findViewById(R.id.etEmailAddresscus);
        Password=findViewById(R.id.etPasswordLogincus);
        Login=findViewById(R.id.btnLogincus);
        Forgot=findViewById(R.id.forgotcus);
        SignUp=findViewById(R.id.tvdontcus);
        progressBar=findViewById(R.id.progressBarlcus);
        mFirebaseAuth=FirebaseAuth.getInstance();

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=Email.getText().toString();
                String password=Password.getText().toString();

                if(email.isEmpty()){
                    Email.setError("Please enter your Email");
                    Email.requestFocus();
                }else if(password.isEmpty()){
                    Password.setError("Please Enter your Password");
                    Password.requestFocus();
                }else if(email.isEmpty() && password.isEmpty() ){
                    toast=Toast.makeText(CustomerLoginActivity.this, "Fields Are Empty!",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else if(!(email.isEmpty() && password.isEmpty() )){

                    progressBar.setVisibility(View.VISIBLE);
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(CustomerLoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(CustomerLoginActivity.this, "Login Failed, Please Try again",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(CustomerLoginActivity.this, "Login Successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CustomerLoginActivity.this, CustomerActivity.class));
                                finish();
                            }

                        }
                    });
                }else {
                    Toast.makeText(CustomerLoginActivity.this, "Error Occurred!",Toast.LENGTH_SHORT).show();

                }


            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLoginActivity.this, CustomerRegisterActivity.class));
                finish();
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomerLoginActivity.this, forgotpasswordActivity.class));
            }
        });
    }
}