package com.example.janinacosta.ami;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActividadCrearAlarma extends AppCompatActivity {
    private EditText editText_hora_inicial, editText_frecuencia;
    private String string_hora, string_minutos;
    private int frecuencia_horas;
    private int hora, minutos;

    LinearLayout layout_horas;

    //data recyccler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    //Crear alarma
    private AlarmManager alarmManager;
    private Context context;
    private PendingIntent pending_intent;
    private Intent my_intent;
    final Calendar calendar= Calendar.getInstance();
    EditText e_hora_inicial, e_frecuencia;

    //collapsing
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    //Boton Guaradar
    Button btnGuardar ;

    //helper
    DatabaseHelpher helpher;

    //Alarma
    AlarmaModel alarma_inicial;

    ArrayList<AlarmaModel> listaHoras;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alarma);
        layout_horas = (LinearLayout) findViewById(R.id.layout_horas);
        layout_horas.setVisibility(View.GONE);

       //listaHoras= new ArrayList<AlarmaModel>();

        //recycler
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleviewhora);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //
        e_hora_inicial= (EditText)findViewById(R.id.txt_HoraInicial);
        e_frecuencia= (EditText)findViewById(R.id.txt_frecuencia);

        //

        //****RECIBIR DATOs DE ACTIVITY NUEVO MEDICAMENTO
        // 1. get passed intent
        Intent intent = getIntent();

        // 2. get message value from intent
        final MedicamentoModel medicamento_new = (MedicamentoModel)intent.getSerializableExtra("medicamento");

        // 4. get bundle from intent
        Bundle bundle = intent.getExtras();

        // 5. get status value from bundle
        String status = bundle.getString("status");

        // 6. show status on Toast
        Toast toast =Toast.makeText(this, status, Toast.LENGTH_LONG);
        toast.show();

        /// FIN RECIBIR DATOS

        //metodo para pasar de crear alarma a la lista de medicamentos

        btnGuardar  =(Button)findViewById(R.id.btnCrearMedicamento);
        btnGuardar .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpher = new DatabaseHelpher(ActividadCrearAlarma.this);
                helpher.insert_medicamento(medicamento_new.getName(), medicamento_new.getNum_dias(),medicamento_new.getDosis(),medicamento_new.getIndicaciones(),Integer.valueOf(e_frecuencia.getText().toString()));

                //Obtener id medicamento
                MedicamentoModel medi = helpher.getDataFromDB().get(helpher.getDataFromDB().size()-1);
                int id_medi=medi.getId_medi();


                Toast.makeText(ActividadCrearAlarma.this, medicamento_new.getName()+" agregado a 'Mis Medicamentos'", Toast.LENGTH_LONG).show();

                //Guardar en Base de dato
                //helpher.insert_alarma(alarma_inicial.getHora(),alarma_inicial.getMin());

                //Insertar relacion ALARMA_MEDI
                for (AlarmaModel alarma : listaHoras){
                    helpher.insert_alarma(alarma.getHora(),alarma.getMin());
                    AlarmaModel a= helpher.getTodasAlarmas().get(helpher.getTodasAlarmas().size()-1);
                    int id_alarma = a.getId_alarma() ;

                    helpher.insert_alarma_medi(id_alarma,id_medi);

                }
                Log.e("TODAS: ", ""+helpher.getTodasAlarmas().size());

                //Alarma iniciando
                calendar.set(Calendar.HOUR_OF_DAY, alarma_inicial.getHora());
                calendar.set(Calendar.MINUTE, alarma_inicial.getMin());

                //
                my_intent.putExtra("extra", "alarm on");

                //create a pending intent
                pending_intent = PendingIntent.getBroadcast(ActividadCrearAlarma.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);

                //FIN ALARMA

                Intent i = new Intent(getApplicationContext(), MisMedicamentosActivity.class);
                startActivity(i);
            }
        });

        this.context=this;
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        my_intent = new Intent(this.context, Alarm_Receiver.class);

        editText_hora_inicial = (EditText) findViewById(R.id.txt_HoraInicial);
        editText_hora_inicial.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alarmaPickerDialog();
            }

        });

        editText_frecuencia = (EditText) findViewById(R.id.txt_frecuencia);

        editText_frecuencia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //frecuenciaPickerDialog();
                //action_frecuencia();
            }

        });

        //collapsin
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //collapsin
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Alarma");
        dynamicToolbarColor();
        toolbarTextAppernce();
        //fin collapsin
    }


    private void alarmaPickerDialog(){
        NumberPicker np_hora_inicial = new NumberPicker(this);
        np_hora_inicial.setMaxValue(12);
        np_hora_inicial.setMinValue(0);
        np_hora_inicial.setWrapSelectorWheel(false);
        np_hora_inicial.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //editText_hora_inicial.setText(String.valueOf(newVal));
                string_hora= String.valueOf(newVal);
            }
        });

        NumberPicker np_minutos_inicial = new NumberPicker(this);
        np_minutos_inicial.setMaxValue(60);
        np_minutos_inicial.setMinValue(0);
        np_minutos_inicial.setWrapSelectorWheel(false);
        np_minutos_inicial.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //editText_hora_inicial.setText(String.valueOf(newVal));
                string_minutos= String.valueOf(newVal);
            }
        });

        //CON TIME PICKER

        //Calendar calendar = Calendar.getInstance();
        final TimePicker tp_horaInicial = new TimePicker(this);
        tp_horaInicial.setIs24HourView(true);

        AlertDialog.Builder picker_dialog_horaInicial = new AlertDialog.Builder(this).setView(tp_horaInicial);

        picker_dialog_horaInicial.setTitle("Definir Hora Inicial");

        //Acción del boton aceptar
        picker_dialog_horaInicial.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                calendar.set(Calendar.HOUR_OF_DAY, tp_horaInicial.getCurrentHour());
                calendar.set(Calendar.MINUTE, tp_horaInicial.getCurrentMinute());

                hora= tp_horaInicial.getCurrentHour();
                minutos= tp_horaInicial.getCurrentMinute();
                String hour_string = String.valueOf(hora);
                String minute_string = String.valueOf(minutos);

                layout_horas.setVisibility(View.VISIBLE);

                String am_pm="";

                if (hora > 12) {
                    hour_string = String.valueOf(hora - 12);
                    am_pm = "PM";
                }else
                    am_pm = "AM";

                if (minutos < 10) {
                    minute_string = "0" + String.valueOf(minutos);
                }

                alarma_inicial = new AlarmaModel(0,hora,minutos);

                //set in EditText
                editText_hora_inicial.setText(String.valueOf(hour_string + ":" + minute_string+" "+am_pm));

                //Calcular las horaas
                frecuencia_horas = Integer.valueOf(editText_frecuencia.getText().toString());
                String horas= "";

                listaHoras= new ArrayList<AlarmaModel>();

                for(int i=hora; i<=24;i=(i+frecuencia_horas)){
                    horas = horas + ", "+i+":"+minutos;

                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, minutos);

                    listaHoras.add(new AlarmaModel(0,i,minutos));


                }

                // specify an adapter (see also next example)
                mAdapter = new HorasAdapter(ActividadCrearAlarma.this, listaHoras);
                mRecyclerView.setAdapter(mAdapter);


            }
        });
        /*
        picker_dialog_horaInicial.setNegativeButton("Cancelar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });*/
        picker_dialog_horaInicial.show();
    }

    private void action_frecuencia (){
        frecuencia_horas = Integer.valueOf(editText_frecuencia.getText().toString());
        String horas= "";

        //TextView textView_listaHoras = (TextView) findViewById(R.id.txt_lista_horas);

        ArrayList<String> listaHoras= new ArrayList<String>();



        for(int i=hora; i<24;i=(i+frecuencia_horas)){
            horas = horas + ", "+i+":"+minutos;

            calendar.set(Calendar.HOUR_OF_DAY, i);
            calendar.set(Calendar.MINUTE, minutos);

            //
            my_intent.putExtra("extra", "alarm on");

            //create a pending intent
            pending_intent = PendingIntent.getBroadcast(ActividadCrearAlarma.this, 0,
                    my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //set the alarm manager
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
        }
        //textView_listaHoras.setText("Listas: "+horas);
    }

    private void frecuenciaPickerDialog(){
        NumberPicker np_frecuencia = new NumberPicker(this);
        np_frecuencia.setMaxValue(24);
        np_frecuencia.setMinValue(0);
        np_frecuencia.setWrapSelectorWheel(false);
        np_frecuencia.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //editText_hora_inicial.setText(String.valueOf(newVal));
                frecuencia_horas=newVal;
            }
        });


        AlertDialog.Builder picker_dialog_frecuencia = new AlertDialog.Builder(this).setView(np_frecuencia);

        picker_dialog_frecuencia.setTitle("Frecuencia");
        picker_dialog_frecuencia.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

                editText_frecuencia.setText(String.valueOf(frecuencia_horas));
                String horas= "";

                //TextView textView_listaHoras = (TextView) findViewById(R.id.txt_lista_horas);

                ArrayList<String> listaHoras= new ArrayList<String>();



                for(int i=hora; i<24;i=(i+frecuencia_horas)){
                    horas = horas + ", "+i+":"+minutos;

                    calendar.set(Calendar.HOUR_OF_DAY, i);
                    calendar.set(Calendar.MINUTE, minutos);

                    //
                    my_intent.putExtra("extra", "alarm on");

                    //create a pending intent
                    pending_intent = PendingIntent.getBroadcast(ActividadCrearAlarma.this, 0,
                            my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    //set the alarm manager
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
                }
                //textView_listaHoras.setText("Listas: "+horas);

            }
        });
        picker_dialog_frecuencia.show();
    }

    //funciones collapsin
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
        finish();
        Intent i = new Intent(getApplicationContext(), NuevoMedicamentoActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

}
