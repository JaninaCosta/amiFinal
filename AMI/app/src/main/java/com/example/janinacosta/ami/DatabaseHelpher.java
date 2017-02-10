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
    private static final int DATABASE_VERSION = 2;
    private static final String NAME_MEDICAMENTO = "medicamentos";
    private static final String NAME_ALARMA = "alarma";
    private static final String NAME_MEDI_ALARMA = "med_alarma";
    private static final String NAME_RECETA = "receta";

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

    /*Campos de la tabla Receta*/
    static final String idReceta="idReceta";
    static final String nombreReceta = "nombreReceta";
    /*
    static final String descripcion  = "descripcion";
    static final String fecha  = "fecha";
    static final String medico  = "medico";*/
    static final String urlFoto  = "urlFoto";

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

    /*Tabla de Receta*/
    private static final String RECETA_TABLE = "create table receta" +
            "(" + idReceta + " INTEGER primary key autoincrement, "
            + nombreReceta + " TEXT , "
            + urlFoto + " TEXT, "
            + "UNIQUE (idReceta))";


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
        db.execSQL(RECETA_TABLE);
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


    /** Insertar una alarma_medi **/
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

    /** Insertar una receta**/
    public void insert_receta(String nombreReceta, String urlFoto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreReceta", nombreReceta);
        values.put("urlFoto", urlFoto);
        db.insert("receta", null, values);
        db.close();
        Log.e("Receta:", "idReceta: "+idReceta+ " urlFoto: "+urlFoto );
        //Toast.makeText(context, "Alarma configurada con éxito", Toast.LENGTH_LONG);

    }

    /* Obtener todos los medicamentos de la base para el recycler */

    public ArrayList<MedicamentoModel> getDataFromDB(){
        ArrayList<MedicamentoModel> modelList = new ArrayList<MedicamentoModel>();
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

    public ArrayList<AlarmaModel> getTodasAlarmas(){
        ArrayList<AlarmaModel> modelList = new ArrayList<AlarmaModel>();
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

    /* Obtener todos las recetas de la base */

    public ArrayList<RecetaModel> getTodasRecetas(){
        ArrayList<RecetaModel> modelList = new ArrayList<RecetaModel>();
        String query = "select * from receta";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                RecetaModel model = new RecetaModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                modelList.add(model);
            }while (cursor.moveToNext());
        }
        return modelList;
    }

    //metodo para actualizar

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ NAME_ALARMA);
        db.execSQL("DROP TABLE IF EXISTS "+ NAME_MEDI_ALARMA);
        db.execSQL("DROP TABLE IF EXISTS "+ NAME_MEDICAMENTO);
        db.execSQL("DROP TABLE IF EXISTS "+ NAME_RECETA);
        onCreate(db);
    }



    /*eliminar una fila */

    /*public void deleteARow(String nombreMedicamento){
        SQLiteDatabase db= this.getWritableDatabase();
        db.delete(MEDICAMENTOS_TABLE, "name" + " = ?", new String[] { nombreMedicamento });
        db.close();
    }*/

    //Devuelve medicamento buscandolo por el ID
    public AlarmaModel get_alarma (int idAlarma){

        String query_alarma = "select * from alarma where idAlarma="+idAlarma;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_alarma,null);
        AlarmaModel model = new AlarmaModel(0,0,0);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            model = new AlarmaModel(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2));
        }
        Log.e("ALARMA UNICA: ", ""+idAlarma);
        return model;
    }

    //Devuelve medicamento buscandolo por el ID
    public RecetaModel get_Receta (int idReceta){

        String query_alarma = "select * from receta where idReceta="+idReceta;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_alarma,null);
        RecetaModel model = new RecetaModel(0,"","");
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            model = new RecetaModel(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
        }
        Log.e("Receta : ", ""+idReceta);
        return model;
    }

    //Obtener lista de alarma de un medicamento
    public ArrayList<AlarmaModel> lista_alarmas (int idMedi){

        Log.e("ID MEDICAMENTOO: ",""+idMedi);

        ArrayList<AlarmaModel> lista = new ArrayList<AlarmaModel>();
        String query_alarma = "select * from med_alarma where fkMedicamento="+idMedi;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_alarma,null);
        if (cursor.moveToFirst()){
            do {
                AlarmaModel model = get_alarma(cursor.getInt(1));
                lista.add(model);
            }while (cursor.moveToNext());
        }
        return lista;
    }

    //Alarmas del día

    public void actualizarUltimaRecetaFoto(String url){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("urlFoto", url);
        RecetaModel ultima = getTodasRecetas().get(getTodasRecetas().size()-1);
        Log.e("ID RECETA ULTIMA", "FOTO "+ultima.getIdReceta());
        db.update(NAME_RECETA, values, "idReceta='"+ultima.getIdReceta()+"'", null);


    }

    public void actualizarUltimaRecetaNombre(String nombre){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombreReceta", nombre);
        RecetaModel ultima = getTodasRecetas().get(getTodasRecetas().size()-1);
        Log.e("ID RECETA ULTIMA", "NOMBRE "+ultima.getIdReceta());
        db.update(NAME_RECETA, values, "idReceta='"+ultima.getIdReceta()+"'", null);

    }


}
