package com.example.chatconversa;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MensajeViewHolder extends RecyclerView.ViewHolder {


        private ImageView fotoPerfil;
        private ImageView fotomensaje;
        private TextView nombre;
        private TextView date;
        private TextView mensaje;

        public MensajeViewHolder(@NonNull View itemView) {
            super(itemView);

            fotomensaje = itemView.findViewById(R.id.imagenmensaje);
            fotoPerfil = itemView.findViewById(R.id.fotomensaje);
            nombre = itemView.findViewById(R.id.nombremensaje);
            date = itemView.findViewById(R.id.datemensaje);
            mensaje = itemView.findViewById(R.id.mensajemensaje);

        }

    public ImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public ImageView getFotomensaje() {
        return fotomensaje;
    }

    public void setFotomensaje(ImageView fotomensaje) {
        this.fotomensaje = fotomensaje;
    }

    public void setFotoPerfil(ImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getNombre() {
        return nombre;
    }

    public void setNombre(TextView nombre) {
        this.nombre = nombre;
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
    }

    public TextView getMensaje() {
        return mensaje;
    }

    public void setMensaje(TextView mensaje) {
        this.mensaje = mensaje;
    }


}
