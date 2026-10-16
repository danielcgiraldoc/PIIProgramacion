package org.example.viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.example.controller.TecnicoController;
import org.example.model.Solicitud;
import org.example.model.Tecnico;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class TecnicosViewController {

    private final TecnicoController tecnicosController = new TecnicoController();

    private Solicitud solicitudSeleccionada;

    @FXML
    private ComboBox<String> cmbEspecialidad;

    @FXML
    private Label lblResultadoTecnico;

    @FXML
    private Label lblDocumentoTecnico;

    @FXML
    private Label lblDisponibilidadTecnico;

    @FXML
    private Label lblCostoDesplazamiento;

    @FXML
    private TableView<Solicitud> tablaSolicitudes;

    @FXML
    private TableColumn<Solicitud, String> colCodigoSolicitud;

    @FXML
    private TableColumn<Solicitud, String> colCliente;

    @FXML
    private TableColumn<Solicitud, String> colFechaCreacion;

    @FXML
    private TableColumn<Solicitud, String> colAtencionPresencial;

    @FXML
    private TableColumn<Solicitud, String> colCostoTotal;

    @FXML
    private Label lblCostoSolicitudSeleccionada;

    @FXML
    private void initialize() {
        cmbEspecialidad.getItems().setAll("REDES", "HARDWARE", "RECUPERACION", "SEGURIDAD");

        colCodigoSolicitud.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));

        colCliente.setCellValueFactory(data -> {
            Solicitud solicitud = data.getValue();

            String nombreCliente = solicitud.getTheCliente() == null
                            ? "Sin cliente"
                            : solicitud.getTheCliente().getNombre();

            return new SimpleStringProperty(nombreCliente);
        });

        colFechaCreacion.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getFechaCreacion())));

        colAtencionPresencial.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isAtencionPresencial()
                                ? "Sí"
                                : "No"));

        colCostoTotal.setCellValueFactory(data -> new SimpleStringProperty(formatearCosto(data.getValue().getCostoTotal())));

        cargarSolicitudes(null);
        limpiarResultadoTecnico();
    }

    @FXML
    private void consultarTecnico(ActionEvent event) {
        String especialidad = cmbEspecialidad.getValue();

        if (especialidad == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Especialidad requerida", "Selecciona una especialidad para consultar.");
            return;
        }

        try {
            Tecnico tecnico = tecnicosController.consultarTecnico(especialidad);

            lblDocumentoTecnico.setText(tecnico.getDocumento());
            lblDisponibilidadTecnico.setText(tecnico.isDisponible()
                            ? "Disponible"
                            : "No disponible");

            lblCostoDesplazamiento.setText(formatearCosto(tecnico.getCostoDesplazamiento()));

            lblResultadoTecnico.setText(tecnico.isDisponible()
                            ? "Se encontró un técnico para esta especialidad."
                            : "No hay un técnico disponible para esta especialidad.");
        } catch (IllegalArgumentException e) {
            limpiarResultadoTecnico();
            lblResultadoTecnico.setText(e.getMessage());

            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo consultar", e.getMessage());
        }
    }

    @FXML
    private void actualizarSolicitudes(ActionEvent event) {
        cargarSolicitudes(solicitudSeleccionada);
    }

    @FXML
    private void seleccionarSolicitud(MouseEvent event) {
        solicitudSeleccionada = tablaSolicitudes.getSelectionModel().getSelectedItem();

        if (solicitudSeleccionada != null) {
            lblCostoSolicitudSeleccionada.setText(formatearCosto(solicitudSeleccionada.getCostoTotal()));
        }
    }

    @FXML
    private void recalcularCosto(ActionEvent event) {
        solicitudSeleccionada = tablaSolicitudes.getSelectionModel().getSelectedItem();

        if (solicitudSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Solicitud no seleccionada", "Selecciona una solicitud de la tabla.");
            return;
        }
        try {
            double costo = tecnicosController.recalcularCosto(solicitudSeleccionada);

            tablaSolicitudes.refresh();
            lblCostoSolicitudSeleccionada.setText(formatearCosto(costo));
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo recalcular", e.getMessage()
            );
        }
    }

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org.example/inicio.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    private void cargarSolicitudes(Solicitud solicitudASeleccionar) {
        tablaSolicitudes.getItems().setAll(tecnicosController.listarSolicitudes());

        if (solicitudASeleccionar != null) {
            tablaSolicitudes.getSelectionModel().select(solicitudASeleccionar);

            lblCostoSolicitudSeleccionada.setText(formatearCosto(solicitudASeleccionar.getCostoTotal()));
        } else {
            solicitudSeleccionada = null;
            lblCostoSolicitudSeleccionada.setText(formatearCosto(0));
        }
    }

    private void limpiarResultadoTecnico() {
        lblDocumentoTecnico.setText("—");
        lblDisponibilidadTecnico.setText("—");
        lblCostoDesplazamiento.setText(formatearCosto(0));
        lblResultadoTecnico.setText("Selecciona una especialidad para consultar.");
    }

    private String formatearCosto(double costo) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        formato.setMaximumFractionDigits(0);
        formato.setMinimumFractionDigits(0);
        return formato.format(costo);
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}