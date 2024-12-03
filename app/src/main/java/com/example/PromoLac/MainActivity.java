package com.example.PromoLac;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.promolac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Button login;
    EditText editText1, editText2;
    TextView forgetpass, register;
    String Email, Password;
    RelativeLayout backhome;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        login = findViewById(R.id.login);
        register = findViewById(R.id.registerid);
        editText1 = (EditText) findViewById(R.id.LoginID);
        editText2 = (EditText) findViewById(R.id.PasswordID);
        forgetpass = (TextView) findViewById(R.id.ForgetID);
        progressBar = findViewById(R.id.progressBar_Login);
        progressBar.setVisibility(View.INVISIBLE);

        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this);
        forgetpass.setOnClickListener(this);
        register.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v == login) {
            Email = editText1.getText().toString();
            Password = editText2.getText().toString();
            if (Email.isEmpty() || Password.isEmpty())
                Toast.makeText(MainActivity.this, "Enter Required Field", Toast.LENGTH_LONG).show();
            else {
                progressBar.setVisibility(View.VISIBLE);
                v.setVisibility(View.GONE);
                validate(Email, Password);
            }
        } else if (v == forgetpass) {

            startActivity(new Intent(this, ForgetPass.class));

        } else if (v == register) {
            startActivity(new Intent(getApplicationContext(), Registration.class));
        }

    }


    private void validate(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                login.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Incorrect email or password ", Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Quit PromoLac")
                .setMessage("Are you sure you want to quit PromoLac")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
