package com.practicaClases.sendmeal.model;

public class Plato {
  private int id_plato;
  private String titulo, descripcion;
  private Double precio;
  private Integer calorías;
  private static int idCounter = 0;

    public Plato(String titulo, String descripcion, Double precio, Integer calorías) {
        this.id_plato = idCounter++;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.calorías = calorías;
    }

    public int getId_plato() {
        return id_plato;
    }

    public void setId_plato(int id_plato) {
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
}
