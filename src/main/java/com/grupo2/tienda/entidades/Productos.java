package com.grupo2.tienda.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Productos {
	
	@Id
	@Column(name = "codigo")
	private Long codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "nit")
	private Long nit;
	
	@Column(name = "costo")
	private double costo;
	
	@Column(name = "iva")
	private double iva;
	
	@Column(name = "precio")
	private double precio;
	
}
