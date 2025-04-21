package com.juan.proyectcoop.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.juan.proyectcoop.R;
import com.juan.proyectcoop.model.ManagerDB;

public class lista_actividadesActivity extends AppCompatActivity {

    private ManagerDB managerDB;
    private LinearLayout layoutActividades;
    private Button btnNuevaActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividades);

        managerDB = new ManagerDB(this);
        layoutActividades = findViewById(R.id.layoutActividades);
        btnNuevaActividad = findViewById(R.id.btnNuevaActividad);

        btnNuevaActividad.setOnClickListener(v -> {
            startActivity(new Intent(this, crear_actividadActivity.class));
        });

        cargarActividades();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarActividades();
    }

    private void cargarActividades() {
        layoutActividades.removeAllViews();

        Cursor cursor = managerDB.getAllActividades();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String titulo = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String fecha = cursor.getString(3);
                String lugar = cursor.getString(4);
                String responsables = cursor.getString(5);

                View actividadView = getLayoutInflater().inflate(R.layout.activity_item_actividad, null);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(18, 19, 18, 14);
                actividadView.setLayoutParams(params);

                TextView tvNombre = actividadView.findViewById(R.id.tvNombre);
                TextView tvTituloActividad = actividadView.findViewById(R.id.tvTituloActividad);
                TextView tvEmail = actividadView.findViewById(R.id.tvEmail);
                Button btnVerDetalles = actividadView.findViewById(R.id.btnVerDetalles);
                ImageButton btnEditar = actividadView.findViewById(R.id.btnEditar);
                ImageButton btnEliminar = actividadView.findViewById(R.id.btneliminar);
                ImageButton btnCompartir = actividadView.findViewById(R.id.btnCompartir);
                Switch switchPromocion = actividadView.findViewById(R.id.switchPromocion);
                ImageView iconoAgregar = actividadView.findViewById(R.id.iconoAgregarAsistentes);

                tvNombre.setText(titulo);
                tvEmail.setText("Lugar: " + lugar + " | Fecha: " + fecha);

                btnVerDetalles.setOnClickListener(v -> {
                    Intent intent = new Intent(this, item_actividadActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("descripcion", descripcion);
                    intent.putExtra("fecha", fecha);
                    intent.putExtra("lugar", lugar);
                    intent.putExtra("responsables", responsables);
                    startActivity(intent);
                });

                btnEditar.setOnClickListener(v -> {
                    Intent intent = new Intent(this, crear_actividadActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("descripcion", descripcion);
                    intent.putExtra("fecha", fecha);
                    intent.putExtra("lugar", lugar);
                    intent.putExtra("responsables", responsables);
                    startActivity(intent);
                });

                btnEliminar.setOnClickListener(v -> {
                    int resultado = managerDB.deleteActividad(id);
                    if (resultado > 0) {
                        Toast.makeText(this, "Actividad eliminada", Toast.LENGTH_SHORT).show();
                        cargarActividades();
                    } else {
                        Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });

                btnCompartir.setOnClickListener(v -> {
                    Intent intent = new Intent(this, RedesSociales.class);
                    intent.putExtra("titulo", titulo);
                    intent.putExtra("descripcion", descripcion);
                    intent.putExtra("fecha", fecha);
                    intent.putExtra("lugar", lugar);
                    intent.putExtra("responsables", responsables);
                    startActivity(intent);
                });

                switchPromocion.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        Toast.makeText(this, "Promocionando: " + titulo, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Promoción desactivada: " + titulo, Toast.LENGTH_SHORT).show();
                    }
                });

                layoutActividades.addView(actividadView);

            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
