package com.example.janinacosta.ami;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {
    //data
    static List<MedicamentoModel> dbList;
    static Context context;
    final String NOMBRE_BASEDATOS = "medAlarmas.db";



    //constructor
    RecyclerAdapter(Context context, List<MedicamentoModel> dbList ){
        this.dbList = new ArrayList<MedicamentoModel>();
        this.context = context;
        this.dbList = dbList;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, null);
        // crea un viewholder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        holder.name.setText(dbList.get(position).getName());
        holder.frecuencia.setText("Cada "+ dbList.get(position).getFrecuencia()+" horas");
        holder.dosis.setText("Dosis: "+ dbList.get(position).getDosis());

    }


    @Override
    public int getItemCount() {
        return dbList.size();
    }

    //clase interna
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name,dosis, frecuencia;
        Button eliminar, verDetalle;
        DatabaseHelpher helpher ;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            name = (TextView) itemLayoutView .findViewById(R.id.rvname);
            frecuencia = (TextView) itemLayoutView .findViewById(R.id.rvfrecuencia);
            dosis = (TextView) itemLayoutView .findViewById(R.id.rvdosis);

            eliminar= (Button)itemLayoutView .findViewById(R.id.btnEliminar);
            verDetalle = (Button)itemLayoutView .findViewById(R.id.btnVerDetalle);
            itemLayoutView.setOnClickListener(this);

            //Accion boton eliminar
            eliminar.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    helpher = new DatabaseHelpher(RecyclerAdapter.context);
                    SQLiteDatabase bd = helpher.getWritableDatabase();

                    //getAdapterPosition() para obtener la posición del card en el recycler
                    int position = getAdapterPosition();
                    String identificarNombre = dbList.get(position).getName();

                    //para eliminar un medicamento

                    int cant = bd.delete("medicamentos", "name='" + identificarNombre+"'", null);
                    bd.close();
                    if (cant == 1)
                        Toast.makeText(RecyclerAdapter.context, "Ha eliminado el medicamento " +  identificarNombre, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(context.getApplicationContext(), MisMedicamentosActivity.class);
                    context.startActivity(i);
                }
            });

            //evento para ver detalle de un medicamento
            verDetalle.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context,DetailsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putInt("position",getAdapterPosition());
                    intent.putExtras(extras);
                    context.startActivity(intent);
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

        //método para eliminar un medicamento

        public void eliminar_medicamento(String namess){
            String sql = "DELETE FROM medicamentos WHERE name = + '" + namess + "'";
            SQLiteDatabase bd = helpher.getWritableDatabase();
            bd.execSQL(sql);

        }
    }

    //animacion

    //Obtiene un holder y lo pasa al método
    @Override
    public void onViewAttachedToWindow(ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        animateCircularReveal(holder.itemView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularReveal(View view){
        int centerX = 0,centerY = 0, startRadius =0;
        int endRadius = Math.max(view.getWidth(), view.getHeight());
        Animator a = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, startRadius, endRadius);
        view.setVisibility(View.VISIBLE);
        a.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animateCircularDelete (final View v, final int lis_position){
        int centerX = v.getWidth();
        int centerY= v.getHeight();
        int startRadius = v.getWidth();
        int endRadius = 0;
        Animator a = ViewAnimationUtils.createCircularReveal(v, centerX, centerY, startRadius, endRadius);
    }




}
