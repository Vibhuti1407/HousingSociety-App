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

public class MemberNotices extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MyAdapter_Notices myAdapterNotices;
    ArrayList<Notice> list;
    String flatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_notices);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        recyclerView = findViewById(R.id.notices);
        databaseReference = FirebaseDatabase.getInstance().getReference("Notice");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapterNotices = new MyAdapter_Notices(this, list);
        recyclerView.setAdapter(myAdapterNotices);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Notice notice = dataSnapshot.getValue(Notice.class);
                    list.add(notice);
                }
                myAdapterNotices.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberNotices.this, MemberHomePage.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}