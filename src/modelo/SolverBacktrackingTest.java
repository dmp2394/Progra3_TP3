package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SolverBacktrackingTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Grafo<Integer> grafo = new Grafo<>();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertTrue(resultado.isEmpty());
	}

	@Test
	public void grafoEstrellaEncuentraElCentroComoMinimo() {
		Grafo<Integer> grafo = crearGrafoEstrella();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertEquals(conjunto(1), resultado);
	}

	@Test
	public void encuentraConjuntoDominanteMinimo() {
		Grafo<Integer> grafo = crearGrafoCamino();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertEquals(2, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverBacktracking(null);
	}

	private Grafo<Integer> crearGrafoEstrella() {
		Grafo<Integer> grafo = new Grafo<>();
		for (int i = 1; i <= 5; i++) {
			grafo.agregarVertice(i);
		}
		for (int i = 2; i <= 5; i++) {
			grafo.agregarArista(1, i);
		}
		return grafo;
	}

	private Grafo<Integer> crearGrafoCamino() {
		Grafo<Integer> grafo = new Grafo<>();
		for (int i = 1; i <= 5; i++) {
			grafo.agregarVertice(i);
		}
		for (int i = 1; i < 5; i++) {
			grafo.agregarArista(i, i + 1);
		}
		return grafo;
	}

	private Set<Integer> conjunto(Integer... vertices) {
		Set<Integer> resultado = new HashSet<>();
		for (Integer vertice : vertices) {
			resultado.add(vertice);
		}
		return resultado;
	}

	private void verificarDominacion(Grafo<Integer> grafo, Set<Integer> solucion) {
		for (Integer vertice : grafo.obtenerVertices()) {
			if (!solucion.contains(vertice)
					&& !intersecta(solucion, grafo.obtenerVecinos(vertice))) {
				assertTrue("El vértice " + vertice + " no quedó dominado.", false);
			}
		}
	}

	private boolean intersecta(Set<Integer> solucion, Set<Integer> vecinos) {
		for (Integer vertice : solucion) {
			if (vecinos.contains(vertice)) {
				return true;
			}
		}
		return false;
	}
}
