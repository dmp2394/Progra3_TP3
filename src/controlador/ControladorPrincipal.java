package controlador;

import modelo.Aplicacion;
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

		_aplicacion.cargarGrafo(
			archivo.obtenerVertices(),
			archivo.obtenerAristas()
		);

	}

	public void resolver() {
		// TODO Auto-generated method stub
		_aplicacion.resolver();
	}
}
