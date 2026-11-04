package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class SolverGolosoTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Grafo<Integer> grafo = GrafosDePrueba.vacio();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertTrue(resultado.isEmpty());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Grafo<Integer> grafo = GrafosDePrueba.unSoloVertice();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Grafo<Integer> grafo = GrafosDePrueba.verticesAislados();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1, 2, 3), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEstrellaEligeElCentro() {
		Grafo<Integer> grafo = GrafosDePrueba.estrella();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEjemplo1DevuelveUnoDosYSeis() {
		Grafo<Integer> grafo = GrafosDePrueba.ejemplo1();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1, 2, 6), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = GrafosDePrueba.desconectado();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(3, resultado.size());
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void aporteCuentaSoloVerticesNoDominados() {
		Grafo<Integer> grafo = GrafosDePrueba.tresVerticesEnLinea();

		SolverGoloso solver = new SolverGoloso(grafo);

		assertEquals(1, solver.aporte(1, conjunto(1, 2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverGoloso(null);
	}

	private Set<Integer> conjunto(Integer... vertices) {
		return new HashSet<>(Arrays.asList(vertices));
	}

	private void verificarDominacion(Grafo<Integer> grafo, Set<Integer> solucion) {

		for (Integer elegido : solucion) {
			assertTrue("La solución contiene un vértice ajeno al grafo.", grafo.existeVertice(elegido));
		}

		for (Integer vertice : grafo.obtenerVertices()) {
			boolean cubierto = solucion.contains(vertice);

			for (Integer vecino : grafo.obtenerVecinos(vertice)) {
				if (solucion.contains(vecino)) {
					cubierto = true;
					break;
				}
			}

			assertTrue("El vértice " + vertice + " no quedó dominado.", cubierto);
		}
	}
}
