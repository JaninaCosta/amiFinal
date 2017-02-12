package com.example.janinacosta.ami;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sianna-chan on 08/02/2017.
 */

public class RecetaAdapter extends RecyclerView.Adapter<RecetaAdapter.HolderReceta>{

    //data
    static ArrayList<RecetaModel> dbList;
    static Context context;
    final String NOMBRE_BASEDATOS = "AppmedAlarma.db";

    public RecetaAdapter(Context context, ArrayList<RecetaModel> dbList){
        this.dbList = new ArrayList<RecetaModel>();
        this.context = context;
        this.dbList = dbList;
    }


    @Override
    public RecetaAdapter.HolderReceta onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView4 = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_receta, null);
        RecyclerView.ViewHolder viewHolder = new RecetaAdapter.HolderReceta(itemLayoutView4);
        return (RecetaAdapter.HolderReceta) viewHolder;
    }

    @Override
    public void onBindViewHolder(RecetaAdapter.HolderReceta holder, int position) {
        String nombreReceta =dbList.get(position).getNombreReceta();
        String urlFoto= dbList.get(position).getUrlFoto();

        holder.nombre_receta.setText(nombreReceta);

        //Imagen receta
        Bitmap bitmap = BitmapFactory.decodeFile(urlFoto);
        holder.img_receta.setImageBitmap(bitmap);
        //holder.horas.setText(""+hour_string + ":" + minute_string+" "+am_pm);
    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }

    public class HolderReceta extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombre_receta;
        Button btnVerReceta;
        ImageView img_receta;

        public HolderReceta(View itemView) {
            super(itemView);
            nombre_receta = (TextView)  itemView .findViewById(R.id.nombre_receta);
            btnVerReceta = (Button) itemView.findViewById(R.id.btnVerReceta);
            img_receta = (ImageView)itemView.findViewById(R.id.img_receta);
            itemView.setOnClickListener(this);

            btnVerReceta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,VerReceta.class);

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

    }
}
