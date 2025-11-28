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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {
    private EditText aemail, apassword;
    private Button buttonLogin;
    private TextView attempts;
    private TextView register,forgotpass;
    private int counter=5;
    private FirebaseAuth mAuth;
    //private FirebaseAuth.AuthStateListener mAuthstateListener;
    private static final String TAG = "AdminLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        mAuth=FirebaseAuth.getInstance();
        aemail = findViewById(R.id.a_email);
        apassword = findViewById(R.id.a_password);
        buttonLogin = findViewById(R.id.btn_login);
        forgotpass = findViewById(R.id.forgot_password);
        attempts = findViewById(R.id.task_attempt);
        //register = findViewById(R.id.registerNow);
        attempts.setText("No of Attempts Remaining: 5");

        mAuth=FirebaseAuth.getInstance();

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminForgotPass.class);
                startActivity(intent);
                finish();
            }
        });
        /* register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminRegister.class);
                startActivity(intent);
                finish();
            }
        }); */

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(aemail.getText());
                String password = String.valueOf(apassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(AdminLogin.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(AdminLogin.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Admin");
                //Query query = reference.orderByChild("aemail").equalTo(email);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            loginUser(email,password);
                        }else {
                            Toast.makeText(AdminLogin.this,"User does not exist. Re-enter email",Toast.LENGTH_SHORT).show();
                            aemail.setError("User does not exist");
                            aemail.requestFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            private void loginUser(String email, String password){
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AdminHomePage.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        aemail.setError("User does not exist or is no longer valid. Please re-enter email");
                                        aemail.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e){
                                        aemail.setError("Invalid credentials. Kindly, check and re-enter email");
                                        aemail.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(AdminLogin.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                    if(counter==0 || counter < 0)
                                    {
                                        buttonLogin.setEnabled(false);
                                        aemail.setFocusable(false);
                                        apassword.setFocusable(false);
                                    } else{
                                        Toast.makeText(AdminLogin.this, "Login failed.",Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(AdminLogin.this,MemberLogin.class));
        super.onBackPressed();
    }
}