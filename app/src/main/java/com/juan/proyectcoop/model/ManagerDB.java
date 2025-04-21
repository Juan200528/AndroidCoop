package com.juan.proyectcoop.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ManagerDB {
    private final Dbhelper dbHelper;
    private SQLiteDatabase db;

    // Constructor de ManagerDB
    public ManagerDB(Context context) {
        dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase(); // Abrir la base de datos en modo escritura
    }

    // Método para insertar una actividad en la base de datos
    public long insertActividad(String titulo, String descripcion, String fecha, String lugar, String responsables) {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase(); // Abrir la base de datos si no está abierta
        }
        ContentValues valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descripcion", descripcion);
        valores.put("fecha", fecha);
        valores.put("lugar", lugar);
        valores.put("responsables", responsables);

        return db.insert("Actividades", null, valores); // Insertar los valores en la tabla
    }

    // Método para actualizar una actividad existente
    public int updateActividad(int id, String titulo, String descripcion, String fecha, String lugar, String responsables) {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase(); // Abrir la base de datos si no está abierta
        }
        ContentValues valores = new ContentValues();
        valores.put("titulo", titulo);
        valores.put("descripcion", descripcion);
        valores.put("fecha", fecha);
        valores.put("lugar", lugar);
        valores.put("responsables", responsables);

        return db.update("Actividades", valores, "id = ?", new String[]{String.valueOf(id)}); // Actualizar la actividad por su id
    }

    // Método para eliminar una actividad de la base de datos
    public int deleteActividad(int id) {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getWritableDatabase(); // Abrir la base de datos si no está abierta
        }
        return db.delete("Actividades", "id = ?", new String[]{String.valueOf(id)}); // Eliminar la actividad por su id
    }

    // Método para obtener todas las actividades de la base de datos
    public Cursor getAllActividades() {
        if (db == null || !db.isOpen()) {
            db = dbHelper.getReadableDatabase(); // Abrir la base de datos en modo lectura
        }
        return db.query("Actividades",
                new String[]{"id", "titulo", "descripcion", "fecha", "lugar", "responsables"},
                null, null, null, null, "titulo ASC"); // Consultar todas las actividades ordenadas por título
    }
}
