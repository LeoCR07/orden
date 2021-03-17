package com.example.orden.General;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orden.R;


public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView menuPopUp;
    TextView Nombre;
    ImageView Img;
    TextView ID_Factuta;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        menuPopUp = itemView.findViewById(R.id.ImgView_Opc);
        Nombre = itemView.findViewById(R.id.cart_iteam_name);
        Img = itemView.findViewById(R.id.cart_view_img);
        ID_Factuta = itemView.findViewById(R.id.cart_iteam_ID);
    }

}
