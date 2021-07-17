package com.example.smartfarmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText Fullname, Username, phonenumber,  email, Password, Confirmpassword, fieldname, fieldsize, subcounty;
    Spinner  County;
    Button Register, Login;
    ProgressBar progressbar;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference reference;
    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbarregister);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Farmer Registration");

        Fullname = findViewById(R.id.etFullname);
        Username = findViewById(R.id.etusername);
        phonenumber = findViewById(R.id.etRegPhone);
        email=findViewById(R.id.etEmailregister);
        Password=findViewById(R.id.etPassword);
        Confirmpassword=findViewById(R.id.etPassword2);

        County = findViewById(R.id.countylocation);
        subcounty = findViewById(R.id.subcounty);
        fieldname = findViewById(R.id.fieldnameregister);
        fieldsize = findViewById(R.id.fieldsizeregister);

        Register=findViewById(R.id.registerbutton);
        Login=findViewById(R.id.btnGotoLogin);
        progressbar=findViewById(R.id.progressBar);

        mFirebaseAuth=FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt_fname = Fullname.getText().toString();
                String txt_user = Username.getText().toString();
                String txt_phone = phonenumber.getText().toString();
                String  txt_email= email.getText().toString();
                String txt_password=Password.getText().toString();
                String txt_confpassword=Confirmpassword.getText().toString();
                String txt_county = County.getSelectedItem().toString();
                String txt_subcounty = subcounty.getText().toString();
                String txt_fieldname = fieldname.getText().toString();
                String txt_fieldsize = fieldsize.getText().toString();

                String emailpattern = "[a-zA-z]+.[a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]+.[a-zA-Z.]+.[a-zA-Z]";

                if(txt_fname.isEmpty()) {
                    Fullname.setError("Please enter your Full Name");
                    Fullname.requestFocus();
                }else if(txt_user.isEmpty()){
                    Username.setError("Please enter your Username");
                    Username.requestFocus();
                }else if(txt_phone.isEmpty()) {
                    phonenumber.setError("Please enter your Phone Number");
                    phonenumber.requestFocus();
                }else if(txt_email.isEmpty()){
                    email.setError("Please enter your Email");
                    email.requestFocus();
                }else if(!txt_email.matches(emailpattern)){
                    email.setError("Please enter a valid email address");
                    email.requestFocus();
                }else if(txt_password.isEmpty()){
                    Password.setError("Please Enter your Password");
                    Password.requestFocus();
                }else if(txt_confpassword.isEmpty()){
                    Confirmpassword.setError("Please Confirm your Password");
                    Confirmpassword.requestFocus();
                }else if(txt_subcounty.isEmpty()){
                    subcounty.setError("Please enter your Sub-County");
                    subcounty.requestFocus();
                }else if(txt_fieldname.isEmpty()){
                    fieldname.setError("Please enter your Farm Name");
                    fieldname.requestFocus();
                }else if(txt_fieldsize.isEmpty()){
                    fieldsize.setError("Please Confirm your Password");
                    fieldsize.requestFocus();
                }else if(txt_password.length()<8){
                    Password.setError("Please Enter a Stronger Password");
                    Password.requestFocus();
                }else if(!(txt_password.equals(txt_confpassword))){
                    Confirmpassword.setError("Passwords Don't Match");
                    Confirmpassword.requestFocus();
                }else if(!(txt_fname.isEmpty() && txt_user.isEmpty() && txt_phone.isEmpty() && txt_email.isEmpty() && txt_password.isEmpty() && txt_confpassword.isEmpty() && txt_subcounty.isEmpty() && txt_fieldname.isEmpty() && txt_fieldsize.isEmpty()) && (txt_password.equals(txt_confpassword))){
                    progressbar.setVisibility(View.VISIBLE);
                    register(txt_fname, txt_user,txt_phone, txt_email, txt_password, txt_county,txt_subcounty, txt_fieldname, txt_fieldsize);

                }else {
                    Toast.makeText(RegisterActivity.this, "Error Occurred!",Toast.LENGTH_SHORT).show();

                }
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void register(final String txt_fname, final String txt_user, final String txt_phone, String txt_email, String txt_password, final String txt_county, final String txt_subcounty, final String txt_fieldname, final String txt_fieldsize) {

        mFirebaseAuth.createUserWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    FirebaseUser firebaseUser=mFirebaseAuth.getInstance().getCurrentUser();
                    assert firebaseUser != null;
                    String userid=firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String, String>hashMap=new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("Fullname", txt_fname);
                    hashMap.put("Username", txt_user);
                    hashMap.put("PhoneNumber", txt_phone);
                    hashMap.put("County", txt_county);
                    hashMap.put("SubCounty", txt_subcounty);
                    hashMap.put("Farmname", txt_fieldname);
                    hashMap.put("Fieldsize", txt_fieldsize);
                    hashMap.put("imageURL", "default");
                    hashMap.put("status", "offline" );

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "SignUp Successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                intent.addFlags((Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this, "SignUp UnSuccessful, Please try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}