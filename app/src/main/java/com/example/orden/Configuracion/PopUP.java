package com.example.orden.Configuracion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.R;

public class PopUP extends AppCompatActivity {

    protected TextView _ViewMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_u_p);

        try{

            _ViewMensaje = findViewById(R.id.TextMensajeAyuda);
            String mensaje = getIntent().getStringExtra("mensaje");

            _ViewMensaje.setText(mensaje);
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getRealMetrics(metrics);

            int ancho = metrics.widthPixels;
            int alto = metrics.heightPixels;

            getWindow().setLayout((int)(ancho*0.80),(int)(alto*0.5));
        }catch (Exception x){
            Toast.makeText(this, ""+x.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(x.getMessage());
        }


    }
}