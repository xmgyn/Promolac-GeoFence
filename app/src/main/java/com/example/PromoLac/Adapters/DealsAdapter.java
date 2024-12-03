package com.example.PromoLac.Adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.PromoLac.DealScreen;
import com.example.PromoLac.DealsInfoPreview;
import com.example.promolac.R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static android.os.Build.VERSION_CODES.P;

public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {

    private ArrayList<Deals> dealsArrayList;
    private Context context;

    public DealsAdapter(ArrayList<Deals> dealsArrayList) {
        this.dealsArrayList = dealsArrayList;
        Collections.reverse(dealsArrayList);
    }

    public DealsAdapter(ArrayList<Deals> dealsArrayList, Context context) {
        this.dealsArrayList = dealsArrayList;
        this.context = context;
        Collections.reverse(dealsArrayList);
    }

    @NonNull
    @Override
    public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_deals,parent,false);
        DealsViewHolder dealsViewHolder =new DealsViewHolder(view);
        return dealsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {
        Deals deals=dealsArrayList.get(position);

        //
        Glide.with(context).load(deals.getDealimg()).into(holder.imageView);
//        holder.imag/eView.setImageResource(deals.getDealimg());

        //
        holder.Title.setText(deals.getDealtitle());
        holder.Price.setText(deals.getDealprice());
    }


    @Override
    public int getItemCount() {
        return dealsArrayList.size();
    }
    public class DealsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView Title, Price;
        public DealsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            //

            imageView = (ImageView) itemView.findViewById(R.id.VcardviewImage);


            Title = (TextView) itemView.findViewById(R.id.VcardName);
            Price = (TextView) itemView.findViewById(R.id.VcardPrice);
        }


        @Override
        public void onClick(View view) {
            //deals info preview
            Intent intent = new Intent(view.getContext(),DealsInfoPreview.class);
            intent.putExtra("Price",dealsArrayList.get(getLayoutPosition()).getDealprice());
            intent.putExtra("Img",dealsArrayList.get(getLayoutPosition()).getDealimg());
            intent.putExtra("Title",dealsArrayList.get(getLayoutPosition()).getDealtitle());
            intent.putExtra("latitude",dealsArrayList.get(getLayoutPosition()).getLatitude());
            intent.putExtra("longitute",dealsArrayList.get(getLayoutPosition()).getLongitute());
            intent.putExtra("rating",dealsArrayList.get(getLayoutPosition()).getRating());

            Log.d("InamInam", "onClick: "+dealsArrayList.get(getLayoutPosition()).getLongitute()+";"+dealsArrayList.get(getLayoutPosition()).getLatitude()+"/"+getLayoutPosition());
            view.getContext().startActivity(intent);
        }
    }

}