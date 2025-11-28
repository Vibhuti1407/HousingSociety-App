package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MemberMaintenanceDet extends AppCompatActivity {

    String[] month_list = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] year_list = {"2024","2025","2026"};
    AutoCompleteTextView autoCompleteTextView_month, autoCompleteTextView_year;
    ArrayAdapter<String> adapterItems_month, arrayAdapter_year;
    Button btn_show;
    TextView text_show;
    Payment payment;
    String month, year, flatNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_maintenance_det);

        autoCompleteTextView_month = findViewById(R.id.inputTv1);
        autoCompleteTextView_year = findViewById(R.id.inputTv2);
        btn_show = findViewById(R.id.btn_show);
        text_show = findViewById(R.id.text_show);
        Intent intent = getIntent();
        String flat = intent.getStringExtra("flatNo");
        flatNo = flat;

        payment = new Payment();
        adapterItems_month = new ArrayAdapter<String>(this, R.layout.list_items, month_list);
        arrayAdapter_year = new ArrayAdapter<String>(this, R.layout.list_items, year_list);

        autoCompleteTextView_month.setAdapter(adapterItems_month);
        autoCompleteTextView_year.setAdapter(arrayAdapter_year);

        autoCompleteTextView_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MemberMaintenanceDet.this, "Selected month is " + item, Toast.LENGTH_SHORT).show();
                month = item;
            }
        });

        autoCompleteTextView_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(MemberMaintenanceDet.this, "Selected year is " + item, Toast.LENGTH_SHORT).show();
                year = item;
            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView_month.getText().toString().isEmpty()) {
                    autoCompleteTextView_month.setError("Please select month");
                } else if (autoCompleteTextView_year.getText().toString().isEmpty()) {
                    autoCompleteTextView_year.setError("Please select year");
                } else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Payment").child(year).child(month).child(flatNo);
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String db_status = snapshot.child("status").getValue(String.class);
                                if(db_status != null && db_status.equals("Paid")){
                                    text_show.setText("Maintenance for "+ month + " "+ year + " is paid.");
                                }
                                else{
                                    Intent intent = new Intent(MemberMaintenanceDet.this, MemberGenerateBill.class);
                                    intent.putExtra("month", month);
                                    intent.putExtra("year", year);
                                    intent.putExtra("flatNo",flatNo);
                                    startActivity(intent);
                                }
                            } else {
                                //no record
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MemberMaintenanceDet.this, MemberHomePage.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);

    }
}