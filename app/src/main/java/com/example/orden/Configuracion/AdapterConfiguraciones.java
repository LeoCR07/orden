package com.example.orden.Configuracion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.orden.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orden.Caja.ModeloMenuFirebase;

import java.util.List;



public class AdapterConfiguraciones extends ArrayAdapter<ModeloNuevoPlato> {

    private Context context;
    private int recursos;

    public AdapterConfiguraciones(@NonNull Context context, int resource, @NonNull List<ModeloNuevoPlato> objects) {
        super(context, resource, objects);
        this.context = context;
        this.recursos = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(recursos, parent, false);

        String nombre = getItem(position).getNombre();
       // String tipo = getItem(position).getTipo();
        int precio = getItem(position).getPrecio();

        TextView ViewNombre = convertView.findViewById(R.id.TextViewNombreCF);
        TextView ViewPrecio = convertView.findViewById(R.id.TextViewPrecioCF);
        //TextView ViewTipo = convertView.findViewById(R.id.TextViewEstiloCF);


        ViewNombre.setText(nombre);
        ViewPrecio.setText(precio+"");
       // ViewTipo.setText(tipo);
        //ViewNombre.setText("Hola");

        return  convertView;
    }
}
