package com.example.smartfarmer.ui.farmplanner;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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

import com.example.smartfarmer.Addactivity;
import com.example.smartfarmer.Addemployee;
import com.example.smartfarmer.Addequipment;
import com.example.smartfarmer.R;
import com.example.smartfarmer.ui.farmrecords.Addfield;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

public class FarmPlannerFragment extends Fragment {

    DatePickerDialog.OnDateSetListener from_datelistener, to_datelistener;
    DatabaseReference databaseReference, fDatabaseRoot;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    Addactivity addactivity;
    private FirebaseRecyclerOptions<Addactivity> options;
    private FirebaseRecyclerAdapter<Addactivity, MyViewHolder> adapter;
    private RecyclerView recyclerView;
    private ArrayList<String> arraylist = new ArrayList<>();
    Addfield addfield;
    Dialog addactivitydialog;
    Addemployee addemployee;

    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    boolean[] checkedequip;
    ArrayList<Integer> mUserEquip = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_farmplanner, container, false);

        Button addnewactivity = (Button)root.findViewById(R.id.addactivitybutton);

        recyclerView = root.findViewById(R.id.recyclerviewactivity);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        addnewactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addactivitydialog =new Dialog(getContext());

                addactivitydialog.setContentView(R.layout.addnewactivity);

                final Spinner mtaskname = (Spinner) addactivitydialog.findViewById(R.id.taskname);
                final Spinner mfieldname = (Spinner) addactivitydialog.findViewById(R.id.fieldname);
                final TextView mstartdate= (TextView) addactivitydialog.findViewById(R.id.startdate);
                final TextView mfinaldate = (TextView) addactivitydialog.findViewById(R.id.enddate);
                final TextView listequipment = (TextView) addactivitydialog.findViewById(R.id.selectedequip);
                final TextView listemployee = (TextView) addactivitydialog.findViewById(R.id.selectedemp);
                final EditText mdescription = (EditText) addactivitydialog.findViewById(R.id.taskdescription);
                Button addtask = (Button) addactivitydialog.findViewById(R.id.add_activity);


                mstartdate.setOnClickListener(new View.OnClickListener() {
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
                                mstartdate.setText(date1);

                            }
                        };

                    }
                });

                mfinaldate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(),to_datelistener, year, month, day);
                        datePickerDialog.show();
                        to_datelistener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                month =month+1;
                                String date2 = dayOfMonth+"/"+month+"/"+year;
                                mfinaldate.setText(date2);
                            }
                        };
                    }
                });
