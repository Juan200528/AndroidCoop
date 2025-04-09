package com.juan.proyectcoop.controller;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.juan.proyectcoop.R;
import com.juan.proyectcoop.model.ManagerDB;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class crear_actividadActivity extends AppCompatActivity {

    private int idActividad = -1;
    private ManagerDB managerDB;
    private EditText etTitulo, etDescripcion, etFecha, etLugar, etResponsables;
    private Button btnCrear;
    private ImageButton btnFecha;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_actividad);

        managerDB = new ManagerDB(this);
        calendar = Calendar.getInstance();

        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etFecha = findViewById(R.id.etFecha);
        etLugar = findViewById(R.id.etLugar);
        etResponsables = findViewById(R.id.etResponsables);
        btnCrear = findViewById(R.id.btnCrear);
        btnFecha = findViewById(R.id.btnFecha);

        // Configurar el selector de fecha
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateFecha();
            }
        };

        View.OnClickListener fechaClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(crear_actividadActivity.this, dateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        };

        etFecha.setOnClickListener(fechaClickListener);
        btnFecha.setOnClickListener(fechaClickListener);

        // Configurar el click listener para el botón
        btnCrear.setOnClickListener(v -> crearActividad());

        // Verificar si estamos editando
        Intent intent = getIntent();
        if (intent.hasExtra("id")) {
            idActividad = intent.getIntExtra("id", -1);
            etTitulo.setText(intent.getStringExtra("titulo"));
            etDescripcion.setText(intent.getStringExtra("descripcion"));
            etFecha.setText(intent.getStringExtra("fecha"));
            etLugar.setText(intent.getStringExtra("lugar"));
            etResponsables.setText(intent.getStringExtra("responsables"));
            btnCrear.setText("Actualizar");
        }
    }

    private void updateFecha() {
        String formato = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        etFecha.setText(sdf.format(calendar.getTime()));
    }

    private void crearActividad() {
        String titulo = etTitulo.getText().toString().trim();
        String descripcion = etDescripcion.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String lugar = etLugar.getText().toString().trim();
        String responsables = etResponsables.getText().toString().trim();

        if (!titulo.isEmpty()) {
            long resultado;
            if (idActividad == -1) {
                resultado = managerDB.insertActividad(titulo, descripcion, fecha, lugar, responsables);
            } else {
                resultado = managerDB.updateActividad(idActividad, titulo, descripcion, fecha, lugar, responsables);
            }

            if (resultado > 0) {
                Toast.makeText(this,
                        idActividad == -1 ? "Actividad creada" : "Actividad actualizada",
                        Toast.LENGTH_SHORT).show();

                // Ir a la lista después de guardar
                Intent intent = new Intent(crear_actividadActivity.this, lista_actividadesActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "El título es obligatorio", Toast.LENGTH_SHORT).show();
        }
    }
}
