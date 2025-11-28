package com.example.society;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminRegister extends AppCompatActivity {

    private EditText aflatno, aname, acontact, aemail, apass, aconpass;
    private Button buttonReg;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private static final String TAG = "AdminRegister";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);
        aflatno = findViewById(R.id.a_flat_no);
        aname = findViewById(R.id.a_name);
        acontact = findViewById(R.id.a_contact);
        aemail = findViewById(R.id.a_email);
        apass = findViewById(R.id.a_password);
        aconpass = findViewById(R.id.a_confirm_password);
        buttonReg = findViewById(R.id.btn_register);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flatno = String.valueOf(aflatno.getText());
                String name = String.valueOf(aname.getText());
                String contact = String.valueOf(acontact.getText());
                String email = String.valueOf(aemail.getText());
                String password = String.valueOf(apass.getText());
                String cpassword = String.valueOf(aconpass.getText());

                db = FirebaseDatabase.getInstance();
                reference = db.getReference().child("Admin");

                if (TextUtils.isEmpty(flatno)) {
                    Toast.makeText(AdminRegister.this, "Please enter flat no", Toast.LENGTH_SHORT).show();
                    aflatno.setError("Flat No is required");
                    aflatno.requestFocus();
                }else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AdminRegister.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    aname.setError("Name is required");
                    aname.requestFocus();
                }else if (TextUtils.isEmpty(contact)) {
                    Toast.makeText(AdminRegister.this, "Please enter contact No", Toast.LENGTH_SHORT).show();
                    acontact.setError("Contact No is required");
                    acontact.requestFocus();
                } else if (contact.length() != 10) {
                    Toast.makeText(AdminRegister.this, "Please re-enter contact No", Toast.LENGTH_SHORT).show();
                    acontact.setError("Contact No should be 10 digits");
                    acontact.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(AdminRegister.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    aemail.setError("Email is required");
                    aemail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(AdminRegister.this, "Please re-enter email", Toast.LENGTH_SHORT).show();
                    aemail.setError("Valid email is required");
                    aemail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(AdminRegister.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    apass.setError("Password is required");
                    apass.requestFocus();
                } else if (password.length() < 6) {
                    Toast.makeText(AdminRegister.this, "Password should be at least 6 digits ", Toast.LENGTH_SHORT).show();
                    apass.setError("Password too weak");
                    apass.requestFocus();
                } else if (TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(AdminRegister.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    aconpass.setError("Password Confirmation is required");
                    aconpass.requestFocus();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(AdminRegister.this, "Please enter same password", Toast.LENGTH_SHORT).show();
                    aconpass.setError("Password Confirmation is required");
                    aconpass.requestFocus();
                    apass.clearComposingText();
                    aconpass.clearComposingText();
                } else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");
                    Query query = reference.orderByChild("aflat").equalTo(flatno);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(AdminRegister.this, "User already exist. Re-enter flatno", Toast.LENGTH_SHORT).show();
                                aflatno.setError("User already exist");
                                aflatno.requestFocus();
                            } else {

                                registerUser(flatno, name, contact, email, password, cpassword);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    //registerUser(flatno, name, contact,email, password, cpassword);
                }
            }

            private void registerUser(String flatno, String name, String contact, String email, String password, String cpassword) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AdminRegister.this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Admin admin = new Admin(flatno, name, contact, email, password);
                                    reference.child(flatno).setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                aflatno.setText("");
                                                aname.setText("");
                                                aname.setText("");
                                                acontact.setText("");
                                                aemail.setText("");
                                                apass.setText("");
                                                aconpass.setText("");

                                                Member member = new Member(flatno, name, contact, email, password);
                                                db = FirebaseDatabase.getInstance();
                                                reference = db.getReference().child("Member");
                                                reference.child(flatno).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        //data saved in member table also
                                                    }
                                                });

                                                Toast.makeText(AdminRegister.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(AdminRegister.this, "Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    try{
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e){
                                        apass.setError("User is already registered with this email. Use another email");
                                        apass.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(AdminRegister.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(AdminRegister.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdminRegister.this,AdminLogin.class));
        super.onBackPressed();
    }
}