package com.example.asus.skripsi;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asus.skripsi.model.Pengemudi_model;
import com.example.asus.skripsi.model.Shared_p;
import com.example.asus.skripsi.model.bus_model;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.google.maps.android.ui.IconGenerator;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {

    private Shared_p saver;
    Dialog exitpopup;
    public Marker ourGlobalMarker;
    public Marker bus1;
    public Marker bus2;
    public Marker bus3;
    public Marker bus4;
    public Marker bus5;

    public LatLng mylocation;

    public Marker mgww;
    public Marker mfaperta;
    public Marker mfmipa;
    public Marker mastri;
    public Marker mtanoto;
    public Marker mmenwa;
    public Marker mfahutan;
    public Marker masramainternasional;
    public Marker malhur;
    public Marker mgorlam;
    public Marker mfpik;
    public Marker mfkh;
    public Marker mteknofast;
    public Marker mlsi;
    public Marker mfem;
    public JSONObject paket_id;

    TextView input_no_hp;
    private bus_model bus;
    private Pengemudi_model pengemudi;
    private GoogleMap mMap;
    private View mapView;
    private LocationManager locationManager;
    public String bestProvider;
    com.rey.material.widget.Switch status_switch;
    TextView text_status;
    com.github.nkzawa.socketio.client.Socket socket;

    public BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId, @DrawableRes int icon) {
        Drawable background = ContextCompat.getDrawable(context, icon);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mylocation = new LatLng(-6.556072,106.726984);
        final Context mcon = this;
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
        // start - 24 -mar - 2018
        saver = new Shared_p("Session2", this);
        Log.d("done", saver.getStatus()+" "+saver.getNama_driver()+" "+saver.getId_bus()+" "+saver.getNomor_kendaraan()+" "+saver.getNomor_kontak()+" "+saver.getToken()+" "+saver.isSwitchs());
        // end

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        //bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
        Location location = locationManager.getLastKnownLocation(bestProvider);
        locationManager.requestLocationUpdates(bestProvider,3000,0, (LocationListener) this);


        try{
            //socket = IO.socket("http://192.168.31.74:3000");
            socket = IO.socket("http://bukajurnal.com");
        }catch (URISyntaxException e){
            Log.d("error", "Oncreate:"+e.toString());
        }

        TextView no_kontak = findViewById(R.id.text_nomor_kontak);
        TextView noken = (TextView) findViewById(R.id.text_noken);
        TextView nama_drv = findViewById(R.id.nama_driver);
        ImageView proficonbus = (ImageView) findViewById(R.id.prof_bus_icon);
        pengemudi =new Pengemudi_model();
        pengemudi.setNamapengemudi(saver.getNama_driver());
        pengemudi.setToken(saver.getToken());
        pengemudi.setState(saver.getStatus());
        bus = new bus_model();
        bus.setPlat_nomor_kendaraan(saver.getNomor_kendaraan().toString());
        no_kontak.setText(saver.getNomor_kontak());
        nama_drv.setText(pengemudi.getNamapengemudi());
        noken.setText(bus.getPlat_nomor_kendaraan());
        proficonbus.setImageResource(saver.getPath_image());

        bus.setNomor_kontak(saver.getNomor_kontak());

        paket_id = new JSONObject();
        try {
            paket_id.put("id",saver.getId_bus());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.on("lokasi",handling );
        socket.on("rcv_pinloc", handling_halte );
        socket.on("obus", handling_out );
        socket.on("flag", handling_flag );
        socket.on("authfail", handling_fail );
        socket.on("checkhalte", handling_halte_awal );
        socket.connect();
        socket.emit("checktoken", pengemudi.getToken());
        socket.emit("checkhalte"," ");



        ImageView img_bus = findViewById(R.id.prof_bus_icon);
        img_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 18));
            }
        });
        text_status = (TextView) findViewById(R.id.text_statusdriver);
        status_switch = (com.rey.material.widget.Switch) findViewById(R.id.status_switch);
        status_switch.setChecked(saver.isSwitchs());
        status_switch.setOnCheckedChangeListener(new com.rey.material.widget.Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(com.rey.material.widget.Switch view, boolean checked) {
                if (checked){
                    new SweetAlertDialog(mcon, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Status aktif!")
                            .setContentText("Lokasi anda akan dikirimkan!")
                            .show();
                    text_status.setText("Aktif");
                    socket.emit("i-bus",saver.getId_bus());
                    saver.setSwitchs(true);
                }
                else {
                    new SweetAlertDialog(mcon, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Status tidak aktif!")
                            .setContentText("Saat tidak aktif, lokasi anda tidak akan muncul!")
                            .setConfirmText("Ya, saya mengerti")
                            .show();
                    text_status.setText("Tidak Aktif");
                    socket.emit("obus", paket_id);
                    saver.setSwitchs(false);
                }
            }
        });



        exitpopup = new Dialog(this);
        final CardView logout = (CardView) findViewById(R.id.logoutdriver);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final AlertDialog.Builder mbuilder = new AlertDialog.Builder(MapsActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.popup_exit,null);
                Button mKeluar = (Button) mView.findViewById(R.id.btn_keluar);
                TextView button_x = mView.findViewById(R.id.btn_x_exit);
                button_x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mbuilder.setView(mView);
                        AlertDialog dialog =mbuilder.create();
                        dialog.dismiss();
                    }
                });
                mKeluar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        logout(MapsActivity.this);
                    }
                });
                mbuilder.setView(mView);
                AlertDialog dialog =mbuilder.create();
                dialog.show();
            }
        });

        final AlertDialog[] gantinomor = new AlertDialog[1];
        ImageView settings_trig= findViewById(R.id.setting_popup_triger);
        settings_trig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder mbuilders = new AlertDialog.Builder(MapsActivity.this);
                final View mViewss = getLayoutInflater().inflate(R.layout.update_kontak,null);
                CardView submits = (CardView) mViewss.findViewById(R.id.sub_gantinohp);

                final TextView btn_x = mViewss.findViewById(R.id.btn_x_gantinomor);
                btn_x.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        gantinomor[0].dismiss();
                    }
                });
                submits.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        input_no_hp = mViewss.findViewById(R.id.input_kontak_bus);
                        final String no_hp = input_no_hp.getText().toString();
                        RequestQueue queue = Volley.newRequestQueue(MapsActivity.this);

                        String url = "http://bukajurnal.com/updatekont";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        if(response.equals("false")){

                                            Toast.makeText(view.getContext(), "gagal", Toast.LENGTH_SHORT).show();

                                        }else{
                                            TextView textkont = findViewById(R.id.text_nomor_kontak);
                                            textkont.setText(input_no_hp.getText().toString());
                                            if (input_no_hp.getText().toString().equals("")){
                                                textkont.setText("nomor belum ada");
                                                saver.setNomor_kontak("kontak Belum ada");
                                            }else {
                                                saver.setNomor_kontak(input_no_hp.getText().toString());
                                            }
                                            input_no_hp.setText("");
                                            Toast.makeText(view.getContext(), "berhasil", Toast.LENGTH_SHORT).show();
                                            gantinomor[0].dismiss();
                                            new SweetAlertDialog(mcon, SweetAlertDialog.SUCCESS_TYPE)
                                                    .setTitleText("Berhasil!")
                                                    .setContentText("Nomor kontak kendaraan telah berubah!")
                                                    .show();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(view.getContext(), "volley gagal", Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id_bus", saver.getId_bus());
                                params.put("no_hp", no_hp);
                                return params;
                            }
                        };
                        queue.add(stringRequest);



                    }
                });
                mbuilders.setView(mViewss);
                gantinomor[0] =mbuilders.create();
                gantinomor[0].show();
            }
        });

    }


    public void logout(final Activity activity){
        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = "http://bukajurnal.com/update_drv";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.equals("false")){

                            Toast.makeText(activity, "gagal update", Toast.LENGTH_SHORT).show();

                        }else{
                            saver.clear();
                            if(saver.isSwitchs()){
                                socket.emit("obus", paket_id);
                                socket.disconnect();
                            }else{
                                socket.disconnect();
                            }
                            MapsActivity.this.finish();
                            System.exit(0);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "volly gagal", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_bus", saver.getId_bus() );
                params.put("id_driver", saver.getId_driver() );
                params.put("status", "0" );
                return params;
            }
        };
        queue.add(stringRequest);
    }


    private Emitter.Listener handling = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject terimapaketlokasi = (JSONObject)args[0];
            String paket_id = new String();
            String paket_longitude = new String();
            String paket_latitude = new String();
            try {
                paket_id= terimapaketlokasi.getString("id");
                paket_longitude = terimapaketlokasi.getString("longitude");
                paket_latitude = terimapaketlokasi.getString("latitude");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String finalPaket_id = paket_id;
            final String finalPaket_longitude = paket_longitude;
            final String finalPaket_latitude = paket_latitude;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    movebus(finalPaket_id,finalPaket_longitude,finalPaket_latitude);
                }
            });
        }
    };

    private Emitter.Listener handling_out = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject terimapaketlokasi = (JSONObject)args[0];
            String paket_id = new String();
            try {
                paket_id= terimapaketlokasi.getString("id");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String finalPaket_id = paket_id;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    removebus(finalPaket_id);

                }
            });
        }
    };

    private Emitter.Listener handling_halte_awal = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONArray array = (JSONArray) args[0];
            String paket_id = new String();
            int ix=0;
            for (; ix < array.length();ix++){
                try {
                    final String count = ""+array.getInt(ix);
                    final String ix_string = ""+ix;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            update_halte(ix_string,count);

                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    private Emitter.Listener handling_fail = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            //Toast.makeText(MapsActivity.this, "HARAP LOGIN KEMBALI", Toast.LENGTH_LONG).show();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    logout(MapsActivity.this);

                }
            });
        }
    };

    private Emitter.Listener handling_flag = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MapsActivity.this, "masuk ajg flag", Toast.LENGTH_SHORT).show();

                }
            });
        }
    };

    private Emitter.Listener handling_halte = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            JSONObject terimapakethalte = (JSONObject)args[0];
            String id_halte = new String();
            String counter_halte = new String();
            try {
                id_halte= terimapakethalte.getString("id");
                counter_halte = terimapakethalte.getString("counter");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String f_id_halte = id_halte;
            final String f_counter = counter_halte;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    update_halte(f_id_halte,f_counter);
                }
            });
        }
    };

    public void movebus(String id, String longi, String lati){
        LatLng lokasibus = new LatLng(Double.parseDouble(lati),Double.parseDouble(longi));
        switch (id){
            case "1":
                if(bus1 == null) { // First time adding marker to map

                    bus1 = mMap.addMarker(new MarkerOptions().position(lokasibus)
                            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_bus)));
                    if(saver.getId_bus().equals("1")){
                        bus1.setTitle("saya");
                        bus1.showInfoWindow();
                    }else{
                        bus1.setTitle("Bus 1");
                    }
                    MarkerAnimation.animateMarkerToICS(bus1, lokasibus, new LatLngInterpolator.Spherical());
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }else {

                    bus1.setVisible(true);
                    MarkerAnimation.animateMarkerToICS(bus1, lokasibus, new LatLngInterpolator.Spherical());

                }
                break;
            case "2":
                if(bus2 == null) { // First time adding marker to map
                    bus2 = mMap.addMarker(new MarkerOptions().position(lokasibus)
                            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_bus)));
                    if(saver.getId_bus().equals("2")){
                        bus2.setTitle("saya");
                        bus2.showInfoWindow();
                    }else{
                        bus2.setTitle("Bus 2");
                    }
                    MarkerAnimation.animateMarkerToICS(bus2, lokasibus, new LatLngInterpolator.Spherical());
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }else {
                    bus2.setVisible(true);
                    MarkerAnimation.animateMarkerToICS(bus2, lokasibus, new LatLngInterpolator.Spherical());
                }break;
            case "3":
                if(bus3 == null) { // First time adding marker to map
                    bus3 = mMap.addMarker(new MarkerOptions().position(lokasibus)
                            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_bus)));
                    if(saver.getId_bus().equals("3")){
                        bus3.setTitle("saya");
                        bus3.showInfoWindow();
                    }else{
                        bus3.setTitle("Bus 3");
                    }
                    MarkerAnimation.animateMarkerToICS(bus3, lokasibus, new LatLngInterpolator.Spherical());
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }else {
                    bus3.setVisible(true);
                    MarkerAnimation.animateMarkerToICS(bus3, lokasibus, new LatLngInterpolator.Spherical());
                }break;
            case "4":
                if(bus4 == null) { // First time adding marker to map
                    bus4 = mMap.addMarker(new MarkerOptions().position(lokasibus)
                            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_bus)));
                    if(saver.getId_bus().equals("4")){
                        bus4.setTitle("saya");
                        bus4.showInfoWindow();
                    }else{
                        bus4.setTitle("Bus 4");
                    }
                    MarkerAnimation.animateMarkerToICS(bus4, lokasibus, new LatLngInterpolator.Spherical());
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }else {
                    bus4.setVisible(true);
                    MarkerAnimation.animateMarkerToICS(bus4, lokasibus, new LatLngInterpolator.Spherical());
                }break;
            case "5":
                if(bus5 == null) { // First time adding marker to map
                    bus5 = mMap.addMarker(new MarkerOptions().position(lokasibus)
                            .icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_bus)));
                    if(saver.getId_bus().equals("5")){
                        bus5.setTitle("saya");
                        bus5.showInfoWindow();
                    }else{
                        bus5.setTitle("Bus 5");
                    }
                    MarkerAnimation.animateMarkerToICS(bus5, lokasibus, new LatLngInterpolator.Spherical());
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                }else {
                    bus5.setVisible(true);
                    MarkerAnimation.animateMarkerToICS(bus5, lokasibus, new LatLngInterpolator.Spherical());
                }break;

        }
    }

    public void removebus(String id){

        switch (id){
            case "1":
                if(bus1 == null) { // First time adding marker to map

                }else {
                    bus1.setVisible(false);
                }break;
            case "2":
                if(bus2 == null) { // First time adding marker to map

                }else {

                    bus2.setVisible(false);
                }break;
            case "3":
                if(bus3 == null) { // First time adding marker to map

                }else {

                    bus3.setVisible(false);
                }break;

            case "4":
                if(bus4 == null) { // First time adding marker to map

                }else {

                    bus4.setVisible(false);
                }break;
            case "5":
                if(bus5 == null) { // First time adding marker to map

                }else {
                    bus5.setVisible(false);
                }break;

        }
    }

    public void update_halte(String id_halte, String counter){
        IconGenerator iconfu = new IconGenerator(this);
        iconfu.setStyle(IconGenerator.STYLE_ORANGE);
        switch (id_halte){
            case "0": mgww.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "1": mfaperta.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "2": mfmipa.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "3": mastri.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "4": mtanoto.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "5": mmenwa.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "6": mfahutan.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "7": masramainternasional.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "8": malhur.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "9": mgorlam.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "10": mfpik.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "11": mfkh.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "12": mteknofast.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "13": mlsi.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
            case "14": mfem.setIcon(BitmapDescriptorFactory.fromBitmap(iconfu.makeIcon(counter)));break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // fungsi icon


        // Add a marker in Sydney and move the camera
        LatLng camera = new LatLng(-6.556072,106.726984);
        LatLng hltfmipa = new LatLng(-6.557868, 106.731493);
        LatLng hltfprta = new LatLng(-6.559080,106.730794);
        LatLng hltastri = new LatLng(-6.555988,106.731571);
        LatLng hlttanoto = new LatLng(-6.555785,106.729938);
        LatLng hltmenwa = new LatLng(-6.555717,106.729453);
        LatLng hltfahutan = new LatLng(-6.555871,106.728027);
        LatLng hltasmintr = new LatLng(-6.556095,106.727295);
        LatLng hltalhur = new LatLng(-6.556473,106.725597);
        LatLng hltgorlam = new LatLng(-6.556300,106.724379);
        LatLng hltfpik = new LatLng(-6.556766,106.723743);
        LatLng hltfkh = new LatLng(-6.5570296 ,106.72167157 );
        LatLng hlttcnprk = new LatLng(-6.5574578 ,106.72680445 );
        LatLng hltlsi = new LatLng(-6.55843494, 106.72685789 );
        LatLng hltfema = new LatLng(-6.55975009,106.7281918 );
        LatLng hltgww = new LatLng(-6.56052054 ,106.73042162 );


        LatLng t1 = new LatLng(-6.557920,106.731532);
        LatLng t2 = new LatLng(-6.557548,106.731700);
        LatLng t3 = new LatLng(-6.556360,106.731715);
        LatLng t4 = new LatLng(-6.556027,106.731676);
        LatLng t5 = new LatLng(-6.555795,106.731476);
        LatLng t6 = new LatLng(-6.555704,106.731192);
        LatLng t7 = new LatLng(-6.555712,106.730591);
        LatLng t8 = new LatLng(-6.555716,106.729858);
        LatLng t9 = new LatLng(-6.555661,106.729036);
        LatLng t10 = new LatLng(-6.555689,106.728272);
        LatLng t11 = new LatLng(-6.555953,106.727828);
        LatLng t12 = new LatLng(-6.556040,106.727578);
        LatLng t13 = new LatLng(-6.556128,106.726778);
        LatLng t14 = new LatLng(-6.556393,106.726215);
        LatLng t15 = new LatLng(-6.556513,106.725835); // until here, jump 27
        LatLng t16 = new LatLng(-6.556390,106.725266);
        LatLng t17 = new LatLng(-6.556256,106.725019);
        LatLng t18 = new LatLng(-6.556273,106.724215);
        LatLng t19 = new LatLng(-6.556686,106.723809);
        LatLng t20 = new LatLng(-6.556749,106.723604);
        LatLng t21 = new LatLng(-6.556758,106.722955);
        LatLng t22 = new LatLng(-6.556742,106.722473);
        LatLng t23 = new LatLng(-6.556747,106.721773);// repeat
        LatLng t24 = new LatLng(-6.556691,106.721454);
        LatLng t25 = new LatLng(-6.556973,106.721357);
        LatLng t26 = new LatLng(-6.556989,106.721727); // jum ke23
        LatLng t27 = new LatLng(-6.556830,106.726385);
        LatLng t28 = new LatLng(-6.557206,106.726651);
        LatLng t29 = new LatLng(-6.557512,106.726767);
        LatLng t30 = new LatLng(-6.558131,106.726845);
        LatLng t31 = new LatLng(-6.558978,106.727306);
        LatLng t32 = new LatLng(-6.559431,106.727684);
        LatLng t33 = new LatLng(-6.559640,106.727913);
        LatLng t34 = new LatLng(-6.559717,106.728055);
        LatLng t35 = new LatLng(-6.559839,106.729141);
        LatLng t36 = new LatLng(-6.559888,106.729975);
        LatLng t37 = new LatLng(-6.559945,106.730309); // aware 1
        LatLng t38 = new LatLng(-6.559786,106.730451); // aware 2
        LatLng t39 = new LatLng(-6.560969,106.730396);
        LatLng t40 = new LatLng(-6.560851,106.730049);
        LatLng t41 = new LatLng(-6.560319,106.730083); // jmp ke aw1 aw2
        LatLng t42 = new LatLng(-6.558912,106.730942);
        LatLng t43 = new LatLng(-6.558049,106.731438);



        IconGenerator iconfun = new IconGenerator(this);
        iconfun.setStyle(IconGenerator.STYLE_ORANGE);

        MarkerOptions faperta = new MarkerOptions().position(hltfprta).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Faperta");
        MarkerOptions fmipa = new MarkerOptions().position(hltfmipa).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Fmipa");
        MarkerOptions astri = new MarkerOptions().position(hltastri).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Astri");
        MarkerOptions tanoto = new MarkerOptions().position(hlttanoto).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Tanoto");
        MarkerOptions menwa = new MarkerOptions().position(hltmenwa).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Menwa");
        MarkerOptions asinter = new MarkerOptions().position(hltasmintr).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Asrama internasional");
        MarkerOptions alhur = new MarkerOptions().position(hltalhur).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Al-hurriyah");
        MarkerOptions gorlam = new MarkerOptions().position(hltgorlam).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Gor lama");
        MarkerOptions fahutan = new MarkerOptions().position(hltfahutan).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Fahutan");
        MarkerOptions fpik = new MarkerOptions().position(hltfpik).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Fpik");
        MarkerOptions fkh = new MarkerOptions().position(hltfkh).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Fkh");
        MarkerOptions techpark = new MarkerOptions().position(hlttcnprk).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Techno Park");
        MarkerOptions lsi = new MarkerOptions().position(hltlsi).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Lsi");
        MarkerOptions fema = new MarkerOptions().position(hltfema).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Fema");
        MarkerOptions gww = new MarkerOptions().position(hltgww).icon(BitmapDescriptorFactory.fromBitmap(iconfun.makeIcon("0"))).title("Gww");



        mfaperta = mMap.addMarker(faperta);
        mfmipa = mMap.addMarker(fmipa);
        mastri = mMap.addMarker(astri);
        mtanoto=mMap.addMarker(tanoto);
        mmenwa =mMap.addMarker(menwa);
        mfahutan =mMap.addMarker(fahutan);
        masramainternasional=mMap.addMarker(asinter);
        malhur=mMap.addMarker(alhur);
        mgorlam=mMap.addMarker(gorlam);
        mfpik=mMap.addMarker(fpik);
        mfkh=mMap.addMarker(fkh);
        mteknofast =mMap.addMarker(techpark);
        mlsi =mMap.addMarker(lsi);
        mfem = mMap.addMarker(fema);
        mgww= mMap.addMarker(gww);

        //mfmipa.showInfoWindow();

        PolylineOptions Track = new PolylineOptions()
                .add(t1).add(t2).add(t3).add(t4).add(t5).add(t6).add(t7).add(t8).add(t9).add(t10).add(t11).add(t12)
                .add(t13).add(t14).add(t15).add(t16).add(t17).add(t18).add(t19).add(t20).add(t21).add(t22).add(t23)
                .add(t24).add(t25).add(t26).add(t23).add(t22).add(t21).add(t20).add(t19).add(t18).add(t17).add(t16)
                .add(t15).add(t27).add(t28).add(t29).add(t30).add(t31).add(t32).add(t33).add(t34).add(t35).add(t36)
                .add(t37).add(t38).add(t39).add(t40).add(t41).add(t37).add(t38).add(t42).add(t43)
                .add(t1).width(12).color(Color.GREEN).geodesic(true);
        mMap.addPolyline(Track);

        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, (float) 15.0));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        /*
        // membuat fungsi dan icon mylocation
        mMap.setMyLocationEnabled(true);
        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 15);
        }*/


    }

    @Override
    public void onLocationChanged(Location location) {

        TextView Searchingloc_txt  = findViewById(R.id.Searchlocation_txt);
        Searchingloc_txt.setVisibility(View.GONE);

        /*
        String message = String.format(
                "New Location \n Latitude: %1$s \n Longitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );
        */
        //Cek.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        bus.setLatlang(latLng);
        mylocation = latLng;
        if(ourGlobalMarker == null) { // First time adding marker to map
            ourGlobalMarker = mMap.addMarker(new MarkerOptions().position(bus.getLatlang()).icon(bitmapDescriptorFromVector(this,R.drawable.ic_pets_black_24dp,R.drawable.ic_school_bus)));
            if(saver.isSwitchs()){
                ourGlobalMarker.setVisible(false);
            }else {
                ourGlobalMarker.setVisible(true);
            }
            MarkerAnimation.animateMarkerToICS(ourGlobalMarker, latLng, new LatLngInterpolator.Spherical());
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        } else {
            MarkerAnimation.animateMarkerToICS(ourGlobalMarker, latLng, new LatLngInterpolator
                    .Spherical());
            if(saver.isSwitchs()){
                ourGlobalMarker.setVisible(false);
                JSONObject paketlokasi = new JSONObject();
                String longitude = null;
                String latitude = null;
                try {
                    longitude = String.format(String.valueOf(((location.getLongitude()))));
                    latitude = String.format(String.valueOf(location.getLatitude()));
                    paketlokasi.put("longitude",longitude);
                    paketlokasi.put("latitude",latitude);
                    paketlokasi.put("id",saver.getId_bus());
                    paketlokasi.put("token",saver.getToken());

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                kirimpaketloc(paketlokasi);
            }else {
                ourGlobalMarker.setVisible(true);
            }

            //Toast.makeText(MapsActivity.this, message, Toast.LENGTH_LONG).show();
            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
        }

    }

    private void kirimpaketloc(JSONObject paketlokasi) {
        socket.emit("lokasi", paketlokasi);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {
        Toast.makeText(MapsActivity.this,
                "Provider enabled by the user. GPS turned on",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String s) {
        Toast.makeText(MapsActivity.this,
                "Provider disabled by the user. GPS turned off",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        //status_drv=false;
        if(saver.isSwitchs()==true){

            socket.emit("obus", paket_id);
            socket.disconnect();
        }
        //socket2.disconnect();
        //socket.emit("obus", paket_id);
        super.onDestroy();

    }


}
