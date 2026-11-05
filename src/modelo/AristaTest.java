package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class AristaTest {

	private static Integer _extremo1 = 1;
	private static Integer _extremo2 = 2;
	private static Integer _extremo3 = 3;
	private static Integer _extremo4 = 4;

	static Arista<Integer> _arista12 = new Arista<>(_extremo1, _extremo2);
	static Arista<Integer> _arista34 = new Arista<>(_extremo3, _extremo4);
	static Arista<Integer> _otraArista12 = new Arista<>(_extremo1, _extremo2);
	static Arista<Integer> _arista12AlReves = new Arista<>(_extremo2, _extremo1);

	public static class ConstructorExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo1NuloTest() {
			new Arista<>(null, _extremo2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo2NuloTest() {
			new Arista<>(_extremo1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConAmbosExtremosNulosTest() {
			new Arista<>(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConLoopTest() {
			new Arista<>(_extremo1, _extremo1);
		}

	}

	public static class GettersCasoFeliz {

		@Test
		public void obtenerExtremo1Test() {
			assertEquals(_extremo1, _arista12.obtenerExtremo1());
		}

		@Test
		public void obtenerExtremo2Test() {
			assertEquals(_extremo2, _arista12.obtenerExtremo2());
		}
	}

	public static class EqualsExcepcionesYCasosBorde {

		@Test
		public void equalsNuloTest() {
			assertFalse(_arista12.equals(null));
		}

		@Test
		public void equalsMismoObjetoTest() {
			assertTrue(_arista12.equals(_arista12));
		}

		@Test
		public void equalsSimetricoTest() {

			boolean simetrico = _arista12.equals(_arista12AlReves)
					&& _arista12AlReves.equals(_arista12);

			assertTrue(simetrico);
		}
	}

	public static class EqualsCasoFeliz {

		@Test
		public void equalsMismosExtremosTest() {

			assertTrue(_arista12.equals(_otraArista12));
		}

		@Test
		public void equalsDistintosExtremosTest() {
			assertFalse(_arista12.equals(_arista34));
		}
	}

	public static class HashCodeExcepcionesYCasosBorde {

		@Test
		public void hashCodeExtremosAlRevesTest() {
			Arista<Integer> arista1 = new Arista<>(_extremo1, _extremo2);
			Arista<Integer> arista2 = new Arista<>(_extremo2, _extremo1);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}
	}

	public static class HashCodeCasoFeliz {

		@Test
		public void hashCodeAristasIgualesTest() {
			Arista<Integer> arista1 = new Arista<>(_extremo1, _extremo2);
			Arista<Integer> arista2 = new Arista<>(_extremo1, _extremo2);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}

		@Test
		public void hashCodeContainsEnHashSetTest() {
			HashSet<Arista<Integer>> aristas = new HashSet<>();

			Arista<Integer> arista1 = new Arista<>(_extremo1, _extremo2);
			Arista<Integer> arista2 = new Arista<>(_extremo2, _extremo1);

			aristas.add(arista1);

			assertTrue(aristas.contains(arista2));
		}

		@Test
		public void hashCodeNoDuplicaEnHashSetTest() {
			HashSet<Arista<Integer>> aristas = new HashSet<>();

			Arista<Integer> arista1 = new Arista<>(_extremo1, _extremo2);
			Arista<Integer> arista2 = new Arista<>(_extremo2, _extremo1);

			aristas.add(arista1);
			aristas.add(arista2);

			assertEquals(1, aristas.size());
		}
	}

}
