package com.example.inmobiliaria.ui.Inicio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.inmobiliaria.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment {

    private InicioViewModel mViewModel;
    private GoogleMap mapa;
    private static final LatLng ULP = new LatLng(-33.150720,-66.306864);
    public static InicioFragment newInstance() {
        return new InicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        // SOLO ESTAS DOS LÍNEAS (sin if, sin extras)
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new MapaActual());
    }
    private class MapaActual implements OnMapReadyCallback{
        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            mapa=googleMap;
            mapa.addMarker(new MarkerOptions().position(ULP)).setTitle("Inmobiliaria ULP");
        }
    }

}