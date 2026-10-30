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
		Grafo<Integer> grafo = new Grafo<>();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertTrue(resultado.isEmpty());
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Grafo<Integer> grafo = new Grafo<>();
		grafo.agregarVertice(1);

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1), resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Grafo<Integer> grafo = new Grafo<>();

		grafo.agregarVertice(1);
		grafo.agregarVertice(2);
		grafo.agregarVertice(3);

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1, 2, 3), resultado);
	}

	@Test
	public void grafoEstrellaEligeElCentro() {
		Grafo<Integer> grafo = new Grafo<>();

		for (int i = 1; i <= 5; i++) {
			grafo.agregarVertice(i);
		}

		for (int i = 2; i <= 5; i++) {
			grafo.agregarArista(1, i);
		}

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1), resultado);
	}

	@Test
	public void grafoEjemplo1DevuelveUnoDosYSeis() {
		Grafo<Integer> grafo = crearGrafoEjemplo1();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(conjunto(1, 2, 6), resultado);
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = new Grafo<>();

		for (int i = 1; i <= 5; i++) {
			grafo.agregarVertice(i);
		}

		grafo.agregarArista(1, 2);
		grafo.agregarArista(3, 4);
		// El vértice 5 queda aislado.

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(5));
		verificarDominacion(grafo, resultado);
	}

	@Test
	public void aporteCuentaSoloVerticesNoDominados() {
		Grafo<Integer> grafo = new Grafo<>();

		grafo.agregarVertice(1);
		grafo.agregarVertice(2);
		grafo.agregarVertice(3);

		grafo.agregarArista(1, 2);
		grafo.agregarArista(1, 3);

		SolverGoloso solver = new SolverGoloso(grafo);

		// Ya están dominados 1 y 2: solo queda por cubrir 3.
		assertEquals(1, solver.aporte(1, conjunto(1, 2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverGoloso(null);
	}

	private Grafo<Integer> crearGrafoEjemplo1() {
		Grafo<Integer> grafo = new Grafo<>();

		for (int i = 1; i <= 8; i++) {
			grafo.agregarVertice(i);
		}

		grafo.agregarArista(1, 2);
		grafo.agregarArista(1, 3);
		grafo.agregarArista(1, 8);
		grafo.agregarArista(2, 8);
		grafo.agregarArista(2, 4);
		grafo.agregarArista(3, 5);
		grafo.agregarArista(5, 6);
		grafo.agregarArista(6, 7);
		grafo.agregarArista(7, 8);

		return grafo;
	}

	private Set<Integer> conjunto(Integer... vertices) {
		return new HashSet<>(Arrays.asList(vertices));
	}

	private void verificarDominacion(Grafo<Integer> grafo, Set<Integer> solucion) {

		// Comprueba que todos los elegidos pertenezcan al grafo.
		for (Integer elegido : solucion) {
			assertTrue("La solución contiene un vértice ajeno al grafo.", grafo.existeVertice(elegido));
		}

		// Comprueba que cada vértice esté elegido
		// o tenga un vecino elegido.
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