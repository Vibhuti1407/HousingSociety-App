package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemberEventDet extends AppCompatActivity {

    Button buttonAddEvent,buttonViewEvent;
    String flatNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_event_det);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        buttonAddEvent = findViewById(R.id.btn_addevent);
        buttonViewEvent = findViewById(R.id.btn_viewevent);

        buttonAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberEventDet.this, MemberAddEvent.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        buttonViewEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberEventDet.this, MemberViewEvent.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberEventDet.this, MemberHomePage.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}