package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MemberViewPlumber extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MyAdapter_Plumber myAdapter_plumber;
    ArrayList<Plumber> list;
    String flatNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_plumber);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        recyclerView = findViewById(R.id.plumbers);
        databaseReference = FirebaseDatabase.getInstance().getReference("Plumber");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter_plumber = new MyAdapter_Plumber(this, list);
        recyclerView.setAdapter(myAdapter_plumber);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Plumber plumber = dataSnapshot.getValue(Plumber.class);
                    list.add(plumber);
                }
                myAdapter_plumber.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberViewPlumber.this, MemberStaffDet.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}