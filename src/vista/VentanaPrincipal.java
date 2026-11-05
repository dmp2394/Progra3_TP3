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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

import controlador.ControladorPrincipal;
import modelo.Aplicacion;
import modelo.ObservadorConjuntoDominante;
import modelo.ResultadoComparacion;
import modelo.ResultadoStress;

public class VentanaPrincipal implements ObservadorConjuntoDominante {

	private JFrame _frame;
	private ControladorPrincipal _controlador;
	private Aplicacion _aplicacion;
	private VisualizadorGrafo _visualizadorGrafo;
	private JLabel _etiquetaEstadisticas;
	private JTextArea _areaResultados;
	private JButton _botonDetener;
	private JButton _botonStress;
	private JButton _botonComparar;
	private JButton _botonLimpiar;
	private JToggleButton _botonTema;
	private SwingWorker<?, ?> _trabajoActual;
	private String _ultimoAlgoritmo;
	private Timer _timerResolviendo;
	private int _cantidadPuntos;
	private String _resolviendoTextoBase = "Resolviendo";
	private String _resolviendoTextoComparacion = "Comparando";
	private int _intervaloResolviendo = 421;
	private JButton _botonResolverBacktracking;
	private JButton _botonResolverGoloso;

	private static final int PIXELES_ANCHO = 1366;
	private static final int PIXELES_ALTO = 768;

	public static void main(String[] args) {
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
		panelContenido.add(crearBotonLimpiar());
		panelContenido.add(crearVisualizacionGrafo());
		panelContenido.add(crearPanelResultados());
		panelContenido.add(crearEtiquetaEstadisticasEnPie());
		panelContenido.add(crearBotonTema());

		Tema.aplicar(_frame);

	}

