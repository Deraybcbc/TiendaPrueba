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

    @SerializedName("dades_pagament")
    private String dades_pagament;

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

    public String getDades_pagament() {
        return dades_pagament;
    }

    public void setDades_pagament(String dades_pagament) {
        this.dades_pagament = dades_pagament;
    }
}
