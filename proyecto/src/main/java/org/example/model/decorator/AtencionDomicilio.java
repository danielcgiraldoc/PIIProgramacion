package org.example.model.decorator;

import org.example.model.composite.ElementoContratado;

public class AtencionDomicilio extends Decorator{

    /**
     * Metodo construct de la clase AtencionDomicilio
     * @param elementoContratado
     */
    public AtencionDomicilio(ElementoContratado elementoContratado) {
        super(elementoContratado);
    }

    /**
     * Metodo para declarar el valor adicional de la AtencionDomicilio
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoContratado.calcularCosto() + 25000;
    }
}
