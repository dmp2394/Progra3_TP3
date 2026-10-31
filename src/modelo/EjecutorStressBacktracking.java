package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class EjecutorStressBacktracking {

	public interface ObservadorProgreso {
		void informar(ResultadoStress resultado);
	}

	private final int cantidadInstancias;
	private final int cantidadVertices;
	private final Random aleatorio;

	public EjecutorStressBacktracking(int cantidadInstancias, int cantidadVertices) {
		if (cantidadInstancias <= 0 || cantidadVertices < 1) {
			throw new IllegalArgumentException("La cantidad de instancias y vértices debe ser positiva.");
		}
		this.cantidadInstancias = cantidadInstancias;
		this.cantidadVertices = cantidadVertices;
		this.aleatorio = new Random();
	}

	public List<ResultadoStress> ejecutar(AtomicBoolean cancelado,
			ObservadorProgreso observador) {
		List<ResultadoStress> resultados = new ArrayList<>();

		for (int instancia = 1;
				instancia <= cantidadInstancias && !cancelado.get();
				instancia++) {
			Grafo<Integer> grafo = generarGrafo();

			long inicioGoloso = System.nanoTime();
			Set<Integer> solucionGolosa = new SolverGoloso(grafo).resolver();
			long tiempoGoloso = System.nanoTime() - inicioGoloso;

			long inicioBacktracking = System.nanoTime();
			Set<Integer> solucionBacktracking =
				new SolverBacktracking(grafo).resolver(cancelado);
			long tiempoBacktracking = System.nanoTime() - inicioBacktracking;

			if (cancelado.get()) {
				break;
			}

			ResultadoStress resultado = new ResultadoStress(
				instancia, cantidadVertices, grafo.obtenerAristas().size(),
				tiempoGoloso, tiempoBacktracking,
				solucionGolosa.size(), solucionBacktracking.size());
			resultados.add(resultado);
			if (observador != null) {
				observador.informar(resultado);
			}
		}

		return resultados;
	}

	private Grafo<Integer> generarGrafo() {
		Grafo<Integer> grafo = new Grafo<>();
		for (int vertice = 1; vertice <= cantidadVertices; vertice++) {
			grafo.agregarVertice(vertice);
		}

		for (int vertice1 = 1; vertice1 <= cantidadVertices; vertice1++) {
			for (int vertice2 = vertice1 + 1;
					vertice2 <= cantidadVertices;
					vertice2++) {
				if (aleatorio.nextBoolean()) {
					grafo.agregarArista(vertice1, vertice2);
				}
			}
		}

		return grafo;
	}
}
