package com.perfumelandiaspa.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    //Test de crear cliente
    @Test
    @DisplayName(" Test para crear Cliente")
    void testCrearCliente()
    {

    // Configuración del mock
    when(clienteRepository.existsByRut("12345678-9")).thenReturn(false);
    when(clienteRepository.save(any(ClienteEntity.class))).thenReturn(clienteEntity);
    
    // Ejecución
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

    // Configuración del mock
    when(clienteRepository.existsByRut("12345678-9")).thenReturn(true);
    
    
    // Ejecución
    String resultado = clienteService.crearCliente(cliente);
    
    // Verificaciones
    assertEquals("Este cliente ya tiene asociado su RUT", resultado);
    verify(clienteRepository).existsByRut("12345678-9");
    verify(clienteRepository, never()).save(any(ClienteEntity.class));

    }

    
    
}
