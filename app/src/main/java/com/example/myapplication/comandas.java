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
import java.util.Optional;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class comandas extends AppCompatActivity {

    //private static final String URL = "http://192.168.1.35:3044/";
    private static final String URL = "http://pfcgrup7.dam.inspedralbes.cat:3044/";


    private RecyclerView recyclerViewComandas;
    private RecyclerViewAdaptadorComandas adaptadorComandas;

    private ArrayList<RecibirComandas> comandasList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comandas);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mSocket.connect();
        mSocket.on(mSocket.EVENT_CONNECT, onConnect);
        mSocket.emit("ComandasUsuari");
        mSocket.on("getComandasUsuari", Recibir);
        //mSocket.on("cambioEstado", Cambiarestado);


    }

    private Emitter.Listener onConnect = args -> {
        runOnUiThread(() -> {
            Log.d("SOCKET","Conectado");
        });
    };
    /*
    private Emitter.Listener Cambiarestado = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            int idRecibido=(int) args[0];
            String estatRecibido= (String) args[1];

            Optional<RecibirComandas> id = comandasList.stream()
                    .filter(customer ->  customer.getId_comanda() == idRecibido)
                    .findAny();
            id.ifPresent(comanda -> comanda.setEstat(estatRecibido));
            adaptadorComandas.notifyDataSetChanged();
        }
    };*/



    private Emitter.Listener Recibir = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            JSONArray comandasArray = (JSONArray) args[0]; // Suponiendo que el servidor envía un array JSON

            Log.d("SocketData", "JSON Recibido: " + comandasArray.toString());


            for (int i = 0; i < comandasArray.length(); i++) {
                if (comandasArray.isNull(i)){
                    Toast.makeText(comandas.this, "No has fet cap comanda", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        JSONObject comanda = comandasArray.getJSONObject(i);

                        int id_comanda = comanda.getInt("id_comanda");
                        int id_usuario = comanda.getInt("id_usuari");
                        String data_comanda = comanda.getString("data_comanda");
                        String estat = comanda.getString("estat");
                        String nombre_usuario = comanda.getString("nombre_usuario");

                        JSONArray productosArray = comanda.getJSONArray("productos");
                        List<Productos> productosList = new ArrayList<>();

                        for (int j = 0; j < productosArray.length(); j++) {
                            JSONObject producto = productosArray.getJSONObject(j);

                            String nom = producto.getString("nombre_producto");
                            int cantidad = producto.getInt("quantitat");
                            Productos prod = new Productos(nom, cantidad);
                            productosList.add(prod);

                        }

                        // Crea un objeto de RecibirComandas con los datos obtenidos
                        RecibirComandas recibirComanda = new RecibirComandas(id_comanda, id_usuario, data_comanda, estat, nombre_usuario, productosList);
                        System.out.println("COMANDA: " + recibirComanda.getNombre_usuario());

                        comandasList.add(recibirComanda);

                        runOnUiThread(() -> {
                            recyclerViewComandas = (RecyclerView) findViewById(R.id.Comandas);
                            recyclerViewComandas.setLayoutManager(new LinearLayoutManager(comandas.this));
                            adaptadorComandas = new RecyclerViewAdaptadorComandas(comandasList);
                            recyclerViewComandas.setAdapter(adaptadorComandas);
                            adaptadorComandas.notifyDataSetChanged();
                        });


                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
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
        Intent intent = new Intent(this, Botiga.class);
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