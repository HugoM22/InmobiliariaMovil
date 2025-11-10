package com.example.inmobiliaria.ui.perfil;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Propietario;
import com.example.inmobiliaria.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {

    private final MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private final MutableLiveData<Boolean> tvMestado = new MutableLiveData<>();
    private final MutableLiveData<String> mText = new MutableLiveData<>();

    public LiveData<Propietario> getMPropietario() {
        return mPropietario;
    }

    public LiveData<Boolean> getTvMestado() {
        return tvMestado;
    }

    public LiveData<String> getmText() {
        return mText;
    }

    public PerfilViewModel(@NonNull Application app) {
        super(app);
    }

    public void guardarBtn(String btn, String dni, String apellido, String nombre, String tel, String email) {
        if (btn.equals("Editar")) {
            tvMestado.setValue(true);
            mText.setValue("Guardar");
        } else {
            dni = dni == null ? "" : dni.trim();
            apellido = apellido == null ? "" : apellido.trim();
            nombre = nombre == null ? "" : nombre.trim();
            tel = tel == null ? "" : tel.trim();
            email = email == null ? "" : email.trim();

            if (dni.isEmpty() || apellido.isEmpty() || nombre.isEmpty() || tel.isEmpty() || email.isEmpty()) {
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mPropietario.getValue() == null) {
                Toast.makeText(getApplication(), "No se pudo obtener el propietario actual", Toast.LENGTH_SHORT).show();
                return;
            }

            Propietario p = new Propietario();
            p.setIdPropietario(mPropietario.getValue().getIdPropietario());
            p.setDni(dni);
            p.setApellido(apellido);
            p.setNombre(nombre);
            p.setTelefono(tel);
            p.setEmail(email);

            mText.setValue("Editar");
            tvMestado.setValue(false);

            String token = ApiClient.leerToken(getApplication());
            Call<Propietario> llamada = ApiClient.getApiInmobiliaria().actualizarPropietario("Bearer " + token, p);
            llamada.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Propietario actualizado", Toast.LENGTH_SHORT).show();
                        leerPropietario();
                    } else {
                        Toast.makeText(getApplication(), "Error al actualizar el propietario", Toast.LENGTH_SHORT).show();
                        Log.d("Error", response.message());
                    }
                }

                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                    Log.d("Error", t.getMessage() != null ? t.getMessage() : "Error desconocido");
                }
            });
        }
    }

    public void leerPropietario() {
        String token = ApiClient.leerToken(getApplication());
        Call<Propietario> llamada = ApiClient.getApiInmobiliaria().obtenerPropietario("Bearer " + token);
        llamada.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener el propietario.", Toast.LENGTH_SHORT).show();
                    Log.d("Error", response.message());
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en el servidor.", Toast.LENGTH_SHORT).show();
                Log.d("Error", t.getMessage());
            }
        });
    }
}
