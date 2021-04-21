package com.example.orden.Caja;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.orden.Comanda.ComandaA1Activity;
import com.example.orden.Comanda.Factura;
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
    private String NMesa;    //variable importante
    private String numero;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factura);
        Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();

        NMesa ="SodaPoas/Salon/"+getIntent().getStringExtra("Mesa");
        numero = getIntent().getStringExtra("Mesa");
        getSupportActionBar().setTitle("Mesa "+numero);
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
                itemList.add(new ModeloFactura(R.drawable.mesa_activa_2,"Cuenta "+(i+1),ListaID.get(i),NMesa));
                //NMesa es la dirreccion en firebase
            }

           // Toast.makeText(getApplicationContext(), ListaID.get(0)+"", Toast.LENGTH_SHORT).show();


            adapter = new MyAdapter(getApplicationContext(),itemList,"linear");
            adapter.setOnIteamClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(FacturaActivity.this,CajaActivity.class);
                    //factura = NMesa+"/"+itemList.get(recyclerView.getChildAdapterPosition(view)).getID();
                   factura = NMesa;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.icon_comanda:{
                Intent in = new Intent(FacturaActivity.this, ComandaA1Activity.class);
                in.putExtra("num",numero);
                startActivity(in);
                //OpenDialog();
                break;
            }


        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.comanda,menu);
        return super.onCreateOptionsMenu(menu);
    }


}
