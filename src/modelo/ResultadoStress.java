package modelo;

public class ResultadoStress {

	private final int _numeroInstancia;
	private final int _cantidadVertices;
	private final int _cantidadAristas;
	private final long _tiempoGolosoNano;
	private final long _tiempoBacktrackingNano;
	private final int _tamanioGoloso;
	private final int _tamanioBacktracking;

	public ResultadoStress(int numeroInstancia, int cantidadVertices, int cantidadAristas,
			long tiempoGolosoNano, long tiempoBacktrackingNano,
			int tamanioGoloso, int tamanioBacktracking) {
		_numeroInstancia = numeroInstancia;
		_cantidadVertices = cantidadVertices;
		_cantidadAristas = cantidadAristas;
		_tiempoGolosoNano = tiempoGolosoNano;
		_tiempoBacktrackingNano = tiempoBacktrackingNano;
		_tamanioGoloso = tamanioGoloso;
		_tamanioBacktracking = tamanioBacktracking;
	}

	public int obtenerNumeroInstancia() { return _numeroInstancia; }
	public int obtenerCantidadVertices() { return _cantidadVertices; }
	public long obtenerTiempoGolosoNano() { return _tiempoGolosoNano; }
	public long obtenerTiempoBacktrackingNano() { return _tiempoBacktrackingNano; }
	public int obtenerTamanioGoloso() { return _tamanioGoloso; }
	public int obtenerTamanioBacktracking() { return _tamanioBacktracking; }

	public String obtenerResumen() {
		return String.format(
			"Instancia %d: |V|=%d, |E|=%d, goloso=%d (%.3f ms), backtracking=%d (%.3f ms)",
			_numeroInstancia, _cantidadVertices, _cantidadAristas,
			_tamanioGoloso, _tiempoGolosoNano / 1_000_000.0,
			_tamanioBacktracking, _tiempoBacktrackingNano / 1_000_000.0);
	}
}
