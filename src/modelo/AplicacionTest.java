package modelo;

import static org.junit.Assert.assertEquals;
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

        private int _notificacionesGrafo;
        private int _notificacionesSolucion;

        @Test(expected = IllegalArgumentException.class)
        public void rechazaObservadorNulo() {
            new Aplicacion().registrar(null);
        }

        @Test
        public void registraObservadorYLeNotificaLosCambios() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.registrar(new ObservadorConjuntoDominante() {
                @Override
                public void notificarNuevoGrafo() {
                    _notificacionesGrafo++;
                }

                @Override
                public void notificarSolucion() {
                    _notificacionesSolucion++;
                }
            });

            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());
            aplicacion.resolver();

            assertEquals(1, _notificacionesGrafo);
            assertEquals(1, _notificacionesSolucion);
        }

        @Test
        public void variosObservadoresRecibenLasNotificaciones() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador primerObservador = new ContadorObservador();
            ContadorObservador segundoObservador = new ContadorObservador();
            aplicacion.registrar(primerObservador);
            aplicacion.registrar(segundoObservador);

            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());
            aplicacion.resolver();

            assertEquals(1, primerObservador._notificacionesGrafo);
            assertEquals(1, segundoObservador._notificacionesGrafo);
            assertEquals(1, primerObservador._notificacionesSolucion);
            assertEquals(1, segundoObservador._notificacionesSolucion);
        }
    }

    public static class CargarGrafoTest {

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
            assertEquals(1, observador._notificacionesGrafo);
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

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoConVerticeRepetidoPropagaLaExcepcion() {
            new Aplicacion().cargarGrafo(Arrays.asList(1, 1), Collections.emptyList());
        }

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoConAristaAVerticeInexistentePropagaLaExcepcion() {
            new Aplicacion().cargarGrafo(Arrays.asList(1), Collections.singletonList(new Arista<>(1, 2)));
        }

        @Test(expected = IllegalArgumentException.class)
        public void cargarGrafoConAristaRepetidaPropagaLaExcepcion() {
            new Aplicacion().cargarGrafo(Arrays.asList(1, 2), Arrays.asList(new Arista<>(1, 2), new Arista<>(2, 1)));
        }

        @Test
        public void cargarGrafoReemplazaLaSolucionAnterior() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());
            aplicacion.resolver();
            assertEquals(Collections.singleton(1), aplicacion.obtenerSolucion());

            aplicacion.cargarGrafo(Arrays.asList(2, 3), Collections.singletonList(new Arista<>(2, 3)));

            assertTrue(aplicacion.obtenerSolucion().isEmpty());
        }

        @Test
        public void cargaFallidaNoModificaGrafoNiNotifica() {
            Aplicacion aplicacion = new Aplicacion();
            ContadorObservador observador = new ContadorObservador();
            aplicacion.registrar(observador);
            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());

            try {
            	aplicacion.cargarGrafo(Arrays.asList(2, 2), Collections.emptyList());
            } catch (IllegalArgumentException excepcion) {
            	// La segunda carga falla porque el vértice 2 está repetido.
            }

            assertEquals(Collections.singletonList(1), aplicacion.obtenerVertices());
            assertEquals(1, observador._notificacionesGrafo);
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
            aplicacion.cargarGrafo(Arrays.asList(1, 2), Collections.singletonList(new Arista<>(1, 2)));

            aplicacion.resolver();

            assertEquals(1, aplicacion.obtenerSolucion().size());
        }

        @Test
        public void resolverBacktrackingCalculaLaSolucionMinima() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1, 2, 3), Arrays.asList(new Arista<>(1, 2), new Arista<>(1, 3)));

            aplicacion.resolverBacktracking();

            assertEquals(Collections.singleton(1), aplicacion.obtenerSolucion());
        }

        @Test
        public void resolverDeNuevoDespuesDeCambiarElGrafoCalculaLaNuevaSolucion() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1, 2), Collections.emptyList());
            aplicacion.resolver();

            aplicacion.cargarGrafo(Arrays.asList(1, 2), Collections.singletonList(new Arista<>(1, 2)));
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

            assertEquals(1, observador._notificacionesSolucion);
        }

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerSolucionDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());
            aplicacion.resolver();

            aplicacion.obtenerSolucion().add(2);
        }
    }

    public static class ColeccionesInmodificablesTest {

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerVerticesDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1), Collections.emptyList());

            aplicacion.obtenerVertices().add(2);
        }

        @Test(expected = UnsupportedOperationException.class)
        public void obtenerAristasDevuelveUnaVistaInmodificable() {
            Aplicacion aplicacion = new Aplicacion();
            aplicacion.cargarGrafo(Arrays.asList(1, 2), Collections.singletonList(new Arista<>(1, 2)));

            aplicacion.obtenerAristas().clear();
        }
    }

    private static class ContadorObservador implements ObservadorConjuntoDominante {

        private int _notificacionesGrafo;
        private int _notificacionesSolucion;

        @Override
        public void notificarNuevoGrafo() {
            _notificacionesGrafo++;
        }

        @Override
        public void notificarSolucion() {
            _notificacionesSolucion++;
        }
    }
}
