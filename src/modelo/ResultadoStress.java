package modelo;

public class ResultadoStress {

	private final int _numeroInstancia;
	private final int _cantidadVertices;
	private final int _cantidadAristas;
	private final long _tiempoNano;
	private final int _tamanioSolucion;

	public ResultadoStress(int numeroInstancia, int cantidadVertices, int cantidadAristas,
			long tiempoNano, int tamanioSolucion) {
		_numeroInstancia = numeroInstancia;
		_cantidadVertices = cantidadVertices;
		_cantidadAristas = cantidadAristas;
		_tiempoNano = tiempoNano;
		_tamanioSolucion = tamanioSolucion;
	}

	public int obtenerNumeroInstancia() { return _numeroInstancia; }
	public int obtenerCantidadVertices() { return _cantidadVertices; }
	public int obtenerCantidadAristas() { return _cantidadAristas; }
	public long obtenerTiempoNano() { return _tiempoNano; }
	public int obtenerTamanioSolucion() { return _tamanioSolucion; }

	public String obtenerResumen() {
		return String.format(
			"Instancia %d: |V|=%d, |E|=%d, backtracking=%d (%.3f ms)",
			_numeroInstancia, _cantidadVertices, _cantidadAristas,
			_tamanioSolucion, _tiempoNano / 1_000_000.0);
	}
}
