package com.example.orden.Comanda;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.ItemTouchHelper.Callback.*;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Actividad.MesasActivity;
import com.example.orden.Caja.CajaActivity;
import com.example.orden.Caja.ModeloMenuFirebase;
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


public class MainComandaActivity extends AppCompatActivity implements ExampleDialogListener,CrearSelect  {


    /****  TextView Barra****/
    public TextView TViewContador, TViewMesa;
    Context context;


    /*** Swipe y menu***/
    public static LinkedList<Menu> Pedido = new LinkedList<>();
    public RecyclerView recycler;
    public static AdapterDatos MyAdapter;
    public static ArrayList<Menu> menu = new ArrayList();

    /*** Expandible ***/
    private ExpandableListView expandableListView;
    private ExpLVAadapter adapter;
    private ArrayList<String> listCategorias;
    private Map<String, ArrayList<String>> mapChild;

    /**
     * notificacion
     **/
    private Window window;
    private String nmesa;


    /** firebase **/
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Salon");
    private String MesaSeleccion = "";


    @Override
    protected void onDestroy() {
        Pedido.clear();
        super.onDestroy();
    }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_comanda);


        /*** ActionBar y notificacion ***/
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        this.window = getWindow();
        context = this;

        /*** Swipe ***/
        CargarSwipe();


        /*** Expandible ***/
        TextView TViewContador = findViewById(R.id.TxtViewCantidad);
        expandableListView = (ExpandableListView) findViewById(R.id.expLV);
        listCategorias = new ArrayList<>();
        mapChild = new HashMap<>();


        /****  TextView Barra****/

        TViewContador = findViewById(R.id.TxtViewCantidad);
        TViewMesa = findViewById(R.id.TextViewMesa);
        CargarExpandible();


        /** firebase **/
        MesaSeleccion = getIntent().getStringExtra("mesa");
      //  Toast.makeText(this, ""+MesaSeleccion, Toast.LENGTH_SHORT).show();
        TViewMesa.setText("# "+MesaSeleccion);

        if("Llevar".equals(MesaSeleccion)){
            TViewMesa.setText("# "+MesaSeleccion);
           // MesaSeleccion = "hola";
            MesaSeleccion = String.valueOf(ComandaA1Activity.mapMesas.size());
         //   Toast.makeText(this, ""+MesaSeleccion, Toast.LENGTH_SHORT).show();
        }
        /*
        if(ComandaA1Activity.mapMesas.size()==n){
            Toast.makeText(this, "Igual", Toast.LENGTH_SHORT).show();
        }*/


       // Toast.makeText(this, "c:"+ComandaA1Activity.mapMesas.size()+" N:"+n, Toast.LENGTH_SHORT).show();

    }


    private void CargarExpandible() {

        /*** SubConjuntos ***/
        ArrayList<String> LGallos = new ArrayList();
        ArrayList<String> LDesayunos = new ArrayList();
        ArrayList<String> LCasados = new ArrayList();
        ArrayList<String> LSopas = new ArrayList();
        ArrayList<String> LAlmuerzos = new ArrayList();
        ArrayList<String> LCalientes = new ArrayList();
        ArrayList<String> LBatidos = new ArrayList();
        ArrayList<String> LOrdenes = new ArrayList();

        /*** Categorias ***/
        listCategorias.add("Gallos");
        listCategorias.add("Desayunos");
        listCategorias.add("Casados");
        listCategorias.add("Almuerzos");
        listCategorias.add("Sopas");
        listCategorias.add("Calientes");
        listCategorias.add("Batidos");
        listCategorias.add("Ordenes");



        CompletarMenu();
        /*** Almuerzos ***/


        try {

            /*** Cargar Categorias ***/
            for (Menu plato : menu) {

                switch (plato.getCategoria()) {
                    case GALLO: {
                        LGallos.add(plato.getNombre());
                        break;
                    }
                    case DESAYUNO: {
                        LDesayunos.add(plato.getNombre());
                        break;
                    }
                    case CASADO: {
                        LCasados.add(plato.getNombre());
                        break;
                    }
                    case ALMUERZO: {
                        LAlmuerzos.add(plato.getNombre());
                        break;
                    }
                    case SOPAS: {
                        LSopas.add(plato.getNombre());
                        break;
                    }
                    case CALIENTE: {
                        LCalientes.add(plato.getNombre());
                        break;
                    }
                    case BATIDOS: {
                        LBatidos.add(plato.getNombre());
                        break;
                    }
                    case ORDEN: {
                        LOrdenes.add(plato.getNombre());
                        break;
                    }

                }


            }

        } catch (Exception E) {
            Toast.makeText(getApplicationContext(), E.getMessage()+" hola", Toast.LENGTH_LONG).show();
            System.out.println(E.getMessage());
        }


        mapChild.put(listCategorias.get(0), LGallos);
        mapChild.put(listCategorias.get(1), LDesayunos);
        mapChild.put(listCategorias.get(3), LAlmuerzos);
        mapChild.put(listCategorias.get(2), LCasados);
        mapChild.put(listCategorias.get(4), LSopas);
        mapChild.put(listCategorias.get(5), LCalientes);
        mapChild.put(listCategorias.get(6), LBatidos);
        mapChild.put(listCategorias.get(7), LOrdenes);


        adapter = new ExpLVAadapter(MainComandaActivity.this, listCategorias, mapChild, getSupportFragmentManager(), TViewContador);
        expandableListView.setAdapter(adapter);
    }

    private void CargarSwipe() {

        Resources resources = getResources();
        MyAdapter = new AdapterDatos(Pedido, resources,getApplicationContext(),getSupportFragmentManager());
        recycler = findViewById(R.id.recyclerID);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(MyAdapter);

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
                        ActualizarContadores();
                        MyAdapter.notifyDataSetChanged();

                    } else if (Pedido.get(i).getCantidad() == 1) {
                        Pedido.get(i).Disminuir();
                        Pedido.remove(i);
                        ActualizarContadores();
                        MyAdapter.notifyDataSetChanged();
                    }


                } else if ((direction == ItemTouchHelper.RIGHT)/*&&(Pedido.size()>1)*/) {
                    Pedido.get(i).Aumentar();
                    ActualizarContadores();
                    MyAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext() ,Pedido.get(i).toString(),Toast.LENGTH_SHORT).show();
                }


                MyAdapter.notifyDataSetChanged();

            }


        }));

        MyAdapter.notifyDataSetChanged();
        helper.attachToRecyclerView(recycler);

    }

    private void CompletarMenu() {

        int PrecioCasado = 3700;
        int PrecioMedio = 2500, PrecioOrden = 500;
        int PrecioGallo = 1000, PrecioBatido = 1000;

        // Pedido.add(new Menu("Arroz con leche",1000,Categorias.ORDEN));

        /*** GALLOS ***/
        menu.add(new Menu("Gallo Carne", PrecioGallo + 500, Categorias.GALLO, false));
        menu.add(new Menu("Gallo lengua", PrecioGallo * 2, Categorias.GALLO, false));
        menu.add(new Menu("Gallo papa", PrecioGallo, Categorias.GALLO, false));
        menu.add(new Menu("Gallo queso", PrecioGallo, Categorias.GALLO, false));
        menu.add(new Menu("Gallo Salchichon", PrecioGallo, Categorias.GALLO, true));
        menu.add(new Menu("Gallo chorizo", PrecioGallo, Categorias.GALLO, true));

        /*** DESAYUNOS ***/
        menu.add(new Menu("Pinto de la casa", 3000, Categorias.DESAYUNO, true));
        menu.add(new Menu("Maduro Queso", PrecioMedio - 300, Categorias.DESAYUNO, false));
        menu.add(new Menu("Chorreada", PrecioMedio - 500, Categorias.DESAYUNO, false));
        menu.add(new Menu("Tortilla Queso", PrecioMedio - 500, Categorias.DESAYUNO, false));
        menu.add(new Menu("Sandwich", 2200, Categorias.DESAYUNO, true));
        menu.add(new Menu("Gallo pescado", 2000, Categorias.DESAYUNO, false));


        /*** CASADOS ***/
        menu.add(new Menu("C.Carne", PrecioCasado, Categorias.CASADO, false));
        menu.add(new Menu("C.Lengua", PrecioCasado, Categorias.CASADO, false));
        menu.add(new Menu("C.Chuleta", PrecioCasado, Categorias.CASADO, false));
        menu.add(new Menu("C.Pollo", PrecioCasado, Categorias.CASADO, false));
        menu.add(new Menu("C.Bistec", PrecioCasado, Categorias.CASADO, false));
        menu.add(new Menu("C.Pescado", PrecioCasado, Categorias.CASADO, false));


        /*** ALMUERZOS ***/
        menu.add(new Menu("Arroz Lengua", PrecioMedio, Categorias.ALMUERZO, false));
        menu.add(new Menu("Arroz Carne", PrecioMedio, Categorias.ALMUERZO, false));
        menu.add(new Menu("Camarones", PrecioCasado + 100, Categorias.ALMUERZO, false));
        menu.add(new Menu("Cantones", PrecioCasado, Categorias.ALMUERZO, false));
        menu.add(new Menu("Arroz con Pollo", PrecioCasado, Categorias.ALMUERZO, false));
        menu.add(new Menu("Pechuga", PrecioMedio + 500, Categorias.ALMUERZO, false));


        /*** SOPAS ***/
        menu.add(new Menu("Olla de carne", 3800, Categorias.SOPAS, true));
        menu.add(new Menu("Mondongo", PrecioCasado + 100, Categorias.SOPAS, false));
        menu.add(new Menu("Sopa Negra", PrecioCasado + 100, Categorias.SOPAS, false));


        /*** CALIENTES ***/
        menu.add(new Menu("Cafe", 700, Categorias.CALIENTE, true));
        menu.add(new Menu("Aguadulce", 700, Categorias.CALIENTE, true));
        menu.add(new Menu("Chocolate", PrecioBatido - 300, Categorias.CALIENTE, false));
        menu.add(new Menu("Te", 700, Categorias.CALIENTE, true));

        /*** BATIDOS **/
        menu.add(new Menu("Fresa", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Mora", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Piña", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Papaya", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Cas", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Maracuya", PrecioBatido, Categorias.BATIDOS, true));
        menu.add(new Menu("Guanabana", PrecioBatido, Categorias.BATIDOS, true));


        /*** ORDENES ***/
        menu.add(new Menu("Arroz", PrecioOrden, Categorias.ORDEN, false));
        menu.add(new Menu("Tortilla", PrecioOrden, Categorias.ORDEN, false));
        menu.add(new Menu("Papas", PrecioOrden * 2, Categorias.ORDEN, false));
        menu.add(new Menu("Pan", PrecioOrden, Categorias.ORDEN, false));
        menu.add(new Menu("Ensalada", PrecioOrden, Categorias.ORDEN, false));

    }



    /***** Floating boton *****/
    public void AddDiferenteBebidas(View view) {

        ExampleDialog dialog = new ExampleDialog("AddDiferente", "Bebida");
        dialog.show(getSupportFragmentManager(), "example dialog");

    }


    public void AddDiferenteComida(View view) {
        ExampleDialog dialog = new ExampleDialog("AddDiferente", "Comida");
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    public void AddNuevaComanda(View view) {
    }

    public void SetComanda(View view) {

        if(Pedido.size()>0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("¿Ya terminastes?")
                    .setCancelable(false)
                    .setNegativeButton("No :(",null)
                    .setPositiveButton("Sip", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myRef.child(MesaSeleccion).push().setValue(Pedido);
                            Toast.makeText(context, "Orden enviada", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }else{
            Toast.makeText(context, "La orden no puede ir vacia", Toast.LENGTH_SHORT).show();
        }


    }



    @Override
    public void CreatePreferenceAndDescription(String preference, String comentario, Menu e,boolean prevalencia)
    {
        Boolean FistGetIn = false;

        int CDPreferencia = 0;  // Contador diferente preferencia
        int CIPreferencia = 0; //Contador diferentente comentario
        int CMismoPlato = 0;    // contador mismo plato
        int CMismaPreferencia = 0;  // contador misma preferencia
        int CDPlato = 0; //Contador diferente plato

        if(prevalencia){
            e.setComentario(comentario);
            e.setPreferencia(preference);
            MyAdapter.notifyDataSetChanged();
        }else{
            if ("null".equals(preference)) {
                Toast.makeText(this, "Debes elegir alguna opcion", Toast.LENGTH_SHORT).show();
            } else {

                for (Menu p : Pedido) {
                    FistGetIn = true;

                    /* Mismo Plato */
                    if ((p.getNombre().equals(e.getNombre()))) {

                        if (p.getPreferencia().equals(preference)) {

                            if ((p.getComentario().equals(comentario))) {
                                p.Aumentar();
                            } else if (comentario != p.getComentario()) {
                                CIPreferencia++;
                            }
                            CMismaPreferencia++;
                        }
                        /* Preferencia diferente q no exista en la lista*/
                        else if (p.getPreferencia() != preference) {
                            CDPreferencia++;
                        }
                        CMismoPlato++;

                    } else if (p.getNombre() != e.getNombre()) {
                        CDPlato++;
                    }

                }

                if ((CIPreferencia == CMismaPreferencia) && (CIPreferencia > 0) || (CDPreferencia == CMismoPlato) && (CDPreferencia > 0)) {
                    /**Nuevo plato misma preferencia diferente comentario o diferente prefrencia mismo plato*/
                    CrearInstancia(e, preference, comentario);

                } else if ((CDPlato > 0) && (CDPlato == Pedido.size())) {
                    /** caundo ingresa nuevo plato **/
                    CrearInstancia(e, preference, comentario);
                }

                if (!FistGetIn) {
                    /** primero en la lista**/
                    CrearInstancia(e, preference, comentario);
                }
                ActualizarContadores();
                MyAdapter.notifyDataSetChanged();
            }

        }



    }

    @Override
    public void CreateDescription(String comentario, Menu e,boolean prevalencia) {
        boolean firstGetIn = false;
        int CDComentario = 0;    //Contador Diferente Comentario de igual nombre
        int CMismoPlato = 0;    //Contador Mismo Plato
        int CDPlato = 0;    //Contador Diferente Plato


        if(prevalencia){
            e.setComentario(comentario);
            MyAdapter.notifyDataSetChanged();
        }else {

            if (comentario.isEmpty()) {
                Toast.makeText(this, "falta un comentario XD", Toast.LENGTH_LONG).show();
            } else {
                for (Menu p : Pedido) {
                    firstGetIn = true;
                    if (p.getNombre().equals(e.getNombre())) {
                        if (p.getComentario().equals(comentario)) {
                            p.Aumentar();
                        } else if (p.getComentario() != comentario) {
                            CDComentario++;
                        }
                        CMismoPlato++;
                    } else if (p.getNombre() != e.getNombre()) {
                        CDPlato++;
                    }
                }

                if (((CDComentario == CMismoPlato) && (CDComentario > 0)) || ((CDPlato == Pedido.size()) && (CDPlato > 0))) {
                    /** Cuando el plato ingresa con comentario nuevo o ingresa un plato nuevo**/
                    CrearInstancia(e, "", comentario);

                }

                if (!firstGetIn) {
                    /** Primera vez en la lista **/
                    CrearInstancia(e, "", comentario);
                }
                ActualizarContadores();
                MyAdapter.notifyDataSetChanged();
            }


        }



    }

    @Override
    public void CreateNewName(String nombre, Categorias categoria) {
        /** imposible centralizar con crear instancia **/
        Boolean FistGetIn = false;
        int Cnombre = 0; //Contador de nombre

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Debes completar el espacio", Toast.LENGTH_LONG).show();
        } else {
            for (Menu p : Pedido) {
                FistGetIn = true;
                if (p.getNombre().equals(nombre)) {
                    p.Aumentar();
                } else if (p.getNombre() != nombre) {
                    Cnombre++;
                }
            }
            if ((!FistGetIn) || ((Cnombre == Pedido.size()) && (Pedido.size() > 0))) {
                Menu platoB = new Menu(nombre, categoria);
                platoB.Aumentar();

                if (platoB.getCategoria() == Categorias.BATIDOS) {
                    Pedido.addLast(platoB);
                } else {
                    Pedido.addFirst(platoB);
                }
            }
            ActualizarContadores();
            MyAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void CreateSelectName(String seleccion) {

        Toast.makeText(this, "NOMBRE SELECCIONADO", Toast.LENGTH_LONG).show();
        boolean FistGetIn = false;
        int c = 0;
        int CNuevo = 0;

        for (Menu e : menu) {
            if (e.getNombre().equals(seleccion)) {
                if (!e.getEleccionMultiple()) {
                    for (Menu p : Pedido) {
                        FistGetIn = true;
                        if (p.getNombre().equals(seleccion)) {
                            if (p.getComentario().isEmpty()) {
                                if (p.getCantidad() != 0) {
                                    p.Aumentar();
                                }
                            } else if (!p.getComentario().isEmpty()) {
                                CNuevo++;
                            }
                        } else if ((seleccion != p.getNombre())) {
                            c++;
                        }

                        if ((Pedido.size() == c) || ((Pedido.size() == c + CNuevo) && (Pedido.size() > 0))) {
                            /** Cuando un plato es nuevo o cuando un plato ingresa despues de un plato con comentario **/
                            CrearInstancia(e, "", "");
                            MyAdapter.notifyDataSetChanged();
                            ActualizarContadores();

                        }

                    }

                    if (!FistGetIn) {
                        /** primer plato lista vacia**/
                        MainComandaActivity.CrearInstancia(e, "", "");
                    }

                } else {

                    ExampleDialog dialog = new ExampleDialog(e, "ApplyPreferenceAndDescription");
                    dialog.show(getSupportFragmentManager(), "example dialog");
                }
            }

        }
        MyAdapter.notifyDataSetChanged();
        ActualizarContadores();

    }

    public static void CrearInstancia(Menu e, String preference, String comentario) {

        Menu platoA = new Menu(e.getNombre(), e.getPrecio(), e.getCategoria(), e.getEleccionMultiple());
        platoA.setPreferencia(preference);
        platoA.setComentario(comentario);
        platoA.Aumentar();

        if ((e.getCategoria() == Categorias.BATIDOS) || e.getCategoria() == Categorias.CALIENTE) {
            Pedido.addLast(platoA);
        } else {
            Pedido.addFirst(platoA);
        }

    }

    private void ActualizarContadores(){

        int comidas = 0,bebidas = 0;

        for(Menu p:Pedido){
            if((p.getCategoria()==Categorias.BATIDOS)||(p.getCategoria()==Categorias.CALIENTE)) {
                bebidas+=p.getCantidad();
            }else if((p.getCategoria()!=Categorias.BATIDOS)&&(p.getCategoria()!=Categorias.CALIENTE)){
                comidas+=p.getCantidad();
            }
        }

        //TViewContador.setText(bebidas + " / " + comidas);
        TViewContador.setText("B:"+bebidas+" / "+"C:"+comidas);

    }

}

