package com.example.myapplication;

public class Productos {
    private String titulo;
    private String Descripcion;
    private String precio;
    private int Imagenpro;

    public Productos() {
    }

    public Productos(String titulo, String descripcion, String precio, int imagenpro) {
        this.titulo = titulo;
        this.Descripcion = descripcion;
        this.precio = precio;
        this.Imagenpro = imagenpro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public int getImagenpro() {
        return Imagenpro;
    }

    public void setImagenpro(int imagenpro) {
        Imagenpro = imagenpro;
    }
}
