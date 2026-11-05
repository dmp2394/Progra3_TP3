package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class SolverBacktrackingTest {

	@Test
	public void grafoVacioDevuelveConjuntoVacio() {
		Grafo<Integer> grafo = GrafosDePrueba.vacio();

		Set<Integer> resultado = resolver(grafo);

		assertTrue(resultado.isEmpty());
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void unSoloVerticeDebeSerElegido() {
		Grafo<Integer> grafo = GrafosDePrueba.unSoloVertice();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(Verificaciones.conjunto(1), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void verticesAisladosDebenSerElegidosTodos() {
		Grafo<Integer> grafo = GrafosDePrueba.verticesAislados();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(Verificaciones.conjunto(1, 2, 3), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEstrellaEncuentraElCentroComoMinimo() {
		Grafo<Integer> grafo = GrafosDePrueba.estrella();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(Verificaciones.conjunto(1), resultado);
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void encuentraConjuntoDominanteMinimo() {
		Grafo<Integer> grafo = GrafosDePrueba.camino();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(2, resultado.size());
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoEjemplo1EncuentraUnMinimoDeTresVertices() {
		Grafo<Integer> grafo = GrafosDePrueba.ejemplo1();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(3, resultado.size());
		Verificaciones.verificarDominacion(grafo, resultado);
	}

	@Test
	public void grafoDesconectadoCubreTodasLasComponentes() {
		Grafo<Integer> grafo = GrafosDePrueba.desconectado();

		Set<Integer> resultado = resolver(grafo);

		assertEquals(3, resultado.size());
		assertTrue(resultado.contains(5));
		Verificaciones.verificarDominacion(grafo, resultado);
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
}
