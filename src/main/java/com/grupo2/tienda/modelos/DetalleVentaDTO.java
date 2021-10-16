package com.grupo2.tienda.modelos;

public class DetalleVentaDTO {
	
	private String codigoVenta;
	private Long codigoProducto;
	private Long cantidad;
	private double valorUnitario;
	private double iva;
	private double total;
	
	public DetalleVentaDTO(String codigoVenta, Long codigoProducto, Long cantidad, double valorUnitario, double iva,
			double total) {
		this.codigoVenta = codigoVenta;
		this.codigoProducto = codigoProducto;
		this.cantidad = cantidad;
		this.valorUnitario = valorUnitario;
		this.iva = iva;
		this.total = total;
	}

	public String getCodigoVenta() {
		return codigoVenta;
	}

	public Long getCodigoProducto() {
		return codigoProducto;
	}

	public Long getCantidad() {
		return cantidad;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public double getIva() {
		return iva;
	}

	public double getTotal() {
		return total;
	}
	
}
