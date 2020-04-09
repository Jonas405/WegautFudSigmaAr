package com.example.samplestickerapp;

public class Usuario {

    private Integer id;
    private String nombre;
    private int image;

    public Usuario(Integer id, String nombre, int image) {
        this.id = id;
        this.nombre = nombre;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


}
