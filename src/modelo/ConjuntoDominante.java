package modelo;

import java.util.ArrayList;

public class ConjuntoDominante {

	private Grafo<Integer> _grafo;
	private ArrayList<ObservadorConjuntoDominante> _observadores;

	public ConjuntoDominante() {
		_grafo = new Grafo<Integer>();
		_observadores = new ArrayList<>();
	}

	public void registrar(ObservadorConjuntoDominante observador) {
		if (observador == null) {
			throw new IllegalArgumentException("El observador no puede ser null.");
		}

		_observadores.add(observador);
	}

	public void agregarVertice(Integer vertice) {
		_grafo.agregarVertice(vertice);

		notificarObservadores();
	}

	public void agregarArista(Arista<Integer> arista) {
		_grafo.agregarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());

		notificarObservadores();
	}

	private void notificarObservadores() {
		for (ObservadorConjuntoDominante observador : _observadores)
			observador.notificar(this);

	}

	public ArrayList<Integer> obtenerVertices() {
		return _grafo.obtenerVertices();

	}

	public ArrayList<Arista<Integer>> obtenerAristas() {
		return _grafo.obtenerAristas();

	}
}
