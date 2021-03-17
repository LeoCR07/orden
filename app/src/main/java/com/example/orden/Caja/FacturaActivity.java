package com.example.orden.Caja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

public class FacturaActivity extends AppCompatActivity {

    protected RecyclerView recyclerView;
    protected MyAdapter adapter;
    protected LinearLayoutManager layoutManager;
    //private ProgressDialog progressDialog;

    private List<String> ListaID = new ArrayList<>();
    private List<String>ListaCuenta = new ArrayList<>();
    private List<ModeloFactura> itemList = new ArrayList<>();
    private int cantidad = 0;
    private String NMesa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();

        NMesa ="SodaPoas/Salon/"+getIntent().getStringExtra("Mesa");
        getSupportActionBar().setTitle(NMesa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recycler_facturas);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ShowFacturas();
    }

    private void ShowFacturas(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(NMesa);
        ValueEventListener listener = new facturaslistener();
        myRef.addValueEventListener(listener);
    }

    private class facturaslistener implements ValueEventListener {
       String factura = "";

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            ListaID.clear();
            ListaCuenta.clear();
            itemList.clear();

            //obtener las fac
            cantidad = (int)snapshot.getChildrenCount();

            //obtener id
            for(DataSnapshot e:snapshot.getChildren()){
                ListaID.add(e.getKey());
            }

            for(int i =0;i<cantidad;i++){
                itemList.add(new ModeloFactura(R.drawable.mesa_activa,"Cuenta "+(i+1),ListaID.get(i),NMesa));
            }

           // Toast.makeText(getApplicationContext(), ListaID.get(0)+"", Toast.LENGTH_SHORT).show();


            adapter = new MyAdapter(getApplicationContext(),itemList,"linear");
            adapter.setOnIteamClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(getApplicationContext(),CajaActivity.class);
                    factura = NMesa+"/"+itemList.get(recyclerView.getChildAdapterPosition(view)).getID();
                    in.putExtra("factura",factura);
                    startActivity(in);
                }
            });
            recyclerView.setAdapter(adapter);

        }



        @Override
        public void onCancelled(@NonNull DatabaseError error) {

            Log.w("facturaActividad", "Fallo lectura.", error.toException());
        }
    }
}