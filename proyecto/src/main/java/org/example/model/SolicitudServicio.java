package org.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudServicio {

    private String codigo;
    private LocalDate fechaCreacion;
    private boolean atencionPresencial;
    private double costoTotal;
    private Cliente theCliente;
    private List<ServicioTecnologico> elementosContratados;

    public SolicitudServicio(String codigo, LocalDate fechaCreacion, boolean atencionPresencial, double costoTotal, List<ServicioTecnologico> elementosContratados) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
        this.atencionPresencial = atencionPresencial;
        this.costoTotal = costoTotal;
        this.elementosContratados = new ArrayList<>();
    }
    


    // Calcula el costo total de los servicios contratados
    public double calcularCostoTotal() {
        double total = 0;

        for (ServicioTecnologico servicio : elementosContratados) {
            total += servicio.calcularCosto();
        }

        return total;
    }


    @Override
    public String toString() {
        return "SolicitudServicio{" +
                "codigo='" + codigo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", cliente=" + cliente +
                ", elementosContratados=" + elementosContratados +
                ", atencionPresencial=" + atencionPresencial +
                ", costoTotal=" + costoTotal +
                '}';
    }
}
