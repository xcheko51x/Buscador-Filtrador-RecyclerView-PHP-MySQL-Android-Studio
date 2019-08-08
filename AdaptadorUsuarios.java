package com.xcheko51x.buscadorrecyclerviewmysql;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.UsuarioViewHolder> {

    Context context;
    List<Usuario> listaUsuarios;

    public AdaptadorUsuarios(Context context, List<Usuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rv_usuario, viewGroup, false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder usuarioViewHolder, int i) {
        usuarioViewHolder.tvIdUsuario.setText(listaUsuarios.get(i).getIdUsuario());
        usuarioViewHolder.tvNombre.setText(listaUsuarios.get(i).getNombre());
        usuarioViewHolder.tvTelefono.setText(listaUsuarios.get(i).getTelefono());
        usuarioViewHolder.tvEmail.setText(listaUsuarios.get(i).getEmail());
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView tvIdUsuario, tvNombre, tvTelefono, tvEmail;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdUsuario = itemView.findViewById(R.id.tvIdUsuario);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvTelefono = itemView.findViewById(R.id.tvTelefono);
            tvEmail = itemView.findViewById(R.id.tvEmail);
        }
    }

    public void filtrar(ArrayList<Usuario> filtroUsuarios) {
        this.listaUsuarios = filtroUsuarios;
        notifyDataSetChanged();
    }
}
