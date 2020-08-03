package com.example.chatconversa;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MensajesAdapter extends RecyclerView.Adapter<MensajeViewHolder> implements View.OnClickListener {

    private Context myCtx;
    private List<Mensaje> mensajelist;
    private View.OnClickListener listener;

    public MensajesAdapter(Context myCtx, List<Mensaje> mensajelist) {
        this.myCtx = myCtx;
        this.mensajelist = mensajelist;
    }

    public void addMensaje(Mensaje m){
        mensajelist.add(0,m);
    }

    @NonNull
    @Override
    public MensajeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(myCtx).inflate(R.layout.recyclerview_layout,parent,false);
        v.setOnClickListener(this);
        return new MensajeViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MensajeViewHolder holder, int position) {
        holder.getNombre().setText(mensajelist.get(position).getUser().getUsername());
        holder.getMensaje().setText(mensajelist.get(position).getMessage());
        holder.getDate().setText(mensajelist.get(position).getDate());

        //FINAL
        if (!mensajelist.get(position).getUser().getUser_image().equals("")){
            Glide.with(myCtx)
                    .load(mensajelist.get(position).getUser().getUser_image())
                    .into(holder.getFotoPerfil());
        }


        if(!mensajelist.get(position).getImage().equals("")){
            holder.getFotomensaje().setVisibility(View.VISIBLE);
            holder.getFotomensaje().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog mydialog = new Dialog(v.getContext());
                    mydialog.setCancelable(true);

                    LayoutInflater li = LayoutInflater.from(myCtx);
                    View p = li.inflate(R.layout.customdialog,null);

                    ImageView image = p.findViewById(R.id.imagenengrande);

                    System.out.println("Click en imagen de: "+ mensajelist.get(position).getImage());
                    Glide.with(myCtx)
                            .load(mensajelist.get(position).getImage())
                            .into(image);
                    mydialog.setContentView(p);
                    mydialog.show();
                    System.out.println("Click en imagen de: "+ mensajelist.get(position).getImage());
                }
            });
            Glide.with(myCtx)
                    .load(mensajelist.get(position).getImage())
                    .into(holder.getFotomensaje());
        }else{
            holder.getFotomensaje().setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
            return mensajelist.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if (listener!=null){
            listener.onClick(v);
        }
    }
}
