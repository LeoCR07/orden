package com.example.orden.Comanda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.orden.R;

import java.util.ArrayList;
import java.util.Map;

import static com.example.orden.Comanda.MainComandaActivity.MyAdapter;
import static com.example.orden.Comanda.MainComandaActivity.Pedido;
import static com.example.orden.Comanda.MainComandaActivity.menu;


public class ExpLVAadapter extends BaseExpandableListAdapter {

    private ArrayList<String> ListCategorias;
    private Map<String, ArrayList<String>> mapChild;
    protected Context context;
    protected FragmentManager Support;
    protected TextView TViewContador;


    public ExpLVAadapter(Context context, ArrayList<String> listCategorias, Map<String, ArrayList<String>> mapChild, FragmentManager Support, TextView TViewContador) {
        ListCategorias = listCategorias;
        this.mapChild = mapChild;
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
        return mapChild.get(ListCategorias.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return ListCategorias.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mapChild.get(ListCategorias.get(i)).get(i1);
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

        if(i==0){
            view = LayoutInflater.from(context).inflate(R.layout.elv_grup_primero, null);
        }else{
            view = LayoutInflater.from(context).inflate(R.layout.elv_group, null);
        }


        TextView tvGroup = (TextView) view.findViewById(R.id.tvGroup);
        tvGroup.setText(tituloCategoria);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

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
                            MainComandaActivity.CrearInstancia(e,"","");
                            MyAdapter.notifyDataSetChanged();
                            ActualizarContadores();
                            Toast.makeText(context, "Primer plato op2", Toast.LENGTH_SHORT).show();

                        }

                    }

                    if(!FistGetIn){
                        /** primer plato lista vacia**/
                        MainComandaActivity.CrearInstancia(e,"","");
                      //  Instancia(e,"","");
                        ActualizarContadores();
                        MyAdapter.notifyDataSetChanged();
                        Toast.makeText(context, "Primer plato op1 ", Toast.LENGTH_SHORT).show();
                    }

                }else{

                    /**Preferencia y comentario **/
                    ExampleDialog dialog = new ExampleDialog(e,"ApplyPreferenceAndDescription");
                    dialog.show(Support,"example dialog");
                }
            }

        }
        MyAdapter.notifyDataSetChanged();
        ActualizarContadores();


    }



    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {


        final String Son = (String) getChild(i, i1);
        view = LayoutInflater.from(context).inflate(R.layout.elv_child, null);
        Button Boton = (Button) view.findViewById(R.id.BtnPlato);
        Boton.setText(Son);

        Boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    CrearOrden(Son);
                    // listenerSelect.CreateSelectName(Son);

                    // listener.CreateOrden(Son);
                }catch (Exception X){
                    //Toast.makeText(context,X.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    //throw new ClassCastException(context.toString() +" must");
                }

            }
        });

        Boton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                try {
                    for(Menu e :menu){
                        if(Son.equals(e.getNombre())){

                            if(!e.getEleccionMultiple()){
                              //  Toast.makeText(context, "Hola 1", Toast.LENGTH_SHORT).show();
                                ExampleDialog dialog = new ExampleDialog(e,"ApplyDescription");
                                dialog.show(Support,"example dialog");
                            }else{
                                Toast.makeText(context,"No esta permitido", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                }catch (Exception x){
                    Toast.makeText(context,x.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        return view;
    }

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


    public void Instancia(Menu e, String preference, String comentario) {

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

}