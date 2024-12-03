package com.example.PromoLac.Payment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import android.graphics.Color;

import com.example.PromoLac.LocationWork.ServiceForLocation;
import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

public class MainPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    TextView dealname;
    Button ApplyPromoCode;
    EditText Promocode;
    String prrcode,Prcode,dealname_;
    int count=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_payment);

        dealname= (TextView)findViewById(R.id.dealnametext);
        progressBar = findViewById(R.id.progressBar_);
        progressBar.setVisibility(View.INVISIBLE);
        Promocode=findViewById(R.id.promocode_enter);


        Intent intent = getIntent();
        String Title = intent.getStringExtra("Title");
        dealname_=Title;
        Prcode = intent.getStringExtra("promocode");
        ApplyPromoCode=findViewById(R.id.apply);
        dealname.setText(Title);
        ApplyPromoCode.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==ApplyPromoCode){
            prrcode= Promocode.getText().toString();
            if(prrcode.compareTo(Prcode)==0){
                //trying
               MyCountFunction();

                new SweetAlertDialog(MainPaymentActivity.this)
                        .setTitleText("Promo code applied successfully")
                        .show();

            }
            else if(prrcode.isEmpty()){
                new SweetAlertDialog(MainPaymentActivity.this)
                        .setTitleText("Oops...")
                        .setContentText("Field is Required")
                        .show();
            }
            else{
                new SweetAlertDialog(MainPaymentActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops...")
                        .setContentText("The promo code you entered is invalid.Please try again.")
                        .show();
            }

        }
    }

    private void MyCountFunction() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("UAvailedDeals").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PaymentDetails/"+prrcode);
        Log.d("ammmmmmm", ref.getKey() );


            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        PaymentDetails paymentDetails = dataSnapshot.getValue(PaymentDetails.class);
//                        String string = dataSnapshot.getValue(String.class);
                    Log.d("banana", "onCreate: "+ paymentDetails.count + ";"+Prcode);
                    UploadToDatabase(dealname_,prrcode,new Timestamp(System.currentTimeMillis()).toString(),paymentDetails.count+1);
                    }catch (Exception e)
                    {
                        UploadToDatabase(dealname_,prrcode,new Timestamp(System.currentTimeMillis()).toString(),1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//        Log.d("AAAAAAAAAA", "MyCountFunction: ");
//        UploadToDatabase(dealname_,prrcode,new Timestamp(System.currentTimeMillis()).toString(),1);
    }

    private void UploadToDatabase(String dealname, String promocode, String time, int count) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("UAvailedDeals").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PaymentDetails").child(prrcode).setValue(new PaymentDetails(dealname,promocode,time,count));
        Log.d("Laaaaaaaa", "UploadToDatabase: ");
    }
}