package com.example.asus.skripsi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.example.asus.skripsi.model.Shared_p;
import com.example.asus.skripsi.model.bus_model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by root on 03/03/18.
 */

public class Adapter_bus  extends RecyclerView.Adapter<Adapter_bus.Listviewholder>{
    //public Context conext ;


    private List<bus_model>listbus;
    private Activity activity;
    private Shared_p saver;

    public Adapter_bus(Activity activity, List<bus_model> dataitem){
        this.activity=activity;
        this.listbus=dataitem;
        this.saver = new Shared_p("Session2", activity);

    }

    @Override
    public Listviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_item,parent,false);
        return new Listviewholder(view);
    }

    @Override
    public void onBindViewHolder(Listviewholder holder, int position) {
        bus_model m = listbus.get(position);
        String id = m.getId_bus();
        String noken = m.getNomor_kendaraan();
        int path = R.drawable.bus;
        holder.idbus = id;
        holder.pathimgbus = path;
        holder.nokenbus = noken;
        holder.nokontak = m.getNomor_kontak();

        ((Listviewholder) holder).Ikat(position , m);
    }

    @Override
    public int getItemCount() {
        return listbus.size();
    }

    public class Listviewholder extends RecyclerView.ViewHolder{
        private TextView mBustext;
        private TextView mStatustext;
        private ImageView mImage;
        private String idbus;
        private String nokenbus;
        private String nokontak;
        private int pathimgbus;

        public Listviewholder(View itemView){
            super(itemView);
            mBustext =  itemView.findViewById(R.id.text_bus);
            mImage = itemView.findViewById(R.id.item_bus_icon);
            mStatustext= itemView.findViewById(R.id.text_statusbus);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final ProgressDialog dialog = ProgressDialog.show(view.getContext(), "", "Loading. Please wait...", true);
                    RequestQueue queue = Volley.newRequestQueue(activity);

                    String url = "http://bukajurnal.com/update_drv";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    if(response.equals("false")){

                                        Toast.makeText(activity, "gagal", Toast.LENGTH_SHORT).show();

                                    }else{
                                        //dialog.dismiss();
                                        Intent paket = new Intent(activity, MapsActivity.class);
                                        /*
                                        paket.putExtra("id_bus",idbus);
                                        paket.putExtra("path_image", pathimgbus );
                                        paket.putExtra("nokenbus", nokenbus);

                                        SharedPreferences Session = activity.getSharedPreferences("Session", activity.MODE_PRIVATE);
                                        SharedPreferences.Editor sEditor = Session.edit();
                                        sEditor.putString("status", "done");
                                        sEditor.putString("id_bus", idbus);
                                        sEditor.putString("nokenbus",nokenbus);
                                        sEditor.putInt("path_image",pathimgbus);
                                        sEditor.putString("nokontak", nokontak);
                                        sEditor.apply();
                                        */
                                        // start - sab - 24- mar - 2018
                                        saver.setStatus("done");
                                        saver.setId_bus(idbus);
                                        saver.setNomor_kendaraan(nokenbus);
                                        saver.setPath_image(pathimgbus);
                                        saver.setNomor_kontak(nokontak);
                                        // end
                                        activity.startActivity(paket);
                                        activity.finish();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        //adding parameters to the request
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            /*
                            SharedPreferences Sesion = activity.getSharedPreferences("Session",MODE_PRIVATE);
                            String id_driver = Sesion.getString("id_driver", "ga ada haha");
                            */
                            params.put("id_bus", idbus );
                            params.put("id_driver", saver.getId_driver() );
                            params.put("statusq", "1" );
                            return params;
                        }
                    };
                    queue.add(stringRequest);






                }
            });

        }
        public void Ikat(int position, bus_model m){
            mBustext.setText("Bus "+m.getId_bus());
            mImage.setImageResource(R.drawable.bus);
            mStatustext.setText("Nomor kendaraan: "+m.getNomor_kendaraan());
        }


    }
}
