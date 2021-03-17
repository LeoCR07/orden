package com.example.orden.Comanda;

import java.util.LinkedList;

public class Factura {

    private int ID;
    int NumeroMesa;
    private int NCuentas; //Numero de Cuentas en una factura
    private LinkedList<Menu>Orden;
    private int SubTotal;
    private int Total;

    public Factura(int ID, LinkedList<Menu> orden) {
        this.ID = ID;
        Orden = orden;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public LinkedList<Menu> getOrden() {
        return Orden;
    }

    public void setOrden(LinkedList<Menu> orden) {
        Orden = orden;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
