package modelo;

import static org.junit.Assert.assertEquals;

import java.util.Comparator;
import java.util.Set;

import org.junit.Test;

public class SolverGolosoTest {

	@Test
	public void resolverOrdenandoPorCantidadDeVecinosTest() {
		SolverGoloso solver = new SolverGoloso(ejemploEnunciadoTP(), new Comparator<Integer>() {
			@Override
			public int compare(Integer uno, Integer otro) {
				return -uno.getBeneficio() + otro.getBeneficio();
			}
		});
		Set<Integer> solucion = solver.resolver();

		assertEquals(Set.of(4, 3, 5, 2), solucion);
	}

	private Instancia ejemploEnunciadoTP() {
		Grafo<Integer> grafo = new Grafo<>();

		grafo.agregarVertice(1);
		grafo.agregarVertice(2);
		grafo.agregarVertice(3);
		grafo.agregarVertice(4);
		grafo.agregarVertice(5);
		grafo.agregarVertice(6);

		grafo.agregarArista(6, 4);
		grafo.agregarArista(4, 5);
		grafo.agregarArista(4, 3);
		grafo.agregarArista(5, 2);
		grafo.agregarArista(5, 1);
		grafo.agregarArista(1, 2);
		grafo.agregarArista(2, 3);

		return (new Instancia(grafo));

	}
}
