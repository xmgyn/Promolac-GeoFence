package com.example.PromoLac;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.PromoLac.NotificationLogs.NotificationPermission;
import com.example.promolac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class Main3Activity extends AppCompatActivity {
    ImageView imageView,backhome,done;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    Uri imageUri;
    LinearLayout logout,home,location,notificationsetting;
    TextView Name;
    String NameString;
    LocationManager locationManager;
    LocationListener locationListener;



    @Override
    protected void onPostResume() {
        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
            Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imageView);
        super.onPostResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);



        logout = findViewById(R.id.logout);
        notificationsetting = findViewById(R.id.notificationsetting);
        home = findViewById(R.id.home_linear_layout);
        backhome = findViewById(R.id.backhome);
        location = findViewById(R.id.location);

        TextView text;
        text = (TextView)findViewById(R.id.name);
        final Intent intent = getIntent();
        String str = intent.getStringExtra("value");
        text.setText(str);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);


        imageView=findViewById(R.id.profile_image);
//            Glide.with(getApplicationContext()).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);

//        String link= "https://firebasestorage.googleapis.com/v0/b/xdadeveloperes.appspot.com/o/Users%2FimageszTj0xC3kiihvoG4Y3Y9UMXqrQRX2?alt=media&token=e96ddcbc-5fba-4df5-8ebf-f7840ea6a722";
//        Glide.with(this).load(link).into(imageView);
        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null)
            Glide.with(this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(imageView);

        location.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Intent intent1 = new Intent(getApplicationContext(),MapsActivity.class);
                intent1.putExtra("decider","1");
                startActivity(intent1);
            }
        });

        notificationsetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationPermission.class));
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery,"Select Picture"),1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Name = (TextView) findViewById(R.id.profileidName);
        Name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        done = (ImageView) findViewById(R.id.profileidNamePic);
        done.setVisibility(View.INVISIBLE);
        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setCursorVisible(true);
                Name.setFocusableInTouchMode(true);
                Name.setInputType(InputType.TYPE_CLASS_TEXT);
                Name.requestFocus();
                done.setVisibility(View.VISIBLE);



            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setCursorVisible(false);
                Name.setFocusableInTouchMode(false);
                done.setVisibility(View.INVISIBLE);
                NameString = Name.getText().toString();
                UpdateProfileName(NameString);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 &&   resultCode==RESULT_OK){
            imageUri =data.getData();
            try {

                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                imageView.setImageBitmap(bitmap);
                uploadFile(bitmap);
            }catch (Exception e){

                Toast.makeText(this, "Exception"+e.toString(), Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void uploadFile(Bitmap bitmap) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference("Users/");
        StorageReference mountainImagesRef = storageRef.child("images"+firebaseUser.getUid());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = mountainImagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(Main3Activity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Task<Uri> task1= taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        task.getResult();
                        Toast.makeText(Main3Activity.this, "Get link", Toast.LENGTH_SHORT).show();
                        Log.d("downloadUri", "onComplete: " +task.getResult().toString());
                        UpdateProfile(task.getResult());
                    }
                });

                Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                //sendMsg("" + downloadUrl, 2);
//                Log.d("downloadUrl-->", "" + downloadUrl);

//                Toast.makeText(Main3Activity.this, "Uplodaed", Toast.LENGTH_SHORT).show();
//                UpdateProfile(downloadUrl);
            }
        });
    }

    private void UpdateProfile(Uri downloadUrl) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(downloadUrl)
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Pakistan", "User profile updated.");
                        }
                    }
                });
    }
    private void UpdateProfileName(String nameString) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameString)
                .build();

        firebaseUser.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Pakistan", "User profile updated.");
                        }
                    }
                });
    }
}
