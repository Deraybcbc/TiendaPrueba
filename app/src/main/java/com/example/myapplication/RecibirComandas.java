package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecibirComandas {
    @SerializedName("id_comanda")
    private int id_comanda;
    @SerializedName("id_usuari")
    private int id_usuari;
    @SerializedName("data_comanda")
    private String data_comanda;
    @SerializedName("estat")
    private String estat;
    @SerializedName("nombre_usuario")
    private String nombre_usuario;
    @SerializedName("productos")
    private List<Productos> productos;

    public RecibirComandas(int id_comanda, int id_usuari, String data_comanda, String estat, String nombre_usuario, List<Productos> productos) {
        this.id_comanda = id_comanda;
        this.id_usuari = id_usuari;
        this.data_comanda = data_comanda;
        this.estat = estat;
        this.nombre_usuario = nombre_usuario;
        this.productos = productos;
    }

    public int getId_comanda() {
        return id_comanda;
    }

    public void setId_comanda(int id_comanda) {
        this.id_comanda = id_comanda;
    }

    public int getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(int id_usuari) {
        this.id_usuari = id_usuari;
    }

    public String getData_comanda() {
        return data_comanda;
    }

    public void setData_comanda(String data_comanda) {
        this.data_comanda = data_comanda;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public List<Productos> getProductos() {
        return productos;
    }

    public void setProductos(List<Productos> productos) {
        this.productos = productos;
    }
}
