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

	private final int _tamanioInicial;
	private final Random _aleatorio;

	public EjecutorStressBacktracking(int tamanioInicial) {
		if (tamanioInicial < 1) {
			throw new IllegalArgumentException("El tamaño inicial debe ser positivo.");
		}
		_tamanioInicial = tamanioInicial;
		_aleatorio = new Random();
	}

	public List<ResultadoStress> ejecutar(AtomicBoolean cancelado,
			ObservadorProgreso observador) {
		if (cancelado == null) {
			throw new IllegalArgumentException("La señal de cancelación no puede ser nula.");
		}

		List<ResultadoStress> resultados = new ArrayList<>();

		for (int instancia = 1; !cancelado.get(); instancia++) {
			int cantidadVertices = _tamanioInicial + instancia - 1;
			Grafo<Integer> grafo = generarGrafo(cantidadVertices);

			long inicio = System.nanoTime();
			Set<Integer> solucion = new SolverBacktracking(grafo).resolver(cancelado);
			long tiempo = System.nanoTime() - inicio;

			if (cancelado.get()) {
				break;
			}

			ResultadoStress resultado = new ResultadoStress(
				instancia, cantidadVertices, grafo.obtenerAristas().size(),
				tiempo, solucion.size());
			resultados.add(resultado);
			if (observador != null) {
				observador.informar(resultado);
			}
		}

		return resultados;
	}

	private Grafo<Integer> generarGrafo(int cantidadVertices) {
		Grafo<Integer> grafo = new Grafo<>();
		for (int vertice = 1; vertice <= cantidadVertices; vertice++) {
			grafo.agregarVertice(vertice);
		}

		for (int vertice1 = 1; vertice1 <= cantidadVertices; vertice1++) {
			for (int vertice2 = vertice1 + 1;
					vertice2 <= cantidadVertices;
					vertice2++) {
				if (_aleatorio.nextBoolean()) {
					grafo.agregarArista(vertice1, vertice2);
				}
			}
		}

		return grafo;
	}
}
