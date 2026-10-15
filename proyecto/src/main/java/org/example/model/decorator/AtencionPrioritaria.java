package org.example.model.decorator;

import org.example.model.composite.ElementoSistema;

public class AtencionPrioritaria extends Decorator {

    /**
     * Metodo que construye la clase AtencionPrioritaria
     * @param elementoSistema
     */
    public AtencionPrioritaria(ElementoSistema elementoSistema) {
        super(elementoSistema);
    }

    /**
     * Metodo que permite agragar el valor adicional de AtencionPrioritaria
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoSistema.calcularCosto() + 35000;
    }
}
