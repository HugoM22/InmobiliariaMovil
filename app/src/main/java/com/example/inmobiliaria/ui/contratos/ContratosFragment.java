package com.example.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.inmobiliaria.databinding.FragmentInmueblesBinding;
import com.example.inmobiliaria.modelo.Inmuebles;

import java.util.List;
//lista los inmuebles con contrato
public class ContratosFragment extends Fragment {
    private FragmentInmueblesBinding binding;
    private ContratosViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentInmueblesBinding.inflate(inflater, container, false);

        //gird card
        binding.rvListaInmueble.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        vm.getInmueblesConContrato().observe(getViewLifecycleOwner(), lista ->
                binding.rvListaInmueble.setAdapter(new InmuebleContratoAdapter(lista)));

        vm.cargar();
        return binding.getRoot();
    }

    @Override public void onDestroyView() { super.onDestroyView(); binding = null; }
}
