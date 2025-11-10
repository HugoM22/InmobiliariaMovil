package com.example.inmobiliaria.ui.perfil;


import android.app.Application;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarClaveViewModel extends AndroidViewModel {

    public MutableLiveData<String> actual = new MutableLiveData<>();
    public MutableLiveData<String> nueva = new MutableLiveData<>();
    public MutableLiveData<String> conf = new MutableLiveData<>();
    public MutableLiveData<Boolean> claveCambiada = new MutableLiveData<>();

    public CambiarClaveViewModel(@NonNull Application app) {
        super(app);
    }

    public void onGuardarClick() {
        String a = actual.getValue() == null ? "" : actual.getValue().trim();
        String n = nueva.getValue() == null ? "" : nueva.getValue().trim();
        String c = conf.getValue() == null ? "" : conf.getValue().trim();

        if (a.isEmpty()) {
            Toast.makeText(getApplication(), "Ingresá la clave actual", Toast.LENGTH_SHORT).show();
            return;
        }
        if (n.length() < 6) {
            Toast.makeText(getApplication(), "La nueva clave debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!n.equals(c)) {
            Toast.makeText(getApplication(), "La confirmación no coincide", Toast.LENGTH_SHORT).show();
            return;
        }

        String token = ApiClient.leerToken(getApplication());
        if (token == null || token.isEmpty()) {
            Toast.makeText(getApplication(), "Sesión expirada. Iniciá sesión.", Toast.LENGTH_SHORT).show();
            claveCambiada.postValue(false);
            return;
        }

        ApiClient.getApiInmobiliaria()
                .cambiarClave("Bearer " + token, a, n)
                .enqueue(new retrofit2.Callback<Void>() {
                    @Override
                    public void onResponse(retrofit2.Call<Void> call, retrofit2.Response<Void> r) {
                        if (r.isSuccessful()) {
                            Toast.makeText(getApplication(), "Clave actualizada", Toast.LENGTH_SHORT).show();
                            claveCambiada.postValue(true);
                        } else {
                            String err = "";
                            try {
                                if (r.errorBody() != null) err = r.errorBody().string();
                            } catch (Exception ignored) {
                            }
                            Toast.makeText(getApplication(), "Error " + r.code() + " " + err, Toast.LENGTH_LONG).show();
                            claveCambiada.postValue(false);
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                        Toast.makeText(getApplication(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        claveCambiada.postValue(false);
                    }
                });
    }
}