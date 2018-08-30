package com.example.asus.skripsi.model;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaCas;

import com.example.asus.skripsi.R;

/**
 * Created by root on 24/03/18.
 */

public class Shared_p {
    private SharedPreferences Session;
    private SharedPreferences.Editor sEditor;
    private String status;
    private String id_driver;
    private String token;
    private String id_bus;
    private String nama_driver;
    private String nomor_kendaraan;
    private String nomor_kontak;
    private boolean switchs;
    private int path_image;

    public Shared_p(String shared_name, Activity activity) {
        setShared_prev(activity.getSharedPreferences(shared_name, activity.MODE_PRIVATE));
        setsEditor(this.Session.edit());
        setStatus(Session.getString("status", "none"));
        setToken(Session.getString("token","none"));
        setId_bus(Session.getString("id_bus","none"));
        setNama_driver(Session.getString("nama_driver", "null"));
        setNomor_kendaraan(Session.getString("nokenbus", "null"));
        setNomor_kontak(Session.getString("nokontak","null"));
        setPath_image(Session.getInt("path_image", R.drawable.bus));
        setId_driver(Session.getString("id_driver","null"));
        setSwitchs(Session.getBoolean("switch",false));
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        this.sEditor.putString("status", status);
        this.sEditor.apply();
    }

    public String getId_bus() {
        return id_bus;
    }

    public void setId_bus(String id_bus) {
        this.id_bus = id_bus;
        this.sEditor.putString("id_bus", id_bus);
        this.sEditor.apply();
    }

    public String getNama_driver() {
        return nama_driver;
    }

    public void setNama_driver(String nama_driver) {
        this.nama_driver = nama_driver;
        this.sEditor.putString("nama_driver", nama_driver );
        this.sEditor.apply();
    }

    public String getNomor_kendaraan() {
        return nomor_kendaraan;
    }

    public void setNomor_kendaraan(String nomor_kendaraan) {
        this.nomor_kendaraan = nomor_kendaraan;
        this.sEditor.putString("nokenbus",nomor_kendaraan);
        this.sEditor.apply();
    }

    public String getNomor_kontak() {
        return nomor_kontak;
    }

    public void setNomor_kontak(String nomor_kontak) {
        this.nomor_kontak = nomor_kontak;
        this.sEditor.putString("nokontak", nomor_kontak);
        this.sEditor.apply();
    }

    public int getPath_image() {
        return path_image;
    }

    public void setPath_image(int path_image) {
        this.path_image = path_image;
        this.sEditor.putInt("path_image",path_image);
        this.sEditor.apply();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        this.sEditor.putString("token", token);
        this.sEditor.apply();
    }


    public void setShared_prev(SharedPreferences shared_prev) {
        this.Session = shared_prev;
    }

    public void setsEditor(SharedPreferences.Editor sEditor) {
        this.sEditor = sEditor;
    }

    public String getId_driver() {
        return id_driver;
    }

    public void setId_driver(String id_driver) {
        this.id_driver = id_driver;
        this.sEditor.putString("id_driver", id_driver);
        this.sEditor.apply();
    }

    public boolean isSwitchs() {
        return switchs;
    }

    public void setSwitchs(boolean switchs) {
        this.switchs = switchs;
        this.sEditor.putBoolean("switch", switchs);
        this.sEditor.apply();
    }
    public void clear(){
        this.Session.edit().remove("token").commit();
        this.Session.edit().remove("status").commit();
        this.Session.edit().remove("switch").commit();
        this.Session.edit().remove("nokontak").commit();
        this.Session.edit().remove("nokenbus").commit();
        this.Session.edit().remove("nama_driver").commit();
    }
}
