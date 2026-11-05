package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class SolverGolosoTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Grafo<Integer> grafo = GrafosDePrueba.vacio();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertTrue(resultado.isEmpty());
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Grafo<Integer> grafo = GrafosDePrueba.unSoloVertice();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(Verificaciones.conjunto(1), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Grafo<Integer> grafo = GrafosDePrueba.verticesAislados();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(Verificaciones.conjunto(1, 2, 3), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEstrellaEligeElCentro() {
		Grafo<Integer> grafo = GrafosDePrueba.estrella();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(Verificaciones.conjunto(1), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEjemplo1DevuelveUnoDosYSeis() {
		Grafo<Integer> grafo = GrafosDePrueba.ejemplo1();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(Verificaciones.conjunto(1, 2, 6), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = GrafosDePrueba.desconectado();

		Set<Integer> resultado = new SolverGoloso(grafo).resolver();

		assertEquals(3, resultado.size());
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void aporteCuentaSoloVerticesNoDominados() {
		Grafo<Integer> grafo = GrafosDePrueba.tresVerticesEnLinea();

		SolverGoloso solver = new SolverGoloso(grafo);

		assertEquals(1, solver.calcularAporte(1, Verificaciones.conjunto(1, 2)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaGrafoNulo() {
		new SolverGoloso(null);
	}
}
