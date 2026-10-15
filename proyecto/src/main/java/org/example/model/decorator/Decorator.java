package org.example.model.decorator;

import org.example.model.composite.ElementoSistema;

public abstract class Decorator implements ElementoSistema {

    protected final ElementoSistema elementoSistema;

    public Decorator(ElementoSistema elementoSistema) {
        this.elementoSistema = elementoSistema;
    }
}
