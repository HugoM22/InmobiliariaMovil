package com.example.inmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentPerfilBinding;
import com.example.inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);

        mViewModel.getMPropietario().observe(getViewLifecycleOwner(), propietario -> {
            if (propietario == null) return;
            binding.tvDniPerfil.setText(propietario.getDni());
            binding.tvApellidoPerfil.setText(propietario.getApellido());
            binding.tvNombrePerfil.setText(propietario.getNombre());
            binding.tvTelefonoPerfil.setText(propietario.getTelefono());
            binding.tvEmailPerfil.setText(propietario.getEmail());
        });

        mViewModel.getTvMestado().observe(getViewLifecycleOwner(), estado -> {
            binding.tvDniPerfil.setEnabled(estado);
            binding.tvApellidoPerfil.setEnabled(estado);
            binding.tvNombrePerfil.setEnabled(estado);
            binding.tvTelefonoPerfil.setEnabled(estado);
            binding.tvEmailPerfil.setEnabled(estado);
        });

        mViewModel.getmText().observe(getViewLifecycleOwner(), texto -> {
            binding.btnEditarPerfil.setText(texto);
        });

        mViewModel.leerPropietario();

        binding.btnEditarPerfil.setOnClickListener(v -> {
            String dni = binding.tvDniPerfil.getText().toString();
            String apellido = binding.tvApellidoPerfil.getText().toString();
            String nombre = binding.tvNombrePerfil.getText().toString();
            String tel = binding.tvTelefonoPerfil.getText().toString();
            String email = binding.tvEmailPerfil.getText().toString();
            mViewModel.guardarBtn(binding.btnEditarPerfil.getText().toString(), dni, apellido, nombre, tel, email);
        });
        binding.btnClavePerfil.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(R.id.cambiarClaveFragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
