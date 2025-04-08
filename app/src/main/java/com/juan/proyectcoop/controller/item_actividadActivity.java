package com.juan.proyectcoop.controller;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.juan.proyectcoop.R; // ✅ Import correcto

public class item_actividadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_actividad); // Asegúrate de que este XML exista en res/layout
    }
}
