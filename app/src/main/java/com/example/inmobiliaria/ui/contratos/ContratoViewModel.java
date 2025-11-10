package com.example.inmobiliaria.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.modelo.Contrato;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private final MutableLiveData<Contrato> contrato = new MutableLiveData<>();
    private final MutableLiveData<String> periodo   = new MutableLiveData<>("-");
    private final MutableLiveData<String> monto     = new MutableLiveData<>("-");
    private final MutableLiveData<String> estado    = new MutableLiveData<>("-");
    private final MutableLiveData<String> inquilino = new MutableLiveData<>("-");

    // evento de navegación
    private final MutableLiveData<Integer> goPagos = new MutableLiveData<>();
    private Integer idContratoActual = null;

    public ContratoViewModel(@NonNull Application app) { super(app); }

    public LiveData<Contrato> getContrato() { return contrato; }
    public LiveData<String>  getPeriodo()   { return periodo; }
    public LiveData<String>  getMonto()     { return monto; }
    public LiveData<String>  getEstado()    { return estado; }
    public LiveData<String>  getInquilino() { return inquilino; }
    public LiveData<Integer> getGoPagos()   { return goPagos; }

    public void cargarContrato(Bundle args){
        int idInmueble = args != null ? args.getInt("idInmueble", 0) : 0;
        if (idInmueble <= 0){
            Toast.makeText(getApplication(), "Falta idInmueble", Toast.LENGTH_SHORT).show();
            return;
        }
        String t = ApiClient.leerToken(getApplication());
        if (TextUtils.isEmpty(t)){
            Toast.makeText(getApplication(), "Sesión expirada", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiClient.getApiInmobiliaria()
                .obtenerContratoPorInmueble("Bearer " + t, idInmueble)
                .enqueue(new Callback<Contrato>() {
                    @Override public void onResponse(Call<Contrato> call, Response<Contrato> r) {
                        if (r.isSuccessful() && r.body()!=null){
                            Contrato c = r.body();
                            contrato.postValue(c);
                            idContratoActual = c.getIdContrato();

                            periodo.postValue("Periodo: " + safe(c.getFechaInicio()) + " a " + safe(c.getFechaFinalizacion()));
                            monto.postValue("Monto: $ " + c.getMontoAlquiler());
                            estado.postValue("Estado: " + (c.isEstado() ? "Activo" : "Finalizado"));
                            String iq = (c.getInquilino()!=null)
                                    ? safe(c.getInquilino().getNombre()) + " " + safe(c.getInquilino().getApellido())
                                    : "-";
                            inquilino.postValue("Inquilino: " + iq);
                        } else {
                            Toast.makeText(getApplication(), "Sin contrato para este inmueble", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override public void onFailure(Call<Contrato> call, Throwable e) {
                        Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void pedirPagos(){
        if (idContratoActual != null) {
            goPagos.setValue(idContratoActual);
        }
    }

    public void pagosNavegado(){ goPagos.setValue(null); }

    private String safe(String s){ return s==null? "" : s; }
}


