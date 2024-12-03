package com.example.PromoLac.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.PromoLac.DealScreen;
import com.example.PromoLac.NotificationsTrying.MessagesAndNotifications;
import com.example.promolac.R;

import java.util.ArrayList;

public class ItemsAdapter  extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    private ArrayList<Items> itemsArrayList;


    public ItemsAdapter(ArrayList<Items> itemsArrayList) {
        this.itemsArrayList = itemsArrayList;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Items items = itemsArrayList.get(position);
        holder.imageView.setImageResource(items.getImage());
        holder.textView.setText((CharSequence) items.getLabel());
    }



    @Override
    public int getItemCount() {
        return itemsArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.RecyleViewID);
            imageView = (ImageView) itemView.findViewById(R.id.RecyleViewImageID);
        }

        @Override
        public void onClick(View view) {
            if(getLayoutPosition() == 0) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Nearby");
                view.getContext().startActivity(intent);
            }
            if(getLayoutPosition() == 1) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Discounted Deals");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() == 2) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Top Pick");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==3) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Local Offers");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==4) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Foods");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==5) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Breakfast");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==6) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Bar");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==7) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Shopping");
                view.getContext().startActivity(intent);
            }
            else if(getLayoutPosition() ==8) {
                Intent intent = new Intent(view.getContext(), DealScreen.class);
                intent.putExtra("checker","Credit Offers");
                view.getContext().startActivity(intent);
            }else if(getLayoutPosition() == 9){
                //Code here for Notifications
                Intent intent = new Intent(view.getContext(), MessagesAndNotifications.class);
                intent.putExtra("checker","NotificationScreen");
                view.getContext().startActivity(intent);

            }

        }

    }
}





