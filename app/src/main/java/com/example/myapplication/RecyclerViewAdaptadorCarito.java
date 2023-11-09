package com.example.myapplication;

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

import java.util.Base64;
import java.util.List;

public class RecyclerViewAdaptadorCarito extends RecyclerView.Adapter<RecyclerViewAdaptadorCarito.ViewHolder>{
    private List<Productos> productosEnCarrito;



    private LlamadoCambioCantidad llamadoCambioCantidad;


    public RecyclerViewAdaptadorCarito(List<Productos> productos) {
        this.productosEnCarrito = productos;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titulo,Descripcion,precio,cantidad;
        ImageView Imagenpro,mas,menos;
        Button eliminar,comprar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.titCar);
            Descripcion=(TextView)itemView.findViewById(R.id.desCar);
            precio=(TextView)itemView.findViewById(R.id.preCar);
            Imagenpro=(ImageView)itemView.findViewById(R.id.imgCar);
            mas = (ImageView) itemView.findViewById(R.id.masCar);
            menos = (ImageView) itemView.findViewById(R.id.menosCar);
            cantidad = (TextView) itemView.findViewById(R.id.cantCar);
            eliminar = (Button) itemView.findViewById(R.id.delete);
            comprar = (Button) itemView.findViewById(R.id.comprar);
        }
    }


    @Override
    public RecyclerViewAdaptadorCarito.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_carrito,parent,false);
        RecyclerViewAdaptadorCarito.ViewHolder viewHolder = new RecyclerViewAdaptadorCarito.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdaptadorCarito.ViewHolder holder, int position) {

                System.out.println(productosEnCarrito.get(position).getNom());

                System.out.println("TAMAÑO: "+productosEnCarrito.size());


            Productos producto = productosEnCarrito.get(position);

                System.out.println(producto.getNom());

                holder.titulo.setText(producto.getNom());
                holder.Descripcion.setText(producto.getDescripcio());
                holder.precio.setText(String.valueOf(producto.getPreu() + " €"));
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
                        if (llamadoCambioCantidad != null) {
                            llamadoCambioCantidad.CambioCantidad();
                        }
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
                            if (llamadoCambioCantidad != null) {
                                llamadoCambioCantidad.CambioCantidad();
                            }
                        }
                    }
                });

                holder.cantidad.setText(String.valueOf(producto.getContador()));

                holder.eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int posicion = holder.getAdapterPosition();

                        List<Productos> selectedProductos = ProductoSelecionado.getInstance().getSelectedProductos();

                        if (posicion != RecyclerView.NO_POSITION && posicion < selectedProductos.size()) {
                            // Remove the product by position
                            ProductoSelecionado.getInstance().removeSelectedProducto(posicion);
                            notifyItemRemoved(posicion); // Notify the adapter of the item removal
                            System.out.println(selectedProductos.size());
                            Toast.makeText(v.getContext(), "Producto eliminado", Toast.LENGTH_SHORT).show();
                            if (llamadoCambioCantidad != null) {
                                llamadoCambioCantidad.CambioCantidad();
                            }
                        }
                    }
                });

    }
    @Override
    public int getItemCount() {
            return productosEnCarrito.size();
    }
    

    // Declarar una interfaz para el listener de cambios de cantidad
    public interface LlamadoCambioCantidad {
        void CambioCantidad();
    }

    // Método para asignar el listener
    public void AsignarCambioCantidad(LlamadoCambioCantidad listener) {
        this.llamadoCambioCantidad = listener;
    }

}

