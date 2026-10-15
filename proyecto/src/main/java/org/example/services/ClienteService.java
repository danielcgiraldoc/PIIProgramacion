package org.example.services;

import org.example.model.Cliente;
import org.example.model.Empresa;

import java.util.List;

public class ClienteService {

    private final Empresa empresa = Empresa.getInstance();

    /**
     * Metodo para registrar el cliente
     * @param documento
     * @param nombreCompleto
     * @param correo
     * @return
     */
    public Cliente registrarCliente(String documento, String nombreCompleto, String correo) {
        if (documento == null || documento.trim().isEmpty()
                || nombreCompleto == null || nombreCompleto.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos del cliente son obligatorios");
        }
        if (buscarPorDocumento(documento) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese documento");
        }
        Cliente cliente = new Cliente.Builder(documento, nombreCompleto, correo).build();
        empresa.getListClientes().add(cliente);
        return cliente;
    }

    /**
     * Metodo que permite bucar un cliente por su documento
     * @param documento
     * @return
     */
    public Cliente buscarPorDocumento(String documento) {
        for (Cliente cliente : empresa.getListClientes()) {
            if (cliente.getDocumento().equals(documento)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Metodo para listar los clientes registrados en la empresa
     * @return
     */
    public List<Cliente> listarClientes() {
        return empresa.getListClientes();
    }
}