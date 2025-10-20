package com.example.inmobiliaria.ui.perfil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.databinding.FragmentPerfilBinding;
import com.example.inmobiliaria.modelo.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel mViewModel;
    private FragmentPerfilBinding binding;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);

        mViewModel.getMPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.tvDniPerfil.setText(propietario.getDni());
                binding.tvApellidoPerfil.setText(propietario.getApellido());
                binding.tvNombrePerfil.setText(propietario.getNombre());
                binding.tvTelefonoPerfil.setText(propietario.getTelefono());
                binding.tvEmailPerfil.setText(propietario.getEmail());
            }
        });

        mViewModel.getTvMestado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBool) {
                binding.tvDniPerfil.setEnabled(aBool);
                binding.tvApellidoPerfil.setEnabled(aBool);
                binding.tvNombrePerfil.setEnabled(aBool);
                binding.tvTelefonoPerfil.setEnabled(aBool);
                binding.tvEmailPerfil.setEnabled(aBool);
            }
        });
        mViewModel.getmText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnEditarPerfil.setText(s);
            }
        });

        mViewModel.leerPropietario();
        binding.btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String dni = binding.tvDniPerfil.getText().toString();
                String apellido = binding.tvApellidoPerfil.getText().toString();
                String nombre = binding.tvNombrePerfil.getText().toString();
                String tel = binding.tvTelefonoPerfil.getText().toString();
                String email = binding.tvEmailPerfil.getText().toString();
                mViewModel.guardarBtn(binding.btnEditarPerfil.getText().toString(),dni,apellido,nombre,tel,email);
            }
        });
        return binding.getRoot();
    }
}