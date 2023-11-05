package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.List;

public class finalizarcompra extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://192.168.1.35:3001/";
    //private static final String URL = "http://192.168.205.213:3001/";


    public static ApiService apiService;

    public ImageView atrasCarrito;

    private TextView textoFecha;
    private TextView textoHora;

    private RecyclerView recyclerViewFinalizar;
    private RecyclerViewAdaptadorFinalizar adaptadorFinalizar;

    private List<Productos> selectedProductos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalizarcompra);

        //MENU
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //BUSCAR IDS
        textoFecha = (TextView) findViewById(R.id.FData);
        textoHora = (TextView)findViewById(R.id.Fhora);

        //VOLVER CARRITO
        atrasCarrito = (ImageView) findViewById(R.id.atrascarrito);
        atrasCarrito.setOnClickListener(this);

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
                        Toast.makeText(finalizarcompra.this, "Comando creada", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(finalizarcompra.this,Botiga.class);
                        startActivity(intent);
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

        //RecyclerView
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
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent =  new Intent(this,carrito.class);
        startActivity(intent);
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
                String selectedDate = selectedDayStr + "/" + selectedMonthStr + "/" + selectedYear;
                textoFecha.setText(selectedDate);
            }
        }, day, month, year);

        datePickerDialog.show();
    }
/*
    private void ObtenerHora(){
        Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minutos = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora = hourOfDay+":"+minute;
                textoHora.setText(hora);
            }
        },hora,minutos);
        timePickerDialog.show();
    }*/
}