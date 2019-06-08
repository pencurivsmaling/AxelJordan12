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
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.FirebaseApp;

import static com.gayung.qrscanner.SubmitActivity.REQUEST_CODE;

public class MainActivity extends AppCompatActivity {
    Button btn_Detect;
    Button btn_Manual;
    Button btn_Peserta;
    Button btn_Delete;
    public static final int REQUEST_CODE=100;
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
        btn_Delete = findViewById(R.id.btn_Delete);

        //Request Acces ke Camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST);
        }

        //OnClickListener, ketika tombol dipencet
        btn_Detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        btn_Manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ManualActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        btn_Peserta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PesertaActivity.class);
                startActivity(intent);
            }
        });

        btn_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 001 || resultCode == 001){
            Toast.makeText(this,"DATA INPUT BERHASIL", Toast.LENGTH_LONG).show();
        }
    }
}
