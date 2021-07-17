package com.example.smartfarmer.ui.farmrecords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.R;

import java.util.ArrayList;

public class fieldviewholder extends RecyclerView.Adapter<fieldviewholder.MyViewHolder> {

    ArrayList<Addfield> addfield;

    public fieldviewholder (ArrayList<Addfield> addfield){
        this.addfield = addfield;
    }

    @NonNull
    @Override
    public fieldviewholder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewfields, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull fieldviewholder.MyViewHolder holder, int position) {
        holder.viewfieldname.setText("Field Name: "+addfield.get(position).getFieldname());
        holder.viewfieldsize.setText("Field Size: " +addfield.get(position).getFieldsize());
        holder.viewfieldstatus.setText("Field Status: "+addfield.get(position).getFieldstatus());

    }

    @Override
    public int getItemCount() {
        return addfield.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView viewfieldname, viewfieldsize, viewfieldstatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            viewfieldname = itemView.findViewById(R.id.viewfieldname);
            viewfieldsize = itemView.findViewById(R.id.viewfieldsize);
            viewfieldstatus = itemView.findViewById(R.id.viewfieldstatus);
        }
    }
}
