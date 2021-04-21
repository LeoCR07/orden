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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.orden.General.AdapterPagerView;
import com.example.orden.General.ModeloPagerView;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

        int ancho = metrics.widthPixels;
        int alto = metrics.heightPixels;


        getWindow().setLayout((int)(ancho*0.80),(int)(alto*0.5));
    }


    public void ClickComanda(View view) {
        Intent in = new Intent(ComandaA1Activity.this,ComandaActivity.class);
        String mesa = getIntent().getStringExtra("num");
        in.putExtra("n",mesa);
        startActivity(in);
    }

    public void ClickComandaLlevar(View view) {
        Intent in = new Intent(ComandaA1Activity.this,ComandaActivity.class);
        String mesa = getIntent().getStringExtra("num");
        in.putExtra("n",mesa);
        in.putExtra("llevar","llevar");
        startActivity(in);
    }
}