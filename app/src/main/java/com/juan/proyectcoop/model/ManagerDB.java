package com.juan.proyectcoop.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ManagerDB {
    private Dbhelper dbHelper;
    private SQLiteDatabase db;

    public ManagerDB(Context context) {
        dbHelper = new Dbhelper(context);
        db = dbHelper.getWritableDatabase(); // Abre la base de datos en modo escritura
    }

    // Insertar una nueva publicación
    public long insertarPublicacion(String descripcion, String enlace, String redSocial) {
        ContentValues valores = new ContentValues();
        valores.put("descripcion", descripcion);
        valores.put("enlace", enlace);
        valores.put("red_social", redSocial);
        return db.insert("Publicacion", null, valores);
    }

    // Cerrar la base de datos correctamente
    public void closeDb() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
