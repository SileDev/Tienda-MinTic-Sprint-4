package com.grupo2.tienda.modelos;

public class VentaDTO {

	private String codigoVenta;
	private long cedulaCliente;
	private long cedulaUsuario;
	private double precio;
	private double iva;
	private double total;

	public VentaDTO(String codigoVenta, long cedulaCliente, long cedulaUsuario, double precio, double iva,
			double total) {
		this.codigoVenta = codigoVenta;
		this.cedulaCliente = cedulaCliente;
		this.cedulaUsuario = cedulaUsuario;
		this.precio = precio;
		this.iva = iva;
		this.total = total;
	}

	public String getCodigoVenta() {
		return codigoVenta;
	}

	public long getCedulaCliente() {
		return cedulaCliente;
	}

	public long getCedulaUsuario() {
		return cedulaUsuario;
	}

	public double getPrecio() {
		return precio;
	}

	public double getIva() {
		return iva;
	}

	public double getTotal() {
		return total;
	}

}
