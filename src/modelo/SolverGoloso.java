package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class SolverGoloso {

	private Grafo<Integer> _instancia;

	public SolverGoloso(Grafo<Integer> ejemploEnunciadoTP) {

		_instancia = ejemploEnunciadoTP;
	}

	public Set<Integer> resolver() {
		// voy a dejar anotado asi nomas el algoritmo goloso:
		// sea A un conjunto vacio
		// obtengo el vertice con mas vecinos, lo agrego a A y lo marco como visitado
		// loop:
		// paso por todos los vertices no visitados hasta que llegue a que uno no es
		// vecino de alguno de A. La forma de pasar es mediante una lista ordenada con
		// cantidad de vecinos por vertice
		// si pasa esto, A no es conjunto dominante, entonces luego agrego este que no
		// es vecino de alguno de A. Si no pasa eso, A es conjunto dominante (minimo
		// tentativo)
		// vuelvo a pasar por todos los vertices no cubiertos, o sea repito el loop.
		// Condicion de corte: Todos los no visitados son vecinos de A
		Set<Integer> solucion = new HashSet<>();

		for (Integer vertice : verticesOrdenadosPorCantidadDeVecinos()) {

		}

		return solucion;
	}

	private ArrayList<Integer> verticesOrdenadosPorCantidadDeVecinos() {
		ArrayList<Integer> ret = _instancia.obtenerVertices();

		// Ordena de mayor a menor
		Comparator<Integer> comparador = new Comparator<Integer>() {
			@Override
			public int compare(Integer uno, Integer otro) {
				return _instancia.obtenerVecinos(otro).size() - _instancia.obtenerVecinos(uno).size();
			}
		};

		Collections.sort(ret, comparador);

		return ret;
	}

}
