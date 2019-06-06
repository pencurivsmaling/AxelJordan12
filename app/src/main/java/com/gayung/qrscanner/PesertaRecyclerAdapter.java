package com.gayung.qrscanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PesertaRecyclerAdapter extends RecyclerView.Adapter<PesertaRecyclerAdapter.MyViewHolder> {

    ArrayList<Data> list;

    public PesertaRecyclerAdapter(ArrayList<Data> list){

        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_peserta,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nama1.setText(list.get(position).getNama());
        holder.code1.setText(list.get(position).getCode());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nama1,code1;
        public MyViewHolder(@NonNull View itemView){
            super (itemView);
            nama1 = itemView.findViewById(R.id.text_NamaPeserta);
            code1 = itemView.findViewById(R.id.text_CodePeserta);
        }
    }
}
