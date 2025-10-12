package com.example.inmobiliaria.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.inmobiliaria.MainActivity;
import com.example.inmobiliaria.request.ApiClient;
import com.example.inmobiliaria.ui.home.HomeFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> mLogin = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mensajeVisible = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application app) {
        super(app);
    }

    public LiveData<Boolean> getLogin() { return mLogin; }
    public LiveData<String> getMensaje() { return mensaje; }
    public LiveData<Boolean> getMensajeVisible() { return mensajeVisible; }

    public void Ingresar(String usuario, String pass) {
        mensajeVisible.postValue(false);
        mensaje.postValue("");

        ApiClient.InmobiliariaService api = ApiClient.getApiInmobiliaria(getApplication());
        Call<String> llamada = api.obtenerLogin(usuario.trim(), pass);

        llamada.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mLogin.postValue(true);
                    mensaje.postValue("¡Bienvenido!");
                    Intent i = new Intent(getApplication(), MainActivity.class); // o tu MenuRes si es la activity del drawer
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    getApplication().startActivity(i);
                } else {
                    mLogin.postValue(false);
                    mensaje.postValue("Usuario/clave inválidos");
                }
                mensajeVisible.postValue(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mLogin.postValue(false);
                mensaje.postValue(t.getMessage() != null ? t.getMessage() : "Error de red");
                mensajeVisible.postValue(true);
            }
        });
    }

    public void logout() {
        ApiClient.borrarToken(getApplication());
    }

    public boolean isLoggedIn() {
        return !ApiClient.leerToken(getApplication()).isEmpty();
    }
}

