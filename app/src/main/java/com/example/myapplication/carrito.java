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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class carrito extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptadorCarito adaptadorCarito;
    private List<Productos> selectedProductos;

    private double preuTotal=0.0;

    //private static final String URL = "http://192.168.1.35:3044/";
    private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044";
    public static ApiService apiService;
    private Button comprar;
    private TextView preu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerViewProductos = (RecyclerView) findViewById(R.id.carritoRecycler);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(carrito.this));

        selectedProductos = ProductoSelecionado.getInstance().getSelectedProductos();

        //Calcular precio de los productos añadidos al carrito
        CalcularPrecio();

        if (selectedProductos == null || selectedProductos.isEmpty()) {
            Toast.makeText(this, "Cistella Buida", Toast.LENGTH_SHORT).show();
        } else {
            adaptadorCarito = new RecyclerViewAdaptadorCarito(selectedProductos);
            recyclerViewProductos.setAdapter(adaptadorCarito);
            adaptadorCarito.notifyDataSetChanged(); // Notificar al RecyclerView que los datos han cambiado
            Log.d("Carrito: ","Productos en carrito"+selectedProductos);
            //Para cuando suma o resta la cantidad
            adaptadorCarito.AsignarCambioCantidad(new RecyclerViewAdaptadorCarito.LlamadoCambioCantidad() {
                @Override
                public void CambioCantidad() {
                    preuTotal = 0.0;
                    CalcularPrecio();
                }
            });

        }

        comprar = findViewById(R.id.comprar);

        comprar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(selectedProductos == null||selectedProductos.isEmpty()){
                    Toast.makeText(carrito.this, "Cistella Buida", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(carrito.this, finalizarcompra.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Botiga.class);
        startActivity(intent);
    }

    private void CalcularPrecio(){
        List<Productos> ProductosSellecionados = ProductoSelecionado.getInstance().getSelectedProductos();

        for(Productos productos : ProductosSellecionados){
            int cantidad = productos.getContador();
            double precio =  productos.getPreu();
            double PrecioProdcuto = cantidad * precio;
            preuTotal += PrecioProdcuto;
        }
        TextView precioTotal = (TextView) findViewById(R.id.precioCarrito);
        precioTotal.setText(String.valueOf(preuTotal)+" €");
    }

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