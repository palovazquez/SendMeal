package com.practicaClases.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

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


    public Pedido (String correoElectronico, String direccion, Boolean envio){


        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.envio = envio;
    }


}


