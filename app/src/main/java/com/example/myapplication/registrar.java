package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class registrar extends AppCompatActivity {

    private EditText nom,cognom,usuari,passwd,correo,cvv,ntarjeta,dataCaducitat;
    private String noms,cognoms,usuaris,passwds, cvvs, ntarjetas,dataCaducitats,correos;
    private Button boton,cancelar;
    private ApiService apiService;

    private static final String URL = "http://192.168.1.35:3044/";

    //private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //BUSCAR IDS
        nom = (EditText) findViewById(R.id.Rnom);
        cognom = (EditText) findViewById(R.id.Rapellido);
        usuari = (EditText) findViewById(R.id.Rusuari);
        passwd = (EditText) findViewById(R.id.Rpasswd);
        correo = (EditText) findViewById(R.id.Rcorreo);
        cvv = (EditText) findViewById(R.id.Rcvv);
        ntarjeta = (EditText) findViewById(R.id.Rtarjeta);
        dataCaducitat = (EditText) findViewById(R.id.Rdata);
        boton = (Button) findViewById(R.id.registrarse);
        cancelar = (Button)findViewById(R.id.cancelar);


        boton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                noms = nom.getText().toString();
                cognoms = cognom.getText().toString();
                usuaris = usuari.getText().toString();
                passwds = passwd.getText().toString();
                correos = correo.getText().toString();
                cvvs = cvv.getText().toString();
                ntarjetas = ntarjeta.getText().toString();
                dataCaducitats = dataCaducitat.getText().toString();

                System.out.println(noms);
                System.out.println(cognoms);
                System.out.println(usuaris);
                System.out.println(passwds);
                System.out.println(correos);
                System.out.println(cvvs);
                System.out.println(ntarjetas);
                System.out.println(dataCaducitats);


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                apiService = retrofit.create(ApiService.class);

                UsuariTrobat usuariTrobat = new UsuariTrobat(usuaris,noms,cognoms,passwds,ntarjetas,cvvs,dataCaducitats,correos);

                Call<Respuesta> call = apiService.EnviarUsuario(usuariTrobat);

                call.enqueue(new Callback<Respuesta>() {
                    @Override
                    public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                        if(response.isSuccessful()){
                            Log.d("CONEXION","CONEXION SERVIDOR CONECTADO");
                            Respuesta r = response.body();
                            System.out.println(r.isAutoritzacio());
                            if(r.isAutoritzacio()){
                                Intent intent = new Intent(registrar.this,Botiga.class);
                                intent.putExtra("user",usuaris);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Respuesta> call, Throwable t) {
                        Log.e("ERROR",t.getMessage());
                    }
                });
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registrar.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}