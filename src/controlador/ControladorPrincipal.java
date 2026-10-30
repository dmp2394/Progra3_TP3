package controlador;

import modelo.Arista;
import modelo.GrafoJSON;
import modelo.Orquestador;

public class ControladorPrincipal {

	private Orquestador _orquestador;

	public ControladorPrincipal(Orquestador orquestador) {
		_orquestador = orquestador;
	}

	public void cargarGrafoDesdeJSON(String ruta) {
		GrafoJSON archivo = GrafoJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerVertices().isEmpty()) {
			throw new IllegalArgumentException("El archivo JSON no tiene vértices.");
		}

		for (Integer vertice : archivo.obtenerVertices()) {
			_orquestador.agregarVertice(vertice);
		}

		for (Arista<Integer> arista : archivo.obtenerAristas()) {
			_orquestador.agregarArista(arista);
		}

	}
}
