package com.grupo2.tienda.controladores;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import com.grupo2.tienda.implementaciones.ImplementacionClientes;
import com.grupo2.tienda.implementaciones.ImplementacionProductos;
import com.grupo2.tienda.implementaciones.ImplementacionProveedores;
import com.grupo2.tienda.implementaciones.ImplementacionUsuarios;
import com.grupo2.tienda.implementaciones.ImplementacionVentas;

@Component
public class ControladorBaseDatos {
	
	@Bean
	public DataSource getDataSource() {

		DriverManagerDataSource fuenteDatos = new DriverManagerDataSource();
		
		fuenteDatos.setDriverClassName("com.mysql.cj.jdbc.Driver");
		fuenteDatos.setUrl("jdbc:mysql://localhost/tiendagenerica");
		fuenteDatos.setUsername("root");
		fuenteDatos.setPassword("databasepassword");

		return fuenteDatos;
		
	}
	
	@Bean
	public ImplementacionUsuarios getImplementacionUsuarios() {
		
		return new ImplementacionUsuarios(getDataSource());
		
	}
	
	@Bean
	public ImplementacionClientes getImplementacionClientes() {
		
		return new ImplementacionClientes(getDataSource());
		
	}
	
	@Bean
	public ImplementacionProveedores getImplementacionProveedores() {
		
		return new ImplementacionProveedores(getDataSource());
		
	}
	
	@Bean
	public ImplementacionProductos getImplementacionProductos() {
		
		return new ImplementacionProductos(getDataSource());
		
	}
	
	@Bean
	public ImplementacionVentas getImplementacionVentas() {
		
		return new ImplementacionVentas(getDataSource());
		
	}

}
