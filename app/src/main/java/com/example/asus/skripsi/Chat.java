package com.example.asus.skripsi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asus.skripsi.model.halte_model;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.client.Socket;
import com.github.nkzawa.socketio.client.IO;
import com.google.android.gms.maps.model.LatLng;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    private halte_model halteModel;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager manager;
    EditText chat;
    com.github.nkzawa.socketio.client.Socket socket;
    ArrayList<String>chat_data = new ArrayList<>();
    ArrayList<String>pengirim_= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        halteModel.setCounter(1);
        halteModel.setIdhalte(1);
        LatLng loc = new LatLng(-6.556072,106.726984);
        halteModel.setLokasi(loc);
        int id = halteModel.getCounter();
        int counter=halteModel.getIdhalte();
        LatLng lokasi= halteModel.getLokasi();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        adapter = new chat_adapter(chat_data,pengirim_);
        recyclerView.setAdapter(adapter);
        manager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        chat = (EditText)findViewById(R.id.chat);
        try{
            socket = IO.socket("http://bukajurnal.com");
        }catch (URISyntaxException e){
            Log.d("error", "Oncreate:"+e.toString());
        }
        socket.connect();
        socket.on("pesan",handling);

    }


    private Emitter.Listener handling = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //tambahpesan(args[0].toString(),null);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tambahpesan(args[0].toString(),null);
                    //masuks(args[0]);
                }
            });
        }
    };

    public void masuks(Object arg){
        Toast.makeText(Chat.this, "masuk", Toast.LENGTH_LONG).show();
    }

    public void kirim(View v){
        kirim_pesan();
        //socket.emit("log", "aaah");
    }
    public void kirim_pesan(){
        String pesan = chat.getText().toString().trim();
        chat.setText("");
        tambahpesan(pesan,"me");
        socket.emit("pesan", pesan);
    }
    public  void tambahpesan(String pesan, String pengirim){
        Toast.makeText(Chat.this, "masuk", Toast.LENGTH_LONG).show();
        chat_data.add(pesan);
        this.pengirim_.add(pengirim);
        adapter.notifyItemInserted(chat_data.size());
        recyclerView.smoothScrollToPosition(chat_data.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
