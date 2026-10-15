package org.example.model.composite;

import java.util.ArrayList;
import java.util.List;

public class PaqueteServicio implements ElementoContratado {

    private String codigo;
    private List<ElementoContratado> listElementos;


    /**
     * Metodo que permite crear la clase ParquetService
     */
    public PaqueteServicio(String codigo) {
        this.codigo = codigo;
        this.listElementos = new ArrayList<>();
    }

    /**
     * Metdo que permite calcular el costo de la sumatoria de elementos dentro del paquete
     * @return
     */
    @Override
    public double calcularCosto() {
        double resultado = 0;

        for (ElementoContratado elemento : listElementos) {
            resultado += elemento.calcularCosto();
        }

        return resultado;
    }

    /**
     * Metodo para agregar un elemento al paquete
     * @param elemento
     */
    public void agregarElemento(ElementoContratado elemento) {
        listElementos.add(elemento);
    }


    /**
     * Metodo para eliminar un elemento del paquete
     * @param elemento
     */
    public void eliminarElemento(ElementoContratado elemento) {
        listElementos.remove(elemento);
    }


    public List<ElementoContratado> getListElementos() {
        return listElementos;
    }
    public void setListElementos(List<ElementoContratado> listElementos) {
        this.listElementos = listElementos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
