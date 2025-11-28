package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MemberPayment extends AppCompatActivity {
    Button btn_close;
    String flatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_payment);

        btn_close=findViewById(R.id.btn_close);
        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberPayment.this, MemberMaintenanceDet.class);
                intent.putExtra("flatNo",flatNo);
                startActivity(intent);
            }
        });

        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());

        TextView textView=findViewById(R.id.txvcurrentdate);
        textView.setText(currentDate);
    }
}