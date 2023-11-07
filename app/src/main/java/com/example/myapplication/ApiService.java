package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("usuaris")
    Call<Respuesta> EnviarUsuari(@Body UsuariTrobat usuariTrobat);

    @GET("dadesUsuari")
    Call<List<UsuariTrobat>> ObtenerUsers();

    @GET("getProductos")
    Call<List<Productos>> ObtenerProductos();

    @POST("crearComanda")
    Call<Respuesta> EnviarComando(@Body List<ProductosEnviar> ListaProductos);

    @GET("getComandes")
    Call<List<RecibirComandas>> RecibirComandas ();

    @POST("registrarUsuari")
    Call<Respuesta> EnviarUsuario(@Body UsuariTrobat usuariTrobat);
}
