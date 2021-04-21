package com.example.orden.Configuracion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import com.example.orden.Comanda.ComandaActivity;
import com.example.orden.Comanda.Menu;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Comanda.ExpLVAadapter;
import com.example.orden.General.AdapterPagerView;
import com.example.orden.General.ModeloPagerView;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarPlatoActivity extends AppCompatActivity {


    private String Dir = "SodaPoas/Menu";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference(Dir);
    private ArrayList<String>listdireccion = new ArrayList<>();

    /** Expandible **/
    private ExpandableListView expandableListView;
    private ExpLVAadpterC adapter;
    private ArrayList<String> listCategorias = new ArrayList<String>();
    private Map<String, ArrayList<Menu>> mapChild = new HashMap<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_plato);


        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Menu");

        /** Inicializar el exapandible **/
        expandableListView = (ExpandableListView) findViewById(R.id.expLVMod);
        listCategorias = new ArrayList<>();
        mapChild = new HashMap<>();


        GenerarContenido();




    }



    private void GenerarContenido() {

        try{
            listdireccion.clear();
            listCategorias.clear();
            /** Categorias de menu **/
            listCategorias.add("Bebida Fria");
            listCategorias.add("Desayuno");
            listCategorias.add("Orden");

            /** lista del menu **/
            ArrayList<Menu> L1Menu = new ArrayList();       //bebida fria
            ArrayList<Menu> L2Menu = new ArrayList();       //orden
            ArrayList<Menu> L3Menu = new ArrayList();       //desayuno


            /***limpiar **/
            L1Menu.clear();
            L2Menu.clear();
            L3Menu.clear();

        for(String e:listCategorias){
            myRef.child(e).addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot e1: snapshot.getChildren()){

                        String id = e1.getKey();
                        String nombre = e1.child("nombre").getValue(String.class);
                        String tipo = e1.child("tipo").getValue(String.class);
                        int precio = e1.child("precio").getValue(Integer.class);
                        String categoria = e1.child("category").getValue(String.class);
                        boolean estado = e1.child("estado").getValue(Boolean.class);
                        Menu obj = new Menu(id,nombre,precio,categoria,estado,tipo);
                        listdireccion.add(e1.getKey());


                        Toast.makeText(ModificarPlatoActivity.this, e1.getKey()+"", Toast.LENGTH_SHORT).show();
                        if(categoria.equals(listCategorias.get(0))){
                            L1Menu.add(obj);
                         }else if(categoria.equals(listCategorias.get(1))){
                            L2Menu.add(obj);
                        }else if(categoria.equals(listCategorias.get(2))){
                            L3Menu.add(obj);
                        }

                    }

                    mapChild.put(listCategorias.get(0),L1Menu);
                    mapChild.put(listCategorias.get(1),L2Menu);
                    mapChild.put(listCategorias.get(2),L3Menu);

                    adapter = new ExpLVAadpterC(ModificarPlatoActivity.this, listCategorias, mapChild,getResources());
                    expandableListView.setAdapter(adapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }





        }catch (Exception x){
            Toast.makeText(this,x.getMessage().toString(),Toast.LENGTH_LONG).show();
            System.out.println("AQUI "+x.getMessage());
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_comanda:{
                //Toast.makeText(getApplicationContext(),"Crear nueva factura",Toast.LENGTH_SHORT).show();
                //OpenDialog();
                startActivity(new Intent(ModificarPlatoActivity.this,AgregarPlato2Activity.class));
                break;
            }


        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.comanda,menu);
        return super.onCreateOptionsMenu(menu);
    }


}