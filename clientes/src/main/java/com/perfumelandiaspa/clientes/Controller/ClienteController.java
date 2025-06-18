package com.perfumelandiaspa.clientes.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.perfumelandiaspa.clientes.Model.Cliente;
import com.perfumelandiaspa.clientes.Model.Entity.ClienteEntity;
import com.perfumelandiaspa.clientes.Service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;










@RequestMapping("/api/v1/clientes")
@RestController
@Tag(name = "Cliente",description = "Operaciones relacionadas con el cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


//Documentacion con Sweeger De crear cliente
    @Operation(
        summary = "Crear un nuevo cliente",
        description = "Este endPoint crea un nuevo cliente si  su rut no esta registrado en el sistema",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Cliente creado exitosamente",
                content = @Content(schema = @Schema(implementation = String.class)) //Devuelve un cuerpo tipo Json
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            )
        }   
    )

    @PostMapping("/CrearCliente")
    public ResponseEntity<String> CrearCliente(@RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }
    

//Documentacion con Sweeger de Listar clientes
    @Operation(
        summary = "Listar Clientes",
        description = "Este endPoint lista todo los clientes que se encuentran en la base de datos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Clientes listados exitosamente",
                content = @Content(schema = @Schema(implementation = ClienteEntity.class))
            ),

            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()
            ),

            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),

            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            )

        }
    )

    @GetMapping("/listarClientes")
    public ResponseEntity<List<ClienteEntity>> listarClientes() {
        List<ClienteEntity> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }





//Documentacion con Swagger Eliminar cliente por id

    @Operation(
        summary = "Eliminar clientes por Id",
        description = "Eliminar clientes que se encuentren en la base de datos por Id",
        responses = {

            @ApiResponse(
                responseCode = "200",
                description = "Cliente Buscado y eliminado Correctamente",
                content = @Content(schema = @Schema(implementation = String.class))
            ),

            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()
            ),


            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),

            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            )


        }
    )

    @DeleteMapping("/eliminarPorId/{idCliente}")
    public ResponseEntity<String> eliminarPorId(@PathVariable int idCliente) {
    try {
        String resultado = clienteService.eliminarPorId(idCliente);
        if (resultado.equals("No existe un cliente con el ID proporcionado")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error del servidor: " + e.getMessage());
    }
}




//Documentacion con Swagger Actualizar cliente por id
    @Operation(
        summary = "Actualizar clientes por Id",
        description = "Actualizar clientes que se encuentren en la base de datos por Id",
        responses = {

            @ApiResponse(
                responseCode = "200",
                description = "Cliente Buscado y Actualizado Correctamente",
                content = @Content(schema = @Schema(implementation = String.class))
            ),

            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()
            ),


            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),

            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            )


        }
    )

    @PutMapping("/actualizarCliente/{idCliente}")
    public ResponseEntity<String> actualizarCliente(@PathVariable int idCliente, @RequestBody Cliente cliente) {
    try {
        String resultado = clienteService.actualizarCliente(idCliente, cliente);
        if (resultado.equals("No existe un cliente con el ID proporcionado")) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body("Error al actualizar cliente: " + e.getMessage());
    }

}




//Documentacion con Swagger Buscar cliente por id
    @Operation(
        summary = "buscar clientes por Id",
        description = "buscar clientes que se encuentren en la base de datos por Id",
        responses = {

            @ApiResponse(
                responseCode = "200",
                description = "Cliente Buscado Correctamente",
                content = @Content(schema = @Schema(implementation = ClienteEntity.class))
            ),

            @ApiResponse(
                responseCode = "400",
                description = "Solicitud invalida",
                content = @Content()
            ),


            @ApiResponse(
                responseCode = "404",
                description = "Recurso no fue encontrado",
                content = @Content()
            ),

            @ApiResponse(
                responseCode = "500",
                description = "Error interno en el servidor",
                content = @Content() //No hay cuerpo en la respuesta por eso esta vacio
            )


        }
    )


    @GetMapping("/clientesPorID/{idCliente}")
    public ResponseEntity<ClienteEntity> buscarClienteID(@PathVariable Integer idCliente) {
        try {
            ClienteEntity cliente = clienteService.buscarClienteID(idCliente);
            return ResponseEntity.ok(cliente);  
        } catch (Exception e) {
            return ResponseEntity.notFound().build();  
        }
    }

    
}

