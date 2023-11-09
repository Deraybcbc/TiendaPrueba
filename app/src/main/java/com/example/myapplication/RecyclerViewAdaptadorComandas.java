package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdaptadorComandas extends RecyclerView.Adapter<RecyclerViewAdaptadorComandas.ViewHolder> {

    private ArrayList<RecibirComandas> comandasList;
    public String productosCompletos;

    @Override
    public RecyclerViewAdaptadorComandas.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_comandas,parent,false);
        RecyclerViewAdaptadorComandas.ViewHolder viewHolder = new RecyclerViewAdaptadorComandas.ViewHolder(view);
        return viewHolder;
    }


    // Método para actualizar la lista de comandas
    public void actualizarLista(ArrayList<RecibirComandas> nuevaLista) {
        comandasList = nuevaLista;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptadorComandas.ViewHolder holder, int position) {
        RecibirComandas comanda = comandasList.get(position);

        productosCompletos="";

        System.out.println("Productos"+comandasList.get(position).getProductos().toString());

        holder.CidC.setText("COMANDA: "+String.valueOf(comanda.getId_comanda()));
        holder.Cdata.setText("DATA COMANDA: "+String.valueOf(comanda.getData_comanda()));
        holder.Cestat.setText("ESTAT: "+String.valueOf(comanda.getEstat()));
        holder.Cnom.setText("USUARIO: "+String.valueOf(comanda.getNombre_usuario()));


        for (Productos producto : comanda.getProductos()) {
            productosCompletos+=producto.getNom()+" Cantidad: "+String.valueOf(producto.getContador())+"\n";
        }

        holder.Cproductos.setText("PRODUCTOS:"+"\n"+productosCompletos);

        /*for(int i=0; i < comanda.getProductos().size();i++){

            //TextView text = new TextView(this);
            //holder.text.setText("PRODUCTOS: "+comanda.getProductos().get(i).getNom());
            holder.Cproductos.setText("PRODUCTOS: "+comanda.getProductos().get(i).getNom());
            holder.Ccantidad.setText("CANTIDAD: "+String.valueOf(comanda.getProductos().get(i).getContador()));
        }*/
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Botiga.class);
                intent.putExtra("id_comanda", comanda.getId_comanda());
                intent.putExtra("id_usuario", comanda.getId_usuari());
                // Agrega otros datos de la comanda a los extras del Intent si es necesario
                v.getContext().startActivity(intent);
            }
        });
*/
    }

    public RecyclerViewAdaptadorComandas(ArrayList<RecibirComandas> comandasList) {
        this.comandasList = comandasList;
        //System.out.println("DENTRORECYCLER: "+comandasList);
    }


    @Override
    public int getItemCount() {
        return comandasList.size();
        //return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView CidC,Cdata,Cestat,Cnom,Cproductos;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            CidC = (TextView) itemView.findViewById(R.id.Cidc);
            Cdata = (TextView) itemView.findViewById(R.id.Cdata);
            Cestat = (TextView) itemView.findViewById(R.id.Cestat);
            Cnom = (TextView) itemView.findViewById(R.id.Cnom);
            Cproductos = (TextView) itemView.findViewById(R.id.Cproductos);

        }
    }
}
