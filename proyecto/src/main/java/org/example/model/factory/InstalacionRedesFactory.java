package org.example.model.factory;
import org.example.model.InstalacionRedes;
import org.example.model.ServicioTecnologico;

public class InstalacionRedesFactory extends ServicioFactory {


    /**
     * Metodo para crear la clase InstalacionRedes
     * @param nombre
     * @param codigo
     * @param descripcion
     * @return
     */
        @Override
        public ServicioTecnologico crearServicio(String nombre, String codigo, String descripcion) {
            return new InstalacionRedes(nombre, codigo, descripcion);
        }
    }
