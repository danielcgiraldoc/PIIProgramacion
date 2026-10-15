package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class PaqueteServicio {

    private double costo;
    private List<ServicioTecnologico> listServicios;

    /**
     * Metodo que permite crear la clase ParquetService
     */
    public PaqueteServicio() {
        this.costo = calcularCostoTotal();
        this.listServicios = new ArrayList<>();
    }

    /**
     * Metdo que permite calcular el costo de la sumatoria de servicios dentro del paquete
     * @return
     */
    public double calcularCostoTotal(){
        double resultado = 0;
        for (ServicioTecnologico s : listServicios){
            resultado += s.getCostoBase();
        }
        return resultado;
    }




    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public List<ServicioTecnologico> getListServicios() {
        return listServicios;
    }

    public void setListServicios(List<ServicioTecnologico> listServicios) {
        this.listServicios = listServicios;
    }
}
