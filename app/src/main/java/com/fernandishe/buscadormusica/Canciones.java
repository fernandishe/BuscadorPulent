package com.fernandishe.buscadormusica;

import android.app.Activity;

import java.util.List;

/**
 * Created by andres on 04/10/15.
 */
public class Canciones {
    String name;
    String description;
    String urlImagen;
    List detalleMusica;
    Activity activity;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public List getDetalleMusica() {
        return detalleMusica;
    }

    public void setDetalleMusica(List detalleMusica) {
        this.detalleMusica = detalleMusica;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    Canciones(String name, String description, String urlImagen, List detalleMusica, Activity activity){
        this.name = name;
        this.description = description;
        this.urlImagen = urlImagen;
        this.detalleMusica = detalleMusica;
        this.activity = activity;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
