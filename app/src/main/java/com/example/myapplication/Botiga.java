package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ActivityBotigaBinding;

import java.util.ArrayList;
import java.util.List;

public class Botiga extends AppCompatActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBotigaBinding binding;

    private RecyclerView recyclerViewProductos;
    private RecyclerViewAdaptador adaptadorProductos;

    public String usuari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBotigaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Para mostrar los Productos con el RecyclerView
        recyclerViewProductos=(RecyclerView)findViewById(R.id.Productos);
        recyclerViewProductos.setLayoutManager(new LinearLayoutManager(this));
        adaptadorProductos=new RecyclerViewAdaptador(obtenerProductos());
        recyclerViewProductos.setAdapter(adaptadorProductos);



        usuari = getIntent().getStringExtra("user");

        if(usuari==null){
            Log.d("VACIO","SOLO PARA NO MOSTRAR VACIO");
        }else{
            Toast.makeText(this, "Bienvenido "+usuari, Toast.LENGTH_SHORT).show();
        }
    }

    public List<Productos> obtenerProductos(){
        List<Productos> pro = new ArrayList<>();

        pro.add(new Productos("Coca-Cola","Tamany Gran","19,20 €",R.drawable.cocacola));

        return pro;
    }

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
        }

        return super.onOptionsItemSelected(item);
    }

}