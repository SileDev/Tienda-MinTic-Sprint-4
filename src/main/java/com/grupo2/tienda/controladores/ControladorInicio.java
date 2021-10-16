package com.grupo2.tienda.controladores;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.modelos.UsuarioDTO;

@Controller
public class ControladorInicio {
	
	@Autowired
	private ImplementacionUsuarios impUsuarios;
	
	@GetMapping("/Inicio")
	public String Inicio(Principal principal, Model modelo) {

		UsuarioDTO usuarioSesion = impUsuarios.ObtenerUsuarioSesion(principal.getName());
		
		modelo.addAttribute("usuarioSesion", usuarioSesion);
		
		return "Inicio/index";
		
	}
	
}
