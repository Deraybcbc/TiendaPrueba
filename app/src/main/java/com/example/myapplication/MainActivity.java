package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String Usuario;
    private String Contrasenya;

    private static final String URL = "http://192.168.1.35:3001/";

    public static ApiService apiService;
    private EditText User;
    private EditText Contras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = (Button) findViewById(R.id.acceder);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //BUSCAR IDS
        User = (EditText) findViewById(R.id.users);
        Contras = (EditText) findViewById(R.id.contra);

        Usuario = User.getText().toString();
        Contrasenya = Contras.getText().toString();

        if(Usuario.isEmpty() || Contrasenya.isEmpty()){
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }
        else{
            VerificarUsuari();
        }
    }

    private void VerificarUsuari() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        UsuariTrobat datosLogin = new UsuariTrobat(Usuario,Contrasenya);

        Call<UsuariTrobat> call = apiService.EnviarUsuari(datosLogin);

        call.enqueue(new Callback<UsuariTrobat>() {
            @Override
            public void onResponse(Call<UsuariTrobat> call, Response<UsuariTrobat> response) {
                if(response.isSuccessful()){
                    Log.d("Conexion","CONEXION SERVIDOR ESTABLECIDA");

                    Intent intent = new Intent(MainActivity.this,Botiga.class);
                    intent.putExtra("user",Usuario);
                    startActivity(intent);

                }
                else{
                    Log.d("Conexion","CONEXION SERVIDOR ERROR");

                }
            }

            @Override
            public void onFailure(Call<UsuariTrobat> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}