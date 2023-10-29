package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo,Descripcion,precio;
        ImageView Imagenpro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.TituloC);
            Descripcion=(TextView)itemView.findViewById(R.id.descripcionC);
            precio=(TextView)itemView.findViewById(R.id.precio);
            Imagenpro=(ImageView)itemView.findViewById(R.id.imagenC);

        }
    }

    public List<Productos> productos;

    public RecyclerViewAdaptador(List<Productos> productos){
        this.productos = productos;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_productos,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.titulo.setText(productos.get(position).getTitulo());
        holder.Descripcion.setText(productos.get(position).getDescripcion());
        holder.precio.setText(productos.get(position).getPrecio());
        holder.Imagenpro.setImageResource(productos.get(position).getImagenpro());

    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}
