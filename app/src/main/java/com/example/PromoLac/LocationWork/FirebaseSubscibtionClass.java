package com.example.PromoLac.LocationWork;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Arrays;
import java.util.Stack;

public class FirebaseSubscibtionClass {
    static Stack<String> stringStack = new Stack<>();
    private static String[] arraySubStrings = new String[20];


    static void unsubscribe() {
//        stringStack.push("karachicity");
//        stringStack.push("Bilal");
//        stringStack.push("BinQasimTown");
//        stringStack.push("GreenTownShahFaisalColony");

        while(!stringStack.isEmpty())
            FirebaseMessaging.getInstance().unsubscribeFromTopic(stringStack.pop());



    }

    public static void subscribe(String add) {
        add = add.replaceAll(" ","");
        add=add.toLowerCase();
        arraySubStrings = add.split(",");
        for (String substring:arraySubStrings) {
            FirebaseMessaging.getInstance().subscribeToTopic(substring);
            stringStack.add(substring);
        }
//                                FirebaseMessaging.getInstance().subscribeToTopic("Green Town Shah Faisal Colony");
        Log.d("LocationAva", "onNewLocationAvailable: "+ Arrays.toString(arraySubStrings)+ "  ");

    }
}
