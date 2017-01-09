package com.example.janinacosta.ami;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class NuevoMedicamentoActivity extends AppCompatActivity implements NumberPicker.OnValueChangeListener{
    EditText etName,etDosis, etIndicaciones, etCantDias;
    Button btnSiguiente;
    private TextView DiasDeTratamiento, Dosis;

    List<MedicamentoModel> dbList;

    //collapsing
    private CollapsingToolbarLayout collapsingToolbarLayout = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_medicamento_uno);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icono_atras_02_01);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //collapsing
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Medicamento");
        dynamicToolbarColor();
        toolbarTextAppernce();
        //fin collapsin

        etName = (EditText)findViewById(R.id.txt_NombreMedicament);
        dbList= new ArrayList<MedicamentoModel>();

        etName = (EditText)findViewById(R.id.txt_NombreMedicament);
        etCantDias = (EditText)findViewById(R.id.txt_DiasDeMedicament);
        etDosis = (EditText)findViewById(R.id.txt_dosis);
        etIndicaciones = (EditText)findViewById(R.id.txt_indicaciones);
        //etFrecuencia = (EditText)findViewById(R.id.etFrecuencia);


        btnSiguiente  =(Button)findViewById(R.id.btnSiguiente);


        /**************GUARDAR MEDICAMENTO ACCION EN BOTON DE ALARMA *********/
        //Guarda los datos en la BDD


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etName.getText().toString().equals("")|| etCantDias.getText().toString().equals("")||etDosis.getText().toString().equals("") || etIndicaciones.getText().toString().equals("")){
                        Toast.makeText(NuevoMedicamentoActivity.this,"Por favor, llene todos los campos",Toast.LENGTH_LONG).show();
                }else {
                    String name = etName.getText().toString();
                    int num_dias = Integer.parseInt(etCantDias.getText().toString());
                    int dosis = Integer.parseInt(etDosis.getText().toString());
                    String indicaciones = etIndicaciones.getText().toString();
                    //String frecuencia=etFrecuencia.getText().toString();

                    MedicamentoModel medicamento_new= new MedicamentoModel(name,num_dias,dosis,indicaciones,"");

                    // 1. create an intent pass class name or intnet action name
                    Intent intent = new Intent(getApplicationContext(), ActividadCrearAlarma.class);

                    // 2. put key/value data
                    intent.putExtra("medicamento", medicamento_new);

                    // 3. or you can add data to a bundle
                    Bundle extras = new Bundle();
                    extras.putString("status", "Datos correctos");

                    // 4. add bundle to intent
                    intent.putExtras(extras);

                    // 5. start the activity
                    startActivity(intent);
                }



            }
        });




        /*
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActividadCrearAlarma.class);
                startActivity(i);
            }
        });
        */



    }


    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        Log.i("value is",""+i1);
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
        getMenuInflater().inflate(R.menu.menu_nuevo_medicamento, menu);
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

}
