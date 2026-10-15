package org.example.model;

import org.example.model.composite.ServicioTecnologico;

import java.util.ArrayList;
import java.util.List;

public class Empresa {

    private static Empresa instance;

    private String nombre;
    private String nit;
    private String correo;
    private String paginaWeb;
    private String descripcion;
    private List<Cliente> listClientes;
    private List<ServicioTecnologico> listServicios;
    private List<Paquete> listPaquetes;

    /**
     * Metodo constructr que llena los datos de la empresa unica
     */

    private Empresa() {
        this.nombre = "TechSolutions";
        this.nit = "1092";
        this.correo = "techsolutions@gmail.com";
        this.paginaWeb = "https//techslutions.com";
        this.descripcion = "Somos una empresa dedicada a alas soluciones tecnologicas";
        this.listClientes = new ArrayList<>();
        this.listServicios = new ArrayList<>();
        this.listPaquetes = new ArrayList<>();
    }


    /**
     * Metodo de instancia unica para la empresa
     * @return
     */
    public static Empresa getInstance(){
        if (instance == null){
            instance = new Empresa();
        }
        return instance;
    }




















    //Getters & Setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNit() { return nit; }
    public void setNit(String nit) { this.nit = nit; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getPaginaWeb() { return paginaWeb; }
    public void setPaginaWeb(String paginaWeb) { this.paginaWeb = paginaWeb; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Cliente> getListClientes() { return listClientes; }
    public void setListClientes(List<Cliente> listClientes) { this.listClientes = listClientes; }

    public List<ServicioTecnologico> getListServicios() { return listServicios; }
    public void setListServicios(List<ServicioTecnologico> listServicios) { this.listServicios = listServicios; }

    public List<Paquete> getListPaquetes() { return listPaquetes; }
    public void setListPaquetes(List<Paquete> listPaquetes) { this.listPaquetes = listPaquetes; }
}
