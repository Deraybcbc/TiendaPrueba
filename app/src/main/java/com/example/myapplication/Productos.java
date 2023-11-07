package com.example.myapplication;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Productos implements Serializable {
    @SerializedName("id_producte")
    private int id_producte;

    @SerializedName("nom")
    private String nom;

    @SerializedName("descripcio")
    private String descripcio;

    @SerializedName("preu")
    private float preu;

    @SerializedName("estat")
    @JsonAdapter(ConvertirBoolean.class)
    private boolean estat;

    @SerializedName("foto")
    private String foto;

    @SerializedName("cantidad")
    private int contador;

    @SerializedName("tipus_producte")
    private String tipus_producte;

    public Productos() {
    }

    public Productos(int id_producte, int contador) {
        this.id_producte = id_producte;
        this.contador = contador;
    }

    public int getId_producte() {
        return id_producte;
    }

    public void setId_producte(int id_producte) {
        this.id_producte = id_producte;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public boolean isEstat() {
        return estat;
    }

    public void setEstat(boolean estat) {
        this.estat = estat;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }


    public String getTipus_producte() {
        return tipus_producte;
    }

    public void setTipus_producte(String tipus_producte) {
        this.tipus_producte = tipus_producte;
    }


}
