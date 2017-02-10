package com.example.janinacosta.ami;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Sianna-chan on 08/02/2017.
 */

public class ActividadCrearReceta extends AppCompatActivity {
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/MisRecetas/";
    private File file = new File(ruta_fotos);
    private Button boton, guardar;
    private EditText nombreReceta;
    private String nombre_foto;
    private ArrayList<RecetaModel> lista_receta= new ArrayList<RecetaModel>();
    private int size_receta, idReceta;

    //helper
    DatabaseHelpher helpher;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_receta);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);

        //Cantidad de recetas
        helpher = new DatabaseHelpher(ActividadCrearReceta.this);
        lista_receta=helpher.getTodasRecetas();
        size_receta=lista_receta.size();
        //if (size_receta>0){  idReceta= helpher.get_Receta(size_receta).getIdReceta();}
        //Log.e("ID RECETA PENULTIMO", ""+idReceta+" size: "+size_receta);


        //TextView nombre receta
        nombreReceta= (EditText)findViewById(R.id.txt_nombreReceta);

        //======== codigo nuevo ========
        boton = (Button) findViewById(R.id.btnTomaFoto);
        //Si no existe crea la carpeta donde se guardaran las fotos
        file.mkdirs();
        //accion para el boton tomar foto
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String file = ruta_fotos + getCode() + ".jpg";
                File mi_foto = new File(file);
                try {
                    mi_foto.createNewFile();
                } catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }
                //
                Uri uri = Uri.fromFile(mi_foto);
                //Abre la camara para tomar la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Guarda imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                Log.e("MEDIA:" + MediaStore.EXTRA_OUTPUT, uri.toString());

                helpher = new DatabaseHelpher(ActividadCrearReceta.this);
                Log.e("recetas" , "anterior"+lista_receta.size()+"ahora: "+helpher.getTodasRecetas().size());

                if (size_receta==helpher.getTodasRecetas().size()){
                    //Guarda en la base
                    Log.e("ENTR NUEVA", "INSEERTAR");
                    helpher.insert_receta(nombreReceta.getText().toString(),file);
                }else{
                    Log.e("ACTUALIZAR", "DEBE ACTUALIZAE");
                    helpher.actualizarUltimaRecetaFoto(file);
                }

                //Retorna a la actividad
                startActivityForResult(cameraIntent, 0);

            }

        });
        //====== codigo nuevo:end ======


        //Boton guardar
        guardar = (Button) findViewById(R.id.btnCrearReceta);
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Guarda en la base
                helpher = new DatabaseHelpher(ActividadCrearReceta.this);
               //Actualizo antes de guardar, cualquier cambio que se haya dado
                helpher.actualizarUltimaRecetaNombre(nombreReceta.getText().toString());
                Intent intent = new Intent (v.getContext(),ActividadRecetas.class );
                v.getContext().startActivity(intent);
                finish();


            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        nombre_foto = "receta_" + date;
        return nombre_foto;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.e("EXTRA: ","ANTES DE ENTRAR");
        if (requestCode == 0) {
            //BASE
            helpher = new DatabaseHelpher(ActividadCrearReceta.this);

            //Obtener id receta
            RecetaModel receta = helpher.getTodasRecetas().get(helpher.getTodasRecetas().size()-1);
            String urlFoto = receta.getUrlFoto();

            //Mostrar imagen
            ImageView jpgView = (ImageView) findViewById(R.id.image_receta);
            Bitmap bitmap = BitmapFactory.decodeFile(urlFoto);
            jpgView.setImageBitmap(bitmap);



        }
    }

    public void EliminarRecetasTemporales(){
        helpher = new DatabaseHelpher(ActividadCrearReceta.this);
        SQLiteDatabase bd = helpher.getWritableDatabase();
        int fin=helpher.get_Receta(helpher.getTodasRecetas().size()-1).getIdReceta();
        for (int i=idReceta+1;i<fin;i++){
            int elimina=bd.delete("receta", "idReceta='" + i+"'", null);
            Log.e("ID RECETA", ""+i);
            if (elimina>1) Log.e("ELIMINO", "SI ELIMINOOOO");
        }

        bd.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

}
