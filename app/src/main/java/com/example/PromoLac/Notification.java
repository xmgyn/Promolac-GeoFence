package com.example.PromoLac;

import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.promolac.R;

public class Notification extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private EditText title;
    private EditText message;
    private Button defaultchannel;
    private Button highchannel;
    private Button lowchannel;
    private Button nochannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationManager= NotificationManagerCompat.from(this);

        title=findViewById(R.id.title);
        message=findViewById(R.id.message);

        defaultchannel=findViewById(R.id.defaultbutton);
        highchannel=findViewById(R.id.highbutton);
        lowchannel=findViewById(R.id.lowbutton);
        nochannel=findViewById(R.id.nobutton);

//        defaultchannel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"NotificationD")
//                        .setContentTitle("PromoLac")
//                .setAutoCancel(true)
//                //.setSmallIcon(R.drawable.message)
//                .setContentText("Hello");
//            }
//        });
    }

}
