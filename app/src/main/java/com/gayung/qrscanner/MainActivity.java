package com.gayung.qrscanner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.FirebaseApp;

public class MainActivity extends AppCompatActivity {
    Button btn_Detect;
    Button btn_Manual;
    Button btn_Peserta;
    public static final int PERMISSION_REQUEST=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        //Biar Layar Selalu Tegak
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Masukin fungsi ke variable
        btn_Detect = findViewById(R.id.btn_Detect);
        btn_Manual = findViewById(R.id.btn_Manual);
        btn_Peserta = findViewById(R.id.btn_Peserta);

        //Request Acces ke Camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        //OnClickListener, ketika tombol dipencet
        btn_Detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
                startActivity(intent);

            }
        });

        btn_Manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                startActivity(intent);
            }
        });

        btn_Peserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PesertaActivity.class);
                startActivity(intent);
            }
        });
    }

}
