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

	private static Integer extremo1 = 1;
	private static Integer extremo2 = 2;
	private static Integer extremo3 = 3;
	private static Integer extremo4 = 4;

	static Arista<Integer> arista12YPeso100 = new Arista<>(extremo1, extremo2);
	static Arista<Integer> arista34YPeso100 = new Arista<>(extremo3, extremo4);
	static Arista<Integer> arista34YPeso50 = new Arista<>(extremo3, extremo4);
	static Arista<Integer> otraArista12YPeso100 = new Arista<>(extremo1, extremo2);
	static Arista<Integer> arista12YPeso100AlReves = new Arista<>(extremo2, extremo1);

	public static class ConstructorExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo1NuloTest() {
			new Arista<>(null, extremo2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo2NuloTest() {
			new Arista<>(extremo1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConAmbosExtremosNulosTest() {
			new Arista<>(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConLoopTest() {
			new Arista<>(extremo1, extremo1);
		}

	}

	public static class GettersCasoFeliz {

		@Test
		public void obtenerExtremo1Test() {
			assertEquals(extremo1, arista12YPeso100.obtenerExtremo1());
		}

		@Test
		public void obtenerExtremo2Test() {
			assertEquals(extremo2, arista12YPeso100.obtenerExtremo2());
		}
	}

	public static class EqualsExcepcionesYCasosBorde {

		@Test
		public void equalsNuloTest() {
			assertFalse(arista12YPeso100.equals(null));
		}

		@Test
		public void equalsMismoObjetoTest() {
			assertTrue(arista12YPeso100.equals(arista12YPeso100));
		}

		@Test
		public void equalsSimetricoTest() {

			boolean simetrico = arista12YPeso100.equals(arista12YPeso100AlReves)
					&& arista12YPeso100AlReves.equals(arista12YPeso100);

			assertTrue(simetrico);
		}

		@Test
		public void equalsMismosExtremosDistintoPesoTest() {
			assertTrue(arista34YPeso100.equals(arista34YPeso50));
		}
	}

	public static class EqualsCasoFeliz {

		@Test
		public void equalsMismosExtremosTest() {

			assertTrue(arista12YPeso100.equals(otraArista12YPeso100));
		}

		@Test
		public void equalsDistintosExtremosTest() {
			assertFalse(arista12YPeso100.equals(arista34YPeso100));
		}
	}

	public static class HashCodeExcepcionesYCasosBorde {

		@Test
		public void hashCodeExtremosAlRevesTest() {
			Arista<Integer> arista1 = new Arista<>(extremo1, extremo2);
			Arista<Integer> arista2 = new Arista<>(extremo2, extremo1);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}
	}

	public static class HashCodeCasoFeliz {

		@Test
		public void hashCodeAristasIgualesTest() {
			Arista<Integer> arista1 = new Arista<>(extremo1, extremo2);
			Arista<Integer> arista2 = new Arista<>(extremo1, extremo2);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}

		@Test
		public void hashCodeContainsEnHashSetTest() {
			HashSet<Arista<Integer>> aristas = new HashSet<>();

			Arista<Integer> arista1 = new Arista<>(extremo1, extremo2);
			Arista<Integer> arista2 = new Arista<>(extremo2, extremo1);

			aristas.add(arista1);

			assertTrue(aristas.contains(arista2));
		}

		@Test
		public void hashCodeNoDuplicaEnHashSetTest() {
			HashSet<Arista<Integer>> aristas = new HashSet<>();

			Arista<Integer> arista1 = new Arista<>(extremo1, extremo2);
			Arista<Integer> arista2 = new Arista<>(extremo2, extremo1);

			aristas.add(arista1);
			aristas.add(arista2);

			assertEquals(1, aristas.size());
		}
	}

}
