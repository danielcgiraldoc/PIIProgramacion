package org.example.viewController;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import org.example.controller.SolicitudController;
import org.example.model.Cliente;
import org.example.model.Solicitud;
import org.example.model.Tecnico;
import org.example.model.composite.ElementoContratado;
import org.example.model.composite.PaqueteServicio;
import org.example.model.composite.ServicioTecnologico;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SolicitudesViewController {

    private final SolicitudController solicitudController = new SolicitudController();

    private final List<ElementoContratado> elementosSeleccionados = new ArrayList<>();

    private Tecnico tecnicoConsultado;

    @FXML
    private ComboBox<Cliente> cmbCliente;

    @FXML
    private DatePicker dpFechaProgramada;

    @FXML
    private TextArea txtObservaciones;

    @FXML
    private CheckBox chkPrioridad;

    @FXML
    private CheckBox chkAtencionPresencial;

    @FXML
    private ComboBox<String> cmbEspecialidad;

    @FXML
    private Label lblEstadoTecnico;

    @FXML
    private Label lblCostoDesplazamiento;

    @FXML
    private ComboBox<ElementoDisponible> cmbElementoDisponible;

    @FXML
    private TableView<ElementoFila> tablaElementos;

    @FXML
    private TableColumn<ElementoFila, String> colNombreElemento;

    @FXML
    private TableColumn<ElementoFila, String> colCostoElemento;

    @FXML
    private Label lblCodigoSolicitud;

    @FXML
    private Label lblCostoTotal;

    @FXML
    private void initialize() {
        cmbCliente.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                if (cliente == null) {
                    return "";
                }
                return cliente.getNombre() + " — " + cliente.getDocumento();
            }

            @Override
            public Cliente fromString(String texto) {
                return null;
            }
        });
        cmbCliente.getItems().setAll(solicitudController.listarClientes());
        cmbEspecialidad.getItems().setAll("REDES", "HARDWARE", "RECUPERACION", "SEGURIDAD");
        cmbEspecialidad.setDisable(true);

        colNombreElemento.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));

        colCostoElemento.setCellValueFactory(data -> new SimpleStringProperty(formatearCosto(data.getValue().getCosto())));

        cargarElementosDisponibles();
        actualizarCostoTotal();
        actualizarEstadoTecnico();
    }

    @FXML
    private void cambiarAtencionPresencial(ActionEvent event) {
        boolean presencial = chkAtencionPresencial.isSelected();

        cmbEspecialidad.setDisable(!presencial);

        if (!presencial) {
            tecnicoConsultado = null;
            actualizarEstadoTecnico();
            actualizarCostoTotal();
        }
    }

    @FXML
    private void consultarTecnico(ActionEvent event) {
        if (!chkAtencionPresencial.isSelected()) {
            mostrarAlerta(Alert.AlertType.INFORMATION, "Atención presencial", "Marca atención presencial para consultar un técnico.");
            return;
        }

        String especialidad = cmbEspecialidad.getValue();

        if (especialidad == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Especialidad requerida", "Selecciona una especialidad técnica.");
            return;
        }

        try {
            tecnicoConsultado = solicitudController.consultarTecnico(especialidad);

            actualizarEstadoTecnico();
            actualizarCostoTotal();
        } catch (IllegalArgumentException e) {
            tecnicoConsultado = null;
            actualizarEstadoTecnico();

            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo consultar el técnico", e.getMessage());
        }
    }

    @FXML
    private void agregarElemento(ActionEvent event) {
        ElementoDisponible seleccionado = cmbElementoDisponible.getValue();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Elemento no seleccionado", "Selecciona un servicio o paquete para agregar.");
            return;
        }

        elementosSeleccionados.add(seleccionado.getElemento());

        tablaElementos.getItems().add(new ElementoFila(seleccionado.getNombre(), seleccionado.getElemento().calcularCosto(), seleccionado.getElemento()));

        actualizarCostoTotal();
    }

    @FXML
    private void quitarElemento(ActionEvent event) {
        ElementoFila seleccionado = tablaElementos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Elemento no seleccionado", "Selecciona un elemento de la tabla para quitarlo.");
            return;
        }

        elementosSeleccionados.remove(seleccionado.getElemento());
        tablaElementos.getItems().remove(seleccionado);

        actualizarCostoTotal();
    }

    @FXML
    private void registrarSolicitud(ActionEvent event) {
        try {
            Solicitud solicitud = solicitudController.registrarSolicitud(cmbCliente.getValue(), elementosSeleccionados, chkAtencionPresencial.isSelected(), tecnicoConsultado);

            lblCodigoSolicitud.setText(solicitud.getCodigo());
            lblCostoTotal.setText(formatearCosto(solicitud.getCostoTotal()));

            mostrarAlerta(Alert.AlertType.INFORMATION, "Solicitud registrada", "Se registró la solicitud " + solicitud.getCodigo() + ".");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(Alert.AlertType.WARNING, "No se pudo registrar", e.getMessage());
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

    private void cargarElementosDisponibles() {
        cmbElementoDisponible.getItems().clear();

        for (ElementoContratado elemento : solicitudController.listarElementosDisponibles()) {
            cmbElementoDisponible.getItems().add(new ElementoDisponible(obtenerNombreElemento(elemento), elemento));
        }
    }

    private void actualizarEstadoTecnico() {
        if (tecnicoConsultado == null) {
            lblEstadoTecnico.setText("Técnico: sin consultar");
            lblCostoDesplazamiento.setText("Desplazamiento: 0 COP");
            return;
        }
        if (tecnicoConsultado.isDisponible()) {
            lblEstadoTecnico.setText("Técnico disponible: " + tecnicoConsultado.getDocumento());

            lblCostoDesplazamiento.setText("Desplazamiento: " + formatearCosto(tecnicoConsultado.getCostoDesplazamiento()));
        } else {
            lblEstadoTecnico.setText("No hay técnico disponible para esta especialidad.");
            lblCostoDesplazamiento.setText("Desplazamiento: 0 COP");
        }
    }

    private void actualizarCostoTotal() {
        double total = 0;

        for (ElementoContratado elemento : elementosSeleccionados) {
            total += elemento.calcularCosto();
        }

        if (chkAtencionPresencial.isSelected() && tecnicoConsultado != null && tecnicoConsultado.isDisponible()) {total += tecnicoConsultado.getCostoDesplazamiento();
        }
        lblCostoTotal.setText(formatearCosto(total));
    }

    private String obtenerNombreElemento(ElementoContratado elemento) {
        if (elemento instanceof ServicioTecnologico servicio) {
            return "Servicio · " + servicio.getCodigo() + " — " + servicio.getNombre();
        }

        if (elemento instanceof PaqueteServicio paquete) {
            return "Paquete · " + paquete.getCodigo();
        }

        return "Elemento contratado";
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

        private String getNombre() {
            return nombre;
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