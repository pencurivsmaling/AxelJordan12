package com.gayung.qrscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button btn_delete;
    CheckBox checkBox;
    EditText text_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        FirebaseApp.initializeApp(this);
        text_Code= findViewById(R.id.text_deletePeserta);
        btn_delete= findViewById(R.id.btn_submitDelete);
        checkBox = findViewById(R.id.checkBoxDelete);

        //Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("data");


        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = text_Code.getText().toString().toUpperCase().trim();

                if ( checkBox.isChecked() && !TextUtils.isEmpty(code)){
                    // String id = databaseReference.push().getKey();
                    databaseReference.child(code).removeValue();
                    Toast.makeText(DeleteActivity.this,"Berhasil Delete", Toast.LENGTH_LONG).show();
                    finish();
                }else if (!checkBox.isChecked()){
                    Toast.makeText(DeleteActivity.this,"Konfirmasi Checkbox", Toast.LENGTH_LONG).show();
                }else if (TextUtils.isEmpty(code)){
                    Toast.makeText(DeleteActivity.this,"Data Kosong", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
