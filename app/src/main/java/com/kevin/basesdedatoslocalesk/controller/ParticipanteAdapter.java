package com.kevin.basesdedatoslocalesk.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juan.proyectcoop.R;
import com.kevin.basesdedatoslocalesk.model.ManagerDB;
import java.util.List;

public class ParticipanteAdapter extends RecyclerView.Adapter<ParticipanteAdapter.ParticipanteViewHolder> {

    private List<Participante> listaParticipantes;
    private Context context;
    private ManagerDB managerDB;
    private OnParticipanteListener onParticipanteListener;

    // Interface para manejar clicks
    public interface OnParticipanteListener {
        void onParticipanteClick(int position);
        void onDeleteClick(int position);
    }

    public ParticipanteAdapter(List<Participante> listaParticipantes, Context context, OnParticipanteListener listener) {
        this.listaParticipantes = listaParticipantes;
        this.context = context;
        this.managerDB = new ManagerDB(context);
        this.onParticipanteListener = listener;
    }

    @NonNull
    @Override
    public ParticipanteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_participante, parent, false);
        return new ParticipanteViewHolder(view, onParticipanteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipanteViewHolder holder, int position) {
        Participante participante = listaParticipantes.get(position);

        // Configurar datos
        holder.tvNombre.setText(participante.getNombre());
        holder.tvTitulo.setText(participante.getTituloActividad());
        holder.tvEmail.setText(participante.getEmail());

        // Configurar botón eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            if (onParticipanteListener != null) {
                onParticipanteListener.onDeleteClick(position);
            }
        });

        // Configurar click en el item
        holder.itemView.setOnClickListener(v -> {
            if (onParticipanteListener != null) {
                onParticipanteListener.onParticipanteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaParticipantes.size();
    }

    // Método para actualizar la lista
    public void actualizarLista(List<Participante> nuevaLista) {
        listaParticipantes.clear();
        listaParticipantes.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    // Método para eliminar un participante
    public void eliminarParticipante(int position) {
        Participante participante = listaParticipantes.get(position);
        int resultado = managerDB.eliminarParticipante(participante.getId());

        if (resultado > 0) {
            listaParticipantes.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaParticipantes.size());
            Toast.makeText(context, "Participante eliminado", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
        }
    }

    // ViewHolder
    public static class ParticipanteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvTitulo, tvEmail;
        Button btnEliminar;

        public ParticipanteViewHolder(@NonNull View itemView, OnParticipanteListener listener) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTitulo = itemView.findViewById(R.id.tvTituloActividad);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}