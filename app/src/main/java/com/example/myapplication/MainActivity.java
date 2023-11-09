package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String Usuario;
    private String Contrasenya;

    //private static final String URL = "http://192.168.1.35:3044/";
    private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044";

    public static ApiService apiService;
    private EditText User;
    private EditText Contras;

    private TextView registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button boton = (Button) findViewById(R.id.acceder);
        boton.setOnClickListener(this);

        TextView registro = (TextView) findViewById(R.id.registro);
        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registrar.class);
                startActivity(intent);
            }
        });
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

        Call<Respuesta> call = apiService.EnviarUsuari(datosLogin);

        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if(response.isSuccessful()){
                    Log.d("Conexion","CONEXION SERVIDOR ESTABLECIDA");

                    Respuesta r = response.body();

                    if (r.isAutoritzacio()){
                        Log.d("User","entro");
                        Intent intent = new Intent(MainActivity.this,Botiga.class);
                        intent.putExtra("user",Usuario);
                        intent.putExtra("contra",Contrasenya);
                        startActivity(intent);
                    }else{
                        Log.e("USER","NO ESTA");
                        Toast.makeText(MainActivity.this, "No esta en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}