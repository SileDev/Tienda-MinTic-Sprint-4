package com.grupo2.tienda.controladores;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;

//import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo2.tienda.implementaciones.ImplementacionClientes;
import com.grupo2.tienda.implementaciones.ImplementacionProductos;
import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.implementaciones.ImplementacionVentas;
import com.grupo2.tienda.modelos.ClienteDTO;
import com.grupo2.tienda.modelos.DetalleVentaDTO;
import com.grupo2.tienda.modelos.ProductoDTO;
import com.grupo2.tienda.modelos.UsuarioDTO;
import com.grupo2.tienda.modelos.VentaDTO;

//import com.grupo2.tienda.modelos.UsuarioDTO;

@Controller
public class ControladorVentas {

	@Autowired
	private ImplementacionUsuarios impUsuarios;

	@Autowired
	private ImplementacionClientes impClientes;

	@Autowired
	private ImplementacionProductos impProductos;

	@Autowired
	private ImplementacionVentas impVentas;

	@GetMapping("/Ventas")
	public String Ventas(Principal principal, Model modelo) {

		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());

		modelo.addAttribute("usuarioSesion", usuarioSesion);

		List<VentaDTO> ListaVentas = impVentas.ListarVentas();

		System.out.println(ListaVentas.size());

		modelo.addAttribute("ListaVentas", ListaVentas);

