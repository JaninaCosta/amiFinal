package com.example.janinacosta.ami;

import java.io.Serializable;

/**
 * Created by Janina Costa on 3/1/2017.
 */

public class MedicamentoModel implements Serializable {
    private int id_medi;
    private String name;
    private int num_dias;
    private int dosis;
    private String indicaciones;
    private int frecuencia;

    public MedicamentoModel(int id_medi,String name, int num_dias, int dosis, String indicaciones, int frecuencia) {
        this.id_medi=id_medi;
        this.name = name;
        this.num_dias = num_dias;
        this.dosis = dosis;
        this.indicaciones = indicaciones;
        this.frecuencia = frecuencia;
    }

    public int getId_medi() {
        return id_medi;
    }

    public void setId_medi(int id_medi) {
        this.id_medi = id_medi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum_dias() {
        return num_dias;
    }

    public void setNum_dias(int num_dias) {
        this.num_dias = num_dias;
    }

    public int getDosis() {
        return dosis;
    }

    public void setDosis(int dosis) {
        this.dosis = dosis;
    }

    public String getIndicaciones() {
        return indicaciones;
    }

    public void setIndicaciones(String indicaciones) {
        this.indicaciones = indicaciones;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }
}
