package vista;

import java.awt.EventQueue;
import java.io.File;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import controlador.ControladorPrincipal;
import modelo.Aplicacion;
import modelo.Arista;
import modelo.ObservadorConjuntoDominante;

public class VentanaPrincipal implements ObservadorConjuntoDominante {

	private JFrame _frame;
	private ControladorPrincipal _controlador;
	private Aplicacion _aplicacion;
	private Graph _grafo;

	public static void main(String[] args) {
		System.setProperty("org.graphstream.ui", "swing");

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal window = new VentanaPrincipal();
					window._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaPrincipal() {
		_aplicacion = new Aplicacion();
		_controlador = new ControladorPrincipal(_aplicacion);

		initialize();

		// registro luego de inicializar
		_aplicacion.registrar(this);
	}

	private void initialize() {
		_frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();

		panelContenido.add(crearBotonCargar());
		panelContenido.add(crearBotonResolverGoloso());
		panelContenido.add(crearVisualizacionGrafo());

	}

	private void configurarVentana() {
		_frame.setTitle("Bienvenido - Conjunto Dominante Mínimo");
		_frame.setSize(1024, 768);
		_frame.setResizable(false);
		_frame.setLocationRelativeTo(null);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private JPanel crearPanelContenido() {
		JPanel panelContenido = new JPanel();
		panelContenido.setLayout(null);

		_frame.setContentPane(panelContenido);
		return panelContenido;
	}

	private JButton crearBotonCargar() {
		JButton btnCargar = new JButton("Cargar grafo (.json)");
		btnCargar.setBounds(10, 10, 180, 30);
		btnCargar.addActionListener(e -> seleccionarArchivo());
		return btnCargar;
	}

	private void seleccionarArchivo() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Archivos JSON (*.json)", "json"));
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(_frame) == JFileChooser.APPROVE_OPTION) {
			File archivo = chooser.getSelectedFile();
			_controlador.cargarGrafoDesdeJSON(archivo.getAbsolutePath());
		}
	}

	private JButton crearBotonResolverGoloso() {
		JButton btnResolver = new JButton("Resolver goloso");
		int ancho = 180;
		int x = (_frame.getWidth() - ancho) / 2;
		btnResolver.setBounds(x, 10, ancho, 30);
		btnResolver.addActionListener(e -> resolverGoloso());
		return btnResolver;
	}

	private void resolverGoloso() {
		_controlador.resolver();
	}

	private ViewPanel crearVisualizacionGrafo() {
		_grafo = new SingleGraph("Grafo");

		SwingViewer viewer = new SwingViewer(_grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();

		ViewPanel panelGrafo = (ViewPanel) viewer.addDefaultView(false);
		panelGrafo.setBounds(10, 50, 988, 670);

		return panelGrafo;
	}

	// TODO: el parametro esta al pedo aca? ya es un atributo de clase
	@Override
	public void notificarNuevoGrafo(Aplicacion aplicacion) {
		mostrarGrafo();
	}

	private void mostrarGrafo() {
		_grafo.clear();
		_grafo.setAttribute("ui.stylesheet",
				"node { size: 24px; fill-color: #DDDDDD; stroke-mode: plain; stroke-color: black; text-size: 14; text-alignment: center; }");

		agregarVertices();
		agregarAristas();
	}

	// TODO: va directo al modelo. Esta mal?
	private void agregarVertices() {
		for (Integer vertice : _aplicacion.obtenerVertices()) {
			Node nodo = _grafo.addNode(String.valueOf(vertice));
			nodo.setAttribute("ui.label", String.valueOf(vertice));
		}
	}

	// TODO: va directo al modelo. Esta mal?
	private void agregarAristas() {
		for (Arista<Integer> arista : _aplicacion.obtenerAristas()) {
			String extremo1 = String.valueOf(arista.obtenerExtremo1());
			String extremo2 = String.valueOf(arista.obtenerExtremo2());
			String id = extremo1 + "-" + extremo2;
			_grafo.addEdge(id, extremo1, extremo2);
		}
	}

	@Override
	public void notificarSolucion(Aplicacion aplicacion) {
		mostrarSolucionSobreGrafo(aplicacion);
	}

	private void mostrarSolucionSobreGrafo(Aplicacion aplicacion) {
		Set<Integer> conjuntoSolucion = aplicacion.obtenerSolucion();

		pintarSolucionSobreGrafo(conjuntoSolucion);
	}

	private void pintarSolucionSobreGrafo(Set<Integer> conjuntoSolucion) {
		for (Integer vertice : conjuntoSolucion) {
			Node nodo = _grafo.getNode(String.valueOf(vertice));
			nodo.setAttribute("ui.style", "fill-color: yellow;");
		}
	}

}
