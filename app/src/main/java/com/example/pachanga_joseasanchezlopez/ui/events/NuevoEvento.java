package com.example.pachanga_joseasanchezlopez.ui.events;

import com.google.firebase.firestore.FirebaseFirestore;

public class NuevoEvento {
    public String fecha;
    public String lugar;
    public String nombre;
    public String hora;
    public Boolean privado;
    public String creador;

    public NuevoEvento(String fecha, String lugar, String nombre, String hora, Boolean privado) {
        this.fecha = fecha;
        this.lugar = lugar;
        this.nombre = nombre;
        this.hora = hora;
        this.privado = privado;
    }

    public NuevoEvento(String fecha, String lugar, String nombre, String hora, Boolean privado, String creador) {
        this.fecha = fecha;
        this.lugar = lugar;
        this.nombre = nombre;
        this.hora = hora;
        this.privado = privado;
        this.creador = creador;
    }

    public NuevoEvento() {

    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public Boolean getPrivado() {
        return privado;
    }

    public void setPrivado(Boolean privado) {
        this.privado = privado;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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
