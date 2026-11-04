package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SolverBacktracking {

	private final Grafo<Integer> _instancia;

	private List<Integer> _vertices;
	private List<Integer> _candidatos;
	private Set<Integer> _solucion;
	private AtomicBoolean _cancelado;

	public SolverBacktracking(Grafo<Integer> instancia) {
		if (instancia == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo.");
		}

		_instancia = instancia;
	}

	public Set<Integer> resolver(AtomicBoolean cancelado) {
		if (cancelado == null) {
			throw new IllegalArgumentException("La señal de cancelación no puede ser nula.");
		}

		_cancelado = cancelado;

		_vertices = new ArrayList<>(_instancia.obtenerVertices());
		Collections.sort(_vertices);

		_candidatos = new ArrayList<>();
		_solucion = new HashSet<>();

		buscar(0);

		return _solucion;
	}

	private void buscar(int inicio) {
		if (fueCanceladoPorUsuario()) {
			return;
		}

		if (noMejoraLaSolucion()) {
			return;
		}

		if (esConjuntoDominante(_candidatos)) {
			guardarSiEsMejorSolucion();
			return;
		}

		for (int i = inicio; i < _vertices.size(); i++) {
			_candidatos.add(_vertices.get(i));
			buscar(i + 1);
			_candidatos.remove(_candidatos.size() - 1);
		}
	}

	private boolean fueCanceladoPorUsuario() {
		return _cancelado.get();
	}

	private boolean noMejoraLaSolucion() {
		return !_solucion.isEmpty() && _candidatos.size() >= _solucion.size();
	}

	private void guardarSiEsMejorSolucion() {
		if (_solucion.isEmpty() || _candidatos.size() < _solucion.size()) {
			_solucion.clear();
			_solucion.addAll(_candidatos);
		}
	}

	private boolean esConjuntoDominante(List<Integer> candidatos) {
		Set<Integer> dominados = new HashSet<>(candidatos);

		for (Integer vertice : candidatos) {
			dominados.addAll(_instancia.obtenerVecinos(vertice));
		}

		return dominados.containsAll(_instancia.obtenerVertices());
	}
}
