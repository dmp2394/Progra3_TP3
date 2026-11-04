package modelo;

import java.util.HashSet;
import java.util.Set;

public class SolverGoloso {

	private final Grafo<Integer> _instancia;

	public SolverGoloso(Grafo<Integer> instancia) {
		if (instancia == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo.");
		}

		_instancia = instancia;
	}

	public Set<Integer> resolver() {
		Set<Integer> solucion = new HashSet<>();
		Set<Integer> dominados = new HashSet<>();

		int cantidadVertices = _instancia.obtenerVertices().size();

		while (dominados.size() < cantidadVertices) {
			Integer mejorVertice = elegirVerticeConMayorAporte(dominados, solucion);

			solucion.add(mejorVertice);

			dominados.add(mejorVertice);
			dominados.addAll(_instancia.obtenerVecinos(mejorVertice));
		}

		return solucion;
	}

	private Integer elegirVerticeConMayorAporte(Set<Integer> dominados, Set<Integer> solucion) {
		Integer mejor = null;
		int mayorAporte = -1;

		for (Integer candidato : _instancia.obtenerVertices()) {
			if (solucion.contains(candidato)) {
				continue;
			}

			int aporteActual = calcularAporte(candidato, dominados);

			if (aporteActual > mayorAporte || (aporteActual == mayorAporte && candidato < mejor)) {
				mejor = candidato;
				mayorAporte = aporteActual;
			}
		}

		return mejor;
	}

	public int calcularAporte(Integer vertice, Set<Integer> dominados) {
		int noDominados = 0;

		if (!dominados.contains(vertice)) {
			noDominados++;
		}

		for (Integer vecino : _instancia.obtenerVecinos(vertice)) {
			if (!dominados.contains(vecino)) {
				noDominados++;
			}
		}

		return noDominados;
	}
}
