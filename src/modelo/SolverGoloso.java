package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class SolverGoloso {

	private Instancia _instancia;
	private Comparator<Arista<Integer>> _comparador;

	public SolverGoloso(Instancia ejemploEnunciadoTP, Comparator<Integer> comparator) {
		// TODO Auto-generated constructor stub
		_instancia = ejemploEnunciadoTP;
	}

	public Set<Integer> resolver() {

//		voy a dejar anotado asi nomas el algoritmo goloso:
//
//			obtengo el vertice con mas vecinos
//			paso por todos los vertices "no cubiertos" hasta que llegue a que un no cubierto no es vecino del que puse recien.
//			si pasa esto, A no es conjunto dominante, entonces luego agrego el vecino no cubierto con mas cantidad de vecinos desde el no cubierto que parti antes
//			vuelvo a pasar por todos los vertices no cubiertos, o sea repito el loop. Condicion de corte: Ningun cubierto no es vecino de A

		Set<Integer> solucion = new HashSet<>();

		for (Arista<Integer> arista : verticesOrdenadosPorCantidadDeVecinos()) {

		}
	}

	private ArrayList<Arista<Integer>> verticesOrdenadosPorCantidadDeVecinos() {
		ArrayList<Integer> ret = _instancia.obtenerVertices();
		Collections.sort(ret, _comparador);

		return ret;
	}

}
