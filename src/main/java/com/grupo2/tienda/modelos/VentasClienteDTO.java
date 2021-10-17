package com.grupo2.tienda.modelos;

public class VentasClienteDTO {
	
	private long cedulaCliente;
	private long ventas;
	private double totalVentas;
	
	public VentasClienteDTO(long cedulaCliente, long ventas, double totalVentas) {
		super();
		this.cedulaCliente = cedulaCliente;
		this.ventas = ventas;
		this.totalVentas = totalVentas;
	}

	public long getCedulaCliente() {
		return cedulaCliente;
	}

	public long getVentas() {
		return ventas;
	}

	public double getTotalVentas() {
		return totalVentas;
	}
	
}
