package com.practicaClases.sendmeal.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Pedido {
    @PrimaryKey(autoGenerate = true)
    private Long id_pedido;
    private String correoElectronico, direccion;
    private Boolean envio;
    //private List<Plato> listaPlatos;
    private List<Long> listaPlatos;

    public Pedido (String correoElectronico, String direccion, Boolean envio, List<Long> listaPlatos){

        this.correoElectronico = correoElectronico;
        this.direccion = direccion;
        this.envio = envio;
        this.listaPlatos = listaPlatos;

    }


}


