package com.juan.proyectcoop.controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.juan.proyect.R;

public class RedesSociales extends AppCompatActivity {

    private EditText descriptionEditText, linkEditText;
    private ImageButton facebookButton, instagramButton, publishButton;
    private String selectedPlatform = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redes_sociales);

        // Referencias a vistas
        descriptionEditText = findViewById(R.id.descriptionEditText);
        linkEditText = findViewById(R.id.linkEditText);
        facebookButton = findViewById(R.id.facebookButton);
        instagramButton = findViewById(R.id.instagramButton);
        publishButton = findViewById(R.id.publishButton);

        // Selección de red social
        facebookButton.setOnClickListener(v -> {
            selectedPlatform = "facebook";
            Toast.makeText(this, "Facebook seleccionado", Toast.LENGTH_SHORT).show();
        });

        instagramButton.setOnClickListener(v -> {
            selectedPlatform = "instagram";
            Toast.makeText(this, "Instagram seleccionado", Toast.LENGTH_SHORT).show();
        });

        // Publicar
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
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, content);

            // Seleccionar paquete según red
            if (selectedPlatform.equals("facebook")) {
                intent.setPackage("com.facebook.katana");
            } else if (selectedPlatform.equals("instagram")) {
                intent.setPackage("com.instagram.android");
            }

            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, selectedPlatform + " no está instalado", Toast.LENGTH_LONG).show();
            }
        });
    }
}