//employee display and selection
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("addemployee");

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList <String> listemp = new ArrayList();

                        listemp.clear();
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            addemployee = item.getValue(Addemployee.class);

                            assert addemployee != null;
                            assert firebaseUser != null;
                            if(addemployee.getId().equals(firebaseUser.getUid())) {
                                listemp.add(item.child("employeename").getValue(String.class));
                            }
                        }

                        final String[] listemployees = listemp.toArray(new String[0]);
                        checkedItems = new boolean[listemployees.length];


                        TextView assigntaskto = (TextView) addactivitydialog.findViewById(R.id.assigntaskto);
                        assigntaskto.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                                alertBuilder.setTitle("List of Employees");
                                alertBuilder.setMultiChoiceItems(listemployees, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                                                if(isChecked){
                                                    if(!mUserItems.contains(position)){
                                                        mUserItems.add(position);
                                                    }else{
                                                        mUserItems.remove(position);
                                                    }
                                                }
                                            }
                                        });

                                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String employee = "";
                                                for (int i = 0; i < mUserItems.size(); i++) {
                                                    employee = employee + listemployees[mUserItems.get(i)];
                                                    if (i != mUserItems.size() - 1) {
                                                        employee = employee + ",";
                                                    }
                                                }
                                                listemployee.setText(employee);
                                                listemployee.setVisibility(View.VISIBLE);

                                            }
                                        });
                                alertBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for(int i = 0; i < checkedItems.length; i++) {
                                            checkedItems [i] = false;
                                            mUserItems.clear();
                                            listemployee.setText("");
                                            listemployee.setVisibility(View.GONE);
                                        }
                                    }
                                });

                                AlertDialog mDialog = alertBuilder.create();
                                mDialog.show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//fieldname display

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("addfield");

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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
                            mfieldname.setAdapter(arrayAdapter);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//equipment selection and display
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("addequipment");

                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final ArrayList <String> listequip = new ArrayList();

                        listequip.clear();
                        for(DataSnapshot item: dataSnapshot.getChildren()){
                            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            Addequipment addequipment = item.getValue(Addequipment.class);


                            assert addequipment != null;
                            assert firebaseUser != null;
                            if(addequipment.getId().equals(firebaseUser.getUid())) {
                                listequip.add(item.child("name").getValue(String.class));
                            }
                        }
                        final String[] listequipments = listequip.toArray(new String[0]);
                        checkedequip = new boolean[listequipments.length];


                        TextView assignedquip = (TextView) addactivitydialog.findViewById(R.id.assignequip);
                        assignedquip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                                alertBuilder.setTitle("List of Equipment");
                                alertBuilder.setMultiChoiceItems(listequipments, checkedequip, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                                        if(isChecked){
                                            if(!mUserEquip.contains(position)){
                                                mUserEquip.add(position);
                                            }else{
                                                mUserEquip.remove(position);
                                            }
                                        }
                                    }
                                });

                                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String equip = "";
                                        for (int i = 0; i < mUserEquip.size(); i++) {
                                            equip = equip + listequipments[mUserEquip.get(i)];
                                            if (i != mUserEquip.size() - 1) {
                                                equip = equip + ",";
                                            }
                                        }
                                        listequipment.setText(equip);
                                        listequipment.setVisibility(View.VISIBLE);

                                    }
                                });
                                alertBuilder.setNegativeButton("DISMISS", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for(int i = 0; i < checkedequip.length; i++) {
                                            checkedequip [i] = false;
                                            mUserEquip.clear();
                                            listequipment.setText("");
                                            listequipment.setVisibility(View.GONE);
                                        }
                                    }
                                });

                                AlertDialog mDialog = alertBuilder.create();
                                mDialog.show();
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                addtask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String taskname = mtaskname.getSelectedItem().toString();
                        String fieldname = mfieldname.getSelectedItem().toString();
                        String startdate = mstartdate.getText().toString();
                        String finaldate = mfinaldate.getText().toString();
                        String employee = listemployee.getText().toString();
                        String equipment = listequipment.getText().toString();
                        String description = mdescription.getText().toString();


                        if(fieldname.isEmpty()){
                            mfieldname.requestFocus();
                        }else if(description.isEmpty()) {
                            mdescription.setError("Please Enter a task description");
                            mdescription.requestFocus();
                        }else if(employee.isEmpty()){
                            Toast.makeText(getActivity(), "NO EMPLOYEE HAS BEEN ASSIGNED TO THIS TASK", Toast.LENGTH_SHORT).show();
                        }else if(equipment.isEmpty()){
                            Toast.makeText(getActivity(), "NO EQUIPMENT HAS BEEN SELECTED FOR THIS TASK", Toast.LENGTH_SHORT).show();
                        }else{

                            addactivity(taskname, fieldname,startdate, finaldate,employee, equipment, description );
                            Toast.makeText(getActivity(), "Activity added Successfully", Toast.LENGTH_SHORT).show();


                            mstartdate.setText("");
                            mfinaldate.setText("");
                            mdescription.setText("");
                            addactivitydialog.dismiss();

                        }
                }
                });

                addactivitydialog.show();


            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("addactivity");
        options = new FirebaseRecyclerOptions.Builder<Addactivity>().setQuery(databaseReference, Addactivity.class).build();

            adapter = new FirebaseRecyclerAdapter<Addactivity, MyViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Addactivity model) {
                    firebaseUser =  FirebaseAuth.getInstance().getCurrentUser();
                    if(model.getId().equals(firebaseUser.getUid())){

                        holder.taskname.setText("Task Name: " + model.getTaskname());
                        holder.fieldname.setText("Field Name: " + model.getFieldname());
                        holder.startdate.setText("Start Date: " + model.getStartdate());
                        holder.enddate.setText("End Date: " + model.getFinaldate());
                        holder.employee.setText("Assigned To: " + model.getEmployee());
                        holder.equipment.setText("Equipment: " + model.getEquipment());
                        holder.description.setText("Description: " + model.getDescription());
                    }
                }

                @NonNull
                @Override
                public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewactivity, parent, false);
                    return new MyViewHolder(v);
                }
            };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return root;
    }



    private void addactivity (final String taskname, String fieldname, String startdate, String finaldate,String employee, String equipment, String description){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("addactivity");
        firebaseUser=  FirebaseAuth.getInstance().getCurrentUser();
        addactivity = new Addactivity();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",firebaseUser.getUid() );
        hashMap.put("taskname", taskname);
        hashMap.put("fieldname", fieldname);
        hashMap.put("startdate", startdate);
        hashMap.put("finaldate", finaldate);
        hashMap.put("employee", employee);
        hashMap.put("equipment", equipment);
        hashMap.put("description", description);


        databaseReference.push().setValue(hashMap);


    }
}
