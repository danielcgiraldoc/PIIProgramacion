package org.example.services;

import org.example.model.Empresa;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.decorator.Decorator;

import java.util.List;

public class PaqueteService {

    private final Empresa empresa = Empresa.getInstance();

    /**
     * Metodo para crear un paquete
     * @param codigo
     * @return
     */
    public PaqueteServicio crearPaquete(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del paquete es obligatorio");
        }
        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un paquete con ese código");
        }
        PaqueteServicio paquete = new PaqueteServicio(codigo);
        empresa.getListPaquetes().add(paquete);
        return paquete;
    }

    /**
     * Metodo que permite buscar un paquete de servicio por su codigo
     * @param codigo
     * @return
     */
    public PaqueteServicio buscarPorCodigo(String codigo) {
        for (PaqueteServicio paquete : empresa.getListPaquetes()) {
            if (paquete.getCodigo().equals(codigo)) {
                return paquete;
            }
        }
        return null;
    }

    /**
     * Metodo para listar los paquetes de servicio de la empresa
     * @return
     */
    public List<PaqueteServicio> listarPaquetes() {
        return empresa.getListPaquetes();
    }

    /**
     * Metodo para agregar un nuevo elemento al paquete
     * @param paquete
     * @param elemento
     */
    public void agregarElemento(PaqueteServicio paquete, ElementoContratado elemento) {
        if (paquete == null || elemento == null) {
            throw new IllegalArgumentException("El paquete y el elemento son obligatorios");
        }
        if (contiene(elemento, paquete)) {
            throw new IllegalArgumentException("Un paquete no puede incluirse a sí mismo, directa o indirectamente");
        }
        paquete.agregarElemento(elemento);
    }

    /**
     * Metodo para eliminar un elemento del paquete
     * @param paquete
     * @param elemento
     */
    public void eliminarElemento(PaqueteServicio paquete, ElementoContratado elemento) {
        paquete.eliminarElemento(elemento);
    }

    /**
     * Metodo para consultar los elementos de un paquete
     * @param paquete
     * @return
     */
    public List<ElementoContratado> consultarElementos(PaqueteServicio paquete) {
        return paquete.getListElementos();
    }

    /**
     * Metodo para calcular el costo del paquete (invocacion)
     * @param paquete
     * @return
     */
    public double calcularCostoTotal(PaqueteServicio paquete) {
        return paquete.calcularCosto();
    }

    /**
     * Metodo para verificar si un elemento esta contenido en un paquete u otro elemento
     * @param contenedor
     * @param buscado
     * @return
     */
    private boolean contiene(ElementoContratado contenedor, ElementoContratado buscado) {
        if (contenedor == buscado) {
            return true;
        }
        if (contenedor instanceof Decorator) {
            return contiene(((Decorator) contenedor).getElementoContratado(), buscado);
        }
        if (contenedor instanceof PaqueteServicio) {
            for (ElementoContratado hijo : ((PaqueteServicio) contenedor).getListElementos()) {
                if (contiene(hijo, buscado)) {
                    return true;
                }
            }
        }
        return false;
    }
}