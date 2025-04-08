package com.kevin.basesdedatoslocalesk.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.kevin.basesdedatoslocalesk.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class EditarParticipante extends AppCompatActivity {

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
        participanteId = intent.getIntExtra("ID_PARTICIPANTE", -1);
        String nombre = intent.getStringExtra("NOMBRE");
        String titulo = intent.getStringExtra("TITULO_ACTIVIDAD");
        String email = intent.getStringExtra("EMAIL");
        emailOriginal = email;

        // Rellenar campos
        etNombre.setText(nombre);
        etTitulo.setText(titulo);
        etEmail.setText(email);

        managerDB = new ManagerDB(this);

        // Configurar listeners
        btnGuardar.setOnClickListener(v -> guardarCambios());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void guardarCambios() {
        String nombre = etNombre.getText().toString().trim();
        String titulo = etTitulo.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (validarCampos(nombre, titulo, email)) {
            // Verificar si el email fue modificado
            if (!email.equals(emailOriginal)) {
                if (managerDB.existeEmail(email)) {
                    Toast.makeText(this, "El email ya está registrado", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            boolean resultado = managerDB.actualizarParticipante(participanteId, nombre, titulo, email);

            if (resultado) {
                Toast.makeText(this, "Participante actualizado", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("ID_PARTICIPANTE", participanteId);
                resultIntent.putExtra("NOMBRE", nombre);
                resultIntent.putExtra("TITULO_ACTIVIDAD", titulo);
                resultIntent.putExtra("EMAIL", email);
                setResult(RESULT_OK, resultIntent);  // Pasar el resultado de vuelta
                finish();
            } else {
                Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show();
            }
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