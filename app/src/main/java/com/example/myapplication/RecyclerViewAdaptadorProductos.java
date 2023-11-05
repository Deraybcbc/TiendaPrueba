package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class RecyclerViewAdaptadorProductos extends RecyclerView.Adapter<RecyclerViewAdaptadorProductos.ViewHolder> {

    private List<Productos> productos;

    private OnItemClickListener onItemClickListener;




    //Para cuando le demos clic
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titulo,Descripcion,precio,cantidad;
        ImageView Imagenpro,mas,menos;
        Button carrito;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.TituloC);
            Descripcion=(TextView)itemView.findViewById(R.id.descripcionC);
            precio=(TextView)itemView.findViewById(R.id.precio);
            Imagenpro=(ImageView)itemView.findViewById(R.id.imagenC);
            mas = (ImageView) itemView.findViewById(R.id.mas);
            menos = (ImageView) itemView.findViewById(R.id.menos);
            cantidad = (TextView) itemView.findViewById(R.id.cantidad);
            carrito = (Button) itemView.findViewById(R.id.car);

        }
    }

    public RecyclerViewAdaptadorProductos(List<Productos> productos){
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
        Productos producto = productos.get(position);
        holder.titulo.setText(producto.getNom());
        holder.Descripcion.setText(producto.getDescripcio());
        holder.precio.setText(String.valueOf(producto.getPreu()+" €"));
        //Codificar la imagen

        String base64String = producto.getFoto();
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.Imagenpro.setImageBitmap(decodedByte);

        //Incrementar y disminuir la cantidad
        holder.mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int contador = producto.getContador();
                contador++;
                producto.setContador(contador);
                holder.cantidad.setText(String.valueOf(contador));
            }
        });

        holder.menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int contador = producto.getContador();
                if (contador > 0) {
                    contador--;
                    producto.setContador(contador);
                    holder.cantidad.setText(String.valueOf(contador));
                }
            }
        });


        holder.cantidad.setText(String.valueOf(producto.getContador()));

        //Para cuando le damos clic al carrito
        holder.carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Productos> productosEnCarrito = new ArrayList<>();

                //Añadir producto selecionado
                for (Productos producto : productos) {
                    if (producto.getContador() > 0) {
                        productosEnCarrito.add(producto);
                        System.out.println(productosEnCarrito);
                    }
                }

                //Añadir producto seleccionado a la clase productoSelecionado
                if (!productosEnCarrito.isEmpty()) {
                    ProductoSelecionado.getInstance().addSelectedProductos(producto);
                    Toast.makeText(v.getContext(), "Productos añadidos", Toast.LENGTH_SHORT).show();
                }

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(productosEnCarrito);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return productos.size();
    }

    public interface OnItemClickListener {
        void onItemClick(List<Productos> listaProductos);
    }
}
