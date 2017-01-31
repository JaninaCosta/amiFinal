package com.example.janinacosta.ami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class DatabaseHelpher extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="AppmedAlarma";
    private static final int DATABASE_VERSION = 1;
    //private static final String MEDICAMENTOS_TABLE = "medicamentos";

    /*Campos de la tabla Medicamentos*/
    static final String idMedicamento="idMedicamento";
    static final String Name="name";
    static final String NumDias = "num_dias";
    static final String Dosis = "dosis";
    static final String Indicaciones ="Indicaciones";
    static final String Frecuencia = "frecuencia";

    /*Campos de la tabla Alarma*/
    static final String idAlarma="idAlarma";
    static final String hora = "hora";
    static final String min  = "min";

    /*Campos de la tabla Alarma_Medicamento*/
    static final String idmed_alarma="idmed_alarma";
    static final String fkAlarma = "fkAlarma";
    static final String fkMedicamento  = "fkMedicamento";

    Context context;

    /*Tabla de Medicamenos*/
    private static final String MED_TABLE = "create table medicamentos" +
            "(" + idMedicamento + " INTEGER primary key autoincrement, "
            + Name + " TEXT , "
            + NumDias + " INT, "
            + Dosis + " INT, "
            + Indicaciones + " TEXT,"
            + Frecuencia + " INT,"
            + "UNIQUE (idMedicamento))";

    /*private static final String MED_TABLE = "create table "+MEDICAMENTOS_TABLE +
            "(name TEXT primary key, num_dias INT, dosis TEXT, indicaciones TEXT)";*/

    /*Tabla de Alarma*/
    private static final String ALARMA_TABLE = "create table alarma" +
            "("+idAlarma  + " INTEGER primary key autoincrement, "
            + hora + " INT, "
            + min + " INT, "
            + "UNIQUE (idAlarma))";

    /*Tabla de Medicamento-Alarma*/
    private static final String MED_ALARMA_TABLE = "create table med_alarma" +
            "("+idmed_alarma  + " INTEGER primary key autoincrement, "
            + fkAlarma + " INTEGER, "
            + fkMedicamento + " INTEGER, "
            + "UNIQUE (idmed_alarma))";


    //constructor de la clase
    public DatabaseHelpher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MED_TABLE);
        db.execSQL(ALARMA_TABLE);
        db.execSQL(MED_ALARMA_TABLE);
    }


    /***METODO PARA INSERTAR EN LA BDD*/
    public void insert_medicamento(String nombre, int num_dias , int dosis, String indicaciones, int frecuencia){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nombre);
        values.put("num_dias", num_dias);
        values.put("dosis", dosis);
        values.put("indicaciones", indicaciones);
        values.put("frecuencia",frecuencia);
        db.insert("medicamentos", null, values);
        db.close();
        Toast.makeText(context, nombre+" creado con éxito", Toast.LENGTH_LONG);

    }
    /** Insertar una alarma **/
    public void insert_alarma(int hora , int min){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hora", hora);
        values.put("min", min);
        db.insert("alarma", null, values);
        db.close();
        Log.e("ALARMA", "Alarma: "+hora+":"+min );
        //Toast.makeText(context, "Alarma configurada con éxito", Toast.LENGTH_LONG);

    }


    /** Insertar una alarma **/
    public void insert_alarma_medi(int fk_alarma , int fk_medi){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("fkAlarma", fk_alarma);
        values.put("fkMedicamento", fk_medi);
        db.insert("med_alarma", null, values);
        db.close();
        Log.e("ALARMA_MEDI", "fk_alarma: "+fk_alarma+ " fk_medi: "+fk_medi );
        //Toast.makeText(context, "Alarma configurada con éxito", Toast.LENGTH_LONG);

    }


    /* Obtener todos los medicamentos de la base para el recycler */

    public List<MedicamentoModel> getDataFromDB(){
        List<MedicamentoModel> modelList = new ArrayList<MedicamentoModel>();
        String query = "select * from medicamentos";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                MedicamentoModel model = new MedicamentoModel(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4),Integer.valueOf(cursor.getString(5)));
                //model.setName(cursor.getString(0));
                //model.setNum_dias(cursor.getInt(1));
                //model.setDosis(cursor.getInt(2));
                //model.setIndicaciones(cursor.getString(3));

                modelList.add(model);
            }while (cursor.moveToNext());
        }

        return modelList;
    }


    /* Obtener todos los medicamentos de la base para el recycler */

    public List<AlarmaModel> getTodasAlarmas(){
        List<AlarmaModel> modelList = new ArrayList<AlarmaModel>();
        String query = "select * from alarma";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                AlarmaModel model = new AlarmaModel(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2));
                modelList.add(model);
            }while (cursor.moveToNext());
        }

        return modelList;
    }

    //metodo para actualizar

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MED_TABLE);
        onCreate(db);
    }



    /*eliminar una fila */

    /*public void deleteARow(String nombreMedicamento){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(MEDICAMENTOS_TABLE, "name" + " = ?", new String[] { nombreMedicamento });
        db.close();
    }*/

}
