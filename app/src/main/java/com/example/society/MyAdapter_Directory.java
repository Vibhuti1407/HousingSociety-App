package com.example.society;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Directory extends RecyclerView.Adapter<MyAdapter_Directory.MyViewHolder>{
    Context context;
    ArrayList<Member> list;

    public MyAdapter_Directory(Context context, ArrayList<Member> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter_Directory.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.directory, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter_Directory.MyViewHolder holder, int position) {
        Member member = list.get(position);
        holder.flat.setText(member.getMflat());
        holder.name.setText(member.getMfname());
        holder.contact.setText(member.getMcontact());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView flat, name, contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            flat = itemView.findViewById(R.id.tvflat);
            name = itemView.findViewById(R.id.tvname);
            contact = itemView.findViewById(R.id.tvcontact);
        }
    }
}
