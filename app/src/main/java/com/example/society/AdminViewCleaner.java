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

public class AdminViewCleaner extends AppCompatActivity {

    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MyAdapter_Cleaner myAdapter_cleaner;
    ArrayList<Cleaner> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_cleaner);

        recyclerView = findViewById(R.id.cleaners);
        databaseReference = FirebaseDatabase.getInstance().getReference("Cleaner");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapter_cleaner = new MyAdapter_Cleaner(this, list);
        recyclerView.setAdapter(myAdapter_cleaner);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Cleaner cleaner = dataSnapshot.getValue(Cleaner.class);
                    list.add(cleaner);
                }
                myAdapter_cleaner.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminViewCleaner.this,AdminStaffDet.class));
    }
}