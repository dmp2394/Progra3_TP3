package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo<V> {

	private Map<V, Set<Arista<V>>> _listaDeAristasIncidentes;
	private ArrayList<Arista<V>> _aristas;

	public Grafo() {
		_listaDeAristasIncidentes = new HashMap<>();
		_aristas = new ArrayList<>();
	}

	public void agregarVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("Error: El vértice no puede ser nulo.");
		}

		if (_listaDeAristasIncidentes.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice ya existe en el grafo.");
		}

		_listaDeAristasIncidentes.put(vertice, new HashSet<>());
	}

	public void agregarArista(V vertice1, V vertice2) {
		if (!_listaDeAristasIncidentes.containsKey(vertice1) || !_listaDeAristasIncidentes.containsKey(vertice2)) {
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");
		}

		if (vertice1.equals(vertice2)) {
			throw new IllegalArgumentException("Error: No se permiten loops.");
		}

		Arista<V> arista = new Arista<>(vertice1, vertice2);

		for (Arista<V> aristaExistente : _listaDeAristasIncidentes.get(vertice1)) {
			if (aristaExistente.equals(arista)) {
				throw new IllegalArgumentException("Error: La arista ya existe entre estos vértices.");
			}
		}

		_listaDeAristasIncidentes.get(vertice1).add(arista);
		_listaDeAristasIncidentes.get(vertice2).add(arista);
		_aristas.add(arista);
	}

	public Set<V> obtenerVecinos(V vertice) {
		if (!existeVertice(vertice)) {
			throw new IllegalArgumentException("Error: El vértice no existe en el grafo.");
		}

		Set<V> vecinos = new HashSet<>();

		for (Arista<V> arista : _listaDeAristasIncidentes.get(vertice)) {
			V vecino = arista.obtenerExtremo1().equals(vertice) ? arista.obtenerExtremo2() : arista.obtenerExtremo1();

			vecinos.add(vecino);
		}

		return vecinos;
	}

	public boolean existeVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("Error: El vértice no puede ser nulo.");
		}

		return _listaDeAristasIncidentes.containsKey(vertice);
	}

	public ArrayList<Arista<V>> obtenerAristas() {
		return new ArrayList<>(_aristas);
	}

	public ArrayList<V> obtenerVertices() {
		ArrayList<V> vertices = new ArrayList<>();

		for (V vertice : _listaDeAristasIncidentes.keySet()) {
			vertices.add(vertice);
		}

		return vertices;
	}

	public boolean esVacio() {
		return _listaDeAristasIncidentes.isEmpty();
	}

	public boolean existeArista(V vertice1, V vertice2) {
		if (!existeVertice(vertice1) || !existeVertice(vertice2)) {
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen.");
		}

		Set<Arista<V>> aristasDeVertice1 = _listaDeAristasIncidentes.get(vertice1);

		Arista<V> buscada = new Arista<>(vertice1, vertice2);

		for (Arista<V> unaArista : aristasDeVertice1) {
			if (unaArista.equals(buscada)) {
				return true;
			}
		}

		return false;
	}

	public void eliminarVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("Error: No se puede borrar el vértice null.");
		}

		if (!_listaDeAristasIncidentes.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice no existe en el grafo.");
		}

		ArrayList<Arista<V>> aristasDelVertice = new ArrayList<>(_listaDeAristasIncidentes.get(vertice));

		for (Arista<V> arista : aristasDelVertice) {
			eliminarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		}

		_listaDeAristasIncidentes.remove(vertice);
	}

	public void eliminarArista(V vertice1, V vertice2) {
		if (!_listaDeAristasIncidentes.containsKey(vertice1) || !_listaDeAristasIncidentes.containsKey(vertice2)) {
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen.");
		}

		Arista<V> buscada = new Arista<>(vertice1, vertice2);
		Arista<V> arista = null;

		for (Arista<V> aristaExistente : _listaDeAristasIncidentes.get(vertice1)) {
			if (aristaExistente.equals(buscada)) {
				arista = aristaExistente;
				break;
			}
		}

		if (arista == null) {
			throw new IllegalArgumentException("Error: La arista no existe entre estos vértices.");
		}

		_listaDeAristasIncidentes.get(vertice1).remove(arista);
		_listaDeAristasIncidentes.get(vertice2).remove(arista);
		_aristas.remove(arista);
	}
}