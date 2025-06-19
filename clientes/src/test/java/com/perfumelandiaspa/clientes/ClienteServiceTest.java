package com.perfumelandiaspa.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.perfumelandiaspa.clientes.Model.Cliente;
import com.perfumelandiaspa.clientes.Model.Entity.ClienteEntity;
import com.perfumelandiaspa.clientes.Repository.ClienteRepository;
import com.perfumelandiaspa.clientes.Service.ClienteService;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {



    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;
    private ClienteEntity clienteEntity;


    @BeforeEach
    void setUp() {

        cliente = new Cliente();
        cliente.setIdCliente(1);
        cliente.setRut("12345678-9");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono(912345678);


        clienteEntity = new ClienteEntity();
        clienteEntity.setIdCliente(1);
        clienteEntity.setRut("12345678-9");
        clienteEntity.setNombre("Juan");
        clienteEntity.setApellido("Pérez");
        clienteEntity.setDireccion("Calle Falsa 123");
        clienteEntity.setTelefono(912345678);
    }

    


    //Test para crear clientes
    @Test
    @DisplayName(" Test para crear Cliente")
    void testCrearCliente()
    {

    
    when(clienteRepository.existsByRut("12345678-9")).thenReturn(false);
    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);
    
   
    String resultado = clienteService.crearCliente(cliente);
    
    // Verificaciones
    assertEquals("Cliente creado con éxito", resultado);
    verify(clienteRepository).existsByRut("12345678-9");
    verify(clienteRepository).save(any(ClienteEntity.class));

    }



    //Test de crear cliente pero en caso de que ya exista manda error de mensaje
    @Test
    @DisplayName(" Test de crear cliente en caso de ya haber uno existente")
    void testClienteExistentePorRut()
    {

    
    when(clienteRepository.existsByRut("12345678-9")).thenReturn(true);
    
    
    
    String resultado = clienteService.crearCliente(cliente);
    
    // Verificaciones
    assertEquals("Este cliente ya tiene asociado su RUT", resultado);
    verify(clienteRepository).existsByRut("12345678-9");
    verify(clienteRepository, never()).save(any(ClienteEntity.class));

    }

    @Test
    void testEliminarClienteExistente() {
        // Arrange
        int idCliente = 1;
        when(clienteRepository.existsById(idCliente)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(idCliente);

        // Act
        String resultado = clienteService.eliminarPorId(idCliente);

        // Assert
        assertEquals("Cliente eliminado correctamente", resultado);
        verify(clienteRepository).existsById(idCliente);
        verify(clienteRepository).deleteById(idCliente);
    }

    @Test
    void testEliminarClienteMensajeError() {
        // Arrange
        int idCliente = 1;
        when(clienteRepository.existsById(idCliente)).thenReturn(false);

        // Act
        String resultado = clienteService.eliminarPorId(idCliente);

        // Assert
        assertEquals("No existe un cliente con el Id proporcionado", resultado);
        verify(clienteRepository).existsById(idCliente);
        verify(clienteRepository, never()).deleteById(idCliente);
    }

    
     @Test
    void testActualizarClienteExistente() {
        // Arrange
        int idCliente = 1;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        // Act
        String resultado = clienteService.actualizarCliente(idCliente, cliente);

        // Assert
        assertEquals("Cliente actualizado con éxito", resultado);
        verify(clienteRepository).findById(idCliente);
        verify(clienteRepository).save(clienteEntity);
    }

    @Test
    void testActualizarClienteNoExistente() {
        // Arrange
        int idCliente = 1;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.empty());

        // Act
        String resultado = clienteService.actualizarCliente(idCliente, cliente);

        // Assert
        assertEquals("No existe un cliente con el ID proporcionado", resultado);
        verify(clienteRepository).findById(idCliente);
        verify(clienteRepository, never()).save(any(ClienteEntity.class));
    }


    @Test
    void testBuscarClientePorID() {
        // Arrange
        int idCliente = 1;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteEntity));

        // Act
        ClienteEntity resultado = clienteService.buscarClienteID(idCliente);

        // Assert
        assertNotNull(resultado);
        assertEquals(idCliente, resultado.getIdCliente());
        verify(clienteRepository).findById(idCliente);
    }

    @Test
    void testBuscarTodoslosClientes() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEntity));

        // Act
        List<ClienteEntity> resultado = clienteService.findAll();

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(clienteRepository).findAll();
    }
}

