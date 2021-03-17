package com.example.orden.Caja;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.orden.Comanda.Menu;

public class ModeloMenuFirebase extends Menu {

    private String Nombre;
    private int Cantidad;
    private int Precio;
    private String Comentario;


    private int Seleccion;

    public ModeloMenuFirebase() {
    }


    public ModeloMenuFirebase(String nombre, int cantidad, int precio, String comentario) {
        Nombre = nombre;
        Cantidad = cantidad;
        Precio = precio;
        Comentario = comentario;
        Seleccion = 0;
    }


    public void Agregar(){Seleccion+=1;}

    public int getSeleccion() {
        return Seleccion;
    }

    public void setSeleccion(int seleccion) {
        Seleccion = seleccion;
    }



    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getPrecio() {
        return Precio;
    }

    public void setPrecio(int precio) {
        Precio = precio;
    }

    public String getComentario() {
        return Comentario;
    }


    public void setComentario(String comentario) {
        Comentario = comentario;
    }




}
