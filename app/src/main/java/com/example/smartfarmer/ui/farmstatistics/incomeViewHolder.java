package com.example.smartfarmer.ui.farmstatistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartfarmer.Addincome;
import com.example.smartfarmer.R;

import java.util.ArrayList;

public class incomeViewHolder extends RecyclerView.Adapter<incomeViewHolder.MyViewHolder> {

    ArrayList<Addincome> addincome;

    public incomeViewHolder (ArrayList<Addincome> addincome){
        this.addincome = addincome;
    }

    @NonNull
    @Override
    public incomeViewHolder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewincome, parent, false);
        return new incomeViewHolder.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull incomeViewHolder.MyViewHolder holder, int position) {
        holder.viewtransactiondate.setText("Transaction Date: " + addincome.get(position).getTransactiondate());
        holder.viewtransactiontype.setText("Transaction Type: " + addincome.get(position).getTransactiontype());
        holder.viewtransactionname.setText("Transaction Name: " + addincome.get(position).getTransactionname());
        holder.viewtransactioncost.setText("Transaction Cost: " + addincome.get(position).getTransactioncost());
        holder.viewtransactiondetails.setText("Transaction Details: " + addincome.get(position).getTransactiondetails());


    }

    @Override
    public int getItemCount() {
        return addincome.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView viewtransactiondate, viewtransactiontype, viewtransactionname, viewtransactioncost, viewtransactiondetails;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            viewtransactiondate = itemView.findViewById(R.id.viewtransactiondate);
            viewtransactiontype = itemView.findViewById(R.id.viewtransactiontype);
            viewtransactionname = itemView.findViewById(R.id.viewtransactionname);
            viewtransactioncost = itemView.findViewById(R.id.viewtransactioncost);
            viewtransactiondetails = itemView.findViewById(R.id.viewtransactiondetails);
        }
    }
}
