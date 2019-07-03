package com.gayung.qrscanner;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManualActivity extends AppCompatActivity {

    TextView text_Nama;
    EditText text_Code;
    Button btn_Refresh,btn_Submit;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        FirebaseApp.initializeApp(this);

        //Assign Nama
        text_Nama   = findViewById(R.id.text_Nama);
        text_Code   = findViewById(R.id.text_Code);
        btn_Refresh  = findViewById(R.id.btn_Refresh1);
        btn_Submit = findViewById(R.id.btn_Submit1);

        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        btn_Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String code = text_Code.getText().toString().trim().toUpperCase();

                databaseReference.child(code).child("hadir").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean kehadiran = dataSnapshot.getValue(Boolean.class);
                        if (kehadiran==true){
                            peringatan();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                if (TextUtils.isEmpty(code)){
                    Toast.makeText(ManualActivity.this,"Harap Masukan Kode", Toast.LENGTH_LONG).show();
                }


            databaseReference.child(code).child("nama").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String nama = dataSnapshot.getValue(String.class);
                text_Nama.setText(nama);

                btn_Submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(nama)){
                            databaseReference.child(code).child("hadir").setValue(true);
                            setResult(001);
                            finish();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("onCancelled", databaseError.toException());
            }
        });

            }
        });
    }

    private void peringatan(){

        AlertDialog.Builder peringatan = new AlertDialog.Builder(this);

        peringatan.setTitle("PERINGATAN");

        peringatan
                .setMessage("PESERTA SUDAH STATUS HADIR")
                .setCancelable(false)
                .setNeutralButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                    }
                });

        AlertDialog dialog = peringatan.create();
        if(!isFinishing()) {
            dialog.show();
        }
    }
}
