package com.example.PromoLac.NotificationsTrying;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;

public class MessagesService extends Service {
    String fileName = "Messages.txt";
    DatabaseReference databaseReference;
    MessagesNotifications m;
    ArrayList<MessagesNotifications> messagesNotificationsArrayList;
    public static  int loop = 0;

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Log.d("Pakistan", "onHandleIntent: Called");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        messagesNotificationsArrayList  = new ArrayList<MessagesNotifications>();
        //
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot ds1= dataSnapshot.child("Messages");
                Iterable<DataSnapshot> iterable= ds1.getChildren();
                for (DataSnapshot contact: iterable) {
                    m = contact.getValue(MessagesNotifications.class);
                    messagesNotificationsArrayList.add(m);
                }
                saveFile(fileName,messagesNotificationsArrayList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return 1;//;super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void saveFile(String file, ArrayList<MessagesNotifications> messagesNotifications){
        try {
            String filePath = getApplicationContext().getFilesDir().getPath().toString() + fileName;
            File fileRoot = new File(filePath);
            fileRoot.delete();
            if(!fileRoot.exists()){
                fileRoot.createNewFile();
            }

            FileOutputStream f = new FileOutputStream(fileRoot);
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            for (MessagesNotifications m : messagesNotifications
                 ) {
                o.writeObject(m);
            }

            o.close();
            f.close();
            Toast.makeText(this, "SuccessFull Write in Messages Serv", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to Save in File", Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
