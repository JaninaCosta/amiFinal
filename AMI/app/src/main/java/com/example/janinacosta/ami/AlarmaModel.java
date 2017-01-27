package com.example.janinacosta.ami;

/**
 * Created by Sianna-chan on 18/01/2017.
 */

public class AlarmaModel {

    private int id_alarma,hora, min;

    public AlarmaModel(int id_alarma,int hora, int min) {
        this.id_alarma=id_alarma;
        this.hora = hora;
        this.min = min;
    }

    public int getHora() {
        return hora;
    }

    public int getId_alarma() {
        return id_alarma;
    }

    public void setId_alarma(int id_alarma) {
        this.id_alarma = id_alarma;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }
}
