package com.example.orden.Comanda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.orden.R;

public class ExampleDialog extends AppCompatDialogFragment {

    private com.example.orden.Comanda.ExampleDialogListener listener;
    private Menu e;
    private String Metodo;
    private String TipoCategoria;
    private boolean pre = false;
    private TextView contador;

    private View Vx2;
    private View Vx1;


    public ExampleDialog(String metodo, String tipoCategoria) {
        this.Metodo = metodo;
        this.TipoCategoria = tipoCategoria;
    }


    public ExampleDialog(Menu plato, String metodo) {
        this.e = plato;
        this.Metodo = metodo;
        this.contador = contador;
   }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Hola 2", Toast.LENGTH_SHORT).show();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        if(Metodo.equals("ApplyPreferenceAndDescription")){
            Vx2 = LayoutInflater.from(getContext()).inflate(R.layout.radiox2,null);

            /** x2 View **/
            final RadioButton rd1x2 = Vx2.findViewById(R.id.Rb1);
            final RadioButton rd2x2 = Vx2.findViewById(R.id.Rb2);
            final EditText ComentarioX2 = Vx2.findViewById(R.id.EditComentarioX2);

            SetRadioButtonValue(rd1x2,rd2x2);
            final Boolean prevalencia = pre;

            builder.setTitle(e.getNombre())
                    .setView(Vx2)
                    .setNegativeButton("Cancelar",null)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String comentario = ComentarioX2.getText().toString().trim();
                            listener.CreatePreferenceAndDescription(GetRadioButtonValue(rd1x2,rd2x2),comentario,e,prevalencia);
                        }
                    });

        }else if(Metodo.equals("ApplyDescription")){
            Vx1= LayoutInflater.from(getContext()).inflate(R.layout.comentario,null);
            final EditText ComentarioX1 = Vx1.findViewById(R.id.EditComentarioX1);

            if(e.getComentario()!=""){
                ComentarioX1.setText(e.getComentario());
                pre = true;
            }

            final boolean prevalencia = pre;
            builder.setTitle(e.getNombre())
                    .setView(Vx1)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String COMENTARIO = ComentarioX1.getText().toString().trim();
                            listener.CreateDescription(COMENTARIO,e,prevalencia);

                        }
                    });



        }else if(Metodo.equals("AddDiferente")){
            Vx1= LayoutInflater.from(getContext()).inflate(R.layout.comentario,null);
            final EditText NombreX1 = Vx1.findViewById(R.id.EditComentarioX1);
            NombreX1.setHint("Agregar tu pedido");
            final Categorias categoria;

            if(TipoCategoria.equals("Bebida")){
                categoria = Categorias.BATIDOS;
            }else{
                categoria = Categorias.CASADO;
            }

            builder.setTitle("Nueva "+TipoCategoria)
                    .setView(Vx1)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String Nombre = "."+NombreX1.getText().toString().trim();
                            listener.CreateNewName(Nombre,categoria);
                        }
                    });

        }else if(Metodo.equals("EditarComentario")){
            Vx1= LayoutInflater.from(getContext()).inflate(R.layout.comentario,null);
            final EditText ComentarioX1 = Vx1.findViewById(R.id.EditComentarioX1);

            ComentarioX1.setText(e.getComentario());

            final boolean prevalencia = true;
            builder.setTitle(e.getNombre())
                    .setView(Vx1)
                    .setCancelable(true)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String COMENTARIO = ComentarioX1.getText().toString().trim();
                            listener.CreateDescription(COMENTARIO,e,prevalencia);

                        }
                    });

        }else if(Metodo.equals("EditarPreferenceAndDescription")){
            Vx2 = LayoutInflater.from(getContext()).inflate(R.layout.radiox2,null);

            /** x2 View **/
            final RadioButton rd1x2 = Vx2.findViewById(R.id.Rb1);
            final RadioButton rd2x2 = Vx2.findViewById(R.id.Rb2);
            final EditText ComentarioX2 = Vx2.findViewById(R.id.EditComentarioX2);

            SetRadioButtonValue(rd1x2,rd2x2);
            ComentarioX2.setText(e.getComentario());

            if(e.getPreferencia()==rd1x2.getText()){
                rd1x2.setChecked(true);
            }else if (e.getPreferencia()==rd2x2.getText()){
                rd2x2.setChecked(true);
            }

            final Boolean prevalencia = true;

            builder.setTitle(e.getNombre())
                    .setView(Vx2)
                    .setNegativeButton("Cancelar",null)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            final String comentario = ComentarioX2.getText().toString().trim();
                            listener.CreatePreferenceAndDescription(GetRadioButtonValue(rd1x2,rd2x2),comentario,e,prevalencia);
                        }
                    });
        }

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{

        }catch (ClassCastException e){
             throw new ClassCastException(context.toString()+" must implement ExampleDialogListener");
        }
        listener = (com.example.orden.Comanda.ExampleDialogListener) context;
    }


    private void SetRadioButtonValue(RadioButton rd1x2, RadioButton rd2x2){

        switch (e.getCategoria()){
            case GALLO:{
                rd1x2.setText("Arreglado");
                rd2x2.setText("Simple");
                break;
            }case DESAYUNO:{
                if(e.getNombre().equals("Pinto de la casa")){
                    rd1x2.setText("Huevo Frito");
                    rd2x2.setText("Huevo Picado");
                    break;
                }else if(e.getNombre().equals("Sandwich")){
                    rd1x2.setText("Carne");
                    rd2x2.setText("Jamon y Queso");
                    break;
                }
            }case SOPAS:{
                rd1x2.setText("Entera");
                rd2x2.setText("Media");
                break;
            }case CALIENTE:{
                if(e.getNombre().equals("Cafe")){
                    rd1x2.setText("Con leche");
                    rd2x2.setText("Negro");

                }else if(e.getNombre().equals("Aguadulce")){
                    rd1x2.setText("Con leche");
                    rd2x2.setText("Negro");

                }else if(e.getNombre().equals("Te")){
                    rd1x2.setText("Manzanilla");
                    rd2x2.setText("Negro");
                }
                break;
            }case BATIDOS:{
                rd1x2.setText("Con leche");
                rd2x2.setText("Con agua");
                break;
            }
        }
    }

    private String GetRadioButtonValue(RadioButton rd1x2, RadioButton rd2x2){
        String preference = String.valueOf(rd1x2.isChecked()? rd1x2.getText():rd2x2.isChecked()?rd2x2.getText():null);
        return preference;
    }




}
