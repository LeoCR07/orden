package com.example.orden.Configuracion;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import com.example.orden.Comanda.Categorias;
import com.example.orden.Comanda.ComandaActivity;
import com.example.orden.Comanda.ExampleDialog;

import com.example.orden.Comanda.Menu;
import com.example.orden.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ExpLVAadpterC extends BaseExpandableListAdapter {

    /** Propiedades **/
    private Map<String, ArrayList<Menu>> hashChild = new HashMap<>();
    private ArrayList<String> ListCategorias;
    protected Context context;
    private  Resources resources;


    /** FireBase **/
    private String Dir = "SodaPoas/Menu";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(Dir);

    public ExpLVAadpterC(ModificarPlatoActivity context, ArrayList<String> listCategorias, Map<String, ArrayList<Menu>> Child,Resources resources) {
        ListCategorias = listCategorias;
        this.hashChild = Child;
        this.context = context;
        this.resources = resources;

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
        //return mapChild.get(ListCategorias.get(i)).get(i1);
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





    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {


        final String Son = (String)getChild(i,i1);
        view = LayoutInflater.from(context).inflate(R.layout.elv_child_menu, null);
        TextView Boton =(TextView)  view.findViewById(R.id.ViewPlato);
        TextView CategoriaV = (TextView)view.findViewById(R.id.ViewCategoria);

        ImageView img_edit = view.findViewById(R.id.ImgEditarPM);
        ImageView img_bloqueo = (ImageView)view.findViewById(R.id.ImgBloqueoPM);
        ImageView img_borrar = (ImageView)view.findViewById(R.id.ImgBorrarPM);
        Boton.setText(Son);

        if(hashChild.get(ListCategorias.get(i)).get(i1).getEstado()){
            img_bloqueo.setImageResource(R.drawable.ic_unlock_);
        }else{
            img_bloqueo.setImageResource(R.drawable.ic_lock_open);
        }







        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Editar", Toast.LENGTH_SHORT).show();
            }
        });


        img_bloqueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = hashChild.get(ListCategorias.get(i)).get(i1).getID();
                String hijo = hashChild.get(ListCategorias.get(i)).get(i1).getCategory();
              //  Toast.makeText(context, ""+img_bloqueo.getDrawable()+" | "+resources.getDrawable(R.drawable.ic_lock_open), Toast.LENGTH_LONG).show();


                if(hashChild.get(ListCategorias.get(i)).get(i1).getEstado()){
                    img_bloqueo.setImageResource(R.drawable.ic_lock_open);
                    hashChild.get(ListCategorias.get(i)).get(i1).setEstado(false);
                    myRef.child(hijo).child(id).child("estado").setValue(false);
                    Toast.makeText(context, "Bloqueado", Toast.LENGTH_SHORT).show();

                }else{
                    hashChild.get(ListCategorias.get(i)).get(i1).setEstado(true);
                    img_bloqueo.setImageResource(R.drawable.ic_unlock_);
                    myRef.child(hijo).child(id).child("estado").setValue(true);
                    Toast.makeText(context, "Habilitado", Toast.LENGTH_SHORT).show();
                }




            }
        });

        img_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Eliminar", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void DetectarBloqueos(int i,int i1) {



    }


}
