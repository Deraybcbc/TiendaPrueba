package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("usuaris")
    Call<Respuesta> EnviarUsuari(@Body UsuariTrobat usuariTrobat);

    @POST("dadesUsuari")
    Call<List<UsuariTrobat>> ObtenerUsers(@Body UsuariTrobat usuariTrobat);
}
