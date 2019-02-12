package com.mortega.basesdedatos.base;

import java.io.Serializable;

public class Vehiculos implements Serializable {
    //Datos del Objeto

    public enum Tipo {
        COMPACTO,
        SEDAN,
        CUPE,
        SUV,
        TODOTERRENO,
        RANCHERA,
        DEPORTIVO,
        HYPER
    }

    public enum Motor {
        GASOLINA,
        DIESEL,
        GLE,
        HIBRIDO,
        ELECTRICO
    }

    public enum Cambio {
        AUTOMATICO,
        MANUAL
    }

    private long id;
    private String marca;
    private String modelo;
    private Tipo tipo;
    private Motor motor;
    private int anio;
    private Cambio cambio;
    private String imagen;

    public long getId() {return id;}
    public void setId(long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Tipo getTipo() {
        return tipo;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Motor getMotor() {
        return motor;
    }
    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public int getAnio() {
        return anio;
    }
    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Cambio getCambio() {
        return cambio;
    }
    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public String toString() {
        return (marca+" "+modelo);
    }

    //Agregar imagenes
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

}
