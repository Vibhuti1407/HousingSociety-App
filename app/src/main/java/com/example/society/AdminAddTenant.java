package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminAddTenant extends AppCompatActivity {

    Button buttonAdd;
    EditText tname, tflatno, tcontact;
    Tenant tenant;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_tenant);

        tname = findViewById(R.id.name);
        tcontact =findViewById(R.id.contact);
        tflatno = findViewById(R.id.flat_no);
        buttonAdd =findViewById(R.id.btn_add);

        tenant = new Tenant();
        ref= FirebaseDatabase.getInstance().getReference().child("Tenant");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(tname.getText());
                String contact = String.valueOf(tcontact.getText());
                String flatNo = String.valueOf(tflatno.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AdminAddTenant.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    tname.setError("Name is required");
                    tname.requestFocus();
                } else if (TextUtils.isEmpty(flatNo)) {
                    Toast.makeText(AdminAddTenant.this, "Please enter Flat No", Toast.LENGTH_SHORT).show();
                    tcontact.setError("Flat no is required");
                    tcontact.requestFocus();
                } else if (TextUtils.isEmpty(contact)) {
                    Toast.makeText(AdminAddTenant.this, "Please enter contact no", Toast.LENGTH_SHORT).show();
                    tcontact.setError("Contact No is required");
                    tcontact.requestFocus();
                } else if (contact.length() != 10) {
                    Toast.makeText(AdminAddTenant.this, "Please re-enter contact No", Toast.LENGTH_SHORT).show();
                    tcontact.setError("Contact No should be 10 digits");
                    tcontact.requestFocus();
                } else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tenant");
                    Query query = reference.orderByChild("flatNo").equalTo(flatNo);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()){
                                Toast.makeText(AdminAddTenant.this,"User already exist. Re-enter flat no",Toast.LENGTH_SHORT).show();
                                tflatno.setError("User already exist");
                                tflatno.requestFocus();
                            }else {
                                tenant.setName(name);
                                tenant.setFlatNo(flatNo);
                                tenant.setContact(contact);

                                ref.child(flatNo).setValue(tenant);

                                tname.setText("");
                                tflatno.setText("");
                                tcontact.setText("");

                                Toast.makeText(AdminAddTenant.this,"Tenant's Data Added ",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdminAddTenant.this,AdminTenantDet.class));
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
        startActivity(new Intent(AdminAddTenant.this,AdminTenantDet.class));
    }
}