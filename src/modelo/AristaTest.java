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

	private static String extremo1 = "Buenos Aires";
	private static String extremo2 = "Santa Fé";
	private static String extremo3 = "Rio Negro";
	private static String extremo4 = "Santa Cruz";

	static Arista<String> arista12YPeso100 = new Arista<>(extremo1, extremo2, 100);
	static Arista<String> arista34YPeso100 = new Arista<>(extremo3, extremo4, 100);
	static Arista<String> arista34YPeso50 = new Arista<>(extremo3, extremo4, 50);

	public static class ConstructorExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo1NuloTest() {
			new Arista<>(null, extremo2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConExtremo2NuloTest() {
			new Arista<>(extremo1, null, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConAmbosExtremosNulosTest() {
			new Arista<>(null, null, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConLoopTest() {
			new Arista<>(extremo1, extremo1, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConPesoCeroTest() {
			new Arista<>(extremo1, extremo2, 0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void noPermiteCrearAristaConPesoNegativoTest() {
			new Arista<>(extremo1, extremo2, -100);
		}
	}

	public static class GettersCasoFeliz {

		@Test
		public void devolverPesoTest() {
			assertEquals(100, arista12YPeso100.devolverPeso());
		}

		@Test
		public void obtenerExtremo1Test() {
			assertEquals(extremo1, arista12YPeso100.obtenerExtremo1());
		}

		@Test
		public void obtenerExtremo2Test() {
			assertEquals(extremo2, arista12YPeso100.obtenerExtremo2());
		}
	}

	public static class CompareToExcepcionesYCasosBorde {

		@Test(expected = IllegalArgumentException.class)
		public void compareToAristaNulaTest() {
			arista12YPeso100.compareTo(null);
		}

		@Test
		public void compareToMismoPesoTest() {
			assertEquals(0, arista12YPeso100.compareTo(arista12YPeso100));
		}
	}

	public static class CompareToCasoFeliz {

		@Test
		public void compareToMenorPesoTest() {
			assertEquals(1, arista12YPeso100.compareTo(arista34YPeso50));
		}

		@Test
		public void compareToMayorPesoTest() {
			assertEquals(-1, arista34YPeso50.compareTo(arista12YPeso100));
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
			Arista<String> arista12YPeso100AlReves = new Arista<>(extremo2, extremo1, 100);

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
			Arista<String> otraArista12YPeso100 = new Arista<>(extremo1, extremo2, 100);

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
			Arista<String> arista1 = new Arista<>("A", "B", 4);
			Arista<String> arista2 = new Arista<>("B", "A", 4);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}
	}

	public static class HashCodeCasoFeliz {

		@Test
		public void hashCodeAristasIgualesTest() {
			Arista<String> arista1 = new Arista<>("A", "B", 4);
			Arista<String> arista2 = new Arista<>("A", "B", 9);

			assertEquals(arista1.hashCode(), arista2.hashCode());
		}

		@Test
		public void hashCodeContainsEnHashSetTest() {
			HashSet<Arista<String>> aristas = new HashSet<>();

			Arista<String> arista1 = new Arista<>("A", "B", 4);
			Arista<String> arista2 = new Arista<>("B", "A", 9);

			aristas.add(arista1);

			assertTrue(aristas.contains(arista2));
		}

		@Test
		public void hashCodeNoDuplicaEnHashSetTest() {
			HashSet<Arista<String>> aristas = new HashSet<>();

			Arista<String> arista1 = new Arista<>("A", "B", 4);
			Arista<String> arista2 = new Arista<>("B", "A", 9);

			aristas.add(arista1);
			aristas.add(arista2);

			assertEquals(1, aristas.size());
		}
	}

}
