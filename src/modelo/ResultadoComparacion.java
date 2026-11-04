package modelo;

import java.util.Collections;
import java.util.Set;

public class ResultadoComparacion {

	private final Set<Integer> _solucionGolosa;
	private final Set<Integer> _solucionBacktracking;
	private final long _tiempoGolosoNano;
	private final long _tiempoBacktrackingNano;

	public ResultadoComparacion(Set<Integer> solucionGolosa, Set<Integer> solucionBacktracking,
			long tiempoGolosoNano, long tiempoBacktrackingNano) {
		_solucionGolosa = Collections.unmodifiableSet(solucionGolosa);
		_solucionBacktracking = Collections.unmodifiableSet(solucionBacktracking);
		_tiempoGolosoNano = tiempoGolosoNano;
		_tiempoBacktrackingNano = tiempoBacktrackingNano;
	}

	public Set<Integer> obtenerSolucionGolosa() {
		return _solucionGolosa;
	}

	public Set<Integer> obtenerSolucionBacktracking() {
		return _solucionBacktracking;
	}

	public long obtenerTiempoGolosoNano() {
		return _tiempoGolosoNano;
	}

	public long obtenerTiempoBacktrackingNano() {
		return _tiempoBacktrackingNano;
	}

	public int obtenerTamanioGrafoGoloso() {
		return _solucionGolosa.size();
	}

	public int obtenerTamanioGrafoBacktracking() {
		return _solucionBacktracking.size();
	}

	public String obtenerResumen() {
		return String.format(
			"Goloso: %d vértices, %.3f ms | Backtracking: %d vértices, %.3f ms",
			_solucionGolosa.size(),
			_tiempoGolosoNano / 1_000_000.0,
			_solucionBacktracking.size(),
			_tiempoBacktrackingNano / 1_000_000.0);
	}
}
