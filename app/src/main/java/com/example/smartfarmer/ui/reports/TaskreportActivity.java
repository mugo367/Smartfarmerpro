package com.example.smartfarmer.ui.reports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.R;
import com.example.smartfarmer.ui.Employee.Addreport;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TaskreportActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taskreport);

        Toolbar toolbar = findViewById(R.id.tooltask);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("Report on Tasks");

        recyclerView = findViewById(R.id.reportfarmerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskreportActivity.this));
    }

    public void onStart() {
        super.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("taskreports");
        databaseReference.keepSynced(true);

        FirebaseRecyclerOptions<Addreport> options=new FirebaseRecyclerOptions.Builder<Addreport>()
                .setQuery(databaseReference,Addreport.class)
                .build();

        final FirebaseRecyclerAdapter<Addreport, TaskreportActivity.reportsViewHolder> adapter=new FirebaseRecyclerAdapter<Addreport, TaskreportActivity.reportsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final TaskreportActivity.reportsViewHolder holder, int position, @NonNull final Addreport model) {


                holder.tvName.setText(model.getTask());
                holder.tvdate.setText(model.getDate());
                holder.tvempname.setText(model.getName());
                holder.problems.setText(model.getProblem());
                holder.recom.setText(model.getRec());
                holder.add.setText(model.getAdd());


            }

            @NonNull
            @Override
            public TaskreportActivity.reportsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reporttask,viewGroup,false);
                TaskreportActivity.reportsViewHolder viewHolder=new TaskreportActivity.reportsViewHolder(view);


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