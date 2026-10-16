package services;

import org.example.model.Empresa;

public class EmpresaTestUtil {

    public static void reiniciar() {
        Empresa empresa = Empresa.getInstance();
        empresa.getListClientes().clear();
        empresa.getListServicios().clear();
        empresa.getListPaquetes().clear();
        empresa.getListSolicitudes().clear();
    }
}