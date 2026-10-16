package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.model.Cliente;
import org.example.model.Empresa;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.model.factory.InstalacionRedesFactory;
import org.example.model.factory.MantenimientoCorrectivoFactory;
import org.example.model.factory.MantenimientoPreventivoFactory;
import org.example.model.factory.RecuperacionInformacionFactory;
import org.example.model.factory.ServicioFactory;
import org.example.services.ClienteService;
import org.example.services.PaqueteService;

import java.util.ArrayList;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        cargarDatosIniciales();

        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/org.example/inicio.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("TechSolutions");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
    }

    private void cargarDatosIniciales() {
        Empresa empresa = Empresa.getInstance();

        if (empresa.getListSolicitudes() == null) {
            empresa.setListSolicitudes(new ArrayList<>());
        }

        cargarClientes(empresa);
        cargarServicios(empresa);
        cargarPaqueteDemostracion(empresa);
    }

    private void cargarClientes(Empresa empresa) {
        if (!empresa.getListClientes().isEmpty()) {
            return;
        }

        ClienteService clienteService = new ClienteService();

        clienteService.registrarCliente(
                "1001",
                "Daniel Torres",
                "daniel.torres@email.com"
        );

        clienteService.registrarCliente(
                "1002",
                "Carlos Méndez",
                "carlos.mendez@email.com"
        );

        clienteService.registrarCliente(
                "1003",
                "Laura Gómez",
                "laura.gomez@email.com"
        );
    }

    private void cargarServicios(Empresa empresa) {
        if (!empresa.getListServicios().isEmpty()) {
            return;
        }

        ServicioFactory fabricaPreventivo =
                new MantenimientoPreventivoFactory();
        ServicioFactory fabricaCorrectivo =
                new MantenimientoCorrectivoFactory();
        ServicioFactory fabricaRedes =
                new InstalacionRedesFactory();
        ServicioFactory fabricaRecuperacion =
                new RecuperacionInformacionFactory();

        ServicioTecnologico preventivo = fabricaPreventivo.crearServicio(
                "Mantenimiento preventivo",
                "SER-001",
                "Revisión y mantenimiento preventivo."
        );

        ServicioTecnologico correctivo = fabricaCorrectivo.crearServicio(
                "Mantenimiento correctivo",
                "SER-002",
                "Diagnóstico y reparación."
        );

        ServicioTecnologico redes = fabricaRedes.crearServicio(
                "Instalación de redes",
                "SER-003",
                "Instalación y configuración de redes."
        );

        ServicioTecnologico recuperacion =
                fabricaRecuperacion.crearServicio(
                        "Recuperación de información",
                        "SER-004",
                        "Recuperación de información en dispositivos."
                );

        empresa.getListServicios().add(preventivo);
        empresa.getListServicios().add(correctivo);
        empresa.getListServicios().add(redes);
        empresa.getListServicios().add(recuperacion);
    }

    private void cargarPaqueteDemostracion(Empresa empresa) {
        if (!empresa.getListPaquetes().isEmpty()) {
            return;
        }

        PaqueteService paqueteService = new PaqueteService();

        ServicioTecnologico preventivo = empresa.getListServicios().get(0);
        ServicioTecnologico correctivo = empresa.getListServicios().get(1);
        ServicioTecnologico redes = empresa.getListServicios().get(2);
        ServicioTecnologico recuperacion = empresa.getListServicios().get(3);

        // preventivo + correctivo
        PaqueteServicio paqueteMantenimiento = paqueteService.crearPaquete("PAQ-MANT-001");
        paqueteService.agregarElemento(paqueteMantenimiento, preventivo);
        paqueteService.agregarElemento(paqueteMantenimiento, correctivo);

        // mantenimiento + redes + recuperación
        PaqueteServicio paqueteEmpresarial = paqueteService.crearPaquete("PAQ-EMP-001");
        paqueteService.agregarElemento(paqueteEmpresarial, paqueteMantenimiento);
        paqueteService.agregarElemento(paqueteEmpresarial, redes);
        paqueteService.agregarElemento(paqueteEmpresarial, recuperacion);
    }

    public static void main(String[] args) {
        launch(args);
    }
}