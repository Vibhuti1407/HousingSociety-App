package com.example.society;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MemberLogin extends AppCompatActivity {
    private EditText memail, mpassword, mflatNo;
    private Button buttonLogin;
    private TextView attempts;
    private TextView register,forgotpass, admin;
    private int counter=5;
    private FirebaseAuth mAuth;
    String flatNo;

    private static final String TAG = "MemberLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_login);
        mAuth=FirebaseAuth.getInstance();
        mflatNo = findViewById(R.id.m_flatNo);
        memail = findViewById(R.id.m_email);
        mpassword = findViewById(R.id.m_password);
        buttonLogin = findViewById(R.id.btn_login);
        forgotpass = findViewById(R.id.forgot_password);
        attempts = findViewById(R.id.task_attempt);
        register = findViewById(R.id.registerNow);
        admin = findViewById(R.id.admin);
        attempts.setText("No of Attempts Remaining: 5");

        mAuth=FirebaseAuth.getInstance();

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberForgotPass.class);
                startActivity(intent);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberRegister.class);
                startActivity(intent);
                finish();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLogin.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flatNo = String.valueOf(mflatNo.getText());
                String email = String.valueOf(memail.getText());
                String password = String.valueOf(mpassword.getText());

                if (TextUtils.isEmpty(flatNo)){
                    Toast.makeText(MemberLogin.this, "Enter flat No", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(MemberLogin.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(MemberLogin.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Member");
                Query query = reference.orderByChild("mflat").equalTo(flatNo);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            loginUser(flatNo,email,password);

                        }else {
                            Toast.makeText(MemberLogin.this,"User does not exist. Re-enter email",Toast.LENGTH_SHORT).show();
                            memail.setError("User does not exist");
                            memail.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            private void loginUser(String flatNo, String email, String password){

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MemberHomePage.class);
                                    intent.putExtra("flatNo",flatNo);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        memail.setError("User does not exist or is no longer valid. Please re-enter email");
                                        memail.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e){
                                        memail.setError("Invalid credentials. Kindly, check and re-enter email");
                                        memail.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(MemberLogin.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                    if(counter==0 || counter < 0)
                                    {
                                        buttonLogin.setEnabled(false);
                                        memail.setFocusable(false);
                                        mpassword.setFocusable(false);
                                    } else{
                                        Toast.makeText(MemberLogin.this, "Login failed.",Toast.LENGTH_SHORT).show();
                                        counter--;
                                        attempts.setText("No. of attempts remaining :" + String.valueOf(counter));
                                    }
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MemberLogin.this,MainActivity.class));
        super.onBackPressed();
    }
}