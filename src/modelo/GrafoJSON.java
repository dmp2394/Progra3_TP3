package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class GrafoJSON {

	private ArrayList<Integer> vertices;
	private ArrayList<Arista<Integer>> aristas;

	public static GrafoJSON leerJSON(String archivo) {
		try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
			return new Gson().fromJson(lector, GrafoJSON.class);
		} catch (IOException e) {
			throw new IllegalArgumentException("No se pudo leer el archivo JSON: " + archivo, e);
		}
	}

	public ArrayList<Integer> obtenerVertices() {
		return vertices == null ? new ArrayList<>() : new ArrayList<>(vertices);
	}

	public ArrayList<Arista<Integer>> obtenerAristas() {
		return aristas == null ? new ArrayList<>() : new ArrayList<>(aristas);
	}

}