	private void configurarVentana() {
		_frame.setTitle("Bienvenido - Conjunto Dominante Mínimo");
		_frame.setSize(PIXELES_ANCHO, PIXELES_ALTO);
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
			try {
				_controlador.cargarGrafoDesdeJSON(archivo.getAbsolutePath());
				_botonResolverGoloso.setEnabled(true);
				_botonResolverBacktracking.setEnabled(true);
				_botonComparar.setEnabled(true);
				_botonLimpiar.setEnabled(true);
				_areaResultados.setText(
						"Resultados\n\nElija un algoritmo, compare Goloso y Backtracking o ejecute un test de Stress del backtracking.");
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(_frame, "No se pudo cargar el grafo:\n" + e.getMessage(),
						"Error al cargar el archivo", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private JButton crearBotonResolverGoloso() {
		_botonResolverGoloso = new JButton("Resolver goloso");
		_botonResolverGoloso.setBounds(208, 10, 180, 30);
		_botonResolverGoloso.addActionListener(e -> resolverGoloso());
		_botonResolverGoloso.setEnabled(false);
		return _botonResolverGoloso;
	}

	private void resolverGoloso() {
		_ultimoAlgoritmo = "Goloso";
		mostrarGrafo();
		_controlador.resolver();
	}

	private void mostrarGrafo() {
		_visualizadorGrafo.mostrar(_aplicacion.obtenerVertices(), _aplicacion.obtenerAristas());
	}

	private JButton crearBotonResolverBacktracking() {
		_botonResolverBacktracking = new JButton("Resolver backtracking");
		_botonResolverBacktracking.setBounds(430, 10, 180, 30);
		_botonResolverBacktracking.addActionListener(e -> resolverBacktracking());
		_botonResolverBacktracking.setEnabled(false);
		return _botonResolverBacktracking;
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

	private JButton crearBotonComparar() {
		_botonComparar = new JButton("Comparar algoritmos");
		_botonComparar.setBounds(660, 10, 155, 30);
		_botonComparar.addActionListener(e -> compararAlgoritmos());
		_botonComparar.setEnabled(false);
		return _botonComparar;
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
						return;
					}
					pintarSolucionSobreGrafo(resultado.obtenerSolucionBacktracking());
					_etiquetaEstadisticas
							.setText(resultado.obtenerResumen() + " | En el grafo: solución del backtracking");
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

	private void pintarSolucionSobreGrafo(Set<Integer> conjuntoSolucion) {
		_visualizadorGrafo.pintarSolucion(conjuntoSolucion);
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

	private JButton crearBotonStress() {
		_botonStress = new JButton("Stress");
		_botonStress.setBounds(870, 10, 123, 30);
		_botonStress.addActionListener(e -> ejecutarStress());
		return _botonStress;
	}

	private void ejecutarStress() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}

		_botonStress.setEnabled(false);
		_botonDetener.setEnabled(true);
		_botonLimpiar.setEnabled(true);
		_botonStress.setText("Ejecutando...");
		_visualizadorGrafo.limpiar();
		_areaResultados.setText("STRESS\n\n");
		_etiquetaEstadisticas.setText("Stress iniciado: tamaño creciente desde " + Aplicacion.TAMANIO_INICIAL_STRESS
				+ " vértices. Presione Detener para cortar.");

		SwingWorker<Void, ResultadoStress> worker = new SwingWorker<Void, ResultadoStress>() {
			@Override
			protected Void doInBackground() {
				_controlador.ejecutarStress(resultado -> publish(resultado));
				return null;
			}

			@Override
			protected void process(List<ResultadoStress> resultados) {
				for (ResultadoStress resultado : resultados) {
					_areaResultados.append(formatearResultadoStress(resultado));
				}
				_areaResultados.setCaretPosition(_areaResultados.getDocument().getLength());

				// Si ya se canceló, la barra de abajo quedó vacía y no se vuelve a escribir.
				if (!_controlador.fueCancelado()) {
					ResultadoStress ultimo = resultados.get(resultados.size() - 1);
					_etiquetaEstadisticas.setText(ultimo.obtenerResumen());
				}
			}

			@Override
			protected void done() {
				_botonStress.setEnabled(true);
				_botonDetener.setEnabled(false);
				_botonStress.setText("Stress");
			}
		};
		_trabajoActual = worker;
		worker.execute();
	}

	private String formatearResultadoStress(ResultadoStress resultado) {
		return String.format("#%d: %d vértices, %d aristas\nTiempo: %s\n\n",
				resultado.obtenerNumeroInstancia(), resultado.obtenerCantidadVertices(),
				resultado.obtenerCantidadAristas(), formatearTiempo(resultado.obtenerTiempoNano()));
	}

	private JButton crearBotonDetener() {
		_botonDetener = new JButton("Detener");
		_botonDetener.setBounds(1037, 10, 160, 30);
		_botonDetener.setEnabled(false);
		_botonDetener.addActionListener(e -> detenerEjecucion());
		return _botonDetener;
	}

	private void detenerEjecucion() {
		_controlador.detenerEjecucion();
		_etiquetaEstadisticas.setText("");
	}

	private JButton crearBotonLimpiar() {
		_botonLimpiar = new JButton("Limpiar");
		_botonLimpiar.setBounds(1207, 11, 133, 30);
		_botonLimpiar.setEnabled(false);
		_botonLimpiar.addActionListener(e -> limpiarSolucion());
		return _botonLimpiar;
	}

	private void limpiarSolucion() {
		if (_trabajoActual != null && !_trabajoActual.isDone()) {
			return;
		}
		_controlador.limpiarSolucion();
		mostrarGrafo();
		_etiquetaEstadisticas.setText(String.format("Grafo: %d vértices, %d aristas - solución limpiada",
				_aplicacion.obtenerVertices().size(), _aplicacion.obtenerAristas().size()));
	}

	private JComponent crearVisualizacionGrafo() {
		_visualizadorGrafo = new VisualizadorGrafo("Grafo");

		JComponent panelGrafo = _visualizadorGrafo.obtenerPanel();
		panelGrafo.setBounds(10, 61, 1017, 565);

		return panelGrafo;
	}

	private JScrollPane crearPanelResultados() {

		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(1037, 61, 303, 565);
		scroll.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(
						Tema.esOscuro() ? new java.awt.Color(115, 125, 135) : new java.awt.Color(150, 160, 170)),
				BorderFactory.createEmptyBorder(4, 4, 4, 4)));
		_areaResultados = new JTextArea(
				"Resultados\n\nCargue un grafo en formato .json o ejecute un test de Stress del backtracking.");
		scroll.setViewportView(_areaResultados);
		_areaResultados.setEditable(false);
		_areaResultados.setLineWrap(true);
		_areaResultados.setWrapStyleWord(true);
		_areaResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		return scroll;
	}

	private JLabel crearEtiquetaEstadisticasEnPie() {
		_etiquetaEstadisticas = new JLabel("Grafo: 0 vértices, 0 aristas");
		_etiquetaEstadisticas.setBounds(10, 679, 780, 24);
		return _etiquetaEstadisticas;
	}

	private JToggleButton crearBotonTema() {
		_botonTema = Tema.crearBotonTema();
		_botonTema.setBounds(1176, 651, 164, 32);
		_botonTema.addActionListener(e -> SwingUtilities.invokeLater(this::aplicarTemaAGrafo));
		return _botonTema;
	}

	private void aplicarTemaAGrafo() {
		_visualizadorGrafo.aplicarTema();
	}

	@Override
	public void notificarNuevoGrafo() {
		mostrarGrafo();
		_etiquetaEstadisticas.setText(String.format("Grafo: %d vértices, %d aristas",
				_aplicacion.obtenerVertices().size(), _aplicacion.obtenerAristas().size()));
	}

	@Override
	public void notificarSolucion() {
		mostrarSolucionSobreGrafo();
		mostrarSolucionIndividual(_ultimoAlgoritmo == null ? "Algoritmo" : _ultimoAlgoritmo,
				_aplicacion.obtenerSolucion());
	}

	private void mostrarSolucionSobreGrafo() {
		Set<Integer> conjuntoSolucion = _aplicacion.obtenerSolucion();
		pintarSolucionSobreGrafo(conjuntoSolucion);
	}

	private void mostrarSolucionIndividual(String algoritmo, Set<Integer> solucion) {
		_areaResultados.setText(algoritmo + "\n" + "Tamaño: " + solucion.size() + "\n" + "Vértices: "
				+ formatearVertices(solucion) + "\n");
		_etiquetaEstadisticas.setText(algoritmo + ": " + solucion.size() + " vértices dominantes");
	}

}
