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

	private static Integer _vertice1 = 1;
	private static Integer _vertice2 = 2;
	private static Integer _vertice3 = 3;
	private static Integer _vertice4 = 4;

	public static class AgregarVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeNuloTest() {
			_grafo.agregarVertice(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarVerticeYaExistenteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice1);
		}
	}

	public static class AgregarVerticeCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void agregarVerticeTest() {
			_grafo.agregarVertice(_vertice1);
			assertTrue(_grafo.existeVertice(_vertice1));
		}
	}

	public static class AgregarAristaExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaAmbosVerticesNoExistenTest() {
			_grafo.agregarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaVerticeNullTest() {
			_grafo.agregarArista(null, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaPrimerExtremoNuloTest() {
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(null, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaSegundoExtremoNuloTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarArista(_vertice1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarLoopTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarArista(_vertice1, _vertice1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void agregarAristaDuplicadaAlRevesTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice2, _vertice1);
		}

	}

	public static class AgregarAristaCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void agregarAristaTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);

			assertTrue(_grafo.existeArista(_vertice1, _vertice2));

		}
	}

	public static class EliminarAristaExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaPrimerVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaSegundoVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.eliminarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaAmbosVerticesNoExistenTest() {
			_grafo.eliminarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaVerticeNuloTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.eliminarArista(_vertice1, null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaInexistenteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarAristaDosVecesTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);
		}

		@Test
		public void eliminarAristaConVerticesInvertidosTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.eliminarArista(_vertice2, _vertice1);

			assertFalse(_grafo.existeArista(_vertice1, _vertice2));
		}
	}

	public static class EliminarAristaCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void eliminarAristaTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);

			assertFalse(_grafo.existeArista(_vertice1, _vertice2));
		}

		@Test
		public void eliminarAristaLaSacaDeLaListaDeAristasTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.eliminarArista(_vertice1, _vertice2);

			assertEquals(0, _grafo.obtenerAristas().size());
		}

		@Test
		public void eliminarAristaNoEliminaOtrasAristasTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice2, _vertice3);
			_grafo.eliminarArista(_vertice1, _vertice2);

			assertEquals(1, _grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void obtenerAristasGrafoVacioTest() {
			assertTrue(_grafo.obtenerAristas().isEmpty());
		}

		@Test
		public void obtenerAristasDevuelveCopiaTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.obtenerAristas().clear();

			assertEquals(1, _grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerAristasCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void obtenerAristasCantidadTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice2, _vertice3);

			assertEquals(2, _grafo.obtenerAristas().size());
		}
	}

	public static class ObtenerVerticesExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void obtenerVerticesGrafoVacioTest() {
			assertTrue(_grafo.obtenerVertices().isEmpty());
		}

		@Test
		public void obtenerVerticesDevuelveCopiaTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.obtenerVertices().clear();

			assertTrue(_grafo.existeVertice(_vertice1));
		}
	}

	public static class ObtenerVerticesCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void obtenerVerticesTodosLosVerticesTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);

			assertEquals(3, _grafo.obtenerVertices().size());
		}
	}

	public static class ExisteVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeVerticeNuloTest() {
			_grafo.existeVertice(null);
		}

		@Test
		public void existeVerticeConOtroObjetoIntegerTest() {
			_grafo.agregarVertice(_vertice1);
			assertTrue(_grafo.existeVertice(Integer.valueOf(1)));
		}
	}

	public static class ExisteVerticeCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void existeVerticeExistenteTest() {
			_grafo.agregarVertice(_vertice1);
			assertTrue(_grafo.existeVertice(_vertice1));
		}

		@Test
		public void existeVerticeInexistenteTest() {
			_grafo.agregarVertice(_vertice1);
			assertFalse(_grafo.existeVertice(_vertice2));
		}
	}

	public static class ExisteAristaExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaPrimerVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice2);
			_grafo.existeArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaSegundoVerticeNoExisteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.existeArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaAmbosVerticesNoExistenTest() {
			_grafo.existeArista(_vertice1, _vertice2);
		}

		@Test(expected = IllegalArgumentException.class)
		public void existeAristaVerticeNuloTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.existeArista(_vertice1, null);
		}

		@Test
		public void existeAristaAlRevesTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice2, _vertice1);

			assertTrue(_grafo.existeArista(_vertice1, _vertice2));
		}

		@Test
		public void existeAristaConOtroObjetoYMismoNombreIntegerTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);

			assertTrue(_grafo.existeArista(Integer.valueOf(1), Integer.valueOf(2)));
		}
	}

	public static class ExisteAristaCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void existeAristaExistenteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);

			assertTrue(_grafo.existeArista(_vertice1, _vertice2));
		}

		@Test
		public void existeAristaInexistenteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);
			_grafo.agregarArista(_vertice1, _vertice2);

			assertFalse(_grafo.existeArista(_vertice1, _vertice3));
		}
	}

	public static class EsVacioExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void esVacioGrafoRecienCreadoTest() {
			assertTrue(_grafo.esVacio());
		}

		@Test
		public void esVacioConUnVerticeAisladoTest() {
			_grafo.agregarVertice(_vertice1);
			assertFalse(_grafo.esVacio());
		}
	}

	public static class EsVacioCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void esVacioConVerticesYAristasTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarArista(_vertice1, _vertice2);

			assertFalse(_grafo.esVacio());
		}
	}

	public static class EliminarVerticeExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeInexistenteTest() {
			_grafo.eliminarVertice(_vertice1);
		}

		@Test(expected = IllegalArgumentException.class)
		public void eliminarVerticeNullTest() {
			_grafo.eliminarVertice(null);
		}
	}

	public static class EliminarVerticeCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void eliminarVerticeAisladoTest() {
			_grafo.agregarVertice(_vertice1);

			_grafo.eliminarVertice(_vertice1);

			assertFalse(_grafo.existeVertice(_vertice1));
		}

		@Test
		public void eliminarVerticeEliminaSusAristasTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);

			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice3, _vertice1);

			_grafo.eliminarVertice(_vertice1);

			assertTrue(_grafo.obtenerAristas().isEmpty());

		}

		@Test
		public void eliminarVerticeNoAfectaOtrasConexionesTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);

			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice2, _vertice3);

			_grafo.eliminarVertice(_vertice1);

			assertTrue(_grafo.existeArista(_vertice2, _vertice3));

		}
	}

	public static class ObtenerVecinosExcepcionesYCasosBorde {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeNullTest() {
			_grafo.obtenerVecinos(null);
		}

		@Test(expected = IllegalArgumentException.class)
		public void obtenerVecinosVerticeInexistenteTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.obtenerVecinos(_vertice2);
		}

		@Test
		public void obtenerVecinosVerticeAisladoTest() {
			_grafo.agregarVertice(_vertice1);

			assertTrue(_grafo.obtenerVecinos(_vertice1).isEmpty());
		}
	}

	public static class ObtenerVecinosCasoFeliz {

		private Grafo<Integer> _grafo = new Grafo<>();

		@Test
		public void obtenerVecinosTest() {
			_grafo.agregarVertice(_vertice1);
			_grafo.agregarVertice(_vertice2);
			_grafo.agregarVertice(_vertice3);
			_grafo.agregarVertice(_vertice4);
			_grafo.agregarArista(_vertice1, _vertice2);
			_grafo.agregarArista(_vertice3, _vertice1);

			Set<Integer> vecinos = _grafo.obtenerVecinos(_vertice1);

			assertEquals(new HashSet<>(Arrays.asList(_vertice2, _vertice3)), vecinos);
		}
	}
}
