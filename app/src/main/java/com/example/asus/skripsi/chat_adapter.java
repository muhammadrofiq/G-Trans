package com.example.asus.skripsi;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.telecom.TelecomManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by root on 19/02/18.
 */

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ViewHolder>{


    ArrayList<String> chat_data = new ArrayList<>();
    ArrayList<String>pengirim_= new ArrayList<>();

    public chat_adapter(ArrayList<String> chat_data, ArrayList<String> pengirim_){
        this.chat_data=chat_data;
        this.pengirim_=pengirim_;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat,parent,false);

        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (pengirim_.get(position)=="me"){

            holder.getIsipesan().setText(chat_data.get(position));
            holder.getUserpesan().setText(pengirim_.get(position));
            holder.getIsipesan().setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_me));
            holder.getLayout().setGravity(Gravity.RIGHT);
        }else {
            holder.getIsipesan().setText(chat_data.get(position));
            holder.getUserpesan().setText("user lain");
            holder.getLayout().setGravity(Gravity.LEFT);
            holder.getIsipesan().setBackground(holder.itemView.getContext().getResources().getDrawable(R.drawable.bg_you));
        }
    }

    @Override
    public int getItemCount() {


        return chat_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView isipesan,userpesan;
        LinearLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            isipesan = (TextView) itemView.findViewById(R.id.isipesan);
            userpesan = (TextView) itemView.findViewById(R.id.userpesan);
            layout = (LinearLayout)itemView.findViewById(R.id.layoutpesan);
        }

        public TextView getIsipesan() {
            return isipesan;
        }

        public TextView getUserpesan() {
            return userpesan;
        }

        public LinearLayout getLayout() {
            return layout;
        }
    }
}
