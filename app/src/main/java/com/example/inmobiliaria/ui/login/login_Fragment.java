package com.example.inmobiliaria.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.inmobiliaria.databinding.FragmentLoginBinding;

public class login_Fragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel mViewModel;

    public static login_Fragment newInstance() {
        return new login_Fragment();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // mensaje y lo muestra
        mViewModel.getMensaje().observe(getViewLifecycleOwner(), binding.tvMensaje::setText);

        // visibilidad del mensaje
        mViewModel.getMensajeVisible().observe(getViewLifecycleOwner(),
                visible -> binding.tvMensaje.setVisibility(visible ? View.VISIBLE : View.GONE));

        // botón login
        binding.btnLogin.setOnClickListener(v -> {
            mViewModel.Ingresar(
                    binding.etUsuario.getText().toString(),
                    binding.etClave.getText().toString()
            );
        });
    }
}
