package com.example.smartfarmer.ui.farmrecords;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addemployee;
import com.example.smartfarmer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class EmployeeFragment extends Fragment {

    private Dialog addemployeedialog;
    private FloatingActionButton fbaddemployee;
    private EditText employeename, employeeid,employeeemail ,employeephone, employeeemergency, emergencycontact;
    TextView dateofemp;
    DatePickerDialog.OnDateSetListener from_datelistener;
    Spinner employeedesignation, employeetype;
    private Button addemployee;
    private ProgressDialog pd;
    FirebaseAuth mFirebaseAuth;
    private RecyclerView recyclerView;

    Addemployee maddemployee;
    ArrayList<Addemployee> addemployeeArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_employee, container, false);


        fbaddemployee = view.findViewById(R.id.fbemployee);

        addemployeedialog =new Dialog(getContext());
        addemployeedialog.setContentView(R.layout.addemployeedetails);

        employeename = addemployeedialog.findViewById(R.id.employeename);
        employeeid = addemployeedialog.findViewById(R.id.employeeid);
        employeeemail = addemployeedialog.findViewById(R.id.employeeemail);
        employeephone = addemployeedialog.findViewById(R.id.employeecontact);
        employeedesignation = addemployeedialog.findViewById(R.id.employeedesignation);
        employeetype = addemployeedialog.findViewById(R.id.employeetype);
        dateofemp = addemployeedialog.findViewById(R.id.employeedateemp);
        employeeemergency = addemployeedialog.findViewById(R.id.employeecontactperson);
        emergencycontact = addemployeedialog.findViewById(R.id.employeephonenumber);

        mFirebaseAuth=FirebaseAuth.getInstance();

        pd=new ProgressDialog(getContext());
        pd.setTitle("Adding Employee");
        pd.setMessage("Please wait...");

        fbaddemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddemployeedialog();
            }
        });

        recyclerView = view.findViewById(R.id.employeeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void bringaddemployeedialog() {
        addemployee = addemployeedialog.findViewById(R.id.addemp);
        dateofemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), from_datelistener, year, month, day);
                datePickerDialog.show();

                from_datelistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                        month1 =month1+1;
                        String date1 = dayOfMonth1+"/"+month1+"/"+year1;
                        dateofemp.setText(date1);

                    }
                };

            }
        });

        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String memployeename= employeename.getText().toString();
                String memployeeid = employeeid.getText().toString();
                String memployeeemail = employeeemail.getText().toString();
                String memployeephone = employeephone.getText().toString();
                String memployeedesignation = employeedesignation.getSelectedItem().toString();
                String memployeetype = employeetype.getSelectedItem().toString();
                String dateemp = dateofemp.getText().toString();
                String memployeeemergency = employeeemergency.getText().toString();
                String memergencyconctact = emergencycontact.getText().toString();

                String emailpattern = "[a-zA-z]+.[a-zA-Z0-9]+@[a-zA-Z]+.[a-zA-Z]+.[a-zA-Z.]+.[a-zA-Z]";



                if(memployeename.isEmpty()) {
                    employeename.setError("Please enter Employee Name");
                    employeename.requestFocus();
                }else if(memployeeid.isEmpty()){
                    employeeid.setError("Please enter Employee ID");
                    employeeid.requestFocus();
                }else if(memployeeemail.isEmpty()){
                    employeeemail.setError("Please enter Employee Email");
                    employeeemail.requestFocus();
                }else if(!memployeeemail.matches(emailpattern)){
                    employeeemail.setError("Please enter a valid email address");
                    employeeemail.requestFocus();
                }else if(memployeephone.isEmpty()){
                    employeephone.setError("Please enter Employee Phone Number");
                    employeephone.requestFocus();
                }else if(Number_Validate(memployeephone)){
                    employeephone.setError("Please enter a valid Phone Number");
                    employeephone.requestFocus();
                }else if(memployeeemergency.isEmpty()){
                    employeeemergency.setError("Please enter Employee Emergency Contact Person");
                    employeeemergency.requestFocus();
                }else if(dateemp.isEmpty()){
                    dateofemp.setError("Please enter Employee Date of Employment");
                    dateofemp.requestFocus();

                }else if(memergencyconctact.isEmpty()){
                    employeeemergency.setError("Please enter Employee Emergency Contact Person");
                    employeeemergency.requestFocus();


                }else if(!(memployeename.isEmpty() && memployeeid.isEmpty() &&memployeeemail.isEmpty()&& dateemp.isEmpty()&& memployeephone.isEmpty() && memployeedesignation.isEmpty()&& memployeetype.isEmpty() && memergencyconctact.isEmpty() && memployeeemergency.isEmpty())){
                    pd.show();
                    postData(memployeename, memployeeid,memployeeemail, memployeephone,memployeedesignation,memployeetype,dateemp,memployeeemergency, memergencyconctact);
                }
            }
        });

        addemployeedialog.show();
    }

    private void postData(final String memployeename, final String memployeeid, final String memployeeemail, final String memployeephone, final String memployeedesignation, final String memployeetype, final String dateemp, final String memployeeemergency, final String memergencyconctact) {

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("addemployee");//.child("addfield");
                    maddemployee = new Addemployee();

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id",firebaseUser.getUid() );

                    hashMap.put("employeename", memployeename);
                    hashMap.put("personid", memployeeid);
                    hashMap.put("email", memployeeemail);
                    hashMap.put("contact", memployeephone);
                    hashMap.put("designation", memployeedesignation);
                    hashMap.put("dateofemp", dateemp);

                    hashMap.put("emergencyperson", memployeeemergency);
                    hashMap.put("emergencycontact", memergencyconctact);


                    databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                toast("Uploaded successfully");
                                pd.dismiss();
                                addemployeedialog.dismiss();
                                employeename.setText(" ");
                                employeeid.setText(" ");
                                employeephone.setText(" ");
                                employeeemergency.setText(" ");
                                emergencycontact.setText(" ");


                            }else {
                                pd.dismiss();

                                toast(task.getException().getMessage());
                            }
                        }
                    });

    }
    private void toast(String s) {
        Toast.makeText(getContext(), "Message: "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("addemployee");


        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addemployeeArrayList = new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){
                    //addfieldArrayList.add(ds.getValue(Addfield.class));
                    Addemployee addemployee=ds.getValue(Addemployee.class);
                    if(addemployee.getId().equals(firebaseUser.getUid())){
                    addemployeeArrayList.add(addemployee);
                    }
                }

                employeeviewholder employeeviewholder = new employeeviewholder(addemployeeArrayList);
                recyclerView.setAdapter(employeeviewholder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No Child", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private Boolean Number_Validate(String number)
    {
        return  !TextUtils.isEmpty(number) && (number.length()==10) && android.util.Patterns.PHONE.matcher(number).matches();
    }
}