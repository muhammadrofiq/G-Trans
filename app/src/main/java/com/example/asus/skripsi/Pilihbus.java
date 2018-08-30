package com.example.asus.skripsi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.skripsi.model.Shared_p;
import com.example.asus.skripsi.model.bus_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pilihbus extends AppCompatActivity {
    private  static final  String url = "http://bukajurnal.com/bus";
    private List<bus_model> listbus = new ArrayList<bus_model>();
    private ProgressDialog loading;
    Shared_p saver ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Activity aku = this;
        super.onCreate(savedInstanceState);
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
        loading = ProgressDialog.show(Pilihbus.this, "", "Loading. Please wait...", true);

        //start - sab - 24 - mar - 2018
        saver = new Shared_p("Session2", this);
        //Toast.makeText(this, "stat from saver "+saver.getStatus()+saver.getNama_driver()+saver.getToken(), Toast.LENGTH_LONG).show();
        Log.d("login", saver.getStatus()+saver.getNama_driver()+saver.getToken() );
        //end

        RequestQueue queue = Volley.newRequestQueue(Pilihbus.this);
        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        bus_model bus = new bus_model();
                        bus.setId_bus(obj.getString("id_bus"));
                        bus.setNomor_kendaraan(obj.getString("platnomor_bus"));
                        bus.setNomor_kontak(obj.getString("kontak_bus"));
                        listbus.add(bus);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loading.dismiss();
                setContentView(R.layout.activity_pilihbus);

                Fragment_bus fragment = new Fragment_bus(aku,listbus);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.canvasbus, fragment);
                fragmentTransaction.commit();

                //SharedPreferences Sesion = getSharedPreferences("Session",MODE_PRIVATE);
                //String token = Sesion.getString("status", "ga ada haha");
                //Toast.makeText(Pilihbus.this, "masuk gebo", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );
        queue.add(jsonreq);
    }
    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS perangkat anda tidak aktif")
                .setCancelable(false)
                .setPositiveButton("Pergi ke Pengaturan",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

}
