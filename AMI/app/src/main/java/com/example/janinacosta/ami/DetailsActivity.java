package com.example.janinacosta.ami;

import android.animation.Animator;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class DetailsActivity extends AppCompatActivity {
    //data
    DatabaseHelpher helpher;
    List<MedicamentoModel> dbList;
    int position;
    TextView tvname,tvdosis, tvnumdias, tvindicaciones, tvfrecuencia;
    RelativeLayout contenedorNombre;
    LinearLayout layout_detalles;
    String nombreMed;
    Button modificar, guardarCambios;
    Handler h;
    final String NOMBRE_BASEDATOS = "mediAlarma.db";


    //collapsing
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details_uno);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position");

        //tvname =(TextView)findViewById(R.id.name);
        tvname = (EditText)findViewById(R.id.txtnameEdit);
        tvnumdias =(EditText) findViewById(R.id.diasMedicamento);
        tvdosis =(EditText) findViewById(R.id.dosis);
        tvindicaciones =(EditText) findViewById(R.id.indicaciones);
        tvfrecuencia =(EditText)findViewById(R.id.frecuencia);
        modificar = (Button) findViewById(R.id.btnModificar);
        contenedorNombre = (RelativeLayout) findViewById(R.id.layout_nombre);
        layout_detalles = (LinearLayout) findViewById(R.id.layout_detalles);

        //hilos
        h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                startFlash();
            }

        },1000);
        //fin hilos




        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stop();
                tvnumdias.setFocusableInTouchMode(true);
                tvdosis.setFocusableInTouchMode(true);
                tvindicaciones.setFocusableInTouchMode(true);
                tvfrecuencia.setFocusableInTouchMode(true);

                guardarCambios.setVisibility(View.VISIBLE);
                //modificar.setVisibility(View.VISIBLE);
                contenedorNombre.setVisibility(View.VISIBLE);
                tvname.requestFocus();
                collapsingToolbarLayout.setFocusableInTouchMode(true);

            }
        });

        //Guardar cambios al modificar un medicamento
        guardarCambios=(Button)findViewById(R.id.btnGuardar);
        guardarCambios.setVisibility(View.INVISIBLE);
        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DetailsActivity.this,"Los datos fueron modificados correctamente",Toast.LENGTH_LONG).show();
                startActivity(new Intent(DetailsActivity.this, MisMedicamentosActivity.class));
                //nombre
                String nombreActualizado = (tvname.getText()).toString();
                //numero de dias
                String numDias = (tvnumdias.getText()).toString();
                int num_dias = Integer.parseInt(numDias);
                //dosis
                String cantidad_dosis = (tvdosis.getText()).toString();
                int cant_dosis = Integer.parseInt(cantidad_dosis);
                //indicaciones
                String indicaciones = (tvindicaciones.getText()).toString();



                modificar_medicamento(nombreMed, nombreActualizado, num_dias,cant_dosis,indicaciones);
            }
        });


       // helpher
        helpher = new DatabaseHelpher(this);
        dbList= new ArrayList<MedicamentoModel>();
        dbList = helpher.getDataFromDB();

        if(dbList.size()>0){
            String name= dbList.get(position).getName();
            int dias= dbList.get(position).getNum_dias();
            int dosis=dbList.get(position).getDosis();
            String indicaciones= dbList.get(position).getIndicaciones();
            int frecuencia= dbList.get(position).getFrecuencia();

            tvname.setText(name);
            tvnumdias.setText(String.valueOf(dias));
            tvdosis.setText(String.valueOf(dosis));
            tvindicaciones.setText(indicaciones);
            tvfrecuencia.setText(String.valueOf(frecuencia));
            //Guardo el nombre en una variable tmp nombreMed para el título
            nombreMed = name;
        }


        //collapsin
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(nombreMed);

        dynamicToolbarColor();
        toolbarTextAppernce();
        //fin collapsin

        Toast.makeText(DetailsActivity.this, dbList.toString(), Toast.LENGTH_LONG);


    }
    //hilos
    private void startFlash() {
        Animation animacion = new AlphaAnimation(1,0);
        animacion.setDuration(250);
        animacion.setInterpolator(new LinearInterpolator());
        animacion.setRepeatCount(8);
        animacion.setRepeatMode(Animation.REVERSE);
        modificar.startAnimation(animacion);
    }

    public void Stop() {
        h.removeMessages(0);
    }



    //metodos para collapsing
    private void dynamicToolbarColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_pastilla_grande);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(getResources().getColor(R.color.blue)));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(getResources().getColor(R.color.blue)));
            }
        });
    }

    private void toolbarTextAppernce() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }


    //boton regresar

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //Método para Modificar medicamento
    public void modificar_medicamento(String nombreMed, String nombreActual,  int numDias, int dosis, String indicaciones) {
        SQLiteDatabase bd = helpher.getWritableDatabase();
        ContentValues registro = new ContentValues();
        registro.put(helpher.Name, nombreActual);
        registro.put(helpher.NumDias, numDias);
        registro.put(helpher.Dosis, dosis);
        registro.put(helpher.Indicaciones, indicaciones);

        Log.d("NOMBRE MEDICAMENTO",""+ nombreMed);
        int cant = bd.update("medicamentos", registro, "name = + '" + nombreMed + "'", null);
        bd.close();
        if (cant == 1) {
            Toast.makeText(this, "Medicamento modificado correctamente", Toast.LENGTH_SHORT).show();
            Log.d(""+nombreMed," MODIFICADO CORRECTAMENTE");
        }else {
            //Toast.makeText(this, "No existe un medicamento con ese nombre, Toast.LENGTH_SHORT).show();
            Log.d(""+nombreMed," NO SE RECONOCE MEDICAMENTO");
        }
    }




}
