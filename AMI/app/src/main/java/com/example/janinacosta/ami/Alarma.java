package com.example.janinacosta.ami;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarma extends AppCompatActivity {
    AlarmManager alarmManager;
    private TimePicker alarmTimePicker;
    private TextView updateText,medicamento, dosis, indicacion;
    Context context;
    PendingIntent pending_intent;

    DatabaseHelpher helpher;
    List<MedicamentoModel> dbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apagar_alarma);

        this.context=this;
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);
        updateText = (TextView) findViewById(R.id.update_text);
        medicamento = (TextView) findViewById(R.id.textViewMedi);
        dosis =(TextView) findViewById(R.id.textViewDosis);
        indicacion= (TextView) findViewById(R.id.textViewDescrip);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_btn_atras_fondo);
        setSupportActionBar(toolbar);
        //

        //Obteniendo valores del último medicamento
        helpher = new DatabaseHelpher(this);
        dbList = new ArrayList<MedicamentoModel>();
        dbList = helpher.getDataFromDB();

        MedicamentoModel m = dbList.get(dbList.size()-1);

        medicamento.setText(m.getName());
        dosis.setText(""+m.getDosis()+ " Dosis");
        indicacion.setText(m.getIndicaciones());

        /// FIN de obtener último medicamento

        /*
        alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        alarmTimePicker=(TimePicker) findViewById(R.id.timePicker);

        final Calendar calendar = Calendar.getInstance();

        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                int hour = alarmTimePicker.getCurrentHour();
                int minute = alarmTimePicker.getCurrentMinute();

                //convert int to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                }

                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                set_alarm_text("Alarma programada: " + hour_string + ":" + minute_string);

                //
                my_intent.putExtra("extra", "alarm on");


                //create a pending intent
                pending_intent = PendingIntent.getBroadcast(Alarma.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //set the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending_intent);
            }
        });*/

        Button alarm_off = (Button) findViewById(R.id.alarm_off);
        alarm_off.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                set_alarm_text("La Alarma fue apagada!");

                //alarmManager.cancel(pending_intent);

                // put extra string into my intent
                //tells the clock
                my_intent.putExtra("extra", "alarm off");

                // stop the ringtone
                sendBroadcast(my_intent);

                Intent i = new Intent(getApplicationContext(), MisMedicamentosActivity.class);
                startActivity(i);

            }
        });
    }

    private void set_alarm_text(String output) {
        updateText.setText(output);
    }

    //Botón atrás
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        Intent i = new Intent(getApplicationContext(), MisMedicamentosActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
