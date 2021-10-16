package com.grupo2.tienda.modelos;

public class ProductoDTO {
	
	private long codigo;
	private String nombre;
	private long nit;
	private double costo;
	private double iva;
	private double precio;
	
	public ProductoDTO(long codigo, String nombre, long nit, double costo, double iva, double precio) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.nit = nit;
		this.costo = costo;
		this.iva = iva;
		this.precio = precio;
	}
	
	public long getCodigo() {
		return codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public long getNit() {
		return nit;
	}
	public double getCosto() {
		return costo;
	}
	public double getIva() {
		return iva;
	}
	public double getPrecio() {
		return precio;
	}
	
}
