package com.example.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.modelo.Inmuebles;
import com.example.inmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.List;

public class InmuebleContratoAdapter extends RecyclerView.Adapter<InmuebleContratoAdapter.VH> {

    private final List<Inmuebles> lista;
    public InmuebleContratoAdapter(List<Inmuebles> l){
        this.lista = l!=null? l : new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext()).inflate(R.layout.fragment_card, p, false));
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos) {
        Inmuebles i = lista.get(pos);

        h.tvDireccion.setText(i.getDireccion());
        h.tvTipo.setText(i.getTipo());
        h.tvPrecio.setText(String.valueOf(i.getValor()));


        Glide.with(h.itemView.getContext())
                .load(ApiClient.URLBASE + i.getImagen())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(h.imgInmueble);

        h.cardView.setOnClickListener(v -> {
            Bundle b = new Bundle();
            b.putInt("idInmueble", i.getIdInmueble());
            Navigation.findNavController(v).navigate(R.id.contratoFragment, b);
        });
    }

    @Override public int getItemCount(){ return lista.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvDireccion, tvTipo, tvPrecio; ImageView imgInmueble; CardView cardView;
        VH(@NonNull View item){
            super(item);
            tvDireccion = item.findViewById(R.id.tvDireccion);
            tvTipo = item.findViewById(R.id.tvTipo);
            tvPrecio = item.findViewById(R.id.tvPrecio);
            imgInmueble = item.findViewById(R.id.imgInmueble);
            cardView = item.findViewById(R.id.idCard);
        }
    }
}

