package com.example.orden.Comanda;

import com.example.orden.Configuracion.ModeloNuevoPlato;

import java.util.ArrayList;

public class Menu{

    /************ Definiciones de las propiedades mas abstractas de la clase menu ******************/

    /* Tipo */ /** Un plato de cualquier menu de cualquier restaunte tiene varias posibiliades a la hora de perdir lo y prepararlo
     ,por ejemplo un batido tiene la posiblidad que su preparacion sea en agua o en leche
     cuando esto sucede el valor que almacena la propiedad tipo sera "unica" ya que solo una opcion es valida.
     Cuando el plato no posea ninguna variacion signiificativa a la hora de preparlos sera "simple" como una orden de arroz
     y cuando un plato posea la posibilidad de elegir varias acompañamientos sera multiple ej: un gallo pinto que se pueda elegir
     con varias acompañamientos*/

    /*Opciones */ /**es un array generico(ModeloNuevoPlato) que guarda las variaciones o acompañamientos que posean los platos
     que sean multiples o unicos ej: aqui es donde se almacera los valores de agua y leche del objeto fresa con sus respectivos precio **/

    /*Categoria*/ /**Para mostrar los platos y guardarlos en firebase se ubican por categorias(Almuerzo,entrada,desayuno...)***/

    /*Sub*/ /**Toda categoria tiene 4 subcategoria ej: la categoria Bebida posee: bebida caliente ,gaseoso,batido y Licores
     en el futuro me gustaria ser mas robusto esta propiedad dejando por liberdad al usuario que crea las subcategoria que quiera
     y solo limitarlos con las categorias pero dandole liberta en las subcategoria**/

    /*Estado*/ /** Permite bloquear los platos cuando se acaben para no ofrecerlos*/

    /*Seleccion*//** contador que nunca va ser mayor a cantidad y permitira disminuir la cantidad
     ej: en selecion vale 2 y cantidad vale 5 entoces cantidad valdra 3 */





    /** RealTime **/
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();
    //private DatabaseReference myRef = database.getReference("SodaPoas");

    /** ID **/
    private int id;
    private String ID;
   // private static int contador;

    /** menu **/
    private String Nombre;
    private int Cantidad;
    private int Precio;
    private Categorias categoria;
    private String category;
    private Boolean EleccionMultiple;
    private Boolean Estado;
    private String Comentario;
    private String Preferencia;

    /** Beta **/
    private ArrayList<ModeloNuevoPlato>Opciones;
    private String Tipo;
    private String Sub;
    private int Seleccion;


    /** multiple y unica ***/
    public Menu(String nombre, int precio, String categoria, Boolean estado, ArrayList<ModeloNuevoPlato> opciones, String tipo) {
        Nombre = nombre;
        Precio = precio;
        this.category= categoria;
        Estado = estado;
        Opciones = opciones;
        Tipo = tipo;
    }



    /** Crear un plato simple cambiar **/
    public Menu(String nombre, int precio, String categoria, Boolean estado, String tipo) {
        Nombre = nombre;
        Precio = precio;
        this.category= categoria;
        Estado = estado;
        Tipo = tipo;
    }

    /******************************** Multiple y unica ***********************************/
    /**Se usa en el metodo CreacionObjMenu en ComandaActity para obtner la informacion de fireBase y enviarla a Salon en firebase**/
    public Menu(Boolean estado,String nombre, int precio, String sub,String tipo,String categoria,String comentario,ArrayList<ModeloNuevoPlato>lista) {
        Nombre = nombre;
        Precio = precio;
        this.Comentario = comentario;
        this.category= categoria;
        this.Sub = sub;
        Estado = estado;
        Tipo = tipo;
        this.Opciones = lista;
    }



    /***********************************    Simple    ***************************************/
    /** Se utiliza para crear un nuevo plato en AgregarPlato2Activity **/
    public Menu(Boolean estado,String nombre, int precio, String sub,String tipo,String categoria) {
        Nombre = nombre;
        Precio = precio;
        this.category= categoria;
        this.Sub = sub;
        Estado = estado;
        Tipo = tipo;
    }
     /**Se usa en el metodo CreacionObjMenu en ComandaActity para obtner la informacion de fireBase y posteriormente enviarla a salon **/
    public Menu(Boolean estado,String nombre, int precio, String sub,String tipo,String categoria,String comentario) {
        Nombre = nombre;
        Precio = precio;
        this.Comentario = comentario;
        this.category= categoria;
        this.Sub = sub;
        Estado = estado;
        Tipo = tipo;
    }


    /** Se utiliza para crear un Objeto a la LinkList Pedido en ExplVAadpater **/
    public Menu(String nombre,int cantidad,int precio, String comentario,String tipo,String category) {
        this.Cantidad = cantidad;
        this.category = category;
        Nombre = nombre;
        Tipo = tipo;
        Precio = precio;
        this.Comentario = comentario;
    }

    /***********************************    Unica  y multiple  ***************************************/
    /** Se utiliza para crear un Objeto a la LinkList Pedido en ExplVAadpater **/
    public Menu(String nombre, int precio, String comentario,ArrayList<ModeloNuevoPlato> opciones,int cantidad,String tipo) {
        Nombre = nombre;
        Tipo = tipo;
        Cantidad = cantidad;
        Precio = precio;
        Comentario = comentario;
        Opciones = opciones;

    }

    /***********************************  Modificar el plato en  ModificarPlatoActivity ************************/
    public Menu(String iddd,String nombre, int precio, String categoria,boolean estado,String tipo) {
       // this.iddd = iddd;
        Nombre = nombre;
        Precio = precio;
        category = categoria;
        this.Tipo = tipo;
        Estado = estado;

    }




    /** Constructor **/
    public Menu(String nombre, int precio, Categorias tipo, boolean eleccionMultiple) {
       // id = contador;
        Nombre = nombre;
        Precio = precio;
        categoria = tipo;
        EleccionMultiple = eleccionMultiple;
        Cantidad = 0;
        Estado = true;
        Comentario = "";
        Preferencia = "";
        //contador++;
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
        //id = contador;
       // contador++;
    }



    public Menu() {
    }

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
        return Comentario.toString();
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



    /*********** ID *******************/

    public int getIddd() {
        return id;
    }

    public void setIddd(int id) {
        this.id = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }

    public void setEstado(Boolean estado) {
        Estado = estado;
    }

    public ArrayList<ModeloNuevoPlato> getOpciones() {
        return Opciones;
    }

    public void setOpciones(ArrayList<ModeloNuevoPlato> opciones) {
        Opciones = opciones;
    }


    public String getSub() {
        return Sub;
    }

    public void setSub(String sub) {
        Sub = sub;
    }
}

