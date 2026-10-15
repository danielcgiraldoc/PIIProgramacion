package org.example.model.decorator;

import org.example.model.composite.ElementoContratado;

public class AtencionPrioritaria extends Decorator {

    /**
     * Metodo que construye la clase AtencionPrioritaria
     * @param elementoContratado
     */
    public AtencionPrioritaria(ElementoContratado elementoContratado) {
        super(elementoContratado);
    }

    /**
     * Metodo que permite agragar el valor adicional de AtencionPrioritaria
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoContratado.calcularCosto() + 35000;
    }
}
