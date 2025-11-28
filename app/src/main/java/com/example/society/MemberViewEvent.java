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

public class MemberViewEvent extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MyAdapter_Event myAdapterEvent;
    ArrayList<Event> list;
    String flatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view_event);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        recyclerView = findViewById(R.id.event);
        databaseReference = FirebaseDatabase.getInstance().getReference("Event");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapterEvent = new MyAdapter_Event(this, list);
        recyclerView.setAdapter(myAdapterEvent);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Event event = dataSnapshot.getValue(Event.class);
                    list.add(event);
                }
                myAdapterEvent.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberViewEvent.this, MemberEventDet.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}