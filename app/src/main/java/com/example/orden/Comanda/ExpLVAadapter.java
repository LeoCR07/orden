package com.example.orden.Comanda;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.orden.Configuracion.AgregarPlato2Activity;
import com.example.orden.Configuracion.ModeloNuevoPlato;
import com.example.orden.Configuracion.ModificarPlatoActivity;
import com.example.orden.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.example.orden.Comanda.ComandaActivity.Pedido;
import static com.example.orden.Comanda.ComandaActivity.myAdapter;


public class ExpLVAadapter extends BaseExpandableListAdapter {

    private Map<String, ArrayList<Menu>> hashChild = new HashMap<>();
    private ArrayList<String> ListCategorias;
    protected Context context;
    protected FragmentManager Support;
    protected TextView TViewContador;


    public ExpLVAadapter(Context context, ArrayList<String> listCategorias, FragmentManager Support, TextView TViewContador,Map<String, ArrayList<Menu>> Child) {
        ListCategorias = listCategorias;
        this.hashChild = Child;
        this.context = context;
        this.Support = Support;
       this.TViewContador = TViewContador;
    }

    @Override
    public int getGroupCount() {
        return ListCategorias.size();
    }

    @Override
    public int getChildrenCount(int i) {
      // return mapChild.get(ListCategorias.get(i)).size();
        return hashChild.get(ListCategorias.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListCategorias.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return  hashChild.get(ListCategorias.get(i)).get(i1).getNombre();
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        String tituloCategoria = (String) getGroup(i);
        view = LayoutInflater.from(context).inflate(R.layout.elv_group, null);
        TextView tvGroup = (TextView) view.findViewById(R.id.tvGroup);
        tvGroup.setText(tituloCategoria);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
/*
    private void CrearOrden(String seleccion){
        boolean FistGetIn = false;
        int c = 0;
        int CNuevo = 0;


        for(Menu e:menu){
            if(e.getNombre().equals(seleccion)) {
                if (!e.getEleccionMultiple()) {
                    for(Menu p:Pedido){
                        FistGetIn = true;
                        if(p.getNombre().equals(seleccion)){
                            if(p.getComentario().isEmpty()){
                                if (p.getCantidad()!=0) {
                                    p.Aumentar();
                                    MyAdapter.notifyDataSetChanged();
                                }
                            }else if(!p.getComentario().isEmpty()){
                                CNuevo++;
                            }
                        }else if((seleccion!=p.getNombre()) ){
                            c++;
                        }

                        if((Pedido.size()==c)|| ((Pedido.size()==c+CNuevo)&&(Pedido.size()>0))  ){
                            /** Cuando un plato es nuevo o cuando un plato ingresa despues de un plato con comentario **/
                       /*     MainComandaActivity.CrearInstancia(e,"","");
                            MyAdapter.notifyDataSetChanged();
                            ActualizarContadores();
                            Toast.makeText(context, "Primer plato op2", Toast.LENGTH_SHORT).show();

                        }

                    }

                    if(!FistGetIn){
                         primer plato lista vacia
                        MainComandaActivity.CrearInstancia(e,"","");
                      //  Instancia(e,"","");
                        ActualizarContadores();
                        MyAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "Primer plato op1 ", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    //Preferencia y comentario
                    ExampleDialog dialog = new ExampleDialog(e,"ApplyPreferenceAndDescription");
                    dialog.show(Support,"example dialog");
                }
            }

        }
        MyAdapter.notifyDataSetChanged();
        ActualizarContadores();


    }
*/



    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        final String Son = (String)getChild(i,i1);

        //Envia el layout del plato bloqueado o el normal
        if(hashChild.get(ListCategorias.get(i)).get(i1).getEstado()==false){
            view = LayoutInflater.from(context).inflate(R.layout.elv_child_false, null);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.elv_child, null);
        }

        Button Boton = (Button) view.findViewById(R.id.BtnPlato);
        Boton.setText(Son);

        //añadir normal simple
        Boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hashChild.get(ListCategorias.get(i)).get(i1).getEstado()==false){
                    Toast.makeText(context,"Esta agotado", Toast.LENGTH_SHORT).show();
                }else{//diferenciar que tipo de plato es
                    if(hashChild.get(ListCategorias.get(i)).get(i1).getTipo().equals("Simple")){
                        //Añadir obj a Pedidos cuando sea simple y sin comentarios
                        CrearOrdenSimple(i,i1);
                    }else{
                        //Añadir obj a Pedidos cuando sea unico o multiple
                        Menu objMenu = hashChild.get(ListCategorias.get(i)).get(i1);

                        ExampleDialog dialog = new ExampleDialog(objMenu,"GenerarOpcionesUnica");
                        dialog.show(Support,"example dialog");
                    }
                }
            }
        });

        //Añadir obj a Pedidos solo cuando sea simple y  con comentario
        Boton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    if(hashChild.get(ListCategorias.get(i)).get(i1).getTipo().equals("Simple") ){

                        Menu objMenu = hashChild.get(ListCategorias.get(i)).get(i1);
                     //   Toast.makeText(context,""+objMenu.getNombre(), Toast.LENGTH_LONG).show();
                        ExampleDialog dialog = new ExampleDialog(objMenu,"GenerarComentario");
                        dialog.show(Support,"example dialog");

                    }else{
                        Toast.makeText(context, "No esta permitido", Toast.LENGTH_SHORT).show();
                    }


                  /*  for(Menu e :menu){
                        if(Son.equals(e.getNombre())){

                            if(!e.getEleccionMultiple()){
                              //  Toast.makeText(context, "Hola 1", Toast.LENGTH_SHORT).show();
                                ExampleDialog dialog = new ExampleDialog(e,"ApplyDescription");
                                dialog.show(Support,"example dialog");
                            }else{
                                Toast.makeText(context,"No esta permitido", Toast.LENGTH_LONG).show();
                            }

                        }
                    }*/
                }catch (Exception x){
                    Toast.makeText(context,x.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        return view;
    }


    private void CrearOrdenSimple(int i,int i1) {

        try{
            //Validar nombre y cantidad de tipo simple, este metodo no valida los comentarios,
            //El metodo que valida comentarios simple se genera con un evento LongClick en el mismo boton

            boolean fistGetIn = false;    //Para determinar si es la primera entrada
            boolean YaExiste = false;

            for(Menu e:Pedido){
                fistGetIn = true;   //Existen datos
                if(hashChild.get(ListCategorias.get(i)).get(i1).getNombre().equals(e.getNombre())){          //Mismo nombre
                    if(e.getComentario().isEmpty()){  //EN caso que no tenga comentario
                        e.Aumentar();
                        YaExiste = true;
                    }
                }
            }


            //Primer plato o No esta en la lista
            if(!fistGetIn||!YaExiste){
                CreacionObjPedido(i,i1);
            }

            myAdapter.notifyDataSetChanged(); 



        }catch (Exception x){
            //Contiene la exepcion E/RecyclerView: No adapter attached; skipping layout no quitar try catch
            //System.out.println(x.getMessage());
        }

    }

    private void CreacionObjPedido(int i,int i1) {
        //Metodo para crear un objeto en la linklist Pedido

        int cantidad = 1;
        String nombre = hashChild.get(ListCategorias.get(i)).get(i1).getNombre();
        int precio = hashChild.get(ListCategorias.get(i)).get(i1).getPrecio();
        String categoria = hashChild.get(ListCategorias.get(i)).get(i1).getCategory();
        String tipo  = hashChild.get(ListCategorias.get(i)).get(i1).getTipo();
        String comentario = "";    //Se guarda asi porque se ahorra trabajo en cambio null genera un poco mas trabajo
        ArrayList<ModeloNuevoPlato>Opciones = hashChild.get(ListCategorias.get(i)).get(i1).getOpciones();


        if(hashChild.get(ListCategorias.get(i)).get(i1).getTipo().equals("Simple")){   //Cuando es simple
            if(IndicarEntrada(categoria)){
                Pedido.addLast(new Menu(nombre,cantidad,precio,comentario,tipo,categoria));
            }else{
                Pedido.addFirst(new Menu(nombre,cantidad,precio,comentario,tipo,categoria));
            }
        }else{    //Cuando sea multiple o unica

            if(IndicarEntrada(categoria)){
                Pedido.addLast(new Menu(nombre,precio,comentario,Opciones,cantidad,tipo));
            }else{
                Pedido.addFirst(new Menu(nombre,precio,comentario,Opciones,cantidad,tipo));
            }
        }



    }


    boolean IndicarEntrada(String cat){
        //Es para que las bebidas se vea de ultimas siempre y el resto al inicio de la lista
        //este metodo es por si lo llego a cambiar no cambiarlos en todos los if y solo tenerlo que cambiar en este no borrar
        //no se ve tan util pero puede ahorar tiempo en un futuro
        if(cat.equals("Bebida Fria")){
            return true;
        }else{
            return false;
        }

    }
/*
    private void ActualizarContadores(){

        int comidas = 0,bebidas = 0;

        for(Menu p:Pedido){
            if((p.getCategoria()== Categorias.BATIDOS)||(p.getCategoria()== Categorias.CALIENTE)) {
                bebidas+=p.getCantidad();
            }else if((p.getCategoria()!= Categorias.BATIDOS)&&(p.getCategoria()!=Categorias.CALIENTE)){
                comidas+=p.getCantidad();
            }
        }

        //TViewContador.setText(bebidas + " / " + comidas);
        TViewContador.setText("B:"+bebidas+" / "+"C:"+comidas);

    }
*/



}