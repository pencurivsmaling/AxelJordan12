package com.gayung.qrscanner;

import android.widget.TextView;

public class Data {
    String nama;
    String code;

    public Data(){}

    public Data(String nama, String code) {
        this.nama = nama;
        this.code = code;
    }

    public String getNama() {
        return nama;
    }

    public String getCode() {
        return code;
    }
}
