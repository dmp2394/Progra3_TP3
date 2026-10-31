package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class AplicacionTest {

    public static class EstadoInicialTest {

        @Test
        public void constructorInicializaAplicacionVacia() {
            Aplicacion aplicacion = new Aplicacion();

            assertTrue(aplicacion.obtenerVertices().isEmpty());
            assertTrue(aplicacion.obtenerAristas().isEmpty());
            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }
    }

    public static class RegistrarObservadorTest {

        private int notificacionesGrafo;
        private int notificacionesSolucion;

        @Test(expected = IllegalArgumentException.class)
        public void rechazaObservadorNulo() {
            new Aplicacion().registrar(null);
        }

        @Test
        public void registraObservadorYLeNotificaLosCambios() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.registrar(new ObservadorConjuntoDominante() {
                @Override
                public void notificarNuevoGrafo(Aplicacion origen) {
                    notificacionesGrafo++;
                    assertSame(aplicacion, origen);
                }

                @Override
                public void notificarSolucion(Aplicacion origen) {
                    notificacionesSolucion++;
                    assertSame(aplicacion, origen);
                }
            });

            aplicacion.agregarVertice(1);
            aplicacion.resolver();

            assertEquals(1, notificacionesGrafo);
            assertEquals(1, notificacionesSolucion);
        }

        @Test
        public void variosObservadoresRecibenLasNotificaciones() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador primerObservador = new ContadorObservador();
            ContadorObservador segundoObservador = new ContadorObservador();
            aplicacion.registrar(primerObservador);
            aplicacion.registrar(segundoObservador);

            aplicacion.agregarVertice(1);
            aplicacion.resolver();

            assertEquals(1, primerObservador.notificacionesGrafo);
            assertEquals(1, segundoObservador.notificacionesGrafo);
            assertEquals(1, primerObservador.notificacionesSolucion);
            assertEquals(1, segundoObservador.notificacionesSolucion);
        }
    }

    public static class ModificarGrafoTest {

        @Test
        public void agregarVerticeActualizaElEstado() {
            Aplicacion aplicacion = new Aplicacion();

            aplicacion.agregarVertice(1);

            assertEquals(Collections.singletonList(1), aplicacion.obtenerVertices());
        }

        @Test(expected = IllegalArgumentException.class)
        public void agregarVerticeNuloPropagaLaExcepcion() {
            new Aplicacion().agregarVertice(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void agregarVerticeRepetidoPropagaLaExcepcion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(1);
        }

        @Test
        public void agregarAristaActualizaElEstado() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            Arista<Integer> arista = new Arista<>(1, 2);

            aplicacion.agregarArista(arista);

            assertEquals(Collections.singletonList(arista), aplicacion.obtenerAristas());
        }

        @Test(expected = IllegalArgumentException.class)
        public void agregarAristaNulaPropagaLaExcepcion() {
            new Aplicacion().agregarArista(null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void agregarAristaConVerticeInexistentePropagaLaExcepcion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarArista(new Arista<>(1, 2));
        }

        @Test(expected = IllegalArgumentException.class)
        public void agregarAristaRepetidaPropagaLaExcepcion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            aplicacion.agregarArista(new Arista<>(1, 2));
            aplicacion.agregarArista(new Arista<>(2, 1));
        }

        @Test
        public void cargarGrafoActualizaVerticesAristasYNotifica() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador observador = new ContadorObservador();
            aplicacion.registrar(observador);
            List<Integer> vertices = Arrays.asList(1, 2, 3);
            List<Arista<Integer>> aristas = Arrays.asList(
                    new Arista<>(1, 2),
                    new Arista<>(2, 3));

            aplicacion.cargarGrafo(vertices, aristas);

            assertEquals(vertices.size(), aplicacion.obtenerVertices().size());
            assertEquals(aristas.size(), aplicacion.obtenerAristas().size());
            assertEquals(1, observador.notificacionesGrafo);
        }

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoRechazaVerticesNulos() {
            new Aplicacion().cargarGrafo(null, Collections.emptyList());
        }

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoRechazaAristasNulas() {
            new Aplicacion().cargarGrafo(Collections.emptyList(), null);
        }

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoRechazaAristaNullDentroDeLaLista() {
            new Aplicacion().cargarGrafo(
            	Arrays.asList(1, 2),
            	Collections.singletonList(null));
        }

        @Test
        public void cargarGrafoReemplazaLaSolucionAnterior() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.resolver();
            assertEquals(Collections.singleton(1), aplicacion.obtenerSolucion());

            aplicacion.cargarGrafo(Arrays.asList(2, 3), Collections.singletonList(new Arista<>(2, 3)));

            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }

        @Test
        public void agregarVerticeDespuesDeResolverLimpiaLaSolucionAnterior() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.resolver();

            aplicacion.agregarVertice(2);

            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }

        @Test
        public void agregarAristaDespuesDeResolverLimpiaLaSolucionAnterior() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            aplicacion.resolver();

            aplicacion.agregarArista(new Arista<>(1, 2));

            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }

        @Test
        public void operacionFallidaNoModificaGrafoNiNotifica() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador observador = new ContadorObservador();
            aplicacion.registrar(observador);

            try {
            	aplicacion.agregarVertice(1);
            	aplicacion.agregarVertice(1);
            } catch (IllegalArgumentException excepcion) {
            	// La segunda operación falla porque el vértice ya existe.
            }

            assertEquals(Collections.singletonList(1), aplicacion.obtenerVertices());
            assertEquals(1, observador.notificacionesGrafo);
        }

        @Test
        public void borrarGrafoActualLimpiaGrafoYSolucion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());
            aplicacion.resolver();

            aplicacion.borrarGrafoActual();

            assertTrue(aplicacion.obtenerVertices().isEmpty());
            assertTrue(aplicacion.obtenerAristas().isEmpty());
            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }

        @Test
        public void borrarGrafoActualNotificaLosObservadores() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador observador = new ContadorObservador();
            aplicacion.registrar(observador);

            aplicacion.borrarGrafoActual();

            assertEquals(1, observador.notificacionesGrafo);
        }
    }

    public static class ResolucionTest {

    	@Test
    	public void resolverSobreGrafoVacioDevuelveSolucionVacia() {
    		Aplicacion aplicacion = new Aplicacion();

    		aplicacion.resolver();

    		assertTrue(aplicacion.obtenerSolucion().isEmpty());
    	}

        @Test
        public void resolverCalculaLaSolucion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            aplicacion.agregarArista(new Arista<>(1, 2));

            aplicacion.resolver();

            assertEquals(1, aplicacion.obtenerSolucion().size());
        }

        @Test
        public void resolverDeNuevoDespuesDeCambiarElGrafoCalculaLaNuevaSolucion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            aplicacion.resolver();

            aplicacion.agregarArista(new Arista<>(1, 2));
            aplicacion.resolver();

            assertEquals(1, aplicacion.obtenerSolucion().size());
            assertTrue(aplicacion.obtenerSolucion().contains(1)
            		|| aplicacion.obtenerSolucion().contains(2));
        }

        @Test
        public void resolverNotificaLaSolucion() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador observador = new ContadorObservador();
            aplicacion.registrar(observador);

            aplicacion.resolver();

            assertEquals(1, observador.notificacionesSolucion);
        }

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerSolucionDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.resolver();

            aplicacion.obtenerSolucion().add(2);
        }
    }

    public static class ColeccionesInmodificablesTest {

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerVerticesDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);

            aplicacion.obtenerVertices().add(2);
        }

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerAristasDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.agregarVertice(1);
            aplicacion.agregarVertice(2);
            aplicacion.agregarArista(new Arista<>(1, 2));

            aplicacion.obtenerAristas().clear();
        }
    }

    private static class ContadorObservador implements ObservadorConjuntoDominante {

        private int notificacionesGrafo;
        private int notificacionesSolucion;

        @Override
        public void notificarNuevoGrafo(Aplicacion aplicacion) {
            notificacionesGrafo++;
        }

        @Override
        public void notificarSolucion(Aplicacion aplicacion) {
            notificacionesSolucion++;
        }
    }
}
