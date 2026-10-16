package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Tecnico {

    private String documento;
    private boolean disponible;
    private double costoDesplazamiento;


    /**
     * Metodo que construye al tecnico
     * @param documento
     * @param disponible
     * @param costoDesplazamiento
     */
    public Tecnico(String documento, boolean disponible, double costoDesplazamiento) {
        this.documento = documento;
        this.disponible = disponible;
        this.costoDesplazamiento = costoDesplazamiento;
    }



public void setDocumento(String documento) {
    this.documento = documento;
}

public double getCostoDesplazamiento() {
    return costoDesplazamiento;
}

public void setCostoDesplazamiento(double costoDesplazamiento) {
    this.costoDesplazamiento = costoDesplazamiento;
}

    public String getDocumento() {
        return documento;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }


    }















