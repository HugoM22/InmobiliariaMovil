package com.example.inmobiliaria.ui.Inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.inmobiliaria.modelo.Inmuebles;
import com.example.inmobiliaria.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CargarInmuebleViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> mUri = new MutableLiveData<>();

    public CargarInmuebleViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<Uri> getMuri(){
        return mUri;
    }
    public void recibirFoto(ActivityResult result){
        if(result.getResultCode()==RESULT_OK){
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }


    public void cargarInmueble(String direccion, String tipo, String uso, String ambientes, String superficie, String valor, String latitud, String longitud, boolean disponible){
        int superficiePars, ambientesPars, latitudPars, longitudPars;
        double valorPars;
        try{
            valorPars = Double.parseDouble(valor);
            superficiePars = Integer.parseInt(superficie);
            ambientesPars = Integer.parseInt(ambientes);
            latitudPars = Integer.parseInt(latitud);
            longitudPars = Integer.parseInt(longitud);
            if(direccion.isEmpty() || tipo.isEmpty() || uso.isEmpty() || ambientes.isEmpty() || superficie.isEmpty() || valor.isEmpty() || latitud.isEmpty() || longitud.isEmpty()){
                Toast.makeText(getApplication(), "Debe completar todos los campos.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(mUri.getValue()==null){
                Toast.makeText(getApplication(), "Debe seleccionar una foto.", Toast.LENGTH_SHORT).show();
                return;
            }
            Inmuebles inmueble = new Inmuebles();
            inmueble.setDireccion(direccion);
            inmueble.setTipo(tipo);
            inmueble.setUso(uso);
            inmueble.setAmbientes(ambientesPars);
            inmueble.setSuperficie(superficiePars);
            inmueble.setValor(valorPars);
            inmueble.setLatitud(latitudPars);
            inmueble.setLongitud(longitudPars);
            inmueble.setDisponible(disponible);
            // Converitr en uri
            byte[] imagen = transformarImagen();
            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);

            //MULTI PART
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);
            ApiClient.InmobiliariaService inmoService = ApiClient.getApiInmobiliaria();
            String token = ApiClient.leerToken(getApplication());
            Call<Inmuebles> call = inmoService.cargarInmueble("Bearer "+token, imagenPart, inmuebleBody);
            call.enqueue(new Callback<Inmuebles>() {
                @Override
                public void onResponse(Call<Inmuebles> call, Response<Inmuebles> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplication(), "Inmueble cargado con exito.", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplication(), "Error al cargar el inmueble.", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Inmuebles> call, Throwable t) {
                    Toast.makeText(getApplication(),"Error al cargar inmueble", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (NumberFormatException nfe){
            Toast.makeText(getApplication(), "Debe ingresar numeros en los campos correspondientes.", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private byte[] transformarImagen(){
        try{
            Uri uri = mUri.getValue();
            InputStream inputStream = getApplication().getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }catch(FileNotFoundException er){
            Toast.makeText(getApplication(),"No ha seleccionado una foto", Toast.LENGTH_SHORT).show();
            return  new byte[]{};
        }
    }
}