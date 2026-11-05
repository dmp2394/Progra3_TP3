package modelo;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Verificaciones {

	private Verificaciones() {
	}

	public static Set<Integer> conjunto(Integer... vertices) {
		return new HashSet<>(Arrays.asList(vertices));
	}

	public static void verificarDominacion(Grafo<Integer> grafo, Set<Integer> solucion) {
		for (Integer elegido : solucion) {
			assertTrue("La solución contiene un vértice ajeno al grafo.", grafo.existeVertice(elegido));
		}

		for (Integer vertice : grafo.obtenerVertices()) {
			assertTrue("El vértice " + vertice + " no quedó dominado.", estaDominado(grafo, solucion, vertice));
		}
	}

	private static boolean estaDominado(Grafo<Integer> grafo, Set<Integer> solucion, Integer vertice) {
		if (solucion.contains(vertice)) {
			return true;
		}

		for (Integer vecino : grafo.obtenerVecinos(vertice)) {
			if (solucion.contains(vecino)) {
				return true;
			}
		}

		return false;
	}
}
