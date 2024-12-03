package com.example.PromoLac.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.promolac.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class SubscribedAlreadyAdapter extends RecyclerView.Adapter<SubscribedAlreadyAdapter.SubscribeViewHolder>  {

    private ArrayList<Subscribe> SubscribeArrayList;
    private Context context;

    public SubscribedAlreadyAdapter(ArrayList<Subscribe> SubscribeArrayList) {
        this.SubscribeArrayList = SubscribeArrayList;
    }

    public SubscribedAlreadyAdapter(ArrayList<Subscribe> SubscribeArrayList, Context context) {
        this.SubscribeArrayList = SubscribeArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscribeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_subscribe,parent,false);
        SubscribeViewHolder SubscribeViewHolder =new SubscribeViewHolder(view);
        return SubscribeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeViewHolder holder, int position) {
        Subscribe Subscribe=SubscribeArrayList.get(position);


        Glide.with(context).load(Subscribe.getTopicimage_()).into(holder.imageView);
        holder.Title.setText(Subscribe.getTopicname_());
        holder.Description.setText(Subscribe.getToipcdiscription_());
    }


    @Override
    public int getItemCount() {
        return SubscribeArrayList.size();
    }

    public class SubscribeViewHolder extends RecyclerView.ViewHolder  implements  View.OnLongClickListener{

        public ImageView imageView,subscribe;
        public TextView Title, Description;
        public SubscribeViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener( this);

            imageView = (ImageView) itemView.findViewById(R.id.imagesubscribe);
            Title = (TextView) itemView.findViewById(R.id.subscribeName);
            Description = (TextView) itemView.findViewById(R.id.subscribedescription);
            subscribe =(ImageView) itemView.findViewById(R.id.subscribe);
        }

//        @Override
//        public void onClick(View v) {
//
//            int position = getLayoutPosition();
//            Subscribe subscribe= SubscribeArrayList.get(position);
//            Log.d("laaaa", "onClick: "+ position);
//            Toast.makeText(context, "Subscribed"+position, Toast.LENGTH_LONG).show();
//
////            FirebaseMessaging.getInstance().subscribeToTopic(SubscribeArrayList.get(position).topicname_);
//            SubscribeArrayList.remove(position);
//            notifyItemRemoved(getLayoutPosition());
//
//            FirebaseDatabase.getInstance().getReference("Subscribe/UserTopics").child(FirebaseAuth.getInstance().getUid()).push().setValue(subscribe);
//            notifyItemRangeChanged(getAdapterPosition(),SubscribeArrayList.size());
//        }

        @Override
        public boolean onLongClick(View v) {
            AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                    .setTitle("PromoLac")
                    .setMessage("Are you Want to Unsubscribe this topic?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int position = getLayoutPosition();
                            Subscribe subscribe= SubscribeArrayList.get(position);
                            Toast.makeText(context, "Unubscribed", Toast.LENGTH_LONG).show();
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(subscribe.getTopicname_());
                            SubscribeArrayList.remove(position);
                            notifyItemRemoved(position);

                            FirebaseDatabase.getInstance().getReference("Subscribe/UserTopics").child(FirebaseAuth.getInstance().getUid()).removeValue();
//                            notifyItemRangeChanged(getAdapterPosition(),SubscribeArrayList.size());
                            for (Subscribe s:SubscribeArrayList) {
                                UploadToDatabase(s);
                            }
//                            SubscribeArrayList.clear();





                            //FirebaseDatabase.getInstance().getReference().child("Subscribe").child("AllTopics").push().setValue(subscribe);
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
    private void UploadToDatabase(Subscribe s) {
        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase.getInstance().getReference("Subscribe/UserTopics").child(FirebaseAuth.getInstance().getUid()).push().setValue(s);
    }




}