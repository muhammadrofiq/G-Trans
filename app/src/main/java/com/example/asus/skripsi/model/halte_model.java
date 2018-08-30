package com.example.asus.skripsi.model;

import com.google.android.gms.maps.model.LatLng;

public class halte_model {
    private LatLng Lokasi;
    private int Idhalte;
    private int Counter;

    public LatLng getLokasi() {
        return Lokasi;
    }

    public void setLokasi(LatLng lokasi) {
        Lokasi = lokasi;
    }

    public int getIdhalte() {
        return Idhalte;
    }

    public void setIdhalte(int idhalte) {
        Idhalte = idhalte;
    }

    public int getCounter() {
        return Counter;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }
}
