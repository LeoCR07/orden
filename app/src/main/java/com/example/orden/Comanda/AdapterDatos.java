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

import com.example.orden.R;

import java.util.LinkedList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {
    public  static LinkedList<Menu> e; //objeto de las lista
    Drawable MyDrawable;
    Resources resources;
    Context context;
    FragmentManager Support;


    public AdapterDatos() {
    }

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
                    Toast.makeText(context, ""+e.get(position).getNombre(), Toast.LENGTH_SHORT).show();


                    if((!e.get(position).getEleccionMultiple())){
                        ExampleDialog dialog = new ExampleDialog(e.get(position),"EditarComentario");
                        dialog.show(Support,"example dialog");

                    }else{
                        ExampleDialog dialog = new ExampleDialog(e.get(position),"EditarPreferenceAndDescription");
                        dialog.show(Support,"example dialog");
                    }
                    //diferenciar si tiene preferencia o no

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

            /*
            Drawable drawable1 = resources.getDrawable(R.drawable.ic_color_1);
            Drawable drawable2 = resources.getDrawable(R.drawable.ic_color_2);
            Float densidad = resources.getDisplayMetrics().density;
            int WithHight = Math.round(20*densidad);
            drawable1.setBounds(0,0,WithHight,WithHight);
            drawable2.setBounds(0,0,WithHight,WithHight);

             */

            if((m.getCategoria()==Categorias.BATIDOS)||(m.getCategoria()==Categorias.CALIENTE)){
               // dato.setCompoundDrawables(drawable1,null,null,null);

                dato.setTextColor(Color.parseColor("#3C70AF"));
                //dato.setTextColor(Color.parseColor(String.valueOf(R.color.colortextobebidas)));
            }else{
               dato.setTextColor(Color.parseColor("#379E37"));

                //dato.setTextColor(Color.parseColor("#"+(R.color.colortextocomidas)));

            }


            dato.setText(m.getCantidad()+": "+m.getNombre() + " " +m.getPreferencia()+" "+m.getComentario());





/*
            if((m.getPreferencia()==null)&&(m.getComentario()==null)){
                dato.setText(String.valueOf(m.getCantidad()+": "+m.getNombre()));
            }else{
                dato.setText(String.valueOf(m.getCantidad()+": "+m.getNombre() + " " +m.getPreferencia()+" "+m.getComentario()));
            }
*/

        }
    }
}
