package com.gayung.qrscanner;

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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button btn_delete,btn_check;
    EditText text_Code;
    TextView text_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        FirebaseApp.initializeApp(this);
        text_user= findViewById(R.id.text_NamaUser);
        text_Code= findViewById(R.id.text_deletePeserta);
        btn_check = findViewById(R.id.btn_Refresh2);
        btn_delete= findViewById(R.id.btn_submitDelete);
        //checkBox = findViewById(R.id.checkBoxDelete);

        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("data");


        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = text_Code.getText().toString().trim().toUpperCase();

                if (TextUtils.isEmpty(code)){
                    Toast.makeText(DeleteActivity.this,"Harap Masukan Kode", Toast.LENGTH_LONG).show();
                }

                databaseReference.child(code).child("nama").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String nama = dataSnapshot.getValue(String.class);
                        text_user.setText(nama);

                        btn_delete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String code = text_Code.getText().toString().toUpperCase().trim();

                                if ( !TextUtils.isEmpty(code)&& !TextUtils.isEmpty(nama)){
                                    databaseReference.child(code).child("hadir").setValue(false);
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
