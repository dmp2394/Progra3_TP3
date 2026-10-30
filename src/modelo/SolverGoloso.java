package modelo;

import java.util.HashSet;
import java.util.Set;

public class SolverGoloso {

	private final Grafo<Integer> instancia;

	public SolverGoloso(Grafo<Integer> instancia) {
		if (instancia == null) {
			throw new IllegalArgumentException("El grafo no puede ser nulo.");
		}

		this.instancia = instancia;
	}

	public Set<Integer> resolver() {
		/*
		 * El codigo básicamente lo que hace es agregar a dominados el que tenga el
		 * mejor vertice. El mejor vertice es básicamente el que suma mas vertices.
		 * Cuando los suma guarda al mejor vertice en solucion y a los dominados en
		 * dominados. y asi hasta que el conjunto dominados sea mejor a
		 * cantidadVertices.
		 */

		Set<Integer> solucion = new HashSet<>();
		Set<Integer> dominados = new HashSet<>();

		int cantidadVerticesDeInstancia = instancia.obtenerVertices().size();

		while (dominados.size() < cantidadVerticesDeInstancia) {
			Integer mejorVerticeElegido = elegirVerticeCuyosVecinosAportanMasNoDominados(dominados);

			solucion.add(mejorVerticeElegido);

			// Quedan cubiertos el elegido y sus vecinos.
			dominados.add(mejorVerticeElegido);
			dominados.addAll(instancia.obtenerVecinos(mejorVerticeElegido));
		}

		return solucion;
	}

	private Integer elegirVerticeCuyosVecinosAportanMasNoDominados(Set<Integer> dominados) {
		Integer mejor = null;
		// arranca en -1 porque no hay aporte posible con -1. Podrian aportar 0 pero no
		// -1.
		int mayorAporte = -1;

		for (Integer candidato : instancia.obtenerVertices()) {

			int aporteActual = aporte(candidato, dominados);

			/*
			 * Esta parte verifica si todavía no hay un candidato elegido, si el candidato
			 * actual tiene un aporte mayor o, en caso de empate, si tiene un número menor.
			 */

			if (mejor == null || aporteActual > mayorAporte || (aporteActual == mayorAporte && candidato < mejor)) {

				mejor = candidato;
				mayorAporte = aporteActual;
			}

		}

		return mejor;
	}

	public int aporte(Integer vertice, Set<Integer> dominados) {
		int cantidad = 0;

		// El propio vértice también cuenta si no está dominado.
		if (!dominados.contains(vertice)) {
			cantidad++;
		}

		// Contamos únicamente los vecinos aún no dominados.
		for (Integer vecino : instancia.obtenerVecinos(vertice)) {
			if (!dominados.contains(vecino)) {
				cantidad++;
			}
		}

		return cantidad;
	}
}