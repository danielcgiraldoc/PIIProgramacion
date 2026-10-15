package org.example.model.factory;
import org.example.model.MantenimientoPreventivo;
import org.example.model.ServicioTecnologico;

public class MantenimientoPreventivoFactory extends ServicioFactory {

    /**
     * Metodo para crear la clase MantenimientoPreventivo
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String nombre, String codigo, String descripcion) {
        return new MantenimientoPreventivo(nombre, codigo, descripcion);
    }
}