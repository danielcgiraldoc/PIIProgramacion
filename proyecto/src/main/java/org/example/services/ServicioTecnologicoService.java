package org.example.services;

import org.example.model.Empresa;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.ServicioTecnologico;
import org.example.model.decorator.AtencionDomicilio;
import org.example.model.decorator.AtencionPrioritaria;
import org.example.model.decorator.SoporteMensual;
import org.example.model.factory.InstalacionRedesFactory;
import org.example.model.factory.MantenimientoCorrectivoFactory;
import org.example.model.factory.MantenimientoPreventivoFactory;
import org.example.model.factory.RecuperacionInformacionFactory;
import org.example.model.factory.ServicioFactory;

import java.util.List;

public class ServicioTecnologicoService {

    private final Empresa empresa = Empresa.getInstance();

    /**
     * Metodo que permite resgistrar el servicio utilizando un factory dependiendo del tipo de servicio requerido
     * @param tipo
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
    public ServicioTecnologico crearServicio(String tipo, String nombre, String codigo, String descripcion) {
        if (nombre == null || nombre.trim().isEmpty()
                || codigo == null || codigo.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos del servicio son obligatorios");
        }
        if (buscarPorCodigo(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un servicio con ese código");
        }

        ServicioFactory fabrica;
        switch (tipo) {
            case "PREVENTIVO":
                fabrica = new MantenimientoPreventivoFactory();
                break;
            case "CORRECTIVO":
                fabrica = new MantenimientoCorrectivoFactory();
                break;
            case "REDES":
                fabrica = new InstalacionRedesFactory();
                break;
            case "RECUPERACION":
                fabrica = new RecuperacionInformacionFactory();
                break;
            default:
                throw new IllegalArgumentException("Tipo de servicio no válido: " + tipo);
        }

        ServicioTecnologico servicio = fabrica.crearServicio(nombre, codigo, descripcion);
        empresa.getListServicios().add(servicio);
        return servicio;
    }

    /**
     * Metodo que permite listar los servicios de la empresa
     * @return
     */
    public List<ServicioTecnologico> listarServicios() {
        return empresa.getListServicios();
    }

    /**
     * Metodo que busca un servicio tecnologico por el codigo
     * @param codigo
     * @return
     */
    public ServicioTecnologico buscarPorCodigo(String codigo) {
        for (ServicioTecnologico servicio : empresa.getListServicios()) {
            if (servicio.getCodigo().equals(codigo)) {
                return servicio;
            }
        }
        return null;
    }

    /**
     * Metodo que permite personalizar o modificar un servicio
     * @param servicio
     * @param prioritaria
     * @param soporteMensual
     * @param domicilio
     * @return
     */
    public ElementoContratado personalizarServicio(ServicioTecnologico servicio, boolean prioritaria, boolean soporteMensual, boolean domicilio) {
        ElementoContratado resultado = servicio;
        if (prioritaria) {
            resultado = new AtencionPrioritaria(resultado);
        }
        if (soporteMensual) {
            resultado = new SoporteMensual(resultado);
        }
        if (domicilio) {
            resultado = new AtencionDomicilio(resultado);
        }
        return resultado;
    }
}