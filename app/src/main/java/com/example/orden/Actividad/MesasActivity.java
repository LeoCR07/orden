  package com.example.orden.Actividad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orden.Caja.FacturaActivity;
import com.example.orden.Configuracion.ConfiguracionActivity;
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

  public class MesasActivity extends AppCompatActivity {

    protected TextView ViewNombreTitulo;
    protected RecyclerView recyclerView;
    protected MyAdapter adapter;
    protected LinearLayoutManager layoutManager;
    protected ProgressDialog progressDialog;
    private List<ModeloFactura> itemList = new ArrayList<>();

    /**realm**/
    private int Cantidad = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        try{

            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.logo_toolbar);

            ViewNombreTitulo = findViewById(R.id.TituloView);
            recyclerView = findViewById(R.id.RecyclerGrid);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
            ViewNombreTitulo.setText("Salon");
            GenerarSalon();


        }catch (Exception x){
            Toast.makeText(this, x.getMessage()+"", Toast.LENGTH_SHORT).show();
        }





       // Toast.makeText(this, MesasActivas.get(0)+"", Toast.LENGTH_SHORT).show();
    }

    private void GenerarSalon() {

        //Posible cambio a la dirreccion en un futuro
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefsalon = database.getReference("SodaPoas");
        valueListener listener = new valueListener();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Obteniendo informacion en linea...");
        progressDialog.show();


        myRefsalon.addValueEventListener(listener);
    }

    private class valueListener implements ValueEventListener {

        int Diferente = 0;  //cuenta el numero de veces que es diferete una mesa
        private ArrayList<Integer>Activas = new ArrayList<>();   //lleva las mesas que esten en la base de datos(Activas)
        private ArrayList<Integer>Desactivas = new ArrayList<>();

        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            itemList.clear();
            Activas.clear();
            //se debe de limpiar antes por si la aplicacion vuelve a iniciar va cargar los mismos datos

            //Total de Mesas en el salon
            Cantidad = snapshot.child("Cantidad").getValue(int.class);

           for (DataSnapshot e:snapshot.child("Salon").getChildren()){
               Activas.add(Integer.parseInt(e.getKey()));       //Numero de mesa activas que asu vez es su key
               progressDialog.dismiss();
           }


            //para saber cuales mesas estan activas o desactivas

            //Cuando no hay ninguna mesa activa
            int contadorAux = 0;
            for(int i = 1; i<=Cantidad; i++){  // i Numero de mesas j Mesas Activas
                for(int j = 0;j<Activas.size();j++){
                    if(Cantidad==i){   //ultima vuelta
                        if(i==Activas.get(j)){
                          itemList.add(new ModeloFactura("Otras",""+Activas.get(j),R.drawable.mesa_activa));
                         }else{
                          contadorAux++;
                         }
                        if(contadorAux==Activas.size()){
                            itemList.add(new ModeloFactura(R.drawable.mesa_inactiva, "Para Llevar"));
                        }
                    }else{
                        if(i==Activas.get(j)){      //si existe
                            itemList.add(new ModeloFactura("Mesa "+i,""+Activas.get(j),R.drawable.mesa_activa));
                        }else{
                            Diferente++;
                        }
                        //Tiene que ser diferente a todas las mesas activas para que este descactiva
                        if(Diferente==Activas.size()){
                          //  itemList.add(new ModeloFactura(R.drawable.mesa_inactiva, "Mesa "+i));
                            itemList.add(new ModeloFactura("Mesa "+i,""+i,R.drawable.mesa_inactiva));

                        }
                    }
                    //se limpia para contar las otras mesas
                    }
                contadorAux = 0;
                Diferente = 0;
                if(Activas.size()==0){
                    itemList.add(new ModeloFactura(R.drawable.mesa_inactiva, "Mesa "+i));
                }

            }



            adapter = new MyAdapter(getApplication(),itemList,"Grid");
            adapter.setOnIteamClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switch (itemList.get(recyclerView.getChildAdapterPosition(view)).getImagen()){
                        case R.drawable.mesa_activa:{
                            //Enviar la mesa para saber la direcion
                            Toast.makeText(getApplicationContext(),itemList.get(recyclerView.getChildAdapterPosition(view)).getPath(),Toast.LENGTH_LONG).show();
                            Intent in = new Intent(getApplicationContext(), FacturaActivity.class);
                            in.putExtra("Mesa",itemList.get(recyclerView.getChildAdapterPosition(view)).getPath());
                            startActivity(in);
                            break;
                        }

                        case R.drawable.mesa_inactiva:{
                            Toast.makeText(MesasActivity.this, "No existen facturas", Toast.LENGTH_SHORT).show();
                            Intent in = new Intent(getApplicationContext(), FacturaActivity.class);
                            in.putExtra("Mesa",itemList.get(recyclerView.getChildAdapterPosition(view)).getPath());
                            startActivity(in);
                            break;
                        }
                    }

                }
            });
            recyclerView.setAdapter(adapter);

            progressDialog.dismiss();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    }

      @Override
      public boolean onOptionsItemSelected(@NonNull MenuItem item) {

          switch (item.getItemId()){
              case R.id.icon_configuracion:{
                //  Toast.makeText(getApplicationContext(),"Ajustes",Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(MesasActivity.this, ConfiguracionActivity.class));
                // startActivity(new Intent(MesasActivity.this, UsuarioActivity.class));
                  break;
              }

          }
          return true;
      }

      @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.actividad,menu);
          return super.onCreateOptionsMenu(menu);
      }
}

