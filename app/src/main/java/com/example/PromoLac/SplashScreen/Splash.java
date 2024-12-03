package com.example.PromoLac.SplashScreen;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.PromoLac.IntroActivity;
import com.example.PromoLac.Main2Activity;
import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class Splash extends AppCompatActivity {
    Animation animation;
    ImageView imageView;

    TextView titlepage, subtitlepage;
    Animation btt, bttwo, imganim;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Gul", "onCreate: "+ getIntent().getExtras());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        btt = AnimationUtils.loadAnimation(this, R.anim.btt);
        bttwo = AnimationUtils.loadAnimation(this, R.anim.bttwo);
        imganim = AnimationUtils.loadAnimation(this, R.anim.imganim);

        imageView = findViewById(R.id.promolac_splash);
        titlepage = findViewById(R.id.titlepage);
        subtitlepage = findViewById(R.id.subtitlepage);

        imageView.startAnimation(imganim);
        titlepage.startAnimation(btt);
        subtitlepage.startAnimation(bttwo);



        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(1*2000);

                    // After 5 seconds redirect to another intent
                    if(FirebaseAuth.getInstance().getCurrentUser() == null) {
                        startActivity(new Intent(getApplicationContext(), IntroActivity.class));
                    } else {
                        Intent i = new Intent(getBaseContext(), Main2Activity.class);
                        startActivity(i);
                    }
                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
