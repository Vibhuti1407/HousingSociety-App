package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHomePage extends AppCompatActivity {

    private Button buttonBill, buttonNotice, buttonDirectory, buttonStaff, buttonEvent, buttonTenant, buttonComplaints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);
        buttonBill = findViewById(R.id.btn_maintenance);
        buttonNotice = findViewById(R.id.btn_notice);
        buttonDirectory = findViewById(R.id.btn_directory);
        buttonStaff = findViewById(R.id.btn_staff);
        buttonEvent = findViewById(R.id.btn_event);
        buttonTenant = findViewById(R.id.btn_tenant);
        buttonComplaints = findViewById(R.id.btn_complaints);

        buttonBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this, AdminMaintenanceDet.class));
            }
        });

        buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminNotices.class));
            }
        });

        buttonDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminDirectory.class));
            }
        });

        buttonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminStaffDet.class));
            }
        });

        buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminEventDet.class));
            }
        });

        buttonTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminTenantDet.class));
            }
        });

        buttonComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminHomePage.this,AdminComplaints.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminHomePage.this,AdminLogin.class));
        super.onBackPressed();
    }
}