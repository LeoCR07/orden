package com.example.orden.Configuracion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NumeroMesaActivity extends AppCompatActivity {

    protected ProgressDialog progressDialog;
    protected TextView _ViewNumero;
    protected EditText _EditNumero;

    /** firebase **/
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas");
    private int Max = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numero_mesa);

        _ViewNumero = findViewById(R.id.TextViewNM);
        _EditNumero = findViewById(R.id.EditNumeroM);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Obteniendo informacion en linea...");
        progressDialog.show();

        MostrarDato();
    }

    private void MostrarDato() {
        myRef.child("Cantidad").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int valor = snapshot.getValue(Integer.class);
                valor--;
                _ViewNumero.setText(valor+"");
                progressDialog.dismiss();
                Max = valor;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void ClickValidar(View view) {

        if(!_EditNumero.getText().toString().isEmpty()){
            int valor = Integer.parseInt(_EditNumero.getText().toString());
            if(valor<51){
               // progressDialog.show();
                Cambiar(valor);
            }else{
                Toast.makeText(this, "El Maximo permitido es 50", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Debes ingresar un numero", Toast.LENGTH_SHORT).show();
        }

    }

    private void Cambiar(int value) {

        myRef.child("Salon").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String llave = "Sin definir";
                for(DataSnapshot e:snapshot.getChildren()){
                    llave = e.getKey();     //Obtengo la ultima mesa activa
                }
                int UltimaMesa = Integer.parseInt(llave);

                if(UltimaMesa>value){
                    Toast.makeText(NumeroMesaActivity.this, "No es posible realizar la actualizacion, debido que la mesa "+UltimaMesa +" se encuentra activa", Toast.LENGTH_LONG).show();
                }else{
                    int aux = value;
                    aux++;
                    myRef.child("Cantidad").setValue(aux);
                    Toast.makeText(NumeroMesaActivity.this, "Datos Actulizados", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // myRef.child("Cantidad").setValue(value);
    }
}