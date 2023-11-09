package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityBotigaBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Botiga extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBotigaBinding binding;

    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptadorProductos adaptadorProductos;
    public static ApiService apiService;

    private ImageView car;

    public String usuari;

    //private static final String URL = "http://192.168.1.35:3044/";
    private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBotigaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        Call<List<Productos>> call = apiService.ObtenerProductos();

        call.enqueue(new Callback<List<Productos>>() {
            @Override
            public void onResponse(Call<List<Productos>> call, Response<List<Productos>> response) {
                if (response.isSuccessful()) {

                    Log.d("CONEXION", "CONEXION SERVIDOR EXITOSA");


                    List<Productos> listaproductos = response.body();


                    // Obtener una lista de tipos de productos únicos
                    Set<String> tiposProductos = new HashSet<>();
                    for (Productos producto : listaproductos) {
                        if (producto.isEstat()) {
                            tiposProductos.add(producto.getTipus_producte());
                        }
                    }


                    LinearLayout layout = findViewById(R.id.filtrarproducto); // Este sería tu layout donde deseas agregar los botones

                    for (String tipo : tiposProductos) {
                        Button button = new Button(Botiga.this);
                        button.setText(tipo);
                        // Añadir márgenes a los botones
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.setMargins(20, 20, 20, 20); // Establece los márgenes (en este caso, 20px en la parte inferior)

                        button.setLayoutParams(params);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Filtrar productos al hacer clic en el botón del tipo

                                String tipoSeleccionado = ((Button) view).getText().toString();
                                List<Productos> productosFiltrados = filtrarPorTipo(listaproductos, tipoSeleccionado);
                                adaptadorProductos.setProductosFiltrados(productosFiltrados);
                                adaptadorProductos.notifyDataSetChanged();
                            }
                        });
                        layout.addView(button);
                    }


                    //Para mostrar los Productos con el RecyclerView
                    recyclerViewProductos = (RecyclerView) findViewById(R.id.Productos);
                    recyclerViewProductos.setLayoutManager(new LinearLayoutManager(Botiga.this));
                    adaptadorProductos = new RecyclerViewAdaptadorProductos(listaproductos);
                    recyclerViewProductos.setAdapter(adaptadorProductos);

                    //Pillar los datos que hemos seleccionado
                    adaptadorProductos.setOnItemClickListener(new RecyclerViewAdaptadorProductos.OnItemClickListener() {

                        @Override
                        public void onItemClick(List<Productos> listaProductos) {
                            LogicaCarrito();
                        }
                    });
                    adaptadorProductos.notifyDataSetChanged();
                }


            }

            @Override
            public void onFailure(Call<List<Productos>> call, Throwable t) {
                Log.d("ERROR", t.getMessage());
            }
        });


        usuari = getIntent().getStringExtra("user");

        System.out.println("USUARIO: " + usuari);

        if (usuari != null) {
            Toast.makeText(this, "Benvingut " + usuari, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        LogicaCarrito(); // Llamar al método para manejar la lógica del carrito
    }


    private void LogicaCarrito() {
        // Lógica para manejar el carrito
        car = (ImageView) findViewById(R.id.Imgcar);
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Botiga.this, carrito.class);
                intent.putExtra("USER", usuari);
                startActivity(intent);
            }
        });
    }

    private List<Productos> filtrarPorTipo(List<Productos> todosLosProductos, String tipoSeleccionado) {
        List<Productos> productosFiltrados = new ArrayList<>();
        for (Productos producto : todosLosProductos) {
            if (producto.getTipus_producte().equals(tipoSeleccionado) && producto.isEstat()) {
                productosFiltrados.add(producto);
            }
        }
        return productosFiltrados;
    }

    //ACCION DE FLECHA
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, Botiga.class);
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
                intent1.putExtra("infouser", usuari);
                startActivity(intent1);
                return true;
            case R.id.cerrar:
                // Acción para la "Opción 1"
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                ProductoSelecionado.getInstance().clearSelectedProductos();
                finish();
                return true;
            case R.id.comandas:
                Intent intent3 = new Intent(this, comandas.class);
                startActivity(intent3);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}