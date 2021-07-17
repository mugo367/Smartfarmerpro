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

public class FieldFragment extends Fragment {

    private Dialog addfielddialog;
    private FloatingActionButton fbaddfield;
    private EditText fieldname, fieldsize;
    private Spinner status;
    private Button addfieldrecord;
    private ProgressDialog pd;
    private RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    Addfield addfield;

    ArrayList<Addfield> addfieldArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_field, container, false);

        fbaddfield = view.findViewById(R.id.fbfield);

        addfielddialog =new Dialog(getContext());
        addfielddialog.setContentView(R.layout.addfielddetails);

        fieldname = addfielddialog.findViewById(R.id.addfieldname);
        fieldsize = addfielddialog.findViewById(R.id.addfieldsize);
        status = addfielddialog.findViewById(R.id.addstatus);


        pd=new ProgressDialog(getContext());
        pd.setTitle("Uploading Product");
        pd.setMessage("Please wait...");

        fbaddfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddfielddialog();
            }
        });

        recyclerView = view.findViewById(R.id.fieldList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private void bringaddfielddialog() {
        addfieldrecord = addfielddialog.findViewById(R.id.farmbutton);

        addfieldrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mfieldname = fieldname.getText().toString();
                String mfieldsize = fieldsize.getText().toString();
                String mstatus = status.getSelectedItem().toString();

                if(mfieldname.isEmpty()) {
                    fieldname.setError("Please enter your Field Name");
                    fieldname.requestFocus();
                }else if(mfieldsize.isEmpty()){
                    fieldsize.setError("Please enter your Username");
                    fieldsize.requestFocus();
                }else if(!(mfieldname.isEmpty() && mfieldsize.isEmpty())){
                    pd.show();
                    postData(mfieldname,mfieldsize, mstatus);
                }
            }
        });

        addfielddialog.show();
    }

    private void postData(String mfieldname, String mfieldsize, String mstatus) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("addfield");//.child("addfield");
        addfield = new Addfield();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",firebaseUser.getUid() );
        hashMap.put("fieldname", mfieldname);
        hashMap.put("fieldsize", mfieldsize);
        hashMap.put("fieldstatus", mstatus);

        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    toast("Uploaded successfully");
                    pd.dismiss();
                    addfielddialog.dismiss();
                    fieldname.setText("");
                    fieldsize.setText("");

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

        databaseReference = FirebaseDatabase.getInstance().getReference("addfield");
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference.keepSynced(true);

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               addfieldArrayList = new ArrayList<>();
               for (DataSnapshot ds:snapshot.getChildren()){

                   Addfield addfield=ds.getValue(Addfield.class);
                   if(addfield.getId().equals(firebaseUser.getUid())){
                       addfieldArrayList.add(addfield);
                   }

               }

               fieldviewholder fieldviewholder = new fieldviewholder(addfieldArrayList);
               recyclerView.setAdapter(fieldviewholder);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(getContext(), "No Child", Toast.LENGTH_SHORT).show();
           }
       });
    }
}