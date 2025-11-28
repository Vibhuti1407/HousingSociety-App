package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberHomePage extends AppCompatActivity {

    private TextView text2;
    private Button buttonBill, buttonNotice, buttonDirectory, buttonStaff, buttonEvent, buttonTenant, buttonComplaints;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home_page);
        text2 = findViewById(R.id.text2);
        buttonBill = findViewById(R.id.btn_maintenance);
        buttonNotice = findViewById(R.id.btn_notice);
        buttonDirectory = findViewById(R.id.btn_directory);
        buttonStaff = findViewById(R.id.btn_staff);
        buttonEvent = findViewById(R.id.btn_event);
        buttonTenant = findViewById(R.id.btn_tenant);
        buttonComplaints = findViewById(R.id.btn_complaints);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        String flatNo = flat;

        text2.setText("A-"+flatNo+"/Saraswati Society");

        buttonBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberMaintenanceDet.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberNotices.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);            }
        });

        buttonDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberDirectory.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);            }
        });

        buttonStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberStaffDet.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberEventDet.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberViewTenant.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);            }
        });

        buttonComplaints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberHomePage.this, MemberComplaints.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MemberHomePage.this,MemberLogin.class));
        super.onBackPressed();
    }
}