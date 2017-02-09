package com.example.janinacosta.ami;

/**
 * Created by Sianna-chan on 08/02/2017.
 */
public class RecetaModel {

    private int idReceta;
    private String nombreReceta, urlFoto;

    public RecetaModel(int idReceta,String nombreReceta, String urlFoto) {
        this.idReceta=idReceta;
        this.nombreReceta = nombreReceta;
        this.urlFoto = urlFoto;
    }

    public int getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(int idReceta) {
        this.idReceta = idReceta;
    }

    public String getNombreReceta() {
        return nombreReceta;
    }

    public void setNombreReceta(String nombreReceta) {
        this.nombreReceta = nombreReceta;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
