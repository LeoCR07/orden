package com.example.orden.Caja;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orden.Comanda.Menu;
import com.example.orden.General.ModeloFactura;
import com.example.orden.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdapaterCaja extends ArrayAdapter<Menu> {

    private int recursos;
    private Context context;
    private int cantidad,suma;
    private int precio;


    public AdapaterCaja(@NonNull Context context, int resource, @NonNull ArrayList<Menu> objects) {
        super(context, resource, objects);
        recursos = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        /*** platos ***/
        String nombre = getItem(position).getNombre();
        String preferencia = getItem(position).getPreferencia();
        String comentario = getItem(position).getComentario();
        cantidad = getItem(position).getCantidad();
        precio = getItem(position).getPrecio();
        //suma = getItem(position).getSeleccion();


        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(recursos, parent, false);

        /*** Pantalla platos ***/
        TextView ViewNombre = convertView.findViewById(R.id.TxtViewNombre);
        TextView ViewCantidadR = convertView.findViewById(R.id.TxtViewCantidadRealtime);
        TextView ViewPrecio = convertView.findViewById(R.id.TxtViewPrecio);
        TextView ViewApagar = convertView.findViewById(R.id.TxtViewCantidadLocal);

        ViewCantidadR.setText(cantidad + "");
        ViewNombre.setText(nombre +" "+preferencia+" "+ comentario);
        ViewPrecio.setText(suma * precio+"");
        ViewApagar.setText(suma+"");


        return convertView;
    }



}