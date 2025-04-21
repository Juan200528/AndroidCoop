package com.juan.proyectcoop.model;

public class Constantes {
    public static String NAME_DB = "ejemplodb";
    public static int NUM_VERSION = 1;

    public static String SENTENCIA_TABLE = "CREATE TABLE Ciudad (codigo INTEGER, nombre TEXT)";
    public static String SENTENCIA_TABLA2 = "CREATE TABLE Departamento (codigo INTEGER, nombre TEXT)";

    public static String SENTENCIA_PUBLICACION = "CREATE TABLE Publicacion (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "descripcion TEXT, " +
            "enlace TEXT, " +
            "red_social TEXT)";
}
