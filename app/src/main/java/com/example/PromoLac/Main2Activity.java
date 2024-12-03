package com.example.PromoLac;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.PromoLac.Adapters.Deals;
import com.example.PromoLac.Adapters.Items;
import com.example.PromoLac.Adapters.ItemsAdapter;
import com.example.PromoLac.Adapters.SliderAdapter;
import com.example.PromoLac.Adapters.SliderItem;
import com.example.PromoLac.LocationWork.FirebaseSubscibtionClass;
import com.example.PromoLac.LocationWork.ServiceClass_;
import com.example.PromoLac.LocationWork.ServiceForLocation;
import com.example.PromoLac.LocationWork.SingleShotLocationProvider;
import com.example.PromoLac.NotificationLogs.NotificationMainActivity;
import com.example.PromoLac.NotificationsTrying.MessagesAndNotifications;
import com.example.PromoLac.NotificationsTrying.MessagesService;
import com.example.promolac.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main2Activity extends AppCompatActivity {


    Stack<String> stringStack;
    //Location work

    LocationManager locationManager;
    LocationListener locationListener;
    TextView locationText;
    LinearLayout linearLayoutForLocation;
    ImageView dashboard,imageView,imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7;
    ProgressBar progressBar;
    LinearLayout personLinearLayout,notification,dashboardLA,home,message_button;
    FloatingActionButton favorite;
    CardView LocationCardView,DimondCardView,PlatiniumCardView,GoldCardView,SilverCardView,BronezCardView;
    SliderView sliderView;
    private SliderAdapter adapter;


    //for Items
    private RecyclerView recylerView;
    private ItemsAdapter mainScreenAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Items> itemsArrayList;





    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //Log.d("Gul", "onCreate: "+ getIntent().getExtras());

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

//     <Closing Location Service For MyApplication Fencing Testion>
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else {
            startService(new Intent(getApplicationContext(), ServiceClass_.class));
        }


//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
//        } else {
//            ReadContacts();
//        }




        //location
        linearLayoutForLocation = findViewById(R.id.linearLayoutForLocation);
        linearLayoutForLocation.setVisibility(View.INVISIBLE);


        //Slider
        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapter(this);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(7);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("SilderImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable= dataSnapshot.getChildren();
                for (DataSnapshot contact: iterable) {
                    String img = contact.getValue(String.class);
                    SliderItem sliderItem = new SliderItem();
                    sliderItem.setImageUrl(img);
                    adapter.addItem(sliderItem);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
//        adapter.addItem(sliderItem);
//        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
//        adapter.addItem(sliderItem);
//        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
//        adapter.addItem(sliderItem);
//        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
//        adapter.addItem(sliderItem);























        //location working here

        SingleShotLocationProvider.requestSingleUpdate(getApplicationContext(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        locationText = findViewById(R.id.locationsetHere_textView);
                        Geocoder geocoder = new Geocoder(getApplicationContext());
                        try {
                            List<Address> addressList = (List<Address>) geocoder.getFromLocation(location.latitude,location.longitude,1);
                            linearLayoutForLocation.setVisibility(View.VISIBLE);
                            locationText.setText(addressList.get(0).getAddressLine(0).toString());
                            String Add = addressList.get(0).getAddressLine(0);

//                                Log.d("Bilaaaal", "onLocationChanged: "+addressList.get(0));
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }


                });
        //testing
        FirebaseMessaging.getInstance().subscribeToTopic("Malir5558");
        FirebaseMessaging.getInstance().subscribeToTopic("Karachi112");

        personLinearLayout = findViewById(R.id.person_linear_layout);
        notification = findViewById(R.id.notification);
        dashboard = findViewById(R.id.dashboard);
        dashboardLA = findViewById(R.id.dashboardlA);
        home=findViewById(R.id.home);
        message_button=findViewById(R.id.message_button);
        favorite = findViewById(R.id.favorite);
        LocationCardView = findViewById(R.id.LocationBasedDeals);
        DimondCardView =findViewById(R.id.DimondDeals);
        PlatiniumCardView =findViewById(R.id.PlatiniumDeals);
        GoldCardView =findViewById(R.id.GoaldDeals);
        SilverCardView =findViewById(R.id.SilverDeals);
        BronezCardView =findViewById(R.id.BronezDeals);




        // Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(NavigationBar);


        personLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent1 = new Intent(getApplicationContext(),MapsActivity.class);
                intent1.putExtra("decider","1");
                startActivity(intent1);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NotificationMainActivity.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
            }
        });

        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MessagesAndNotifications.class);
                startActivity(intent);
            }
        });

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
                Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(dashboard);
