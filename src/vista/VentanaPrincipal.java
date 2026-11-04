package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
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
import modelo.ResultadoComparacion;
import modelo.ResultadoStress;

public class VentanaPrincipal implements ObservadorConjuntoDominante {

	private JFrame _frame;
	private ControladorPrincipal _controlador;
	private Aplicacion _aplicacion;
	private Graph _grafo;
	private JLabel _etiquetaEstadisticas;
	private JTextArea _areaResultados;
	private JButton _botonDetener;
	private JButton _botonStress;
	private JButton _botonComparar;
	private JButton _botonReset;
	private JToggleButton _botonTema;
	private SwingWorker<?, ?> _trabajoActual;
	private String _ultimoAlgoritmo;
	private Timer _timerResolviendo;
	private int _cantidadPuntos;
	private String _resolviendoTextoBase = "Resolviendo";
	private String _resolviendoTextoComparacion = "Comparando";
	private int _intervaloResolviendo = 421;

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

		_aplicacion.registrar(this);
	}

	private void initialize() {
		_frame = new JFrame();
		configurarVentana();

		JPanel panelContenido = crearPanelContenido();

		panelContenido.add(crearBotonCargar());
		panelContenido.add(crearBotonResolverGoloso());
		panelContenido.add(crearBotonResolverBacktracking());
		panelContenido.add(crearBotonComparar());
		panelContenido.add(crearBotonStress());
		panelContenido.add(crearBotonDetener());
		panelContenido.add(crearBotonReset());
		panelContenido.add(crearVisualizacionGrafo());
		panelContenido.add(crearPanelResultados());
		_etiquetaEstadisticas = new JLabel("Grafo: 0 vértices, 0 aristas");
		_etiquetaEstadisticas.setBounds(10, 655, 780, 24);
		panelContenido.add(_etiquetaEstadisticas);
		panelContenido.add(crearBotonTema());

		Tema.aplicar(_frame);

	}

	private void configurarVentana() {
		_frame.setTitle("Bienvenido - Conjunto Dominante Mínimo");
		_frame.setSize(1024, 768);
		_frame.setResizable(true);
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
		btnCargar.setBounds(10, 10, 160, 30);
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
		int x = 175;
		btnResolver.setBounds(x, 10, ancho, 30);
		btnResolver.addActionListener(e -> resolverGoloso());
		return btnResolver;
	}

	private void resolverGoloso() {
		_ultimoAlgoritmo = "Goloso";
		mostrarGrafo();
		_controlador.resolver();
	}

	private JButton crearBotonResolverBacktracking() {
		JButton btnResolver = new JButton("Resolver backtracking");
		int ancho = 180;
		int x = 360;
		btnResolver.setBounds(x, 10, ancho, 30);
		btnResolver.addActionListener(e -> resolverBacktracking());
		return btnResolver;
	}

	private JButton crearBotonComparar() {
		_botonComparar = new JButton("Comparar algoritmos");
		_botonComparar.setBounds(545, 10, 155, 30);
		_botonComparar.addActionListener(e -> compararAlgoritmos());
		return _botonComparar;
	}

	private JButton crearBotonStress() {
		_botonStress = new JButton("Stress");
		_botonStress.setBounds(705, 10, 105, 30);
		_botonStress.addActionListener(e -> ejecutarStress());
		return _botonStress;
	}

	private JButton crearBotonDetener() {
		_botonDetener = new JButton("Detener");
		_botonDetener.setBounds(815, 10, 90, 30);
		_botonDetener.setEnabled(false);
		_botonDetener.addActionListener(e -> detenerEjecucion());
		return _botonDetener;
	}

	private void detenerEjecucion() {
		_controlador.detenerEjecucion();

	}

	private JButton crearBotonReset() {
		_botonReset = new JButton("Limpiar");
		_botonReset.setBounds(910, 10, 85, 30);
		_botonReset.addActionListener(e -> limpiarSolucion());
		return _botonReset;
	}

	private JToggleButton crearBotonTema() {
		_botonTema = Tema.crearBotonTema();
		_botonTema.setBounds(835, 620, 164, 32);
		_botonTema.setText("🌙 Claro");
		_botonTema.addActionListener(e -> SwingUtilities.invokeLater(this::aplicarTemaAGrafo));
		return _botonTema;
	}

	private void limpiarSolucion() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}
		_controlador.limpiarSolucion();
		mostrarGrafo();
		_etiquetaEstadisticas.setText(String.format("Grafo: %d vértices, %d aristas - solución limpiada",
				// MMMMMM RARIII...
				_aplicacion.obtenerVertices().size(), _aplicacion.obtenerAristas().size()));
	}

	private void compararAlgoritmos() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}
		_botonComparar.setEnabled(false);
		mostrarGrafo();
		_botonDetener.setEnabled(true);
		iniciarAnimacionResolviendo(_resolviendoTextoComparacion);

		SwingWorker<ResultadoComparacion, Void> worker = new SwingWorker<ResultadoComparacion, Void>() {
			@Override
			protected ResultadoComparacion doInBackground() {
				return _controlador.compararAlgoritmos();
			}

			@Override
			protected void done() {
				detenerAnimacionResolviendo();
				_botonComparar.setEnabled(true);
				_botonDetener.setEnabled(false);
				try {
					ResultadoComparacion resultado = get();
					if (_controlador.fueCancelado()) {
						_areaResultados.setText("Cancelado.");
						_etiquetaEstadisticas.setText("Comparación cancelada");
						return;
					}
					pintarSolucionSobreGrafo(resultado.obtenerSolucionBacktracking());
					_etiquetaEstadisticas.setText(resultado.obtenerResumen());
					mostrarComparacion(resultado);
				} catch (Exception ex) {
					if (_controlador.fueCancelado()) {
						_areaResultados.setText("Comparación cancelada.");
					} else {
						_etiquetaEstadisticas.setText("Error al comparar: " + ex.getMessage());
					}
				}
			}
		};

		_trabajoActual = worker;
		worker.execute();
	}

	private void ejecutarStress() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}

		_botonStress.setEnabled(false);
		_botonDetener.setEnabled(true);
		_botonStress.setText("Ejecutando...");
		_areaResultados.setText("Stress iniciado\n20 instancias - 8 vértices\n");
		_etiquetaEstadisticas.setText("Stress iniciado: 20 instancias, 8 vértices");

		SwingWorker<Void, ResultadoStress> worker = new SwingWorker<Void, ResultadoStress>() {
			@Override
			protected Void doInBackground() {
				// arreglar esto
				// _controlador.ejecutarStress(20, 8, resultado -> publish(resultado));
				return null;
			}

			@Override
			protected void process(List<ResultadoStress> resultados) {
				if (!resultados.isEmpty()) {
					_etiquetaEstadisticas.setText(resultados.get(resultados.size() - 1).obtenerResumen());
					ResultadoStress ultimo = resultados.get(resultados.size() - 1);
					_areaResultados.append(ultimo.obtenerResumen() + "\n");
				}
			}

			@Override
			protected void done() {
				_botonStress.setEnabled(true);
				_botonDetener.setEnabled(false);
				_botonStress.setText("Stress");
				if (_controlador.fueCancelado() && _etiquetaEstadisticas.getText().indexOf("cancelado") < 0) {
					_etiquetaEstadisticas.setText(_etiquetaEstadisticas.getText() + " - cancelado");
				}
			}
		};
		_trabajoActual = worker;
		worker.execute();
	}

	private void resolverBacktracking() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}

		_ultimoAlgoritmo = "Backtracking";

		mostrarGrafo();
		_botonDetener.setEnabled(true);

		iniciarAnimacionResolviendo(_resolviendoTextoBase);

		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() {
				_controlador.resolverBacktracking();
				return null;
			}

			@Override
			protected void done() {
				detenerAnimacionResolviendo();
				_botonDetener.setEnabled(false);

				try {
					get();

					if (_controlador.fueCancelado()) {
						_areaResultados.setText("Backtracking cancelado.");
					}
				} catch (Exception ex) {
					_areaResultados.setText("Error en backtracking: " + ex.getMessage());
				}
			}
		};

		_trabajoActual = worker;
		worker.execute();
	}

	private void iniciarAnimacionResolviendo(String texto) {
		_cantidadPuntos = 0;

		_timerResolviendo = new Timer(_intervaloResolviendo, evento -> {
			_cantidadPuntos = (_cantidadPuntos + 1) % 4;

			String puntos = ".".repeat(_cantidadPuntos);
			_areaResultados.setText(texto + puntos);
		});

		_timerResolviendo.start();
	}

	private void detenerAnimacionResolviendo() {
		if (_timerResolviendo != null) {
			_timerResolviendo.stop();
			_timerResolviendo = null;
		}
	}

	private ViewPanel crearVisualizacionGrafo() {
		_grafo = new SingleGraph("Grafo");

		SwingViewer viewer = new SwingViewer(_grafo, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();

		ViewPanel panelGrafo = (ViewPanel) viewer.addDefaultView(false);
		panelGrafo.setBounds(10, 50, 790, 600);

		return panelGrafo;
	}

	// TODO: el parametro esta al pedo aca? ya es un atributo de clase
	@Override
	public void notificarNuevoGrafo(Aplicacion aplicacion) {
		mostrarGrafo();
		_etiquetaEstadisticas.setText(String.format("Grafo: %d vértices, %d aristas",
				aplicacion.obtenerVertices().size(), aplicacion.obtenerAristas().size()));
	}

	private void mostrarGrafo() {
		_grafo.clear();
		aplicarTemaAGrafo();

		agregarVertices();
		agregarAristas();
	}

	private void aplicarTemaAGrafo() {
		if (_grafo == null) {
			return;
		}

		if (Tema.esOscuro()) {
			_grafo.setAttribute("ui.stylesheet",
					"node { size: 24px; fill-color: #3A3D42; stroke-mode: plain; stroke-color: #ECEFF1; text-size: 14; text-alignment: center; }"
							+ "edge { fill-color: #AAB0B6; }" + "graph { fill-color: #2B2D30; }");
		} else {
			_grafo.setAttribute("ui.stylesheet",
					"node { size: 24px; fill-color: #DDDDDD; stroke-mode: plain; stroke-color: black; text-size: 14; text-alignment: center; }"
							+ "edge { fill-color: black; }" + "graph { fill-color: white; }");
		}
	}

	// MMMMMMM RARIII
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
		mostrarSolucionIndividual(_ultimoAlgoritmo == null ? "Algoritmo" : _ultimoAlgoritmo,
				aplicacion.obtenerSolucion());
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

	private JScrollPane crearPanelResultados() {
		JPanel contenedor = new JPanel(null);
		_areaResultados = new JTextArea("Resultados\n\nSeleccione un algoritmo o Stress.");
		_areaResultados.setEditable(false);
		_areaResultados.setLineWrap(true);
		_areaResultados.setWrapStyleWord(true);
		_areaResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		JScrollPane scroll = new JScrollPane(_areaResultados);
		scroll.setBounds(822, 50, 190, 565);
		scroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(
						Tema.esOscuro() ? new java.awt.Color(115, 125, 135) : new java.awt.Color(150, 160, 170)),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		contenedor.setBounds(822, 50, 190, 565);
		contenedor.add(scroll);
		return scroll;
	}

	private void mostrarSolucionIndividual(String algoritmo, Set<Integer> solucion) {
		_areaResultados.setText(algoritmo + "\n" + "Tamaño: " + solucion.size() + "\n" + "Vértices: "
				+ formatearVertices(solucion) + "\n");
		_etiquetaEstadisticas.setText(algoritmo + ": " + solucion.size() + " vértices dominantes");
	}

	private void mostrarComparacion(ResultadoComparacion resultado) {
		StringBuilder texto = new StringBuilder();
		texto.append("COMPARACIÓN\n\n").append("Goloso\n").append("Tamaño: ")
				.append(resultado.obtenerTamanioGrafoGoloso()).append("\n").append("Vértices: ")
				.append(formatearVertices(resultado.obtenerSolucionGolosa())).append("\nTiempo: ")
				.append(formatearTiempo(resultado.obtenerTiempoGolosoNano())).append("\n\nBacktracking\n")
				.append("Tamaño: ").append(resultado.obtenerTamanioGrafoBacktracking()).append("\n")
				.append("Vértices: ").append(formatearVertices(resultado.obtenerSolucionBacktracking()))
				.append("\nTiempo: ").append(formatearTiempo(resultado.obtenerTiempoBacktrackingNano()));
		_areaResultados.setText(texto.toString());
	}

	private String formatearVertices(Set<Integer> vertices) {
		List<Integer> ordenados = new ArrayList<>(vertices);
		Collections.sort(ordenados);
		if (ordenados.isEmpty()) {
			return "{}";
		}
		return ordenados.toString();
	}

	private String formatearTiempo(long nanoSegundos) {
		return String.format("%.3f ms", nanoSegundos / 1_000_000.0);
	}

}
