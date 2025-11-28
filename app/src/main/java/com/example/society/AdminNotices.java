package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNotices extends AppCompatActivity {

    private EditText description;
    Notice notice;
    private Button btn_generate;
    DatabaseReference ref;
    DatePicker datePicker;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notices);
        datePicker = findViewById(R.id.date);
        timePicker = findViewById(R.id.time);
        description = findViewById(R.id.description);
        btn_generate = findViewById(R.id.btn_generate);

        datePicker.setMinDate(System.currentTimeMillis()-1000);
        notice = new Notice();
        ref= FirebaseDatabase.getInstance().getReference().child("Notice");
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day, month, year;
                day = String.valueOf(datePicker.getDayOfMonth());
                month = String.valueOf(datePicker.getMonth() + 1);
                year = String.valueOf(datePicker.getYear());

                String hour, minute;
                hour = String.valueOf(timePicker.getHour());
                minute = String.valueOf(timePicker.getMinute());
                timePicker.setIs24HourView(true);

                String Date = day + "/" + month + "/" + year;
                String Time = hour + ":" + minute;
                String Description= String.valueOf(description.getText());

                if (TextUtils.isEmpty(Description)) {
                    Toast.makeText(AdminNotices.this, "Please enter description", Toast.LENGTH_SHORT).show();
                    description.setError("Description is required");
                    description.requestFocus();
                } else {
                    notice.setDate(Date);
                    notice.setTime(Time);
                    notice.setDescription(Description);

                    ref.push().setValue(notice);

                    description.setText("");

                    Toast.makeText(AdminNotices.this,"Notice Published ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminNotices.this,AdminHomePage.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminNotices.this,AdminHomePage.class));
    }
}