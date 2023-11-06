package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityBotigaBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DadesUsuari extends AppCompatActivity {

    private static String URL="http://192.168.1.35:3001/";
    //private static String URL="http://192.168.205.213:3001/";
    public ApiService apiService;
    private ArrayList<TextView> textos;

    private AppBarConfiguration appBarConfiguration;
    private ActivityBotigaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades_usuari);

        String user = getIntent().getStringExtra("infouser");

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        System.out.println(user);

        Call<List<UsuariTrobat>> call = apiService.ObtenerUsers();

        call.enqueue(new Callback<List<UsuariTrobat>>() {
            @Override
            public void onResponse(Call<List<UsuariTrobat>> call, Response<List<UsuariTrobat>> response) {
                if(response.isSuccessful()){
                    Log.d("CONEXION","CONEXION CON EXITO");

                    List<UsuariTrobat> infousers = response.body();

                    if(infousers!=null){
                        for (int i = 0 ; i < infousers.size();i++) {
                            TextView infouser = (TextView) findViewById(R.id.inforuserD);
                            infouser.setText(infousers.get(i).getUsuario());
                            TextView infon = (TextView) findViewById(R.id.infonom);
                            infon.setText(infousers.get(i).getNom());
                            TextView infoc = (TextView) findViewById(R.id.infocognom);
                            infoc.setText(infousers.get(i).getCognoms());
                            TextView infocorreu = (TextView) findViewById(R.id.infocorreu);
                            infocorreu.setText(infousers.get(i).getCorreu());
                            TextView infocontraD = (TextView) findViewById(R.id.infocontraD);
                            infocontraD.setText(infousers.get(i).getPasswd());
                            TextView infoNumTargeta = (TextView) findViewById(R.id.infoNumTargeta);
                            infoNumTargeta.setText(infousers.get(i).getnTargeta());
                            TextView infoCVV = (TextView) findViewById(R.id.infoCVV);
                            infoCVV.setText(infousers.get(i).getCVV());
                            TextView infofecha = (TextView) findViewById(R.id.infofecha);
                            infofecha.setText(infousers.get(i).getDataCaducitat());
                        }
                    }
                    else{
                        Log.d("ERROR","INFOR USER VACIO");
                    }
                }
                else{
                    Log.d("CONEXION","CONEXION FALLIDA");
                }
            }

            @Override
            public void onFailure(Call<List<UsuariTrobat>> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });

    }

    //Accion flecha
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent =  new Intent(this,Botiga.class);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //Mostrar y enviar a donde queremos ir
        switch (id) {
            case R.id.dades:
                // Acción para la "Opción 1"
                Intent intent1 = new Intent(this, DadesUsuari.class);
                startActivity(intent1);
                return true;
            case R.id.cerrar:
                // Acción para la "Opción 1"
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                return true;
            case R.id.comandas:
                Intent intent3 = new Intent(this,comandas.class);
                startActivity(intent3);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}