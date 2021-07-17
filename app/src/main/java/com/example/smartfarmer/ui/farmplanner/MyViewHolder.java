package com.example.smartfarmer.ui.farmplanner;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView taskname;
    public TextView fieldname;
    public TextView startdate;
    public TextView enddate, employee, equipment;

    public TextView description;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        taskname = itemView.findViewById(R.id.tasknameview);
        fieldname = itemView.findViewById(R.id.fieldnameview);
        startdate = itemView.findViewById(R.id.startdateview);
        enddate = itemView.findViewById(R.id.enddateview);
        employee = itemView.findViewById(R.id.employeeview);
        equipment = itemView.findViewById(R.id.equipmentview);
        description = itemView.findViewById(R.id.descriptionview);


    }
}
