package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class Aplicacion {

	public static final int TAMANIO_INICIAL_STRESS = 5;

	private Grafo<Integer> _grafo;
	private ArrayList<ObservadorConjuntoDominante> _observadores;
	private Set<Integer> _solucion;
	private final AtomicBoolean _cancelado;

	public Aplicacion() {
		_grafo = new Grafo<Integer>();
		_observadores = new ArrayList<>();
		_solucion = new HashSet<>();
		_cancelado = new AtomicBoolean(false);
	}

	public void registrar(ObservadorConjuntoDominante observador) {
		if (observador == null) {
			throw new IllegalArgumentException("El observador no puede ser null.");
		}

		_observadores.add(observador);
	}

	public void agregarVertice(Integer vertice) {
		_grafo.agregarVertice(vertice);
		_solucion = new HashSet<>();

		notificarObservadoresNuevoGrafo();
	}

	public void agregarArista(Arista<Integer> arista) {
		if (arista == null) {
			throw new IllegalArgumentException("La arista no puede ser null.");
		}

		_grafo.agregarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		_solucion = new HashSet<>();

		notificarObservadoresNuevoGrafo();
	}

	public void cargarGrafo(List<Integer> vertices, List<Arista<Integer>> aristas) {
		if (vertices == null || aristas == null) {
			throw new IllegalArgumentException("Los datos del grafo no pueden ser null.");
		}

		Grafo<Integer> grafoCargado = new Grafo<Integer>();

		for (Integer vertice : vertices) {
			grafoCargado.agregarVertice(vertice);
		}

		for (Arista<Integer> arista : aristas) {
			if (arista == null) {
				throw new IllegalArgumentException("Las aristas no pueden ser null.");
			}

			grafoCargado.agregarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		}

		_grafo = grafoCargado;
		_solucion = new HashSet<>();
		notificarObservadoresNuevoGrafo();
	}

	private void notificarObservadoresNuevoGrafo() {
		for (ObservadorConjuntoDominante observador : _observadores)
			observador.notificarNuevoGrafo();
	}

	private void notificarObservadoresSolucion() {
		for (ObservadorConjuntoDominante observador : _observadores)
			observador.notificarSolucion();
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

	public void resolverBacktracking() {
		_cancelado.set(false);

		SolverBacktracking solverBacktracking = new SolverBacktracking(_grafo);

		Set<Integer> resultado = solverBacktracking.resolver(_cancelado);

		if (!_cancelado.get()) {
			_solucion = resultado;
			notificarObservadoresSolucion();
		}
	}

	public ResultadoComparacion compararAlgoritmos() {
		_cancelado.set(false);

		long inicioGoloso = System.nanoTime();
		Set<Integer> solucionGolosa = new SolverGoloso(_grafo).resolver();
		long tiempoGoloso = System.nanoTime() - inicioGoloso;

		if (_cancelado.get()) {
			return null;
		}

		long inicioBacktracking = System.nanoTime();
		Set<Integer> solucionBacktracking = new SolverBacktracking(_grafo).resolver(_cancelado);
		long tiempoBacktracking = System.nanoTime() - inicioBacktracking;

		if (_cancelado.get()) {
			return null;
		}

		_solucion = solucionBacktracking;

		return new ResultadoComparacion(solucionGolosa, solucionBacktracking, tiempoGoloso, tiempoBacktracking);
	}

	public void borrarSolucion() {
		_solucion = new HashSet<>();
		notificarObservadoresSolucion();
	}

	public Set<Integer> obtenerSolucion() {
		return Collections.unmodifiableSet(_solucion);
	}

	public void borrarGrafoActual() {
		_grafo = new Grafo<Integer>();
		_solucion = new HashSet<>();
		notificarObservadoresNuevoGrafo();
	}

	public void ejecutarStress(EjecutorStressBacktracking.ObservadorProgreso observador) {
		_cancelado.set(false);

		new EjecutorStressBacktracking(TAMANIO_INICIAL_STRESS).ejecutar(_cancelado, observador);
	}

	public void detenerEjecucion() {
		_cancelado.set(true);
	}

	public boolean fueCancelado() {
		return _cancelado.get();
	}
}
