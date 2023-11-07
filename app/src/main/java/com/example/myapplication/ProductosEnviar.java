package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductosEnviar {
    @SerializedName("productos")
    private List<ProductosEnviarID> productos;

    @SerializedName("hora_recollida")
    private String hora_recollida;

    @SerializedName("dia_recollida")
    private String dia_recollida;

    @SerializedName("preu")
    private double preu;


    public ProductosEnviar(List<ProductosEnviarID> productes, String hora_recollida, String dia_recollida) {
        this.productos = productes;
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

    public List<ProductosEnviarID> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductosEnviarID> productos) {
        this.productos = productos;
    }
}
