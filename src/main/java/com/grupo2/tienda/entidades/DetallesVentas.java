package com.grupo2.tienda.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "detallesVentas")
public class DetallesVentas {
	
	@Id
	@Column(name = "codigo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name = "codigoVenta")
	private String codigoVenta;
	
	@Column(name = "codigoProducto")
	private Long codigoProducto;
	
	@Column(name = "cantidad")
	private Long cantidad;
	
	@Column(name = "valorUnitario")
	private double valorUnitario;
	
	@Column(name = "iva")
	private double iva;
	
	@Column(name = "total")
	private double total;

}
