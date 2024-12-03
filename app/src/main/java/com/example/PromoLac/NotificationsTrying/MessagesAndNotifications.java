package com.example.PromoLac.NotificationsTrying;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.PromoLac.Fragments.MessagesFragment;
import com.example.PromoLac.Fragments.NotificationsFragment;
import com.example.promolac.R;

public class MessagesAndNotifications extends AppCompatActivity implements View.OnClickListener{

    LinearLayout NotificationButton,MessagesButton,FragmentLayout;
    View Mesageview,NotificationView;
    ImageView backhome;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_and_notifications);
        NotificationButton = (LinearLayout)findViewById(R.id.FragmentNotificationButton);
        MessagesButton = (LinearLayout)findViewById(R.id.Fragmentmessage_button);
        FragmentLayout = (LinearLayout)findViewById(R.id.FragmentToSet);
        Mesageview=(View)findViewById(R.id.viewMessages);
        backhome=(ImageView) findViewById(R.id.backhome);
        NotificationView=(View)findViewById(R.id.viewNotifications);

        NotificationView.setVisibility(View.INVISIBLE);
        Mesageview.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentToSet,new MessagesFragment()).commit();


        NotificationButton.setOnClickListener(this);
        MessagesButton.setOnClickListener(this);
        backhome.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        if(view == NotificationButton){

            NotificationView.setVisibility(View.VISIBLE);
            Mesageview.setVisibility(View.INVISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentToSet,new NotificationsFragment()).commit();
        }
        else if (view == MessagesButton){

            NotificationView.setVisibility(View.INVISIBLE);
            Mesageview.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.FragmentToSet,new MessagesFragment()).commit();
        }
        else if(view==backhome){
            finish();
        }

    }
}
