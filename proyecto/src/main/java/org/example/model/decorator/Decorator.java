package org.example.model.decorator;

import org.example.model.composite.ElementoContratado;

public abstract class Decorator implements ElementoContratado {

    protected final ElementoContratado elementoContratado;

    /**
     * Metodo constructor del Decorator
     * @param elementoContratado
     */
    public Decorator(ElementoContratado elementoContratado) {
        this.elementoContratado = elementoContratado;
    }

    public ElementoContratado getElementoContratado() {
        return elementoContratado;
    }
}
