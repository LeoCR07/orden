package com.example.orden.Configuracion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.General.ModeloFactura;
import com.example.orden.General.MyAdapter;
import com.example.orden.R;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracionActivity extends AppCompatActivity{

    protected TextView ViewNombreTitulo;
    protected RecyclerView recyclerView;
    protected MyAdapter adapter;
    protected LinearLayoutManager layoutManager;
    private List<ModeloFactura> itemList = new ArrayList<>();
    private CharSequence[]array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        try{
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.logo_toolbar);

            ViewNombreTitulo = findViewById(R.id.TituloView);
            recyclerView = findViewById(R.id.RecyclerGrid);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            ViewNombreTitulo.setText("Configuración");



            array = getResources().getStringArray(R.array.ArrayConfiguracion);

            for(int i=0;i<array.length;i++){
                itemList.add(new ModeloFactura(R.drawable.configuracion,array[i]));
            }

            adapter = new MyAdapter(getApplication(),itemList,"Ajustes");
            adapter.setOnIteamClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    String opcion = itemList.get(recyclerView.getChildAdapterPosition(v)).getNombre();
                    if(opcion == array[0]){
                        startActivity(new Intent(ConfiguracionActivity.this,NumeroMesaActivity.class));
                    }
                }
            });

            recyclerView.setAdapter(adapter);

        }catch (Exception x){
            Toast.makeText(this, x.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


}