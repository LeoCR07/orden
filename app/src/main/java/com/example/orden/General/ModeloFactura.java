package com.example.orden.General;

public class ModeloFactura {

    private String nombre;
    private int Imagen;
    private String ID;
    private String path;



    /** Facturas **/
    public ModeloFactura(int imagen,String nombre, String ID,String path) {
        this.nombre = nombre;
        Imagen = imagen;
        this.ID = ID;
        this.path = path;
    }

    /** Mesas activas **/
    public ModeloFactura(String nombre,String path,int imagen) {
        Imagen = imagen;
        this.path = path;
        this.nombre = nombre;
    }

    /** Mesas Inactivas y confijuracion **/
    public ModeloFactura(int imagen,String nombre) {
        Imagen = imagen;
        this.nombre = nombre;
    }

    public ModeloFactura(int imagen,CharSequence nombre) {
        Imagen = imagen;
        this.nombre =(String) nombre;
    }

    public String getPath() {
        return path;
    }

    public String getNombre() {
        return nombre;
    }

    public int getImagen() {
        return Imagen;
    }

    public String getID() {
        return this.ID;
    }

}
