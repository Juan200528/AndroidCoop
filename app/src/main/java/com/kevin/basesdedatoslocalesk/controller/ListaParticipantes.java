package com.kevin.basesdedatoslocalesk.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juan.proyectcoop.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;
import java.util.ArrayList;
import java.util.List;

public class ListaParticipantes extends AppCompatActivity implements ParticipanteAdapter.OnParticipanteListener {

    private static final int REQUEST_CODE_EDITAR = 1;

    private RecyclerView rvParticipantes;
    private ParticipanteAdapter participanteAdapter;
    private ManagerDB managerDB;
    private List<Participante> listaParticipantes;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);

        rvParticipantes = findViewById(R.id.rvParticipantes);
        btnVolver = findViewById(R.id.btnVolver);

        managerDB = new ManagerDB(this);
        listaParticipantes = new ArrayList<>();

        cargarParticipantes();

        btnVolver.setOnClickListener(v -> finish());
    }

    @SuppressLint("Range")
    private void cargarParticipantes() {
        listaParticipantes.clear();
        Cursor cursor = managerDB.obtenerTodosParticipantes();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                listaParticipantes.add(new Participante(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("titulo_actividad")),
                        cursor.getString(cursor.getColumnIndex("email"))
                )); // Paréntesis de cierre correctamente colocados
            } while (cursor.moveToNext());
            cursor.close();
        }

        participanteAdapter = new ParticipanteAdapter(listaParticipantes, this, this);
        rvParticipantes.setLayoutManager(new LinearLayoutManager(this));
        rvParticipantes.setAdapter(participanteAdapter);
    }

    @Override
    public void onParticipanteClick(int position) {
        Participante participante = listaParticipantes.get(position);
        Intent intent = new Intent(this, EditarParticipante.class);
        intent.putExtra("ID_PARTICIPANTE", participante.getId());
        intent.putExtra("NOMBRE", participante.getNombre());
        intent.putExtra("TITULO_ACTIVIDAD", participante.getTituloActividad());
        intent.putExtra("EMAIL", participante.getEmail());
        startActivityForResult(intent, REQUEST_CODE_EDITAR);
    }


    @Override
    public void onDeleteClick(int position) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar participante")
                .setMessage("¿Estás seguro?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    if (managerDB.eliminarParticipante(listaParticipantes.get(position).getId()) > 0) {
                        listaParticipantes.remove(position);
                        participanteAdapter.notifyItemRemoved(position);
                        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDITAR && resultCode == RESULT_OK) {
            cargarParticipantes(); // Refrescar lista después de editar
        }
    }

    @Override
    protected void onDestroy() {
        managerDB.close();
        super.onDestroy();
    }
}