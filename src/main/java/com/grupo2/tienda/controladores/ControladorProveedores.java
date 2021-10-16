package com.grupo2.tienda.controladores;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.grupo2.tienda.implementaciones.ImplementacionProveedores;
import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.modelos.ProveedorDTO;
import com.grupo2.tienda.modelos.UsuarioDTO;

@Controller
public class ControladorProveedores {

	@Autowired
	private ImplementacionUsuarios impUsuarios;
	
	@Autowired
	private ImplementacionProveedores impProveedores;

	@GetMapping("/Proveedores")
	public String Proveedores(@Param("buscar") boolean buscar, @Param("resultado") boolean resultado,
			@Param("nit") String nit, @Param("agregado") boolean agregado, @Param("existente") boolean existente,
			@Param("editado") boolean editado, @Param("eliminado") boolean eliminado, Principal principal,
			Model modelo) {

		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());

		modelo.addAttribute("usuarioSesion", usuarioSesion);
		
		if(resultado) {
		
			ProveedorDTO resultadoBusqueda =  impProveedores.ObtenerProveedor(Long.parseLong(nit));
			
			modelo.addAttribute("resultadoBusqueda", resultadoBusqueda);
			
		}

		List<ProveedorDTO> ListaProveedores = impProveedores.ListarProveedores();

		modelo.addAttribute("ListaProveedores", ListaProveedores);

		return "Proveedores/index";
		
	}

	@GetMapping("/AgregarProveedor")
	public String AgregarProveedor(@RequestParam("nit") long nit, @RequestParam("nombre") String nombre,
			@RequestParam("direccion") String direccion, @RequestParam("telefono") String telefono,
			@RequestParam("ciudad") String ciudad) {
		
		String resultado;

		if (!impProveedores.ValidarProveedor(nit)) {

			impProveedores.AgregarProveedor(nit, nombre, direccion, telefono, ciudad);

			resultado = "?agregado=true";

		} else {

			resultado = "?existente=true&nit=" + nit;

		}

		return "redirect:Proveedores" + resultado;

	}

	@GetMapping("/EditarProveedor")
	public String EditarProveedor(@RequestParam("nit") long nit, Principal principal,
			Model modelo) {

		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());

		modelo.addAttribute("usuarioSesion", usuarioSesion);
		
		ProveedorDTO proveedorObtenido = impProveedores.ObtenerProveedor(nit);

		modelo.addAttribute("proveedorObtenido", proveedorObtenido);

		return "/Proveedores/Editar/index";

	}

	@GetMapping("/ModificarProveedor")
	public String ProcesoEditarProveedor(@RequestParam("nitAnterior") long nitAnterior, @RequestParam("nit") long nit, @RequestParam("nombre") String nombre,
			@RequestParam("direccion") String direccion, @RequestParam("telefono") String telefono,
			@RequestParam("ciudad") String ciudad) {
		
		impProveedores.EditarProveedor(nitAnterior, nit, nombre, direccion, telefono, ciudad);

		return "redirect:Proveedores?editado=true";

	}

	@GetMapping("/EliminarProveedor")
	public String EliminarProveedor(@RequestParam("nit") long nit) {

		impProveedores.EliminarProveedor(nit);

		return "redirect:Proveedores?eliminado=true";

	}

	@GetMapping("/BuscarProveedor")
	public String BuscarProveedor(@RequestParam("nit") long nit) {
		
		if(impProveedores.ValidarProveedor(nit)) {
			
			return "redirect:Proveedores?buscar=true&resultado=true&nit=" + nit;
			
		} else {
			
			return "redirect:Proveedores?buscar=true";
			
		}		

	}

}
