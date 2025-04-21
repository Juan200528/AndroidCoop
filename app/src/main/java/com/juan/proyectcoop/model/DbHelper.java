package com.juan.proyectcoop.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    // Constructor que recibe el contexto y pasa los parámetros necesarios al constructor de la clase padre.
    public Dbhelper(@Nullable Context context) {
        super(context, Constantes.NAME_DB, null, Constantes.NUM_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Crear la tabla Actividades
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Actividades (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "titulo TEXT, " +
                    "descripcion TEXT, " +
                    "fecha TEXT, " +
                    "lugar TEXT, " +
                    "responsables TEXT);";
            db.execSQL(createTableQuery); // Ejecutar la sentencia SQL para crear la tabla
        } catch (Exception e) {
            e.printStackTrace(); // Ver el error en Logcat si ocurre alguno
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la tabla si ya existe y volverla a crear para actualizar la base de datos
        db.execSQL("DROP TABLE IF EXISTS Actividades");
        onCreate(db); // Llamar a onCreate para crear la tabla nuevamente
    }
}
