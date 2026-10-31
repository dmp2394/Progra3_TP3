package modelo;

public class ResultadoStress {

	private final int numeroInstancia;
	private final int cantidadVertices;
	private final int cantidadAristas;
	private final long tiempoGolosoNano;
	private final long tiempoBacktrackingNano;
	private final int tamanioGoloso;
	private final int tamanioBacktracking;

	public ResultadoStress(int numeroInstancia, int cantidadVertices, int cantidadAristas,
			long tiempoGolosoNano, long tiempoBacktrackingNano,
			int tamanioGoloso, int tamanioBacktracking) {
		this.numeroInstancia = numeroInstancia;
		this.cantidadVertices = cantidadVertices;
		this.cantidadAristas = cantidadAristas;
		this.tiempoGolosoNano = tiempoGolosoNano;
		this.tiempoBacktrackingNano = tiempoBacktrackingNano;
		this.tamanioGoloso = tamanioGoloso;
		this.tamanioBacktracking = tamanioBacktracking;
	}

	public int obtenerNumeroInstancia() { return numeroInstancia; }
	public int obtenerCantidadVertices() { return cantidadVertices; }
	public int obtenerCantidadAristas() { return cantidadAristas; }
	public long obtenerTiempoGolosoNano() { return tiempoGolosoNano; }
	public long obtenerTiempoBacktrackingNano() { return tiempoBacktrackingNano; }
	public int obtenerTamanioGoloso() { return tamanioGoloso; }
	public int obtenerTamanioBacktracking() { return tamanioBacktracking; }

	public String obtenerResumen() {
		return String.format(
			"Instancia %d: |V|=%d, |E|=%d, goloso=%d (%.3f ms), backtracking=%d (%.3f ms)",
			numeroInstancia, cantidadVertices, cantidadAristas,
			tamanioGoloso, tiempoGolosoNano / 1_000_000.0,
			tamanioBacktracking, tiempoBacktrackingNano / 1_000_000.0);
	}
}
