package com.example.orden.Actividad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.orden.General.ModeloFactura;
import com.example.orden.General.MyViewHolder;
import com.example.orden.R;
import java.util.List;

public class AdapterUsuario  extends RecyclerView.Adapter<AdapterUsuario.MyViewHolderUsuario> implements View.OnClickListener{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<String> Itemlist;
    private  View.OnClickListener mlistener;

    public AdapterUsuario(Context context, List<String> itemlist) {
        this.context = context;
        this.layoutInflater =LayoutInflater.from(context);
        Itemlist = itemlist;
    }

    @Override
    public void onClick(View v) {
        if(mlistener!=null){
            mlistener.onClick(v);
        }
    }

    public void setOnIteamClickListener( View.OnClickListener listener) {
        this.mlistener = listener;
    }


    @NonNull
    @Override
    public MyViewHolderUsuario onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.linear_usuarios,parent,false);
        itemView.setOnClickListener(this);
        return new MyViewHolderUsuario(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUsuario holder, int position) {

        //holder.NombreCuenta.setText("hola");
        Toast.makeText(context, Itemlist.get(position), Toast.LENGTH_SHORT).show();
        holder.NombreCuenta.setText(Itemlist.get(position));
    }


    @Override
    public int getItemCount() {
        return 0;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    public class MyViewHolderUsuario extends RecyclerView.ViewHolder {

        TextView NombreCuenta;

        public MyViewHolderUsuario(@NonNull View itemView) {
            super(itemView);
            NombreCuenta = itemView.findViewById(R.id.cart_iteam_name);
        }

    }



}
