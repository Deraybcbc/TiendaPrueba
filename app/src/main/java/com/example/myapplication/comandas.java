package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class comandas extends AppCompatActivity implements View.OnClickListener {

    private String URL="http://192.168.1.35:3001/";
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandas);

        ImageView atras = (ImageView) findViewById(R.id.botigaAtras);
        atras.setOnClickListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        Call<List<RecibirComandas>> call = apiService.RecibirComandas();

        call.enqueue(new Callback<List<RecibirComandas>>() {
            @Override
            public void onResponse(Call<List<RecibirComandas>> call, Response<List<RecibirComandas>> response) {
                if(response.isSuccessful()){
                    Log.d("CONEXION","CONEXION CON EL SERVIDOR EXITOSA");
                }
            }

            @Override
            public void onFailure(Call<List<RecibirComandas>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,Botiga.class);
        startActivity(intent);
    }
}