package com.example.janinacosta.ami;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sianna-chan on 26/01/2017.
 */

public class HorasAdapter extends RecyclerView.Adapter<HorasAdapter.HolderHoras>{
    //data
    static List<AlarmaModel> dbList;
    static Context context;
    final String NOMBRE_BASEDATOS = "medAlarmas.db";

    public HorasAdapter(Context context, List<AlarmaModel> dbList){
        this.dbList = new ArrayList<AlarmaModel>();
        this.context = context;
        this.dbList = dbList;
    }

    @Override
    public HolderHoras onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView3 = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_horas, null);
        RecyclerView.ViewHolder viewHolder = new HorasAdapter.HolderHoras(itemLayoutView3);
        return (HorasAdapter.HolderHoras) viewHolder;
    }

    @Override
    public void onBindViewHolder(HolderHoras holder, int position) {
        if(dbList.size()==0){
            holder.horas.setText("nada");
        }else{
        holder.horas.setText(String.valueOf(dbList.get(position).getHora()));}

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    //clase interna
    public static class HolderHoras extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView horas;
        Button btnModiHora;


        public HolderHoras(View itemLayoutView) {
            super(itemLayoutView);
            horas = (TextView) itemLayoutView .findViewById(R.id.horas);
            btnModiHora = (Button) itemLayoutView .findViewById(R.id.btnModiHora);
            itemLayoutView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            /*
            Intent intent = new Intent(context,DetailsActivity.class);
            Bundle extras = new Bundle();
            extras.putInt("position",getAdapterPosition());
            intent.putExtras(extras);
            context.startActivity(intent);
            */
        }

    }

}
