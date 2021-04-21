package com.example.orden.Comanda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.orden.Configuracion.ModeloNuevoPlato;
import com.example.orden.R;

import java.util.ArrayList;

import static com.example.orden.Comanda.ComandaActivity.myAdapter;

public class ExampleDialog extends AppCompatDialogFragment {

    private com.example.orden.Comanda.ExampleDialogListener listener;
    private Menu e;
    private String Metodo;
    private String TipoCategoria;
    private boolean pre = false;
    private TextView contador;
    private Context contexto;
    ModeloNuevoPlato opc = new ModeloNuevoPlato("",0,false);

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

        //Menu obj = new Menu(e.getEstado(),e.getNombre(),e.getPrecio(),e.getSub(),e.getTipo(),e.getCategory(),e.getComentario(),e.getOpciones());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try{
            Vx1= LayoutInflater.from(getContext()).inflate(R.layout.comentario,null);
            final EditText ComentarioX1 = Vx1.findViewById(R.id.EditComentarioX1);


           // Toast.makeText(getContext(), e.getComentario()+"", Toast.LENGTH_SHORT).show();

           // System.out.println(e.getComentario()+"");
            if(Metodo.equals("GenerarComentario")){
                builder.setTitle(e.getNombre())
                        .setView(Vx1)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String COMENTARIO = ComentarioX1.getText().toString().trim();
                                listener.CrearObjMenuComentarioSimple(COMENTARIO,e);
                            }
                        });
            }else if(Metodo.equals("EditarComentarioSimple")){
                ComentarioX1.setText(e.getComentario());
                builder.setTitle(e.getNombre())
                        .setView(Vx1)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final String COMENTARIO = ComentarioX1.getText().toString().trim();
                               // e.setComentario(COMENTARIO);
                               // myAdapter.notifyDataSetChanged();
                                listener.EditarObjMenuComentarioSimple(COMENTARIO,e);
                            }
                        });

            }else if(Metodo.equals("GenerarOpcionesUnica")){

                final int[] position = new int[1];
                position[0]=-1;
                CharSequence []char1 = new CharSequence[e.getOpciones().size()];
                int i  = 0;
                ArrayList<ModeloNuevoPlato> listOpc = new ArrayList<>();

                for(ModeloNuevoPlato mn :e.getOpciones()){
                    listOpc.add(new ModeloNuevoPlato(mn.getNombre(),mn.getPrecio(),false));
                    char1[i] = mn.getNombre();
                    i++;
                }


                builder.setTitle(e.getNombre())
                        .setView(Vx1)
                        .setSingleChoiceItems(char1, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                position[0] = which;
                                Activar(which);
                                //Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                            }

                            private void Activar(int which) {
                                for(ModeloNuevoPlato e:listOpc){
                                    if(e.getNombre().equals(char1[which])){
                                        e.setSelec(true);
                                    }else{
                                        e.setSelec(false);
                                    }
                                }
                            }
                        })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(position[0]==-1){
                                    Toast.makeText(getContext(), "Debes de elegir una opcion", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    final String COMENTARIO = ComentarioX1.getText().toString().trim();
                                    listener.CrearObjMenuOpcionesUnica(COMENTARIO,e,listOpc,position[0]);
                                }

                            }
                        });
            }else if(Metodo.equals("EditarObjMenuComentarioUnico")){

                final int[] position = new int[1];
                position[0]=-1;
                CharSequence []char1 = new CharSequence[e.getOpciones().size()];
                int i  = 0;
                ArrayList<ModeloNuevoPlato> listOpc = new ArrayList<>();

                ComentarioX1.setText(e.getComentario());

                for(ModeloNuevoPlato mn :e.getOpciones()){

                    if(mn.getSelec()){
                        position[0] = i;
                        listOpc.add(new ModeloNuevoPlato(mn.getNombre(),mn.getPrecio(),true));
                    }else{
                        listOpc.add(new ModeloNuevoPlato(mn.getNombre(),mn.getPrecio(),false));
                    }

                    char1[i] = mn.getNombre();
                    i++;
                }


                builder.setTitle(e.getNombre())
                        .setView(Vx1)
                        .setSingleChoiceItems(char1, position[0], new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                position[0] = which;
                                Activar(which);
                                //Toast.makeText(getContext(), "Hola", Toast.LENGTH_SHORT).show();
                            }

                            private void Activar(int which) {
                                for(ModeloNuevoPlato e:listOpc){
                                    if(e.getNombre().equals(char1[which])){
                                        e.setSelec(true);
                                    }else{
                                        e.setSelec(false);
                                    }
                                }
                            }
                        })
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                final String COMENTARIO = ComentarioX1.getText().toString().trim();
                               // e.setComentario(COMENTARIO);
                                listener.EditarObjMenuComentarioUnico(COMENTARIO,e,listOpc,position[0]);

                            }
                        });
            }
        }catch (Exception X){
            System.out.println(X.getMessage());
           // Toast.makeText(contexto, ""+X.getMessage(), Toast.LENGTH_SHORT).show();
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





}
