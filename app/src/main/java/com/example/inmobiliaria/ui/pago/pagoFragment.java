package com.example.inmobiliaria.ui.pago;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.inmobiliaria.databinding.FragmentPagosBinding;

public class pagoFragment extends Fragment {
    private FragmentPagosBinding binding;
    private PagoViewModel vm;
    private PagoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle s) {
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PagoViewModel.class);

        adapter = new PagoAdapter();
        binding.rvPagos.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvPagos.setAdapter(adapter);

        vm.getPagos().observe(getViewLifecycleOwner(), adapter::updateData);
        vm.cargar(getArguments());
        return binding.getRoot();
    }
}
