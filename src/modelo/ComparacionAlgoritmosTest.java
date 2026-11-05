package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class ComparacionAlgoritmosTest {

	@Test
	public void comparaAmbosAlgoritmosSobreElMismoGrafo() {
		Aplicacion aplicacion = new Aplicacion();
		aplicacion.cargarGrafo(
			Arrays.asList(1, 2, 3),
			Arrays.asList(new Arista<>(1, 2), new Arista<>(2, 3)));

		ResultadoComparacion resultado = aplicacion.compararAlgoritmos();

		assertTrue(resultado.obtenerSolucionBacktracking().contains(2));
		assertEquals(1, resultado.obtenerSolucionBacktracking().size());
		assertEquals(1, resultado.obtenerTamanioGrafoBacktracking());
		assertTrue(resultado.obtenerTiempoGolosoNano() >= 0);
		assertTrue(resultado.obtenerTiempoBacktrackingNano() >= 0);
		assertTrue(resultado.obtenerResumen().contains("Backtracking"));
	}

	@Test
	public void backtrackingEncuentraElMinimoDondeElGolosoNoLoLogra() {
		Grafo<Integer> grafo = GrafosDePrueba.caminoDondeElGolosoNoEsOptimo();

		Set<Integer> solucionGolosa = new SolverGoloso(grafo).resolver();
		Set<Integer> solucionBacktracking = new SolverBacktracking(grafo).resolver(new AtomicBoolean(false));

		assertEquals(Verificaciones.conjunto(1, 2, 3), solucionGolosa);
		assertEquals(2, solucionBacktracking.size());
		Verificaciones.verificarDominacion(grafo, solucionGolosa);
		Verificaciones.verificarDominacion(grafo, solucionBacktracking);
	}
}
