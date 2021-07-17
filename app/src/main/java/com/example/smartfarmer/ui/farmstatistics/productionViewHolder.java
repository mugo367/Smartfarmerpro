package com.example.smartfarmer.ui.farmstatistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addproduction;
import com.example.smartfarmer.R;

import java.util.ArrayList;

public class productionViewHolder extends RecyclerView.Adapter<productionViewHolder.MyViewHolder> {

    ArrayList<Addproduction> addproductions;

    public productionViewHolder ( ArrayList<Addproduction> addproductions){
        this.addproductions = addproductions;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewproduction, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.viewproductionlabel.setText("Production Name: " + addproductions.get(position).getProductionlabel());
        holder.viewproductiondate.setText("Production Date: " + addproductions.get(position).getProductiondate());
        holder.viewproductionfield.setText("Production Field: " + addproductions.get(position).getFieldname());
        holder.viewmaizetype.setText("Maize Type: " + addproductions.get(position).getMaizetype());
        holder.viewproductiontype.setText("Production Type: " + addproductions.get(position).getProductiontype());
        holder.viewproductionquantity.setText("Production Quantity: " + addproductions.get(position).getProductionquantity());
        holder.viewproductiondetails.setText("Production Details: " + addproductions.get(position).getProductiondetails());


    }

    @Override
    public int getItemCount() {
        return addproductions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView viewproductionlabel, viewproductiondate, viewproductionfield, viewmaizetype,  viewproductiontype, viewproductionquantity, viewproductiondetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            viewproductionlabel = itemView.findViewById(R.id.viewproductionlabel);
            viewproductiondate = itemView.findViewById(R.id.viewproductiondate);
            viewproductionfield = itemView.findViewById(R.id.viewproductionfield);
            viewmaizetype = itemView.findViewById(R.id.viewmaizetype);
            viewproductiontype = itemView.findViewById(R.id.viewproductiontype);
            viewproductionquantity = itemView.findViewById(R.id.viewproductionquantity);
            viewproductiondetails = itemView.findViewById(R.id.viewproductiondetails);

        }
    }
}
