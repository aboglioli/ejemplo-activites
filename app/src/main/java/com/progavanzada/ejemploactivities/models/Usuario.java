package com.progavanzada.ejemploactivities.models;

/**
 * Created by aboglioli on 11/22/17.
 */

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String clave;

    public Usuario() {

    }

    public Usuario(String nombre, String email, String clave) {
        this.nombre = nombre;
        this.email = email;
        this.clave = clave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
