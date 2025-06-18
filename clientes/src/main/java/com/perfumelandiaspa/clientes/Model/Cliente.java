package com.perfumelandiaspa.clientes.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    private int idCliente;

    private String rut;

    private String nombre;

    private String apellido;

    private String direccion;

    private int telefono;



    
    


}
