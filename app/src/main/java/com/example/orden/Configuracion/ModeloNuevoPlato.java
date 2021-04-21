package com.example.orden.Configuracion;

public class ModeloNuevoPlato {

    private String nombre;
    private int Precio;
    private Boolean selec;

    public ModeloNuevoPlato(String nombre, int precio,Boolean Selec) {
        this.nombre = nombre;
        Precio = precio;
        this.selec = Selec;
    }

    public Boolean getSelec() {
        return selec;
    }

    public void setSelec(Boolean selec) {
        this.selec = selec;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }
}
