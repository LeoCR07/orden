package com.example.orden.Configuracion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orden.Actividad.AdapterUsuario;
import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.General.ModeloFactura;
import com.example.orden.General.MyAdapter;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditarUsuarioActivity extends AppCompatActivity {


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Usuarios");


    protected RecyclerView recyclerViewUsuario;
    protected MyAdapter adapter;
    private List<ModeloFactura> itemList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        try{
            ActionBar actionbar = getSupportActionBar();
            actionbar.setTitle("Usuarios");

            recyclerViewUsuario = findViewById(R.id.RecyclerEditarUsuario);
            recyclerViewUsuario.setHasFixedSize(true);
            recyclerViewUsuario.setLayoutManager(new LinearLayoutManager(this));
             LlenarArray();
        }catch (Exception x){
            Toast.makeText(this, x.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }


    public void LlenarArray() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemList.clear();
                for(DataSnapshot e:snapshot.getChildren()){
                    itemList.add(new ModeloFactura(R.drawable.ic_delete,e.getValue(String.class)));
                }
                adapter = new MyAdapter(getApplicationContext(),itemList,"UsuarioEdit");
                recyclerViewUsuario.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void Click_NuevoUsuario(View view) {

        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.comentario,null);
        EditText _Usuario = v.findViewById(R.id.EditComentarioX1);
        _Usuario.setHint("Escribe el nombre");
        AlertDialog.Builder builder = new AlertDialog.Builder(EditarUsuarioActivity.this);
        builder.setTitle("Nuevo usuario")
                .setCancelable(false)
                .setView(v)
                .setNegativeButton("cancelar",null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nombre = _Usuario.getText().toString().trim();

                        if(!nombre.isEmpty()){
                            myRef.child(nombre).setValue(nombre);
                        }else{
                            Toast.makeText(EditarUsuarioActivity.this, "No puede ir vacio", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}