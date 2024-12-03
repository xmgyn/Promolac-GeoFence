package com.example.PromoLac;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.PromoLac.Adapters.Deals;
import com.example.PromoLac.Adapters.DealsAdapter;
import com.example.PromoLac.Payment.MainPaymentActivity;
import com.example.promolac.R;
import com.firebase.geofire.core.GeoHash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DealsInfoPreview extends AppCompatActivity {

    private RecyclerView recylerView;
    private DealsAdapter dealsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Deals> dealsArrayList;

    ImageView back,img1;
    Button onmap,Promocode;
    TextView mainTitle,mainPrice,address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_info_preview);
        RatingBar ratingBar = findViewById(R.id.rating);
        ratingBar.setNumStars(3);


        Intent intent = getIntent();
        String Price = intent.getStringExtra("Price");
        final String Title = intent.getStringExtra("Title");
        final String Prcode = intent.getStringExtra("rating");
        final String lat= intent.getStringExtra("latitude");
        final String lon= intent.getStringExtra("longitute");
        String  img= intent.getStringExtra("Img");

        Log.d("Pakistani",""+lat+" "+lon);
//        Toast.makeText(this, "Helllooooooooooooooooooo", Toast.LENGTH_SHORT).show();
//        Log.d("gggggg", "onCreate: "+Title);





        back= findViewById(R.id.backdealinfo);
        mainTitle= findViewById(R.id.dealname);
        address= findViewById(R.id.address);
        mainPrice= findViewById(R.id.price);
        img1 = findViewById(R.id.picture);
        onmap = findViewById(R.id.onmap);
        Promocode=findViewById(R.id.odernow);
        Glide.with(getApplicationContext()).asBitmap().load(img).into(img1);


        mainPrice.setText(Price);
        mainTitle.setText(Title);

        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> addressList = (List<Address>) geocoder.getFromLocation(Double.valueOf(lat),Double.valueOf(lon),1);
            address.setText(addressList.get(0).getAddressLine(0).toString());
            Log.d("Bilaaaal", "onLocationChanged: "+lat+";"+lon);
        } catch (Exception e){
            Log.d("Bilaaaal", "onCreate: "+e.getMessage());
            e.printStackTrace();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        onmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
//                intent.putExtra("longitute",lon);
//                intent.putExtra("latitude",lat);
//                intent.putExtra("decider","0");

                String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)", Double.valueOf(lat), Double.valueOf(lon), "Target Location");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

//                startActivity(intent);
            }
        });

        Promocode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainPaymentActivity.class);
                intent.putExtra("Title",Title);
                intent.putExtra("promocode",Prcode);
                startActivity(intent);
            }
        });

//        TextView text1,text2;
//        ImageView text;
//        text = (ImageView)findViewById(R.id.picture);
//        text1 = (TextView)findViewById(R.id.dealname);
//        text2 = (TextView)findViewById(R.id.price);
//        Intent intent = getIntent();
//        //String str = intent.getStringExtra("image");
//        String str1 = intent.getStringExtra("name");
//        String str2 = intent.getStringExtra("price");
//        //text.setImageResource(str.hashCode());
//        text1.setText(str1);
//        text2.setText(str2);

    }
}
