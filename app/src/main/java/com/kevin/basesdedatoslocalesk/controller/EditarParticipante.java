package com.kevin.basesdedatoslocalesk.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.juan.proyectcoop.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class EditarParticipante extends AppCompatActivity {

    public static final String EXTRA_ID = "ID_PARTICIPANTE";
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_TITULO = "TITULO_ACTIVIDAD";
    public static final String EXTRA_EMAIL = "EMAIL";

    private TextInputEditText etNombre, etTitulo, etEmail;
    private Button btnGuardar, btnCancelar;
    private ManagerDB managerDB;
    private int participanteId;
    private String emailOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_participante);

        // Inicializar vistas
        etNombre = findViewById(R.id.etNombre);
        etTitulo = findViewById(R.id.etTituloActividad);
        etEmail = findViewById(R.id.etEmail);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Obtener datos del intent
        Intent intent = getIntent();
        participanteId = intent.getIntExtra(EXTRA_ID, -1);
        String nombre = intent.getStringExtra(EXTRA_NOMBRE);
        String titulo = intent.getStringExtra(EXTRA_TITULO);
        String email = intent.getStringExtra(EXTRA_EMAIL);
        emailOriginal = email;

        // Rellenar campos
        etNombre.setText(nombre);
        etTitulo.setText(titulo);
        etEmail.setText(email);

        // Inicializar DB
        managerDB = new ManagerDB(this);

        // Listeners
        btnGuardar.setOnClickListener(v -> guardarCambios());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarCambios() {
        // Ocultar teclado para una mejor UX
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        String nombre = etNombre.getText().toString().trim();
        String titulo = etTitulo.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (!validarCampos(nombre, titulo, email)) return;

        // Verificar duplicidad del email si fue modificado
        if (!email.equals(emailOriginal) && managerDB.existeEmail(email)) {
            Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar participante
        boolean resultado = managerDB.actualizarParticipante(participanteId, nombre, titulo, email);

        if (resultado) {
            Toast.makeText(this, "Participante actualizado", Toast.LENGTH_SHORT).show();
            Intent resultIntent = new Intent();
            resultIntent.putExtra(EXTRA_ID, participanteId);
            resultIntent.putExtra(EXTRA_NOMBRE, nombre);
            resultIntent.putExtra(EXTRA_TITULO, titulo);
            resultIntent.putExtra(EXTRA_EMAIL, email);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarCampos(String nombre, String titulo, String email) {
        if (nombre.isEmpty() || titulo.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un email válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        managerDB.close();
        super.onDestroy();
    }
}
