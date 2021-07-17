package com.example.smartfarmer.ui.farmrecords;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addequipment;
import com.example.smartfarmer.R;

import java.util.ArrayList;

public class equipviewholder extends RecyclerView.Adapter<equipviewholder.MyViewHolder> {

    ArrayList<Addequipment> addequipment;

    public equipviewholder (ArrayList<Addequipment> addequipment){
        this.addequipment = addequipment;
    }

    @NonNull
    @Override
    public equipviewholder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewequip, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull equipviewholder.MyViewHolder holder, int position) {
        holder.viewequipname.setText("Equipment Name: "+addequipment.get(position).getName());
        holder.viewquantity.setText("Quantity: " +addequipment.get(position).getQuantity());
        holder.viewequipstatus.setText("Status: "+addequipment.get(position).getEquipstatus());

    }

    @Override
    public int getItemCount() {
        return addequipment.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView viewequipname, viewquantity, viewequipstatus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            viewequipname = itemView.findViewById(R.id.viewequipname);
            viewquantity = itemView.findViewById(R.id.viewquantity);
            viewequipstatus = itemView.findViewById(R.id.viewequipstatus);
        }
    }
}
