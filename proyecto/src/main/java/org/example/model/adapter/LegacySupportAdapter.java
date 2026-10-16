package org.example.model.adapter;

import org.example.model.external.LegacySupport;
import org.example.model.Tecnico;

public class LegacySupportAdapter implements ProveedorTecnicos {

    private final LegacySupport legacySupport;

    /**
     * Metodo constructor del adapter
     * @param legacySupport
     */
    public LegacySupportAdapter(LegacySupport legacySupport) {
        this.legacySupport = legacySupport;
    }


    /**
     * Metodo para consultar y retornar un tecnico desde el agente externo al interno, covirtiendo
     * la manera en que se mueestran sus datos
     * @param especialidad
     * @return
     */
    @Override
    public Tecnico consultarTecnico(String especialidad) {
        int codigo = obtenerCodigo(especialidad);
        String respuesta = legacySupport.consultarTecnico(codigo);

        String[] partes = respuesta.split("\\|");
        String id = partes[0];
        boolean disponible = partes[1].equals("DISPONIBLE");
        double costo = Double.parseDouble(partes[2]);

        return new Tecnico(id, disponible, costo);
    }

    /**
     * Metodo para obtener el codigo de un tecnico segun el servicio en que se escializa
     * @param especialidad
     * @return
     */
    private int obtenerCodigo(String especialidad) {
        switch (especialidad.trim().toUpperCase()) {
            case "REDES":
                return 1;
            case "HARDWARE":
                return 2;
            case "RECUPERACION":
                return 3;
            case "SEGURIDAD":
                return 4;
            default:
                throw new IllegalArgumentException("Especialidad no válida: " + especialidad);
        }
    }
}