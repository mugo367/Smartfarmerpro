package com.example.smartfarmer.ui.farmrecords;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addequipment;
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
import java.util.HashMap;


public class EquipmentFragment extends Fragment {

    private Dialog addequipdialog;
    private FloatingActionButton fbaddmachine;
    private EditText equipname, quantity;
    private Spinner status;
    private Button addequiprecord;
    private ProgressDialog pd;
    private RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Addequipment addequipment;

    ArrayList<Addequipment> addequipmentArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_equipment, container, false);

        fbaddmachine = view.findViewById(R.id.fbequip);

        addequipdialog =new Dialog(getContext());
        addequipdialog.setContentView(R.layout.add_machinerydetails);

        equipname = addequipdialog.findViewById(R.id.equipname);
        quantity = addequipdialog.findViewById(R.id.quantity);
        status = addequipdialog.findViewById(R.id.statusequip);


        pd=new ProgressDialog(getContext());
        pd.setTitle("Adding Details");
        pd.setMessage("Please wait...");

        fbaddmachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddmachinedialog();
            }
        });

        recyclerView = view.findViewById(R.id.equipmentList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void bringaddmachinedialog() {
        addequiprecord = addequipdialog.findViewById(R.id.addequip);

        addequiprecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String meqiupname = equipname.getText().toString();
                String mquatity = quantity.getText().toString();
                String mstatus = status.getSelectedItem().toString();

                if(meqiupname.isEmpty()) {
                    equipname.setError("Please enter Machine Name");
                    equipname.requestFocus();
                }else if(mquatity.isEmpty()){
                    quantity.setError("Please enter it's Quantity");
                    quantity.requestFocus();
                }else if(!(meqiupname.isEmpty() && mquatity.isEmpty())){
                    pd.show();
                    postData(meqiupname,mquatity, mstatus);
                }
            }
        });

        addequipdialog.show();
    }

    private void postData(String mequipname, String mquantity, String mstatus) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("addequipment");//.child("addfield");
        addequipment= new Addequipment();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",firebaseUser.getUid() );
        hashMap.put("name", mequipname);
        hashMap.put("quantity", mquantity);
        hashMap.put("equipstatus", mstatus);

        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    toast("Uploaded successfully");
                    pd.dismiss();
                    addequipdialog.dismiss();
                    equipname.setText("");
                    quantity.setText("");

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

        databaseReference = FirebaseDatabase.getInstance().getReference("addequipment");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.keepSynced(true);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                addequipmentArrayList = new ArrayList<>();
                for (DataSnapshot ds:snapshot.getChildren()){

                    Addequipment addequip=ds.getValue(Addequipment.class);
                    if(addequip.getId().equals(firebaseUser.getUid())){
                        addequipmentArrayList.add(addequip);
                    }

                }

                equipviewholder equipviewholder = new equipviewholder(addequipmentArrayList);
                recyclerView.setAdapter(equipviewholder);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "No Child", Toast.LENGTH_SHORT).show();
            }
        });
    }
}