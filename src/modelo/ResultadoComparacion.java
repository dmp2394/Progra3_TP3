package modelo;

import java.util.Collections;
import java.util.Set;

public class ResultadoComparacion {

	private final Set<Integer> solucionGolosa;
	private final Set<Integer> solucionBacktracking;
	private final long tiempoGolosoNano;
	private final long tiempoBacktrackingNano;

	public ResultadoComparacion(Set<Integer> solucionGolosa, Set<Integer> solucionBacktracking,
			long tiempoGolosoNano, long tiempoBacktrackingNano) {
		this.solucionGolosa = Collections.unmodifiableSet(solucionGolosa);
		this.solucionBacktracking = Collections.unmodifiableSet(solucionBacktracking);
		this.tiempoGolosoNano = tiempoGolosoNano;
		this.tiempoBacktrackingNano = tiempoBacktrackingNano;
	}

	public Set<Integer> obtenerSolucionGolosa() {
		return solucionGolosa;
	}

	public Set<Integer> obtenerSolucionBacktracking() {
		return solucionBacktracking;
	}

	public long obtenerTiempoGolosoNano() {
		return tiempoGolosoNano;
	}

	public long obtenerTiempoBacktrackingNano() {
		return tiempoBacktrackingNano;
	}

	public int obtenerTamanioGrafoGoloso() {
		return solucionGolosa.size();
	}

	public int obtenerTamanioGrafoBacktracking() {
		return solucionBacktracking.size();
	}

	public String obtenerResumen() {
		return String.format(
			"Goloso: %d vértices, %.3f ms | Backtracking: %d vértices, %.3f ms",
			solucionGolosa.size(),
			tiempoGolosoNano / 1_000_000.0,
			solucionBacktracking.size(),
			tiempoBacktrackingNano / 1_000_000.0);
	}
}
