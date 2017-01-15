package com.example.janinacosta.ami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class DatabaseHelpher extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="mediAlarmas";
    private static final int DATABASE_VERSION = 1;
    //private static final String MEDICAMENTOS_TABLE = "medicamentos";

    /*Campos de la tabla Medicamentos*/
    static final String Name="name";
    static final String NumDias = "num_dias";
    static final String Dosis = "dosis";
    static final String Indicaciones ="Indicaciones";
    Context context;

    /*Tabla de Medicamenos*/
    private static final String MED_TABLE = "create table medicamentos" +
            "(" + Name + " TEXT primary key, "
            + NumDias + " INT, "
            + Dosis + " INT, "
            + Indicaciones + " TEXT)";

    /*private static final String MED_TABLE = "create table "+MEDICAMENTOS_TABLE +
            "(name TEXT primary key, num_dias INT, dosis TEXT, indicaciones TEXT)";*/


    //constructor de la clase
    public DatabaseHelpher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MED_TABLE);
    }


    /***METODO PARA INSERTAR EN LA BDD*/
    public void insertIntoDB(String nombre, int num_dias , int dosis, String indicaciones){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", nombre);
        values.put("num_dias", num_dias);
        values.put("dosis", dosis);
        values.put("indicaciones", indicaciones);
        db.insert("medicamentos", null, values);
        db.close();
        Toast.makeText(context, nombre+" creado con éxito", Toast.LENGTH_LONG);

    }
    /* Obtener todos los medicamentos de la base para el recycler */

    public List<MedicamentoModel> getDataFromDB(){
        List<MedicamentoModel> modelList = new ArrayList<MedicamentoModel>();
        String query = "select * from medicamentos";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                MedicamentoModel model = new MedicamentoModel(cursor.getString(0),cursor.getInt(1),cursor.getInt(2),cursor.getString(3),"");
                //model.setName(cursor.getString(0));
                //model.setNum_dias(cursor.getInt(1));
                //model.setDosis(cursor.getInt(2));
                //model.setIndicaciones(cursor.getString(3));

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
