package com.kevin.basesdedatoslocalesk.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, Constantes.NAME_BD, null, Constantes.NUM_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear las tablas Participantes y Usuarios
        db.execSQL(Constantes.SENTENCIA_TABLA_PARTICIPANTES); // Crear tabla de Participantes
        db.execSQL(Constantes.SENTENCIA_TABLA_USUARIOS); // Crear tabla de Usuarios
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas viejas si existen
        db.execSQL("DROP TABLE IF EXISTS Participantes");
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        onCreate(db); // Vuelve a crear las tablas
    }
}
