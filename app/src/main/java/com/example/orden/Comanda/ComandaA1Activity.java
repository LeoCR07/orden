package com.example.orden.Comanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orden.Caja.CajaActivity;
import com.example.orden.General.AdapterPagerView;
import com.example.orden.General.ModeloPagerView;
import com.example.orden.Principal.MainActivity;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ComandaA1Activity extends AppCompatActivity {


    /* FireBase */
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Cantidad");
    public static LinkedHashMap<String,String> mapMesas = new LinkedHashMap<>();


    protected Button BtnSeleccion;
    private Window window;

    protected ViewPager viewPager;
    protected AdapterPagerView adapterPagerView;
    private List<ModeloPagerView> model1s;
    private Integer[]colors = null;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_a1);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.logo_toolbar);

        try{
            model1s = new ArrayList<>();

            BtnSeleccion = findViewById(R.id.BtnSeleccionX2);

            model1s.add(new ModeloPagerView(R.drawable.aqui,"Comer aqui","Podrás crear una cuenta con el número de mesa."));
            model1s.add(new ModeloPagerView(R.drawable.llevar,"Para Llevar","Podrás crear una cuenta para cualquier persona."));


            adapterPagerView = new AdapterPagerView(model1s,this);
            viewPager = findViewById(R.id.ViewPager);
            viewPager.setAdapter(adapterPagerView);
            viewPager.setPadding(130,0,130,0);

            Integer [] colors_temp = {getResources().getColor(R.color.colorAqui),getResources().getColor(R.color.colorLlevar)};

            colors = colors_temp;

            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                   Toast.makeText(ComandaA1Activity.this, ""+position, Toast.LENGTH_SHORT).show();
                    BtnSeleccion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Dialog(position);
                        }
                    });

                }

                @Override
                public void onPageSelected(final int position) {


                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }catch (Exception x){
            Toast.makeText(this,x.getMessage().toString(),Toast.LENGTH_LONG).show();
        }


    }


    private void GenerarDialog(int total,int p) {

        mapMesas.clear();
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.input_mesa,null);

        Spinner spinner = v.findViewById(R.id.SpinnerMesa);

        for(int i =1;i<=total-1;i++){
            String aux = String.valueOf(i);
            mapMesas.put("Mesa "+(i),aux);
        }

        if(p==1){
            mapMesas.put("Para llevar","Llevar");
        }

        Object []Keys = mapMesas.keySet().toArray();

        ArrayAdapter<Object> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,Keys);
        spinner.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(ComandaA1Activity.this);
        builder.setTitle("Elige una mesa")
                .setCancelable(false)
                .setView(v)
                .setNegativeButton("cancelar",null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(ComandaA1Activity.this, ""+mapMesas.size(), Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(getApplicationContext(), MainComandaActivity.class);
                        in.putExtra("mesa",mapMesas.get(spinner.getSelectedItem().toString()).toString());
                        startActivity(in);

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void Dialog(int p) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int total = snapshot.getValue(Integer.class);
                GenerarDialog(total,p);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}