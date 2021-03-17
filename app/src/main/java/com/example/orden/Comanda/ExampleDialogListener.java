package com.example.orden.Comanda;

public interface ExampleDialogListener {
    void CreatePreferenceAndDescription(String preference, String comentario, Menu dish, boolean prevalencia);
    void CreateDescription(String comentario, Menu e, boolean prevalencia);
    void CreateNewName(String nombre, Categorias categoria);

}
