package com.example.inmobiliaria.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.modelo.Inmuebles;
import com.example.inmobiliaria.request.ApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//trae inmuebles con contrato
public class ContratosViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Inmuebles>> inmueblesConContrato = new MutableLiveData<>();
    public LiveData<List<Inmuebles>> getInmueblesConContrato(){ return inmueblesConContrato; }

    public ContratosViewModel(@NonNull Application app){ super(app); }

    public void cargar(){

        //leer token
        String t = ApiClient.leerToken(getApplication());
        if (t==null || t.isEmpty()){
            Toast.makeText(getApplication(),"Sesión expirada", Toast.LENGTH_SHORT).show();
            inmueblesConContrato.postValue(Collections.emptyList());
            return;
        }

        ApiClient.getApiInmobiliaria().obtenerInmuebles("Bearer " + t)
                .enqueue(new Callback<List<Inmuebles>>() {
                    @Override public void onResponse(Call<List<Inmuebles>> call, Response<List<Inmuebles>> r) {
                        if (r.isSuccessful() && r.body()!=null){
                            //filtrado solo los que tienen contrato
                            List<Inmuebles> out = new ArrayList<>();
                            for (Inmuebles i : r.body())
                                if (i.isTieneContratoVigente()) out.add(i);
                            inmueblesConContrato.postValue(out);
                        } else inmueblesConContrato.postValue(Collections.emptyList());
                    }
                    @Override public void onFailure(Call<List<Inmuebles>> call, Throwable e) {
                        inmueblesConContrato.postValue(Collections.emptyList());
                    }
                });
    }
}