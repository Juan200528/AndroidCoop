package com.kevin.basesdedatoslocalesk.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.kevin.basesdedatoslocalesk.controller.Participante;

public class ManagerDB {
    private DbHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public ManagerDB(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
    }

    // ==================== OPERACIONES BÁSICAS ====================
    public void openDbWr() {
        db = dbHelper.getWritableDatabase();
    }

    public void openDbRd() {
        db = dbHelper.getReadableDatabase();
    }

    public void close() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // ==================== OPERACIONES PARA PARTICIPANTES ====================
    public long insertParticipante(String nombre, String tituloActividad, String email) {
        openDbWr();
        if (existeEmail(email)) {
            Toast.makeText(context, "Error: Email ya registrado", Toast.LENGTH_SHORT).show();
            return -1;
        }

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("titulo_actividad", tituloActividad);
        valores.put("email", email);

        long resultado = db.insert("Participantes", null, valores);

        if (resultado == -1) {
            Toast.makeText(context, "Error al registrar participante", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Participante registrado", Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

    public boolean actualizarParticipante(int id, String nombre, String titulo, String email) {
        openDbWr();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("titulo_actividad", titulo);
        valores.put("email", email);

        int filasAfectadas = db.update(
                "Participantes",
                valores,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return filasAfectadas > 0;
    }

    public int eliminarParticipante(int id) {
        openDbWr();
        int filasAfectadas = db.delete("Participantes", "id = ?",
                new String[]{String.valueOf(id)});

        if (filasAfectadas > 0) {
            Toast.makeText(context, "Participante eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
        return filasAfectadas;
    }

    public Cursor obtenerTodosParticipantes() {
        openDbRd();
        return db.rawQuery("SELECT * FROM Participantes ORDER BY nombre ASC", null);
    }

    public Participante obtenerParticipantePorId(int id) {
        openDbRd();
        Cursor cursor = db.rawQuery("SELECT * FROM Participantes WHERE id = ?",
                new String[]{String.valueOf(id)});

        Participante participante = null;
        if (cursor.moveToFirst()) {
            participante = new Participante(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        cursor.close();
        return participante;
    }

    // ==================== MÉTODOS AUXILIARES PARA PARTICIPANTES ====================
    public boolean existeEmail(String email) {
        openDbRd();
        Cursor cursor = db.rawQuery(
                "SELECT email FROM Participantes WHERE email = ?",
                new String[]{email});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean existeParticipante(int id) {
        openDbRd();
        Cursor cursor = db.rawQuery(
                "SELECT id FROM Participantes WHERE id = ?",
                new String[]{String.valueOf(id)});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // ==================== OPERACIONES PARA USUARIOS ====================

    // Insertar un nuevo usuario
    public long insertUsuario(String nombre, String email, String password) {
        openDbWr();
        if (existeEmailUsuario(email)) {
            Toast.makeText(context, "Error: Email ya registrado para usuario", Toast.LENGTH_SHORT).show();
            return -1;
        }

        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("email", email);
        valores.put("password", password); // Asegúrate de hacer un hash a la contraseña antes de insertarla.

        long resultado = db.insert("Usuarios", null, valores);

        if (resultado == -1) {
            Toast.makeText(context, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Usuario registrado", Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

    // Verificar si el email ya está registrado en la tabla de usuarios
    public boolean existeEmailUsuario(String email) {
        openDbRd();
        Cursor cursor = db.rawQuery(
                "SELECT email FROM Usuarios WHERE email = ?",
                new String[]{email});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Obtener todos los usuarios
    public Cursor obtenerTodosUsuarios() {
        openDbRd();
        return db.rawQuery("SELECT * FROM Usuarios ORDER BY nombre ASC", null);
    }

    // Obtener un usuario por su ID
    public Cursor obtenerUsuarioPorId(int id) {
        openDbRd();
        return db.rawQuery("SELECT * FROM Usuarios WHERE id = ?", new String[]{String.valueOf(id)});
    }

    // Actualizar un usuario
    public boolean actualizarUsuario(int id, String nombre, String email, String password) {
        openDbWr();
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("email", email);
        valores.put("password", password);

        int filasAfectadas = db.update(
                "Usuarios",
                valores,
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        return filasAfectadas > 0;
    }

    // Eliminar un usuario
    public int eliminarUsuario(int id) {
        openDbWr();
        int filasAfectadas = db.delete("Usuarios", "id = ?",
                new String[]{String.valueOf(id)});

        if (filasAfectadas > 0) {
            Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
        return filasAfectadas;
    }
    // Método para validar el login de un usuario
    public boolean validarLogin(String email, String password) {
        openDbRd();
        Cursor cursor = db.rawQuery(
                "SELECT email, password FROM Usuarios WHERE email = ? AND password = ?",
                new String[]{email, password}
        );

        boolean isValid = false;

        if (cursor != null && cursor.getCount() > 0) {
            isValid = true;  // Las credenciales son correctas
        }

        if (cursor != null) {
            cursor.close();
        }

        return isValid;
    }

}

