package com.example.PromoLac;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.PromoLac.Adapters.Fence_;
import com.example.PromoLac.LocationWork.SingleShotLocationProvider;
import com.example.PromoLac.NotificationLogs.NotificationMainActivity;
import com.example.PromoLac.NotificationsTrying.MessagesAndNotifications;
import com.example.promolac.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.eventbus.Subscribe;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingDeque;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback , View.OnClickListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    LinearLayout search,location_, home,message;
    FloatingActionButton favorite;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        home=findViewById(R.id.homedata_);
        message=findViewById(R.id.message);
        favorite = findViewById(R.id.favorite);
        search = findViewById(R.id.search);
        back =findViewById(R.id.backhome);
        location_=findViewById(R.id.location_);

        home.setOnClickListener(this);
        message.setOnClickListener(this);
        favorite.setOnClickListener(this);
        search.setOnClickListener(this);
        back.setOnClickListener(this);

        Log.d("aMMMMMMMMMMMMMMMMMM", "onCreate: ");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        int decider = Integer.parseInt(intent.getStringExtra("decider"));
        //LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //if(decider == 1){

        SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(),
                        new SingleShotLocationProvider.LocationCallback() {
                            @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                                Log.d("Location", "my location is " + location.toString());
                                LatLng sydney = new LatLng(location.latitude, location.longitude);
                                mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(location.latitude, location.longitude)).title("You"));
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 12.0f));
                                Working();
//                                mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f), 2000, null);
                            }
                        });
         //   } else {

//            String a = intent.getStringExtra("longitute");
//            String b = intent.getStringExtra("latitude");
            Log.d("MMMMMMM", "onCreate: 1");
     //   }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }
    private void Working() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Fences_").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot contact : iterable) {
                    Fence_ fence = contact.getValue(Fence_.class);

                    ///////////////////////////////////
                    Date date= new Date();
                    //getTime() returns current time in milliseconds
                    long time = date.getTime();
                    //Passed the milliseconds to constructor of Timestamp class
                    Timestamp ts = new Timestamp(time);
                    Timestamp timestamp =null;
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        Date parsedDate = dateFormat.parse(fence.getTimeStamp());
                        timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    } catch(Exception e) { //this generic but you can control another types of exception
                        // look the origin of excption
                    }

//                    Log.d("TimeStampppp", "onDataChange: "+timestamp+";"+ts);
                    if(timestamp.after(ts)) {
                        //fence_List.add(fence);
                        LatLng MyAreas = new LatLng(Float.parseFloat(fence.getLatitude()), Float.parseFloat(fence.getLongitude()));
                        mMap.addCircle(new CircleOptions()
                                .center(MyAreas)
                                .radius(Integer.parseInt(fence.getRadius()) * 100)
                                .strokeColor(Color.BLUE)
                                .fillColor(0x220000FF)
                                .strokeWidth(5.0f));
                    }
//                    Log.d("ABCDEFG", "onDataChange: " + fence.getNotification_().toString() + ";" + fence_List.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.d("ABCDEFG", "onCancelled: ");
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v==home){
            finish();
        }
        else if(v==message){
            Intent intent = new Intent(getApplicationContext(), MessagesAndNotifications.class);
            startActivity(intent);
            finish();
        }
        else if(v==favorite){
            Intent intent = new Intent(getApplicationContext(), SubscribeActivity.class);
            startActivity(intent);
        }
        else if(v==back){
            finish();
        }
        else if(v==location_){
            Toast.makeText(this, "Fence", Toast.LENGTH_LONG).show();
        }
        else if(v==search){
            Intent intent = new Intent(getApplicationContext(), NotificationMainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
