package com.example.orden.Comanda;

import com.example.orden.Configuracion.ModeloNuevoPlato;

import java.util.ArrayList;

public interface ExampleDialogListener {
    void EditarObjMenuComentarioUnico(String comentario, Menu e,ArrayList<ModeloNuevoPlato> listOpc,int position);
    void CrearObjMenuOpcionesUnica(String comentario, Menu e, ArrayList<ModeloNuevoPlato> listOpc,int position);
    void CrearObjMenuComentarioSimple(String comentario, Menu e);
    void EditarObjMenuComentarioSimple(String comentario, Menu e);
}
