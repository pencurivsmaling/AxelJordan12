package com.gayung.qrscanner;

import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PesertaActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Data> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta);

        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        searchView = findViewById(R.id.search_Attack);

        recyclerView = findViewById(R.id.recyclerViewPeserta);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        if (databaseReference!=null){

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.exists()){
                   list = new ArrayList<>();
                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                       list.add(dataSnapshot1.getValue(Data.class));
                   }
                   PesertaRecyclerAdapter pesertaAdapter = new PesertaRecyclerAdapter(list);
                   recyclerView.setAdapter(pesertaAdapter);

               }

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

               Toast.makeText(PesertaActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
           }
       });
        }

        if (searchView!=null){

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    firebaseSearch(newText);
                    return true;
                }
            });
        }
    }

    private void firebaseSearch(String input) {
        ArrayList<Data> mylist = new ArrayList<>();
        for(Data object : list){

            if (object.getNama().toLowerCase().contains(input.toLowerCase())){
                mylist.add(object);
            }
            PesertaRecyclerAdapter pesertaAdapter = new PesertaRecyclerAdapter(mylist);
            recyclerView.setAdapter(pesertaAdapter);
        }
    }
}
