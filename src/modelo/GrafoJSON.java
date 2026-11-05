package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

public class GrafoJSON {

	private ArrayList<Integer> _vertices;
	private ArrayList<Arista<Integer>> _aristas;

	public static GrafoJSON leerJSON(String archivo) {
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
			// Los atributos empiezan con "_" pero en el archivo las claves van sin él
			// (por ejemplo "vertices" y "extremo1").
			Gson gson = new GsonBuilder()
					.setFieldNamingStrategy(campo -> campo.getName().replaceFirst("^_", ""))
					.create();

			return gson.fromJson(lector, GrafoJSON.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer el archivo JSON: " + archivo, e);
		} catch (JsonParseException e) {
			throw new IllegalArgumentException("El archivo no tiene un formato JSON de grafo válido.", e);
		}
	}

	public ArrayList<Integer> obtenerVertices() {
		return _vertices == null ? new ArrayList<>() : new ArrayList<>(_vertices);
	}

	public ArrayList<Arista<Integer>> obtenerAristas() {
		return _aristas == null ? new ArrayList<>() : new ArrayList<>(_aristas);
	}

}
