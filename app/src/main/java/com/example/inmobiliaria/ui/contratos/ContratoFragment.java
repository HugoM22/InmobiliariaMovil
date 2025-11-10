package com.example.inmobiliaria.ui.contratos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentContratoBinding;


//detalle de un contrato de un inmueble
public class ContratoFragment extends Fragment {
    private FragmentContratoBinding binding;
    private ContratoViewModel vm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentContratoBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ContratoViewModel.class);

        //observes
        vm.getPeriodo().observe(getViewLifecycleOwner(), binding.tvPeriodo::setText);
        vm.getMonto().observe(getViewLifecycleOwner(), binding.tvMonto::setText);
        vm.getEstado().observe(getViewLifecycleOwner(), binding.tvEstado::setText);
        vm.getInquilino().observe(getViewLifecycleOwner(), binding.tvInquilino::setText);

        //btn
        binding.btnPagos.setOnClickListener(v -> vm.pedirPagos());
        //navegacion
        vm.getGoPagos().observe(getViewLifecycleOwner(), idContrato -> {
            if (idContrato == null) return;
            Bundle b = new Bundle(); b.putInt("idContrato", idContrato);
            Navigation.findNavController(binding.getRoot())
                    .navigate(R.id.pagoFragment, b);
            vm.pagosNavegado();
        });

        vm.cargarContrato(getArguments());

        return binding.getRoot();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
