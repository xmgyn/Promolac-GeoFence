package com.example.PromoLac.NotificationsTrying;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PromoLac.Main2Activity;
import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>{

    ArrayList<MessagesNotifications> messagesNotificationsArrayList;

    public MessagesAdapter(ArrayList<MessagesNotifications> messagesNotifications) {
        this.messagesNotificationsArrayList = messagesNotifications;
    }


    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.messages_item,viewGroup,false);
        MessagesViewHolder messagesViewHolder =new MessagesViewHolder(view);
        return messagesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder messagesViewHolder, int i) {
        MessagesNotifications messagesNotifications = messagesNotificationsArrayList.get(i);
        messagesViewHolder.Title.setText(messagesNotifications.getNotificationTitle());
        messagesViewHolder.Time.setText(messagesNotifications.getNotificationTime());
        messagesViewHolder.Content.setText(messagesNotifications.getNotificationDescription());

//        messagesViewHolder.Title.setText("Google");
//        messagesViewHolder.Time.setText("GGGGGGGGGGG");
//        messagesViewHolder.Content.setText("HHHHHHHH");

    }



    @Override
    public int getItemCount() {
        return messagesNotificationsArrayList.size();
    }

    public class MessagesViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener {

        public RelativeLayout message_item;
        public ImageView imageView;
        public TextView Title,Content,Time;
        public MessagesViewHolder(@NonNull View itemView) {
                super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.MessagesItem_Image);
            message_item= (RelativeLayout) itemView.findViewById(R.id.message_item);
            Title = (TextView) itemView.findViewById(R.id.MessagesItem_Title);
            Content = (TextView) itemView.findViewById(R.id.MessagesItem_Content);
            Time = (TextView) itemView.findViewById(R.id.MessagesItem_Time);
            itemView.setOnLongClickListener( this);

        }






        @Override
        public boolean onLongClick(View v) {
            Toast.makeText(v.getContext(), "jjjjjjjj"+getLayoutPosition(), Toast.LENGTH_SHORT).show();

            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                    .setTitle("Are you sure you want to Delete?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final MessagesNotifications messagesNotifications = messagesNotificationsArrayList.get(getLayoutPosition());
                            messagesNotificationsArrayList.remove(getLayoutPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(),messagesNotificationsArrayList.size());
                            //t
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref.child("UsersData")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child("Messages");

                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        MessagesNotifications mn = appleSnapshot.getValue(MessagesNotifications.class);
                                        if(mn.getNotificationId() == messagesNotifications.getNotificationId()){
                                            appleSnapshot.getRef().removeValue();
                                            return;
//                                            messagesNotificationsArrayList=new ArrayList<>();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.e("Exexex", "onCancelled", databaseError.toException());
                                }
                            });

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            message_item.setBackgroundColor(Color.parseColor("E5E5E5"));
                        }
                    })
                    .show();
            return true;

        }
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Do your Yes progress
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //Do your No progress
                        break;
                }
            }
        };



}
}
