package com.example.orden.Configuracion;

import androidx.appcompat.app.ActionBar;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orden.Comanda.Categorias;
import com.example.orden.Comanda.Menu;
import com.example.orden.General.MyAdapter;
import com.example.orden.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AgregarPlato2Activity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Menu");

    private String[] CategoriaS = {"Entrada","Desayuno", "Almuerzo", "Bebida Fria", "Postre","Orden"};
    private String[] Estilo = {"Simple", "Unica", "Multiple"};

    private ArrayList<ModeloNuevoPlato> ListaOpciones = new ArrayList<>();

    protected AdapterConfiguraciones adapterConfiguraciones;
    protected TextInputLayout _EditPrecio, _EditNombre;
    protected Spinner _SpinerEleccion, _SpinnerCategoria;
    protected ListView _ListaCentral;
    protected Button BtnAgregar,BtnEnviar;
    protected LinearLayout _LinearTitulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_plato2);


        try {
            ActionBar actionbar = getSupportActionBar();
            actionbar.setTitle("Crear un nuevo plato");


            adapterConfiguraciones = new AdapterConfiguraciones(AgregarPlato2Activity.this, R.layout.configuracion_view, ListaOpciones);
            _SpinnerCategoria = findViewById(R.id.SpinerCategoria);     //Spinner categoria
            _EditNombre = findViewById(R.id.EditNombreNew);
            _EditPrecio = findViewById(R.id.EditPrecioNew);          //Precio
            _SpinerEleccion = findViewById(R.id.SpinerEleccion);        //spinner tipo
            _ListaCentral = findViewById(R.id.ListaCentral);        //lista
            BtnAgregar = findViewById(R.id.BtnAgregarDialg);        //boton
            _LinearTitulo = findViewById(R.id.linearTitulo);        //titulo

            /** eleccion **/
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AgregarPlato2Activity.this, android.R.layout.simple_spinner_item, Estilo);
            _SpinerEleccion.setAdapter(adapter);

            /** categoria **/
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AgregarPlato2Activity.this, android.R.layout.simple_spinner_item, CategoriaS);
            _SpinnerCategoria.setAdapter(adapter1);

           _SpinerEleccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        _EditPrecio.setVisibility(View.VISIBLE);
                        _LinearTitulo.setVisibility(View.GONE);
                        BtnAgregar.setVisibility(View.GONE);    //Boton de agregar opcionees
                        _ListaCentral.setVisibility(View.GONE);
                        ListaOpciones.clear();
                        adapterConfiguraciones.notifyDataSetChanged();
                    } else if (position == 1) {
                        _EditPrecio.setVisibility(View.GONE);
                        _LinearTitulo.setVisibility(View.VISIBLE);
                        _ListaCentral.setVisibility(View.VISIBLE);
                        BtnAgregar.setVisibility(View.VISIBLE);
                        ListaOpciones.clear();
                        adapterConfiguraciones.notifyDataSetChanged();
                        _EditPrecio.getEditText().setText(null);
                    } else if (position == 2) {
                        _EditPrecio.setVisibility(View.VISIBLE);
                        _LinearTitulo.setVisibility(View.VISIBLE);
                        _ListaCentral.setVisibility(View.VISIBLE);
                        BtnAgregar.setVisibility(View.VISIBLE);
                        ListaOpciones.clear();
                        adapterConfiguraciones.notifyDataSetChanged();
                        _EditPrecio.getEditText().setText(null);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            /** Lista de opciones **/

            _ListaCentral.setAdapter(adapterConfiguraciones);
            _ListaCentral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(AgregarPlato2Activity.this, "" + ListaOpciones.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(AgregarPlato2Activity.this);
                    builder.setTitle("Desea Eliminar a " + ListaOpciones.get(position).getNombre() + "?")
                            .setCancelable(false)
                            .setNegativeButton("cancelar", null)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ListaOpciones.remove(position);
                                    adapterConfiguraciones.notifyDataSetChanged();
                                }
                            });

                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            });



            BtnEnviar = findViewById(R.id.BtnCrearPlatoNuevo);
            BtnEnviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String nombre = _EditNombre.getEditText().getText().toString().trim();
                        String precio = _EditPrecio.getEditText().getText().toString().trim();
                        String tipo = _SpinerEleccion.getSelectedItem().toString();
                        String cat =  _SpinnerCategoria.getSelectedItem().toString();

                        if(ValidarNombre(nombre)){
                            ValidarTipo(tipo,cat,nombre,precio);
                        }

                    }catch (Exception x){
                        Toast.makeText(AgregarPlato2Activity.this, ""+x.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        } catch (Exception x) {
            Toast.makeText(this, x.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }


    }

    public void InfoEstilo(View view) {
        String mensaje = "Simple:Cuando el plato no posee ningun acompañante y ninguna variacion que se pueda elegir " +
                "Por ejemplo: Un gallo de carne\n" +
                "Eleccion unica:Cuando el plato posee varias opciones a elegir pero solo es una valida " +
                "Por ejemplo:Un batido de fresa sus variaciones podria ser agua o en leche.\n" +
                "Eleccion Multiple:Cuando un plato posee varios acopañamientos a elegir " +
                "Por ejemplo:El pinto se puede agregar varios acompañamientos";
        Intent in = new Intent(getApplicationContext(), PopUP.class);
        in.putExtra("mensaje", mensaje);
        startActivity(in);

    }

    public void infoCategoria(View view) {
        String mensaje = "La opcion de categoria sirve para mostrar los platos con un mayor orden, ademas indica a que categoria pertenece el plato";
        Intent in = new Intent(getApplicationContext(), PopUP.class);
        in.putExtra("mensaje", mensaje);
        startActivity(in);
    }

    public void ClickOpenDialog(View view) {

        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.configuracion_nuevo_plato, null);
        EditText _nombre = v.findViewById(R.id.EditNombreDialog);
        EditText _precio = v.findViewById(R.id.EditPrecioDialog);


        AlertDialog.Builder builder = new AlertDialog.Builder(AgregarPlato2Activity.this);
        builder.setTitle("Agregar Opciones")
                .setCancelable(false)
                .setView(v)
                .setNegativeButton("cancelar", null)
                .setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String nombre = _nombre.getText().toString();
                        String precio = _precio.getText().toString();

                        if (nombre.isEmpty() || precio.isEmpty()) {
                            Toast.makeText(AgregarPlato2Activity.this, "No pueden quedar espacios vacios", Toast.LENGTH_SHORT).show();
                        } else {
                            int aux = Integer.parseInt(precio);
                            int c1 = 0;

                            for (ModeloNuevoPlato e : ListaOpciones) {
                                if (nombre.equals(e.getNombre())) {
                                    Toast.makeText(AgregarPlato2Activity.this, "Esa opcion ya existe", Toast.LENGTH_SHORT).show();
                                } else {
                                    c1++;
                                }
                            }

                            if (ListaOpciones.size() == c1) {
                                ListaOpciones.add(new ModeloNuevoPlato(nombre,aux,false));
                                adapterConfiguraciones.notifyDataSetChanged();
                            }
                        }


                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ValidarTipo(String tipo,String cat,String nombre,String precio) {


        if(tipo.equals(Estilo[0])){
            if(ValidarPrecio(precio)){      //SIMPLE
                int valor = Integer.parseInt(precio);
               Menu NuevoA = new Menu(nombre,valor,cat,true,tipo);
               myRef.child(cat).push().setValue(NuevoA);
               Toast.makeText(this, "Se agrego simple", Toast.LENGTH_SHORT).show();
               LimpiarImput();
            }
        }else if(tipo.equals(Estilo[1])){   //UNICA
            ValidarOpciones();
            Menu NuevoB =  new Menu(nombre,0,cat,true,ListaOpciones,tipo);
            myRef.child(cat).push().setValue(NuevoB);
            Toast.makeText(AgregarPlato2Activity.this, "se agrego unica", Toast.LENGTH_SHORT).show();
            LimpiarImput();

        } else if(tipo.equals(Estilo[2])){    //MULTIPLE
            if(ValidarOpciones()){
                if(ValidarPrecio(precio)){
                    int valor = Integer.parseInt(precio);
                    Menu NuevoC = new Menu(nombre,valor,cat,true,ListaOpciones,tipo);
                    myRef.child(cat).push().setValue(NuevoC);
                    Toast.makeText(this, "Se agrego multiple", Toast.LENGTH_SHORT).show();
                    LimpiarImput();
                }
            }
        }

    }

    private void LimpiarImput() {
        _EditNombre.getEditText().setText(null);
        _EditPrecio.getEditText().setText(null);
        ListaOpciones.clear();
        adapterConfiguraciones.notifyDataSetChanged();
    }

    private boolean ValidarPrecio(String precio) {
        if(precio.isEmpty()){
            Toast.makeText(this, "Debes indicar el precio", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private Boolean ValidarOpciones() {
        Boolean valor = false;
        if (ListaOpciones.size() >= 2) {
            //Bien
            valor = true;
        } else if (ListaOpciones.size() == 0) {
            Toast.makeText(this, "Tienes que agregar almenos dos opciones", Toast.LENGTH_LONG).show();
            valor = false;
        } else if (ListaOpciones.size() < 2) {
            Toast.makeText(this, "Si se agrego una opcion, te recomendamos que el plato sea de tipo simple", Toast.LENGTH_LONG).show();
            valor = false;
        }

        return valor;
    }

    private boolean ValidarNombre(String nombre) {
        if (nombre.isEmpty()) {
            Toast.makeText(AgregarPlato2Activity.this, "Debes de llenar el nombre", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void AgregarMenu(String name, String precio) {
        int valor = Integer.parseInt(precio);
        String cat =_SpinnerCategoria.getSelectedItem().toString();
        String tipo = _SpinerEleccion.getSelectedItem().toString();



      //  Toast.makeText(this,tipo+" Bueno", Toast.LENGTH_SHORT).show();

        if (tipo.equals(Estilo[0])){
            // Menu NuevoA = new Menu(name,valor,cat,true,tipo);
            // myRef.child(cat).push().setValue(NuevoA);
            //Toast.makeText(this, "Hola simple", Toast.LENGTH_SHORT).show();

        }else if(tipo.equals(Estilo[1])||tipo.equals(Estilo[2])){
           // Menu NuevoB = new Menu(name,valor,cat,true,tipo);
            //myRef.child(cat).push().setValue(NuevoB);
            //myRef.setValue(NuevoB);
           // Toast.makeText(this, "Hola las dos", Toast.LENGTH_SHORT).show();
        }


    }
}