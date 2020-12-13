package com.practicaClases.sendmeal.model;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;

import java.util.Objects;


@Entity(foreignKeys = @ForeignKey(entity = Pedido.class,
        parentColumns = "id_pedido",
        childColumns = "idPedido"))
public class Plato {
    @PrimaryKey(autoGenerate = true)
    @Expose
    private Long id_plato;
    @Expose
    private String titulo, descripcion;
    @Expose
    private Double precio;
    @Expose
    private Integer calorías;
    @Expose
    private Long idPedido;
    @Expose
    private Uri uriImagen;


    public Plato(String titulo, String descripcion, Double precio, Integer calorías, Uri uriImagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorías = calorías;
        //Ver si no agregaron foto
        if(uriImagen==null){
            this.uriImagen=null;
        }else{
            this.uriImagen = uriImagen;
        }

    }

    public Uri getUriImagen() {
        return uriImagen;
    }

    public void setUriImagen(Uri uriImagen) {
        this.uriImagen = uriImagen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plato)) return false;
        Plato plato = (Plato) o;
        return getTitulo().equals(plato.getTitulo()) &&
                getDescripcion().equals(plato.getDescripcion()) &&
                getPrecio().equals(plato.getPrecio()) &&
                getCalorías().equals(plato.getCalorías());
    }

    public Long getId_plato() {
        return id_plato;
    }

    public void setId_plato(Long id_plato) {
        this.id_plato = id_plato;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCalorías() {
        return calorías;
    }

    public void setCalorías(Integer calorías) {
        this.calorías = calorías;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
    }
}
