package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddElectrician extends AppCompatActivity {

    Button buttonAdd;
    EditText cname, ccontact;
    Electrician electrician;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_electrician);

        cname = findViewById(R.id.name);
        ccontact =findViewById(R.id.contact);
        buttonAdd =findViewById(R.id.btn_add);

        electrician = new Electrician();
        ref= FirebaseDatabase.getInstance().getReference().child("Electrician");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(cname.getText());
                String contact = String.valueOf(ccontact.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AdminAddElectrician.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    cname.setError("Name is required");
                    cname.requestFocus();
                } else if (TextUtils.isEmpty(contact)) {
                    Toast.makeText(AdminAddElectrician.this, "Please enter contact no", Toast.LENGTH_SHORT).show();
                    ccontact.setError("Contact No is required");
                    ccontact.requestFocus();
                } else if (contact.length() != 10) {
                    Toast.makeText(AdminAddElectrician.this, "Please re-enter contact No", Toast.LENGTH_SHORT).show();
                    ccontact.setError("Contact No should be 10 digits");
                    ccontact.requestFocus();
                } else{
                    electrician.setName(name);
                    electrician.setContact(contact);

                    ref.push().setValue(electrician);

                    cname.setText("");
                    ccontact.setText("");

                    Toast.makeText(AdminAddElectrician.this,"Electrician's Data Added ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminAddElectrician.this,AdminStaffDet.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminAddElectrician.this,AdminStaffDet.class));
    }
}