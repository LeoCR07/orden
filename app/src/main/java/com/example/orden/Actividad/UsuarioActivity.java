package com.example.orden.Actividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.orden.Caja.CajaActivity;
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

public class UsuarioActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Usuarios");


    protected RecyclerView recyclerViewUsuario;
    protected MyAdapter adapter;
    private List<ModeloFactura> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);


        try{
            AjustarVentana();


            recyclerViewUsuario = findViewById(R.id.RecyclerUsuario);
            recyclerViewUsuario.setHasFixedSize(true);
            recyclerViewUsuario.setLayoutManager(new LinearLayoutManager(this));
            LlenarArray();

/*
            MyAdapterU.setOnIteamClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UsuarioActivity.this, "Hola", Toast.LENGTH_SHORT).show();
                }
            });
*/

            //recyclerViewUsuario.setAdapter(MyAdapterU);



        }catch (Exception x){
            Toast.makeText(this, ""+x.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(x.getMessage());
        }


    }

    public void LlenarArray() {
  //      itemList.add(new ModeloFactura("Leo"));
//        itemList.add(new ModeloFactura("Meyel"));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot e:snapshot.getChildren()){
                    itemList.add(new ModeloFactura(e.getValue(String.class)));
                }
                adapter = new MyAdapter(getApplicationContext(),itemList,"Usuario");
                recyclerViewUsuario.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void AjustarVentana() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        int ancho = metrics.widthPixels;
        int alto = metrics.heightPixels;

        getWindow().setLayout((int)(ancho*0.80),(int)(alto*0.60));
    }
}