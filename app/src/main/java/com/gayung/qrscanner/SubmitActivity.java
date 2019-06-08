package com.gayung.qrscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubmitActivity extends AppCompatActivity {

    Button btn_Scan;
    Button btn_Submit;
    EditText text_Nama;
    CheckBox checkBox;
    TextView result;
    public static final int REQUEST_CODE=100;
    public static final int PERMISSION_REQUEST=200;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        //Initialize Variable
        FirebaseApp.initializeApp(this);
        btn_Scan = findViewById(R.id.btn_Scan1);
        btn_Submit = findViewById(R.id.btn_Submit1);
        text_Nama = findViewById(R.id.text_nama1);
        result= findViewById(R.id.qr_code);
        checkBox = findViewById(R.id.checkBox);

        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        //Onclicklistener -> Jalan kalau tombol di klik
        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubmitActivity.this,ScanActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    //Setelah hasil Barcode terbaca
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            if(data != null){
                final Barcode barcode = data.getParcelableExtra("barcode");
                result.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(barcode.displayValue);
                    }
                });
            }
        }
    }

    //Memasukan data ke Firebase
    private void addData() {

        String nama = text_Nama.getText().toString().trim().toUpperCase();
        String code = result.getText().toString().toUpperCase();
        boolean hadir = true;

        if (!TextUtils.isEmpty(nama)&& checkBox.isChecked() && !TextUtils.isEmpty(code)){
            // String id = databaseReference.push().getKey();
            Data data3 = new Data(nama,code,hadir);
            databaseReference.child(code).setValue(data3);
            Toast.makeText(this,"Berhasil Input", Toast.LENGTH_LONG).show();
            finish();
        }else if (!checkBox.isChecked()){
            Toast.makeText(this,"Konfirmasi Checkbox", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(nama)){
            Toast.makeText(this,"Nama Kosong", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(nama) && !checkBox.isChecked()){
            Toast.makeText(this,"Data Kosong", Toast.LENGTH_LONG).show();
        }
    }



}
