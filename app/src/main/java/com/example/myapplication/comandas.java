package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class comandas extends AppCompatActivity {

    private static final String URL = "http://192.168.1.35:3044/";
    //private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044/";


    private RecyclerView recyclerViewComandas;
    private RecyclerViewAdaptadorComandas adaptadorComandas;
    private ArrayList<RecibirComandas> comandasList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandas);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        comandasList = new ArrayList<>();


        mSocket.connect();
        mSocket.on(mSocket.EVENT_CONNECT,onConnect);
        mSocket.emit("ComandasUsuari");
        mSocket.on("getComandasUsuari", Recibir);
        //mSocket.emit("getComandasUsuari");




        recyclerViewComandas = (RecyclerView) findViewById(R.id.Comandas);
        recyclerViewComandas.setLayoutManager(new LinearLayoutManager(this));
        adaptadorComandas = new RecyclerViewAdaptadorComandas(comandasList);
        recyclerViewComandas.setAdapter(adaptadorComandas);
    }

    private Emitter.Listener onConnect = args -> {
        runOnUiThread(()->{
            Toast.makeText(this, "Conectado", Toast.LENGTH_SHORT).show();
        });
    };

    private Emitter.Listener Recibir = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(()->{
                Toast.makeText(comandas.this, "recibir", Toast.LENGTH_SHORT).show();
            });
            Log.d("JOSNDATa", "JSON Recibido: " + args[0]);

           /*JSONArray comandasArray = (JSONArray) args[0]; // Suponiendo que el servidor envía un array JSON

            Log.d("SocketData", "JSON Recibido: " + comandasArray.toString());


            for (int i=0; i < comandasArray.length();i++){
                try {
                    JSONObject comanda = comandasArray.getJSONObject(i);

                    int id_comanda = comanda.getInt("id_comanda");
                    int id_usuario = comanda.getInt("id_usuari");
                    String data_comanda = comanda.getString("data_comanda");
                    String estat = comanda.getString("estat");
                    String nombre_usuario = comanda.getString("nombre_usuario");

                    JSONArray productosArray = comanda.getJSONArray("productos");
                    List<Productos> productosList = new ArrayList<>();

                    for (int j=0; j < productosArray.length();j++){
                        JSONObject producto = productosArray.getJSONObject(j);

                        int id_producte=producto.getInt("id_producte");
                        String nom = producto.getString("nom");
                        Productos prod = new Productos(id_producte,nom);
                        productosList.add(prod);

                    }

                    // Crea un objeto de RecibirComandas con los datos obtenidos
                    RecibirComandas recibirComanda = new RecibirComandas(id_comanda, id_usuario, data_comanda, estat, nombre_usuario, productosList);

                    comandasList.add(recibirComanda);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }*/
        }
    };


    private Socket mSocket;

    {
        try {
            mSocket = IO.socket(URL);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    //ACCION DE FLECHA
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this,Botiga.class);
        startActivity(intent);
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mSocket != null && mSocket.connected()) {
            mSocket.disconnect();
        }
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