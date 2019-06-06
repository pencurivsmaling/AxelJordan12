package com.gayung.qrscanner;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PesertaActivity extends AppCompatActivity {

    ListView listViewPeserta;
    DatabaseReference databaseReference;
    List<Data> pesertaList;
    EditText text_Search;
    Button btn_Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta);

        FirebaseApp.initializeApp(this);

        btn_Search = findViewById(R.id.btn_Search);
        text_Search= findViewById(R.id.text_Search);
        listViewPeserta= findViewById(R.id.listViewPeserta);

        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        pesertaList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                pesertaList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren() ){
                    Data data = dataSnapshot1.getValue(Data.class);
                    pesertaList.add(data);
                }

                PesertaList adapter = new PesertaList(PesertaActivity.this,pesertaList);
                listViewPeserta.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
