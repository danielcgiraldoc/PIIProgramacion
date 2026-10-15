package org.example.model.decorator;

import org.example.model.composite.ElementoSistema;

public class AtencionDomicilio extends Decorator{

    /**
     * Metodo construct de la clase AtencionDomicilio
     * @param elementoSistema
     */
    public AtencionDomicilio(ElementoSistema elementoSistema) {
        super(elementoSistema);
    }

    /**
     * Metodo para declarar el valor adicional de la AtencionDomicilio
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoSistema.calcularCosto() + 25000;
    }
}
