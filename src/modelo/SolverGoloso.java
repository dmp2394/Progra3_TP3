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
		/*
		 * El codigo básicamente lo que hace es agregar a dominados el que tenga el
		 * mejor vertice. El mejor vertice es básicamente el que suma mas vertices.
		 * Cuando los suma guarda al mejor vertice en solucion y a los dominados en
		 * dominados. y asi hasta que el conjunto dominados sea mejor a
		 * cantidadVertices.
		 */

		Set<Integer> solucion = new HashSet<>();
		Set<Integer> dominados = new HashSet<>();

		int cantidadVerticesDeInstancia = _instancia.obtenerVertices().size();

		while (dominados.size() < cantidadVerticesDeInstancia) {
			
			Integer mejorVerticeElegido = elegirVerticeCuyosVecinosAportanMasNoDominados(dominados, solucion);

			solucion.add(mejorVerticeElegido);

			
			dominados.add(mejorVerticeElegido);
			dominados.addAll(_instancia.obtenerVecinos(mejorVerticeElegido));
		}

		return solucion;
	}

	private Integer elegirVerticeCuyosVecinosAportanMasNoDominados(Set<Integer> dominados, Set<Integer> solucion) {
		Integer mejor = null;
		
		int mayorAporte = -1;

		for (Integer candidato : _instancia.obtenerVertices()) {
			if (solucion.contains(candidato)) {
			    continue;
			}
			
			int aporteActual = aporte(candidato, dominados);

			if (mejor == null || aporteActual > mayorAporte || (aporteActual == mayorAporte && candidato < mejor)) {

				mejor = candidato;
				mayorAporte = aporteActual;
			}

		}

		return mejor;
	}

	public int aporte(Integer vertice, Set<Integer> dominados) {
		int cantidad = 0;

		
		if (!dominados.contains(vertice)) {
			cantidad++;
		}

	
		for (Integer vecino : _instancia.obtenerVecinos(vertice)) {
			if (!dominados.contains(vecino)) {
				cantidad++;
			}
		}

		return cantidad;
	}
}