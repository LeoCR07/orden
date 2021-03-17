package com.example.orden.Comanda;

public class Menu extends AccionFactura{

    /** RealTime **/
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference myRef = database.getReference("SodaPoas");

    /** menu **/
    private String Nombre;
    private int Cantidad;
    private int Precio;
    private Categorias categoria;
    private Boolean EleccionMultiple;
    private Boolean Estado;
    private String Comentario;
    private String Preferencia;
    //private int Seleccion;


    /** Constructor **/
    public Menu(String nombre, int precio, Categorias tipo, boolean eleccionMultiple) {
        Nombre = nombre;
        Precio = precio;
        categoria = tipo;
        EleccionMultiple = eleccionMultiple;
        Cantidad = 0;
        Estado = true;
        Comentario = "";
        Preferencia = "";
    }


    public Menu(String nombre, Categorias categoria){
        Nombre = nombre;
        Precio = 0;
        categoria = categoria;
        EleccionMultiple = false;
        Cantidad = 0;
        Estado = true;
        Comentario = "";
        Preferencia = "";
    }

    public Menu() { }

    /*** GETTER ***/

    public String getNombre() {
        return Nombre;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public int getPrecio() {
        return Precio;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public Boolean getEleccionMultiple() {
        return EleccionMultiple;
    }

    public Boolean getEstado() {
        return Estado;
    }

    public String getComentario() {
        return Comentario;
    }

    public String getPreferencia() {
        return Preferencia;
    }

    /** Metodos **/
    public void Aumentar() {
        Cantidad+=1;
    }

    public void Disminuir() {
        Cantidad-=1;
    }

    /** Metodos de firebase **/
    /*

    public void AumentarRTime(String ruta,int cant) {
        cant++;
        myRef.child(ruta).child("cantidad").setValue(cant);
    }

    public void DisminuirRTime(String ruta,int cant) {
        //Disminuye el plato o elimina cuando llegue a cero
        cant--;
        if (cant == 0) {
            myRef.child(ruta).removeValue();
        } else {
            myRef.child(ruta).child("cantidad").setValue(cant);
        }
    }

    public void Agregar(){Seleccion+=1;}

    public int getSeleccion() {
        return Seleccion;
    }

    public void setSeleccion(int seleccion) {
        Seleccion = seleccion;
    }

     */


    /*** Setters ***/
    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }
    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    public void setPreferencia(String preferencia) {
        Preferencia = preferencia;
    }



}

