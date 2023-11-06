package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class finalizarcompra extends AppCompatActivity {

    private static final String URL = "http://192.168.1.35:3001/";
    //private static final String URL = "http://192.168.205.213:3001/";


    public static ApiService apiService;

    public ImageView atrasCarrito;

    private TextView textoFecha;
    private TextView textoHora;

    private RecyclerView recyclerViewFinalizar;

    private RecyclerViewAdaptadorFinalizar adaptadorFinalizar;


    private List<Productos> selectedProductos;

    private double preuTotal=0.0;

    private String selectedDate;
    private String Hora;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizarcompra);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BUSCAR IDS
        textoFecha = (TextView) findViewById(R.id.FData);
        textoHora = (TextView)findViewById(R.id.Fhora);

        //ALERTA DE COMANDA
        Button pagar = (Button) findViewById(R.id.pagar);
        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(finalizarcompra.this);
                builder.setTitle("Comanda");
                builder.setMessage("Esteu segur de crear aquesta comanda");

                // Agregar botones y lógica para manejar los clics
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Hacer el retrofit

                        Retrofit retrofit =  new Retrofit.Builder()
                                .baseUrl(URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        apiService = retrofit.create(ApiService.class);

                        List<Productos> selectedProducts = ProductoSelecionado.getInstance().getSelectedProductos();
                        List<ProductosEnviar> listaProductos = new ArrayList<>();

                        for (Productos productos: selectedProducts){
                            int id = productos.getId_producte();
                            int cantidad = productos.getContador();

                            ProductosEnviar productosEnviar = new ProductosEnviar(id,cantidad,Hora,selectedDate);
                            listaProductos.add(productosEnviar);
                        }

                        Call<Respuesta> call = apiService.EnviarComando(listaProductos);

                        call.enqueue(new Callback<Respuesta>() {
                            @Override
                            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                                if(response.isSuccessful()){
                                    Log.d("CONEXION","CONECTADO CON EL SERVIDOR");


                                    Respuesta r = response.body();

                                    if(r.isAutoritzacio()){
                                        Log.d("COMANDA","COMANDO HECHA");
                                        Toast.makeText(finalizarcompra.this, "Comando creada", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(finalizarcompra.this,Botiga.class);
                                        startActivity(intent);
                                        ProductoSelecionado.getInstance().clearSelectedProductos();
                                    }

                                    else{
                                        Log.d("COMANDO","FALLO AL HACER LA COMANDA");
                                    }


                                }
                            }

                            @Override
                            public void onFailure(Call<Respuesta> call, Throwable t) {
                                Log.e("ERROR",t.getMessage());
                            }
                        });
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lógica cuando el usuario hace clic en "Cancelar"
                        // Por ejemplo: cerrar el cuadro de diálogo
                        Toast.makeText(finalizarcompra.this, "Comanda cancelada", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                // Mostrar el cuadro de diálogo
                builder.show();

            }
        });

        ImageButton BFecha = (ImageButton) findViewById(R.id.BFecha);
        BFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerFecha();
            }
        });

        ImageButton BHora = (ImageButton) findViewById(R.id.BHora);
        BHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObtenerHora();
            }
        });

        CalcularPrecio();
        //ObtenerNumeroTarjeta();

        //RecyclerView Productos Seleccionados
        recyclerViewFinalizar = (RecyclerView) findViewById(R.id.PagarProductos);
        recyclerViewFinalizar.setLayoutManager(new LinearLayoutManager(this));

        selectedProductos = ProductoSelecionado.getInstance().getSelectedProductos();
        System.out.println("FINALZAR: "+selectedProductos);
        adaptadorFinalizar = new RecyclerViewAdaptadorFinalizar(selectedProductos);
        recyclerViewFinalizar.setAdapter(adaptadorFinalizar);

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

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent =  new Intent(this,carrito.class);
        startActivity(intent);
        return true;
    }


    private void ObtenerFecha() {
        /*
        //Configuracion del Idioma en español
        Locale locale = new Locale("es", "ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());*/

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear  , int selectedMonth, int selectedDay) {
                //Para poner un 0 a los dias y meses que solo tenga un numero
                String selectedMonthStr =String.valueOf(selectedMonth+1);
                String selectedDayStr=String.valueOf(selectedDay);

                if ((selectedMonth+1)<10){
                    selectedMonthStr = "0"+selectedMonthStr;
                }
                if(selectedDay <10){
                    selectedDayStr = "0"+selectedDayStr;
                }

                // Procesa la fecha seleccionada aquí y actualiza el contenido del EditText
                selectedDate = selectedDayStr + "/" + selectedMonthStr + "/" + selectedYear;
                textoFecha.setText(selectedDate);
            }
        }, day, month, year);

        datePickerDialog.show();
    }

    private void ObtenerHora(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(finalizarcompra.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Hora = hourOfDay+":"+minute;
                textoHora.setText(Hora);
            }
        },hora,minutos,true);
        timePickerDialog.show();
    }

    private void CalcularPrecio(){
        List<Productos> ProductosSellecionados = ProductoSelecionado.getInstance().getSelectedProductos();

        for(Productos productos : ProductosSellecionados){
            int cantidad = productos.getContador();
            double precio =  productos.getPreu();
            double PrecioProdcuto = cantidad * precio;
            preuTotal += PrecioProdcuto;
        }
        TextView precioTotal = (TextView) findViewById(R.id.Fprecio);
        precioTotal.setText(String.valueOf(preuTotal)+" €");
    }
/*
    public void ObtenerNumeroTarjeta(){
        List<UsuariTrobat> usuariTrobats = new ArrayList<>();
        String NTarjeta="";
        for (int i = 0; i < usuariTrobats.size();i++){
            NTarjeta = usuariTrobats.get(i).getnTargeta();
            System.out.println(usuariTrobats.get(i).getnTargeta());
            System.out.println(usuariTrobat.getnTargeta());
        }

        TextView NumTar = (TextView) findViewById(R.id.FNTarjeta);
        NumTar.setText(NTarjeta);
    }*/


}