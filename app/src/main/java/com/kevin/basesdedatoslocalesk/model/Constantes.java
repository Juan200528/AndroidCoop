package com.kevin.basesdedatoslocalesk.model;

public class Constantes {
    public static final String NAME_BD = "panascoopDB";
    public static final int NUM_VERSION = 1;

    // Sentencia para crear la tabla de Participantes
    public static final String SENTENCIA_TABLA_PARTICIPANTES = "CREATE TABLE Participantes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "titulo_actividad TEXT," +
            "email TEXT)";

    // Sentencia para crear la tabla de Usuarios
    public static final String SENTENCIA_TABLA_USUARIOS = "CREATE TABLE Usuarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "email TEXT," +
            "password TEXT)";
}
