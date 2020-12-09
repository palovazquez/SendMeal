package com.practicaClases.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;

import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    @Expose
    private Long id_pedido;
    @Expose
    private String correoElectronico, direccion;
    @Expose
    private Boolean envio;
    @Expose
    private LatLng ubicacion;

    public Pedido(){}

    public Pedido (String correoElectronico, String direccion, Boolean envio, LatLng  ubic){


        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.envio = envio;
        this.ubicacion = ubic;

    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Long getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(Long id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Boolean getEnvio() {
        return envio;
    }

    public void setEnvio(Boolean envio) {
        this.envio = envio;
    }

    public LatLng  getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng  ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}


