package com.perfumelandiaspa.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
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



    @Test
    void testEliminarClienteExistente() {
       
        int idCliente = 1;
        when(clienteRepository.existsById(idCliente)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(idCliente);

        
        String resultado = clienteService.eliminarPorId(idCliente);

        
        assertEquals("Cliente eliminado correctamente", resultado);
        verify(clienteRepository).existsById(idCliente);
        verify(clienteRepository).deleteById(idCliente);
    }

   
    
     @Test
    void testActualizarClienteExistente() {
    
        int idCliente = 1;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteEntity));
        when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);

        
        String resultado = clienteService.actualizarCliente(idCliente, cliente);

        
        assertEquals("Cliente actualizado con éxito", resultado);
        verify(clienteRepository).findById(idCliente);
        verify(clienteRepository).save(clienteEntity);
    }

    


    @Test
    void testBuscarClientePorID() {
        
        int idCliente = 1;
        when(clienteRepository.findById(idCliente)).thenReturn(Optional.of(clienteEntity));

        
        ClienteEntity resultado = clienteService.buscarClienteID(idCliente);

        
        assertNotNull(resultado);
        assertEquals(idCliente, resultado.getIdCliente());
        verify(clienteRepository).findById(idCliente);
    }

    @Test
    void testBuscarTodoslosClientes() {
       
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEntity));

        
        List<ClienteEntity> resultado = clienteService.findAll();

       
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(clienteRepository).findAll();
    }
}

