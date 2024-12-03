package com.example.PromoLac;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.PromoLac.Adapters.Deals;
import com.example.PromoLac.Adapters.DealsAdapter;
import com.example.PromoLac.Adapters.FirebaseDeal1;
import com.example.PromoLac.NotificationsTrying.MessagesNotifications;
import com.example.promolac.R;
import com.firebase.geofire.core.GeoHash;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class DealScreen extends AppCompatActivity {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout homenearby;
    ImageView imagenearby;
    ProgressBar progressBar;
    Animation myAnim;

    private RecyclerView recylerView;
    private DealsAdapter dealsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Deals> dealsArrayList;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_screen);

        homenearby= findViewById(R.id.homenearby);
        imagenearby= findViewById(R.id.backhomenearby);
        progressBar = findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);

        homenearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imagenearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mytrans);


        dealsArrayList = new ArrayList<Deals>();
        //Testing
        TextView text;
        text = (TextView)findViewById(R.id.DealsTitle);
        Intent intent = getIntent();
        String str = intent.getStringExtra("checker");
        text.setText(str);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(str).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Iterable<DataSnapshot> iterable= dataSnapshot.getChildren();
                    for (DataSnapshot contact: iterable) {
                        Deals deals = contact.getValue(Deals.class);
                        dealsArrayList.add(deals);
                    }
                    dealsAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        recylerView =(RecyclerView)findViewById(R.id.dealsitem);
        recylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setAnimation(myAnim);
        recylerView.setItemViewCacheSize(6);

        //setting here


        dealsAdapter =new DealsAdapter(dealsArrayList,getApplicationContext());
        recylerView.setAdapter(dealsAdapter);

    }
}

