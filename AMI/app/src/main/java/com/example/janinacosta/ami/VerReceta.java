package com.example.janinacosta.ami;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by Sianna-chan on 12/02/2017.
 */

public class VerReceta extends AppCompatActivity {

    int position;
    DatabaseHelpher helpher;
    ArrayList<RecetaModel> dbList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_ver_receta);

        /*
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);*/

        //Recive position of recycler
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");

        // helpher
        helpher = new DatabaseHelpher(this);
        dbList= new ArrayList<RecetaModel>();
        dbList = helpher.getTodasRecetas();

        if(dbList.size()>0){
            String urlFoto= dbList.get(position).getUrlFoto();
            Log.e("VER RECETA","URL FOTO: "+urlFoto);
            Intent intent_foto = new Intent();
            intent_foto.setAction(Intent.ACTION_VIEW);
            Uri imgUri = Uri.parse("file://"+urlFoto);
            intent_foto.setDataAndType(imgUri, "image/*");
            startActivity(intent_foto);
            //Recetas
            Intent intent_recetas = new Intent (getApplicationContext(),ActividadRecetas.class );
            startActivity(intent_recetas);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
