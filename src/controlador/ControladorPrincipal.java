package controlador;

import modelo.Arista;
import modelo.GrafoJSON;

public class ControladorPrincipal {

	public void cargarProvinciasDesdeJSON(String ruta) {
		GrafoJSON archivo = GrafoJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerVertices().isEmpty()) {
			throw new IllegalArgumentException("El archivo JSON no tiene vértices.");
		}

		int cantidadVertices = archivo.obtenerVertices().size();

		for (Integer vertice : archivo.obtenerVertices()) {
			orquestador.agregarVertice(vertice);
		}

		for (Arista<Integer> arista : archivo.obtenerAristas()) {
			orquestador.agregarArista();
		}

	}
}
