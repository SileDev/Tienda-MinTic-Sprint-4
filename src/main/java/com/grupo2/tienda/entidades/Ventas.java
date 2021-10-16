package com.grupo2.tienda.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ventas")
public class Ventas {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@Column(name = "codigoVenta")
	private String codigoVenta;

	@Column(name = "cedulaCliente")
	private long cedulaCliente;
	
	@Column(name = "cedulaUsuario")
	private long cedulaUsuario;
	
	@Column(name = "precio")
	private double precio;
	
	@Column(name = "iva")
	private double iva;
	
	@Column(name = "total")
	private double total;

}
