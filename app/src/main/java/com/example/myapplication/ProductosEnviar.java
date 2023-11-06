package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class ProductosEnviar {
    @SerializedName("id")
    private int Idproducto;

    @SerializedName("cantidad")
    private int cantidad;

    @SerializedName("hora_recollida")
    private String hora_recollida;

    @SerializedName("dia_recollida")
    private String dia_recollida;

    @SerializedName("preu")
    private double preu;


    public ProductosEnviar(int idproducto, int cantidad,String hora_recollida, String dia_recollida) {
        Idproducto = idproducto;
        this.cantidad = cantidad;
        this.hora_recollida = hora_recollida;
        this.dia_recollida = dia_recollida;
    }

    public String getHora_recollida() {
        return hora_recollida;
    }

    public void setHora_recollida(String hora_recollida) {
        this.hora_recollida = hora_recollida;
    }

    public String getDia_recollida() {
        return dia_recollida;
    }

    public void setDia_recollida(String dia_recollida) {
        this.dia_recollida = dia_recollida;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
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
