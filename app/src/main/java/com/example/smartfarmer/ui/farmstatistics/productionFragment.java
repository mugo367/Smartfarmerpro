package com.example.smartfarmer.ui.farmstatistics;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import com.example.smartfarmer.Addproduction;
import com.example.smartfarmer.R;
import com.example.smartfarmer.ui.farmrecords.Addfield;
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


public class productionFragment extends Fragment {

    private Dialog addproductiondialog;
    private FloatingActionButton fbproduction;
    TextView  productiondate;
    private EditText productionlabel, productionquantity, productiondetails;
    private Spinner fieldname, maizetype, productiontype, unit;
    private Button productionsave;

    private ProgressDialog pd;
    private RecyclerView recyclerView;
    DatePickerDialog.OnDateSetListener datelistener;
    private ArrayList<String> arraylist = new ArrayList<>();

    Addfield addfield;
    FirebaseUser firebaseUser;
    Addproduction addproduction;
    ArrayList<Addproduction> addproductionArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_production, container, false);

        fbproduction = view.findViewById(R.id.fbproduction);

        addproductiondialog =new Dialog(getContext());
        addproductiondialog.setContentView(R.layout.add_productionrecords);

        productionlabel = addproductiondialog.findViewById(R.id.productionname);
        productiondate = addproductiondialog.findViewById(R.id.productiondate);
        fieldname = addproductiondialog.findViewById(R.id.fieldname_record);
        maizetype = addproductiondialog.findViewById(R.id.maizetype);
        productiontype = addproductiondialog.findViewById(R.id.productiontype);
        productionquantity = addproductiondialog.findViewById(R.id.productionquantity);
        unit = addproductiondialog.findViewById(R.id.unit);
        productiondetails = addproductiondialog.findViewById(R.id.productiondetails);

        pd=new ProgressDialog(getContext());
        pd.setTitle("Adding Details");
        pd.setMessage("Please wait...");

        fbproduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddproductiondialog();
            }
        });

        recyclerView = view.findViewById(R.id.productionList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void bringaddproductiondialog() {
        productionsave = addproductiondialog.findViewById(R.id.productionsave);

        productiondate.setOnClickListener(new View.OnClickListener() {
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
                        month =month+1;
                        String date = dayOfMonth+"/"+month+"/"+year;
                        productiondate.setText(date);

                    }
                };

            }
        });
        DatabaseReference fDatabaseRoot = FirebaseDatabase.getInstance().getReference();
        fDatabaseRoot.child("addfield").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if(addfield.getId().equals(firebaseUser.getUid()))
                arraylist.clear();
                for(DataSnapshot item: dataSnapshot.getChildren()){
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    addfield = item.getValue(Addfield.class);


                    assert addfield != null;
                    assert firebaseUser != null;
                    if(addfield.getId().equals(firebaseUser.getUid())) {
                        arraylist.add(item.child("fieldname").getValue(String.class));
                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, arraylist);
                fieldname.setAdapter(arrayAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        productionsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_productionlabel = productionlabel.getText().toString();
                String txt_productiondate =  productiondate.getText().toString();
                String txt_fieldname = fieldname.getSelectedItem().toString();
                String txt_maizetype = maizetype.getSelectedItem().toString();
                String txt_productiontype = productiontype.getSelectedItem().toString();
                String txt_productionquantity = productionquantity.getText().toString();
                String txt_unit = unit.getSelectedItem().toString();
                String txt_productiondetails = productiondetails.getText().toString();


                if(txt_productionlabel.isEmpty() || txt_productiondate.isEmpty() || txt_fieldname.isEmpty() || txt_productiontype.isEmpty() || txt_productionquantity.isEmpty()){
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    pd.show();
                    addproduction(txt_productionlabel, txt_productiondate, txt_fieldname, txt_maizetype, txt_productiontype, txt_productionquantity, txt_unit, txt_productiondetails);
                }
            }
        });
        addproductiondialog.show();
    }

    private void addproduction(String txt_productionlabel, String txt_productiondate, String txt_fieldname,String txt_maizetype, String txt_productiontype, String txt_productionquantity, String txt_unit, String txt_productiondetails) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("productionrecords");
        FirebaseUser firebaseUser=  FirebaseAuth.getInstance().getCurrentUser();
        addproduction = new Addproduction();


        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",firebaseUser.getUid() );
        hashMap.put("productionlabel", txt_productionlabel);
        hashMap.put("productiondate", txt_productiondate);
        hashMap.put("fieldname", txt_fieldname);
        hashMap.put("maizetype", txt_maizetype);
        hashMap.put("productiontype", txt_productiontype);
        hashMap.put("productionquantity", txt_productionquantity);
        hashMap.put("unit", txt_unit);
        hashMap.put("productiondetails", txt_productiondetails);


        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    toast("Uploaded successfully");
                    pd.dismiss();
                    addproductiondialog.dismiss();
                    productionlabel.setText("");
                    productiondate.setText("");
                    productionquantity.setText("");
                    productiondetails.setText("");

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

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("productionrecords");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addproductionArrayList = new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){

                    Addproduction addproduction=ds.getValue(Addproduction.class);
                    if(addproduction.getId().equals(firebaseUser.getUid())){
                        addproductionArrayList.add(addproduction);
                    }

                }

                productionViewHolder productionViewHolder = new productionViewHolder(addproductionArrayList);
                recyclerView.setAdapter(productionViewHolder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No Child", Toast.LENGTH_SHORT).show();
            }
        });
    }
}