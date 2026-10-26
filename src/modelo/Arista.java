package modelo;

import java.util.Objects;

public class Arista<V> {

	private final V extremo1;
	private final V extremo2;

	public Arista(V extremo1, V extremo2) {
		if (extremo1 == null || extremo2 == null)
			throw new IllegalArgumentException(
					"Error: Extremo1 y Extremo2 no pueden ser null y peso no puede ser 0 o negativo");

		if (extremo1.equals(extremo2))
			throw new IllegalArgumentException("Error: No se permiten loops.");

		this.extremo1 = extremo1;
		this.extremo2 = extremo2;
	}

	public V obtenerExtremo1() {
		return extremo1;
	}

	public V obtenerExtremo2() {
		return extremo2;
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

		boolean extremosEnOrden = extremo1.equals(otra.extremo1) && extremo2.equals(otra.extremo2);

		boolean extremosAlReves = extremo1.equals(otra.extremo2) && extremo2.equals(otra.extremo1);

		return extremosEnOrden || extremosAlReves;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(extremo1) + Objects.hashCode(extremo2);
	}

}
