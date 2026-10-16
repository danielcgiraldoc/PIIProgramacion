package services;

import org.example.model.Cliente;
import org.example.services.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private ClienteService clienteService;

    /**
     * Metodo para inicializar la empresa
     */
    @BeforeEach
    void setUp() {
        EmpresaTestUtil.reiniciar();
        clienteService = new ClienteService();
    }

    /**
     * Metodo que registra el cliente de la prueba
     */
    @Test
    void registrarCliente() {
        Cliente cliente = clienteService.registrarCliente("1094", "Daniel Giraldo", "daniel@correo.com");

        assertNotNull(cliente);
        assertEquals("1094", cliente.getDocumento());
        assertEquals(1, clienteService.listarClientes().size());
    }

    /**
     * Metodo que resgistra un cliente con documento repetido
     */
    @Test
    void registrarClienteConDocumentoRepetido() {
        clienteService.registrarCliente("1094", "Daniel Giraldo", "daniel@correo.com");

        assertThrows(IllegalArgumentException.class,
                () -> clienteService.registrarCliente("1094", "Otro", "otro@correo.com"));
    }

    /**
     * Metodo que registra un cliente sin datos
     */
    @Test
    void registrarClienteConDatosVacios() {
        assertThrows(IllegalArgumentException.class,
                () -> clienteService.registrarCliente("", "Daniel Giraldo", "daniel@correo.com"));
    }

    /**
     * Metodo que busca un cliente por su documento
     */
    @Test
    void buscarPorDocumento() {
        Cliente cliente = clienteService.registrarCliente("1094", "Daniel Giraldo", "daniel@correo.com");

        assertEquals(cliente, clienteService.buscarPorDocumento("1094"));
    }

    /**
     * Metodo que busca un cliente inexistente
     */
    @Test
    void buscarPorDocumentoInexistente() {
        assertNull(clienteService.buscarPorDocumento("0000"));
    }
}