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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    private static final String URL = "http://192.168.1.35:3044/";
    //private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044";
    public ApiService apiService;
    private AppBarConfiguration appBarConfiguration;
    private ActivityBotigaBinding binding;
    public ImageButton vercontra,verCVV,actualizar;
    private boolean visibleContra=false;
    private boolean visibleCVV=false;

    private String infonom,infocognom,infocorreo,infousuario,infocontr,infonT,infodata,infocvv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dades_usuari);

        vercontra =  (ImageButton)findViewById(R.id.vercontra);
        verCVV = (ImageButton) findViewById(R.id.verCVV);

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
                            EditText infouser = (EditText) findViewById(R.id.infousersD);
                            infouser.setText(infousers.get(i).getUsuario());

                            EditText infon = (EditText) findViewById(R.id.infonoms);
                            infon.setText(infousers.get(i).getNom());

                            EditText infoc = (EditText) findViewById(R.id.infocognoms);
                            infoc.setText(infousers.get(i).getCognoms());

                            EditText infocorreu = (EditText) findViewById(R.id.infocorreus);
                            infocorreu.setText(infousers.get(i).getCorreu());

                            EditText infocontraD = (EditText) findViewById(R.id.infocontrasD);
                            vercontra.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(visibleContra){
                                        for (int j = 0 ; j < infousers.size();j++) {
                                            infocontraD.setInputType(129);
                                            visibleContra = false;
                                            infocontraD.setText(infousers.get(j).getPasswd());
                                        }
                                    }else{
                                        for (int j = 0 ; j < infousers.size();j++) {
                                            infocontraD.setInputType(128);
                                            visibleContra = true;
                                            infocontraD.setText(infousers.get(j).getPasswd());
                                        }
                                    }
                                }
                            });

                            EditText infoNumTargeta = (EditText) findViewById(R.id.infoNumTargetas);
                            infoNumTargeta.setText(infousers.get(i).getnTargeta());

                            EditText infoCVV = (EditText) findViewById(R.id.infoCVVS);

                            verCVV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(visibleCVV){
                                        for (int i = 0 ; i < infousers.size();i++) {

                                            infoCVV.setInputType(129);
                                            visibleCVV = false;
                                            infoCVV.setText(infousers.get(i).getCVV());
                                        }
                                    }else{
                                        //sino es 128 puede ser 144
                                        for (int i = 0 ; i < infousers.size();i++) {

                                            infoCVV.setInputType(128);
                                            visibleCVV = true;
                                            infoCVV.setText(infousers.get(i).getCVV());
                                        }
                                    }
                                }
                            });

                            EditText infofecha = (EditText) findViewById(R.id.infofechas);
                            infofecha.setText(infousers.get(i).getDataCaducitat());
/*
                            infonom = infousers.get(i).getNom();
                            infocognom = infousers.get(i).getCognoms();
                            infocorreo = infousers.get(i).getCorreu();
                            infousuario = infousers.get(i).getUsuario();
                            infocontr = infousers.get(i).getPasswd();
                            infonT = infousers.get(i).getnTargeta();
                            infodata = infousers.get(i).getDataCaducitat();
                            infocvv = infousers.get(i).getCVV();
                            Log.d("NOM: ",infonom);
                            Log.d("infocognom: ",infocognom);
                            Log.d("infocorreo: ",infocorreo);
                            Log.d("infousuario: ",infousuario);
                            Log.d("infocontr: ",infocontr);
                            Log.d("infonT: ",infonT);
                            Log.d("infodata: ",infodata);
                            Log.d("infocvv: ",infocvv);*/

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







        actualizar = (ImageButton) findViewById(R.id.actualizzar);
        actualizar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                EditText infouser = (EditText) findViewById(R.id.infousersD);
                EditText infon = (EditText) findViewById(R.id.infonoms);
                EditText infoc = (EditText) findViewById(R.id.infocognoms);
                EditText infocorreu = (EditText) findViewById(R.id.infocorreus);
                EditText infocontraD = (EditText) findViewById(R.id.infocontrasD);
                EditText infoNumTargeta = (EditText) findViewById(R.id.infoNumTargetas);
                EditText infoCVV = (EditText) findViewById(R.id.infoCVVS);
                EditText infofecha = (EditText) findViewById(R.id.infofechas);



                String infonom = infon.getText().toString();
                String infocognom = infoc.getText().toString();
                String infocorreo = infocorreu.getText().toString();
                String infousuario = infouser.getText().toString();
                String infocontr = infocontraD.getText().toString();
                String infonT= infoNumTargeta.getText().toString();
                String infodata = infofecha.getText().toString();
                String infocvv = infoCVV.getText().toString();

                Retrofit retrofit1 = new Retrofit.Builder()
                        .baseUrl(URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ApiService  apiService = retrofit1.create(ApiService.class);

                UsuariTrobat usuarioactualizado = new UsuariTrobat(infousuario,infonom,infocognom,infocontr,infonT,infocvv,infodata,infocorreo);

                Call<Void> callUpdate  = apiService.EnviarUsuarioActualizado(usuarioactualizado);

                callUpdate .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Log.d("CONEXION","SERVIDOR CONECTADO");
                            Toast.makeText(DadesUsuari.this, "Usuario Modificado", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DadesUsuari.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("CONEXION",t.getMessage());
                    }
                });
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
                finish();
                return true;
            case R.id.comandas:
                Intent intent3 = new Intent(this,comandas.class);
                startActivity(intent3);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}