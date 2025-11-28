package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Tenant extends RecyclerView.Adapter<MyAdapter_Tenant.MyViewHolder>{

    Context context;
    ArrayList<Tenant> list;

    public MyAdapter_Tenant(Context context, ArrayList<Tenant> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Tenant.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tenant, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Tenant.MyViewHolder holder, int position) {
        Tenant tenant = list.get(position);
        holder.name.setText(tenant.getName());
        holder.flatNo.setText(tenant.getFlatNo());
        holder.contact.setText(tenant.getContact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, flatNo, contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvname);
            flatNo = itemView.findViewById(R.id.tvflatNo);
            contact = itemView.findViewById(R.id.tvcontact);
        }
    }
}
