package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Tecnico {

    private String documento;
    private String nombre;
    private String correo;
    private double costoDesplazamiento;
    private List<SolicitudServicio> listSolicitudes;

    /**
     * Metodo que construye al tecnico
     * @param documento
     * @param nombre
     * @param correo
     * @param listSolicitudes
     */
    public Tecnico(String documento, String nombre, String correo, List<SolicitudServicio> listSolicitudes, double costoDesplazamiento) {
        this.documento = documento;
        this.nombre = nombre;
        this.correo = correo;
        this.costoDesplazamiento = costoDesplazamiento;
        this.listSolicitudes = new ArrayList<>();
    }














    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<SolicitudServicio> getListSolicitudes() {
        return listSolicitudes;
    }

    public void setListSolicitudes(List<SolicitudServicio> listSolicitudes) {
        this.listSolicitudes = listSolicitudes;
    }
}
