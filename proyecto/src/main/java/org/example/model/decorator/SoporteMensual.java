package org.example.model.decorator;

import org.example.model.composite.ElementoSistema;

public class SoporteMensual extends Decorator{

    /**
     * Metodo constructo de la clase SoporteMensual
     * @param elementoSistema
     */
    public SoporteMensual(ElementoSistema elementoSistema) {
        super(elementoSistema);
    }

    /**
     * Metodo para declarar el costo adicional de la clase SoporteMensual
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoSistema.calcularCosto() + 50000;
    }
}
