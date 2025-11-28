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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MemberForgotPass extends AppCompatActivity {

    private EditText email;
    private Button btn_sendpass;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_forgot_pass);
        email = findViewById(R.id.email);
        btn_sendpass = findViewById(R.id.btn_sendpass);
        firebaseAuth = FirebaseAuth.getInstance();

        btn_sendpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(email.getText().toString())){
                    Toast.makeText(MemberForgotPass.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MemberForgotPass.this,"Please check your email to reset password",Toast.LENGTH_SHORT).show();
                                email.setText("");
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(MemberForgotPass.this, "Error occured"+message , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MemberForgotPass.this,MemberLogin.class));
        super.onBackPressed();
    }
}