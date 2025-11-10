package com.example.inmobiliaria.ui.pago;

import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Pago;
import com.example.inmobiliaria.request.ApiClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagoViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Pago>> pagos = new MutableLiveData<>(Collections.emptyList());
    public LiveData<List<Pago>> getPagos(){ return pagos; }

    public PagoViewModel(@NonNull Application app){ super(app); }

    public void cargar(android.os.Bundle args){
        int idContrato = (args!=null) ? args.getInt("idContrato", 0) : 0;
        if (idContrato <= 0) {
            Toast.makeText(getApplication(), "Falta idContrato", Toast.LENGTH_SHORT).show();
            return;
        }
        String t = ApiClient.leerToken(getApplication());
        if (TextUtils.isEmpty(t)) {
            Toast.makeText(getApplication(), "Sesión expirada", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiClient.getApiInmobiliaria()
                .obtenerPagosPorContrato("Bearer " + t, idContrato)
                .enqueue(new Callback<List<Pago>>() {
                    @Override public void onResponse(Call<List<Pago>> c, Response<List<Pago>> r) {
                        if (r.isSuccessful() && r.body()!=null) pagos.postValue(r.body());
                        else Toast.makeText(getApplication(),"Sin pagos para este contrato", Toast.LENGTH_SHORT).show();
                    }
                    @Override public void onFailure(Call<List<Pago>> c, Throwable e) {
                        Toast.makeText(getApplication(),"Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}