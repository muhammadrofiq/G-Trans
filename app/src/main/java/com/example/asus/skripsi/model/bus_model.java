package com.example.asus.skripsi.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by root on 12/03/18.
 */

public class bus_model {
    private String id_bus;
    private String nomor_kendaraan;
    private String nomor_kontak;
    private LatLng latlang;
    private String longitude;
    private String latitude;
    private String plat_nomor_kendaraan;

    public bus_model(){

    }


    public String getId_bus() {
        return id_bus;
    }

    public void setId_bus(String id_bus) {
        this.id_bus = id_bus;
    }

    public String getNomor_kendaraan() {
        return nomor_kendaraan;
    }

    public void setNomor_kendaraan(String nomor_kendaraan) {
        this.nomor_kendaraan = nomor_kendaraan;
    }

    public String getNomor_kontak() {
        return nomor_kontak;
    }

    public void setNomor_kontak(String nomor_kontak) {
        this.nomor_kontak = nomor_kontak;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPlat_nomor_kendaraan() {
        return plat_nomor_kendaraan;
    }

    public void setPlat_nomor_kendaraan(String plat_nomor_kendaraan) {
        this.plat_nomor_kendaraan = plat_nomor_kendaraan;
    }

    public LatLng getLatlang() {
        return latlang;
    }

    public void setLatlang(LatLng latlang) {
        this.latlang = latlang;
    }
}

