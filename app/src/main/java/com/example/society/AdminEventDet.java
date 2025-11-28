package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminEventDet extends AppCompatActivity {

    Button buttonAddEvent,buttonViewEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_det);

        buttonAddEvent = findViewById(R.id.btn_addevent);
        buttonViewEvent = findViewById(R.id.btn_viewevent);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminEventDet.this, AdminAddEvent.class));
            }
        });

        buttonViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminEventDet.this, AdminViewEvent.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminEventDet.this,AdminHomePage.class));
    }
}