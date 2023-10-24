package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("usuaris")
    Call<UsuariTrobat> EnviarUsuari(@Body UsuariTrobat usuariTrobat);
}
