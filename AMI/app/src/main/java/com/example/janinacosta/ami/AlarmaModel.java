package com.example.janinacosta.ami;

/**
 * Created by Sianna-chan on 18/01/2017.
 */

public class AlarmaModel {

    private int hora, min;

    public AlarmaModel(int hora, int min) {
        this.hora = hora;
        this.min = min;
    }

    public int getHora() {
        return hora;
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
