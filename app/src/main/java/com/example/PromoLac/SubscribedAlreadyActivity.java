package com.example.PromoLac;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.PromoLac.Adapters.Deals;
import com.example.PromoLac.Adapters.Subscribe;
import com.example.PromoLac.Adapters.SubscribeAdapter;
import com.example.PromoLac.Adapters.FirebaseDeal1;
import com.example.PromoLac.Adapters.SubscribedAlreadyAdapter;
import com.example.PromoLac.NotificationsTrying.MessagesNotifications;
import com.example.PromoLac.Payment.PaymentDetails;
import com.example.promolac.R;
import com.firebase.geofire.core.GeoHash;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class SubscribedAlreadyActivity extends AppCompatActivity  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout home;
    ImageView back;
    ProgressBar progressBar;
    Animation myAnim;

    private RecyclerView recylerView;
    private SubscribedAlreadyAdapter SubscribeAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Subscribe> SubscribeArrayList=new ArrayList<Subscribe>();
    private Button b1,b2;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_already);

        home= findViewById(R.id.backhomesubscribe);
        back= findViewById(R.id.backimagehomesubscribe);
        progressBar = findViewById(R.id.progress_circular_subscribe);
        b1=findViewById(R.id.btnSign);
        b2=findViewById(R.id.txt_signup);
        progressBar.setVisibility(View.VISIBLE);
        ;

        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Subscribe/UserTopics").child(FirebaseAuth.getInstance().getUid());
        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubscribeArrayList.clear();
                Iterable<DataSnapshot> iterable= dataSnapshot.getChildren();
                for (DataSnapshot contact: iterable) {
                    Subscribe subscribe = contact.getValue(Subscribe.class);
                    SubscribeArrayList.add(subscribe);
                }
                SubscribeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SubscribeActivity.class));
                finish();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubscribedAlreadyActivity.this, "Subscribing", Toast.LENGTH_LONG).show();
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UploadToDatabase("McDonalds","012e","https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500","Hi how are you");
                finish();
            }
        });
        myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mytrans);



        recylerView =(RecyclerView)findViewById(R.id.subscribeitem);
        recylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setAnimation(myAnim);
        recylerView.setItemViewCacheSize(6);
        SubscribeAdapter = new SubscribedAlreadyAdapter(SubscribeArrayList,getApplicationContext());
        recylerView.setAdapter(SubscribeAdapter);
    }
}