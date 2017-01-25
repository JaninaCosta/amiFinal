package com.example.janinacosta.ami;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janina Costa on 24/1/2017.
 */

public class MedicamentoDiaAdapter  extends RecyclerView.Adapter<MedicamentoDiaAdapter.Holder> {
    //data
    static List<MedicamentoModel> dbList;
    static Context context;
    final String NOMBRE_BASEDATOS = "medAlarmas.db";

    public MedicamentoDiaAdapter(Context context, List<MedicamentoModel> dbList){
        this.dbList = new ArrayList<MedicamentoModel>();
        this.context = context;
        this.dbList = dbList;
    }

    @Override
    public MedicamentoDiaAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_med_dia, null);
        RecyclerView.ViewHolder viewHolder = new MedicamentoDiaAdapter.Holder(itemLayoutView2);
        return (Holder) viewHolder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.name.setText(dbList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    //clase interna
    public static class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        TextClock tc;
        Button verDetalle;
        DatabaseHelpher helpher ;

        public Holder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView .findViewById(R.id.idName);
            tc = (TextClock)itemLayoutView .findViewById(R.id.textClock5);
            verDetalle = (Button)itemLayoutView .findViewById(R.id.btnVerDetalled2);
            itemLayoutView.setOnClickListener(this);

            //evento para ver detalle de un medicamento
            verDetalle.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,DetailsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("position",getAdapterPosition());
                    intent.putExtras(extras);
                    context.startActivity(intent);
                    tc.setText("");
                }
            });

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
