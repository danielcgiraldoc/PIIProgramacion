package org.example.model.external;

public class LegacySupport {

    /**
     * Metodo que consulta un tecnico desde la "app" externa que maneja los tecnicos
     * @param codigoEspecialidad
     * @return
     */
    public String consultarTecnico(int codigoEspecialidad) {
        switch (codigoEspecialidad) {
            case 1:
                return "TEC-105|DISPONIBLE|30000";
            case 2:
                return "TEC-210|DISPONIBLE|20000";
            case 3:
                return "TEC-318|DISPONIBLE|25000";
            default:
                return "SIN_TECNICO|NO_DISPONIBLE|0";
        }
    }
}