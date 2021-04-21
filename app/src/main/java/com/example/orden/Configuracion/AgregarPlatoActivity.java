package com.example.orden.Configuracion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orden.Comanda.AdapterDatos;
import com.example.orden.Comanda.Categorias;
import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.R;

import java.util.ArrayList;
import java.util.List;

public class AgregarPlatoActivity extends AppCompatActivity {

    protected ImageView _ImgAgregar;
    protected LinearLayout _linear;
    protected Button BtnEnviar;

    /**************   Lista de Spinner   ******************/
    private String []CategoriaS ={"Entrada","Orden","Desayuno","Almuerzo","Bebidas","Caliente","Gaseosa","Postre"};
    private String[] Estado ={"Activo","Desactivo"};
    private String[]Estilo = {"Simple","Eleccion unica","Eleccion Multiple"};
    protected Spinner _SpinnerEstado,_SpinnerCategoria,_SpinerEleccion;

    /************ lista de elecciones  **************/
    protected ArrayAdapter<String> adapterLista;
    protected ListView _ListaViewEleccion;
    private List<String> ListaEleccion = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_plato);
        try{

           // _ImgAgregar = findViewById(R.id.BtnAgregarOpcion);

            /************ ID ************/
           // BtnEnviar = findViewById(R.id.BotonOpc);
            _SpinnerCategoria = findViewById(R.id.SpinerCategoria);
            _SpinerEleccion = findViewById(R.id.SpinerEleccion);
            _SpinnerEstado = findViewById(R.id.SpinerEstado);
          //  _linear = findViewById(R.id.LinearOP);
           // _ListaViewEleccion = findViewById(R.id.ListViewEleccion);

            /************* Spiner ***********/
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AgregarPlatoActivity.this, android.R.layout.simple_spinner_item,CategoriaS);
            _SpinnerCategoria.setAdapter(adapter1);

            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(AgregarPlatoActivity.this, android.R.layout.simple_spinner_item,Estilo);
            _SpinerEleccion.setAdapter(adapter2);

            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(AgregarPlatoActivity.this, android.R.layout.simple_spinner_item,Estado);
            _SpinnerEstado.setAdapter(adapter3);

            _SpinerEleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){
                    // _linear.setVisibility(View.GONE);
                    }else{
                      //  _linear.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            /*****************  ListView *********************/
            adapterLista = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,ListaEleccion);
            _ListaViewEleccion.setAdapter(adapterLista);

        }catch (Exception x){
            Toast.makeText(this, x.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void InfoEstilo(View view) {
        String mensaje ="Simple:Cuando el plato no posee ningun acompañante y ninguna variacion que se pueda elegir "+
                  "Por ejemplo: Un gallo de carne\n"+
                "Eleccion unica:Cuando el plato posee varias opciones a elegir pero solo es una valida "+
                "Por ejemplo:Un batido de fresa sus variaciones podria ser agua o en leche.\n"+
                "Eleccion Multiple:Cuando un plato posee varios acopañamientos a elegir "+
                "Por ejemplo:El pinto se puede agregar varios acompañamientos";
        Intent in = new Intent(getApplicationContext(), PopUP.class);
        in.putExtra("mensaje",mensaje);
        startActivity(in);

    }

    public void InfoEstado(View view) {
        String mensaje = "Cuando eliges la opcion de activo perimite que el plato salga en el menu, y la opcion de desactivo provoca que el plato" +
                " no este disponible, se desactiva para que no se ofresca";
        Intent in = new Intent(getApplicationContext(), PopUP.class);
        in.putExtra("mensaje",mensaje);
        startActivity(in);
    }

    public void infoCategoria(View view) {
        String mensaje = "La opcion de categoria sirve para mostrar los platos con un mayor orden, ademas indica a que categoria pertenece el plato";
        Intent in = new Intent(getApplicationContext(), PopUP.class);
        in.putExtra("mensaje",mensaje);
        startActivity(in);
    }

    public void ClickAgregarLista(View view) {

        try{
            View Vx1 = LayoutInflater.from(AgregarPlatoActivity.this).inflate(R.layout.comentario,null);
            final EditText ComentarioX1 = Vx1.findViewById(R.id.EditComentarioX1);
            ComentarioX1.setHint("Escribe una opcion");

            AlertDialog.Builder builder = new AlertDialog.Builder(AgregarPlatoActivity.this);
            builder.setTitle("Agrega las elecciones");
            builder.setCancelable(false);
            builder.setView(Vx1);
            builder.setNegativeButton("cancelar", null);
            builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    final String comentario = ComentarioX1.getText().toString().trim();

                    ListaEleccion.add(comentario);
                    adapterLista.notifyDataSetChanged();

                   /* if (!comentario.isEmpty()) {
                        //Valida para que no entre datos iguales
                        for (String e : ListaEleccion) {
                            if (e.equals(comentario)) {
                                Toast.makeText(AgregarPlatoActivity.this, "Ya existe el elemento", Toast.LENGTH_SHORT).show();
                            } else {
                                ListaEleccion.add(comentario);
                                adapterLista.notifyDataSetChanged();
                            }
                        }

                        //Cuando es el primer plato
                        if (ListaEleccion.isEmpty()) {
                            ListaEleccion.add(comentario);
                            adapterLista.notifyDataSetChanged();
                            ComentarioX1.clearComposingText();
                        }
                    } else {
                        Toast.makeText(AgregarPlatoActivity.this, "Debes agregar una opcion", Toast.LENGTH_SHORT).show();
                    }*/

                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }catch (Exception x){
            Toast.makeText(this, ""+x.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(x.getMessage());
        }

    }
}