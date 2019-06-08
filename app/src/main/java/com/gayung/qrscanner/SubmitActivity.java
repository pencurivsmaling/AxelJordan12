package com.gayung.qrscanner;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SubmitActivity extends AppCompatActivity {

    Button btn_Scan;
    Button btn_Submit;
    TextView text_Nama;
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

                        //TEMPEL SINI AJA!!

                        final String code = result.getText().toString().toUpperCase();
                        final Boolean hadir1 = true;

                        databaseReference.child(code).child("nama").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String code = result.getText().toString().toUpperCase();

                                final String nama = dataSnapshot.getValue(String.class);
                                text_Nama.setText(nama);

                                btn_Submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        if (!TextUtils.isEmpty(code)&&!TextUtils.isEmpty(nama)){
                                            databaseReference.child(code).child("hadir").setValue(hadir1);
                                            setResult(001);
                                            finish();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        }
    }
}
