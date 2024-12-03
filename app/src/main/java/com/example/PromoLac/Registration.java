package com.example.PromoLac;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promolac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class Registration extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button register;
    EditText Name,Email,Password;
    String mail,pass,name;
    ProgressBar progressBar;
    ImageView back_Registration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        register=findViewById(R.id.registeridr);
        Name=findViewById(R.id.NameId);
        Email = findViewById(R.id.EmailIdr);
        Password = findViewById(R.id.PasswordIdr);
        back_Registration=findViewById(R.id.back_Registration);
        progressBar = findViewById(R.id.progressBar_registration);
        progressBar.setVisibility(View.INVISIBLE);

        name=null;
        pass=null;
        mail=null;
        back_Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                mail=Email.getText().toString();
                pass=Password.getText().toString();
                name=Name.getText().toString();
                firebaseAuth=FirebaseAuth.getInstance();
                if(mail.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Registration.this, "Enter Valid Mail and Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    register.setVisibility(View.VISIBLE);
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Registration.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Registration.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);
                        }
                    });
//                     if(name != null)
//                         UpdateProfileName(name);
                }
            }
        });


    }
    private void UpdateProfileName(String nameString) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(nameString)
                .build();

        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Pakistan", "User profile updated.");
                        }
                    }
                });
       // FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification();
    }
}
