package com.example.inmobiliaria.ui.pago;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.modelo.Pago;

import java.util.ArrayList;
import java.util.List;

public class PagoAdapter extends RecyclerView.Adapter<PagoAdapter.VH> {
    private final List<Pago> data = new ArrayList<>();

    public void updateData(List<Pago> items){
        data.clear();
        if (items != null) data.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull @Override public VH onCreateViewHolder(@NonNull ViewGroup p, int vt){
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_pago, p, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH h, int pos){
        Pago p = data.get(pos);
        h.tvNro.setText("Pago N° " + p.getIdPago());
        h.tvFecha.setText("Fecha: " + (p.getFechaPago()!=null ? p.getFechaPago() : "-"));
        h.tvImporte.setText("Importe: $ " + p.getMonto());
    }

    @Override public int getItemCount(){ return data.size(); }

    static class VH extends RecyclerView.ViewHolder{
        final TextView tvNro, tvFecha, tvImporte;
        VH(@NonNull View v){
            super(v);
            tvNro = v.findViewById(R.id.tvNroPago);
            tvFecha = v.findViewById(R.id.tvFechaPago);
            tvImporte = v.findViewById(R.id.tvImportePago);
        }
    }
}
