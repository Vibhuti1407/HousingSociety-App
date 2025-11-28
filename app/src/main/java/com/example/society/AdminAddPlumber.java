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

public class AdminAddPlumber extends AppCompatActivity {

    Button buttonAdd;
    EditText cname, ccontact;
    Plumber plumber;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_plumber);

        cname = findViewById(R.id.name);
        ccontact =findViewById(R.id.contact);
        buttonAdd =findViewById(R.id.btn_add);

        plumber = new Plumber();
        ref= FirebaseDatabase.getInstance().getReference().child("Plumber");
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = String.valueOf(cname.getText());
                String contact = String.valueOf(ccontact.getText());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AdminAddPlumber.this, "Please enter Name", Toast.LENGTH_SHORT).show();
                    cname.setError("Name is required");
                    cname.requestFocus();
                } else if (TextUtils.isEmpty(contact)) {
                    Toast.makeText(AdminAddPlumber.this, "Please enter contact no", Toast.LENGTH_SHORT).show();
                    ccontact.setError("Contact No is required");
                    ccontact.requestFocus();
                } else if (contact.length() != 10) {
                    Toast.makeText(AdminAddPlumber.this, "Please re-enter contact No", Toast.LENGTH_SHORT).show();
                    ccontact.setError("Contact No should be 10 digits");
                    ccontact.requestFocus();
                } else{
                    plumber.setName(name);
                    plumber.setContact(contact);

                    ref.push().setValue(plumber);

                    cname.setText("");
                    ccontact.setText("");

                    Toast.makeText(AdminAddPlumber.this,"Plumber's Data Added ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminAddPlumber.this,AdminStaffDet.class));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AdminAddPlumber.this,AdminStaffDet.class));
    }
}