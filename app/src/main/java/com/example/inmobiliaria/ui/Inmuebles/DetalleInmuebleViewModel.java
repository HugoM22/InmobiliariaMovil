package com.example.inmobiliaria.ui.Inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Inmuebles;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {
    private final MutableLiveData<Inmuebles> inmueble = new MutableLiveData<>();

    private final MutableLiveData<Integer> irAContrato = new MutableLiveData<>();

    public LiveData<Inmuebles> getInmueble(){ return inmueble; }
    public LiveData<Integer> getIrAContrato(){ return irAContrato; } // 👈 getter

    public DetalleInmuebleViewModel(@NonNull Application application) { super(application); }

    public void obtenerInmueble(Bundle inmuebleBundle){
        Inmuebles i = (Inmuebles) (inmuebleBundle != null ? inmuebleBundle.getSerializable("inmueble") : null);
        if(i != null){ this.inmueble.setValue(i); }
    }

    public void onVerContratoClick(){
        Inmuebles i = inmueble.getValue();
        if(i != null){
            irAContrato.setValue(i.getIdInmueble()); // dispara navegación
        }
    }

    public void irAContratoConsumido(){ irAContrato.setValue(null); }

    public void actualizarInmueble(Boolean disponible){
        Inmuebles i = new Inmuebles();
        i.setDisponible(disponible);
        i.setIdInmueble(this.inmueble.getValue().getIdInmueble());
        String token = ApiClient.leerToken(getApplication());
        Call<Inmuebles> call = ApiClient.getApiInmobiliaria().actualizarInmueble("Bearer " + token, i);
        call.enqueue(new Callback<Inmuebles>() {
            @Override public void onResponse(Call<Inmuebles> call, Response<Inmuebles> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(),"Inmueble actualizado correctamente ", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplication(), "Error al actualizar inmueble "+response.message(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override public void onFailure(Call<Inmuebles> call, Throwable t) {
                Toast.makeText(getApplication(), "Error al contectar con el servidor "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
