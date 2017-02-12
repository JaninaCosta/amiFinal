package com.example.janinacosta.ami;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.janinacosta.ami.MisMedicamentosActivity.TRANSITION_FAB;

/**
 * Created by Sianna-chan on 01/02/2017.
 */

public class ActividadRecetas extends AppCompatActivity {

    //FloatingActionButton fab;
    Button fab_receta;
    //helper
    DatabaseHelpher helpher;

    //data recyccler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<RecetaModel> listaRecetas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);

        /////// RECYCLER ////////////
        //recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleviewReceta);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        helpher = new DatabaseHelpher(ActividadRecetas.this);
        listaRecetas = helpher.getTodasRecetas();
        Log.e("RECETA SIZE", ""+listaRecetas.size());
        // specify an adapter (see also next example)
        mAdapter = new RecetaAdapter(ActividadRecetas.this, listaRecetas);
        mRecyclerView.setAdapter(mAdapter);

        /// FIN RECYCLER

        //Boton nueva receta
        fab_receta = (Button) findViewById(R.id.fab_receta);
        fab_receta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair<View, String> pair = Pair.create(view.findViewById(R.id.fab_receta),TRANSITION_FAB);
                ActivityOptionsCompat op;
                Activity act = ActividadRecetas.this;
                op = ActivityOptionsCompat.makeSceneTransitionAnimation(act, pair);

                Intent i = new Intent(act, ActividadCrearReceta.class);
                act.startActivityForResult(i, mAdapter.getItemCount(), op.toBundle());

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        finish();
        Intent i = new Intent(getApplicationContext(), MenuActividad.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
