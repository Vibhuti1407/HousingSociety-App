package com.example.society;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class MemberGenerateBill extends AppCompatActivity implements PaymentResultListener {

    Button payBill;
    TextView text;
    String amt = "1900";
    String status = "Paid";
    String month, year, flatNo;
    Payment payment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_generate_bill);

        payment = new Payment();
        Checkout.preload(MemberGenerateBill.this);
        payBill = findViewById(R.id.btn_paybill);
        text = findViewById(R.id.text1);

        Intent intent = getIntent();
        String m = intent.getStringExtra("month");
        String y = intent.getStringExtra("year");
        String flat = intent.getStringExtra("flatNo");

        month = m;
        year = y;
        flatNo = flat;
        text.setText("Payment for " + month +","+year);

        payBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment("1900");
            }
        });
    }

    private void startPayment(String samount){
        int amount = Math.round(Float.parseFloat(samount)*100);
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Cop5LIF6Ot5aAw");
        checkout.setImage(R.drawable.admin);
        JSONObject object = new JSONObject();
        try{
            object.put("name","Society App");
            object.put("description","Test Payment");
            object.put("theme.color","");
            object.put("currency","INR");
            object.put("amount",amount);
            object.put("prefill.contact","+91**********");
            object.put("prefill.email","vibhutinaik14@gamil.com");
            checkout.open(MemberGenerateBill.this,object);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Payment").child(year).child(month).child(flatNo);
        ref.child("status").setValue(status);
        Intent intent = new Intent(MemberGenerateBill.this, MemberPayment.class);
        intent.putExtra("flatNo",flatNo);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}