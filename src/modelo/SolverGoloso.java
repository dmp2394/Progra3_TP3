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
