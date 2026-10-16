package org.example.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioViewController {

    @FXML
    private void abrirServicios(ActionEvent event) throws IOException {
        abrirVista(event, "servicios.fxml");
    }

    @FXML
    private void abrirPaquetes(ActionEvent event) throws IOException {
        abrirVista(event, "paquetes.fxml");
    }

    @FXML
    private void abrirSolicitudes(ActionEvent event) throws IOException {
        abrirVista(event, "solicitudes.fxml");
    }

    @FXML
    private void abrirTecnicos(ActionEvent event) throws IOException {
        abrirVista(event, "tecnicos.fxml");
    }

    private void abrirVista(ActionEvent event, String archivo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org.example/" + archivo));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}