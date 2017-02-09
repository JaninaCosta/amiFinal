package com.example.janinacosta.ami;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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

/**
 * Created by Sianna-chan on 01/02/2017.
 */

public class ActividadRecetas extends AppCompatActivity {

    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private Button boton;
    private String nombre_foto;
    private static final int CAMERA_REQUEST = 1888;
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
/*
        //======== codigo nuevo ========
           boton = (Button) findViewById(R.id.btnTomaFoto);
           //Si no existe crea la carpeta donde se guardaran las fotos
           file.mkdirs();
           //accion para el boton
           boton.setOnClickListener(new View.OnClickListener() {

                        @Override
                public void onClick(View v) {
                     String file = ruta_fotos + getCode()+ ".jpg";
                     File mi_foto = new File( file );
                     try {
                         mi_foto.createNewFile();
                     } catch (IOException ex) {
                         Log.e("ERROR ", "Error:" + ex);
                     }
                      //
                      Uri uri = Uri.fromFile( mi_foto );
                      //Abre la camara para tomar la foto
                      Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      //Guarda imagen
                      cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                      //cameraIntent.putExtra("data", "valoooooor");
                      Log.e("MEDIA:"+MediaStore.EXTRA_OUTPUT,uri.toString());

                      //Guarda en la base
                      helpher = new DatabaseHelpher(ActividadRecetas.this);
                      helpher.insert_receta("Receta1",file);
                      //Retorna a la actividad
                      startActivityForResult(cameraIntent, 0);

                    }

           });
           //====== codigo nuevo:end ======
*/
    }

    /**
     60  * Metodo privado que genera un codigo unico segun la hora y fecha del sistema
     61  * @return photoCode
     62  * */
    @SuppressLint("SimpleDateFormat")
     private String getCode()
     {
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
       String date = dateFormat.format(new Date() );
       nombre_foto = "pic_" + date;
       return nombre_foto;
     }
/*
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.e("EXTRA: ","ANTES DE ENTRAR");
        if (requestCode == 0) {
            //mostrar imagen
            //ImageView jpgView = (ImageView)findViewById(R.id.image_receta);
            //Bitmap bitmap = BitmapFactory.decodeFile(ruta_fotos+"/img.jpg");
            //Log.e("DIRECTORIO", file);
            //jpgView.setImageBitmap(bitmap);
            //Log.e("EXTRA: ",data.getExtras().get("data").toString());
            //Bitmap theImage = (Bitmap) data.getExtras().get("data");
            //jpgView .setImageBitmap(theImage);

            //Obtener id receta
            RecetaModel receta = helpher.getTodasRecetas().get(helpher.getTodasRecetas().size()-1);
            String urlFoto=receta.getUrlFoto();

            //Mostrar imagen
            ImageView jpgView = (ImageView)findViewById(R.id.image_receta);
            Bitmap bitmap = BitmapFactory.decodeFile(urlFoto);
            Log.e("DIRECTORIO", urlFoto);
            jpgView.setImageBitmap(bitmap);



        }
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}
