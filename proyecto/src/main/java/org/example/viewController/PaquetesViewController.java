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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.example.controller.PaqueteController;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;
import org.example.model.decorator.Decorator;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class PaquetesViewController {

    private final PaqueteController paqueteController = new PaqueteController();

    private PaqueteServicio paqueteSeleccionado;

    @FXML
    private TextField txtCodigoPaquete;

    @FXML
    private TableView<PaqueteServicio> tablaPaquetes;

    @FXML
    private TableColumn<PaqueteServicio, String> colCodigoPaquete;

    @FXML
    private TableColumn<PaqueteServicio, String> colCostoPaquete;

    @FXML
    private ComboBox<ElementoDisponible> cmbElementoDisponible;

    @FXML
    private TableView<ElementoFila> tablaElementos;

    @FXML
    private TableColumn<ElementoFila, String> colNombreElemento;

    @FXML
    private TableColumn<ElementoFila, String> colCostoElemento;

    @FXML
    private Label lblCostoTotalPaquete;

    @FXML
    private void initialize() {
        colCodigoPaquete.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));

        colCostoPaquete.setCellValueFactory(data -> new SimpleStringProperty(formatearCosto(paqueteController.calcularCostoTotal(data.getValue()))));

        colNombreElemento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        colCostoElemento.setCellValueFactory(data -> new SimpleStringProperty(formatearCosto(data.getValue().getCosto())));

        cargarPaquetes(null);
        limpiarDetalle();
    }

    @FXML
    private void crearPaquete(ActionEvent event) {
        try {
            PaqueteServicio nuevoPaquete = paqueteController.crearPaquete(txtCodigoPaquete.getText());

            txtCodigoPaquete.clear();
            cargarPaquetes(nuevoPaquete);
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo crear el paquete", e.getMessage());
        }
    }

    @FXML
    private void seleccionarPaquete(MouseEvent event) {
        PaqueteServicio seleccionado = tablaPaquetes.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        paqueteSeleccionado = seleccionado;
        cargarElementosDisponibles();
        cargarComponentesPaquete();
    }

    @FXML
    private void agregarElemento(ActionEvent event) {
        if (paqueteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Paquete no seleccionado", "Selecciona un paquete de la tabla primero.");
            return;
        }

        ElementoDisponible disponible = cmbElementoDisponible.getValue();

        if (disponible == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Componente no seleccionado", "Selecciona un servicio o paquete para agregar.");
            return;
        }

        try {
            paqueteController.agregarElemento(paqueteSeleccionado, disponible.getElemento());

            cargarPaquetes(paqueteSeleccionado);
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo agregar el componente", e.getMessage());
        }
    }

    @FXML
    private void quitarElemento(ActionEvent event) {
        if (paqueteSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Paquete no seleccionado", "Selecciona un paquete de la tabla primero.");
            return;
        }

        ElementoFila filaSeleccionada = tablaElementos.getSelectionModel().getSelectedItem();

        if (filaSeleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Componente no seleccionado", "Selecciona un componente de la tabla para quitarlo.");
            return;
        }

        paqueteController.eliminarElemento(paqueteSeleccionado, filaSeleccionada.getElemento());

        cargarPaquetes(paqueteSeleccionado);
    }

    @FXML
    private void volverInicio(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org.example/inicio.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    private void cargarPaquetes(PaqueteServicio paqueteASeleccionar) {
        tablaPaquetes.getItems().setAll(paqueteController.listarPaquetes());

        if (paqueteASeleccionar == null) {
            paqueteSeleccionado = null;
            tablaPaquetes.getSelectionModel().clearSelection();
            limpiarDetalle();
            return;
        }

        paqueteSeleccionado = paqueteASeleccionar;
        tablaPaquetes.getSelectionModel().select(paqueteASeleccionar);

        cargarElementosDisponibles();
        cargarComponentesPaquete();
    }

    private void cargarElementosDisponibles() {
        cmbElementoDisponible.getItems().clear();

        for (ServicioTecnologico servicio : paqueteController.listarServicios()) {
            cmbElementoDisponible.getItems().add(new ElementoDisponible("Servicio · " + servicio.getCodigo() + " — " + servicio.getNombre(), servicio));
        }

        for (PaqueteServicio paquete : paqueteController.listarPaquetes()) {
            if (paquete != paqueteSeleccionado) {
                cmbElementoDisponible.getItems().add(new ElementoDisponible("Paquete · " + paquete.getCodigo(), paquete));
            }
        }
        cmbElementoDisponible.getSelectionModel().clearSelection();
    }

    private void cargarComponentesPaquete() {
        tablaElementos.getItems().clear();

        if (paqueteSeleccionado == null) {
            lblCostoTotalPaquete.setText(formatearCosto(0));
            return;
        }

        for (ElementoContratado elemento :
                paqueteController.consultarElementos(paqueteSeleccionado)) {
            tablaElementos.getItems().add(new ElementoFila(obtenerNombreElemento(elemento), elemento.calcularCosto(), elemento));
        }

        lblCostoTotalPaquete.setText(formatearCosto(paqueteController.calcularCostoTotal(paqueteSeleccionado)));

        tablaPaquetes.refresh();
    }

    private void limpiarDetalle() {
        tablaElementos.getItems().clear();
        cmbElementoDisponible.getItems().clear();
        lblCostoTotalPaquete.setText(formatearCosto(0));
    }

    private String obtenerNombreElemento(ElementoContratado elemento) {
        ElementoContratado base = elemento;
        boolean personalizado = false;

        while (base instanceof Decorator decorator) {
            personalizado = true;
            base = decorator.getElementoContratado();
        }

        if (base instanceof ServicioTecnologico servicio) {
            String prefijo = personalizado
                    ? "Servicio personalizado · "
                    : "Servicio · ";

            return prefijo + servicio.getCodigo() + " — " + servicio.getNombre();
        }

        if (base instanceof PaqueteServicio paquete) {
            return "Paquete · " + paquete.getCodigo();
        }
        return "Componente";
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

    private static class ElementoDisponible {
        private final String nombre;
        private final ElementoContratado elemento;

        private ElementoDisponible(String nombre, ElementoContratado elemento) {
            this.nombre = nombre;
            this.elemento = elemento;
        }

        private ElementoContratado getElemento() {
            return elemento;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    private static class ElementoFila {
        private final String nombre;
        private final double costo;
        private final ElementoContratado elemento;

        private ElementoFila(String nombre, double costo, ElementoContratado elemento) {
            this.nombre = nombre;
            this.costo = costo;
            this.elemento = elemento;
        }

        private String getNombre() {
            return nombre;
        }

        private double getCosto() {
            return costo;
        }

        private ElementoContratado getElemento() {
            return elemento;
        }
    }
}