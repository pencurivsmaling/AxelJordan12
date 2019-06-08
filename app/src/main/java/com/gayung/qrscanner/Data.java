package com.gayung.qrscanner;

import android.widget.TextView;

public class Data {
    String nama;
    String code;
    boolean hadir;

    public Data(){}

    public Data(String nama, String code,boolean hadir) {
        this.nama = nama;
        this.code = code;
        this.hadir = hadir;
    }

    public String getNama() {
        return nama;
    }

    public String getCode() {
        return code;
    }

    public boolean getHadir() { return hadir; }
}
