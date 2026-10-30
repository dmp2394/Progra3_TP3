package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Aplicacion {

	private Grafo<Integer> _grafo;
	private ArrayList<ObservadorConjuntoDominante> _observadores;
	private Set<Integer> _solucion;

	public Aplicacion() {
		_grafo = new Grafo<Integer>();
		_observadores = new ArrayList<>();
		_solucion = new HashSet<>();
	}

	public void registrar(ObservadorConjuntoDominante observador) {
		if (observador == null) {
			throw new IllegalArgumentException("El observador no puede ser null.");
		}

		_observadores.add(observador);
	}

	public void agregarVertice(Integer vertice) {
		_grafo.agregarVertice(vertice);

		notificarObservadoresNuevoGrafo();
	}

	public void agregarArista(Arista<Integer> arista) {
		_grafo.agregarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());

		notificarObservadoresNuevoGrafo();
	}

	private void notificarObservadoresNuevoGrafo() {
		for (ObservadorConjuntoDominante observador : _observadores)
			observador.notificarNuevoGrafo(this);
	}

	private void notificarObservadoresSolucion() {
		for (ObservadorConjuntoDominante observador : _observadores)
			observador.notificarSolucion(this);
	}

	public List<Integer> obtenerVertices() {
		return Collections.unmodifiableList(_grafo.obtenerVertices());

	}

	public List<Arista<Integer>> obtenerAristas() {
		return Collections.unmodifiableList(_grafo.obtenerAristas());

	}

	public void resolver() {
		SolverGoloso solverGoloso = new SolverGoloso(_grafo);

		_solucion = solverGoloso.resolver();

		notificarObservadoresSolucion();
	}

	public Set<Integer> obtenerSolucion() {
		return Collections.unmodifiableSet(_solucion);
	}

	public void borrarGrafoActual() {
		_grafo = new Grafo<Integer>();
		_solucion = new HashSet<>();
	}
}
