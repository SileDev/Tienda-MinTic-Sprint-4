package com.grupo2.tienda.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.modelos.UsuarioDTO;

@Controller
public class ControladorIngreso {
	
	@Autowired
	private ImplementacionUsuarios impUsuarios;
	
	@GetMapping({"/","/Ingreso"})
	public String Ingreso(Model modelo) {
		
		List<UsuarioDTO> FilasUsuarios = impUsuarios.ListarUsuarios();
		
		if( FilasUsuarios.size() == 0 ) {
			
			//Recuperacion del usuario inicial
			impUsuarios.AgregarUsuario(0, "Administrador Inicial", "admininicial", "No registrado", "admin123456");
			
		}
		
		return "Ingreso/index";
		
	}

}
