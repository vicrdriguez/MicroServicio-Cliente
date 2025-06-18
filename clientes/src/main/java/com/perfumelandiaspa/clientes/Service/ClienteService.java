package com.perfumelandiaspa.clientes.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perfumelandiaspa.clientes.Model.Cliente;
import com.perfumelandiaspa.clientes.Model.Entity.ClienteEntity;
import com.perfumelandiaspa.clientes.Repository.ClienteRepository;




@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;


    //Metodo para Crear Clientes en la base de datos
    public String crearCliente(Cliente cliente) {
        try {
            if (clienteRepository.existsByRut(cliente.getRut())) {
                return "Este cliente ya tiene asociado su RUT";
            }

            ClienteEntity clienteNuevo = new ClienteEntity();
            clienteNuevo.setRut(cliente.getRut());
            clienteNuevo.setNombre(cliente.getNombre());
            clienteNuevo.setApellido(cliente.getApellido());
            clienteNuevo.setDireccion(cliente.getDireccion());
            clienteNuevo.setTelefono(cliente.getTelefono());

            
            clienteRepository.save(clienteNuevo);
            return "Cliente creado con éxito";
        } catch (Exception e) {
            return "Error al crear cliente: " + e.getMessage();
        }
    }

    //Metodo para listar todos los clientes
    public List<ClienteEntity> findAll() {
        return clienteRepository.findAll();
    }


    //Metodo para Eliminar cliente por id
    public String eliminarPorId(int idCliente) {

        //En caso de error
        try {
            if (!clienteRepository.existsById(idCliente)) {
                return "No existe un cliente con el Id proporcionado";
            }
            
            clienteRepository.deleteById(idCliente);
            return "Cliente eliminado correctamente";
        } catch (Exception e) {
            return "Error al eliminar cliente: " + e.getMessage();
        }
    }


    // Método para actualizar cliente por ID sin verificar RUT
    public String actualizarCliente(int idCliente, Cliente cliente) {
    try {
        // Buscar el cliente existente
        ClienteEntity clienteExistente = clienteRepository.findById(idCliente)
            .orElseThrow(() -> new RuntimeException("No existe un cliente con el ID proporcionado"));
        
        // Actualizar todos los campos directamente
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setTelefono(cliente.getTelefono());
        
        // Guardar los cambios
        clienteRepository.save(clienteExistente);
        
        return "Cliente actualizado con éxito";
    } catch (RuntimeException e) {
        return e.getMessage();
    } catch (Exception e) {
        return "Error al actualizar cliente: " + e.getMessage();
    }
}


    public ClienteEntity buscarClienteID(int idCliente){
        return clienteRepository.findById(idCliente).get();
    }

}