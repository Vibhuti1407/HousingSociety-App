package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.lang.reflect.Array;

public class MemberRegister extends AppCompatActivity {

    private EditText mflatno, mname, mcontact, memail, mpass, mconpass;
    private Button buttonReg;
    private FirebaseDatabase db;
    private DatabaseReference reference;
    private static final String TAG = "MemberRegister";
    String[] month_list = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    String[] year_list = {"2024","2025","2026"};
    String status = "Unpaid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_register);
        mflatno = findViewById(R.id.m_flat_no);
        mname = findViewById(R.id.m_name);
        mcontact = findViewById(R.id.m_contact);
        memail = findViewById(R.id.m_email);
        mpass = findViewById(R.id.m_password);
        mconpass = findViewById(R.id.m_confirm_password);
        buttonReg = findViewById(R.id.btn_register);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flatno = String.valueOf(mflatno.getText());
                String name = String.valueOf(mname.getText());
                String contact = String.valueOf(mcontact.getText());
                String email = String.valueOf(memail.getText());
                String password = String.valueOf(mpass.getText());
                String cpassword = String.valueOf(mconpass.getText());

                db = FirebaseDatabase.getInstance();
                reference = db.getReference();

                if (TextUtils.isEmpty(flatno)) {
                    Toast.makeText(MemberRegister.this, "Please enter flat no", Toast.LENGTH_SHORT).show();
                    mflatno.setError("Flat No is required");
                    mflatno.requestFocus();
                }else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MemberRegister.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    mname.setError("Name is required");
                    mname.requestFocus();
                }else if (TextUtils.isEmpty(contact)) {
                    Toast.makeText(MemberRegister.this, "Please enter contact No", Toast.LENGTH_SHORT).show();
                    mcontact.setError("Contact No is required");
                    mcontact.requestFocus();
                } else if (contact.length() != 10) {
                    Toast.makeText(MemberRegister.this, "Please re-enter contact No", Toast.LENGTH_SHORT).show();
                    mcontact.setError("Contact No should be 10 digits");
                    mcontact.requestFocus();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MemberRegister.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    memail.setError("Email is required");
                    memail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(MemberRegister.this, "Please re-enter email", Toast.LENGTH_SHORT).show();
                    memail.setError("Valid email is required");
                    memail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MemberRegister.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    mpass.setError("Password is required");
                    mpass.requestFocus();
                } else if (password.length() < 6) {
                    Toast.makeText(MemberRegister.this, "Password should be at least 6 digits ", Toast.LENGTH_SHORT).show();
                    mpass.setError("Password too weak");
                    mpass.requestFocus();
                } else if (TextUtils.isEmpty(cpassword)) {
                    Toast.makeText(MemberRegister.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    mconpass.setError("Password Confirmation is required");
                    mconpass.requestFocus();
                } else if (!password.equals(cpassword)) {
                    Toast.makeText(MemberRegister.this, "Please enter same password", Toast.LENGTH_SHORT).show();
                    mconpass.setError("Password Confirmation is required");
                    mconpass.requestFocus();
                    mpass.clearComposingText();
                    mconpass.clearComposingText();
                } else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
                    Query query = reference.orderByChild("mflat").equalTo(flatno);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                Toast.makeText(MemberRegister.this, "User already exist. Re-enter flatno", Toast.LENGTH_SHORT).show();
                                mflatno.setError("User already exist");
                                mflatno.requestFocus();
                            } else {
                                registerUser(flatno, name, contact,email, password, cpassword);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            private void registerUser(String flatno, String name, String contact, String email, String password, String cpassword) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MemberRegister.this,
                        new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Member member = new Member(flatno, name, contact, email, password);
                                    Payment payment = new Payment(status,flatno);
                                    for(int i = 0; i< year_list.length; i++){
                                        for(int j = 0; j< month_list.length; j++){
                                            String year = year_list[i];
                                            String month = month_list[j];
                                            reference.child("Payment").child(year).child(month).child(flatno).setValue(payment);
                                        }
                                    }
                                    reference.child("Member").child(flatno).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                mflatno.setText("");
                                                mname.setText("");
                                                mname.setText("");
                                                mcontact.setText("");
                                                memail.setText("");
                                                mpass.setText("");
                                                mconpass.setText("");
                                                Toast.makeText(MemberRegister.this, "Account Created.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MemberLogin.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(MemberRegister.this, "Registration failed.",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    try{
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e){
                                        memail.setError("user is already registered with this email. Use another email");
                                        memail.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(MemberRegister.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(MemberRegister.this, "Registration failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MemberRegister.this,MemberLogin.class));
        super.onBackPressed();
    }
}