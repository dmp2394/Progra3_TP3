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

	private static String provincia1 = "Buenos Aires";
	private static String provincia2 = "Santa Fé";
	private static String provincia3 = "La Pampa";
	private static String provincia4 = "Entre Ríos";

	public static class AgregarVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeNuloTest() {
			grafo.agregarVertice(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeYaExistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia1);
		}
	}

	public static class AgregarVerticeCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void agregarVerticeTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(provincia1));
		}
	}

	public static class AgregarAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaAmbosVerticesNoExistenTest() {
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaVerticeNullTest() {
			grafo.agregarArista(null, null, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerExtremoNuloTest() {
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(null, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoExtremoNuloTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarArista(provincia1, null, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarLoopTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarArista(provincia1, provincia1, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia1, provincia2, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaAlRevesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia1, 100);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPesoCeroTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			grafo.agregarArista(provincia1, provincia2, 0);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPesoNegativoTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);

			grafo.agregarArista(provincia1, provincia2, -100);
		}
	}

	public static class AgregarAristaCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void agregarAristaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));

		}
	}

	public static class EliminarAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaAmbosVerticesNoExistenTest() {
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaVerticeNuloTest() {
			grafo.agregarVertice(provincia1);
			grafo.eliminarArista(provincia1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaInexistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaDosVecesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);
			grafo.eliminarArista(provincia1, provincia2);
		}

		@Test
		public void eliminarAristaConVerticesInvertidosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia2, provincia1);

			assertFalse(grafo.existeArista(provincia1, provincia2));
		}
	}

	public static class EliminarAristaCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void eliminarAristaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);

			assertFalse(grafo.existeArista(provincia1, provincia2));
		}

		@Test
		public void eliminarAristaLaSacaDeLaListaDeAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.eliminarArista(provincia1, provincia2);

			assertEquals(0, grafo.obtenerAristas().size());
		}

		@Test
		public void eliminarAristaNoEliminaOtrasAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia3, 50);
			grafo.eliminarArista(provincia1, provincia2);

			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerAristasGrafoVacioTest() {
			assertTrue(grafo.obtenerAristas().isEmpty());
		}

		@Test
		public void obtenerAristasDevuelveCopiaTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.obtenerAristas().clear();

			assertEquals(1, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerAristasCantidadTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);
			grafo.agregarArista(provincia2, provincia3, 50);

			assertEquals(2, grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerVerticesExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesGrafoVacioTest() {
			assertTrue(grafo.obtenerVertices().isEmpty());
		}

		@Test
		public void obtenerVerticesDevuelveCopiaTest() {
			grafo.agregarVertice(provincia1);
			grafo.obtenerVertices().clear();

			assertTrue(grafo.existeVertice(provincia1));
		}
	}

	public static class ObtenerVerticesCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerVerticesTodosLosVerticesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			assertEquals(3, grafo.obtenerVertices().size());
		}
	}

	public static class ExisteVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeVerticeNuloTest() {
			grafo.existeVertice(null);
		}

		@Test
		public void existeVerticeConOtroObjetoStringTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(new String("Buenos Aires")));
		}
	}

	public static class ExisteVerticeCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void existeVerticeExistenteTest() {
			grafo.agregarVertice(provincia1);
			assertTrue(grafo.existeVertice(provincia1));
		}

		@Test
		public void existeVerticeInexistenteTest() {
			grafo.agregarVertice(provincia1);
			assertFalse(grafo.existeVertice(provincia2));
		}
	}

	public static class ExisteAristaExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaPrimerVerticeNoExisteTest() {
			grafo.agregarVertice(provincia2);
			grafo.existeArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaSegundoVerticeNoExisteTest() {
			grafo.agregarVertice(provincia1);
			grafo.existeArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaAmbosVerticesNoExistenTest() {
			grafo.existeArista(provincia1, provincia2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaVerticeNuloTest() {
			grafo.agregarVertice(provincia1);
			grafo.existeArista(provincia1, null);
		}

		@Test
		public void existeAristaAlRevesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia2, provincia1, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));
		}

		@Test
		public void existeAristaConOtroObjetoYMismoNombreStringTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(new String("Buenos Aires"), new String("Santa Fé")));
		}
	}

	public static class ExisteAristaCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void existeAristaExistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertTrue(grafo.existeArista(provincia1, provincia2));
		}

		@Test
		public void existeAristaInexistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertFalse(grafo.existeArista(provincia1, provincia3));
		}
	}

	public static class EsVacioExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void esVacioGrafoRecienCreadoTest() {
			assertTrue(grafo.esVacio());
		}

		@Test
		public void esVacioConUnVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);
			assertFalse(grafo.esVacio());
		}
	}

	public static class EsVacioCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void esVacioConVerticesYAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarArista(provincia1, provincia2, 100);

			assertFalse(grafo.esVacio());
		}
	}

	public static class PesoTotalExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void pesoTotalGrafoVacioTest() {
			assertEquals(0, grafo.pesoTotal());
		}
	}

	public static class PesoTotalCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void pesoTotalSumaLosPesosDeLasAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarArista(provincia1, provincia2, 30);
			grafo.agregarArista(provincia2, provincia3, 10);
			grafo.agregarArista(provincia1, provincia3, 20);

			assertEquals(60, grafo.pesoTotal());
		}
	}

	public static class EliminarVerticeExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeInexistenteTest() {
			grafo.eliminarVertice(provincia1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeNullTest() {
			grafo.eliminarVertice(null);
		}
	}

	public static class EliminarVerticeCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void eliminarVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);

			grafo.eliminarVertice(provincia1);

			assertFalse(grafo.existeVertice(provincia1));
		}

		@Test
		public void eliminarVerticeEliminaSusAristasTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			grafo.agregarArista(provincia1, provincia2, 4);
			grafo.agregarArista(provincia3, provincia1, 7);

			grafo.eliminarVertice(provincia1);

			assertTrue(grafo.obtenerAristas().isEmpty());

		}

		@Test
		public void eliminarVerticeNoAfectaOtrasConexionesTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);

			grafo.agregarArista(provincia1, provincia2, 4);
			grafo.agregarArista(provincia2, provincia3, 8);

			grafo.eliminarVertice(provincia1);

			assertTrue(grafo.existeArista(provincia2, provincia3));

		}
	}

	public static class ObtenerVecinosExcepcionesYCasosBorde {

		private Grafo<String> grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeNullTest() {
			grafo.obtenerVecinos(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeInexistenteTest() {
			grafo.agregarVertice(provincia1);
			grafo.obtenerVecinos(provincia2);
		}

		@Test
		public void obtenerVecinosVerticeAisladoTest() {
			grafo.agregarVertice(provincia1);

			assertTrue(grafo.obtenerVecinos(provincia1).isEmpty());
		}
	}

	public static class ObtenerVecinosCasoFeliz {

		private Grafo<String> grafo = new Grafo<>();

		@Test
		public void obtenerVecinosTest() {
			grafo.agregarVertice(provincia1);
			grafo.agregarVertice(provincia2);
			grafo.agregarVertice(provincia3);
			grafo.agregarVertice(provincia4);
			grafo.agregarArista(provincia1, provincia2, 10);
			grafo.agregarArista(provincia3, provincia1, 20);

			Set<String> vecinos = grafo.obtenerVecinos(provincia1);

			assertEquals(new HashSet<>(Arrays.asList(provincia2, provincia3)), vecinos);
		}
	}
}
