package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class ProductosEnviarID {

    @SerializedName("id")
    private int Idproducto;

    @SerializedName("cantidad")
    private int cantidad;

    public ProductosEnviarID(int idproducto, int cantidad) {
        Idproducto = idproducto;
        this.cantidad = cantidad;
    }

    public int getIdproducto() {
        return Idproducto;
    }

    public void setIdproducto(int idproducto) {
        Idproducto = idproducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
