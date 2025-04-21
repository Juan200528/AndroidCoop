package com.juan.proyectcoop.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.juan.proyectcoop.R;

public class RedesSociales extends AppCompatActivity {

    private EditText descriptionEditText, linkEditText;
    private ImageButton facebookButton, instagramButton, publishButton;
    private String selectedPlatform = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_sociales);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        linkEditText = findViewById(R.id.linkEditText);
        facebookButton = findViewById(R.id.facebookButton);
        instagramButton = findViewById(R.id.instagramButton);
        publishButton = findViewById(R.id.publishButton);

        // Recuperar datos de la actividad compartida
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        String descripcion = intent.getStringExtra("descripcion");
        String fecha = intent.getStringExtra("fecha");
        String lugar = intent.getStringExtra("lugar");
        String responsables = intent.getStringExtra("responsables");

        descriptionEditText.setText(titulo + "\n" + descripcion);
        linkEditText.setText("Lugar: " + lugar + " | Fecha: " + fecha + "\nResponsables: " + responsables);

        facebookButton.setOnClickListener(v -> {
            selectedPlatform = "facebook";
            Toast.makeText(this, "Facebook seleccionado", Toast.LENGTH_SHORT).show();
        });

        instagramButton.setOnClickListener(v -> {
            selectedPlatform = "instagram";
            Toast.makeText(this, "Instagram seleccionado", Toast.LENGTH_SHORT).show();
        });

        publishButton.setOnClickListener(v -> {
            String description = descriptionEditText.getText().toString().trim();
            String link = linkEditText.getText().toString().trim();

            if (selectedPlatform == null) {
                Toast.makeText(this, "Selecciona una red social", Toast.LENGTH_SHORT).show();
                return;
            }

            if (description.isEmpty() || link.isEmpty()) {
                Toast.makeText(this, "Completa la descripción y el enlace", Toast.LENGTH_SHORT).show();
                return;
            }

            String content = description + "\n" + link;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, content);

            try {
                if (selectedPlatform.equals("facebook")) {
                    shareIntent.setPackage("com.facebook.katana");
                } else if (selectedPlatform.equals("instagram")) {
                    shareIntent.setPackage("com.instagram.android");
                }
                startActivity(shareIntent);
            } catch (Exception e) {
                Toast.makeText(this, selectedPlatform + " no está instalado", Toast.LENGTH_LONG).show();
            }
        });
    }
}
