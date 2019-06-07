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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PesertaActivity extends AppCompatActivity {

    //Variable
    DatabaseReference databaseReference;
    ArrayList<Data> list;
    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta);

        //Database Initialize
        FirebaseApp.initializeApp(this);
        databaseReference = FirebaseDatabase.getInstance().getReference("data");

        //SearchView Initialize
        searchView = findViewById(R.id.search_Attack);

        //RecyclerView Initialize
        recyclerView = findViewById(R.id.recyclerViewPeserta);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        //Pengambilan data dari Firebase
        if (databaseReference!=null){
        databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               //Data ditemukan, Copy dr database ke adapter
               if (dataSnapshot.exists()){
                   list = new ArrayList<>();
                   for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                       list.add(dataSnapshot1.getValue(Data.class));
                   }

                   //Sort Data byName
                   Collections.sort(list, new Comparator<Data>() {
                       @Override
                       public int compare(Data o1, Data o2) {
                           return o1.getNama().compareToIgnoreCase(o2.getNama());
                       }
                   });

                   //Taro data ke RecyclerView
                   PesertaRecyclerAdapter pesertaAdapter = new PesertaRecyclerAdapter(list);
                   recyclerView.setAdapter(pesertaAdapter);

               }

           }

           //Kalo ketemu Error
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(PesertaActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
           }
       });
        }

        //Searchview ada tulisan
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

    //Fungsi Search
    private void firebaseSearch(String input) {
        ArrayList<Data> mylist = new ArrayList<>();
        for(Data object : list){

            if (object.getNama().toLowerCase().contains(input.toLowerCase())){
                mylist.add(object);
            }else if(object.getCode().toLowerCase().contains(input.toLowerCase())){
                mylist.add(object);
            }

            PesertaRecyclerAdapter pesertaAdapter = new PesertaRecyclerAdapter(mylist);
            recyclerView.setAdapter(pesertaAdapter);
        }
    }
}
