package controlador;

import modelo.Aplicacion;
import modelo.Arista;
import modelo.GrafoJSON;

public class ControladorPrincipal {

	private Aplicacion _aplicacion;

	public ControladorPrincipal(Aplicacion aplicacion) {
		_aplicacion = aplicacion;
	}

	public void cargarGrafoDesdeJSON(String ruta) {

		GrafoJSON archivo = GrafoJSON.leerJSON(ruta);

		if (archivo == null || archivo.obtenerVertices().isEmpty()) {
			throw new IllegalArgumentException("El archivo JSON no tiene vértices.");
		}

		_aplicacion.borrarGrafoActual();

		for (Integer vertice : archivo.obtenerVertices()) {
			_aplicacion.agregarVertice(vertice);
		}

		for (Arista<Integer> arista : archivo.obtenerAristas()) {
			_aplicacion.agregarArista(arista);
		}

	}

	public void resolver() {
		// TODO Auto-generated method stub
		_aplicacion.resolver();
	}
}
