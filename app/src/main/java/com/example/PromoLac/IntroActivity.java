package com.example.PromoLac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.PromoLac.SplashScreen.Splash;
import com.example.promolac.R;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnChangeListener;
import com.ramotion.paperonboarding.listeners.PaperOnboardingOnRightOutListener;

import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_main_layout);

//        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
//            startActivity(new Intent(this, LoginActivity.class));
//            finish();
//        }

        if (restorePref()){
            startActivity(new Intent(this, MainActivity.class));
            finish();

        }

        PaperOnboardingEngine engine = new PaperOnboardingEngine(findViewById(R.id.onboardingRootView), getDataForOnboarding(), getApplicationContext());

        engine.setOnChangeListener(new PaperOnboardingOnChangeListener() {
            @Override
            public void onPageChanged(int oldElementIndex, int newElementIndex) {

            }
        });

        engine.setOnRightOutListener(new PaperOnboardingOnRightOutListener() {
            @Override
            public void onRightOut() {
                // Probably here will be your exit action
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });

        savePref();

    }


    // Just example data for Onboarding
    private ArrayList<PaperOnboardingPage> getDataForOnboarding() {
        // prepare data
        PaperOnboardingPage scr1 = new PaperOnboardingPage("Promolac", "Welcome to new Era of Digital Marketing",
                Color.parseColor("#46A7F9"), R.drawable.digitalmarketing, R.mipmap.promolac_logo);
        PaperOnboardingPage scr2 = new PaperOnboardingPage("Location Based Marketing", "First time in Pakistan providing Location Based Marketing Services",
                Color.parseColor("#ffffff"), R.drawable.location_based, R.mipmap.promolac_logo);
        PaperOnboardingPage scr3 = new PaperOnboardingPage("What's New", "Content Media and Services adapted to an individual's current location",
                Color.parseColor("#1A597D"), R.drawable.socialmedia, R.mipmap.promolac_logo);
        PaperOnboardingPage scr4 = new PaperOnboardingPage("Over Motive", "Reaching consumers in the right place at the right time with the right message and experience",
                Color.parseColor("#08e6ff"), R.drawable.powerq, R.mipmap.promolac_logo);

        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        elements.add(scr1);
        elements.add(scr2);
        elements.add(scr3);
        elements.add(scr4);
        return elements;
    }

    private boolean restorePref() {
        SharedPreferences pref= getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        Boolean isbeforeIntro = pref.getBoolean("introOPen", false);
        return isbeforeIntro;
    }

    private void savePref(){
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("introOPen", true);
        editor.commit();
    }

}
