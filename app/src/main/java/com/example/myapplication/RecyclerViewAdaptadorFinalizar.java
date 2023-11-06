package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Base64;
import java.util.List;

public class RecyclerViewAdaptadorFinalizar extends RecyclerView.Adapter<RecyclerViewAdaptadorFinalizar.ViewHolder>{

    private List<Productos> productosEnCarrito;
    private OnItemClickListener onItemClickListener;


    @Override
    public RecyclerViewAdaptadorFinalizar.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_finalizar,parent,false);
        RecyclerViewAdaptadorFinalizar.ViewHolder viewHolder = new RecyclerViewAdaptadorFinalizar.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptadorFinalizar.ViewHolder holder, int position) {
        Productos producto = productosEnCarrito.get(position);

        holder.titulo.setText(producto.getNom());
        holder.Descripcion.setText(producto.getDescripcio());
        holder.precio.setText(String.valueOf(producto.getPreu() + " €"));
        //Codificar la imagen

        String base64String = producto.getFoto();
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.Imagenpro.setImageBitmap(decodedByte);


        holder.cantidad.setText(String.valueOf(producto.getContador()));

    }

    @Override
    public int getItemCount() {
        return productosEnCarrito.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titulo,Descripcion,precio,cantidad;
        ImageView Imagenpro;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.titFin);
            Descripcion=(TextView)itemView.findViewById(R.id.desFin);
            precio=(TextView)itemView.findViewById(R.id.preFin);
            Imagenpro=(ImageView)itemView.findViewById(R.id.imgFina);
            cantidad = (TextView) itemView.findViewById(R.id.cantFin);
        }
    }

    public RecyclerViewAdaptadorFinalizar(List<Productos> productos) {
        this.productosEnCarrito = productos;
    }

    public void setOnItemClickListener(RecyclerViewAdaptadorFinalizar.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(List<Productos> listaProductos);
    }

}
