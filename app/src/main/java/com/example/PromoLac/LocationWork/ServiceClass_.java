package com.example.PromoLac.LocationWork;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;


import com.example.PromoLac.Adapters.Fence_;
import com.example.PromoLac.Main2Activity;
import com.example.PromoLac.MapsActivity;
import com.example.promolac.R;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class ServiceClass_ extends Service {

    private static final int MY_PERMISSION_REQUEST_CODE = 7192;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 300193;


    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;

    public static final int notify = 30000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    DatabaseReference ref;
    GeoFire geoFire;

    private static ArrayList<Fence_> fence_List_Global = new ArrayList<Fence_>();
    private static ArrayList<Fence_> NotifiedFenceList = new ArrayList<Fence_>();

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        if (mTimer != null) // Cancel if already existed
//            mTimer.cancel();
//        else
//        ref = FirebaseDatabase.getInstance().getReference("MyAppUsers/Location");
//        geoFire = new GeoFire(ref);

        mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task
        Log.d("Zzzzzzzzzzzzz", "onCreate: ");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        mTimer.cancel();    //For Cancel Timer
        Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
    }

    //class TimeDisplay for handling task
    class TimeDisplay extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    LocationUpdate();
                    Working();
//                    GeoFenceQuery();
                }
            });
        }
    }

    private void LocationUpdate() {
        SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("GGGGGGG", "onNewLocationAvailableMyLocation: " + location.latitude + ";" + location.longitude);
                        for (Fence_ fence : fence_List_Global) {
                            double radius = CalculationByDistance(location.latitude, location.longitude, Double.parseDouble(fence.getLatitude()), Double.parseDouble(fence.getLongitude()));
                            Log.d("1111111", "onNewLocationAvailable: " + radius + ";" + NotifiedFenceList.contains(fence) + ";" + NotifiedFenceList.size());
                            if (radius <= Double.parseDouble(fence.getRadius()) && !NotifiedFenceList.contains(fence)) {
                                NotifiedFenceList.add(fence);
                                showNotification(getApplicationContext(), "Fence Update", fence.getNotification_(), new Intent(getApplicationContext(), Main2Activity.class));
                            }
                        }
                    }
                });

    }

    public double CalculationByDistance(double initialLat, double initialLong, double finalLat, double finalLong) {
        /*PRE: All the input values are in radians!*/

        double latDiff = finalLat - initialLat;
        double longDiff = finalLong - initialLong;
        double earthRadius = 6371; //In Km if you want the distance in km

        double distance = 2 * earthRadius * Math.asin(Math.sqrt(Math.pow(Math.sin(latDiff / 2.0), 2) + Math.cos(initialLat) * Math.cos(finalLat) * Math.pow(Math.sin(longDiff / 2), 2)));

        return distance;

    }

    private void Working() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Fences_").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                fence_List_Global.clear();

                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot contact : iterable) {
                    Fence_ fence = contact.getValue(Fence_.class);


                    ///////////////////////////////////
                    Date date = new Date();
                    //getTime() returns current time in milliseconds
                    long time = date.getTime();
                    //Passed the milliseconds to constructor of Timestamp class
                    Timestamp ts = new Timestamp(time);
                    Timestamp timestamp = null;
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                        Date parsedDate = dateFormat.parse(fence.getTimeStamp());
                        timestamp = new java.sql.Timestamp(parsedDate.getTime());
                    } catch (Exception e) { //this generic but you can control another types of exception
                        // look the origin of excption
                    }

                    Log.d("TimeStampppp", "onDataChange: " + timestamp + ";" + ts);
                    if (timestamp.after(ts))
                        fence_List_Global.add(fence);
                    Log.d("GGGGGG", "onDataChange: " + fence.getNotification_().toString() + ";" + fence_List_Global.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("GGGGGG", "onCancelled: ");
            }
        });
    }

    public void showNotification(Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.promolac_logo)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);

        notificationManager.notify(notificationId, mBuilder.build());
    }


    ///////////////////////////////////////////////////////////////////////////////

}
