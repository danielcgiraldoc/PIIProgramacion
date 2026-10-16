package org.example.viewController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.example.controller.ServicioController;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.ServicioTecnologico;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class ServiciosViewController {

    private final ServicioController servicioController = new ServicioController();

    @FXML
    private ComboBox<String> cmbTipoServicio;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TableView<ServicioTecnologico> tablaServicios;

    @FXML
    private TableColumn<ServicioTecnologico, String> colCodigo;

    @FXML
    private TableColumn<ServicioTecnologico, String> colNombre;

    @FXML
    private TableColumn<ServicioTecnologico, String> colDescripcion;

    @FXML
    private TableColumn<ServicioTecnologico, String> colCostoBase;

    @FXML
    private CheckBox chkAtencionPrioritaria;

    @FXML
    private CheckBox chkSoporteMensual;

    @FXML
    private CheckBox chkAtencionDomicilio;

    @FXML
    private Label lblCostoBase;

    @FXML
    private Label lblIncrementos;

    @FXML
    private Label lblCostoFinal;

    @FXML
    private void initialize() {
        cmbTipoServicio.getItems().setAll("Mantenimiento preventivo", "Mantenimiento correctivo", "Instalación de redes", "Recuperación de información");

        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo()));

        colNombre.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNombre()));

        colDescripcion.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));

        colCostoBase.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(formatearCosto(data.getValue().getCostoBase())));

        cargarServicios();
        actualizarCostos(0, 0);
    }

    @FXML
    private void crearServicio(ActionEvent event) {
        String tipo = obtenerTipoSeleccionado();

        if (tipo == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Tipo requerido", "Selecciona el tipo de servicio que deseas crear.");
            return;
        }
        try {
            servicioController.crearServicio(tipo, txtNombre.getText(), txtCodigo.getText(), txtDescripcion.getText());
            cargarServicios();
            limpiarFormulario();

            mostrarAlerta(Alert.AlertType.INFORMATION, "Servicio creado", "El servicio se agregó al catálogo.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo crear", e.getMessage());
        }
    }

    @FXML
    private void seleccionarServicio(javafx.scene.input.MouseEvent event) {
        ServicioTecnologico seleccionado = tablaServicios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            return;
        }
        chkAtencionPrioritaria.setSelected(false);
        chkSoporteMensual.setSelected(false);
        chkAtencionDomicilio.setSelected(false);

        actualizarCostos(seleccionado.getCostoBase(), seleccionado.getCostoBase());
    }

    @FXML
    private void calcularPersonalizacion(ActionEvent event) {
        ServicioTecnologico seleccionado = tablaServicios.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Servicio no seleccionado", "Selecciona un servicio de la tabla primero.");
            return;
        }
        ElementoContratado personalizado = servicioController.personalizarServicio(seleccionado, chkAtencionPrioritaria.isSelected(), chkSoporteMensual.isSelected(), chkAtencionDomicilio.isSelected());

        double costoBase = seleccionado.getCostoBase();
        double costoFinal = personalizado.calcularCosto();

        actualizarCostos(costoBase, costoFinal);
    }

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org.example/inicio.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    private String obtenerTipoSeleccionado() {
        String tipoSeleccionado = cmbTipoServicio.getValue();

        if (tipoSeleccionado == null) {
            return null;
        }

        return switch (tipoSeleccionado) {
            case "Mantenimiento preventivo" -> "PREVENTIVO";
            case "Mantenimiento correctivo" -> "CORRECTIVO";
            case "Instalación de redes" -> "REDES";
            case "Recuperación de información" -> "RECUPERACION";
            default -> null;
        };
    }

    private void cargarServicios() {
        tablaServicios.getItems().setAll(servicioController.listarServicios());
    }

    private void actualizarCostos(double costoBase, double costoFinal) {
        lblCostoBase.setText(formatearCosto(costoBase));
        lblIncrementos.setText(formatearCosto(costoFinal - costoBase));
        lblCostoFinal.setText(formatearCosto(costoFinal));
    }

    private String formatearCosto(double costo) {
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
        formato.setMaximumFractionDigits(0);
        formato.setMinimumFractionDigits(0);
        return formato.format(costo);
    }

    private void limpiarFormulario() {
        cmbTipoServicio.setValue(null);
        txtCodigo.clear();
        txtNombre.clear();
        txtDescripcion.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}