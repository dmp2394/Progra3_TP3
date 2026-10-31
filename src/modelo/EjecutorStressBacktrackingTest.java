package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Test;

public class EjecutorStressBacktrackingTest {

	@Test
	public void ejecutaInstanciasAleatorias() {
		EjecutorStressBacktracking stress =
			new EjecutorStressBacktracking(3, 6);

		assertEquals(3, stress.ejecutar(new AtomicBoolean(false), null).size());
	}

	@Test
	public void puedeCancelarElStress() {
		AtomicBoolean cancelado = new AtomicBoolean(true);
		EjecutorStressBacktracking stress =
			new EjecutorStressBacktracking(3, 6);

		assertTrue(stress.ejecutar(cancelado, null).isEmpty());
	}

	@Test(expected = IllegalArgumentException.class)
	public void rechazaConfiguracionesInvalidas() {
		new EjecutorStressBacktracking(0, 6);
	}
}
