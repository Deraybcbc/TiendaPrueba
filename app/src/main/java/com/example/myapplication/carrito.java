package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class carrito extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptadorCarito adaptadorCarito;
    private List<Productos> selectedProductos;


    private static final String URL = "http://192.168.1.35:3044/";
    //private static final String URL = "http://192.168.205.213:3001/";


    public static ApiService apiService;


    private Button comprar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerViewProductos = (RecyclerView) findViewById(R.id.carritoRecycler);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(carrito.this));


        selectedProductos = ProductoSelecionado.getInstance().getSelectedProductos();
        System.out.println(selectedProductos.size());

            if (selectedProductos != null && !selectedProductos.isEmpty()) {
                adaptadorCarito = new RecyclerViewAdaptadorCarito(selectedProductos);
                recyclerViewProductos.setAdapter(adaptadorCarito);
                adaptadorCarito.notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
                System.out.println("CARRRRRRRITO: " + selectedProductos);
            }

            comprar = findViewById(R.id.comprar);

            comprar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(carrito.this,finalizarcompra.class);
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onClick(View v) {
        Intent intent  = new Intent(this,Botiga.class);
        startActivity(intent);
    }

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