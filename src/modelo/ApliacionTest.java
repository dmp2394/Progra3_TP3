package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

public class ApliacionTest {

    private static Integer extremo1 = 1;
    private static Integer extremo2 = 2;

    private static Arista<Integer> arista12 = new Arista<>(extremo1, extremo2);
    private static Arista<Integer> arista21 = new Arista<>(extremo2, extremo1);

    private static Grafo<Integer> grafo = new Grafo<>();

    public static class ConstructorExcepcionesYCasosBorde {

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteAgregarVerticeNuloTest() {
            grafo.agregarVertice(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteAgregarVerticeRepetidoTest() {
            grafo.agregarVertice(extremo1);
            grafo.agregarVertice(extremo1);
        }

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteAgregarAristaConVerticeInexistenteTest() {
            grafo.agregarArista(extremo1, extremo2);
        }

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteAgregarAristaConLoopTest() {
            grafo.agregarVertice(extremo1);
            grafo.agregarArista(extremo1, extremo1);
        }

    }

    public static class AgregarAristaTest {

        @Test
        public void agregarAristaTest() {
            grafo.agregarVertice(extremo1);
            grafo.agregarVertice(extremo2);
            grafo.agregarArista(extremo1, extremo2);

            assertTrue(grafo.obtenerAristas().contains(arista12));
            assertTrue(grafo.obtenerAristas().contains(arista21));
        }

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteAgregarAristaRepetidaTest() {
            grafo.agregarVertice(extremo1);
            grafo.agregarVertice(extremo2);
            grafo.agregarArista(extremo1, extremo2);
            grafo.agregarArista(extremo2, extremo1);
        }

    }

    public static class ObtenerVecinosTest {

        @Test
        public void obtenerVecinosTest() {
            grafo.agregarVertice(extremo1);
            grafo.agregarVertice(extremo2);
            grafo.agregarArista(extremo1, extremo2);

            assertEquals(new HashSet<>(grafo.obtenerVecinos(extremo1)), new HashSet<>(grafo.obtenerVecinos(extremo2)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void noPermiteObtenerVecinosDeVerticeInexistenteTest() {
            grafo.obtenerVecinos(3);
        }

    }
}
