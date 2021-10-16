package com.grupo2.tienda.implementaciones;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

import com.grupo2.tienda.modelos.ArchivoDTO;
import com.grupo2.tienda.modelos.ProductoDTO;

public class ImplementacionProductos {

	private JdbcTemplate jdbcTemp;

	public ImplementacionProductos(DataSource fuenteDatos) {

		jdbcTemp = new JdbcTemplate(fuenteDatos);

	}

	public String CargarArchivo(MultipartFile archivo) {

		File uploadFile = new File("uploaded/" + archivo.getOriginalFilename());

		if (uploadFile.exists()) {

			return "existente";

		} else if (!archivo.isEmpty()) {

			try {

				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(uploadFile));
				out.write(archivo.getBytes());
				out.flush();
				out.close();

				return "cargado";

			} catch (FileNotFoundException e) {

				return "error";

			} catch (IOException e) {

				return "error";

			}

		} else {

			return "vacio";

		}

	}

	@SuppressWarnings("resource")
	public void CargarArchivoBaseDatos(String nombreArchivo) {

		BufferedReader lector;

		try {

			lector = new BufferedReader(new FileReader("uploaded/" + nombreArchivo));

			String lineas = "";

			while ((lineas = lector.readLine()) != null) {

				String[] valores = lineas.split(",");

				jdbcTemp.execute("INSERT INTO productos (codigo, nombre, nit, costo, iva, precio) VALUES ('"
						+ valores[0] + "', '" + valores[1] + "', '" + valores[2] + "', '" + valores[3] + "', '"
						+ valores[4] + "', '" + valores[5] + "')");

			}

		} catch (FileNotFoundException e) {

			return;

		} catch (IOException e) {

			return;

		}

	}

	public List<ArchivoDTO> ListarArchivos() {

		List<ArchivoDTO> listaArchivos = new ArrayList<ArchivoDTO>();

		File ruta = new File("uploaded/");

		if (ruta.listFiles() != null && ruta.listFiles().length != 0) {

			for (File detectedFile : ruta.listFiles()) {

				ArchivoDTO archivo = new ArchivoDTO(detectedFile.getName());

				listaArchivos.add(archivo);

			}

			return listaArchivos;

		} else {

			return null;

		}

	}

	public List<ProductoDTO> ListarProductos() {

		List<ProductoDTO> lista = jdbcTemp.query("SELECT * FROM productos", new RowMapper<ProductoDTO>() {

			@Override
			public ProductoDTO mapRow(ResultSet resultado, int indice) throws SQLException {

				ProductoDTO producto = new ProductoDTO(

						resultado.getLong("codigo"), resultado.getString("nombre"), resultado.getLong("nit"),
						resultado.getDouble("costo"), resultado.getDouble("iva"), resultado.getDouble("precio")

				);

				return producto;

			}

		});

		return lista;

	}

	public boolean BuscarProducto(String codigo) {

		List<ProductoDTO> ResultadoBusqueda = jdbcTemp.query(
				"SELECT * FROM productos WHERE codigo = '" + Long.parseLong(codigo) + "'",
				new RowMapper<ProductoDTO>() {

					@Override
					public ProductoDTO mapRow(ResultSet resultado, int indice) throws SQLException {

						ProductoDTO producto = new ProductoDTO(

								resultado.getLong("codigo"), resultado.getString("nombre"), resultado.getLong("nit"),
								resultado.getDouble("costo"), resultado.getDouble("iva"), resultado.getDouble("precio")

				);

						return producto;

					}

				});

		if (ResultadoBusqueda.size() != 0) {

			return true;

		} else {

			return false;

		}

	}
	
	public ProductoDTO ObtenerProducto(String codigo){
		
		List<ProductoDTO> Resultado = jdbcTemp.query(
				"SELECT * FROM productos WHERE codigo = '" + Long.parseLong(codigo) + "'",
				new RowMapper<ProductoDTO>() {

					@Override
					public ProductoDTO mapRow(ResultSet resultado, int indice) throws SQLException {

						ProductoDTO producto = new ProductoDTO(

								resultado.getLong("codigo"), resultado.getString("nombre"), resultado.getLong("nit"),
								resultado.getDouble("costo"), resultado.getDouble("iva"), resultado.getDouble("precio")

				);

						return producto;

					}

				});
		
		ProductoDTO producto = new ProductoDTO(

				Resultado.get(0).getCodigo(), Resultado.get(0).getNombre(),
				Resultado.get(0).getNit(), Resultado.get(0).getCosto(),
				Resultado.get(0).getIva(), Resultado.get(0).getPrecio()

);
		
		return producto;
		
	}

	public void EliminarArchivoBaseDatos(String nombreArchivo) {

		List<ProductoDTO> listaProductos = ListarProductos();

		for (int i = 0; i < listaProductos.size(); i++) {

			jdbcTemp.execute("DELETE FROM productos WHERE codigo = '" + listaProductos.get(i).getCodigo() + "'");

		}

	}

	public void EliminarArchivo(String nombreArchivo) {

		File archivo = new File("uploaded/" + nombreArchivo);

		if (archivo.exists()) {

			archivo.delete();

		}

	}

}
