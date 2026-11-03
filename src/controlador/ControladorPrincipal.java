package controlador;

import modelo.Aplicacion;
import modelo.EjecutorStressBacktracking;
import modelo.GrafoJSON;
import modelo.ResultadoComparacion;


import java.util.concurrent.atomic.AtomicBoolean;

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
		
		_aplicacion.resolver();
	}


	public void resolverBacktracking(AtomicBoolean cancelado) {
	    _aplicacion.resolverBacktracking(cancelado);
	}

	public void limpiarSolucion() {
		_aplicacion.borrarSolucion();
	}

	public ResultadoComparacion compararAlgoritmos(AtomicBoolean cancelado) {

	    return _aplicacion.compararAlgoritmos(cancelado);
	}
	
	public ResultadoComparacion compararAlgoritmos() {
		return _aplicacion.compararAlgoritmos();
	}

	public void ejecutarStress(int cantidadInstancias, int cantidadVertices,AtomicBoolean cancelado,
			EjecutorStressBacktracking.ObservadorProgreso observador) {
		
		new EjecutorStressBacktracking(cantidadInstancias, cantidadVertices).ejecutar(cancelado, observador);
	}
}
