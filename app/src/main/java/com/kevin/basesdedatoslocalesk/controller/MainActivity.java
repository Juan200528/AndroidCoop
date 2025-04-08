package com.kevin.basesdedatoslocalesk.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kevin.basesdedatoslocalesk.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class MainActivity extends AppCompatActivity {

    ManagerDB managerDB;
    EditText etNombre, etTitulo, etEmail;
    Button btnInsertar, btnActualizar, btnEliminar;
    TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_participantes);

        managerDB = new ManagerDB(MainActivity.this);
        etNombre = findViewById(R.id.etNombreParticipante);  // Changed from tvTituloActividad
        etTitulo = findViewById(R.id.etTituloActividad);    // Changed from etTitulo
        etEmail = findViewById(R.id.etEmail);                // This one matches
        btnInsertar = findViewById(R.id.btnRegistrar);

        // Acción del botón Insertar
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString().trim();
                String titulo = etTitulo.getText().toString().trim();
                String email = etEmail.getText().toString().trim();

                if (!nombre.isEmpty() && !titulo.isEmpty() && !email.isEmpty()) {
                    long resultado = managerDB.insertParticipante(nombre, titulo, email);

                    if (resultado > 0) {
                        tvResultado.setText("Participante insertado con éxito.");
                        limpiarCampos();
                    } else {
                        tvResultado.setText("Error al insertar el participante.");
                    }
                } else {
                    tvResultado.setText("Por favor, completa todos los campos.");
                }
            }
        });

        // Acción del botón Actualizar (necesitarías añadir un campo para el ID)
        // Acción del botón Eliminar (necesitarías añadir un campo para el ID)
    }

    private void limpiarCampos() {
        etNombre.setText("");
        etTitulo.setText("");
        etEmail.setText("");
    }
}