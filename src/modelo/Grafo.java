package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Grafo<V> {

	private Map<V, Set<Arista<V>>> listaDeAristasIncidentes;
	private ArrayList<Arista<V>> aristas;

	private final int ARISTA_PESO_FICTICIO = 100;

	public Grafo() {
		listaDeAristasIncidentes = new HashMap<>();
		aristas = new ArrayList<>();

	}

	public void agregarVertice(V vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("Error: El vértice no puede ser nulo.");

		if (listaDeAristasIncidentes.containsKey(vertice))
			throw new IllegalArgumentException("Error: El vértice ya existe en el grafo.");

		listaDeAristasIncidentes.put(vertice, new HashSet<>());
	}

	public void agregarArista(V vertice1, V vertice2, int peso) {
		if (!listaDeAristasIncidentes.containsKey(vertice1) || !listaDeAristasIncidentes.containsKey(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");

		if (vertice1.equals(vertice2))
			throw new IllegalArgumentException("Error: No se permiten loops (aristas de un vértice a sí mismo).");

		if (peso <= 0)
			throw new IllegalArgumentException("Error: No se permite agregar una arista con peso 0 o negativo.");

		Arista<V> arista = new Arista<V>(vertice1, vertice2, peso);

		for (Arista<V> aristaExistente : listaDeAristasIncidentes.get(vertice1))
			if (aristaExistente.equals(arista))
				throw new IllegalArgumentException("Error: La arista ya existe entre estos vértices.");

		listaDeAristasIncidentes.get(vertice1).add(arista);
		listaDeAristasIncidentes.get(vertice2).add(arista);
		aristas.add(arista);

	}

	public Set<V> obtenerVecinos(V vertice) {
		if (!existeVertice(vertice))
			throw new IllegalArgumentException("Error: El vértice no existe en el grafo.");

		Set<V> vecinos = new HashSet<>();

		for (Arista<V> arista : listaDeAristasIncidentes.get(vertice)) {

			V vecino = arista.obtenerExtremo1().equals(vertice) ? arista.obtenerExtremo2() : arista.obtenerExtremo1();

			vecinos.add(vecino);
		}

		return vecinos;
	}

	public ArrayList<Arista<V>> obtenerAristas() {
		return new ArrayList<Arista<V>>(aristas);
	}

	public ArrayList<V> obtenerVertices() {

		ArrayList<V> vertices = new ArrayList<>();
		for (V vertice : listaDeAristasIncidentes.keySet())
			vertices.add(vertice);

		return vertices;
	}

	public boolean esVacio() {
		return listaDeAristasIncidentes.isEmpty();
	}

	public int pesoTotal() {
		int total = 0;
		for (Arista<V> arista : aristas)
			total += arista.devolverPeso();

		return total;
	}

	public boolean existeArista(V vertice1, V vertice2) {

		if (!existeVertice(vertice1) || !existeVertice(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vertices no existen.");

		Set<Arista<V>> aristasDeVertice1 = listaDeAristasIncidentes.get(vertice1);

		int pesoFicticio = 100;
		Arista<V> buscada = new Arista<V>(vertice1, vertice2, pesoFicticio);
		for (Arista<V> unaArista : aristasDeVertice1)
			if (unaArista.equals(buscada))
				return true;

		return false;
	}

	public void eliminarVertice(V vertice) {
		if (vertice == null) {
			throw new IllegalArgumentException("Error: No se puede borrar el vertice null");
		}

		if (!listaDeAristasIncidentes.containsKey(vertice)) {
			throw new IllegalArgumentException("Error: El vértice no existe en el grafo.");
		}

		ArrayList<Arista<V>> aristasDelVertice = new ArrayList<>(listaDeAristasIncidentes.get(vertice));

		for (Arista<V> arista : aristasDelVertice) {
			eliminarArista(arista.obtenerExtremo1(), arista.obtenerExtremo2());
		}

		listaDeAristasIncidentes.remove(vertice);
	}

	public boolean existeVertice(V vertice) {
		if (vertice == null)
			throw new IllegalArgumentException("Error: El vértice no puede ser nulo.");

		return listaDeAristasIncidentes.containsKey(vertice);
	}

	public void eliminarArista(V vertice1, V vertice2) {
		if (!listaDeAristasIncidentes.containsKey(vertice1) || !listaDeAristasIncidentes.containsKey(vertice2))
			throw new IllegalArgumentException("Error: Uno o ambos vértices no existen en el grafo.");

		// los vertices se colocan en el objeto Arista en variable "buscada" para
		// ejecutar el equals
		Arista<V> buscada = new Arista<V>(vertice1, vertice2, ARISTA_PESO_FICTICIO);
		Arista<V> arista = null;
		for (Arista<V> aristaExistente : listaDeAristasIncidentes.get(vertice1))
			if (aristaExistente.equals(buscada))
				arista = aristaExistente;

		if (arista == null)
			throw new IllegalArgumentException("Error: La arista no existe entre estos vértices.");

		listaDeAristasIncidentes.get(vertice1).remove(arista);
		listaDeAristasIncidentes.get(vertice2).remove(arista);
		aristas.remove(arista);
	}

}
