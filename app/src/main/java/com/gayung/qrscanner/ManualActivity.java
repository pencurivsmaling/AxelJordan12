package com.gayung.qrscanner;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManualActivity extends AppCompatActivity {

    EditText text_Nama;
    EditText text_Code;
    CheckBox checkBox;
    Button btn_Submit;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        FirebaseApp.initializeApp(this);

        //Assign Nama
        text_Nama   = findViewById(R.id.text_Nama);
        text_Code   = findViewById(R.id.text_Code);
        checkBox    = findViewById(R.id.btn_Check);
        btn_Submit  = findViewById(R.id.btn_Submit1);

        databaseReference = FirebaseDatabase.getInstance().getReference("data");
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });

    }

    private void addData(){

        String nama = text_Nama.getText().toString().trim().toUpperCase();
        String code = text_Code.getText().toString().trim().toUpperCase();
        Boolean hadir = true;


        if (!TextUtils.isEmpty(nama) && checkBox.isChecked() && !TextUtils.isEmpty(code)){

            Data data = new Data(nama,code,hadir);
            databaseReference.child(code).setValue(data);

            Toast.makeText(this,"Berhasil Input", Toast.LENGTH_LONG).show();

            finish();
        }else {
            Toast.makeText(this,"Input Invalid", Toast.LENGTH_LONG).show();
        }


    }
}
