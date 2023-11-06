package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityBotigaBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Botiga extends AppCompatActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBotigaBinding binding;

    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptadorProductos adaptadorProductos;
    public static ApiService apiService;

    private ImageView car;

    public String usuari;

    private static final String URL = "http://192.168.1.35:3001/";
    //private static final String URL = "http://192.168.205.213:3001/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBotigaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        Call<List<Productos>> call = apiService.ObtenerProductos();

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if(response.isSuccessful()){

                    Log.d("CONEXION","CONEXION SERVIDOR EXITOSA");

                    List<Productos> listaproductos = response.body();
                    for(int i = 0; i < listaproductos.size();i++){
                        System.out.println("BORITGA: " +listaproductos.get(i).getId_producte());

                    }

                    Log.d("ListaProductos","Lista:"+listaproductos);

                    //Para mostrar los Productos con el RecyclerView

                    recyclerViewProductos=(RecyclerView)findViewById(R.id.Productos);
                    recyclerViewProductos.setLayoutManager(new LinearLayoutManager(Botiga.this));
                    adaptadorProductos=new RecyclerViewAdaptadorProductos(listaproductos);
                    recyclerViewProductos.setAdapter(adaptadorProductos);

                    //Pillar los datos que hemos seleccionado
                    adaptadorProductos.setOnItemClickListener(new RecyclerViewAdaptadorProductos.OnItemClickListener() {

                        @Override
                        public void onItemClick(List<Productos> listaProductos) {
                            car = (ImageView) findViewById(R.id.Imgcar);
                            car.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Botiga.this,carrito.class);
                                    intent.putExtra("USER",usuari);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                    adaptadorProductos.notifyDataSetChanged();
                }



            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                Log.d("ERROR",t.getMessage());
            }
        });


        usuari = getIntent().getStringExtra("user");

        if(usuari==null){
            Log.d("VACIO","SOLO PARA NO MOSTRAR VACIO");
        }else{
            Toast.makeText(this, "Bienvenido "+usuari, Toast.LENGTH_SHORT).show();
        }


    }

    //ACCION DE FLECHA
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_botiga);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
                intent1.putExtra("infouser",usuari);
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