package modelo;

import java.util.Objects;

public class Arista<V> {

	private final V _extremo1;
	private final V _extremo2;

	public Arista(V extremo1, V extremo2) {
		if (extremo1 == null || extremo2 == null)
			throw new IllegalArgumentException(
					"Error: Extremo1 y Extremo2 no pueden ser null y peso no puede ser 0 o negativo");

		if (extremo1.equals(extremo2))
			throw new IllegalArgumentException("Error: No se permiten loops.");

		_extremo1 = extremo1;
		_extremo2 = extremo2;
	}

	public V obtenerExtremo1() {
		return _extremo1;
	}

	public V obtenerExtremo2() {
		return _extremo2;
	}

	@Override
	public boolean equals(Object objeto) {
		if (this == objeto) {
			return true;
		}

		if (objeto == null || getClass() != objeto.getClass()) {
			return false;
		}

		Arista<?> otra = (Arista<?>) objeto;

		boolean extremosEnOrden = _extremo1.equals(otra._extremo1) && _extremo2.equals(otra._extremo2);

		boolean extremosAlReves = _extremo1.equals(otra._extremo2) && _extremo2.equals(otra._extremo1);

		return extremosEnOrden || extremosAlReves;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(_extremo1) + Objects.hashCode(_extremo2);
	}

}
