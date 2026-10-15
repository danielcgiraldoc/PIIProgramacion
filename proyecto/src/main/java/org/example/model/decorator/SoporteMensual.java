package org.example.model.decorator;

import org.example.model.composite.ElementoContratado;

public class SoporteMensual extends Decorator{

    /**
     * Metodo constructo de la clase SoporteMensual
     * @param elementoContratado
     */
    public SoporteMensual(ElementoContratado elementoContratado) {
        super(elementoContratado);
    }

    /**
     * Metodo para declarar el costo adicional de la clase SoporteMensual
     * @return
     */
    @Override
    public double calcularCosto() {
        return elementoContratado.calcularCosto() + 50000;
    }
}
