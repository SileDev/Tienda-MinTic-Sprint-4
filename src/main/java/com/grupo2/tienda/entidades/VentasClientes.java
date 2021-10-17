package com.grupo2.tienda.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ventas_clientes")
public class VentasClientes {
	
	@Id
	@Column(name = "cedulaCliente")
	private Long cedulaCliente;
	
	@Column(name = "ventas")
	private Long ventas;
	
	@Column(name = "totalVentas")
	private double totalVentas;

}