		return "Ventas/index";

	}

	@GetMapping("/ConsultarVenta")
	public String ConsultarVenta(@Param("codigoPrimero") String codigoPrimero,
			@Param("cantidadPrimero") String cantidadPrimero, @Param("codigoSegundo") String codigoSegundo,
			@Param("cantidadSegundo") String cantidadSegundo, @Param("codigoTercero") String codigoTercero,
			@Param("cantidadTercero") String cantidadTercero, @Param("cedulaCliente") String cedulaCliente,
			@Param("cedulaUsuario") String cedulaUsuario, @Param("validar") boolean validar) {

		int faltantes = 0;
		double total = 0, iva = 0, totalIva = 0, cantidadProductos = 0;

		boolean consultandoPrimero = false, consultandoSegundo = false, consultandoTercero = false;

		String resultado = "codigoPrimero=" + codigoPrimero + "&cantidadPrimero=" + cantidadPrimero + "&codigoSegundo="
				+ codigoSegundo + "&cantidadSegundo=" + cantidadSegundo + "&codigoTercero=" + codigoTercero
				+ "&cantidadTercero=" + cantidadTercero + "&cedulaCliente=" + cedulaCliente + "&cedulaUsuario="
				+ cedulaUsuario;

		if (!codigoPrimero.equals("") || !cantidadPrimero.equals("")) {

			if (codigoPrimero.equals("") || cantidadPrimero.equals("")) {

				faltantes++;

				resultado += "&faltantePrimero";

			} else {

				consultandoPrimero = true;

			}

		}

		if (!codigoSegundo.equals("") || !cantidadSegundo.equals("")) {

			if (codigoSegundo.equals("") || cantidadSegundo.equals("")) {

				faltantes++;

				resultado += "&faltanteSegundo";

			} else {

				consultandoSegundo = true;

			}

		}

		if (!codigoTercero.equals("") || !cantidadTercero.equals("")) {

			if (codigoTercero.equals("") || cantidadTercero.equals("")) {

				faltantes++;

				resultado += "&faltanteTercero";

			} else {

				consultandoTercero = true;

			}

		}

		boolean validable;

		boolean encontrado, resultadoPrimero = false, resultadoSegundo = false, resultadoTercero = false;

		if (consultandoPrimero) {

			encontrado = impProductos.BuscarProducto(codigoPrimero);

			if (encontrado) {

				resultadoPrimero = true;

				ProductoDTO producto = impProductos.ObtenerProducto(codigoPrimero);

				resultado += "&resultadoPrimero=true&nombrePrimero=" + producto.getNombre().toString()
						+ "&precioPrimero=" + producto.getCosto() + "&totalPrimero="
						+ producto.getCosto() * Long.parseLong(cantidadPrimero);

				total += producto.getCosto() * Long.parseLong(cantidadPrimero);

				iva += producto.getIva();

				totalIva += producto.getPrecio() * Long.parseLong(cantidadPrimero);

				cantidadProductos += 1;

			} else {

				validable = false;

				resultado += "&errorPrimero=true";

			}

		}

		if (consultandoSegundo) {

			encontrado = impProductos.BuscarProducto(codigoSegundo);

			if (encontrado) {

				resultadoSegundo = true;

				ProductoDTO producto = impProductos.ObtenerProducto(codigoSegundo);

				resultado += "&resultadoSegundo=true&nombreSegundo=" + producto.getNombre().toString()
						+ "&precioSegundo=" + producto.getCosto() + "&totalSegundo="
						+ producto.getCosto() * Long.parseLong(cantidadSegundo);

				total += producto.getCosto() * Long.parseLong(cantidadSegundo);

				iva += producto.getIva();

				totalIva += producto.getPrecio() * Long.parseLong(cantidadSegundo);

				cantidadProductos += 1;

			} else {

				validable = false;

				resultado += "&errorSegundo=true";

			}

		}

		if (consultandoTercero) {

			encontrado = impProductos.BuscarProducto(codigoTercero);

			if (encontrado) {

				resultadoTercero = true;

				ProductoDTO producto = impProductos.ObtenerProducto(codigoTercero);

				resultado += "&resultadoTercero=true&nombreTercero=" + producto.getNombre().toString()
						+ "&precioTercero=" + producto.getCosto() + "&totalTercero="
						+ producto.getCosto() * Long.parseLong(cantidadTercero);

				total += producto.getCosto() * Long.parseLong(cantidadTercero);

				iva += producto.getIva();

				totalIva += producto.getPrecio() * Long.parseLong(cantidadTercero);

				cantidadProductos += 1;

			} else {

				validable = false;

				resultado += "&errorTercero=true";

			}

		}

		boolean errorCliente, errorUsuario;

		if (impClientes.ValidarCliente(Long.parseLong(cedulaCliente))) {

			ClienteDTO cliente = impClientes.ObtenerCliente(Long.parseLong(cedulaCliente));

			resultado += "&cliente=" + cliente.getNombre();

			errorCliente = false;

		} else {

			resultado += "&errorCliente";

			errorCliente = true;

		}

		if (impUsuarios.ValidarUsuario(Long.parseLong(cedulaUsuario))) {

			UsuarioDTO usuario = impUsuarios.ObtenerUsuario(Long.parseLong(cedulaUsuario));

			resultado += "&usuario=" + usuario.getNombre();

			errorUsuario = false;

		} else {

			resultado += "&errorUsuario";

			errorUsuario = true;

		}

		if (faltantes == 0 && cantidadProductos != 0 && !errorCliente && !errorUsuario) {

			validable = true;

		} else {

			validable = false;

		}

		if (validable) {

			iva = iva / cantidadProductos;

			resultado += "&validable";

			if (validar) {

				Date fecha = Calendar.getInstance().getTime();
				DateFormat formatoFecha = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				String codigoVenta = formatoFecha.format(fecha);

				return "redirect:ValidarVenta?" + "validar=" + validar + "&resultadoPrimero=" + resultadoPrimero
						+ "&codigoPrimero=" + codigoPrimero + "&cantidadPrimero=" + cantidadPrimero
						+ "&resultadoSegundo=" + resultadoSegundo + "&codigoSegundo=" + codigoSegundo
						+ "&cantidadSegundo=" + cantidadSegundo + "&resultadoTercero=" + resultadoTercero
						+ "&codigoTercero=" + codigoTercero + "&cantidadTercero=" + cantidadTercero + "&cedulaCliente="
						+ cedulaCliente + "&cedulaUsuario=" + cedulaUsuario + "&codigoVenta=" + codigoVenta;

			} else {

				return "redirect:Ventas?" + resultado + "&total=" + total + "&iva=" + iva + "&totalIva=" + totalIva;

			}

		} else {

			return "redirect:Ventas?" + resultado;

		}

	}

	@GetMapping("/ValidarVenta")
	public String ValidarVenta(@Param("resultadoPrimero") boolean resultadoPrimero,
			@Param("codigoPrimero") String codigoPrimero, @Param("cantidadPrimero") String cantidadPrimero,
			@Param("resultadoSegundo") boolean resultadoSegundo, @Param("codigoSegundo") String codigoSegundo,
			@Param("cantidadSegundo") String cantidadSegundo, @Param("resultadoTercero") boolean resultadoTercero,
			@Param("codigoTercero") String codigoTercero, @Param("cantidadTercero") String cantidadTercero,
			@Param("cedulaCliente") String cedulaCliente, @Param("cedulaUsuario") String cedulaUsuario,
			@Param("codigoVenta") String codigoVenta) {

		System.out.println("Venta registrada en la base de datos");

		int cantidadProductos = 0;

		long precio = 0, iva = 0, total = 0;

		if (resultadoPrimero) {

			ProductoDTO producto = impProductos.ObtenerProducto(codigoPrimero);

			impVentas.RegistrarDetallesVenta(codigoVenta, Long.parseLong(codigoPrimero),
					Long.parseLong(cantidadPrimero), producto.getCosto(), producto.getIva(),
					producto.getPrecio() * Long.parseLong(cantidadPrimero));

			precio += producto.getCosto() * Long.parseLong(cantidadPrimero);

			iva += producto.getIva();

			total += producto.getPrecio() * Long.parseLong(cantidadPrimero);

			cantidadProductos += 1;

		}

		if (resultadoSegundo) {

			ProductoDTO producto = impProductos.ObtenerProducto(codigoSegundo);

			impVentas.RegistrarDetallesVenta(codigoVenta, Long.parseLong(codigoSegundo),
					Long.parseLong(cantidadSegundo), producto.getCosto(), producto.getIva(),
					producto.getPrecio() * Long.parseLong(cantidadSegundo));

			precio += producto.getCosto() * Long.parseLong(cantidadSegundo);

			iva += producto.getIva();

			total += producto.getPrecio() * Long.parseLong(cantidadSegundo);

			cantidadProductos += 1;

		}

		if (resultadoTercero) {

			ProductoDTO producto = impProductos.ObtenerProducto(codigoTercero);

			impVentas.RegistrarDetallesVenta(codigoVenta, Long.parseLong(codigoTercero),
					Long.parseLong(cantidadTercero), producto.getCosto(), producto.getIva(),
					producto.getPrecio() * Long.parseLong(cantidadTercero));

			precio += producto.getCosto() * Long.parseLong(cantidadTercero);

			iva += producto.getIva();

			total += producto.getPrecio() * Long.parseLong(cantidadTercero);

			cantidadProductos += 1;

		}

		impVentas.RegistrarVenta(codigoVenta, Long.parseLong(cedulaCliente), Long.parseLong(cedulaUsuario), precio,
				iva / cantidadProductos, total);

		return "redirect:Ventas?agregado";

	}
	
	@GetMapping("/DetallesVenta")
	public String DetallesVenta(@Param("codigoVenta") String codigoVenta, Principal principal, Model modelo) {
		
		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());

		modelo.addAttribute("usuarioSesion", usuarioSesion);
		
		List<DetalleVentaDTO> ListaDetalles = impVentas.ListarDetallesVentas(codigoVenta);
		
		modelo.addAttribute("ListaDetalles", ListaDetalles);
		
		return "Ventas/Detalles/index";
		
	}

}
