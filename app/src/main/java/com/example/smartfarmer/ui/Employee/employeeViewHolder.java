package com.example.smartfarmer.ui.Employee;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.R;

public class employeeViewHolder extends RecyclerView.ViewHolder {

    public TextView employeetaskname;
    public TextView employeefieldname;
    public TextView employeestartdate;
    public TextView employeeenddate, employeeequipment;
    public employeeViewHolder(@NonNull View itemView) {
        super(itemView);
        employeetaskname = itemView.findViewById(R.id.employeetaskname);
        employeefieldname = itemView.findViewById(R.id.employeefieldname);
        employeestartdate = itemView.findViewById(R.id.employeestartdate);
        employeeenddate = itemView.findViewById(R.id.employeeenddate);
        employeeequipment = itemView.findViewById(R.id.employeeequipment);
    }
}
