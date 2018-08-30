package com.example.asus.skripsi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.skripsi.model.Shared_p;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity  {
    private CardView ilogin;
    Shared_p saver;
    TextView kanan;
    TextView kiri;
    TextView username;
    TextView password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isOnline()){
        }else{
            showInternetDisabledAlertToUser();
        }
        saver = new Shared_p("Session2", this);

        setContentView(R.layout.activity_login);
        ilogin   = (CardView) findViewById(R.id.login);
        kanan = findViewById(R.id.textView2);
        kiri = findViewById(R.id.textView3);
        username = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);

        if(saver.getStatus().equals("login")){
            Intent i = new Intent(Login.this,Pilihbus.class);
            startActivity(i);
            finish();
        }else if (saver.getStatus().equals("done")){
            Intent i = new Intent(Login.this,MapsActivity.class);
            startActivity(i);
            finish();
        }


        ilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog dialog = ProgressDialog.show(view.getContext(), "", "Loading. Please wait...", true);
                RequestQueue queue = Volley.newRequestQueue(Login.this);

                String url = "http://bukajurnal.com/login";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                String[] split = response.split("---");
                                String status = split[0];
                                String token = split[1];
                                String id_drv = split[2];
                                String nama_driver = split[3];
                                if(status.equals("false")){
                                    dialog.dismiss();
                                    password.setText("");
                                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Login gagal!")
                                            .setContentText("Cek kembali username dan password anda")
                                            .setConfirmText("Oke")
                                            .show();
                                }else{
                                    saver.setToken(token);
                                    saver.setStatus("login");
                                    saver.setId_driver(id_drv);
                                    saver.setNama_driver(nama_driver);

                                    Intent i = new Intent(Login.this,Pilihbus.class);
                                    startActivity(i);
                                    finish();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        kanan.setText("Volley error");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", username.getText().toString());
                        params.put("password", password.getText().toString());
                        return params;
                    }
                };
                queue.add(stringRequest);

            }
        });

    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    private void showInternetDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Internet Tidak aktif")
                .setCancelable(false)
                .setPositiveButton("Pergi ke Pengaturan",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        Settings.ACTION_DATA_ROAMING_SETTINGS);
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
