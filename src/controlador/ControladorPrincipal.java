package controlador;

import modelo.Aplicacion;
import modelo.EjecutorStressBacktracking;
import modelo.GrafoJSON;
import modelo.ResultadoComparacion;

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

		_aplicacion.cargarGrafo(archivo.obtenerVertices(), archivo.obtenerAristas());

	}

	public void resolver() {
		_aplicacion.resolver();
	}

	public void resolverBacktracking() {
		_aplicacion.resolverBacktracking();
	}

	public void limpiarSolucion() {
		_aplicacion.borrarSolucion();
	}

	public ResultadoComparacion compararAlgoritmos() {
		return _aplicacion.compararAlgoritmos();
	}

	public void ejecutarStress(int cantidadInstancias, int cantidadVertices,
			EjecutorStressBacktracking.ObservadorProgreso observador) {
		_aplicacion.ejecutarStress(cantidadInstancias, cantidadVertices, observador);
	}

	public void detenerEjecucion() {
		_aplicacion.detenerEjecucion();
	}

	public boolean fueCancelado() {
		return _aplicacion.fueCancelado();
	}
}
