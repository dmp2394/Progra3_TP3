package modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Vertice {
	private int id;
	private List<Vertice> vecinos;
	private String nombre;
	
	public Vertice (int id,String nombre) {
		this.id = id;
		this.nombre = nombre;
		this.vecinos = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Set<Vertice> alcance(Vertice v){
	    Set<Vertice> alcance = new HashSet<>();

	    alcance.add(v);
	    alcance.addAll(v.getVecinos());

	    return alcance;
	}

	public List<Vertice> getVecinos() {
		
		return vecinos;
	}
	
	public int getId() {
		
		return id;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) {
	            return true;
	        }

	        if (!(obj instanceof Vertice)) {
	            return false;
	        }

	        Vertice otro = (Vertice) obj;
	        return this.id == otro.id;
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }
}
