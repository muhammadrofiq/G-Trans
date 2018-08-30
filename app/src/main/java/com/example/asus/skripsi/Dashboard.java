package com.example.asus.skripsi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity implements View.OnClickListener{
    private CardView Maps;
    private CardView Provile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        Maps    = (CardView) findViewById(R.id.actMaps);
        Provile = (CardView) findViewById(R.id.profile);

        Maps.setOnClickListener(this);
        Provile.setOnClickListener(this);

        final Intent Data = getIntent();
        Toast.makeText(Dashboard.this, Data.getStringExtra("id_bus"), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.actMaps: i = new Intent(this,MapsActivity.class);startActivity(i);break;
            case R.id.profile: i = new Intent(this,Chat.class);startActivity(i);break;
        }

    }

    public void showpopup(View view) {
    }
}
