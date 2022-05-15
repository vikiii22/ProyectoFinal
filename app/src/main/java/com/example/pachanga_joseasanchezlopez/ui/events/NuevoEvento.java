package com.example.pachanga_joseasanchezlopez.ui.events;

import com.google.firebase.firestore.FirebaseFirestore;

public class NuevoEvento {
    public String fecha;
    public String lugar;
    public String nombre;

    public NuevoEvento(){

    }

    public NuevoEvento(String fecha, String lugar, String nombre) {
        this.fecha = fecha;
        this.lugar = lugar;
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
