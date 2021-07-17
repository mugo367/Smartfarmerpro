package com.example.smartfarmer.ui.farmstatistics;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addincome;
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

public class IncomeFragment extends Fragment {

    private Dialog addincomedialog;
    private FloatingActionButton fbincome;
    private EditText transactiondate, transactionname, transactioncost, transactiondetails;
    private Spinner transactiontype;
    private Button transactionsave;

    private ProgressDialog pd;
    private RecyclerView recyclerView;
    DatePickerDialog.OnDateSetListener datelistener;

    Addincome addincome;
    FirebaseUser firebaseUser;
    ArrayList<Addincome> addincomeArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income, container, false);

        fbincome = view.findViewById(R.id.fbincome);

        addincomedialog =new Dialog(getContext());
        addincomedialog.setContentView(R.layout.add_income_expenses);

        transactionname =  addincomedialog.findViewById(R.id.transactionname);
        transactiondate =  addincomedialog.findViewById(R.id.transactiondate);
        transactiontype =  addincomedialog.findViewById(R.id.transactiontype);
        transactioncost =  addincomedialog.findViewById(R.id.transactioncost);
        transactiondetails =  addincomedialog.findViewById(R.id.transactiondetatils);

        pd=new ProgressDialog(getContext());
        pd.setTitle("Adding Details");
        pd.setMessage("Please wait...");

        fbincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddincomedialog();
            }
        });

        recyclerView = view.findViewById(R.id.incomeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void bringaddincomedialog() {
        transactionsave = addincomedialog.findViewById(R.id.incomesave);

        transactiondate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), datelistener, year, month, day);
                datePickerDialog.show();

                datelistener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;
                        transactiondate.setText(date);

                    }
                };
            }
        });
        transactionsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_transactiondate = transactiondate.getText().toString();
                String txt_transactiontype = transactiontype.getSelectedItem().toString();
                String txt_transactionname = transactionname.getText().toString();
                String txt_transactioncost = transactioncost.getText().toString();
                String txt_transactiondetails = transactiondetails.getText().toString();

                if(txt_transactiondate.isEmpty() || txt_transactiontype.isEmpty() || txt_transactionname.isEmpty() || txt_transactioncost.isEmpty()){
                    Toast.makeText(getContext(),"Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    pd.show();
                    addtransaction(txt_transactiondate, txt_transactiontype, txt_transactionname, txt_transactioncost, txt_transactiondetails  );
                }
            }
        });
        addincomedialog.show();
    }
        private void addtransaction(String txt_transactiondate, String txt_transactiontype, String txt_transactionname, String txt_transactioncost, String txt_transactiondetails) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("income_expense");
            FirebaseUser firebaseUser=  FirebaseAuth.getInstance().getCurrentUser();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id",firebaseUser.getUid() );
            hashMap.put("transactiondate", txt_transactiondate);
            hashMap.put("transactiontype", txt_transactiontype);
            hashMap.put("transactionname", txt_transactionname);
            hashMap.put("transactioncost", txt_transactioncost);
            hashMap.put("transactiondetails", txt_transactiondetails);



            databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    toast("Uploaded successfully");
                    pd.dismiss();
                    addincomedialog.dismiss();
                    transactiondate.setText("");
                    transactionname.setText("");
                    transactioncost.setText("");
                    transactiondetails.setText("");

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("income_expense");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addincomeArrayList = new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){

                    Addincome addincome=ds.getValue(Addincome.class);
                    if(addincome.getId().equals(firebaseUser.getUid())){
                        addincomeArrayList.add(addincome);
                    }

                }

                incomeViewHolder incomeViewHolder = new incomeViewHolder(addincomeArrayList);
                recyclerView.setAdapter(incomeViewHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No Child", Toast.LENGTH_SHORT).show();
            }
        });
    }
}