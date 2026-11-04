package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class SolverBacktrackingTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Grafo<Integer> grafo = GrafosDePrueba.vacio();

		Set<Integer> resultado = resolver(grafo);

		assertTrue(resultado.isEmpty());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Grafo<Integer> grafo = GrafosDePrueba.unSoloVertice();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(conjunto(1), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Grafo<Integer> grafo = GrafosDePrueba.verticesAislados();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(conjunto(1, 2, 3), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEstrellaEncuentraElCentroComoMinimo() {
		Grafo<Integer> grafo = GrafosDePrueba.estrella();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(conjunto(1), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void encuentraConjuntoDominanteMinimo() {
		Grafo<Integer> grafo = GrafosDePrueba.camino();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(2, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEjemplo1EncuentraUnMinimoDeTresVertices() {
		Grafo<Integer> grafo = GrafosDePrueba.ejemplo1();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(3, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = GrafosDePrueba.desconectado();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(5));
		verificarDominacion(grafo, resultado);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverBacktracking(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaSenialDeCancelacionNula() {
		new SolverBacktracking(GrafosDePrueba.estrella()).resolver(null);
	}

	@Test
	public void canceladoDesdeElInicioNoEncuentraSolucion() {
		AtomicBoolean cancelado = new AtomicBoolean(true);

		Set<Integer> resultado = new SolverBacktracking(GrafosDePrueba.ejemplo1()).resolver(cancelado);

		assertTrue(resultado.isEmpty());
	}

	private Set<Integer> resolver(Grafo<Integer> grafo) {
		return new SolverBacktracking(grafo).resolver(new AtomicBoolean(false));
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
