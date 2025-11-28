package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class MemberComplaints extends AppCompatActivity {

    private EditText description;
    private TextView date, flatNumber;
    Complaints complaints;
    private Button btn_submit;
    DatabaseReference ref;
    String flatNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_complaints);

        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        date = findViewById(R.id.date);
        flatNumber = findViewById(R.id.flat);
        description = findViewById(R.id.description);
        btn_submit = findViewById(R.id.btn_submit);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String currentDateAndTime = sdf.format(new Date());
        date.setText(currentDateAndTime);
        flatNumber.setText(flatNo);

        complaints = new Complaints();
        ref= FirebaseDatabase.getInstance().getReference().child("Complaints");
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = String.valueOf(currentDateAndTime);
                String FlatNo= String.valueOf(flatNo);
                String Description= String.valueOf(description.getText());

                if (TextUtils.isEmpty(Description)) {
                    Toast.makeText(MemberComplaints.this, "Please enter description", Toast.LENGTH_SHORT).show();
                    description.setError("Description is required");
                    description.requestFocus();
                } else {
                    complaints.setDate(Date);
                    complaints.setFlatNo(FlatNo);
                    complaints.setDescription(Description);

                    ref.push().setValue(complaints);

                    description.setText("");

                    Toast.makeText(MemberComplaints.this,"Complaint has been submitted successfully ",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MemberComplaints.this, MemberHomePage.class);
                    intent.putExtra("flatNo",flatNo);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberComplaints.this, MemberHomePage.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }
}