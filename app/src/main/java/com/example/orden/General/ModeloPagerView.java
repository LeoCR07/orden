package com.example.orden.General;

public class ModeloPagerView {

    private int imagen;
    private String titulo;
    private String desc;

    public ModeloPagerView(int imagen, String titulo, String desc) {
        this.imagen = imagen;
        this.titulo = titulo;
        this.desc = desc;
    }

    public int getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDesc() {
        return desc;
    }
}
