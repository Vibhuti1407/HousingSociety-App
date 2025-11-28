package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminStaffDet extends AppCompatActivity {

    Button buttonAddCleaner,buttonViewCleaner, buttonAddElectrician, buttonViewElectrician, buttonAddPlumber, buttonViewPlumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_staff_det);

        buttonAddCleaner = findViewById(R.id.btn_addcleaner);
        buttonViewCleaner = findViewById(R.id.btn_viewcleaner);
        buttonAddElectrician = findViewById(R.id.btn_addelectrician);
        buttonViewElectrician = findViewById(R.id.btn_viewelectrician);
        buttonAddPlumber = findViewById(R.id.btn_addplumber);
        buttonViewPlumber = findViewById(R.id.btn_viewplumber);

        buttonAddCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminAddCleaner.class));
            }
        });

        buttonViewCleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminViewCleaner.class));
            }
        });

        buttonAddElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminAddElectrician.class));
            }
        });

        buttonViewElectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminViewElectrician.class));
            }
        });

        buttonAddPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminAddPlumber.class));
            }
        });

        buttonViewPlumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminStaffDet.this, AdminViewPlumber.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminStaffDet.this,AdminHomePage.class));
    }
}