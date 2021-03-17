package com.example.orden.Comanda;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccionFactura {

    private int Seleccion = 0;

    /** RealTime **/
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Salon");


    public int getSeleccion() {
        return Seleccion;
    }

    public void setSeleccion(int seleccion) {
        Seleccion = seleccion;
    }


    public void AumentarRTime(String ruta,int cant) {
        cant++;
        myRef.child(ruta).child("cantidad").setValue(cant);
    }

    public void DisminuirRTime(String ruta,int cant) {
        //Disminuye el plato o elimina cuando llegue a cero
        cant--;
        if (cant == 0) {
            myRef.child(ruta).removeValue();
        } else {
            myRef.child(ruta).child("cantidad").setValue(cant);
        }
    }




}
