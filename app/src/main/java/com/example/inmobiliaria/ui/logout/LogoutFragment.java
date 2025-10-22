package com.example.inmobiliaria.ui.logout;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.example.inmobiliaria.request.ApiClient;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LogoutFragment extends Fragment {

    private LogoutViewModel mViewModel;

    public static LogoutFragment newInstance() {
        return new LogoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Cierre de sesión")
                .setMessage("¿Estás seguro de cerrar la sesión?")
                .setNegativeButton("Cancelar", (d, w) -> {
                    d.dismiss();
                    Navigation.findNavController(view).popBackStack();
                })
                .setPositiveButton("Aceptar", (d, w) -> {
                    // borrar token y mandar a Login
                    ApiClient.borrarToken(requireContext());
                    NavController nc = Navigation.findNavController(view);
                    nc.navigate(
                            R.id.nav_login,
                            null,
                            new NavOptions.Builder()
                                    .setPopUpTo(R.id.mobile_navigation, true)
                                    .build()
                    );
                })
                .setOnCancelListener(di -> {
                    Navigation.findNavController(view).popBackStack();
                })
                .show();
    }
}