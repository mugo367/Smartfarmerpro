package com.example.smartfarmer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpasswordActivity extends AppCompatActivity {
    EditText Email;
    Button Request, Back;
    ProgressBar progressBar;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        Email=findViewById(R.id.etEmailreset);
        Request=findViewById(R.id.reset_pass_btn);
        Back=findViewById(R.id.back_btn);
        progressBar=findViewById(R.id.progressBarreset);
        mFirebaseAuth=FirebaseAuth.getInstance();

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString();

                if(email.isEmpty()){
                    Email.setError("Please enter your Email");
                    Email.requestFocus();
                }else {
                  mFirebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(forgotpasswordActivity.this, new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                          if(!task.isSuccessful()){
                              Toast.makeText(forgotpasswordActivity.this, "Unable to send reset mail",Toast.LENGTH_SHORT).show();
                          }else{
                              Toast.makeText(forgotpasswordActivity.this, "Reset link Sent to your email",Toast.LENGTH_SHORT).show();
                          }

                      }
                  });
                }
            }
        });
    }
}