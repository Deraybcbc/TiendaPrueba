package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

public class UsuariTrobat {
    @SerializedName("id_usuari")
    private int id_usuari;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("nom")
    private String nom;

    @SerializedName("cognoms")
    private String cognoms;


    @SerializedName("passwd")
    private String passwd;

    @SerializedName("nTargeta")
    private String nTargeta;

    @SerializedName("CVV")
    private String CVV;

    @SerializedName("DataCaducitat")
    private String DataCaducitat;

    @SerializedName("correu")
    private String correu;

    public UsuariTrobat() {
    }

    public UsuariTrobat(int id_usuari, String usuario, String nom, String cognoms, String passwd, String nTargeta, String CVV, String dataCaducitat, String correu) {
        this.id_usuari = id_usuari;
        this.usuario = usuario;
        this.nom = nom;
        this.cognoms = cognoms;
        this.passwd = passwd;
        this.nTargeta = nTargeta;
        this.CVV = CVV;
        DataCaducitat = dataCaducitat;
        this.correu = correu;
    }

    public UsuariTrobat(String usuario) {
        this.usuario = usuario;
    }

    public UsuariTrobat(String usuario, String passwd) {
        this.usuario = usuario;
        this.passwd = passwd;
    }

    public int getId_usuari() {
        return id_usuari;
    }

    public void setId_usuari(int id_usuari) {
        this.id_usuari = id_usuari;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognoms() {
        return cognoms;
    }

    public void setCognoms(String cognoms) {
        this.cognoms = cognoms;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getnTargeta() {
        return nTargeta;
    }

    public void setnTargeta(String nTargeta) {
        this.nTargeta = nTargeta;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public String getDataCaducitat() {
        return DataCaducitat;
    }

    public void setDataCaducitat(String dataCaducitat) {
        DataCaducitat = dataCaducitat;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }
}
