package modelo;

import java.util.HashSet;
import java.util.Set;

public class Solucion {

	private Set<Integer> _conjuntoDominanteMinimo;

	public Solucion() {
		_conjuntoDominanteMinimo = new HashSet<>();
	}

	public Set<Integer> getConjuntoDominanteMinimo() {
		return _conjuntoDominanteMinimo;
	}

	public Integer getTamanioConjuntoDominanteMinimo() {
		return _conjuntoDominanteMinimo.size();
	}

	public void setConjuntoDominanteMinimo(Set<Integer> conjuntoDominanteMinimo) {
		_conjuntoDominanteMinimo = conjuntoDominanteMinimo;
	}
}
