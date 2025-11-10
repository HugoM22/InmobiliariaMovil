package com.example.inmobiliaria.ui.Inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentDetalleInmuebleBinding;
import com.example.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.example.inmobiliaria.request.ApiClient;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mViewModel;
    private FragmentDetalleInmuebleBinding binding;
    public static DetalleInmuebleFragment newInstance() {
        return new DetalleInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);

        mViewModel.getInmueble().observe(getViewLifecycleOwner(), inmuebles -> {
            binding.tvIdInmueble.setText(inmuebles.getIdInmueble()+"");
            binding.tvDireccionI.setText(inmuebles.getDireccion());
            binding.tvTipoI.setText(inmuebles.getTipo());
            binding.tvUsoI.setText(inmuebles.getUso());
            binding.tvAmbientesI.setText(inmuebles.getAmbientes()+"");
            binding.tvSuperficieI.setText(inmuebles.getSuperficie()+" m²");
            binding.tvValorI.setText(inmuebles.getValor()+"");
            binding.tvLatitudI.setText(inmuebles.getLatitud()+"");
            binding.tvLongitudI.setText(inmuebles.getLongitud()+"");
            Glide.with(this)
                    .load(ApiClient.URLBASE + inmuebles.getImagen())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(binding.imgInmueble);
            binding.cbDisponible.setChecked(inmuebles.isDisponible());
        });

        binding.btnVerContrato.setOnClickListener(v -> mViewModel.onVerContratoClick());

        mViewModel.getIrAContrato().observe(getViewLifecycleOwner(), id -> {
            if (id == null) return;
            Bundle b = new Bundle();
            b.putInt("idInmueble", id);
            androidx.navigation.Navigation.findNavController(binding.getRoot())
                    .navigate(com.example.inmobiliaria.R.id.contratoFragment, b);
            mViewModel.irAContratoConsumido(); // evitar re-disparo
        });

        mViewModel.obtenerInmueble(getArguments());

        binding.cbDisponible.setOnClickListener(v ->
                mViewModel.actualizarInmueble(binding.cbDisponible.isChecked()));

        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}