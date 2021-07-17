package com.example.smartfarmer.ui.farmrecords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addemployee;
import com.example.smartfarmer.R;

import java.util.ArrayList;

public class employeeviewholder extends RecyclerView.Adapter<employeeviewholder.MyViewHolder> {

    ArrayList<Addemployee> addemployee;

    public employeeviewholder (ArrayList<Addemployee> addemployee) {
        this.addemployee = addemployee;
    }
    @NonNull
    @Override

    public employeeviewholder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewemployees, parent, false);
        return new employeeviewholder.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull employeeviewholder.MyViewHolder holder, int position) {
        holder.employeename.setText(        "Employee Name : " + addemployee.get(position).getEmployeename());
        holder.employeeid.setText(          "Employee ID : " + addemployee.get(position).getPersonid());
        holder.employeeemail.setText(       "Employee Email : " + addemployee.get(position).getEmail());
        holder.employeephone.setText(       "Employee Phone : " + addemployee.get(position).getContact());
        holder.employeedesignation.setText( "Employee Designation : " + addemployee.get(position).getDesignation());
        holder.employeetype.setText(        "Employment Type  : " + addemployee.get(position).getEmptype());
        holder.employeedate.setText(        "Employed On  : " + addemployee.get(position).getDateofemp());
        holder.employeeemergency.setText(   "Emergency Person : " + addemployee.get(position).getEmergencyperson());
        holder.emergencycontact.setText(    "Emergency Contact : " + addemployee.get(position).getEmergencycontact());

    }

    @Override
    public int getItemCount() {
        return addemployee.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView employeename, employeeid, employeeemail, employeephone, employeedate,employeedesignation, employeetype, employeeemergency, emergencycontact;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            employeename = itemView.findViewById(R.id.employeenameview);
            employeeid = itemView.findViewById(R.id.employeeidview);
            employeeemail = itemView.findViewById(R.id.employeeemailview);
            employeephone = itemView.findViewById(R.id.employeephoneview);
            employeedesignation = itemView.findViewById(R.id.employeedesignationview);
            employeetype = itemView.findViewById(R.id.employeetypeview);
            employeedate = itemView.findViewById(R.id.employeedateview);
            employeeemergency = itemView.findViewById(R.id.employeeemergencyview);
            emergencycontact = itemView.findViewById(R.id.employeecontactpersonview);
        }
    }
}
