package com.grupo2.tienda.implementaciones;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.grupo2.tienda.modelos.DetalleVentaDTO;
import com.grupo2.tienda.modelos.VentaDTO;

public class ImplementacionVentas {

	private JdbcTemplate jdbcTemp;

	public ImplementacionVentas(DataSource fuenteDatos) {

		jdbcTemp = new JdbcTemplate(fuenteDatos);

	}

	public void RegistrarVenta(String codigoVenta, long cedulaCliente, long cedulaUsuario, double precio, double iva,
			double total) {

		jdbcTemp.execute(
				"INSERT INTO ventas (codigo_venta, cedula_cliente, cedula_usuario, precio, iva, total) Values ('"
						+ codigoVenta + "', '" + cedulaCliente + "', '" + cedulaUsuario + "', '" + precio + "', '" + iva
						+ "', '" + total + "')");

	}

	public List<VentaDTO> ListarVentas() {

		List<VentaDTO> lista = jdbcTemp.query("SELECT * FROM ventas", new RowMapper<VentaDTO>() {

			@Override
			public VentaDTO mapRow(ResultSet resultado, int indice) throws SQLException {

				VentaDTO venta = new VentaDTO(

						resultado.getString("codigo_venta"), resultado.getLong("cedula_cliente"),
						resultado.getLong("cedula_usuario"), resultado.getDouble("precio"), resultado.getDouble("iva"),
						resultado.getDouble("total")

				);

				return venta;

			}

		});

		return lista;

	}

	public void RegistrarDetallesVenta(String codigoVenta, long codigoProducto, long cantidad, double precio,
			double iva, double total) {

		jdbcTemp.execute(
				"INSERT INTO detalles_ventas (codigo_venta, codigo_producto, cantidad, valor_unitario, iva, total) Values ('"
						+ codigoVenta + "', '" + codigoProducto + "', '" + cantidad + "', '" + precio + "', '" + iva
						+ "', '" + total + "')");

	}

	public List<DetalleVentaDTO> ListarDetallesVentas(String codigoVenta) {

		List<DetalleVentaDTO> Resultado = jdbcTemp.query(
				"SELECT * FROM detalles_ventas WHERE codigo_venta = '" + codigoVenta + "'",
				new RowMapper<DetalleVentaDTO>() {

					@Override
					public DetalleVentaDTO mapRow(ResultSet resultado, int indice) throws SQLException {

						DetalleVentaDTO detalleVenta = new DetalleVentaDTO(

								resultado.getString("codigo_venta"), resultado.getLong("codigo_producto"),
								resultado.getLong("cantidad"), resultado.getDouble("valor_unitario"),
								resultado.getDouble("iva"), resultado.getDouble("total")

				);

						return detalleVenta;

					}

				});

		return Resultado;

	}

}
