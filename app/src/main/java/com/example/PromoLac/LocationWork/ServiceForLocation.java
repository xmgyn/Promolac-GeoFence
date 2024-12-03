package com.example.PromoLac.LocationWork;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class ServiceForLocation extends Service {

    public static final int notify = 180000;  //interval between two services(Here Service run every 5 Minute)
    private Handler mHandler = new Handler();   //run on another Thread to avoid crash
    private Timer mTimer = null;    //timer handling

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
//        if (mTimer != null) // Cancel if already existed
//            mTimer.cancel();
//        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(), 0, notify);   //Schedule task


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
                    // display toast
                    Toast.makeText(ServiceForLocation.this, "Service is running", Toast.LENGTH_SHORT).show();
                    SingleShotLocationProvider.requestLiveUpdate(getApplicationContext(),
                            new SingleShotLocationProvider.LocationCallback() {
                                @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                                    Log.d("LocationServiceRunning", "my location is " + location.toString());
                                    Geocoder geocoder = new Geocoder(getApplicationContext());
                                    try {
                                        List<Address> addressList = (List<Address>) geocoder.getFromLocation(location.latitude,location.longitude,1);
                                        String Add = addressList.get(0).getAddressLine(0);
//                                        unsubscribe();
//                                        subscribe(Add);
                                        UploadToDatabase(Add);

//                                Log.d("Bilaaaal", "onLocationChanged: "+addressList.get(0));
                                    } catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }


                            });
//                    FirebaseDatabase.getInstance().getReference().child("Time").child("-"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName().toString()).push().setValue(mTimer.toString());

                }
            });
        }
    }
    private void UploadToDatabase(String add) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("UsersData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LocationsData").push().setValue(new LocationData(add,new Timestamp(System.currentTimeMillis())));
        Log.d("Ammmmmmaaaar", "UploadToDatabase: "+new Timestamp(System.currentTimeMillis()).toString());
    }
    private  class LocationData{
        String string;
        Timestamp timestamp;

        public LocationData(String string, Timestamp timestamp) {
            this.string = string;
            this.timestamp = timestamp;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }
}


















       /* extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service of Location Starting", Toast.LENGTH_SHORT).show();

        SingleShotLocationProvider.requestLiveUpdate(getApplicationContext(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = (List<Address>) geocoder.getFromLocation(location.latitude,location.longitude,1);
                            String Add = addressList.get(0).getAddressLine(0);
                            unsubscribe();
                            subscribe(Add);
                            UploadToDatabase(Add);

//                                Log.d("Bilaaaal", "onLocationChanged: "+addressList.get(0));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                });
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service For Location Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }


    private void UploadToDatabase(String add) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("UsersData").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("LocationsData").push().setValue(new LocationData(add,new Timestamp(System.currentTimeMillis())));
        Log.d("Ammmmmmaaaar", "UploadToDatabase: "+new Timestamp(System.currentTimeMillis()).toString());
    }
    private  class LocationData{
        String string;
        Timestamp timestamp;

        public LocationData(String string, Timestamp timestamp) {
            this.string = string;
            this.timestamp = timestamp;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public Timestamp getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Timestamp timestamp) {
            this.timestamp = timestamp;
        }
    }

}
*/