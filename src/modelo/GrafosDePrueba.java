package modelo;

public class GrafosDePrueba {

	private GrafosDePrueba() {
	}

	public static Grafo<Integer> vacio() {
		return new Grafo<>();
	}

	public static Grafo<Integer> unSoloVertice() {
		return conVertices(1);
	}

	public static Grafo<Integer> verticesAislados() {
		return conVertices(3);
	}

	// El vértice 1 es el centro, unido a 2, 3, 4 y 5.
	public static Grafo<Integer> estrella() {
		Grafo<Integer> grafo = conVertices(5);

		for (int i = 2; i <= 5; i++) {
			grafo.agregarArista(1, i);
		}

		return grafo;
	}

	// 1 - 2 - 3 - 4 - 5
	public static Grafo<Integer> camino() {
		Grafo<Integer> grafo = conVertices(5);

		for (int i = 1; i < 5; i++) {
			grafo.agregarArista(i, i + 1);
		}

		return grafo;
	}

	// 4 - 3 - 1 - 5 - 2
	// El goloso desempata por el vértice más chico y elige primero al 1 (centro),
	// y después necesita dos más. El mínimo es de dos vértices, por ejemplo {3, 5}.
	public static Grafo<Integer> caminoDondeElGolosoNoEsOptimo() {
		Grafo<Integer> grafo = conVertices(5);

		grafo.agregarArista(4, 3);
		grafo.agregarArista(3, 1);
		grafo.agregarArista(1, 5);
		grafo.agregarArista(5, 2);

		return grafo;
	}

	// Componentes 1-2 y 3-4, y el vértice 5 aislado.
	public static Grafo<Integer> desconectado() {
		Grafo<Integer> grafo = conVertices(5);

		grafo.agregarArista(1, 2);
		grafo.agregarArista(3, 4);

		return grafo;
	}

	public static Grafo<Integer> tresVerticesEnLinea() {
		Grafo<Integer> grafo = conVertices(3);

		grafo.agregarArista(1, 2);
		grafo.agregarArista(1, 3);

		return grafo;
	}

	public static Grafo<Integer> ejemplo1() {
		Grafo<Integer> grafo = conVertices(8);

		grafo.agregarArista(1, 2);
		grafo.agregarArista(1, 3);
		grafo.agregarArista(1, 8);
		grafo.agregarArista(2, 8);
		grafo.agregarArista(2, 4);
		grafo.agregarArista(3, 5);
		grafo.agregarArista(5, 6);
		grafo.agregarArista(6, 7);
		grafo.agregarArista(7, 8);

		return grafo;
	}

	private static Grafo<Integer> conVertices(int cantidad) {
		Grafo<Integer> grafo = new Grafo<>();

		for (int i = 1; i <= cantidad; i++) {
			grafo.agregarVertice(i);
		}

		return grafo;
	}
}
