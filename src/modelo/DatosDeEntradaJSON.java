package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class DatosDeEntradaJSON {

	private ArrayList<ProvinciaJSON> provincias;
	private ArrayList<AristaJSON> conexiones;
	private Integer cantidadRegiones;

	public Integer obtenerCantidadRegiones() {
		return cantidadRegiones;
	}

	public ArrayList<ProvinciaJSON> obtenerProvincias() {
		return provincias == null ? new ArrayList<>() : new ArrayList<>(provincias);
	}

	public ArrayList<AristaJSON> obtenerConexiones() {
		return conexiones == null ? new ArrayList<>() : new ArrayList<>(conexiones);
	}

	public static DatosDeEntradaJSON leerJSON(String archivo) {
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
			return new Gson().fromJson(lector, DatosDeEntradaJSON.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer el archivo JSON: " + archivo, e);
		}
	}
}
