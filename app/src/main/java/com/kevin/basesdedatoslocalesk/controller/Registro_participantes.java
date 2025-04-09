package com.kevin.basesdedatoslocalesk.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.juan.proyectcoop.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;

public class Registro_participantes extends AppCompatActivity {
    private TextInputEditText etNombreParticipante;
    private TextInputEditText etTituloActividad;
    private TextInputEditText etEmail;
    private Button btnRegistrar;
    private Button btnListaParticipantes; // Botón para ver la lista
    private ManagerDB managerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_participantes);

        etNombreParticipante = findViewById(R.id.etNombreParticipante);
        etTituloActividad = findViewById(R.id.etTituloActividad);
        etEmail = findViewById(R.id.etEmail);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnListaParticipantes = findViewById(R.id.btnListaParticipantes); // Inicializa el botón

        managerDB = new ManagerDB(this);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarParticipante();
            }
        });

        btnListaParticipantes.setOnClickListener(new View.OnClickListener() { // Maneja el clic en el botón
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registro_participantes.this, ListaParticipantes.class);
                startActivity(intent); // Inicia la actividad de la lista de participantes
            }
        });
    }

    private void registrarParticipante() {
        String nombre = etNombreParticipante.getText().toString().trim();
        String tituloActividad = etTituloActividad.getText().toString().trim();
        String email = etEmail.getText().toString().trim();

        if (nombre.isEmpty() || tituloActividad.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        long resultado = managerDB.insertParticipante(nombre, tituloActividad, email);
        if (resultado == -1) {
            Toast.makeText(this, "Error al registrar el participante", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Participante registrado con éxito", Toast.LENGTH_SHORT).show();

            etNombreParticipante.setText("");
            etTituloActividad.setText("");
            etEmail.setText("");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        managerDB.close();
    }
}
