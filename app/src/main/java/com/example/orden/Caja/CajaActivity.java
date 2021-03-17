package com.example.orden.Caja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.Comanda.MainComandaActivity;
import com.example.orden.General.ModeloFactura;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CajaActivity extends AppCompatActivity {


    private TextView _Pantalla;
    protected ListView _ListaViewPantalla;
    protected TextView _Total;
    private String Dir= "";
    private int igual = 0;
    protected Switch _BtnSwitch;


    public ArrayList<ModeloMenuFirebase> lista = new ArrayList<>();
    //public static LinkedList<com.example.orden.Comanda.Menu> lista = new LinkedList<>();
    protected AdapaterCaja adapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Dir = getIntent().getStringExtra("factura");
        getSupportActionBar().setTitle(Dir);
        adapater = new AdapaterCaja(getApplicationContext(),R.layout.caja_view,lista);


        _ListaViewPantalla = findViewById(R.id.ListViewPantalla);
        _Total = findViewById(R.id.TxtViewTotal);
        _BtnSwitch = findViewById(R.id.switch_total);
        Realtime(Dir);

    }

    private void Realtime(String address) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefsalon = database.getReference(address);
        ValueEventListener listener = new CajaListener();
        myRefsalon.addValueEventListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.EditarComanda :{
                //Intent in = new Intent(getApplicationContext(),EditarComandaActivity.class);
                //Intent in = new Intent(getApplicationContext(),MainComandaActivity.class);
               // in.putExtra("address",Dir);
                //startActivity(in);

               // Toast.makeText(getApplicationContext(),"Editar comanda",Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.Agregar:{
                Toast.makeText(getApplicationContext(),"Agregar golosinas",Toast.LENGTH_SHORT).show();
                break;
            }

            case R.id.item_cierre:{
                Toast.makeText(getApplicationContext(),"Realizar el cierre",Toast.LENGTH_SHORT).show();
                break;
            }
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.caja,menu);
        return super.onCreateOptionsMenu(menu);
    }


    /*Switch boton*/
    public void OnClickTodo(View view) {

        if(_BtnSwitch.isChecked()){
            //actualiza la pantalla enviando la cantidad a seleccionados
            for(ModeloMenuFirebase e:lista){
                e.setSeleccion(e.getCantidad());
            }
            //actualizar la varible para llevar el mismo control
            igual = lista.size();
            Toast.makeText(getApplicationContext(), "Toda la mesa", Toast.LENGTH_SHORT).show();
        }else{

            //se deben actulizar igual los componentes que muestran el mismo dato
            for(ModeloMenuFirebase e:lista){
                e.setSeleccion(0);
                igual = 0;
            }

            Toast.makeText(this, "Separado", Toast.LENGTH_SHORT).show();
        }

        ActualizarTotal();
        adapater.notifyDataSetChanged();
    }


    private class CajaListener implements ValueEventListener {

        int total = 0;



        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            lista.clear();
            //Evita la duplicacion de datos
            total =(int) snapshot.getChildrenCount();

            for(int i=0;i<total;i++){
                String hijo =String.valueOf(i);
                lista.add(snapshot.child(hijo).getValue(ModeloMenuFirebase.class));

            }
            Toast.makeText(CajaActivity.this, lista.get(0).getNombre(), Toast.LENGTH_SHORT).show();

            _ListaViewPantalla.setAdapter(adapater);
            _ListaViewPantalla.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    /*
                    LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
                    View v = inflater.inflate(R.layout.caja_view, parent, false);
                    _Selecionador = v.findViewById(R.id.TxtViewCantidadLocal);
                    _Selecionador.setText("12");
                     */
                    //lista.get(position).setNombre("camaron");

                    //agrega 1 a selccionador y no deja agregar mas que la cantidad
                    if((lista.get(position).getSeleccion())<(lista.get(position).getCantidad())){
                        lista.get(position).Agregar();

                    }else{
                        _BtnSwitch.setChecked(false);
                        //se envia 0 SOLO a un dato de la lista  y no toda la lista,
                        // por eso se baja una a la variable si se le envia cero a la varibale es como poner
                        // cero a todos en la lista y los componentes que muestren el mismo dato se deben actualizar igual por eso a
                        //la varible igual se le pone 0
                        lista.get(position).setSeleccion(0);
                        igual--;
                    }



                    //
                    if((lista.get(position).getSeleccion()==lista.get(position).getCantidad())){
                        //unica forma de sumar la varibale solo cuando son iguales
                        igual+=1;
                    }

                    for(int i = 0;i<lista.size();i++){

                    }

                    if(igual ==lista.size()){
                        _BtnSwitch.setChecked(true);
                    }

                    ActualizarTotal();
                    adapater.notifyDataSetChanged();
                    //ListaViewPantalla.setAdapter(adapater);

                    Toast.makeText(CajaActivity.this, "I:"+igual+"L:"+lista.size(), Toast.LENGTH_SHORT).show();
                //    Toast.makeText(getApplicationContext(),lista.get(position).getComentario(),Toast.LENGTH_SHORT).show();

                }
            });
            _ListaViewPantalla.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(CajaActivity.this, lista.get(position).getComentario()+"", Toast.LENGTH_SHORT).show();

                    if(lista.get(position).getComentario()==""){
                        Toast.makeText(CajaActivity.this, "no es posible modificar el precio", Toast.LENGTH_SHORT).show();
                    }else{
                        ModificarPrecioEspecial();
                    }

                    return false;

                }
            });

/*
            _BtnSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(_BtnSwitch.isChecked()){
                        //Toast.makeText(getApplicationContext(), lista.get(0).getNombre()+"", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "Separado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
*/

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

    private void ModificarPrecioEspecial() {

        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("Indica el nuevo precio")
                .setCancelable(false)
                .setNegativeButton("cancelar",null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = build.create();
        alert.show();

    }

    private void ActualizarTotal(){
        int total = 0;

        for(ModeloMenuFirebase e:lista){
            total += e.getSeleccion()*e.getPrecio();
        }

        _Total.setText(total+"");

    }

}