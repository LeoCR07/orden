package com.example.orden.General;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.orden.Caja.FacturaActivity;
import com.example.orden.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> implements View.OnClickListener {

    private String layout;
    private Context context;
    private List<ModeloFactura> Itemlist;
    private  View.OnClickListener mlistener;


    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("SodaPoas/Usuarios");

    @Override
    public void onClick(View view) {
        if(mlistener!=null){
            mlistener.onClick(view);
        }
    }

    public void setOnIteamClickListener( View.OnClickListener listener) {
        this.mlistener = listener;
    }

    public MyAdapter(Context context, List<ModeloFactura> list,String layout) {
        this.context = context;
        this.Itemlist = list;
        this.layout = layout;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;


        if(layout.equals("Grid")){
            itemView = LayoutInflater.from(context).inflate(R.layout.grid,parent,false);
        }else if(layout.equals("Usuario")){
            itemView = LayoutInflater.from(context).inflate(R.layout.linear_usuarios,parent,false);
        }else if(layout.equals("UsuarioEdit")){
            itemView = LayoutInflater.from(context).inflate(R.layout.linear_editar_usuario,parent,false);
        }else{
            //factura
            itemView = LayoutInflater.from(context).inflate(R.layout.linear_factura,parent,false);
        }

        itemView.setOnClickListener(this);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if( layout.equals("Grid")){
            holder.Nombre.setText(Itemlist.get(position).getNombre());
            holder.Img.setImageResource(Itemlist.get(position).getImagen());
        }else if(layout.equals("Usuario")){
            holder.Nombre.setText(Itemlist.get(position).getNombre());
        }else if(layout.equals("UsuarioEdit")){
            holder.Nombre.setText(Itemlist.get(position).getNombre());
            holder.Img.setImageResource(Itemlist.get(position).getImagen());
            holder.Img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Eliminar Usuarios
                    myRef.child(Itemlist.get(position).getNombre()).removeValue();
                }
            });
        }else{
            //factura
            holder.Nombre.setText(Itemlist.get(position).getNombre());
            holder.Img.setImageResource(Itemlist.get(position).getImagen());
            holder.ID_Factuta.setText(Itemlist.get(position).getID());
            holder.menuPopUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(context,v);
                    popupMenu.getMenuInflater().inflate(R.menu.factura,popupMenu.getMenu());
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){

                                case R.id.item_0:{
                                    Toast.makeText(context,"Cambiar mesa "+Itemlist.get(position).getNombre(),Toast.LENGTH_SHORT).show();
                                    break;
                                }

                                case R.id.item_1:{
                                    Toast.makeText(context,"Cambiar mesa "+Itemlist.get(position).getNombre(),Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                case R.id.item_2:{
                                    Toast.makeText(context,"Eliminar "+Itemlist.get(position).getNombre(),Toast.LENGTH_SHORT).show();


                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference(Itemlist.get(position).getPath());
                                    String id = Itemlist.get(position).getID();
                                    myRef.child(id).removeValue();

                                    break;
                                }
                            }
                            return true;
                        }
                    });
                }
            });
        }




    }



    @Override
    public int getItemCount() {
        return Itemlist.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
