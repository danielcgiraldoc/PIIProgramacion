package org.example.model.factory;
import org.example.model.MantenimientoCorrectivo;
import org.example.model.ServicioTecnologico;


public class MantenimientoCorrectivoFactory extends ServicioFactory {

    /**
     * Metodo para crear la clase MantenimientoCorrectivo
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
    @Override
    public ServicioTecnologico crearServicio(String nombre, String codigo, String descripcion) {
        return new MantenimientoCorrectivo(nombre, codigo, descripcion);
    }
}
