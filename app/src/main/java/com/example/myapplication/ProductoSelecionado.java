package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class ProductoSelecionado {
    private static ProductoSelecionado instance;
    private List<Productos> selectedProductos;

    private ProductoSelecionado() {
        selectedProductos = new ArrayList<>();
    }

    public static ProductoSelecionado getInstance() {
        if (instance == null) {
            instance = new ProductoSelecionado();
        }
        return instance;
    }

    public List<Productos> getSelectedProductos() {
        return selectedProductos;
    }

    public void addSelectedProductos(Productos producto) {
        selectedProductos.add(producto);
    }

    public void clearSelectedProductos() {
        selectedProductos.clear();
    }

    public void removeSelectedProducto(int position) {
        if (position >= 0 && position < selectedProductos.size()) {
            selectedProductos.remove(position);
        }
    }
}
