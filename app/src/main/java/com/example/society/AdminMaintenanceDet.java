package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminMaintenanceDet extends AppCompatActivity {
    String[] month_list = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] year_list = {"2024","2025","2026"};
    AutoCompleteTextView autoCompleteTextView_month, autoCompleteTextView_year;
    ArrayAdapter<String> arrayAdapter_month, arrayAdapter_year;
    Button btn_show;
    TextView text_show;
    Payment payment;
    String month, year, status;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    MyAdapter_Payment myAdapterPayment;
    ArrayList<Payment> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintenance_det);

        autoCompleteTextView_month = findViewById(R.id.inputTv1);
        autoCompleteTextView_year = findViewById(R.id.inputTv2);
        btn_show = findViewById(R.id.btn_show);
        text_show = findViewById(R.id.text_show);

        payment = new Payment();
        arrayAdapter_month = new ArrayAdapter<String>(this, R.layout.list_items, month_list);
        arrayAdapter_year = new ArrayAdapter<String>(this, R.layout.list_items, year_list);

        autoCompleteTextView_month.setAdapter(arrayAdapter_month);
        autoCompleteTextView_year.setAdapter(arrayAdapter_year);

        recyclerView = findViewById(R.id.payment);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        myAdapterPayment = new MyAdapter_Payment(this, list);
        //recyclerView.setAdapter(myAdapterPayment);

        autoCompleteTextView_month.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdminMaintenanceDet.this, "Selected month is " + item, Toast.LENGTH_SHORT).show();
                month = item;
            }
        });

        autoCompleteTextView_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdminMaintenanceDet.this, "Selected year is " + item, Toast.LENGTH_SHORT).show();
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
                    databaseReference = FirebaseDatabase.getInstance().getReference("Payment").child(year).child(month);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Payment").child(year).child(month);
                    list.clear();
                    Query query = reference.orderByChild("status");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                text_show.setText("Maintenance for "+ month + " "+ year + " ");
                                recyclerView.setAdapter(myAdapterPayment);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                            Payment payment = dataSnapshot.getValue(Payment.class);
                                            list.add(payment);
                                        }
                                        myAdapterPayment.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                recyclerView.setAdapter(null);
                                text_show.setText("Maintenance for "+ month + " "+ year + " is not paid by anyone.");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        /*
        btn_success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView_month.getText().toString().isEmpty()) {
                    autoCompleteTextView_month.setError("Please select month");
                }
                else if (autoCompleteTextView_year.getText().toString().isEmpty()) {
                    autoCompleteTextView_year.setError("Please select year");
                }
                else{
                    status = "Successful";
                    databaseReference = FirebaseDatabase.getInstance().getReference("Payment");
                    list.clear(); // Clear the list before fetching new data

                    databaseReference.orderByChild("status").equalTo(status).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                text_show.setText("Maintenance for " + month + " " + year + " is paid by ");
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Payment payment = dataSnapshot.getValue(Payment.class);
                                    if (payment != null) {
                                        list.add(payment);
                                    }
                                }
                                myAdapterPayment.notifyDataSetChanged();
                            } else {
                                recyclerView.setAdapter(null);
                                text_show.setText("Maintenance for " + month + " " + year + " is not paid by anyone.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }
            }
        });

        btn_dues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView_month.getText().toString().isEmpty()) {
                    autoCompleteTextView_month.setError("Please select month");
                }
                if (autoCompleteTextView_year.getText().toString().isEmpty()) {
                    autoCompleteTextView_year.setError("Please select year");
                } else {
                    status = "Unsuccessful";
                    //recyclerView.setAdapter(null);
                    databaseReference = FirebaseDatabase.getInstance().getReference("Payment").child(year).child(month);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Payment").child(year).child(month);
                    Query query = reference.orderByChild("status").equalTo(status);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                text_show.setText("Maintenance for "+ month + " "+ year + " is not paid by ");
                                //recyclerView.setAdapter(myAdapterPayment);
                                databaseReference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                            Payment payment = dataSnapshot.getValue(Payment.class);
                                            list.add(payment);
                                        }
                                        myAdapterPayment.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            } else {
                                recyclerView.setAdapter(null);
                                text_show.setText("Maintenance for "+ month + " "+ year + " is paid by everyone.");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


        btn_dues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompleteTextView_month.getText().toString().isEmpty()) {
                    autoCompleteTextView_month.setError("Please select month");
                }
                else if (autoCompleteTextView_year.getText().toString().isEmpty()) {
                    autoCompleteTextView_year.setError("Please select year");
                }
                else {
                    status = "Unsuccessful";
                    databaseReference = FirebaseDatabase.getInstance().getReference("Payment").child(year).child(month);
                    list.clear();

                    //for member with dues
                    databaseReference1 = FirebaseDatabase.getInstance().getReference("Member");
                    final String[][] flatNumbersArray = new String[1][1];
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            List<String> flatNumbers = new ArrayList<>();
                            for (DataSnapshot memberSnapshot : snapshot.getChildren()) {
                                String flatNumber = memberSnapshot.child("mflat").getValue(String.class);
                            }
                            flatNumbersArray[0] = flatNumbers.toArray(new String[0]);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    text_show.setText("Maintenance for " + month + " " + year + " is not paid by ");
                    recyclerView.setAdapter(null);
                    for (int i = 0; i < flatNumbersArray[0].length; i++) {
                        databaseReference.orderByChild("flat").equalTo(String.valueOf(flatNumbersArray[i])).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Payment payment = dataSnapshot.getValue(Payment.class);
                                    if (payment != null) {
                                        list.add(payment);
                                    }
                                }
                                myAdapterPayment.notifyDataSetChanged();

                                } else {

                                    text_show.setText("Maintenance for " + month + " " + year + " is paid by everyone.");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle error (e.g., show a Toast message)
                                Toast.makeText(AdminMaintenanceDet.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    // Query for payments with status "Unsuccessful"
                }
            }
        });
        */
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminMaintenanceDet.this, AdminHomePage.class));
    }
}