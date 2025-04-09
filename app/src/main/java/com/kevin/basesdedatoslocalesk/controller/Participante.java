package com.kevin.basesdedatoslocalesk.controller;

public class Participante {
    private int id; // Asegúrate de que el ID sea un campo en la clase
    private String nombre;
    private String tituloActividad;
    private String email;

    // Constructor que acepta ID, nombre, título de actividad y email
    public Participante(int id, String nombre, String tituloActividad, String email) {
        this.id = id;
        this.nombre = nombre;
        this.tituloActividad = tituloActividad;
        this.email = email;
    }

    // Constructor sin ID, si lo necesitas
    public Participante(String nombre, String tituloActividad, String email) {
        this.nombre = nombre;
        this.tituloActividad = tituloActividad;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTituloActividad() {
        return tituloActividad;
    }

    public String getEmail() {
        return email;
    }
}
