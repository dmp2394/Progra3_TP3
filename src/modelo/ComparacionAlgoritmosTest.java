package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

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
}
