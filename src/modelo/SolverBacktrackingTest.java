package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SolverBacktrackingTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Set<Integer> resultado = new SolverBacktracking(GrafosDePrueba.vacio()).resolver();

		assertTrue(resultado.isEmpty());
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Set<Integer> resultado = new SolverBacktracking(GrafosDePrueba.unSoloVertice()).resolver();

		assertEquals(conjunto(1), resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Set<Integer> resultado = new SolverBacktracking(GrafosDePrueba.verticesAislados()).resolver();

		assertEquals(conjunto(1, 2, 3), resultado);
	}

	@Test
	public void grafoEstrellaEncuentraElCentroComoMinimo() {
		Set<Integer> resultado = new SolverBacktracking(GrafosDePrueba.estrella()).resolver();

		assertEquals(conjunto(1), resultado);
	}

	@Test
	public void encuentraConjuntoDominanteMinimo() {
		Grafo<Integer> grafo = GrafosDePrueba.camino();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertEquals(2, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEjemplo1EncuentraUnMinimoDeTresVertices() {
		Grafo<Integer> grafo = GrafosDePrueba.ejemplo1();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertEquals(3, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = GrafosDePrueba.desconectado();

		Set<Integer> resultado = new SolverBacktracking(grafo).resolver();

		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(5));
		verificarDominacion(grafo, resultado);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverBacktracking(null);
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
			if (!solucion.contains(vertice) && !intersecta(solucion, grafo.obtenerVecinos(vertice))) {
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
