package vista;

import java.util.List;
import java.util.Set;

import javax.swing.JComponent;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

import modelo.Arista;

public class VisualizadorGrafo {

	private static final String ESTILO_CLARO = "node { size: 24px; fill-color: #DDDDDD; stroke-mode: plain; stroke-color: black; text-size: 14; text-alignment: center; }"
			+ "edge { fill-color: black; }" + "graph { fill-color: white; }";

	private static final String ESTILO_OSCURO = "node { size: 24px; fill-color: #3A3D42; stroke-mode: plain; stroke-color: #ECEFF1; text-size: 14; text-alignment: center; }"
			+ "edge { fill-color: #AAB0B6; }" + "graph { fill-color: #2B2D30; }";

	private static final String COLOR_SOLUCION = "yellow";

	static {
		System.setProperty("org.graphstream.ui", "swing");
	}

	private final Graph _grafo;
	private final JComponent _panel;

	public VisualizadorGrafo(String nombre) {
		_grafo = new SingleGraph(nombre);

		SwingViewer viewer = new SwingViewer(_grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();

		_panel = (JComponent) viewer.addDefaultView(false);
	}

	public JComponent obtenerPanel() {
		return _panel;
	}

	public void mostrar(List<Integer> vertices, List<Arista<Integer>> aristas) {
		_grafo.clear();
		aplicarTema();

		agregarVertices(vertices);
		agregarAristas(aristas);
	}

	public void aplicarTema() {
		_grafo.setAttribute("ui.stylesheet", Tema.esOscuro() ? ESTILO_OSCURO : ESTILO_CLARO);
	}

	private void agregarVertices(List<Integer> vertices) {
		for (Integer vertice : vertices) {
			Node nodo = _grafo.addNode(String.valueOf(vertice));
			nodo.setAttribute("ui.label", String.valueOf(vertice));
		}
	}

	private void agregarAristas(List<Arista<Integer>> aristas) {
		for (Arista<Integer> arista : aristas) {
			String extremo1 = String.valueOf(arista.obtenerExtremo1());
			String extremo2 = String.valueOf(arista.obtenerExtremo2());
			String id = extremo1 + "-" + extremo2;
			_grafo.addEdge(id, extremo1, extremo2);
		}
	}

	public void limpiar() {
		_grafo.clear();
		aplicarTema();
	}

	public void pintarSolucion(Set<Integer> solucion) {
		for (Integer vertice : solucion) {
			Node nodo = _grafo.getNode(String.valueOf(vertice));
			nodo.setAttribute("ui.style", "fill-color: " + COLOR_SOLUCION + ";");
		}
	}
}
