package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminTenantDet extends AppCompatActivity {

    Button buttonAddTenant,buttonViewTenant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tenant_det);

        buttonAddTenant = findViewById(R.id.btn_addTenant);
        buttonViewTenant = findViewById(R.id.btn_viewTenant);

        buttonAddTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminTenantDet.this, AdminAddTenant.class));
            }
        });

        buttonViewTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminTenantDet.this, AdminViewTenant.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminTenantDet.this,AdminHomePage.class));
    }
}