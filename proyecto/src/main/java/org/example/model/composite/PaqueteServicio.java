package org.example.model.composite;

import java.util.ArrayList;
import java.util.List;

public class PaqueteServicio implements ElementoSistema {

    private List<ElementoSistema> listElementos;


    /**
     * Metodo que permite crear la clase ParquetService
     */
    public PaqueteServicio() {
        this.listElementos = new ArrayList<>();
    }

    /**
     * Metdo que permite calcular el costo de la sumatoria de elementos dentro del paquete
     * @return
     */
    @Override
    public double calcularCosto() {
        double resultado = 0;

        for (ElementoSistema elemento : listElementos) {
            resultado += elemento.calcularCosto();
        }

        return resultado;
    }

    /**
     * Metodo para agregar un elemento al paquete
     * @param elemento
     */
    public void agregarElemento(ElementoSistema elemento) {
        listElementos.add(elemento);
    }


    /**
     * Metodo para eliminar un elemento del paquete
     * @param elemento
     */
    public void eliminarElemento(ElementoSistema elemento) {
        listElementos.remove(elemento);
    }


    public List<ElementoSistema> getListElementos() {
        return listElementos;
    }
    public void setListElementos(List<ElementoSistema> listElementos) {
        this.listElementos = listElementos;
    }
}
