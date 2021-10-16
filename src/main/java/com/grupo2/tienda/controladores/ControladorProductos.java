package com.grupo2.tienda.controladores;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.grupo2.tienda.implementaciones.ImplementacionProductos;
import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.modelos.ArchivoDTO;
import com.grupo2.tienda.modelos.ProductoDTO;
import com.grupo2.tienda.modelos.UsuarioDTO;

@Controller
public class ControladorProductos {
	
	@Autowired
	private ImplementacionUsuarios impUsuarios;
	
	@Autowired
	private ImplementacionProductos impProductos;
	
	
	@GetMapping("/Productos")
	public String  Productos( Principal principal, Model modelo) {

		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());

		modelo.addAttribute("usuarioSesion", usuarioSesion);
		
		List<ArchivoDTO> ListaArchivos = impProductos.ListarArchivos();
		
		modelo.addAttribute("ListaArchivos", ListaArchivos);
		
		List<ProductoDTO> ListaProductos = impProductos.ListarProductos();

		modelo.addAttribute("ListaProductos", ListaProductos);
		
		return "Productos/index";
		
	}
	
	@PostMapping("/CargarProductos")
	public String  CargarProductos(@RequestParam("archivo") MultipartFile archivo, Principal principal, Model modelo) {
		
		String result = impProductos.CargarArchivo(archivo);
		
		if(result.equals("cargado")) {
		
			impProductos.CargarArchivoBaseDatos(archivo.getOriginalFilename());
		
		}
		
		return "redirect:Productos?" + result;
		
	}
	
	@PostMapping("/EliminarProductos")
	public String  EliminarProductos(@RequestParam("nombreArchivo") String nombreArchivo, Principal principal, Model modelo) {
		
		impProductos.EliminarArchivoBaseDatos(nombreArchivo);
		
		impProductos.EliminarArchivo(nombreArchivo);
		
		return "redirect:Productos?eliminado";
		
	}

}