//        dashboard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                FirebaseMessaging.getInstance().subscribeToTopic("Malir");
//                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
//                intent.putExtra("value","Dashboard");
//                startActivity(intent);
//                //finish();
//            }
//        });
        dashboardLA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FirebaseMessaging.getInstance().subscribeToTopic("Malir");
                Intent intent = new Intent(getApplicationContext(),Main3Activity.class);
                intent.putExtra("value","Dashboard");
                startActivity(intent);
                //finish();
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SubscribeActivity.class);
                startActivity(intent);
            }
        });
        //Hotitem1
        LocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Foods");
                startActivity(intent);

            }
        });
        DimondCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Nearby");
                startActivity(intent);

            }
        });
        PlatiniumCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Sports");
                startActivity(intent);

            }
        });
        GoldCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Home Appliances");
                startActivity(intent);

            }
        });
        SilverCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Makeup");
                startActivity(intent);

            }
        });
        BronezCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DealScreen.class);
                intent.putExtra("checker","Wearings");
                startActivity(intent);

            }
        });


        itemsArrayList=new ArrayList<Items>();
        itemsArrayList.add(new Items("Nearby",R.drawable.ic_navigation_black_24dp));
        itemsArrayList.add(new Items("Deals",R.drawable.discounteddeals));
        itemsArrayList.add(new Items("Top Pick",R.drawable.ratedoffers));
        itemsArrayList.add(new Items("Local Offers",R.drawable.local_offer));
        itemsArrayList.add(new Items("Foods",R.drawable.restaurant));
        itemsArrayList.add(new Items("Breakfast",R.drawable.breakfast));
        itemsArrayList.add(new Items("Bar",R.drawable.bar));
        itemsArrayList.add(new Items("Shopping",R.drawable.grocery_store));
        itemsArrayList.add(new Items("Card Offers",R.drawable.creditcard_offers));
        itemsArrayList.add(new Items("Notifications",R.drawable.notification));

        recylerView = (RecyclerView)findViewById(R.id.itemRecyclerView);
        recylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setItemViewCacheSize(4);
        Animation myanim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.mytrans);
        recylerView.setAnimation(myanim);
        mainScreenAdapter =new ItemsAdapter(itemsArrayList);
        recylerView.setAdapter(mainScreenAdapter);

        imageView = findViewById(R.id.hotItem1);
        imageView1 = findViewById(R.id.hotItem2);
        imageView2 = findViewById(R.id.hotItem3);
        imageView3 = findViewById(R.id.hotItem4);
        imageView4 = findViewById(R.id.hotItem5);
        imageView5 = findViewById(R.id.hotItem6);
        imageView6 = findViewById(R.id.hotItem7);
        imageView7 = findViewById(R.id.hotItem8);
        progressBar = findViewById(R.id.progressBar);
        Glide.with(getApplicationContext()).load("https://images.unsplash.com/photo-1414235077428-338989a2e8c0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").into(imageView);
        Glide.with(getApplicationContext()).load("https://images.unsplash.com/photo-1556228453-efd6c1ff04f6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").into(imageView1);
        Glide.with(getApplicationContext()).load("https://worldinsport.com/wp-content/uploads/2020/04/SPORTS.jpg").into(imageView2);
        Glide.with(getApplicationContext()).load("https://estore.fdl.com.bd/media/wysiwyg/CE_BLOCK.png").into(imageView3);
        Glide.with(getApplicationContext()).load("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUTExIVFhUXGBgZGBgXGB0fGhsaGxgXGB0YFxobHSggGh0nHxgYITEhJSkrLi4uGh8zODMtNygtLisBCgoKDg0OGxAQGy0lICY1LTUwNS0tLS0tNS8vLS0tLS8tLy0tLS0vLS8tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAMoA+gMBIgACEQEDEQH/xAAcAAACAwEBAQEAAAAAAAAAAAAFBgMEBwIBAAj/xABHEAACAAMGAwQHBwIEBAUFAAABAgADEQQFEiExQQZRYRMicYEHMkJSkaGxFCNicsHR8DNDY4KS4SRzsvEVNFODoiWEk8LS/8QAGgEAAwEBAQEAAAAAAAAAAAAAAgMEBQEGAP/EADARAAICAQQBAwIEBgMBAAAAAAECAAMRBBIhMUETIlEFYTJxgfAUQpGx0fGhweFS/9oADAMBAAIRAxEAPwB69IPFAsNmLKR20yqyh13cjkoNfGg3j88zppNSSSSakk1JJzJJ3MbvxRc0u2ik4GvssNU6D9t4yHifhOfZKse/K2mLpTkw9k/Lw3FL0Y4jWoZRmLc2bEcliXUKSDXUajqKb8usRzK6c4t3M6KwZvaJCnkBXE/QbA7Zn2YdFTUbtts0iyu9o7iAhs8TEM1AHGpY0FGz7oLV1gPcfE1qm2+YJJVUo2QGQoaDFnmx3+mUAbumzrbaFk2fShVSRki5CZNYdRRabL3d6xLapKWWfMlthtEovVZoYdpWgIKzVzWYCTkdddM4AoDD39YmoTOKlkyWeYDKmy0OHCahyNAGoK1OoOeuVIyy23q892mTWxOxqWO5P06CD8u9g0siZW0SKUaYF+9ljlPljUfjXlvrCtetiEohkYPJb1HBqPAnnA1VBM4nbLN0L3bxBaZH9Ge6U9mtV80bunxpB+TxwZvdtMmWwORZQQab1GYPyhClTPh/PhEkktMYS0BZmNABuf5vDYKgk4Hc020cNNZ27SVieV6wVWIdOsp9R+U5fSC96300iTJdbQ1o7WtFcAOFXJgzqAcQbu0dTmDyi9cPEcohbPPXs2AVFZjVWoAMzTuk08OscX5w1Kn4mkTJfaA0NGBUNyemn1hT1f8AzNF1ajC3jafnxj9/EFSuLuzestmAyqGGVd1IqaEcxlDncfFcqeAD3X5ag/lMZRfF2TrMcM5CtdG1VvysNfrFC47xaXbJAU5PMRWGxDMFz651r0iet23bSJy6ms171M3lbUN99okW0itNPGMn9Jt5WsdnJszOgcMzuhwsaEAIG1A1JpzEB/R5eN4SLUkm0GY0qZUEO+PCaVDKSThPypWLseJmTd1MdVihItgCAn4b86RVm3m2wA+cGtTN1EveicGGXmACsV2tZ2AECJl5NSjAeUd3fbVmAiveGoj5qSvJlGltrt67l9prHeBd536tnlNM1OiL7zZ0HhqSdgCY7vS1YRhrmdeg/n6xlHFPEQOKaa4VBWSNj+L/ADUHgoHMwEfaQBgQLxjfbuxlqS02ae9TUlsgo6nIAcqCGqUxuyxS7FKztVoIecQc8Td0Ip2GVK8lbQtkO9EnDTTpht88VzPZA7toZnloPPpE/a9rfs1W0l1w1/DKQfV2PnHDwMxKLuYLGa4bgSQtSA81s2c558hyUbQaGhp18o4mAjTT6RGGw5ZnmPLf4xIWzNdUAGBIbxsUmchSaispGYpz5Qs8LXg91W5bG7l7HaD90WNezfLKvmK9CDtDepxClc6jX+UhU9KVirYserS5ktgdwSwQ/JvlB1sQYnUVhlz5E1stCJxTevbP2an7uWc+TOP0X615CJ7ZxKWssgIfvZ0mW7EewrIpJ/Ma0HmdoXwugEVDEy55i3OkdiaP5/3j214JcszHNFUVJ/brtCDN40m1OFVC1NKg1ptXOPiZ9NhrHLgEEEVB1B3jkNEFttGFesYAyZucYmZekHhSzy8U6U3Zj2l2NdQg2J08/OKHAnBT209tOJl2fQU1cDLClQaINCaZ59TByVd7Xtb/ALOCfs0g4p7A6nMYARvqv+s7CNXstkVZnZoFWVKVVCqBQADJfLlypGxp1baN0ybyu87YE4b4Hs0iVaJUtGCz0Ks1TiClcOFWOY9YnxJjPeKeFJ1nynK06SBQT0A7VFGgcHJ1HJsuRWN3kCn8/n8EQ2tEYUJEPxmIn5kMqbIImy3xJWizUJFK+y1c0P4H12Jix20uaGzWRMb1jT7iYf8AFTWU/wCIZeEaXxLwKMTTbGwlua4k/tuNwV0oeVCOlc4zW3XZhcrh7CcP7bGiN/y2JovRWJXkw0gSMQoJnB5TdlMQq+wOYIO6sMmHUQaueQ0rvGoc6HcDofrFJLUUBkzZYZAc5Uyowk7ofWlN1GR6wRSeSvcLTpa5lD/5iUOZH91R7y5844Jd9O1S6a8WMMxnsl7rMASfkdpg/wD2/eJZ1jmhu450HfRqECtRmNa0yBqKgVrSFWRPDAFWDKdCPoRtB67bweRkKOh9YbE/pyjo5M9kVp1lWEwQfB/6+DD138XMi9hbkE6Sfbw1IHN06e8umpCxNY+A7K89LXZp5eUCHWWSCAwzBDalQc6HPrFR7NLtCkpnzX2lPMEZ5bEQGT7RY3MyQzYdWoK759pLHrfnSjDcHWDwDyZ5PX/Snoy9BO3yPj8/8zRrxsSTqA6g1FKVBOuux/m0RSboSW2M1LUpXkOnXwHmIo3NxPZrYFWZ91OOmfdanuNo3hqNwIY1sGEa1HOCxMQWDo9yCQ5Oe23hE1I4kjIRKBFo4EymJLEmRssD5llImBlJB0qIK0ita7QFpudhz2AgXPtOY7TFhau3uDb4JeqOagj7w/h93z+lYQr14ZFqtCBGIUmhUn1Fr3nXmdBQ9Iab4tlKivVj1/n6QY4OusqDPcd5vVHIDT4fWvSM0NlvtPU3VpXR7vxGFbBYVlosoLhRAFVeQAoPlCveHDGC8FtkpjiY/eqdGUoZZKnUN6ppocOVDq7MozNNdc/pFS3Sqiq502/Qw3hhiZgO05lSYB5fzQxBMQCp0J08OVI8J92vgdQf5/OUVrvSzyQvbTZcsnQzHC1I1zJzPxiR6is1Kr1YTuS5rT4mFn0l2v7hLOoq81w2EbhDl/qcoo8+UWb14ws0uplHtmpXKoTIE17Qihy9zEekR8HcPzrVP+3WsEAGstWFCTQgNgPqIoJwqc8yxzj5FPmL1FykbVl0WB7PLRComKABXRsgABXQ0Ap4CJZViVgGQ5kVodetPn/MoYr6QJLLEdANyToBAm87rWy2btps0iYxHcoCCT7KDIggb1/eJbHspclORFBVdRumU8fX4XmGStQks0I5tuT0Gg8+cJRmGNivrhyz26XjRwDSqtkGHLoy/wArvCM/BFqBICoQDriGfWCTXIwy/B/fUBqSDxzNnpFC32NpkqaQzL3WwsoqwNDmo3I2HODYswbIZc4gvSYyFURRhwsWJ6YQF86kk/h6wyjTYPMbdfkcQJwnc/2GzLLl/wBRs2J1ZyNTzAHyENlmTslArU6sTudyfE5xzdkkzAJjrQ0yH69K5fKKXE1raUhwAEmgp7zFgqryoSaeFYuEixmdS7zee/ZycgKY3IqFqKga95iCDTQA57AmZV1inemTCeYmMvyQqvyji5LuEiUqDNtXb3mObMfPbYZQQxU6QBbM+xBb2AqTSY7D3Zhx/At3v/kIC33w9ItSlZiZj/Uu2JW1pnn451BqWCbPqx/nwivPUnMajQnn16Z0PQmFLdziUNT7ciY3xNwbPswzU2iQPVIymyx0NDVfwkFTyXWE6bZio7SW+JVNQ61VkO2LOso9c0Oxj9NrNRpOMjKmh1z0HjtGf8ScGo7GZJPYzczjX1TXYgajnz3rHbbVr5aBXU1n4Yu8CXBJtkuZNtL9lOPdR5YpWmZeenqknQEAVFTXMR9flw2ixms1cUs6TkzQ/m93z+cAnWdZJoBpZ5uxH/l5g6Gnc/6eiQ98L8c4vuJwCPTvSpnqMDutcqHpUHrCjZ/N2PmaWjvs07YTg+QfP7+P6RbsbMGUy64/ZA1Phzhomz6KBNQBtXK7jQKv4ich+0GLNcskTMdlbsmOZkPnKY/hOq+WXSEz0i3TaGRQyOApxTDSoZ6CjBh7IBIAy1O8PW4EcyvX/UW1C4A/z/r+vMpzbgl2gs9iPYztXssz1WI3A3p765jpFu4ON7RZXEi0I1dOzmHvU5yphymD8LUbqYTrFfjIQtoLOFPdmr/VlkaEkZtT4xodyoLyQyZ6JaEwkpPXJqggYWoPWzOeRFDWusNU56nmbQuPdG+6bzkWkF7O4r7ctsiD4HNT0OXhrFk2lA2FmCt7rZN4gHUdRUQhX3wVPslJ1keZMCDMV++WnuNSkxfwMDplHd3cfrMliXapYehA7QCmE6VZSCZbbaEVh3qlRzIRR6h9pj1MtCgZd48lz/7QBtlqIq7escl6bVHSLk6+ZAkjCfW3A0X3jSoA2xAla71yhfvIu5GEetkp9kDmeghFtxcYE2/p+iSlt7+P3xPblsBtM+h/poauebbL/N/CG232wg9mndC5Ej6DkBE1x3csiUqLnuTzJ1MDJinG1dcR+phDnauBKEcX3Fm6HU8WoNQSDzBNfjWsXrDbSWwsc9m509lufQxSIjyR66095fqK/KsAjEGPuqVkORC86zqxFRQnIU/2ivO4eScpRhVG9Yk/9NM8XIilOceXreqSQMbBcRoOZP4QMyegzjxeKJSr94J0oH25smYif5ndQF/zERZumPjEX7luexWG1FpYLPhw4nJYiupq29MstiYerJbFIqKU6Qn3jdmIlk3zp451U7fSKdmtUySdyv08eUQuXQ5liqjrgdxtmWyUbXWe4RZa1lKdGNKs9dMQ0C6/OM7vm8nvW1kAlZCZDou/+ZvpzpDHe17WeZLVXoXJ7orRhShLA65ZfwRJcNzLKBA1Zi5O5J58zEvLsVjMBBmSSLDLwhaYQoopG1No8Nh5OPgYN/Y46+xiHfwNZ5P/ABEG4ysXLZo1Kcv15x19oqwLjanjnr9IDLMeW1DkREtpt+Iqi07RtOQA1c9BkKbkgdQ5bCDLGoRl4jRLY0/nygPe9nLvJ6TUY+C4m+oEd2S1uvdclhzOsWrRnRlIyIIPUEEV8xFCuG6kFlTVnmW3to9mIjNrmTFZ8sx6p+R909R/vvHqtziRnbODKURAuRPcfWOseUXRMECb8t+XYyz942X5RoW8vrTrTi1kmC1oxIbK2KSpzwd9q+LMR8jl4wOl2ymWq8ovXnaxZZSKEJX1cgcqAU/hhXF7SnJKNTSqkZ5iun7RN9RqdsFD1K/p7Jghh3C14XbJtCFWUOp1U6jqNweohEtPB3ZTUxFptlDVZP7ssa/dsM9aVptqCcw4JaaZg6RPZ7UrCja8+fjGQurak4U4Px4/8mlZpVcZYZH/ADB1mEySoeW5tNly7yj76UP8RBqB7y/DeGG9b/lLIGGjh1y3BB3rvAW0XeyN2shzLma1Hqt+YfqIB223Wd5mGeDZpxzqATImE+0VHqsfeUb5qco0tLaLjjrHYkWpqC+7Of7/AK/Mq2vglZydovcfpy/WGzhC7FsdmCD12NWNKVP8/TaLN3SZqgBpeRGTKQUI5qw186RamSSvON6kgczy/wBQR34WX7LeOzfH94AcUcHyLU2OWOynU/qJlXYBho1fpBmyWA+uxyGnjFtKIpc6Dfn4Dc7QbsDwInSI4GWme3Fcc+yM6T2ShYYUYHsZnNgwzkvsCvXakGUMuRjZlZSxASSWJoK6qSSGGfrCvWGDtknVRxQ9aEfGBd4XbNlIQiLOQVZZb6VpkVbVPERG1ZzlZtV3jbtaGJNlfs1aS+HRsLCo/KdwPCKs37xjlhm+1LJ1p7SH2h/Mt0+4vSsspms9us7y3TIMgBryxLUUrlmMvCJ7XxIlucGUcAQ54qhl0OIHLPqMhTXOGvjHMTW7K2VjGZD6YG+H66RYkSBLBmPqBkBt+5OkQ3JeodQjsTMzzIpizNMwKYqDMdIuzXBIyqK5DTzga6wTkR12qYrgyhwjJ7V5tsmgGZjeVLB/ty0OEheRLBiTvlDQVBFCKg6g6eYhUsVsFkmzRMqLPNbtFehIlOQA6vQd1ThxBuZatMoKWjiiyIoJnyyTkFRg7E8lVakmCYYMUrAiDblsYkzrRZV/py8EyUPclzQ33Y6K6PTkCBtFa9rpmOxMtgpHPfpBO5Vf760zlwvOYUSoJRFGGWhIyJ9ZjSubkVIFY9ts2gpqT8yYXYfbiVaWvLbvEQ5txMZ3bMWBAyU0KYlyqp9nc0577QZsV7MpwvkesM0mwdyhzO/iYDXrc5I7orTbfyP6GENpyBkQzqAzEHrxDl3XsGFGzHPfz5wXFOYjNbGsxWKio2zyI6eMMq3TNIr2rCudAxy6Q2ksRzEXVKMHMh4gn0bCoqRr0gPYJq4mJFHbI/lFaBemZPiTF2YK1J1MVJ1nrGG2usLlvHxPQVaStUC+fmHbLasqNmPmItY8IJGawt2WcQcLZ8j+5/WC/YsAERu+/wBM6sfgY09Natoysg1VZq4Pn+kIybTUEr4EEVB3oRvrqM4jFqFe9Kb/ANt1I+D0I+JhQvbjZLMRZ7NJNomEkVFaYhrhCgs58KDrHsnioGYyTAJbqQGV5brQkBgCTkMiDmd4uIU/imPuIJ2xxtNodxSWMHNmIJpyCjKvXFE103QqVY1Zm9ZjqeVenQZRUuK8Jc04aYX1pWoI5qd4ZpaweAOoBJMFXpZgaDp03hKv3hcNVpQCsOmRppDtetpVcTE5IBUeOwrr4dRFebNQDM58t/OMy3f6hxLaiFUTPrkLTCZcyomDXr1HXpBK0WJ5eZzXmP1EH5V1rMmicuRHLfxgzPsYIgbvpqXrlhg/Mrr+pNUQByPiZbxNxCbNILAg8gdCx0H6kchGZWO+ptaTKzQzVIObYifZ61OnwpBz0q2wm2mRhwpK0HvMwBLfQeUEPRTwz200WqYvcQ/djm4yLeA0HWvKKNFpBp69vZ8yPW6r17MjgeI+Wa7rY0yzN6ssKncDUwtlUHalIeZljU6ise2TlyEWTD29vAmVdYWODKP2WmQJpy2ipe5JlgMMgdR8NPOC5EcTZQYEEZHIwIYiKVipilUprmCNdqaftFi6HmT5wlg/drRia+zrT40y6GL7XdlQ5/ykWrAOy0GR15w+WgZmW+krgWf9onWkpjluVwsle4qooo42JbEa5ilM9RCLZ5k6RkrEU9VqVKnrzEfqSRaQwhV4u4GkWhGaXSVMoaMBlU0FSuVTkKRwczp4md8IhrWauMAk0DUGpOYwNsDSp3AoMhGktMKrXMmlBpAi57vk2VVs6d1VyUn2ju2LQseXhlSLVstEaFNAUTNvuLNO1mBt6ZctennF9bDJRQRLUNqSAKk+NIH3NZ8R7Q+qNOp5x7eVuHaLLrqc+gGdenj1EJvKiHp99jBRClpmjbRdPGBEy1KgefMPclgkdSASSBvl+sTTZocAIQQdCDUU51gFfs8zF7GUQBSrMdFljOp6saHwC69oBGaW/mnpyuxNg/X8v/YdtPE9mlyO37QGWR3aZljn3VHPptvCxcnHon2jA8sS0Y0U1qa7Yts4z68LY03CCaqihFAFAAOQG51iCyWZ3dUlgszEKoGpJNKQPqt4ihp1wc/6n6BFlBIagqN4uBIqXJZXlyJaTXxzFUBm5n9aaV3pWCVIsmYTEikeFYuWywlD0+cVqR5GytkOGE9alisMqZXeVFHia9Xs1gmlTQzGEpW3ANS1D4BqdYMyZJdgo3+nOKPpSurFd/dH9J1cge7RkJ8sdfIxpfTKCWNniZ/1LUAJ6fkwn6NOGls9llzWUGfORXcnVQwxLLHJVBGQ1NTAG8rP/wDUbcKVxNJNP/t5I/SKDek9/ssmTZ5dJwlos2ZMHcVwoDdmte+cqgnIVGTZwppbp4mNN+0O0x83ZziDn8Q2poKUoMhQRqOhYYmPW4VsmE+IbdNu6ZJmyyOzxiozOHchTXQgNlG1m81EhZvvAYRzJFQP18AY/NvGV/GfLlyStJgepoQVIoVFDrni0IypvGl3LxBLtEtAJyBUpKBxAYB3VaY1dC7UC12pzMdQsEwe51wrPkdRpsNmNomdo5rLQmn4n3c9Bt/tACotX3r/ANJv6Uv2cGzuPaZtc9KgeJy/OIJdnEmzSFWZNm0CIDkJdaF2YVoNhzPOhgFchwyxJfKZJpLcdVFAw6MKMDyMMC4GIBbccztbAEOOQexmDRkyB6OujjoYO2ni5Uu82pwA4qmD/GBKFR0xAmvuisDLTOSWpd2CqBUkxkfFF/NNHZDJO0mPTq7HMjc0ov8Aq5x0GcYTqxTptsdjPfFZ5VZkx2FSikliss61c17vwzhl4Y4xeR3cEtZIySWRTCo0GMe1TUmoJrpBW5rrRZMqzKhFAHtGKmIzGAJVqZVG42oBAXizh5BUyDh/AdPI7fTw1gchTiUDTWOm4TVeHb9lWnvSzqKFTqD/ADeDxjE/RJcM/wC0PNfHLRBQg5Y2IqPIA1qOka59tZThdSfxD9R+0Cyk8zNsrOZerHjuACToIoz7zRciHJ2ARvqQB84FX1bZhXTCNhr5tQj4QGOMzun0z2uFA7hV7ylrkTU8hnHS22Wd6eMAbDZ1pVhU8joPL9YltkpSMsvD9o+9Yz0P8DUPbz+cNTZRGanOKVrtrtRTkBr1j64rUWUo3s6eHLyihedqKOaioPLUfvFmnKsQxmRrKbEJQc/4n1rIKkMAQdQYFWSU0yZ2YJK61Oqjx36b+Md221ZZZ106+HWDd02QSZZZ6Yjmx5dPARoWPtWYZGTie3na1s8rwoABudgIz3ia9mlS2Gs6Z6xHsjZB1/eL15X52rNNFSqZShz5zP0ELlzS2tNrUkVWWcR6ufVB679MMYltnqNgdT0Wh0hqXLDkxx4Vu7sLLRzmwxzKdc8Ipz0y2HOPQoKTEmOARXtHbLFNINEA5IDoMsRNMlEE7SVSWWJyXQjVpmxHgaU605Qmu2g2GnUnMk9Sc4s0GjOpfJ/CJbqPYu0dxetdxzU72HEvvJmPlpGlejfhfslFqmrSY47gPsofaI95h8B4mFywW55TVXMHVTofHr1jQuG73WctFbMao3rL4Hcdf+0Ov+l+g28ciSXXuV2wy66CJKR2ixJhiQtJMSGdLVgcQBHWF+fdValchsNYv2q8Azdmp8T+0WVGQES2VV2D3DMauospGVMoXbd2DNs2Pyie22UOpU6EEEcwdjXaLVI+pHUUIML1M+26yxtzHmYPxZwVPs8ysgY0Y90Vp/lzNKjOlT8YhsHDFocfeI6dApr8SSI3W3WBZiFSNdOhGYPxhd4jtsux2VpzUrSig+0xGQ/U9AYehz3G1WFhzMK4ouwSpglDOYaEjUiugNNWPIbeIg6lzzJCLZUVu2YdpMIH4dRzCiqLp3sR1YUucEXK9onzLXMNMLFgzCtZhBIFDyGZ90L+Fat/DV2M0wl37SbOOJnApSUpopA9mooAOpOeEGOk4lCqT+Uqeijh9j/xUxSuWFFPsqNBnpSpPixh3tlxWe1hZyODUd2bKehK65MpzHTMQs+lHiIWeT9jlHC8xazSuWCVpToXoV/LiPKM99HvEFsS1qlnmBZJOKYr5yllrmzkZYaDcEZ0EF9oEePSBd0mxWfGzvMmscMoTHJod3C6ZDemuHnCPwPcxmO1qdcSy2wygdHnUqNdVljvdTTkY74ivWbfN4KkqoUnBJB9mWMzNYbbsdNVXlGz3fccqRZVsyqOzVaUO51LH8RNTXnHJ0HB5iyksSpebd45k7k61PnARHDTAWzFQAOZ/aGG9LoJrhq3/UOor631gELvmyZnaYDMSlBUUp1psYQ3Hc2V1KtXhO4/XJNUjIivKFvjS0T1nY1J7IADLbmT8flHF2Xwla1wnlvDc9kDoA4rUZwFt2AMSFK9r5aLN1X6Sozr0rBa3t2iqR1hbvbht5TGZIbxEXeGWmzkYMpAByJ512jqv6gwI5AKnFg8S8kyPJkyLZstcnQg81izLsKDRSTzb9oH0mmj/FVdwXd1vWW7VrUL8TrQdY4k1bFMmavnTYKNB9T5x1OsQUmmef8APGF3iy9D/RTKub02XZddTqc9PGAZyvB8SgIjncvZ+ZHOtknE015jYZLYwqmgLbLi+vSu8Dm43nWuU0t1RQWPeWoqnukHnzrp41hctKvPmLZpWrHPoBqT9B1g1ePB06UoMlWagFRv4j9oPfYy9zLtqoFgIUcf3nYngCGTg+7QksuAA0wkjouVW86fTnCHZpM9mEvs3B3qpAHU1EaetkmCzlE9fCAaClFGijrSE0Utvx8y1r027j4gO/bf2j4V/ppkvU7t+g8zvAyPiKZEUPWPAY97pqFprCLMh3Ltkz0xZukv20vsyQ+IUI8c69KVr0iqxhu4RsaqhmkAluWwroPHeA1V61Vknz1Fs2BH6TmKxJFCTe0oj1qeIP6RL/4hL975H9o8mc5iIIQd7AFBOpJ2612MSWG2guUJFRlrXrrvFa2z+xTCM5j60/m0VbFYGGZ9bXzicDJ4jbK9ycxkj0RRk22mTih57H9osG1IPa+GcMmY1bA4IndpnrLRnY0VQWJPICsYLxVfjXhaUlqwWUhCrU0WtRWY3IaHoMPWGj0r8UMQLJLOEN3n501GKm29Og5iEBSJMvEdTkoPmc/iWJ6nnBj2ymivyY7Xnf0iyNJu+UhmIKB8FMZZ6EDXMuaMwr3VwDUQ13LbhJLFwDMfM7V5KpOQUaD47wkejvhg52ycOfZKdT7z+J9UefSC1vmkkk7xo6PTrYSzSpPcNviZ5xpLtInv24OOYS7vTutsAvIAUAXUAARo/BfDMqx3fNm2oKO0QtOxDIS6f0yN8tRuT4RDJ+8KhhioagkVpT9IYrXd822TZEl0w2WWFmzs8pkwHuSqa4VIxtXI90QvVUrQ4Ge+oFiYlH0b8OpZpUy2vLwNOzlyzXFLlHNJeeeI6nyG0MDzTmzZk6/sOVIkvC0gsD/bXQ7V948hyOnhA61ToznfPAmVqLCTgTmbbQgJYkjKnOpIAHXMjOGGVJXABtSEG7gbVav8GQank0zl4L9a8hBO/wC9XBwSWIbcjYHIeZ/c7QmwFxyZraMPsy06t91Sp0/AqjukGY3zwfvDFa73lywMRIHx+kDbnsnZSwpOdKufmanmYHgdtMMz+2h7vVhv4D6+EcSncMSx28mE7Va61JyX5npSPLDeBx9mkupABw4gGpnmKZUy1gLaMT0+6dq0YFDogpXeoO/8pHdZyYnlKJeJlAM1jjFe7mCDVTsKgxw3bTivgRyaYEZfkxjk3upJVhRlIxDUDxMEJs5QrEmmEEmu3WFibYZlHmtgdloiqrEEmoqTWg1Oh5GPTeGJsJJoVKtUUo261pQ+Gegh1N5Y7Wi7dOB7k8dyrY71WcxKNioaU5QMv3g+cwaZJJmM1SymmIn8NciPw5RJw3KWRaXlnRjkY0OQtImfIbBlF2qb+XgYmYejy5ihmWmahR2bCFYEFQu1Dmvh4cof/tNRQKKc94jtoq58T+kdKIOyxkwBEoq2cmRTpCsM9edBXntlBex2VVTLOudedd4FzcqE8x8jU/IGCK2pZcnG7BVRasTsAM6xTp3LDJ7k+pUr7R1E7j+xS5MtrQSFpQdWJ0UDcwmWWeG0gfx3xc1omCdpKQkSJZ1J/wDVYc+m3xgRwsWky51vnuwlEFUl/wDrTDkNdgdxnkdgRGtpte9R2nkScORJ+NL6wDsEPeYd8jZTt4n6eMQ8BveSLNn2GW0yVLoZ0vVXJGirqWoK93vDLUGkLFlkTbVaAijFNmtlyqcyegA+AEfojgx5d3yJdkmqJYBynD+nMdjmXJzluTlRsvVAOwk1WrN1mT+gnSrMM44g3g7i2z23ug9nPHrSnOdd8Byxj5jcCHASYzD0yyLOLRIl2aX/AMdMYMxl5EjRMQHtlqENkQFJJ0jT7rkTFkylmzMUwS0DthHeYKAzeZqYAHMVA+JiWmt6x9XoD/PpHqu+zEeB/SCVostRSKRlkGlIz7gUOBNajawJluyWmoIfP+ajrHvEN7y7JZXnvTuigHvPoFHiR5CpiGVL3OWXy1J+UZFxtxM1utAVD9zLJEobMxNTMI5foB7ximljtyZHqVXdhYLEx5015841ZjicnSuoHQDU+AG0WeHLsa3WpRmJa5notdfzMf05EQLvS0hV7IHIeudydcPUk5nyEWOH/tlo/wCFsYK4u9MINP8A8jjMKNMI1zrUmkMQZOYhuBgTZ71vix2ZFltOkoUAAXEMQGlMIzpAWyW+x21ikufK7XYKwqf8pzPlC1K9FSy3krOtDETCyt2a4aMEZ1Ck1qKK2ZHKLN4eifDR7LamExTiUTQCKjMUZR3c98JgjqvRfGcGHXU5G5Y42S5uxoKgu3LlsP1MHiMCdmNT6x6f7xmfD/F02yTzIvMFXp3Zh9U8ixGVD7wyyNaRoSTajFrXOsSXB7LTexyfH2EVfY2zaP1nzuNBTKFjiF+zBEonE3dVdsR3XdfDTLICGK1TMK5ZscgIzmbxjIl2+k0M0uXVca50me22HdVGVRnkcjC61J5kdNW5vdG2zSlsVlVaVc0AA1eYf5Unxivw4qzZjsHVzLajUP8Ac38hoPAwvcW8QGnaLUPMUrZwRnLl+1OYHRm2r05EQi3FabQs9WszujVwJhPrE64tmUamoMUivIx5mn6gBwOpuF62gs32eWc9ZjDYcvExaezYJWFRSmkDrlshVe8cTHN295tz0HSCU+caUGvL9uUW16YhczrEt1B9mkJmxZpBKYFNVIZs9Aa1I5ZE1iH712xGXiZFwlJhpmwBFBmrUzz+G8SzpIPssd1zyR+f8rHFokOuLDR3BDGZkMS6FPhXICm+sYltZRsGalbhhkSNZBQqp7qJVmmS8guuTKQRz7x5bR1ITEFAmFxjZgCtCNaEnLI+G8WJchjU1KLMXOgBYnSrHQGmW+gg1YLGxOJjWlNqaQVCEsIvUWBUMXOILrJo65MuYPhE9x8WKQEmmjjKu1IaLTYwdYHzeFJMxsTy/r884stqD8zPruGNrSQd4YxmrZj9477ZdTr1gpLslFCg0AFAMtogn2bmI6a1IwZxLcGJ/El84RhX1iMjso5iFK1Xy06SbPNZjKJGhzyNRUkHfOD1+3JMcED161NfaHIdIS5qlGKsCCNjrElm5W+09t9L02muo2kAnznuC7DwfNtFqAmN9wubODTuj2RyJ5+J1FIE8aX2s+aJcru2eSMEpRplli/QdB1MG7+vtrPIeWCQ05aBa5hCc2PQ5gDepgDwPcotNoXtKdmuZB9sjPAOfM9IqR/Zkzyv1HS106o1VHI/t9o6ejnhqbJli11CzXHdVhUdmdm3BbI1BqKDqC7z+IEwFGlfft3UkNn2jnIBGpRkrqaVVakgQNRHk/0s5Y/tMcgP8NvZ/Ke7kKYczBvg2ydu325wQmEizq2oQ+tOI5voOSj8RhQT1Ggu3opgdwhw/wALSbOiMwV5wzLke2VwkpWpVad0CuSgDaLjW1fcc9QpIPUEDOAvGXEi2fCCCS3sg0OHlXYnc7DqRAZPSkAAPs3wmZeXdiouo4kArY8x7tzqqliaAbwCtN+AeslU99Tp+YcutYoccXqQyyVOnebxPqj6n4QryL1dDrUcjpE116q22ek0f0hrK94/xGnidJ9osjpZyoLgCoOq1GJa7VFR+2oy+8bkm2KWsx8JZ8hQ+rrkRvzJEaDYrYUUzZTYKZsjeodTny/mkJ8uV/4ra1kpjEmWFxEtVqasS3MmoHQVA1hindjEy9ZR6BOYoW+yMkuW7V75YrXVsNKsfNqeOLpG8cDXCtjsqJQdowDzTuXIrTwGg8OsZ/6XLKsifYgFpLWWQANKIy1HwIjVZNsZwGSRNKsAQWwqKHMVDNiH+mKVZE/EZmgE9Ti+8pYf/wBN5b+ADAMf9BaLcUrxsNonSpkr7mXjRkqS0z1gRpROfMx5dVlmzJUt5k9sTIpYIqKKkCuqs2tdCIh1YW1gUMqruFK+8QTx7w6tssrin3ssF5bdQKla8mAp8DtCp6G76cpMs7MWEujSydlb2fy1GnWNOa4pDCkxTMB1E12df9LMV+UYt6OLXLk2m1TiaSUlE16Y+6BzNMgN4LTpgYMnuuW05Aj36Q+JBZ5RC0E6YCFp7K+0/TWg6+cZTw9YEJadOykygHfmR7Eoc2c0JHKg9qO71tsy2Wkuy1LMAEHOpwSgeQ1J/MY7vm1qqCQhxLLarMP7s7Rmp7qnujw6CKOIsCULztj2iazOaM+bcpaDRB0A+Jrzh64BuQCk0qale4D7Ka16M2v7Qu8LXAZ8yhFUUgzTsz6iWOg1byG4jXLNJEsBdz/tFFNefcZTTVu5MnxAAARStc8DInXWOrV3e8GIPxBHUftA6zjGS5I1yAOlOfWH23bV47jSmOYxWUqygio6GJ5dmXLujLSmXygEs6jgKdB/K/zeGOxLUAnlGHrNYlCbm5PiAiMTwZZs9lXUClYIIgAipKekWi9RH2k1tV6+3g/EXajA8zlVqaxIRHksRJSKWOYsTjDHQG0d0j6kcn0CXhZu9WFbjSbYrPJNonyw0xB3Fz77bKabb51oAYeLyIFCds/lH5o9JnFf220kSz9zLJVOTc389ugHWGEAjmUV6qyv8JIP2gCk+32oBRjnTWyGgrr5AAeQEaddN1y5UoWcoQyULqwo2L3viMmBplkcog9GNzPZE+1zJWMzVACj+pLStcQHtYsiRrQClSaQ8X7Os86zh1AmuWwScBo/anIIDSq5jvAjIK2IUBELPPUfSm0F27Pz8RfumwTbTO+yFsUhQGnMfWwE5SSfaL0IrqFDVzIMaFetvSTLJoKIAAo3andQfU9Ip3FdYsdmCYsU1qtMcj15h1Y8lGQA2AUQCFpWZMM5zWz2epqf7k05+fP/AE9RDMBFkpY2v9pLxHaZUixH7SFebNq7IRq5HdFdVwjlCFIs924VxTJ+KgxUApWmdPOCPEk5JklrXaS2OaaWdQdFBzcjeugG/nCmb7mr3Vl2QAZAMpLADIBjueZhI55jX44jZxZeoluvaZu5LH8K11Pwp5HlFOzSi5qpqIW7wthtNomTnFVrp8klj9fPnGhcPcPvZrNMnlGee6Fio1yBIlryJ/blHLdOHOZpaL65Zp1KEZHj7RS4lvAmlllHXOYRy5eekXOBeNpFgBlWiyTJeJjimrmCRloQKgDLInnvBDgPhBpva2m1qwx4gopQ1OrgHSmi15HnE9ktpsE02S3y1mWWZXCxXEtNMQB1HNdV26sqXYMTP12qOps3n9iEuPlkXnYhOssxZplEuKethIoylSMQNKMAQKlBF30U8VLaLOtlmMPtElQtCf6ksZK68yBQH47wFvr0coD9ouu0GUxGMIGqhBzGE1qFPmIze3raZdoXtQZM0Nky1XvD2lYHu+VB4Vj6xQ4xJFYqcz9PGB105B03SbMHkzdoo/0usZVYfSPeUgYZ0hJ4GjMrIxHMuowN5CI5fHl5zps37NZuz7UqarLZypCBKhzRakAZsKZCEJSwMK5g64EevSTxOtkszSkb/iJylUAOaqcmmHkAK05nwNMMsjthKL7bCg54agV6CpPj4CGC/rknSkM+2TCZ005KzYmJ3aY2Yoo9kVHqitMo+4P4eNrmBSCEpVtiJfug7FyKV5YjtFKrjiJVcQfJcSkqp77giWd1Qmjz+hemFTyBPOPLkuxp8xAg1ISWNq0zb8qjPy6xFa5cybMbEuEk9/KioBkJY5BQMNOkOt3TFsFnE8qDOmgLKRssErUuQM+9kT/lGWcFmGqknAjpdV1LZkWSui6Hdq6sT7xP7bRZmLuw0+sCOHOIhaBR1owpXdT1U/oc/GGyXLx0rQqNKjPwruIdXcMcSgM1fBEHSbtaYRXf5CObdcjymJUYkPtAZg7Yv3+kNdks4UVOpipbpuKoGkJtfPJi/UJMXLvu44qHQZnqdh4QwqscSJAUUESx4fV6ltRYXPXiWjgYn0fF6R9CzxHf6y27NcyMzTnyPQRzSozWjacY8z4jIxHKzPWJ6RnXDvF7doEmqO8aKVr8D067b9NAk21GAzArHskYOMrIXQocGSx0I5Z1GrAecAOLOL7PYpDTXbTIKPWYnQKP4IYBF5iv6beIBZ7E0pW+8nfdgA54ci58MPd/ziMS4MuD7VNJf+knrdSdFH1PTxjjiK/J95WrG5AqcKLU4Za13NPNmpzPQbFd3Ctnl2aWkhqMq5TVpVycyW2dSSctgciILBPUKsqGBbqQ2e9XlZT6FNpoFABt2i+z+YZa+rBnhK71mTGvB1CgjDI/5YrWc3V9jslPeIgFYrrm2q0fZZi0lpRrQ6+qyGuGWNwXpmNlDZ5gw3cSXokmW2YCSxVthUCoXwAFT0HlH1aY5Mo1N4b2KeIs+kTioyZZCnvzKhRyHX41bpQbiFvhq+Gt0lZUxBKs9nBac6nJxWtTXRmOXxOVKQl3/e7Wic0x60Ox1C1yX8x1Pw0Ah1uWwi1XcJFlDIPWnEimNx7AI1UDIH5VqIC1uOYukc8Ra4nv8z5rTj3UHdkoNFQZAgbch1qdhCobY/vU6RZvuRNlzCk5Cja02poAuxUDLLrA6OgcQHYkzbOC+Bpqz62iXhSTmoJB7SYdXyJyH7Rp6yBSkWUSI7WaCGHiLEHzDiYKn85mLF5XJItEkyJyBk/+Qb3lPst18tImsNmwjEdT8hyizAAQiZhPFHCdvuyaJ9nmO8lfVmp60ta1wzUzGHPM0wn8JMNlxul72UraklrOGXcPeIypMwnNQTtU6daRpgMJvE/A9nf76S5s8xanueqfAD1D1X4GOmcES7Jbpl0WhZNsBaysaJNA9XqP1XzG8axIkoyq6EMrAFWGYIOYIMZZb7JbpyTLNagJspskmUDMtNGDVrUdRXWHHgK63sdkSQ8wzACStRTCCa4aVNAM946rZjVTLYMUPShw/Nm2pJi1dMCqFBGRBYkEE6GoNekNvBtzCzyRiBxHvOaUzpoOgAoIZC45CPneoI8Y7KPSSJnFFgktPCy5CTJxAZqmigaBptMjoQBQk0O1TAfiLhm0T6TMUlpiigADLUa4aliDvqBDFYP61qr6/bUNfdEuXg8qfOsX4nssOSJ2qoYDeYpXCQq4pUkyvZmySciy5EqT4eRzOuKGi6r2K0I76ggGuTKT7LjY8jodtQI9uKzhrRaRTu0kH/3CHr54RK+US3hcuFwwqtK5qBodjlRhzU5eecAVZfcvmGrq59N4emXmjrVTTnXIjoesBrS6WiUyVrLJK4pbUZWUg6jcGn+9aRTkzuzNWVdQa1OA02Pu+BqNhyghMskucxmyW7Cf7Q9lxiBIZcxQ0AxKK5wW7eMdGKs0zVHPidWRnlIqu7TaZFyADqfjlTXPI6xes9oVxiRgwqRUHcGhB5EHIjaKxlNQLMASYQKkZyyzF8KqTSuSVpqMoSbbcNosk0zbC5QsSWksay5nVCcq65Gh5GkY+p+lq5yntPx4P5fH5QVu+ZoE4mhprGTznLzGY7sSD0r+0PfC3E0u0VlvVJ4yaW+TV6A0r9YrrwWomhsZMvFUr7XOgPyrFFX05qqwByT3+/tDq1C5Of0lS4Lnopn0zIon0LD6Dzi3JnOEIU5kmnIAUqT8dIbJiVGFVoKU0yA0oICX3dy4MAyBwhqbri7w8wSPONausVptETZaG7gpZrMARNdgdCCAD4DlCh6Q+GJ9qRGlGpl4qy2PrVpQg1oTllnTM5ikAze83tXmTFq1ZmFcsKKrFQgBNRSlMh45xoHA9uebKpMNaTGUEjYU66ZsPKCBB4i9pmS8JS5Uma6zQZc71cLilBlXUak7a0prDU96NYxjluArNTs29RiTr+A7kjLUkGNC4j4MstuSkxO9osxaB1/cdDlCdK9F7mcgtE7FZpZxDM1ZcyUIOY0A1OVaR2ARiPVz2YWayLQhpszvu5p35jgEuTUjCBoK5KoA0jJPSLxB2j9ghqqHvV9p8mz86MeuEbGG3jjiVpEsoKBmGFE2RQBTLwoxHVF0NYxyYxZtyT5nM77lmOcfMfE4BLdzXY9omrKTMk5n/qc/z9I/QfDN0JZ5SoooFHxPM9a5wtejvhX7PLDOv3r0LfhGyeW/XwEPhNBSILnycS2pMCBOJOG7PbEKzkFdmHrA8xGazPQ69ThtS0qaVXOm1c42FzFctC1sZejGNWrdw3WIHXPpE0RvGoRM0SRXjqIEiYQMKe1hfvq3hnEsVIHugmrZ0GXh9ekHLQe63gfpCZxOo+ztlt//ADEupYgYEo06jOTLFkm1CtRlDDKoI0JBB6g1g5ZJcR3cxe7ZbOcTYDm2ZyJ3MS3Kfux4wNBw22Os5r3/AHxLBliOklV/7xMY+lxbJ/VIGIGvi4mZ+2kMEmUCsGBwTFFaBqZgipow57iKK2K2Nl2UmWffMwuB1ChFJ8CRDgI+ECUUnJixa6jAMG3HdSyFoCWJJZ3bVnOrH6ADIAAbQUdQRQiseLrHUcbuCDA9uuetSnmDpAaZYHl0IWoGi7j8h28Dl4Q4xXtQ1hfphpZVq3T2nkQJY72BGGZ3l507ykineGoNCcx/vFubZO73QJso07uWJc5SKF5gDGxqa5wPCjtNB6p/SCd15KKZa6eJji88GFqqlX3LxFq9eGpc8B1LMV9WYppOSlQM/bFQcjHV3XzaLKAtrHaStBaEBy/5q6qeufnDBendtEorkWYAkZVAkWggHmK5xeeWO+KChOYpkaqtaw5ZE06stpSYoZGDKRUEGoI6GIbwswcZwmcFMVttqlrkgNQgyUHEcwugh+fSDgxCva6JYcsahmqDgLDFXWqg0BPMUJghcd04QCRhXZf1gnPUGdmIutH00K0AAM7kKIzv0qcbmyFJNnI7bJmqKhV2Ug8+XLlUGNCXQx+ZOLHLW6aWJJ7eZrnoSB8BHx4Eks/GZXv2+ptoftJuEzGoMKiiga4VHzJ5mHT0VcLdo32yaKorES6+1MGreC6DqPwxm59YflP1j9PXDKVbBZwAABJlUAFAO6unxMAeYsdy3KQKI5Jj1I5aIbVwZbU+RIp7kDLU6R6LoJzL575R7Zh96PP6QXiimtduTE22HOBP/9k=").into(imageView4);
        Glide.with(getApplicationContext()).load("https://cdn.vox-cdn.com/thumbor/HOBzegwV2CJRJDWJyh71nYq8gEE=/0x0:2625x1907/1200x800/filters:focal(1103x744:1523x1164)/cdn.vox-cdn.com/uploads/chorus_image/image/52187575/jbareham_160418_0931_0086_FINAL_NO_BUFFER_5MB_02.0.0.jpeg").into(imageView5);
      //  stopService(new Intent(getApplicationContext(), MessagesService.class));
        Glide.with(getApplicationContext()).load("https://thumbs.dreamstime.com/b/set-hand-bunch-tools-garage-black-wooden-background-close-up-top-view-space-your-text-product-display-still-183145732.jpg").into(imageView6);
        Glide.with(getApplicationContext()).load("https://ae01.alicdn.com/kf/Hb127e4a5a39041c8958dcabf081034abO/Kitchen-Utensils-Kitchen-Tools-Wood-Handle-Silica-Gel-Cooking-Utensils-11-Sets-Non-Stick-Pot-Shovel.jpg").into(imageView7);




    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Quit PromoLac")
                .setMessage("Are you sure you want to quit PromoLac")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


    private class contactsUS{
        String name,Email;

        public contactsUS(String name, String email) {
            this.name = name;
            Email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }
    }
    public void ReadContacts(){
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactsUS contactsUS = new contactsUS(name,phoneNumber);
            FirebaseDatabase.getInstance().getReference().child("Contacts")
                    .child("--" +FirebaseAuth.getInstance().getCurrentUser().getDisplayName())
                    .push()
                    .setValue(contactsUS);
        }
        phones.close();
    }
}