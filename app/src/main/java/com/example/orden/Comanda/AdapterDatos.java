package com.example.orden.Comanda;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orden.Configuracion.ModeloNuevoPlato;
import com.example.orden.R;

import java.util.LinkedList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    public  static LinkedList<Menu> e; //objeto de las lista
    Drawable MyDrawable;
    Resources resources;
    Context context;
    FragmentManager Support;

    public AdapterDatos(LinkedList<Menu> lisDatos, Resources resources, Context context, FragmentManager Support) {
        this.resources = resources;
        this.e = lisDatos;
        this.context = context;
        this.Support = Support;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,null,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_comanda,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, final int position) {
            holder.asiganarDatos(e.get(position));

            holder.menuPopUp_plato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context, ""+e.get(position).getNombre(), Toast.LENGTH_SHORT).show();

                    if(e.get(position).getTipo().equals("Simple")){
                        //Toast.makeText(context,e.get(position).getComentario()+"", Toast.LENGTH_SHORT).show();
                        ExampleDialog dialog = new ExampleDialog(e.get(position),"EditarComentarioSimple");
                        dialog.show(Support,"example dialog");

                    }else{
                        ExampleDialog dialog = new ExampleDialog(e.get(position),"EditarObjMenuComentarioUnico");
                        dialog.show(Support,"example dialog");
                    }

                }

            });

    }


    @Override
    public int getItemCount() {
        return e.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        private TextView dato;
        ImageView menuPopUp_plato;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            //dato = itemView.findViewById(R.id.IDDato);
            dato = itemView.findViewById(R.id.item_plato);
            menuPopUp_plato = itemView.findViewById(R.id.Img_EditarPlato);

        }

        public void asiganarDatos(Menu m) {

           // Toast.makeText(context, ""+m.getCategory(), Toast.LENGTH_SHORT).show();
            //Sirve para enviar a la lista con colores diferente las bebidas de las comidas
            if(m.getCategory().equals("Bebida Fria")){
                dato.setTextColor(Color.parseColor("#B27C18"));
            }else{
                dato.setTextColor(Color.parseColor("#379E37"));
            }

            //Para mostrar los datos el simple solo muestra la cantidad nombre y comentario si fuera el caso
            if(m.getTipo().equals("Simple")){
                dato.setText(m.getCantidad()+": "+m.getNombre() + " "+m.getComentario());
            }else if(m.getTipo().equals("Unica")){
                String seleccionada = "";
                for(ModeloNuevoPlato e: m.getOpciones()){
                    if(e.getSelec()){
                        seleccionada = e.getNombre();
                    }
                }
                dato.setText(m.getCantidad()+": "+m.getNombre() +" "+seleccionada+" "+m.getComentario());
            }
        }
    }
}
