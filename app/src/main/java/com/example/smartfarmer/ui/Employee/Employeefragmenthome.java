package com.example.smartfarmer.ui.Employee;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addactivity;
import com.example.smartfarmer.Addemployee;
import com.example.smartfarmer.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Employeefragmenthome extends Fragment {
    DatabaseReference databaseReference;

    Addactivity addactivity;
    private FirebaseRecyclerOptions<Addactivity> options;
    private FirebaseRecyclerAdapter<Addactivity, employeeViewHolder> adapter;
    private RecyclerView recyclerView;

    public Employeefragmenthome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employeefragmenthome, container, false);

        final Employeehome employeehome = (Employeehome) getActivity();

        final TextView Nameemployee = view.findViewById(R.id.emailmployee);

        Bundle results = employeehome.getMyData();
         final String emaillogin = results.getString("val1");

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("addemployee");

        databaseReference1.orderByChild("email").equalTo(emaillogin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    Addemployee addemployee = item.getValue(Addemployee.class);

                    assert addemployee != null;
                    Nameemployee.setText(addemployee.getEmployeename());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        recyclerView = view.findViewById(R.id.taskList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference("addactivity");
        options = new FirebaseRecyclerOptions.Builder<Addactivity>().setQuery(databaseReference, Addactivity.class).build();

        adapter = new FirebaseRecyclerAdapter<Addactivity, employeeViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final employeeViewHolder holder, int position, @NonNull final Addactivity model) {

               String txt_nameemployee = Nameemployee.getText().toString();
               databaseReference.orderByChild("employee").equalTo(txt_nameemployee).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {

                       if(snapshot.exists()){
                       holder.employeetaskname.setText(model.getTaskname());
                       holder.employeefieldname.setText("Field Name: " + model.getFieldname());
                       holder.employeestartdate.setText(model.getStartdate());
                       holder.employeeenddate.setText(model.getFinaldate());
                       holder.employeeequipment.setText(model.getEquipment());
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }


            @NonNull
            @Override
            public employeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singletask, parent, false);
                return new employeeViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return view;

    }
}