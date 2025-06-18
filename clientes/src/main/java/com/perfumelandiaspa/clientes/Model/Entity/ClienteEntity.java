package com.perfumelandiaspa.clientes.Model.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;



@Table(name = "Cliente")
@Entity
@Data
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    @Column(name="rut_cliente", nullable = false, unique = true)
    private String rut;
    
    @Column(name="nombre", nullable = false)
    private String nombre;

    @Column(name="apellido", nullable = false)
    private String apellido;

    @Column(name="direccion", nullable = false)
    private String direccion;

    @Column(name="telefono", nullable = true)
    private int telefono;

    // relacion de  Uno a muchos
    // @OneToOne
    // @JoinColumn(name = "Usuario_id", nullable =  true)
    // private Long id_usuario;

}