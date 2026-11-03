package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class SolverBacktracking {

	private final Grafo<Integer> instancia;

	public SolverBacktracking(Grafo<Integer> instancia) {
		if (instancia == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo.");
		}

		this.instancia = instancia;
	}

	public Set<Integer> resolver() {
		return resolver(new AtomicBoolean(false));
	}

	public Set<Integer> resolver(AtomicBoolean cancelado) {
		if (cancelado == null) {
			throw new IllegalArgumentException("La señal de cancelación no puede ser nula.");
		}

		List<Integer> vertices = new ArrayList<>(instancia.obtenerVertices());
		Collections.sort(vertices);

		Set<Integer> solucion = new HashSet<>();
		buscar(vertices, 0, new ArrayList<>(), solucion, cancelado);
		return solucion;
	}

	private boolean buscar(List<Integer> vertices, int inicio,
			List<Integer> candidatos, Set<Integer> solucion, AtomicBoolean cancelado) {
		if (cancelado.get()) {
			return false;
		}
		
		 if (!solucion.isEmpty() && candidatos.size() >= solucion.size()) {
		        return false;
		    }

		if (esConjuntoDominante(candidatos)) {
			if (solucion.isEmpty() || candidatos.size() < solucion.size()) {
				solucion.clear();
				solucion.addAll(candidatos);
			}
			return true;
		}

		boolean encontroSolucion = false;
		for (int i = inicio; i < vertices.size(); i++) {
			candidatos.add(vertices.get(i));
			if (buscar(vertices, i + 1, candidatos, solucion, cancelado)) {
				encontroSolucion = true;
			}
			candidatos.remove(candidatos.size() - 1);
			if (cancelado.get()) {
				break;
			}
		}

		return encontroSolucion;
	}

	private boolean esConjuntoDominante(List<Integer> candidatos) {
		Set<Integer> dominados = new HashSet<>(candidatos);

		for (Integer vertice : candidatos) {
			dominados.addAll(instancia.obtenerVecinos(vertice));
		}

		return dominados.containsAll(instancia.obtenerVertices());
	}
}
