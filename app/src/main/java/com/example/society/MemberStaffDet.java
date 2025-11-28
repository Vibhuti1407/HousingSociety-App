package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberStaffDet extends AppCompatActivity {
    Button buttonViewCleaner, buttonViewElectrician, buttonViewPlumber;
    String flatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_staff_det);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        buttonViewCleaner = findViewById(R.id.btn_viewcleaner);
        buttonViewElectrician = findViewById(R.id.btn_viewelectrician);
        buttonViewPlumber = findViewById(R.id.btn_viewplumber);

        buttonViewCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberStaffDet.this, MemberViewCleaner.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonViewElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberStaffDet.this, MemberViewElectrician.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonViewPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberStaffDet.this, MemberViewPlumber.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberStaffDet.this, MemberHomePage.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}