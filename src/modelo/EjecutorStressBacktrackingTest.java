package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class EjecutorStressBacktrackingTest {

	@Test
	public void correHastaQueSeCancela() {
		List<ResultadoStress> resultados = ejecutarInstancias(4, 3);

		assertEquals(3, resultados.size());
	}

	@Test
	public void elTamanioCreceDeAUnVertice() {
		List<ResultadoStress> resultados = ejecutarInstancias(4, 3);

		assertEquals(4, resultados.get(0).obtenerCantidadVertices());
		assertEquals(5, resultados.get(1).obtenerCantidadVertices());
		assertEquals(6, resultados.get(2).obtenerCantidadVertices());
	}

	@Test
	public void numeraLasInstanciasDesdeUno() {
		List<ResultadoStress> resultados = ejecutarInstancias(4, 3);

		assertEquals(1, resultados.get(0).obtenerNumeroInstancia());
		assertEquals(3, resultados.get(2).obtenerNumeroInstancia());
	}

	@Test
	public void informaDatosCoherentesDeCadaInstancia() {
		List<ResultadoStress> resultados = ejecutarInstancias(4, 5);

		for (ResultadoStress resultado : resultados) {
			int vertices = resultado.obtenerCantidadVertices();
			int maximoDeAristas = vertices * (vertices - 1) / 2;

			assertTrue(resultado.obtenerCantidadAristas() <= maximoDeAristas);
			assertTrue(resultado.obtenerTamanioSolucion() >= 1);
			assertTrue(resultado.obtenerTamanioSolucion() <= vertices);
			assertTrue(resultado.obtenerTiempoNano() >= 0);
		}
	}

	@Test
	public void canceladoDesdeElInicioNoEjecutaNada() {
		EjecutorStressBacktracking stress = new EjecutorStressBacktracking(4);

		assertTrue(stress.ejecutar(new AtomicBoolean(true), null).isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaTamanioInicialInvalido() {
		new EjecutorStressBacktracking(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaSenialDeCancelacionNula() {
		new EjecutorStressBacktracking(4).ejecutar(null, null);
	}

	// Corre el stress y lo cancela desde el observador al llegar a la última instancia pedida.
	private List<ResultadoStress> ejecutarInstancias(int tamanioInicial, int cantidadInstancias) {
		AtomicBoolean cancelado = new AtomicBoolean(false);
		EjecutorStressBacktracking stress = new EjecutorStressBacktracking(tamanioInicial);

		return stress.ejecutar(cancelado, resultado -> {
			if (resultado.obtenerNumeroInstancia() == cantidadInstancias) {
				cancelado.set(true);
			}
		});
	}
}
