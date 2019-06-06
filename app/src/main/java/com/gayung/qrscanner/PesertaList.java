package com.gayung.qrscanner;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PesertaList extends ArrayAdapter<Data> {

    private Activity context;
    private List<Data> dataList;

    public PesertaList(Activity context, List<Data> dataList){
        super(context, R.layout.list_peserta, dataList);
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_peserta,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.text_NamaPeserta);
        TextView textViewCode = listViewItem.findViewById(R.id.text_CodePeserta);

        Data data = dataList.get(position);

        textViewName.setText(data.getNama());
        textViewCode.setText(data.getCode());

        return listViewItem;
    }
}
