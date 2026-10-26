package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class GrafoTest {

	private static Integer vertice1 = 1;
	private static Integer vertice2 = 2;
	private static Integer vertice3 = 3;
	private static Integer vertice4 = 4;

	public static class AgregarVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeNuloTest() {
			grafo.agregarVertice(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeYaExistenteTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice1);
		}
	}

	public static class AgregarVerticeCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void agregarVerticeTest() {
			grafo.agregarVertice(vertice1);
			assertTrue(grafo.existeVertice(vertice1));
		}
	}

	public static class AgregarAristaExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaAmbosVerticesNoExistenTest() {
			grafo.agregarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaVerticeNullTest() {
			grafo.agregarArista(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerExtremoNuloTest() {
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(null, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoExtremoNuloTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarArista(vertice1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarLoopTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarArista(vertice1, vertice1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaAlRevesTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice2, vertice1);
		}

	}

	public static class AgregarAristaCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void agregarAristaTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);

			assertTrue(grafo.existeArista(vertice1, vertice2));

		}
	}

	public static class EliminarAristaExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(vertice2);
			grafo.eliminarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(vertice1);
			grafo.eliminarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaAmbosVerticesNoExistenTest() {
			grafo.eliminarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaVerticeNuloTest() {
			grafo.agregarVertice(vertice1);
			grafo.eliminarArista(vertice1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaInexistenteTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.eliminarArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaDosVecesTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.eliminarArista(vertice1, vertice2);
			grafo.eliminarArista(vertice1, vertice2);
		}

		@Test
		public void eliminarAristaConVerticesInvertidosTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.eliminarArista(vertice2, vertice1);

			assertFalse(grafo.existeArista(vertice1, vertice2));
		}
	}

	public static class EliminarAristaCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void eliminarAristaTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.eliminarArista(vertice1, vertice2);

			assertFalse(grafo.existeArista(vertice1, vertice2));
		}

		@Test
		public void eliminarAristaLaSacaDeLaListaDeAristasTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.eliminarArista(vertice1, vertice2);

			assertEquals(0, grafo.obtenerAristas().size());
		}

		@Test
		public void eliminarAristaNoEliminaOtrasAristasTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);
			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice2, vertice3);
			grafo.eliminarArista(vertice1, vertice2);

			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void obtenerAristasGrafoVacioTest() {
			assertTrue(grafo.obtenerAristas().isEmpty());
		}

		@Test
		public void obtenerAristasDevuelveCopiaTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);
			grafo.obtenerAristas().clear();

			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void obtenerAristasCantidadTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);
			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice2, vertice3);

			assertEquals(2, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerVerticesExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesGrafoVacioTest() {
			assertTrue(grafo.obtenerVertices().isEmpty());
		}

		@Test
		public void obtenerVerticesDevuelveCopiaTest() {
			grafo.agregarVertice(vertice1);
			grafo.obtenerVertices().clear();

			assertTrue(grafo.existeVertice(vertice1));
		}
	}

	public static class ObtenerVerticesCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesTodosLosVerticesTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);

			assertEquals(3, grafo.obtenerVertices().size());
		}
	}

	public static class ExisteVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeVerticeNuloTest() {
			grafo.existeVertice(null);
		}

		@Test
		public void existeVerticeConOtroObjetoIntegerTest() {
			grafo.agregarVertice(vertice1);
			assertTrue(grafo.existeVertice(Integer.valueOf(1)));
		}
	}

	public static class ExisteVerticeCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void existeVerticeExistenteTest() {
			grafo.agregarVertice(vertice1);
			assertTrue(grafo.existeVertice(vertice1));
		}

		@Test
		public void existeVerticeInexistenteTest() {
			grafo.agregarVertice(vertice1);
			assertFalse(grafo.existeVertice(vertice2));
		}
	}

	public static class ExisteAristaExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(vertice2);
			grafo.existeArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(vertice1);
			grafo.existeArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaAmbosVerticesNoExistenTest() {
			grafo.existeArista(vertice1, vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaVerticeNuloTest() {
			grafo.agregarVertice(vertice1);
			grafo.existeArista(vertice1, null);
		}

		@Test
		public void existeAristaAlRevesTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice2, vertice1);

			assertTrue(grafo.existeArista(vertice1, vertice2));
		}

		@Test
		public void existeAristaConOtroObjetoYMismoNombreIntegerTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);

			assertTrue(grafo.existeArista(Integer.valueOf(1), Integer.valueOf(2)));
		}
	}

	public static class ExisteAristaCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void existeAristaExistenteTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);

			assertTrue(grafo.existeArista(vertice1, vertice2));
		}

		@Test
		public void existeAristaInexistenteTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);
			grafo.agregarArista(vertice1, vertice2);

			assertFalse(grafo.existeArista(vertice1, vertice3));
		}
	}

	public static class EsVacioExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void esVacioGrafoRecienCreadoTest() {
			assertTrue(grafo.esVacio());
		}

		@Test
		public void esVacioConUnVerticeAisladoTest() {
			grafo.agregarVertice(vertice1);
			assertFalse(grafo.esVacio());
		}
	}

	public static class EsVacioCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void esVacioConVerticesYAristasTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarArista(vertice1, vertice2);

			assertFalse(grafo.esVacio());
		}
	}

	public static class EliminarVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeInexistenteTest() {
			grafo.eliminarVertice(vertice1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeNullTest() {
			grafo.eliminarVertice(null);
		}
	}

	public static class EliminarVerticeCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void eliminarVerticeAisladoTest() {
			grafo.agregarVertice(vertice1);

			grafo.eliminarVertice(vertice1);

			assertFalse(grafo.existeVertice(vertice1));
		}

		@Test
		public void eliminarVerticeEliminaSusAristasTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);

			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice3, vertice1);

			grafo.eliminarVertice(vertice1);

			assertTrue(grafo.obtenerAristas().isEmpty());

		}

		@Test
		public void eliminarVerticeNoAfectaOtrasConexionesTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);

			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice2, vertice3);

			grafo.eliminarVertice(vertice1);

			assertTrue(grafo.existeArista(vertice2, vertice3));

		}
	}

	public static class ObtenerVecinosExcepcionesYCasosBorde {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeNullTest() {
			grafo.obtenerVecinos(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeInexistenteTest() {
			grafo.agregarVertice(vertice1);
			grafo.obtenerVecinos(vertice2);
		}

		@Test
		public void obtenerVecinosVerticeAisladoTest() {
			grafo.agregarVertice(vertice1);

			assertTrue(grafo.obtenerVecinos(vertice1).isEmpty());
		}
	}

	public static class ObtenerVecinosCasoFeliz {

		private Grafo<Integer> grafo = new Grafo<>();

		@Test
		public void obtenerVecinosTest() {
			grafo.agregarVertice(vertice1);
			grafo.agregarVertice(vertice2);
			grafo.agregarVertice(vertice3);
			grafo.agregarVertice(vertice4);
			grafo.agregarArista(vertice1, vertice2);
			grafo.agregarArista(vertice3, vertice1);

			Set<Integer> vecinos = grafo.obtenerVecinos(vertice1);

			assertEquals(new HashSet<>(Arrays.asList(vertice2, vertice3)), vecinos);
		}
	}
}
