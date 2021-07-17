package com.example.smartfarmer.ui.Employee;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class EmployeeReportFragment extends Fragment {

    private Dialog addreport;
    private FloatingActionButton fbaddreport;
    private EditText   problem, recomendation, additional;
     private TextView employeename, date;
    private Spinner taskname;
    RecyclerView recyclerView;

    public EmployeeReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_employee_report, container, false);

        fbaddreport = view.findViewById(R.id.fbempreport);

        addreport =new Dialog(getContext());
        addreport.setContentView(R.layout.taskname);

        employeename = addreport.findViewById(R.id.empname);
        date = addreport.findViewById(R.id.empdate);
        problem = addreport.findViewById(R.id.problem);
        recomendation = addreport.findViewById(R.id.recomme);
        taskname =addreport.findViewById(R.id.spinnertask);
        additional = addreport.findViewById(R.id.additionalinfo);

        fbaddreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bringaddreportdialog();
            }
        });

        recyclerView = view.findViewById(R.id.reportList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    private void bringaddreportdialog() {
        Button addrre = addreport.findViewById(R.id.reportadd);

        final Employeehome employeehome = (Employeehome) getActivity();
        Bundle results = employeehome.getMyData();
        final String emaillogin = results.getString("val1");

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("addemployee");
        databaseReference1.orderByChild("email").equalTo(emaillogin).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    Addemployee addemployee = item.getValue(Addemployee.class);

                    assert addemployee != null;
                    employeename.setText(addemployee.getEmployeename());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String currentdate = dateFormat.format(calendar.getTime());

        date.setText(currentdate);


        addrre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = employeename.getText().toString();
                String dae = date.getText().toString();
                String task = taskname.getSelectedItem().toString();
                String problem1 = problem.getText().toString();
                String rec = recomendation.getText().toString();
                String add = additional. getText().toString();

                if(name.isEmpty() &&dae.isEmpty() &&task.isEmpty() &&problem1.isEmpty() &&rec.isEmpty() &&add.isEmpty()){
                    Toast.makeText(getContext(), "NO FIELDS CAN BE EMPTY", Toast.LENGTH_SHORT).show();
                }else {
                    post(name, dae, task, problem1, rec, add);
                }
            }
        });

        addreport.show();
    }

    private void post(String name, String dae, String task, String problem1, String rec, String add) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("taskreports");

        final Addreport addreportclass = new Addreport();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("name", name);
        hashMap.put("date", dae);
        hashMap.put("task", task);
        hashMap.put("problem", problem1);
        hashMap.put("rec", rec);
        hashMap.put("add", add);

        databaseReference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    toast("Uploaded successfully");
                    addreport.dismiss();
                    problem.setText("");
                    recomendation.setText("");
                    additional.setText("");
                } else {

                    toast(task.getException().getMessage());
                }
            }
        });

    }
    private void toast(String s) {
        Toast.makeText(getContext(), "Message: "+s, Toast.LENGTH_SHORT).show();
    }

    public void onStart() {
        super.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("taskreports");
        databaseReference.keepSynced(true);
        FirebaseRecyclerOptions<Addreport> options=new FirebaseRecyclerOptions.Builder<Addreport>()
                .setQuery(databaseReference,Addreport.class)
                .build();

        final FirebaseRecyclerAdapter<Addreport, EmployeeReportFragment.reportsViewHolder> adapter=new FirebaseRecyclerAdapter<Addreport, reportsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final EmployeeReportFragment.reportsViewHolder holder, int position, @NonNull final Addreport model) {


                holder.tvName.setText(model.getTask());
                holder.tvdate.setText(model.getDate());
                holder.tvempname.setText(model.getName());
                holder.problems.setText(model.getProblem());
                holder.recom.setText(model.getRec());
                holder.add.setText(model.getAdd());


            }

            @NonNull
            @Override
            public EmployeeReportFragment.reportsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reporttask,viewGroup,false);
                EmployeeReportFragment.reportsViewHolder viewHolder=new EmployeeReportFragment.reportsViewHolder(view);


                return viewHolder;
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

        recyclerView.smoothScrollToPosition(adapter.getItemCount()+1);


    }

    public static class reportsViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvdate,tvempname, problems,recom, add ;


        public reportsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tasknamere);
            tvdate=itemView.findViewById(R.id.datename);
            tvempname=itemView.findViewById(R.id.empnamere);

            problems =itemView.findViewById(R.id.problems);
            recom = itemView.findViewById(R.id.recommendations);
            add = itemView.findViewById(R.id.additional);
        }
    }
}