package com.example.orden.Comanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Configuracion.ModeloNuevoPlato;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ComandaActivity extends AppCompatActivity  implements ExampleDialogListener{


    /*** Swipe(lista de Pedidos) ***/
    public static LinkedList<Menu> Pedido = new LinkedList<>();
    public static AdapterDatos myAdapter;
    private RecyclerView recycler;


    /****  Contador y # mesa ****/
    public TextView TViewContador, TViewMesa;
    private String llevar;
    private String mesa ="";
    /*** Expandible(Menu) ***/
    private ExpandableListView expandableListView;
    private ExpLVAadapter adapter;
    private ArrayList<String> listSub;
    private Map<String, ArrayList<String>> mapChild;
    private Map<String, ArrayList<Menu>> hashChild = new HashMap<>();

    /** firebase **/
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Menu");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();


        /** Inicializar el contador y # mesa**/
        TViewContador = findViewById(R.id.TxtViewCantidad);
        TViewMesa = findViewById(R.id.TextViewMesa);
        mesa = getIntent().getStringExtra("n");
        llevar = getIntent().getStringExtra("llevar");
        if(llevar==null){
            TViewMesa.setText("# "+mesa);
        }else{
            TViewMesa.setText("# "+mesa+" llevar");
        }

        /** Swipe **/
        Swipe();

        /** Inicializar el exapandible **/
        expandableListView = (ExpandableListView) findViewById(R.id.expLV);
        listSub = new ArrayList<>();
        mapChild = new HashMap<>();
        Expandible("casados","arroces","sopas","Variados","Almuerzo");  //Para que no inicie vacio

    }


    /** Swipe **/
    private void Swipe() {

        Resources resources = getResources();
        myAdapter = new AdapterDatos(Pedido, resources,getApplicationContext(),getSupportFragmentManager());
        recycler = findViewById(R.id.recyclerID);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(myAdapter);

        ItemTouchHelper helper = new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            int ContadorEnabled = 0;


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int i = viewHolder.getAdapterPosition();

                if ((direction == ItemTouchHelper.LEFT)/* &&(Pedido.size()>1)*/) {

                    if (Pedido.get(i).getCantidad() > 1) {
                        Pedido.get(i).Disminuir();
                       // ActualizarContadores();
                        myAdapter.notifyDataSetChanged();

                    } else if (Pedido.get(i).getCantidad() == 1) {
                        Pedido.get(i).Disminuir();
                        Pedido.remove(i);
                       // ActualizarContadores();
                        myAdapter.notifyDataSetChanged();
                    }


                } else if ((direction == ItemTouchHelper.RIGHT)/*&&(Pedido.size()>1)*/) {
                    Pedido.get(i).Aumentar();
                    //ActualizarContadores();
                    myAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext() ,Pedido.get(i).toString(),Toast.LENGTH_SHORT).show();
                }


                myAdapter.notifyDataSetChanged();

            }


            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(ComandaActivity.this, R.color.aumnetar))
                        .addSwipeRightActionIcon(R.drawable.ic_aumentar)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(ComandaActivity.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_disminuir)
                        .setActionIconTint(ContextCompat.getColor(ComandaActivity.this, R.color.white))
                        .create()
                        .decorate();


                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }));





        myAdapter.notifyDataSetChanged();
        helper.attachToRecyclerView(recycler);

    }

    /*** Expandible ***/
    private void Expandible(final String S1,final String S2,final String S3,String S4,String hijo) {

        /** limpiar SubCategorias **/
        listSub.clear();

        /** Categorias  **/
        listSub.add(S1);
        listSub.add(S2);
        listSub.add(S3);
        listSub.add(S4);

        /** Lista del Menu **/
        ArrayList<Menu> Li1 = new ArrayList();
        ArrayList<Menu> Li2 = new ArrayList();
        ArrayList<Menu> Li3 = new ArrayList();
        ArrayList<Menu> Li4 = new ArrayList();


        /** limpiar Menu **/
        Li1.clear();
        Li2.clear();
        Li3.clear();



        myRef.child(hijo).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot e:snapshot.getChildren()){

                    Menu obj = CreacionObjMenu(e);
                   // Toast.makeText(ComandaActivity.this, ""+e.child("sub").getValue(), Toast.LENGTH_SHORT).show();

                    if(e.child("sub").getValue().equals(S1)){
                        Li1.add(obj);
                    }else if(e.child("sub").getValue().equals(S2)){
                        Li2.add(obj);
                    }else if(e.child("sub").getValue().equals(S3)){
                        Li3.add(obj);
                    }else if(e.child("sub").getValue().equals(S4)){
                        Li4.add(obj);
                    }


                }

                hashChild.put(listSub.get(0),Li1);
                hashChild.put(listSub.get(1),Li2);
                hashChild.put(listSub.get(2),Li3);
                hashChild.put(listSub.get(3),Li4);

                adapter = new ExpLVAadapter(ComandaActivity.this, listSub,getSupportFragmentManager(), TViewContador,hashChild);
                expandableListView.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private Menu CreacionObjMenu(DataSnapshot e) {
        //Este metodo obtiene los datos de firebase y las almacena en un HashMap
        //su funcion es mostrar el menu en una lista expandible,
        // para posteriormente crear otro objeto en una LinkList llamada Pedido
        // esta de esta forma porque de la otra da error aveces  ~~~ Menu Obj1= e.getValue(Menu.class);

        String nombre = e.child("nombre").getValue(String.class);
        String tipo = e.child("tipo").getValue(String.class);      //La utilizo en AdapterDatos
        int precio = e.child("precio").getValue(Integer.class);
        String sub = e.child("sub").getValue(String.class);       //la utilizo en ExpLVAadpter
        String categoria  = e.child("category").getValue(String.class); //la utilizo en ExpLVAadpter y AdapterDatos
        boolean estado = e.child("estado").getValue(Boolean.class);    //la utilizo en ExpLVAadpter
        String Comentario = "";        //La utilizo en AdapterDatos

        if(tipo.equals("Simple")){
            return new Menu(estado,nombre,precio,sub,tipo,categoria,Comentario);
        }else{
            ArrayList<ModeloNuevoPlato>opciones = new ArrayList<>();

            for(DataSnapshot np:e.child("opciones").getChildren()){
                //
                String n = np.child("nombre").getValue(String.class);
                int p = np.child("precio").getValue(Integer.class);
                boolean s = np.child("selec").getValue(Boolean.class);
                opciones.add(new ModeloNuevoPlato(n,p,s));
            }

            return new Menu(estado,nombre,precio,sub,tipo,categoria,Comentario,opciones);
        }


    }

    /*** Floating Menu ***/
    public void Bebidas(View view) {
        String hijo = "Bebida Fria";
        Expandible("batido","gaseoso","caliente","licor",hijo);
    }

    public void Desayuno(View view) {
        String hijo = "Desayuno";
        Expandible("desayuno","gallo","pinto","otros",hijo);
    }

    public void Almuerzo(View view) {
        String hijo ="Almuerzo";
        Expandible("casados","arroces","sopas","Variados",hijo);
    }

    public void Orden(View view) {
        String hijo ="Orden";
        Expandible("comunes","Orden Carne","Especiales","Variados",hijo);
    }

    /*** Atras ***/
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Estas seguro que deseas salir?")
                .setCancelable(false)
                .setNegativeButton("No",null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onDestroy() {
        llevar = null;
        Pedido.clear();
        super.onDestroy();
    }


    //  Editar comentario unico
    @Override
    public void EditarObjMenuComentarioUnico(String comentario, Menu e, ArrayList<ModeloNuevoPlato> listOpc,int position) {

        Boolean YaExiste = false;


        for(Menu e1:Pedido){
            if(e1.getNombre().equals(e.getNombre())){   //Mismo Nombre plato
                if(e1.getOpciones().get(position).getSelec()){  //Misma Opcion
                    if(comentario.equals(e1.getComentario())){  //Mismo Comentario
                        YaExiste = true;
                    }
                }
            }
        }


        if(!YaExiste){
            //Toast.makeText(this, "Es diferencte", Toast.LENGTH_SHORT).show();
            e.setOpciones(listOpc);
            e.setComentario(comentario);
            myAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "ya existe ese comentario en "+e.getNombre(), Toast.LENGTH_SHORT).show();
        }

    }

    /*** Dialogo Opciones Multiple y unica  ***/
    @Override
    public void CrearObjMenuOpcionesUnica(String Comentario, Menu e,ArrayList<ModeloNuevoPlato> listOpc,int position){
        //Metodo Valida si hay datos iguales o diferentes de tipo unico en la linklist Pedido
        //no aconsejo eliminar el parametro listOpc, ni el parametro comentario
        //lo mejor es no modificar el obj e solo usar sus datos para guardarlos y compararlo
        //si necesita modicar datos guardarlos en varibles o arreglos y luego enviar desde la instacia del contructor
        //ej: Pedido.add(new Menu(nombre,e.getPrecio) cambie el nombre lo almaceno en variable pero el precio es el mismo
        //(si modifica el obj e esta modifciando la lista Expandible)

        Boolean firstGetIn = false;
        Boolean YaExiste = false;

        for(Menu e1:Pedido){
            firstGetIn = true;

            if(e1.getNombre().equals(e.getNombre())){   //Mismo Nombre plato
                if(e1.getOpciones().get(position).getSelec()){  //Misma Opcion
                    if(Comentario.equals(e1.getComentario())){  //Mismo Comentario
                        e1.Aumentar();          //Solo se aumenta
                        myAdapter.notifyDataSetChanged();
                        YaExiste = true;
                    }
                }
            }
        }


        if(!firstGetIn||!YaExiste){

            //creo un nuevo objetoy los parametros que cambie los guarde en otras varibles
            Menu obj = new Menu(e.getEstado(),e.getNombre(),e.getPrecio(),e.getSub(),e.getTipo(),e.getCategory(),Comentario,listOpc);
            obj.Aumentar();

            if(IndicarEntrada(e.getCategory())){
                Pedido.addLast(obj);
                myAdapter.notifyDataSetChanged();
            }else{
                Pedido.addFirst(obj);
                myAdapter.notifyDataSetChanged();
            }

        }

    }

    /*** Dialogo Simple comentario ***/

    //Crear
    @Override
    public void CrearObjMenuComentarioSimple(String comentario, Menu e) {

        //Valida el comentario de plato simple
        boolean firstGetIn = false;
        boolean YaExiste = false;

        if (comentario.isEmpty()) {
            Toast.makeText(this, "falta el comentario XD", Toast.LENGTH_LONG).show();
        }else{
            for (Menu p : Pedido) {
                firstGetIn = true;
                if (p.getNombre().equals(e.getNombre())) {
                    if (p.getComentario().equals(comentario)) {
                        p.Aumentar();
                        YaExiste = true;
                    }
                }
            }


            if (!firstGetIn||!YaExiste) {
                /** Primera vez en la lista **/
                CrearObjMenuSimpleComentario(e, comentario);
            }
            //ActualizarContadores();
            myAdapter.notifyDataSetChanged();


        }

    }

    //Editar
    @Override
    public void EditarObjMenuComentarioSimple(String comentario, Menu e) {

        boolean YaExiste = false;

        for(Menu p:Pedido){
            if(e.getNombre().equals(p.getNombre())){
                if(comentario.equals(p.getComentario())){
                    YaExiste = true;
                }
            }
        }

        if(!YaExiste){
            e.setComentario(comentario);
            myAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(this, "ya existe ese comentario en "+e.getNombre(), Toast.LENGTH_SHORT).show();
        }


    }

    private void CrearObjMenuSimpleComentario(Menu e, String comentario) {
        //Este metodo crea objetos a la lisklist Pedido con comentarios no vacios de tipo simple
        Menu obj = new Menu(e.getNombre(),e.getCantidad(),e.getPrecio(),comentario,e.getTipo(),e.getCategory());
        obj.Aumentar();

        if(IndicarEntrada(e.getCategory())){
            Pedido.addLast(obj);
        }else{
            Pedido.addFirst(obj);
        }

    }

    boolean IndicarEntrada(String cat){
        //Es para que las bebidas se vea de ultimas siempre y el resto al inicio de la lista
        if(cat.equals("Bebida Fria")){
            return true;
        }else{
            return false;
        }

    }

    public void ClickEnviarComanda(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("¿Esta listo?")
                .setCancelable(false)
                .setNegativeButton("No",null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       myRef = database.getReference("SodaPoas/Salon/"+mesa);
                       myRef.push().setValue(Pedido);
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}