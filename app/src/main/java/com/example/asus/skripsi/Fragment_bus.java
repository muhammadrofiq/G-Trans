package com.example.asus.skripsi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.skripsi.model.bus_model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 03/03/18.
 */

@SuppressLint("ValidFragment")
public class Fragment_bus extends Fragment{

    private List<bus_model> listbus;
    private Activity activity;

    @SuppressLint("ValidFragment")
    public Fragment_bus(Activity activity, List<bus_model> dataitem){
        this.activity=activity;
        this.listbus=dataitem;
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle saved){

        View view = inflater.inflate(R.layout.fragment_bus,container,false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerbus);

        Adapter_bus busadapter = new Adapter_bus(activity, listbus);
        recyclerView.setAdapter(busadapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }



}
